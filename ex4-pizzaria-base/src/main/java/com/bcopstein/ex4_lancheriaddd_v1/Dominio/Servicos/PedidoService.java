package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.*;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.*;

@Service
public class PedidoService {
    private final ProdutosRepository produtosRepository;
    private final ReceitasRepository receitasRepository;
    private final IngredientesRepository ingredientesRepository;
    private final ItemEstoqueRepository itemEstoqueRepository;
    private final PedidosRepository pedidosRepository;
    private final ImpostoService impostoService;
    private final DescontoService descontoService;

    @Autowired
    public PedidoService(ProdutosRepository produtosRepository,
                         ReceitasRepository receitasRepository,
                         IngredientesRepository ingredientesRepository,
                         ItemEstoqueRepository itemEstoqueRepository,
                         PedidosRepository pedidosRepository,
                         ImpostoService impostoService,
                         DescontoService descontoService){
        this.produtosRepository = produtosRepository;
        this.receitasRepository = receitasRepository;
        this.ingredientesRepository = ingredientesRepository;
        this.itemEstoqueRepository = itemEstoqueRepository;
        this.pedidosRepository = pedidosRepository;
        this.impostoService = impostoService;
        this.descontoService = descontoService;
    }

    public static class ResultadoAprovacao {
        public final boolean aprovado;
        public final List<Long> produtosIndisponiveis;
        public final double valor;
        public final double desconto;
        public final double impostos;
        public final double valorCobrado;
        public final long pedidoId;

        public ResultadoAprovacao(boolean aprovado, List<Long> produtosIndisponiveis, double valor, double desconto, double impostos, double valorCobrado, long pedidoId) {
            this.aprovado = aprovado;
            this.produtosIndisponiveis = produtosIndisponiveis;
            this.valor = valor;
            this.desconto = desconto;
            this.impostos = impostos;
            this.valorCobrado = valorCobrado;
            this.pedidoId = pedidoId;
        }
    }

    // submete pedido para aprovação: retorna ResultadoAprovacao. Não faz pagamento.
    public ResultadoAprovacao submeteParaAprovacao(String clienteCpf, List<ItemPedido> itens) {
        // calcula necessidade por ingrediente (assume 1 por ingrediente por unidade do produto) > deveria ser baseado na receita (????)
        Map<Long, Integer> necessidadePorIngrediente = new HashMap<>();
        Map<Long, Long> produtoPorIngrediente = new HashMap<>(); // map ingrediente e produto (para marcar indisponivel)
        List<Produto> produtosEnvolvidos = new ArrayList<>();
        for (ItemPedido ip : itens) {
            Produto p = produtosRepository.recuperaProdutoPorid(ip.getItem().getId());
            produtosEnvolvidos.add(p);
            for (Ingrediente ing : p.getReceita().getIngredientes()) {
                necessidadePorIngrediente.merge(ing.getId(), ip.getQuantidade(), Integer::sum);
                produtoPorIngrediente.put(ing.getId(), p.getId());
            }
        }

        // so precisa checar disponibilidade (muitas responsabilidades pro mesmo servico)
        // so checa o estoque
        Set<Long> ingredientesEmFalta = new HashSet<>();
        for (var entry : necessidadePorIngrediente.entrySet()) {
            long ingId = entry.getKey();
            int need = entry.getValue();
            var ie = itemEstoqueRepository.recuperaPorIngredienteId(ingId);
            int available = ie == null ? 0 : ie.getQuantidade();
            if (available < need) ingredientesEmFalta.add(ingId);
        }

        // produtos indisponiveis
        List<Long> produtosIndisponiveis = ingredientesEmFalta.stream()
            .map(produtoPorIngrediente::get)
            .filter(Objects::nonNull).distinct()
            .collect(Collectors.toList());

        if (!produtosIndisponiveis.isEmpty()) {
            // marca produtos como indisponiveis
            for (Long prodId : produtosIndisponiveis) {
                jdbcMarcaProdutoIndisponivel(prodId);
            }
            return new ResultadoAprovacao(false, produtosIndisponiveis, 0,0,0,0, -1);
        }

        // se tiver tudo ok, calcula preço
        double soma = 0.0;
        for (ItemPedido ip : itens) {
            Produto p = produtosRepository.recuperaProdutoPorid(ip.getItem().getId());
            soma += ((double)p.getPreco()) * ip.getQuantidade();
        }
        double descontoPercent = descontoService.percentualDescontoSeElegivel(clienteCpf);
        double desconto = soma * descontoPercent;
        double base = soma - desconto;
        double impostos = impostoService.calculaImposto(base);
        double valorCobrado = base + impostos;

        // salva pedido (status APROVADO)
        long pedidoId = pedidosRepository.inserePedido(clienteCpf, Pedido.Status.APROVADO.name(), soma, impostos, desconto, valorCobrado);
        pedidosRepository.insereItensPedido(pedidoId, itens);

        // reduz estoque de ingredientes
        for (var entry : necessidadePorIngrediente.entrySet()) {
            itemEstoqueRepository.reduzQuantidade(entry.getKey(), entry.getValue());
        }

        return new ResultadoAprovacao(true, List.of(), soma, desconto, impostos, valorCobrado, pedidoId);
    }

    // so serve pra colocar o produto.disponivel = false
    private void jdbcMarcaProdutoIndisponivel(long produtoId) {
        String sql = "UPDATE produtos SET disponivel = false WHERE id = ?";
        produtosRepository.getClass();
        if (produtosRepository instanceof com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados.ProdutosRepositoryJDBC) {
            ((com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Dados.ProdutosRepositoryJDBC)produtosRepository).marcaIndisponivel(produtoId);
        }
    }
}
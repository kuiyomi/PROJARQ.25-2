package ms.Estoque.Service;

import ms.Estoque.DTO.AtualizarEstoqueRequest;
import ms.Estoque.DTO.ItemEstoqueDTO;
import ms.Estoque.DTO.VerificarDisponibilidadeLoteRequest;
import ms.Estoque.DTO.VerificarDisponibilidadeLoteResponse;
import ms.Estoque.Entidades.Ingrediente;
import ms.Estoque.Entidades.ItemEstoque;
import ms.Estoque.Repositorio.IngredienteRepository;
import ms.Estoque.Repositorio.ItemEstoqueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EstoqueService {

    private final ItemEstoqueRepositorio itemEstoqueRepositorio;
    private final IngredienteRepositorio ingredienteRepositorio;

    public EstoqueService(ItemEstoqueRepositorio itemEstoqueRepositorio, IngredienteRepositorio ingredienteRepositorio) {
        this.itemEstoqueRepositorio = itemEstoqueRepositorio;
        this.ingredienteRepositorio = ingredienteRepositorio;
    }

    public List<ItemEstoqueDTO> listarTodos() {
        return itemEstoqueRepositorio.findAll().stream()
            .map(this::converterParaDTO)
            .collect(Collectors.toList());
    }

    public Optional<ItemEstoqueDTO> buscarPorId(Long id) {
        return itemEstoqueRepositorio.findById(id)
            .map(this::converterParaDTO);
    }

    public Optional<ItemEstoqueDTO> buscarPorIngredienteId(Long ingredienteId) {
        return itemEstoqueRepositorio.findByIngredienteId(ingredienteId)
            .map(this::converterParaDTO);
    }

    // Método que o pizzaria-service usará para verificar se há ingredientes suficientes
    public boolean verificarDisponibilidade(Long ingredienteId, Integer quantidadeNecessaria) {
        Optional<ItemEstoque> itemOpt = itemEstoqueRepositorio.findByIngredienteId(ingredienteId);
        if (itemOpt.isEmpty()) {
            // Se o item nem existe no estoque, não está disponível
            return false;
        }
        return itemOpt.get().getQuantidade() >= quantidadeNecessaria;
    }

    @Transactional // Garante que a operação seja atômica no banco de dados
    public ItemEstoqueDTO baixarEstoque(Long ingredienteId, Integer quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade a ser baixada deve ser maior que zero.");
        }
        Optional<ItemEstoque> itemOpt = itemEstoqueRepositorio.findByIngredienteId(ingredienteId);
        if (itemOpt.isEmpty()) {
            throw new IllegalArgumentException("Item de estoque não encontrado para o ingrediente: " + ingredienteId);
        }

        ItemEstoque item = itemOpt.get();
        if (item.getQuantidade() < quantidade) {
            throw new IllegalStateException("Quantidade insuficiente em estoque para o ingrediente " + ingredienteId + ". Disponível: " +
                item.getQuantidade() + ", Necessário: " + quantidade);
        }

        item.setQuantidade(item.getQuantidade() - quantidade);
        ItemEstoque itemAtualizado = itemEstoqueRepositorio.save(item);
        return converterParaDTO(itemAtualizado);
    }

    @Transactional
    public ItemEstoqueDTO adicionarEstoque(Long ingredienteId, Integer quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade a ser adicionada deve ser maior que zero.");
        }
        Optional<ItemEstoque> itemOpt = itemEstoqueRepositorio.findByIngredienteId(ingredienteId);
        
        ItemEstoque item;
        if (itemOpt.isEmpty()) {
            // Se não existe, cria um novo item de estoque
            Optional<Ingrediente> ingredienteOpt = ingredienteRepositorio.findById(ingredienteId);
            if (ingredienteOpt.isEmpty()) {
                throw new IllegalArgumentException("Ingrediente não encontrado: " + ingredienteId);
            }
            item = new ItemEstoque();
            item.setIngrediente(ingredienteOpt.get());
            item.setQuantidade(quantidade);
        } else {
            item = itemOpt.get();
            item.setQuantidade(item.getQuantidade() + quantidade);
        }

        ItemEstoque itemAtualizado = itemEstoqueRepositorio.save(item);
        return converterParaDTO(itemAtualizado);
    }

    @Transactional
    public ItemEstoqueDTO criarItemEstoque(Long ingredienteId, Integer quantidade) {
        if (quantidade < 0) {
            throw new IllegalArgumentException("A quantidade inicial não pode ser negativa.");
        }
        // Verifica se já existe um item de estoque para esse ingrediente
        if (itemEstoqueRepositorio.findByIngredienteId(ingredienteId).isPresent()) {
            throw new IllegalArgumentException("Já existe um item de estoque para o ingrediente: " + ingredienteId);
        }

        Optional<Ingrediente> ingredienteOpt = ingredienteRepositorio.findById(ingredienteId);
        if (ingredienteOpt.isEmpty()) {
            throw new IllegalArgumentException("Ingrediente não encontrado: " + ingredienteId);
        }

        ItemEstoque item = new ItemEstoque();
        item.setIngrediente(ingredienteOpt.get());
        item.setQuantidade(quantidade);

        ItemEstoque itemSalvo = itemEstoqueRepositorio.save(item);
        return converterParaDTO(itemSalvo);
    }

    // Método que o pizzaria-service usará para verificar a disponibilidade de um lote de ingredientes
    public VerificarDisponibilidadeLoteResponse verificarDisponibilidadeLote(
            VerificarDisponibilidadeLoteRequest request) {
        List<Long> indisponiveis = new ArrayList<>();
        boolean todosDisponiveis = true;
        
        for (VerificarDisponibilidadeLoteRequest.ItemVerificacao item : request.getItens()) {
            if (!verificarDisponibilidade(item.getIngredienteId(), item.getQuantidadeNecessaria())) {
                indisponiveis.add(item.getIngredienteId());
                todosDisponiveis = false;
            }
        }
        
        return new VerificarDisponibilidadeLoteResponse(
            todosDisponiveis, 
            indisponiveis
        );
    }

    private ItemEstoqueDTO converterParaDTO(ItemEstoque item) {
        return new ItemEstoqueDTO(
            item.getId(),
            item.getQuantidade(),
            item.getIngrediente().getId(),
            item.getIngrediente().getDescricao()
        );
    }
}
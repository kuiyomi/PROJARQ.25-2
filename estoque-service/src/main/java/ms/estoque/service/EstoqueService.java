package ms.estoque.service;

import ms.estoque.dto.AtualizarEstoqueRequest;
import ms.estoque.dto.ItemEstoqueDTO;
import ms.estoque.dto.VerificarDisponibilidadeLoteRequest;
import ms.estoque.dto.VerificarDisponibilidadeLoteResponse;
import ms.estoque.entity.Ingrediente;
import ms.estoque.entity.ItemEstoque;
import ms.estoque.repository.IngredienteRepository;
import ms.estoque.repository.ItemEstoqueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EstoqueService {

    private final ItemEstoqueRepository itemEstoqueRepository;
    private final IngredienteRepository ingredienteRepository;

    public EstoqueService(ItemEstoqueRepository itemEstoqueRepository, IngredienteRepository ingredienteRepository) {
        this.itemEstoqueRepository = itemEstoqueRepository;
        this.ingredienteRepository = ingredienteRepository;
    }

    public List<ItemEstoqueDTO> listarTodos() {
        return itemEstoqueRepository.findAll().stream()
            .map(this::converterParaDTO)
            .collect(Collectors.toList());
    }

    public Optional<ItemEstoqueDTO> buscarPorId(Long id) {
        return itemEstoqueRepository.findById(id)
            .map(this::converterParaDTO);
    }

    public Optional<ItemEstoqueDTO> buscarPorIngredienteId(Long ingredienteId) {
        return itemEstoqueRepository.findByIngredienteId(ingredienteId)
            .map(this::converterParaDTO);
    }

    public boolean verificarDisponibilidade(Long ingredienteId, Integer quantidadeNecessaria) {
        Optional<ItemEstoque> itemOpt = itemEstoqueRepository.findByIngredienteId(ingredienteId);
        if (itemOpt.isEmpty()) {
            return false;
        }
        return itemOpt.get().getQuantidade() >= quantidadeNecessaria;
    }

    @Transactional
    public ItemEstoqueDTO baixarEstoque(Long ingredienteId, Integer quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade a ser baixada deve ser maior que zero.");
        }
        Optional<ItemEstoque> itemOpt = itemEstoqueRepository.findByIngredienteId(ingredienteId);
        if (itemOpt.isEmpty()) {
            throw new IllegalArgumentException("Item de estoque não encontrado para o ingrediente: " + ingredienteId);
        }

        ItemEstoque item = itemOpt.get();
        if (item.getQuantidade() < quantidade) {
            throw new IllegalStateException("Quantidade insuficiente em estoque para o ingrediente " + ingredienteId + ". Disponível: " +
                item.getQuantidade() + ", Necessário: " + quantidade);
        }

        item.setQuantidade(item.getQuantidade() - quantidade);
        ItemEstoque itemAtualizado = itemEstoqueRepository.save(item);
        return converterParaDTO(itemAtualizado);
    }

    @Transactional
    public ItemEstoqueDTO adicionarEstoque(Long ingredienteId, Integer quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade a ser adicionada deve ser maior que zero.");
        }
        Optional<ItemEstoque> itemOpt = itemEstoqueRepository.findByIngredienteId(ingredienteId);

        ItemEstoque item;
        if (itemOpt.isEmpty()) {
            Optional<Ingrediente> ingredienteOpt = ingredienteRepository.findById(ingredienteId);
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

        ItemEstoque itemAtualizado = itemEstoqueRepository.save(item);
        return converterParaDTO(itemAtualizado);
    }

    @Transactional
    public ItemEstoqueDTO criarItemEstoque(Long ingredienteId, Integer quantidade) {
        if (quantidade < 0) {
            throw new IllegalArgumentException("A quantidade inicial não pode ser negativa.");
        }
        if (itemEstoqueRepository.findByIngredienteId(ingredienteId).isPresent()) {
            throw new IllegalArgumentException("Já existe um item de estoque para o ingrediente: " + ingredienteId);
        }

        Optional<Ingrediente> ingredienteOpt = ingredienteRepository.findById(ingredienteId);
        if (ingredienteOpt.isEmpty()) {
            throw new IllegalArgumentException("Ingrediente não encontrado: " + ingredienteId);
        }

        ItemEstoque item = new ItemEstoque();
        item.setIngrediente(ingredienteOpt.get());
        item.setQuantidade(quantidade);

        ItemEstoque itemSalvo = itemEstoqueRepository.save(item);
        return converterParaDTO(itemSalvo);
    }

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
package br.edu.unifacisa.impacta.service;

import br.edu.unifacisa.impacta.model.AcaoSocioambiental;
import br.edu.unifacisa.impacta.repository.AcaoSocioambientalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AcaoSocioambientalService {

    private final AcaoSocioambientalRepository acaoRepository;

    public AcaoSocioambientalService(AcaoSocioambientalRepository acaoRepository) {
        this.acaoRepository = acaoRepository;
    }

    public List<AcaoSocioambiental> listarTodos() {
        return acaoRepository.findAll();
    }

    public Optional<AcaoSocioambiental> buscarPorId(Long id) {
        return acaoRepository.findById(id);
    }

    public AcaoSocioambiental salvar(AcaoSocioambiental acao) {
        return acaoRepository.save(acao);
    }

    public AcaoSocioambiental atualizar(Long id, AcaoSocioambiental acaoAtualizada) {
        AcaoSocioambiental acao = acaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ação não encontrada com id: " + id));

        acao.setTitulo(acaoAtualizada.getTitulo());
        acao.setDescricao(acaoAtualizada.getDescricao());
        acao.setData(acaoAtualizada.getData());
        acao.setCapacidadeMax(acaoAtualizada.getCapacidadeMax());
        acao.setPontuacao(acaoAtualizada.getPontuacao());
        acao.setCategoria(acaoAtualizada.getCategoria());

        return acaoRepository.save(acao);
    }

    public void deletar(Long id) {
        if (!acaoRepository.existsById(id)) {
            throw new RuntimeException("Ação não encontrada com id: " + id);
        }
        acaoRepository.deleteById(id);
    }
}

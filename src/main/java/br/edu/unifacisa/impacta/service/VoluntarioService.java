package br.edu.unifacisa.impacta.service;

import br.edu.unifacisa.impacta.model.Voluntario;
import org.springframework.stereotype.Service;
import br.edu.unifacisa.impacta.repository.VoluntarioRepository;
import br.edu.unifacisa.impacta.model.AcaoSocioambiental;

import java.util.List;
import java.util.Optional;

@Service
public class VoluntarioService {

    private final VoluntarioRepository voluntarioRepository;

    public VoluntarioService(VoluntarioRepository voluntarioRepository) {
        this.voluntarioRepository = voluntarioRepository;
    }

    public List<Voluntario> listarTodos() {
        return voluntarioRepository.findAll();
    }

    public Optional<Voluntario> buscarPorId(Long id) {
        return voluntarioRepository.findById(id);
    }

    public Voluntario salvar(Voluntario voluntario) {
        return voluntarioRepository.save(voluntario);
    }

    public Voluntario atualizar(Long id, Voluntario voluntarioAtualizado) {
        Voluntario voluntario = voluntarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voluntário não encontrado com id: " + id));

        voluntario.setNome(voluntarioAtualizado.getNome());
        voluntario.setEmail(voluntarioAtualizado.getEmail());
        voluntario.setMatricula(voluntarioAtualizado.getMatricula());
        voluntario.setQuantidadeAcoes(voluntarioAtualizado.getQuantidadeAcoes());
        voluntario.setPontuacaoImpacto(voluntarioAtualizado.getPontuacaoImpacto());


        return voluntarioRepository.save(voluntario);
    }

    public void deletar(Long id) {
        if (!voluntarioRepository.existsById(id)) {
            throw new RuntimeException("Voluntário não encontrado com id: " + id);
        }
        voluntarioRepository.deleteById(id);
    }

    // VoluntarioService.java - adicione este método
    public List<AcaoSocioambiental> buscarAcoesDeUm(Long voluntarioId) {
        return voluntarioRepository.findAcoesByVoluntarioId(voluntarioId);
    }
}

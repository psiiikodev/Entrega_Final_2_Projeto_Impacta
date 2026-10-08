package br.edu.unifacisa.impacta.service;
import br.edu.unifacisa.impacta.model.Curso;
import br.edu.unifacisa.impacta.model.Estudante;
import br.edu.unifacisa.impacta.repository.CursoRepository;
import br.edu.unifacisa.impacta.repository.EstudanteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class EstudanteService {

    private final EstudanteRepository estudanteRepository;
    private final CursoRepository cursoRepository;

    public EstudanteService(EstudanteRepository estudanteRepository, CursoRepository cursoRepository) {
        this.estudanteRepository = estudanteRepository;
        this.cursoRepository = cursoRepository;
    }

    //crud

    public List<Estudante> listarTodos() {
        return estudanteRepository.findAll();
    }

    public Optional<Estudante> buscarPorId(Long id) {
        return estudanteRepository.findById(id);
    }

    @Transactional
    public Estudante criar(Estudante estudante) {
        if (estudanteRepository.existsByEmail(estudante.getEmail())) {
            throw new IllegalStateException("Já existe um estudante com o email: " + estudante.getEmail());
        }
        // Matrícula só pelo endpoint de matricular, então ignoramos cursos que vierem no body
        estudante.setId(null);
        estudante.setCursos(new HashSet<>());
        return estudanteRepository.save(estudante);
    }

    @Transactional
    public Estudante atualizar(Long id, Estudante atualizado) {
        Estudante estudante = buscarEstudanteOuErro(id);

        if (estudanteRepository.existsByEmailAndIdNot(atualizado.getEmail(), id)) {
            throw new IllegalStateException("Já existe um estudante com o email: " + atualizado.getEmail());
        }

        // Muda só nome e email; as matrículas não mexem aqui
        estudante.setNome(atualizado.getNome());
        estudante.setEmail(atualizado.getEmail());
        return estudanteRepository.save(estudante);
    }

    @Transactional
    public void deletar(Long id) {
        Estudante estudante = buscarEstudanteOuErro(id);
        estudanteRepository.delete(estudante);
    }

    //  relação n:n matricula

    @Transactional
    public Estudante matricular(Long estudanteId, Long cursoId) {
        Estudante estudante = buscarEstudanteOuErro(estudanteId);
        Curso curso = buscarCursoOuErro(cursoId);

        if (!estudante.matricular(curso)) {
            throw new IllegalStateException("Estudante já está matriculado neste curso.");
        }
        return estudanteRepository.save(estudante);
    }

    @Transactional
    public Estudante desmatricular(Long estudanteId, Long cursoId) {
        Estudante estudante = buscarEstudanteOuErro(estudanteId);
        Curso curso = buscarCursoOuErro(cursoId);

        if (!estudante.desmatricular(curso)) {
            throw new IllegalStateException("Estudante não está matriculado neste curso.");
        }
        return estudanteRepository.save(estudante);
    }

    // auxiliares

    private Estudante buscarEstudanteOuErro(Long id) {
        return estudanteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estudante não encontrado com id: " + id));
    }

    private Curso buscarCursoOuErro(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado com id: " + id));
    }
}
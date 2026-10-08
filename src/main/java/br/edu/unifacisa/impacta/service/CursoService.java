package br.edu.unifacisa.impacta.service;

import br.edu.unifacisa.impacta.model.Curso;
import br.edu.unifacisa.impacta.model.Instructor;
import br.edu.unifacisa.impacta.repository.CursoRepository;
import br.edu.unifacisa.impacta.repository.InstructorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;
    private final InstructorRepository instructorRepository;

    public CursoService(CursoRepository cursoRepository,
                        InstructorRepository instructorRepository) {
        this.cursoRepository = cursoRepository;
        this.instructorRepository = instructorRepository;
    }

    public List<Curso> listarTodos() {
        return cursoRepository.findAll();
    }

    public Optional<Curso> buscarPorId(Long id) {
        return cursoRepository.findById(id);
    }

    public Curso criar(Long instructorId, String nome) {

        if (instructorId == null) {
            throw new IllegalArgumentException(
                    "Instrutor é obrigatório para criar o curso."
            );
        }

        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Instrutor não encontrado com id: " + instructorId
                        )
                );

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                    "Nome do curso é obrigatório."
            );
        }

        Curso curso = new Curso(nome);
        curso.setInstructor(instructor);

        return cursoRepository.save(curso);
    }

    public Curso atualizar(Long id, String nome, Long instructorId) {

        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Curso não encontrado com id: " + id
                        )
                );

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                    "Nome do curso é obrigatório."
            );
        }

        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Instrutor não encontrado com id: " + instructorId
                        )
                );

        curso.setNome(nome);
        curso.setInstructor(instructor);

        return cursoRepository.save(curso);
    }

    public void deletar(Long id) {

        if (!cursoRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "Curso não encontrado com id: " + id
            );
        }

        cursoRepository.deleteById(id);
    }
}

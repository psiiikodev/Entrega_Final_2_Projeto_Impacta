package br.edu.unifacisa.impacta.service;

import br.edu.unifacisa.impacta.model.Curso;
import br.edu.unifacisa.impacta.model.Instructor;
import br.edu.unifacisa.impacta.model.Profile;
import br.edu.unifacisa.impacta.repository.InstructorRepository;
import br.edu.unifacisa.impacta.repository.ProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InstructorService {

    private final InstructorRepository instructorRepository;
    private final ProfileRepository profileRepository;

    public InstructorService(InstructorRepository instructorRepository, ProfileRepository profileRepository) {
        this.instructorRepository = instructorRepository;
        this.profileRepository = profileRepository;
    }

    public List<Instructor> listarTodos() {
        return instructorRepository.findAll();
    }

    public Optional<Instructor> buscarPorId(Long id) {
        return instructorRepository.findById(id);
    }

    public List<Instructor> buscarPorProfile(Long profileId) {
        return instructorRepository.findByProfileId(profileId);
    }

    @Transactional
    public Instructor criar(Long profileId, String especialidade, List<String> nomesCursos) {
        if (profileId == null) {
            throw new IllegalArgumentException("Perfil é obrigatório para criar o instrutor.");
        }

        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new IllegalArgumentException("Perfil não encontrado com id: " + profileId));

        if (especialidade == null || especialidade.isBlank()) {
            throw new IllegalArgumentException("Especialidade do instrutor é obrigatória.");
        }
        if (instructorRepository.existsByProfileId(profileId)) {
            throw new IllegalArgumentException("Já existe um instrutor associado a este perfil.");
        }

        Instructor instructor = new Instructor(especialidade);
        instructor.setProfile(profile);

        if (nomesCursos != null) {
            for (String nomeCurso : nomesCursos) {
                if (nomeCurso == null || nomeCurso.isBlank()) {
                    throw new IllegalArgumentException("O nome de cada curso é obrigatório.");
                }
                instructor.adicionarCurso(new Curso(nomeCurso));
            }
        }

        return instructorRepository.save(instructor);
    }

    @Transactional
    public Instructor atualizar(Long id, Instructor instructorAtualizado) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Instrutor não encontrado com id: " + id));

        instructor.setEspecialidade(instructorAtualizado.getEspecialidade());

        if (instructorAtualizado.getProfile() != null) {
            instructor.setProfile(instructorAtualizado.getProfile());
        }

        instructor.getCursos().clear();
        if (instructorAtualizado.getCursos() != null) {
            for (Curso curso : instructorAtualizado.getCursos()) {
                instructor.adicionarCurso(curso);
            }
        }

        return instructorRepository.save(instructor);
    }

    public void deletar(Long id) {
        if (!instructorRepository.existsById(id)) {
            throw new IllegalArgumentException("Instrutor não encontrado com id: " + id);
        }
        instructorRepository.deleteById(id);
    }
}

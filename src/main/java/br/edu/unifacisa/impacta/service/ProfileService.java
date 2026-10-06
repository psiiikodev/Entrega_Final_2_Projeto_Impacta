package br.edu.unifacisa.impacta.service;

import br.edu.unifacisa.impacta.model.Profile;
import br.edu.unifacisa.impacta.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public List<Profile> listarTodos() {
        return profileRepository.findAll();
    }

    public Optional<Profile> buscarPorId(Long id) {
        return profileRepository.findById(id);
    }

    public Profile criar(Profile profile) {
        validarPerfil(profile);
        return profileRepository.save(profile);
    }

    public Profile atualizar(Long id, Profile profileAtualizado) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Perfil não encontrado com id: " + id));

        profile.setNome(profileAtualizado.getNome());
        profile.setEmail(profileAtualizado.getEmail());
        profile.setLinkedin(profileAtualizado.getLinkedin());

        return profileRepository.save(profile);
    }

    public void deletar(Long id) {
        if (!profileRepository.existsById(id)) {
            throw new IllegalArgumentException("Perfil não encontrado com id: " + id);
        }
        profileRepository.deleteById(id);
    }

    private void validarPerfil(Profile profile) {
        if (profile == null) {
            throw new IllegalArgumentException("Perfil é obrigatório.");
        }
        if (profile.getNome() == null || profile.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome do perfil é obrigatório.");
        }
        if (profile.getEmail() == null || profile.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email do perfil é obrigatório.");
        }
    }
}

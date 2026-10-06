package br.edu.unifacisa.impacta.controller;

import br.edu.unifacisa.impacta.model.Profile;
import br.edu.unifacisa.impacta.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public List<Profile> listar() {
        return profileService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profile> buscarPorId(@PathVariable Long id) {
        return profileService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Profile> criar(@Valid @RequestBody Profile profile) {
        try {
            Profile salvo = profileService.criar(profile);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profile> atualizar(@PathVariable Long id, @Valid @RequestBody Profile profile) {
        try {
            return ResponseEntity.ok(profileService.atualizar(id, profile));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            profileService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

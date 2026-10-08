package br.edu.unifacisa.impacta.controller;

import br.edu.unifacisa.impacta.model.Instructor;
import br.edu.unifacisa.impacta.service.InstructorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/instructors")
public class InstructorController {

    private final InstructorService instructorService;

    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping
    public List<Instructor> listar() {
        return instructorService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Instructor> buscarPorId(@PathVariable Long id) {
        return instructorService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/perfil/{profileId}")
    public List<Instructor> buscarPorProfile(@PathVariable Long profileId) {
        return instructorService.buscarPorProfile(profileId);
    }

    @PostMapping
    public ResponseEntity<Instructor> criar(@RequestParam Long profileId,
                                           @RequestParam String especialidade,
                                           @RequestParam(required = false) List<String> cursos) {
        try {
            Instructor salvo = instructorService.criar(profileId, especialidade, cursos);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Instructor> atualizar(@PathVariable Long id, @Valid @RequestBody Instructor instructor) {
        try {
            return ResponseEntity.ok(instructorService.atualizar(id, instructor));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            instructorService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // InstructorController.java - adicione este endpoint
    @GetMapping("/{instructorId}/cursos/quantidade")
    public ResponseEntity<List<Map<String, Object>>> buscarCursosComQuantidadeAlunos(
            @PathVariable Long instructorId) {
        return ResponseEntity.ok(instructorService.buscarCursosComQuantidadeAlunos(instructorId));
    }
}

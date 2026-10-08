package br.edu.unifacisa.impacta.controller;
import br.edu.unifacisa.impacta.model.Estudante;
import br.edu.unifacisa.impacta.service.EstudanteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping ("/estudantes")

public class EstudanteController {
    private final EstudanteService estudanteService;

    public EstudanteController(EstudanteService estudanteService) {
        this.estudanteService = estudanteService;
    }

    @GetMapping
    public List<Estudante> listar() {
        return estudanteService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estudante> buscarPorId(@PathVariable Long id) {
        return estudanteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Estudante> criar(@Valid @RequestBody Estudante estudante) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(estudanteService.criar(estudante));
        } catch (IllegalStateException e) { // email duplicado
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estudante> atualizar(@PathVariable Long id, @Valid @RequestBody Estudante estudante) {
        try {
            return ResponseEntity.ok(estudanteService.atualizar(id, estudante));
        } catch (IllegalArgumentException e) { // não encontrado
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) { // email duplicado
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            estudanteService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // matricula n:n

    // post /estudantes/1/cursos/2  matricula o estudante 1 no curso 2
    @PostMapping("/{estudanteId}/cursos/{cursoId}")
    public ResponseEntity<Estudante> matricular(@PathVariable Long estudanteId, @PathVariable Long cursoId) {
        try {
            return ResponseEntity.ok(estudanteService.matricular(estudanteId, cursoId));
        } catch (IllegalArgumentException e) { // estudante ou curso não existe
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) { // já estava matriculado
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    // delete /estudantes/1/cursos/2  desmatricula o estudante 1 do curso 2
    @DeleteMapping("/{estudanteId}/cursos/{cursoId}")
    public ResponseEntity<Estudante> desmatricular(@PathVariable Long estudanteId, @PathVariable Long cursoId) {
        try {
            return ResponseEntity.ok(estudanteService.desmatricular(estudanteId, cursoId));
        } catch (IllegalArgumentException e) { // estudante ou curso não existe
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) { // não estava matriculado
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }
}
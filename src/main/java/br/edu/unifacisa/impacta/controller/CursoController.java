package br.edu.unifacisa.impacta.controller;

import br.edu.unifacisa.impacta.model.Curso;
import br.edu.unifacisa.impacta.service.CursoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    public ResponseEntity<List<Curso>> listarTodos() {
        return ResponseEntity.ok(cursoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> buscarPorId(@PathVariable Long id) {

        return cursoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Curso> criar(
            @RequestParam Long instructorId,
            @RequestParam String nome) {

        Curso curso = cursoService.criar(instructorId, nome);

        return ResponseEntity.ok(curso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso> atualizar(
            @PathVariable Long id,
            @RequestParam Long instructorId,
            @RequestParam String nome) {

        Curso curso = cursoService.atualizar(
                id,
                nome,
                instructorId
        );

        return ResponseEntity.ok(curso);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        cursoService.deletar(id);

        return ResponseEntity.noContent().build();
    }
}

package br.edu.unifacisa.impacta.controller;

import br.edu.unifacisa.impacta.model.Voluntario;
import br.edu.unifacisa.impacta.service.VoluntarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/voluntarios")
public class VoluntarioController {

    private final VoluntarioService voluntarioService;

    public VoluntarioController(VoluntarioService voluntarioService) {
        this.voluntarioService = voluntarioService;
    }

    @GetMapping
    public List<Voluntario> listar() {
        return voluntarioService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Voluntario> buscarPorId(@PathVariable Long id) {
        return voluntarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Voluntario> criar(@RequestBody Voluntario voluntario) {
        Voluntario salvo = voluntarioService.salvar(voluntario);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Voluntario> atualizar(@PathVariable Long id, @RequestBody Voluntario voluntario) {
        try {
            return ResponseEntity.ok(voluntarioService.atualizar(id, voluntario));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            voluntarioService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

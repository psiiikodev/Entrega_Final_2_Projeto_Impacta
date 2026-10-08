package br.edu.unifacisa.impacta.controller;

import br.edu.unifacisa.impacta.model.AcaoSocioambiental;
import br.edu.unifacisa.impacta.model.Voluntario;
import br.edu.unifacisa.impacta.service.AcaoSocioambientalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acoes")
public class AcaoSocioambientalController {

    private final AcaoSocioambientalService acaoService;

    public AcaoSocioambientalController(AcaoSocioambientalService acaoService) {
        this.acaoService = acaoService;
    }

    @GetMapping
    public List<AcaoSocioambiental> listar() {
        return acaoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcaoSocioambiental> buscarPorId(@PathVariable Long id) {
        return acaoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AcaoSocioambiental> criar(@RequestBody AcaoSocioambiental acao) {
        AcaoSocioambiental salva = acaoService.salvar(acao);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AcaoSocioambiental> atualizar(@PathVariable Long id, @RequestBody AcaoSocioambiental acao) {
        try {
            return ResponseEntity.ok(acaoService.atualizar(id, acao));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            acaoService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // AcaoSocioambientalController.java - adicione este endpoint
    @GetMapping("/{acaoId}/voluntarios")
    public ResponseEntity<List<Voluntario>> buscarVoluntariosDeUma(@PathVariable Long acaoId) {
        return ResponseEntity.ok(acaoService.buscarVoluntariosDeUma(acaoId));
    }
}

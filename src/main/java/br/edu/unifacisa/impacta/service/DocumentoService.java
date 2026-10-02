package br.edu.unifacisa.impacta.service;

import br.edu.unifacisa.impacta.model.Documento;
import br.edu.unifacisa.impacta.repository.DocumentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentoService {

    private final DocumentoRepository documentoRepository;

    public DocumentoService(DocumentoRepository documentoRepository) {
        this.documentoRepository = documentoRepository;
    }

    public List<Documento> listarTodos() {
        return documentoRepository.findAll();
    }

    public Optional<Documento> buscarPorId(Long id) {
        return documentoRepository.findById(id);
    }

    public Documento salvar(Documento documento) {
        return documentoRepository.save(documento);
    }

    public Documento atualizar(Long id, Documento documentoAtualizado) {
        Documento documento = documentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento não encontrado com id: " + id));

        documento.setTipo(documentoAtualizado.getTipo());
        documento.setNumero(documentoAtualizado.getNumero());
        documento.setOrgaoEmissor(documentoAtualizado.getOrgaoEmissor());
        documento.setVoluntario(documentoAtualizado.getVoluntario());

        return documentoRepository.save(documento);
    }

    public void deletar(Long id) {
        if (!documentoRepository.existsById(id)) {
            throw new RuntimeException("Documento não encontrado com id: " + id);
        }
        documentoRepository.deleteById(id);
    }
}

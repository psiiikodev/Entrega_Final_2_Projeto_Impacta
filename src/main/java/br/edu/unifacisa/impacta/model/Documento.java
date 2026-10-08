package br.edu.unifacisa.impacta.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "documentos")
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;
    private String numero;
    private String orgaoEmissor;

    // Um-para-Um: Documento <-> Voluntario
    @OneToOne
    @JoinColumn(name = "voluntario_id", unique = true)
    @JsonIgnoreProperties({"documento", "acoes"})
    private Voluntario voluntario;

    public Documento() {
    }

    public Documento(String tipo, String numero, String orgaoEmissor) {
        this.tipo = tipo;
        this.numero = numero;
        this.orgaoEmissor = orgaoEmissor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getOrgaoEmissor() {
        return orgaoEmissor;
    }

    public void setOrgaoEmissor(String orgaoEmissor) {
        this.orgaoEmissor = orgaoEmissor;
    }

    public Voluntario getVoluntario() {
        return voluntario;
    }

    public void setVoluntario(Voluntario voluntario) {
        this.voluntario = voluntario;
    }
}

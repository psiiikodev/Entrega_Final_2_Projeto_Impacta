package br.edu.unifacisa.impacta.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "voluntarios")
public class Voluntario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String matricula;
    private int quantidadeAcoes;
    private int pontuacaoImpacto;

    // Um-para-Um: Voluntario <-> Documento
    @OneToOne(mappedBy = "voluntario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("voluntario")
    private Documento documento;

    // Muitos-para-Muitos: Voluntario <-> AcaoSocioambiental
    @ManyToMany
    @JoinTable(
            name = "inscricoes",
            joinColumns = @JoinColumn(name = "voluntario_id"),
            inverseJoinColumns = @JoinColumn(name = "acao_id")
    )
    @JsonIgnoreProperties("voluntarios")
    private Set<AcaoSocioambiental> acoes = new HashSet<>();

    public Voluntario() {
    }

    public Voluntario(String nome, String email, String matricula) {
        this.nome = nome;
        this.email = email;
        this.matricula = matricula;
        this.quantidadeAcoes = 0;
        this.pontuacaoImpacto = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getQuantidadeAcoes() {
        return quantidadeAcoes;
    }

    public void setQuantidadeAcoes(int quantidadeAcoes) {
        this.quantidadeAcoes = quantidadeAcoes;
    }

    public int getPontuacaoImpacto() {
        return pontuacaoImpacto;
    }

    public void setPontuacaoImpacto(int pontuacaoImpacto) {
        this.pontuacaoImpacto = pontuacaoImpacto;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
        if (documento != null) {
            documento.setVoluntario(this);
        }
    }

    public Set<AcaoSocioambiental> getAcoes() {
        return acoes;
    }

    public void setAcoes(Set<AcaoSocioambiental> acoes) {
        this.acoes = acoes;
    }
}

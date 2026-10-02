package br.edu.unifacisa.impacta.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "acoes_socioambientais")
public class AcaoSocioambiental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private LocalDateTime data;
    private int capacidadeMax;
    private int pontuacao;

    // Muitos-para-Um: AcaoSocioambiental -> Categoria
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    @JsonIgnoreProperties("acoes")
    private Categoria categoria;

    // Muitos-para-Muitos: AcaoSocioambiental <-> Voluntario
    @ManyToMany(mappedBy = "acoes")
    @JsonIgnoreProperties({"acoes", "documento"})
    private Set<Voluntario> voluntarios = new HashSet<>();

    public AcaoSocioambiental() {
    }

    public AcaoSocioambiental(String titulo, String descricao, LocalDateTime data, int capacidadeMax, int pontuacao) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.data = data;
        this.capacidadeMax = capacidadeMax;
        this.pontuacao = pontuacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public int getCapacidadeMax() {
        return capacidadeMax;
    }

    public void setCapacidadeMax(int capacidadeMax) {
        this.capacidadeMax = capacidadeMax;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Set<Voluntario> getVoluntarios() {
        return voluntarios;
    }

    public void setVoluntarios(Set<Voluntario> voluntarios) {
        this.voluntarios = voluntarios;
    }
}

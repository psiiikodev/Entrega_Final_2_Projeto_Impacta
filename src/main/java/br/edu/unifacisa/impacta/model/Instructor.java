package br.edu.unifacisa.impacta.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "instructors")
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Especialidade do instrutor é obrigatória.")
    @Column(nullable = false)
    private String especialidade;

    @OneToOne
    @JoinColumn(name = "profile_id", unique = true, nullable = false)
    @JsonIgnoreProperties("instructor")
    private Profile profile;

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("instructor")
    private List<Curso> cursos = new ArrayList<>();

    public Instructor() {
    }

    public Instructor(String especialidade) {
        this.especialidade = especialidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    public void adicionarCurso(Curso curso) {
        cursos.add(curso);
        curso.setInstructor(this);
    }
}

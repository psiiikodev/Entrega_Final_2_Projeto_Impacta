package br.edu.unifacisa.impacta.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table (name = "estudantes")
public class Estudante {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do estudante é obrigatório.")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "Email do estudante é obrigatótio.")
    @Email(message = "Email do estudante deve ser válido.")
    @Column(nullable = false, unique = true)
    private String email;

    @ManyToMany
    @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(name = "estudante_id"),
            inverseJoinColumns = @JoinColumn(name = "curso_id")
    )
    private Set<Curso> cursos = new HashSet<>();

    public Estudante(String nome, String email) {
        this.nome = nome;
        this.email = email;
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

    public Set<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(Set<Curso> cursos) {
        this.cursos = cursos;
    }

    public boolean matricular (Curso curso) {
        return cursos.add(curso); // matricula no curso, false se ja tiver
    }

    public boolean desmatricular ( Curso curso) {
        return cursos.remove(curso);  //remover matricula do curso , false se n estiver
    }


}
package br.edu.unifacisa.impacta.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do perfil é obrigatório.")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "Email do perfil é obrigatório.")
    @Email(message = "Email do perfil deve ser válido.")
    @Column(nullable = false, unique = true)
    private String email;

    private String linkedin;

    @OneToOne(mappedBy = "profile")
    @JsonIgnoreProperties("profile")
    private Instructor instructor;

    public Profile() {
    }

    public Profile(String nome, String email, String linkedin) {
        this.nome = nome;
        this.email = email;
        this.linkedin = linkedin;
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

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }
}

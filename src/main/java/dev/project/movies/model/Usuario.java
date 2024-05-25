package dev.project.movies.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Nome não pode ser nulo")
    private String nome;

    @NotBlank(message = "Email não poder nulo")
    @Email(message = "Email digitado inválido")
    private String email;
    @NotBlank(message = "Senha não poder nula")
    private String senha;

}

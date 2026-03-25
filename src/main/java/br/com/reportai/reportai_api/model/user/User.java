package br.com.reportai.reportai_api.model.user;

import br.com.reportai.reportai_api.model.enums.user.RoleUserEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")


public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @Column(unique = true, nullable = false)
    private String cpf;

    @Email(message = "E-mail inválido")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "A senha não pode estar em branco")
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleUserEnum role;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Gatilho para carimbar a data antes de salvar no banco
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
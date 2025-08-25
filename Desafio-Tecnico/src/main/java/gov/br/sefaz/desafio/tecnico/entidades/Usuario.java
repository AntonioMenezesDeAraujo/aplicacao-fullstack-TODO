package gov.br.sefaz.desafio.tecnico.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TB_USUARIO")
public class Usuario {

    public Usuario(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOME", nullable = false, length = 300)
    private String nome;

    @Column(name = "LOGIN", nullable = false, length = 300)
    private String login;

    @Column(name = "SENHA", nullable = false)
    private String senha;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Tarefa> listTarefa;
}

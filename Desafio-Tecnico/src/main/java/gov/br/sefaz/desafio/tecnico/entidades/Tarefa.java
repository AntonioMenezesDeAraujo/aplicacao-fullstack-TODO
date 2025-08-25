package gov.br.sefaz.desafio.tecnico.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Builder
@Entity
@Table(name = "TB_TAREFA")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Tarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TITULO", nullable = false, length = 300)
    private String titulo;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false, name = "STATUS")
    private Status status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_CADASTRO", nullable = false, updatable = false)
    private Date dataCriacao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_ATUALIZACAO", nullable = false, updatable = false)
    private Date dataAtualizacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USUARIO_ID", nullable = false)
    private Usuario usuario;
}

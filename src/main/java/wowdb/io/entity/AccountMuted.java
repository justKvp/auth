package wowdb.io.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "account_muted"
)
@Getter
@Setter
public class AccountMuted extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guid", columnDefinition = "int unsigned", length = 32)
    private Long guid;

    @Column(name = "mutedate", columnDefinition = "int unsigned", nullable = false)
    private Long mutedate;

    @Column(name = "mutetime", columnDefinition = "int unsigned", nullable = false)
    private Long mutetime;

    @Column(name = "mutedby", columnDefinition = "varchar(50)", length = 50, nullable = false)
    private String mutedby;

    @Column(name = "mutereason", columnDefinition = "varchar(255)", nullable = false)
    private String mutereason;
}

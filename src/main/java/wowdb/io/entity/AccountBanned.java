package wowdb.io.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "account_banned"
)
@Getter
@Setter
public class AccountBanned extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int unsigned", length = 32)
    private Long id;

    @Column(name = "bandate", columnDefinition = "int unsigned", nullable = false)
    private Long bandate;

    @Column(name = "unbandate", columnDefinition = "int unsigned", nullable = false)
    private Long unbandate;

    @Column(name = "bannedby", columnDefinition = "varchar(50)", length = 50, nullable = false)
    private String bannedby;

    @Column(name = "banreason", columnDefinition = "varchar(255)", nullable = false)
    private String banreason;

    @Column(name = "active", columnDefinition = "tinyint unsigned", nullable = false)
    private Boolean active;
}

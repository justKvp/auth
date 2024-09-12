package wowdb.io.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "autobroadcast"
)
public class AutoBroadCast extends PanacheEntityBase {

    @Column(name = "realmid", columnDefinition = "int", nullable = false)
    @Getter
    @Setter
    private Integer realmid;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "tinyint unsigned")
    @Getter
    @Setter
    private Integer id;

    @Column(name = "weight", columnDefinition = "tinyint unsigned")
    @Getter
    @Setter
    private Integer weight;

    @Column(name = "text", columnDefinition = "longtext", nullable = false)
    @Getter
    @Setter
    private String text;
}

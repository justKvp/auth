package wowdb.io.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "realmcharacters"
)
public class RealmCharacter extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int")
    @Getter
    @Setter
    private Integer id;

    @Column(name = "realmid", columnDefinition = "int unsigned")
    @Getter
    @Setter
    private Integer realmid;

    @Column(name = "numchars", columnDefinition = "tinyint unsigned")
    @Getter
    @Setter
    private Integer numchars;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "acctid"
    )
    @JsonIgnore
    @Getter
    @Setter
    public Account account;
}

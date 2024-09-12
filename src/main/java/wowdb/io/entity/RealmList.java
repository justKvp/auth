package wowdb.io.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "realmlist"
)
public class RealmList extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int unsigned")
    @Getter
    @Setter
    private Integer id;

    @Column(name = "name", columnDefinition = "varchar(32)", length = 32)
    @Getter
    @Setter
    private String name;

    @Column(name = "address", columnDefinition = "varchar(255)")
    @Getter
    @Setter
    private String address;

    @Column(name = "localAddress", columnDefinition = "varchar(255)")
    @Getter
    @Setter
    private String localAddress;

    @Column(name = "localSubnetMask", columnDefinition = "varchar(255)")
    @Getter
    @Setter
    private String localSubnetMask;

    @Column(name = "port", columnDefinition = "smallint unsigned")
    @JsonIgnore
    @Getter
    @Setter
    private Integer port;

    @Column(name = "icon", columnDefinition = "tinyint unsigned")
    @Getter
    @Setter
    private Integer icon;

    @Column(name = "flag", columnDefinition = "tinyint unsigned")
    @Getter
    @Setter
    private Integer flag;

    @Column(name = "timezone", columnDefinition = "tinyint unsigned")
    @Getter
    @Setter
    private Integer timezone;

    @Column(name = "allowedSecurityLevel", columnDefinition = "tinyint unsigned")
    @Getter
    @Setter
    private Integer allowedSecurityLevel;

    @Column(name = "population", columnDefinition = "float unsigned")
    @Getter
    @Setter
    private Float population;

    @Column(name = "gamebuild", columnDefinition = "int unsigned")
    @Getter
    @Setter
    private Integer gamebuild;
}

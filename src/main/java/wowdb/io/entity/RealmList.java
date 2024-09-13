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
@Getter
@Setter
public class RealmList extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int unsigned")
    private Integer id;

    @Column(name = "name", columnDefinition = "varchar(32)", length = 32)
    private String name;

    @Column(name = "address", columnDefinition = "varchar(255)")
    private String address;

    @Column(name = "localAddress", columnDefinition = "varchar(255)")
    private String localAddress;

    @Column(name = "localSubnetMask", columnDefinition = "varchar(255)")
    private String localSubnetMask;

    @Column(name = "port", columnDefinition = "smallint unsigned")
    @JsonIgnore
    private Integer port;

    @Column(name = "icon", columnDefinition = "tinyint unsigned")
    private Integer icon;

    @Column(name = "flag", columnDefinition = "tinyint unsigned")
    private Integer flag;

    @Column(name = "timezone", columnDefinition = "tinyint unsigned")
    private Integer timezone;

    @Column(name = "allowedSecurityLevel", columnDefinition = "tinyint unsigned")
    private Integer allowedSecurityLevel;

    @Column(name = "population", columnDefinition = "float unsigned")
    private Float population;

    @Column(name = "gamebuild", columnDefinition = "int unsigned")
    private Integer gamebuild;
}

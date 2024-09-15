package wowdb.io.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "build_auth_key"
)
@Getter
@Setter
public class BuildAuthKey extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "build", columnDefinition = "int")
    private Integer build;

    @Column(name = "platform", columnDefinition = "char(4)", nullable = false)
    private String platform;

    @Column(name = "arch", columnDefinition = "char(4)", nullable = false)
    private String arch;

    @Column(name = "type", columnDefinition = "char(4)", nullable = false)
    private String type;

    @Column(name = "key", columnDefinition = "binary(16)", nullable = false)
    private byte[] key;

    public static BuildAuthKey findByBuild(Integer build) {
        return (BuildAuthKey) findAllByBuild(build).firstResult();
    }

    public static PanacheQuery<BuildAuthKey> findAllByBuild(Integer build) {
        return find("build", new Object[]{build});
    }
}

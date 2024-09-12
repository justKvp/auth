package wowdb.io.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "build_info"
)
public class BuildInfo extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "build", columnDefinition = "int")
    @Getter
    @Setter
    private Integer build;

    @Column(name = "majorVersion", columnDefinition = "int")
    @Getter
    @Setter
    private Integer majorVersion;

    @Column(name = "minorVersion", columnDefinition = "int")
    @Getter
    @Setter
    private Integer minorVersion;

    @Column(name = "bugfixVersion", columnDefinition = "int")
    @Getter
    @Setter
    private Integer bugfixVersion;

    @Column(name = "hotfixVersion", columnDefinition = "char(3)", length = 3)
    @Getter
    @Setter
    private String hotfixVersion;

    @Column(name = "winAuthSeed", columnDefinition = "varchar(32)", length = 32)
    @Getter
    @Setter
    private String winAuthSeed;

    @Column(name = "win64AuthSeed", columnDefinition = "varchar(32)", length = 32)
    @Getter
    @Setter
    private String win64AuthSeed;

    @Column(name = "mac64AuthSeed", columnDefinition = "varchar(32)", length = 32)
    @Getter
    @Setter
    private String mac64AuthSeed;

    @Column(name = "winChecksumSeed", columnDefinition = "varchar(40)", length = 40)
    @Getter
    @Setter
    private String winChecksumSeed;

    @Column(name = "macChecksumSeed", columnDefinition = "varchar(40)", length = 40)
    @Getter
    @Setter
    private String macChecksumSeed;

    public static BuildInfo findByBuild(Integer build) {
        return (BuildInfo) findAllByBuild(build).firstResult();
    }

    public static PanacheQuery<BuildInfo> findAllByBuild(Integer build) {
        return find("build", new Object[]{build});
    }
}

package wowdb.io.entity;

import io.quarkus.cache.CacheKey;
import io.quarkus.cache.CacheResult;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "build_info"
)
@Getter
@Setter
public class BuildInfo extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "build", columnDefinition = "int")
    private Integer build;

    @Column(name = "majorVersion", columnDefinition = "int")
    private Integer majorVersion;

    @Column(name = "minorVersion", columnDefinition = "int")
    private Integer minorVersion;

    @Column(name = "bugfixVersion", columnDefinition = "int")
    private Integer bugfixVersion;

    @Column(name = "hotfixVersion", columnDefinition = "char(3)", length = 3)
    private String hotfixVersion;

    @CacheResult(cacheName = "BuildInfoFindByBuild")
    public static BuildInfo findByBuild(@CacheKey Integer build) {
        return (BuildInfo) findAllByBuild(build).firstResult();
    }

    public static PanacheQuery<BuildInfo> findAllByBuild(Integer build) {
        return find("build", new Object[]{build});
    }
}

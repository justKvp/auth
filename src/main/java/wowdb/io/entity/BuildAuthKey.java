package wowdb.io.entity;

import io.quarkus.cache.CacheKey;
import io.quarkus.cache.CacheResult;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(
        name = "build_auth_key"
)
@Getter
@Setter
public class BuildAuthKey extends PanacheEntityBase {

    @Column(name = "build", columnDefinition = "int")
    private Integer build;

    @Column(name = "platform", columnDefinition = "char(4)", length = 4, nullable = false)
    private String platform;

    @Column(name = "arch", columnDefinition = "char(4)", length = 4, nullable = false)
    private String arch;

    @Column(name = "type", columnDefinition = "char(4)", length = 4, nullable = false)
    private String type;

    @Id
    @Column(name = "key", columnDefinition = "binary(16)", length = 16, nullable = false)
    private byte[] key;

    @CacheResult(cacheName = "BuildAuthKeyFindByBuild")
    public static List<BuildAuthKey> findByBuild(@CacheKey Integer build) {
        return findAllByBuild(build).list();
    }

    public static PanacheQuery<BuildAuthKey> findAllByBuild(Integer build) {
        return find("build", new Object[]{build});
    }
}

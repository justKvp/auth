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
        name = "rbac_permissions"
)
@Getter
@Setter
public class RbacPermission extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int unsigned")
    private Long id;

    @Column(name = "name", columnDefinition = "varchar(100)", nullable = false)
    private String name;

    @CacheResult(cacheName = "RbacPermissionFindByName")
    public static RbacPermission findByName(@CacheKey String name) {
        return (RbacPermission) findAllByName(name).firstResult();
    }

    @CacheResult(cacheName = "RbacPermissionFindById")
    public static RbacPermission cacheableFindByID(@CacheKey Long id) {
        return RbacPermission.findById(id);
    }

    public static PanacheQuery<RbacPermission> findAllByName(String name) {
        return find("name", new Object[]{name});
    }
}

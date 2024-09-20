package wowdb.io.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.cache.CacheKey;
import io.quarkus.cache.CacheResult;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "account_access"
)
@Getter
@Setter
public class AccountAccess extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccountID", columnDefinition = "int unsigned", length = 32)
    private Long accountID;

    @OneToOne(
            cascade = {CascadeType.DETACH}
    )
    @JoinColumn(
            name = "AccountID"
    )
    @JsonIgnore
    public Account account;

    @Column(name = "SecurityLevel", columnDefinition = "tinyint unsigned", nullable = false)
    private Integer securityLevel;

    @Column(name = "RealmID", columnDefinition = "int", nullable = false)
    private Integer realmID;

    @Column(name = "Comment", columnDefinition = "varchar(255)")
    private String comment;

    @CacheResult(cacheName = "AccountAccessFindByID")
    public static AccountAccess cacheableFindByID(@CacheKey Long Id) {
        return (AccountAccess) findAllByID(Id).firstResult();
    }

    public static PanacheQuery<AccountAccess> findAllByID(Long Id) {
        return find("accountID", new Object[]{Id});
    }
}

package wowdb.io.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "ip_banned"
)
@Getter
@Setter
public class IpBanned extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ip", columnDefinition = "varchar(15)", length = 15)
    private String ip;

    @Column(name = "bandate", columnDefinition = "int unsigned", nullable = false)
    private Long bandate;

    @Column(name = "unbandate", columnDefinition = "int unsigned", nullable = false)
    private Long unbandate;

    @Column(name = "bannedby", columnDefinition = "varchar(50)", length = 50, nullable = false)
    private String bannedby;

    @Column(name = "banreason", columnDefinition = "varchar(255)", nullable = false)
    private String banreason;

    public static IpBanned findByIp(String ip) {
        return (IpBanned) findAllByIp(ip).firstResult();
    }

    public static PanacheQuery<IpBanned> findAllByIp(String ip) {
        return find("ip", new Object[]{ip});
    }
}

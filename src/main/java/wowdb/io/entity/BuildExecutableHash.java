package wowdb.io.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(
        name = "build_executable_hash"
)
@Getter
@Setter
public class BuildExecutableHash extends PanacheEntityBase {

    @Column(name = "build", columnDefinition = "int")
    private Integer build;

    @Column(name = "platform", columnDefinition = "char(4)", length = 4, nullable = false)
    private String platform;

    @Id
    @Column(name = "executableHash", columnDefinition = "binary(20)", length = 20, nullable = false)
    private byte[] executableHash;

    public static List<BuildExecutableHash> findByBuild(Integer build) {
        return findAllByBuild(build).list();
    }

    public static PanacheQuery<BuildExecutableHash> findAllByBuild(Integer build) {
        return find("build", new Object[]{build});
    }
}

package wowdb.io.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "account"
)
public class Account extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int unsigned", length = 32)
    @Getter
    @Setter
    private Long id;

    @OneToOne(
            mappedBy = "account",
            fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL}
    )
    @Getter
    @Setter
    public AccountAccess accountAccess;

    @OneToMany(
            mappedBy = "account",
            fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL}
    )
    @Getter
    @Setter
    public List<RealmCharacter> realmCharacters = new ArrayList<>();

    @Column(name = "username", columnDefinition = "varchar(32)", length = 32)
    @Getter
    @Setter
    private String username;
    @JsonIgnore
    @Column(name = "salt", columnDefinition = "binary(32)", length = 32)
    @Getter
    @Setter
    private byte[] salt;
    @JsonIgnore
    @Column(name = "verifier", columnDefinition = "binary(32)", length = 32)
    @Getter
    @Setter
    private byte[] verifier;
    @JsonIgnore
    @Column(name = "session_key_auth", columnDefinition = "binary(40)", length = 40)
    @Getter
    @Setter
    private byte[] sessionKeyAuth;
    @JsonIgnore
    @Column(name = "session_key_bnet", columnDefinition = "varbinary(64)", length = 64)
    @Getter
    @Setter
    private byte[] sessionKeyBnet;
    @JsonIgnore
    @Column(name = "totp_secret", columnDefinition = "varbinary(128)", length = 128)
    @Getter
    @Setter
    private byte[] totpSecret;
    @Column(name = "email", columnDefinition = "varchar(255)")
    @Getter
    @Setter
    private String email;
    @Column(name = "reg_mail", columnDefinition = "varchar(255)")
    @Getter
    @Setter
    private String regMail;
    @Column(name = "joindate", columnDefinition = "timestamp")
    @Getter
    @Setter
    private Timestamp joinDate;
    @Column(name = "last_ip", columnDefinition = "varchar(15)", length = 15)
    @Getter
    @Setter
    private String lastIp;
    @Column(name = "last_attempt_ip", columnDefinition = "varchar(15)", length = 15)
    @Getter
    @Setter
    private String lastAttemptIp;
    @Column(name = "failed_logins", columnDefinition = "int unsigned")
    @Getter
    @Setter
    private Integer failedLogins;
    @Column(name = "locked", columnDefinition = "tinyint unsigned")
    @Getter
    @Setter
    private Integer locked;
    @Column(name = "lock_country", columnDefinition = "varchar(2)", length = 2)
    @Getter
    @Setter
    private String lockCountry;
    @Column(name = "last_login", columnDefinition = "timestamp")
    @Getter
    @Setter
    private Timestamp lastLogin;
    @Column(name = "online", columnDefinition = "tinyint unsigned")
    @Getter
    @Setter
    private Integer online;
    @Column(name = "expansion", columnDefinition = "tinyint unsigned")
    @Getter
    @Setter
    private Integer expansion;
    @Column(name = "mutetime", columnDefinition = "bigint")
    @Getter
    @Setter
    private Long muteTime;
    @Column(name = "mutereason", columnDefinition = "varchar(255)")
    @Getter
    @Setter
    private String muteReason;
    @Column(name = "muteby", columnDefinition = "varchar(50)", length = 50)
    @Getter
    @Setter
    private String muteBy;
    @Column(name = "locale", columnDefinition = "tinyint unsigned")
    @Getter
    @Setter
    private Integer locale;
    @Column(name = "os", columnDefinition = "varchar(3)", length = 3)
    @Getter
    @Setter
    private String os;
    @Column(name = "recruiter", columnDefinition = "int unsigned")
    @Getter
    @Setter
    private Integer recruiter;

    public static Account findByUsername(String userName) {
        return (Account)findAllByUsername(userName).firstResult();
    }

    public static PanacheQuery<Account> findAllByUsername(String userName) {
        return find("username", new Object[]{userName});
    }
}

package wowdb.io.services;

import io.quarkus.cache.CacheResult;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import wowdb.io.entity.*;
import wowdb.io.exceptions.CustomException;
import wowdb.io.pojo.requests.AccountCreateRq;
import wowdb.io.pojo.requests.AccountVerifyRq;
import wowdb.io.utils.RUtil;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static wowdb.io.utils.SRP6Util.*;

@ApplicationScoped
public class AuthService {

    public Uni<Response> verifyAccount(AccountVerifyRq accountVerifyRq) {
        Account account = Account.cacheableFindByUsername(accountVerifyRq.getAccount_name().toUpperCase());
        if (account == null) {
            return RUtil.accountDoesNotExist(accountVerifyRq.getAccount_name());
        }

        try {
            return verifySRP6(accountVerifyRq.getAccount_name(),
                    accountVerifyRq.getAccount_password(),
                    account.getSalt(),
                    account.getVerifier()) ? RUtil.success() : RUtil.loginOrPasswordIncorrect();
        } catch (NoSuchAlgorithmException e) {
            throw new CustomException(e);
        }
    }

    public Uni<Response> getAccount(String userName) {
        Account account = Account.cacheableFindByUsername(userName.toUpperCase());
        if (account == null) {
            return RUtil.accountDoesNotExist(userName);
        }
        return RUtil.success(account);
    }

    public Uni<Response> getAccountAccess(String userName) {
        Account account = Account.cacheableFindByUsername(userName.toUpperCase());
        if (account == null) {
            return RUtil.accountDoesNotExist(userName);
        }
        if (account.accountAccess == null) {
            return RUtil.accountAccessDoesNotExist(userName);
        }
        return RUtil.success(account.accountAccess);
    }

    public Uni<Response> createAccount(AccountCreateRq accountCreateRq) {
        // first step - try to get acc from Cache
        Account account = Account.cacheableFindByUsername(accountCreateRq.getAccount_name().toUpperCase());

        // second step - try to get acc without Cache
        if (account == null) {
            account = Account.unCacheableFindByUsername(accountCreateRq.getAccount_name().toUpperCase());
        }

        if (account != null) {
            return RUtil.accountAlreadyExist(accountCreateRq.getAccount_name());
        }
        account = Account.cacheableFindByEmail(accountCreateRq.getAccount_email());
        if (account != null) {
            return RUtil.accountEmailAlreadyInUse(accountCreateRq.getAccount_email());
        }

        try {
            addAccount(accountCreateRq);
        } catch (NoSuchAlgorithmException e) {
            throw new CustomException(e);
        }
        return RUtil.success();
    }

    public Uni<Response> updateCache(AccountVerifyRq accountVerifyRq) {
        Account account = Account.unCacheableFindByUsername(accountVerifyRq.getAccount_name().toUpperCase());
        if (account == null) {
            return RUtil.accountDoesNotExist(accountVerifyRq.getAccount_name());
        }
        return RUtil.success(account);
    }

    @CacheResult(cacheName = "getRealmLists")
    public Uni<Response> getRealmLists() {
        List<RealmList> realms = RealmList.listAll();
        return RUtil.success(realms);
    }

    @CacheResult(cacheName = "getAccountBannedList")
    public Uni<Response> getAccountBannedList() {
        List<AccountBanned> accountBannedList = AccountBanned.listAll();
        return RUtil.success(accountBannedList);
    }

    @CacheResult(cacheName = "getAccountMutedList")
    public Uni<Response> getAccountMutedList() {
        List<AccountMuted> accountMutedList = AccountMuted.listAll();
        return RUtil.success(accountMutedList);
    }

    @CacheResult(cacheName = "getAutoBroadCastList")
    public Uni<Response> getAutoBroadCastList() {
        List<AutoBroadCast> autoBroadCastList = AutoBroadCast.listAll();
        return RUtil.success(autoBroadCastList);
    }

    @CacheResult(cacheName = "getBuildAuthKeyList")
    public Uni<Response> getBuildAuthKeyList() {
        List<BuildAuthKey> buildAuthKeyList = BuildAuthKey.listAll();
        return RUtil.success(buildAuthKeyList);
    }

    public Uni<Response> getBuildAuthKeyByBuild(Integer build) {
        List<BuildAuthKey> buildAuthKey = BuildAuthKey.cacheableFindByBuild(build);
        if (buildAuthKey.isEmpty()) {
            return RUtil.buildAuthKeyDoesNotExist(build);
        }
        return RUtil.success(buildAuthKey);
    }

    @CacheResult(cacheName = "getBuildExecutableHashList")
    public Uni<Response> getBuildExecutableHashList() {
        List<BuildExecutableHash> buildExecutableHashList = BuildExecutableHash.listAll();
        return RUtil.success(buildExecutableHashList);
    }

    public Uni<Response> getBuildExecutableHashByBuild(Integer build) {
        List<BuildExecutableHash> buildExecutableHash = BuildExecutableHash.cacheableFindByBuild(build);
        if (buildExecutableHash.isEmpty()) {
            return RUtil.buildExecutableHashKeyDoesNotExist(build);
        }
        return RUtil.success(buildExecutableHash);
    }

    @CacheResult(cacheName = "getBuildInfoList")
    public Uni<Response> getBuildInfoList() {
        List<BuildInfo> buildInfoList = BuildInfo.listAll();
        return RUtil.success(buildInfoList);
    }

    public Uni<Response> getBuildInfoByBuild(Integer build) {
        BuildInfo buildInfo = BuildInfo.cacheableFindByBuild(build);
        if (buildInfo == null) {
            return RUtil.buildInfoDoesNotExist(build);
        }
        return RUtil.success(buildInfo);
    }

    @CacheResult(cacheName = "getIpBannedList")
    public Uni<Response> getIpBannedList() {
        List<IpBanned> ipBannedList = IpBanned.listAll();
        return RUtil.success(ipBannedList);
    }

    public Uni<Response> getIpBannedByIp(String ip) {
        IpBanned ipBanned = IpBanned.cacheableFindByIp(ip);
        if (ipBanned == null) {
            return RUtil.ipBannedDoesNotExist(ip);
        }
        return RUtil.success(ipBanned);
    }

    @CacheResult(cacheName = "getRbacPermissionList")
    public Uni<Response> getRbacPermissionList() {
        List<RbacPermission> rbacPermissionList = RbacPermission.listAll();
        return RUtil.success(rbacPermissionList);
    }

    public Uni<Response> getRbacPermissionById(Long id) {
        RbacPermission rbacPermission = RbacPermission.cacheableFindByID(id);
        if (rbacPermission == null) {
            return RUtil.rbacPermissionByIdDoesNotExist(id);
        }
        return RUtil.success(rbacPermission);
    }

    public Uni<Response> getRbacPermissionByName(String name) {
        RbacPermission rbacPermission = RbacPermission.cacheableFindByName(name);
        if (rbacPermission == null) {
            return RUtil.rbacPermissionByNameDoesNotExist(name);
        }
        return RUtil.success(rbacPermission);
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
    protected void addAccount(AccountCreateRq accountCreateRq) throws NoSuchAlgorithmException {
        byte[] salt = generateRandomSalt(32);
        byte[] verifier = calculateSRP6TCVerifier(accountCreateRq.getAccount_name(),
                accountCreateRq.getAccount_password(),
                salt);

        Account account = getAccount(accountCreateRq, salt, verifier);

        List<RealmList> realmLists = RealmList.listAll();
        for (RealmList it : realmLists) {
            RealmCharacter realmCharacter = new RealmCharacter();
            realmCharacter.setRealmid(it.getId());
            realmCharacter.setNumchars(0);
            realmCharacter.account = account;
            realmCharacter.persist();
        }
    }

    @Transactional(value = Transactional.TxType.MANDATORY, rollbackOn = Exception.class)
    protected static Account getAccount(AccountCreateRq accountCreateRq, byte[] salt, byte[] verifier) {
        Account account = new Account();
        account.setUsername(accountCreateRq.getAccount_name().toUpperCase());
        account.setSalt(salt);
        account.setVerifier(verifier);
        account.setEmail(accountCreateRq.getAccount_email());
        account.setRegMail(accountCreateRq.getAccount_email());
        account.setJoinDate(Timestamp.from(Instant.now()));
        account.setLastIp("127.0.0.1");
        account.setLastAttemptIp("127.0.0.1");
        account.setFailedLogins(0);
        account.setLocked(0);
        account.setLockCountry("00");
        account.setOnline(0);
        account.setExpansion(2);
        account.setMuteTime(0L);
        account.setMuteReason("");
        account.setMuteBy("");
        account.setLocale(0);
        account.setOs("");
        account.setRecruiter(0);
        account.persist();
        return account;
    }
}

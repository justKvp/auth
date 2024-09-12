package wowdb.io.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import wowdb.io.entity.*;
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

    public Response verifyAccount(AccountVerifyRq accountVerifyRq) {
        Account account = Account.findByUsername(accountVerifyRq.getAccount_name().toUpperCase());
        if (account == null) {
            return RUtil.accountDoesNotExist(accountVerifyRq.getAccount_name());
        }

        try {
            return verifySRP6(accountVerifyRq.getAccount_name(),
                    accountVerifyRq.getAccount_password(),
                    account.getSalt(),
                    account.getVerifier()) ? RUtil.success() : RUtil.loginOrPasswordIncorrect();
        } catch (NoSuchAlgorithmException e) {
            return RUtil.serverError(e.getMessage());
        }
    }

    public Response getAccount(String userName) {
        Account account = Account.findByUsername(userName.toUpperCase());
        if (account == null) {
            return RUtil.accountDoesNotExist(userName);
        }
        return RUtil.success(account);
    }

    public Response getAccountAccess(String userName) {
        Account account = Account.findByUsername(userName.toUpperCase());
        if (account == null) {
            return RUtil.accountDoesNotExist(userName);
        }
        if (account.accountAccess == null) {
            return RUtil.accountAccessDoesNotExist(userName);
        }
        return RUtil.success(account.accountAccess);
    }

    public Response createAccount(AccountCreateRq accountCreateRq) {
        Account account = Account.findByUsername(accountCreateRq.getAccount_name().toUpperCase());
        if (account != null) {
            return RUtil.accountAlreadyExist(accountCreateRq.getAccount_name());
        }
        try {
            addAccount(accountCreateRq);
        } catch (NoSuchAlgorithmException e) {
            return RUtil.serverError(e.getMessage());
        }
        return RUtil.success();
    }

    public Response getRealmLists() {
        List<RealmList> realms = RealmList.listAll();
        return RUtil.success(realms);
    }

    public Response getAccountBannedList() {
        List<AccountBanned> accountBannedList = AccountBanned.listAll();
        return RUtil.success(accountBannedList);
    }

    public Response getAccountMutedList() {
        List<AccountMuted> accountMutedList = AccountMuted.listAll();
        return RUtil.success(accountMutedList);
    }

    public Response getAutoBroadCastList() {
        List<AutoBroadCast> autoBroadCastList = AutoBroadCast.listAll();
        return RUtil.success(autoBroadCastList);
    }

    public Response getBuildInfoList() {
        List<BuildInfo> buildInfoList = BuildInfo.listAll();
        return RUtil.success(buildInfoList);
    }

    public Response getBuildInfoByBuild(Integer build) {
        BuildInfo buildInfo = BuildInfo.findByBuild(build);
        if (buildInfo == null) {
            return RUtil.buildInfoDoesNotExist(build);
        }
        return RUtil.success(buildInfo);
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

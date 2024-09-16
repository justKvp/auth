package wowdb.io.utils;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.core.Response;
import wowdb.io.pojo.responses.CommonMsg;

import static wowdb.io.constants.ResponseInternalCodes.*;
import static wowdb.io.constants.ResponseStrings.*;

public class RUtil {
    public static Uni<Response> success() {
        return success(new CommonMsg(CODE_SUCCESS, SUCCESS_PROCEED));
    }

    public static Uni<Response> success(Object obj) {
        return Uni.createFrom().item(Response.status(Response.Status.OK)
                .entity(obj)
                .build());
    }

    public static Uni<Response> authorizedFailed(Integer code, String msg) {
        return Uni.createFrom().item(Response.status(Response.Status.UNAUTHORIZED)
                .entity(new CommonMsg(code, msg))
                .build());
    }

    public static Uni<Response> preconditionFailed(Integer code, String msg) {
        return Uni.createFrom().item(Response.status(Response.Status.PRECONDITION_FAILED)
                .entity(new CommonMsg(code, msg))
                .build());
    }

    public static Uni<Response> expectationFailed(Integer code, String msg) {
        return Uni.createFrom().item(Response.status(Response.Status.EXPECTATION_FAILED)
                .entity(new CommonMsg(code, msg))
                .build());
    }

    public static Response serverError(String msg) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new CommonMsg(CODE_ERROR_UNEXPECTED, msg))
                .build();
    }

    public static Uni<Response> accountDoesNotExist(String username) {
        return expectationFailed(CODE_ACCOUNT_DOES_NOT_EXIST, String.format(ACCOUNT_DOES_NOT_EXIST, username));
    }

    public static Uni<Response> accountAlreadyExist(String username) {
        return expectationFailed(CODE_ACCOUNT_CREATE_LOGIN_ALREADY_EXIST, String.format(ACCOUNT_LOGIN_ALREADY_EXIST, username));
    }

    public static Uni<Response> accountEmailAlreadyInUse(String username) {
        return expectationFailed(CODE_ACCOUNT_CREATE_EMAIL_ALREADY_IN_USE, String.format(ACCOUNT_EMAIL_ALREADY_IN_USE, username));
    }

    public static Uni<Response> accountAccessDoesNotExist(String username) {
        return expectationFailed(CODE_ACCOUNT_ACCESS_DOES_NOT_EXIST, String.format(ACCOUNT_ACCESS_DOES_NOT_EXIST, username));
    }

    public static Uni<Response> loginOrPasswordIncorrect() {
        return preconditionFailed(CODE_LOGIN_OR_PASSWORD_INCORRECT, LOGIN_OR_PASSWORD_INCORRECT);
    }

    public static Uni<Response> buildInfoDoesNotExist(Integer build) {
        return expectationFailed(CODE_BUILD_INFO_DOES_NOT_EXIST, String.format(BUILD_INFO_DOES_NOT_EXIST, build));
    }

    public static Uni<Response> buildAuthKeyDoesNotExist(Integer build) {
        return expectationFailed(CODE_BUILD_AUTH_KEY_DOES_NOT_EXIST, String.format(BUILD_AUTH_KEY_DOES_NOT_EXIST, build));
    }

    public static Uni<Response> buildExecutableHashKeyDoesNotExist(Integer build) {
        return expectationFailed(CODE_BUILD_EXECUTABLE_HASH_DOES_NOT_EXIST, String.format(BUILD_EXECUTABLE_HASH_DOES_NOT_EXIST, build));
    }

    public static Uni<Response> ipBannedDoesNotExist(String ip) {
        return expectationFailed(CODE_IP_BANNED_DOES_NOT_EXIST, String.format(BUILD_P_BANNED_DOES_NOT_EXIST, ip));
    }
}

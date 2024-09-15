package wowdb.io.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import wowdb.io.pojo.requests.AccountCreateRq;
import wowdb.io.pojo.requests.AccountVerifyRq;
import wowdb.io.services.AuthService;

@Path("/auth")
public class AuthResource {
    @Inject
    AuthService authService;

    @GET
    @Path("/healthCheck")
    public String healthCheck() {
        return "online";
    }

    @GET
    @Path("/getAccount/{userName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccount(@PathParam(value = "userName") String userName) {
        return authService.getAccount(userName);
    }

    @GET
    @Path("/getAccountAccess/{userName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountAccess(@PathParam(value = "userName") String userName) {
        return authService.getAccountAccess(userName);
    }

    @GET
    @Path("/realms")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRealmLists() {
        return authService.getRealmLists();
    }

    @GET
    @Path("/getAccountBanned")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountBannedList() {
        return authService.getAccountBannedList();
    }

    @GET
    @Path("/getAccountMuted")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountMutedList() {
        return authService.getAccountMutedList();
    }

    @GET
    @Path("/getAutoBroadCast")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAutoBroadCastList() {
        return authService.getAutoBroadCastList();
    }

    @GET
    @Path("/getBuildAuthKey/{build}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBuildAuthKeyByBuild(@PathParam(value = "build") Integer build) {
        return authService.getBuildAuthKeyByBuild(build);
    }

    @GET
    @Path("/getBuildAuthKeyList")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBuildAuthKeyList() {
        return authService.getBuildAuthKeyList();
    }

    @GET
    @Path("/getBuildExecutableHash/{build}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBuildExecutableHashByBuild(@PathParam(value = "build") Integer build) {
        return authService.getBuildExecutableHashByBuild(build);
    }

    @GET
    @Path("/getBuildExecutableHashList")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBuildExecutableHashList() {
        return authService.getBuildExecutableHashList();
    }

    @GET
    @Path("/getBuildInfo/{build}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBuildInfoByBuild(@PathParam(value = "build") Integer build) {
        return authService.getBuildInfoByBuild(build);
    }

    @GET
    @Path("/getBuildInfoList")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBuildInfoList() {
        return authService.getBuildInfoList();
    }

    @GET
    @Path("/getIpBanned/{ip}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBuildInfoByBuild(@PathParam(value = "ip") String ip) {
        return authService.getIpBannedByIp(ip);
    }

    @GET
    @Path("/getIpBannedList")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIpBannedList() {
        return authService.getIpBannedList();
    }

    @POST
    @Path("/verifyAccount")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response verifyAccount(@Valid AccountVerifyRq accountVerifyRq) {
        return authService.verifyAccount(accountVerifyRq);
    }

    @POST
    @Path("/createAccount")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAccount(@Valid AccountCreateRq accountCreateRq) {
        return authService.createAccount(accountCreateRq);
    }
}

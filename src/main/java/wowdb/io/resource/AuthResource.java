package wowdb.io.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import wowdb.io.pojo.requests.AccountVerifyRq;
import wowdb.io.pojo.requests.AccountCreateRq;
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

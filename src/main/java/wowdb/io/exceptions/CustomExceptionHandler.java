package wowdb.io.exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import wowdb.io.pojo.responses.CommonMsg;

import static wowdb.io.constants.ResponseInternalCodes.CODE_ERROR_UNEXPECTED;

@Provider
public class CustomExceptionHandler implements ExceptionMapper<CustomException> {
    @Override
    public Response toResponse(CustomException e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new CommonMsg(CODE_ERROR_UNEXPECTED, e.getMessage()))
                .build();
    }
}

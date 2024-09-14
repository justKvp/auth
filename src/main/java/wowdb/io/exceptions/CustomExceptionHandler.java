package wowdb.io.exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import wowdb.io.utils.RUtil;

@Provider
public class CustomExceptionHandler implements ExceptionMapper<CustomException> {
    @Override
    public Response toResponse(CustomException e) {
        return RUtil.serverError(e.getMessage());
    }
}

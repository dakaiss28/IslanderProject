package game.resource;

import org.junit.jupiter.api.Test;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class TestMyExceptionMapper {

    @Test
    void testMyExceptionMapper()  {
        MyExceptionMapper mapperException = new MyExceptionMapper();
        Exception exception = new WebApplicationException();
        assertEquals(((WebApplicationException)exception).getResponse(),mapperException.toResponse(exception));
        Exception exceptionQuelconque = mock(Exception.class);
        assertEquals(Response.status(500).build().getStatus(),mapperException.toResponse(exceptionQuelconque).getStatus());

    }

}

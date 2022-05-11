package ch.bzz.cpu_retailer.service;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("test")
public class TestService {

    @GET
    @Path("test")
    @Produces(MediaType.TEXT_PLAIN)
    public Response test() {

        return Response
                .status(200)
                .entity("Test erfolgreich")
                .build();
    }
}

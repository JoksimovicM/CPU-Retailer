package ch.bzz.cpu_retailer.service;

import ch.bzz.cpu_retailer.data.UserData;
import ch.bzz.cpu_retailer.model.User;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("user")
public class UserService {

    @POST
    @Path("login")
    @Produces(MediaType.TEXT_PLAIN)
    public Response login (
            @FormParam("username") String username,
            @FormParam("password") String password
    ) {
        int httpstatus;

        User user = UserData.findUser(username, password);
        if (user == null || user.getRole() == null || user.getRole().equals("guest")) {
            httpstatus = 404;
        } else {
            httpstatus = 200;
        }
        Response response = Response
                .status(httpstatus)
                .entity("")
                .build();
        return response;
    }
}

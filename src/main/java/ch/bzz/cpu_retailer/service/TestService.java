package ch.bzz.cpu_retailer.service;

import ch.bzz.cpu_retailer.data.DataHandler;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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

    @GET
    @Path("restore")
    @Produces(MediaType.TEXT_PLAIN)
    public Response wiederherstellen() {
        try {
            java.nio.file.Path path = Paths.get(Config.getProperty("cpuJSON"));
            String filename = path.getFileName().toString();
            String folder = path.getParent().toString();

            byte[] bookJSON = Files.readAllBytes(Paths.get(folder, "backup", filename));
            FileOutputStream fileOutputStream = new FileOutputStream(Config.getProperty("cpuJSON"));
            fileOutputStream.write(bookJSON);

            path = Paths.get(Config.getProperty("reiheJSON"));
            filename = path.getFileName().toString();
            folder = path.getParent().toString();

            byte[] publisherJSON = Files.readAllBytes(Paths.get(folder, "backup", filename));
            fileOutputStream = new FileOutputStream(Config.getProperty("reiheJSON"));
            fileOutputStream.write(publisherJSON);
        } catch (IOException e) {
            e.printStackTrace();
        }

        DataHandler.initListen();
        return Response
                .status(200)
                .entity("Erfolgreich")
                .build();
    }
}

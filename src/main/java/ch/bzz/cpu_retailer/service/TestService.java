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
                .entity("Test successful")
                .build();
    }

    @GET
    @Path("restore")
    @Produces(MediaType.TEXT_PLAIN)
    public Response restoreData() {
        try {
            java.nio.file.Path path = Paths.get(Config.getProperty("cpuJSON"));
            String filename = path.getFileName().toString();
            String folder = path.getParent().toString();

            byte[] cpuJSON = Files.readAllBytes(Paths.get(folder, "backup", filename));
            FileOutputStream fileOutputStream = new FileOutputStream(Config.getProperty("cpuJSON"));
            fileOutputStream.write(cpuJSON);

            path = Paths.get(Config.getProperty("seriesJSON"));
            filename = path.getFileName().toString();
            folder = path.getParent().toString();

            byte[] reiheJSON = Files.readAllBytes(Paths.get(folder, "backup", filename));
            fileOutputStream = new FileOutputStream(Config.getProperty("seriesJSON"));
            fileOutputStream.write(reiheJSON);

            path = Paths.get(Config.getProperty("manufacturerJSON"));
            filename = path.getFileName().toString();
            folder = path.getParent().toString();

            byte[] herstellerJSON = Files.readAllBytes(Paths.get(folder, "backup", filename));
            fileOutputStream = new FileOutputStream(Config.getProperty("manufacturerJSON"));
            fileOutputStream.write(herstellerJSON);
        } catch (IOException e) {
            e.printStackTrace();
        }

        DataHandler.initLists();
        return Response
                .status(200)
                .entity("Successful")
                .build();
    }
}

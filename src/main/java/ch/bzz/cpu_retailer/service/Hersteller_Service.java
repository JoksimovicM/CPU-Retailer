package ch.bzz.cpu_retailer.service;

import ch.bzz.cpu_retailer.data.DataHandler;
import ch.bzz.cpu_retailer.model.Hersteller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("hersteller")
public class Hersteller_Service {

    /*
    * Service der alle verfügbaren Hersteller auflistet
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listeAlleHersteller() {
        List<Hersteller> herstellerListe = DataHandler.leseAlleHersteller();
        return Response
                .status(200)
                .entity(herstellerListe)
                .build();
    }

    /*
    * Service der Hersteller mit der mitgegebenen UUID sucht
    * @param herstellerUUID HerstellerUUID mit der gesucht wird
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response leseHersteller(
            @QueryParam("uuid") String herstellerUUID
    ) {
        int httpstatus;
        Hersteller hersteller = DataHandler.leseHerstellerMitUUID(herstellerUUID);
        if (hersteller != null && herstellerUUID.matches("[a-zA-Z0-9]{8}(-[a-zA-Z0-9]{4}){3}-[a-zA-Z0-9]{12}")) {
            httpstatus = 200;
        } else if (!herstellerUUID.matches("[a-zA-Z0-9]{8}(-[a-zA-Z0-9]{4}){3}-[a-zA-Z0-9]{12}")){
            httpstatus = 410;
        } else {
            httpstatus = 404;
        }
        return Response
                .status(httpstatus)
                .entity(hersteller)
                .build();
    }
}

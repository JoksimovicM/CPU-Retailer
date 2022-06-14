package ch.bzz.cpu_retailer.service;

import ch.bzz.cpu_retailer.data.DataHandler;
import ch.bzz.cpu_retailer.model.CPU;
import ch.bzz.cpu_retailer.model.CPU_Reihe;
import ch.bzz.cpu_retailer.model.Hersteller;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

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

    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response erstelleHersteller (
            @Valid @BeanParam Hersteller hersteller
    ) {
        hersteller.setHerstellerUUID(UUID.randomUUID().toString());
        hersteller.setHerstellerName(hersteller.getHerstellerName());

        DataHandler.herstellerHinzu(hersteller);

        Response response = Response
                .status(200)
                .entity("")
                .build();

        return response;
    }

    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response aktualisiereHersteller(
            @Valid @BeanParam Hersteller hersteller
    ) {
        int httpstatus;
        String msg = "";
        if (hersteller.getHerstellerUUID().matches("[a-zA-Z0-9]{8}(-[a-zA-Z0-9]{4}){3}-[a-zA-Z0-9]{12}")) {
            Hersteller alterHersteller = DataHandler.leseHerstellerMitUUID(hersteller.getHerstellerUUID());
            if (alterHersteller != null) {
                alterHersteller.setHerstellerName(hersteller.getHerstellerName());

                DataHandler.herstellerAktuell();

                httpstatus = 200;
            } else {
                httpstatus = 404;
                msg = "Hersteller mit dieser UUID existiert nicht";
            }
        } else {
            httpstatus = 400;
            msg = "Fehler: Ungueltige herstellerUUID";
        }

        Response response = Response
                .status(httpstatus)
                .entity(msg)
                .build();
        return response;
    }

    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response herstellerLoeschen(
            @QueryParam("herstellerUUID") String herstellerUUID
    ) {
        int httpstatus;
        String msg = "";
        if (herstellerUUID.matches("[a-zA-Z0-9]{8}(-[a-zA-Z0-9]{4}){3}-[a-zA-Z0-9]{12}")) {
            httpstatus = 200;
            if (!DataHandler.herstellerLoeschen(herstellerUUID)) {
                httpstatus = 410;
                msg = "Hersteller mit dieser UUID existiert nicht";
            }
        } else {
            httpstatus = 400;
            msg = "Fehler: Ungueltige herstellerUUID";
        }

        return Response
                .status(httpstatus)
                .entity(msg)
                .build();
    }
}

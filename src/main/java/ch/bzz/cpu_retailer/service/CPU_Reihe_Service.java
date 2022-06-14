package ch.bzz.cpu_retailer.service;

import ch.bzz.cpu_retailer.data.DataHandler;
import ch.bzz.cpu_retailer.model.CPU;
import ch.bzz.cpu_retailer.model.CPU_Reihe;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Path("reihe")
public class CPU_Reihe_Service {

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listeAlleReihen() {
        List<CPU_Reihe> reiheListe = DataHandler.leseAlleReihen();
        return Response
                .status(200)
                .entity(reiheListe)
                .build();
    }

    /**
     * reads a cpu identified by the uuid
     * @param reiheUUID ReiheUUID nach der gesucht wird
     * @return reihe
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response leseReihe(
            @QueryParam("uuid") String reiheUUID
    ) {
        int httpstatus;
        CPU_Reihe reihe = DataHandler.leseReiheMitUUID(reiheUUID);
        if (reihe != null && reiheUUID.matches("[a-zA-Z0-9]{8}(-[a-zA-Z0-9]{4}){3}-[a-zA-Z0-9]{12}")) {
            httpstatus = 200;
        } else if (!reiheUUID.matches("[a-zA-Z0-9]{8}(-[a-zA-Z0-9]{4}){3}-[a-zA-Z0-9]{12}")){
            httpstatus = 410;
        } else {
            httpstatus = 404;
        }
        return Response
                .status(httpstatus)
                .entity(reihe)
                .build();
    }

    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response erstelleReihe (
            @FormParam("nameReihe") String nameReihe,
            @FormParam("beschreibung") String beschreibung,
            @FormParam("herstellerUUID") String herstellerUUID
    ) {
        int httpstatus;
        CPU_Reihe reihe = new CPU_Reihe();
        reihe.setReiheUUID(UUID.randomUUID().toString());
        if (herstellerUUID.matches("[a-zA-Z0-9]{8}(-[a-zA-Z0-9]{4}){3}-[a-zA-Z0-9]{12}")) {
            reihe.setNameReihe(nameReihe);
            reihe.setBeschreibung(beschreibung);
            reihe.setHerstellerUUID(herstellerUUID);

            httpstatus = 200;

            DataHandler.reiheHinzu(reihe);
        } else {
            httpstatus = 400;
        }

        Response response = Response
                .status(httpstatus)
                .entity("")
                .build();

        return response;
    }

    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response aktualisiereReihe(
            @FormParam("reiheUUID") String reiheUUID,
            @FormParam("nameReihe") String nameReihe,
            @FormParam("beschreibung") String beschreibung,
            @FormParam("herstellerUUID") String herstellerUUID
    ) {
        int httpstatus;
        if (reiheUUID.matches("[a-zA-Z0-9]{8}(-[a-zA-Z0-9]{4}){3}-[a-zA-Z0-9]{12}")) {
            CPU_Reihe reihe = DataHandler.leseReiheMitUUID(reiheUUID);
            if (reihe != null) {
                reihe.setNameReihe(nameReihe);
                reihe.setBeschreibung(beschreibung);
                reihe.setHerstellerUUID(herstellerUUID);
                DataHandler.reiheAktuell();
                httpstatus = 200;
            } else {
                httpstatus = 404;
            }
        } else {
            httpstatus = 400;
        }


        Response response = Response
                .status(httpstatus)
                .entity("")
                .build();
        return response;
    }

    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response reiheLoeschen(
            @QueryParam("reiheUUID") String reiheUUID
    ) {
        int httpstatus;
        if (reiheUUID.matches("[a-zA-Z0-9]{8}(-[a-zA-Z0-9]{4}){3}-[a-zA-Z0-9]{12}")) {
            httpstatus = 200;
            if (!DataHandler.reiheLoeschen(reiheUUID)) {
                httpstatus = 410;
            }
        } else {
            httpstatus = 400;
        }

        return Response
                .status(httpstatus)
                .entity("")
                .build();
    }
}

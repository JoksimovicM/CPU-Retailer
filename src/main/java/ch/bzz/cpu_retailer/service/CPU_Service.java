package ch.bzz.cpu_retailer.service;


import ch.bzz.cpu_retailer.data.DataHandler;
import ch.bzz.cpu_retailer.model.CPU;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Path("cpu")
public class CPU_Service {

    /*
    * Listet alle verfügbaren CPUs
    * @return cpuListe Liste der gefundenen CPUs
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listeAlleCPUs() {
        List<CPU> cpuListe = DataHandler.leseAlleCPUs();
        return Response
                .status(200)
                .entity(cpuListe)
                .build();
    }

    /**
     * Sucht eine CPU mit der mitgegebenen UUID
     * @param cpuUUID CPU-UUID mit der gesucht wird
     * @return cpu
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response leseCPU(
            @QueryParam("uuid") String cpuUUID
    ) {
        int httpstatus;
        CPU cpu = DataHandler.leseCPUMitUUID(cpuUUID);
        if (cpu != null && cpuUUID.matches("[a-zA-Z0-9]{8}(-[a-zA-Z0-9]{4}){3}-[a-zA-Z0-9]{12}")) {
            httpstatus = 200;
        } else if (!cpuUUID.matches("[a-zA-Z0-9]{8}(-[a-zA-Z0-9]{4}){3}-[a-zA-Z0-9]{12}")){
            httpstatus = 410;
        } else {
            httpstatus = 404;
        }
        return Response
                .status(httpstatus)
                .entity(cpu)
                .build();
    }

    /*
    * Erstellt eine CPU mit den erhaltenen Attributen
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response erstelleCPU (
            @FormParam("name") String name,
            @FormParam("anzahlKerne") Integer anzahlKerne,
            @FormParam("stromverbrauch") Integer stromverbrauch,
            @FormParam("taktfrequenz") Double taktfrequenz,
            @FormParam("sockel") String sockel,
            @FormParam("preis") BigDecimal preis,
            @FormParam("reiheUUID") String reiheUUID
            ) {
        int httpstatus;
        CPU cpu = new CPU();
        cpu.setCpuUUID(UUID.randomUUID().toString());
        if (reiheUUID.matches("[a-zA-Z0-9]{8}(-[a-zA-Z0-9]{4}){3}-[a-zA-Z0-9]{12}")) {
            cpu.setName(name);
            cpu.setAnzahlKerne(anzahlKerne);
            cpu.setStromverbrauch(stromverbrauch);
            cpu.setTaktfrequenz(taktfrequenz);
            cpu.setSockel(sockel);
            cpu.setPreis(preis);
            cpu.setReiheUUID(reiheUUID);

            httpstatus = 200;

            DataHandler.cpuHinzu(cpu);
        } else {
            httpstatus = 400;
        }

        Response response = Response
                .status(httpstatus)
                .entity("")
                .build();

        return response;
    }

    /*
    * Service zum updaten einer CPU
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response aktualisiereCPU(
            @FormParam("cpuUUID") String cpuUUID,
            @FormParam("name") String name,
            @FormParam("anzahlKerne") Integer anzahlKerne,
            @FormParam("stromverbrauch") Integer stromverbrauch,
            @FormParam("taktfrequenz") Double taktfrequenz,
            @FormParam("sockel") String sockel,
            @FormParam("preis") BigDecimal preis,
            @FormParam("reiheUUID") String reiheUUID
    ) {
        int httpstatus;
        if (cpuUUID.matches("[a-zA-Z0-9]{8}(-[a-zA-Z0-9]{4}){3}-[a-zA-Z0-9]{12}")) {
            CPU cpu = DataHandler.leseCPUMitUUID(cpuUUID);
            if (cpu != null) {
                cpu.setName(name);
                cpu.setAnzahlKerne(anzahlKerne);
                cpu.setStromverbrauch(stromverbrauch);
                cpu.setTaktfrequenz(taktfrequenz);
                cpu.setSockel(sockel);
                cpu.setPreis(preis);
                cpu.setReiheUUID(reiheUUID);
                DataHandler.cpuAktuell();
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
    public Response cpuLoeschen(
            @QueryParam("cpuUUID") String cpuUUID
    ) {
        int httpstatus;
        if (cpuUUID.matches("[a-zA-Z0-9]{8}(-[a-zA-Z0-9]{4}){3}-[a-zA-Z0-9]{12}")) {
            httpstatus = 200;
            if (!DataHandler.cpuLoeschen(cpuUUID)) {
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

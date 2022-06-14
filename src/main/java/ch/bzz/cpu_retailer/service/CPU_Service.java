package ch.bzz.cpu_retailer.service;


import ch.bzz.cpu_retailer.data.DataHandler;
import ch.bzz.cpu_retailer.model.CPU;

import javax.validation.Valid;
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
            @Valid @BeanParam CPU cpu,
            @FormParam("reiheUUID") String reiheUUID
            ) {
        int httpstatus;
        String msg = "";
        cpu.setCpuUUID(UUID.randomUUID().toString());
        if (reiheUUID.matches("[a-zA-Z0-9]{8}(-[a-zA-Z0-9]{4}){3}-[a-zA-Z0-9]{12}")) {
            cpu.setReiheUUID(reiheUUID);

            httpstatus = 200;

            DataHandler.cpuHinzu(cpu);
        } else {
            httpstatus = 400;
            msg = "Fehler: Ungueltige reiheUUID";
        }

        Response response = Response
                .status(httpstatus)
                .entity(msg)
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
            @Valid @BeanParam CPU cpu,
            @FormParam("reiheUUID") String reiheUUID
    ) {
        int httpstatus;
        String msg = "";
        if (reiheUUID.matches("[a-zA-Z0-9]{8}(-[a-zA-Z0-9]{4}){3}-[a-zA-Z0-9]{12}")) {
            CPU alteCPU = DataHandler.leseCPUMitUUID(cpu.getCpuUUID());
            if (alteCPU != null) {
                alteCPU.setName(cpu.getName());
                alteCPU.setAnzahlKerne(cpu.getAnzahlKerne());
                alteCPU.setStromverbrauch(cpu.getStromverbrauch());
                alteCPU.setTaktfrequenz(cpu.getTaktfrequenz());
                alteCPU.setSockel(cpu.getSockel());
                alteCPU.setPreis(cpu.getPreis());
                alteCPU.setReiheUUID(reiheUUID);
                DataHandler.cpuAktuell();
                httpstatus = 200;
            } else {
                httpstatus = 404;
                msg = "CPU mit dieser UUID existiert nicht";
            }
        } else {
            httpstatus = 400;
            msg = "Fehler: Ungueltige reiheUUID";
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
    public Response cpuLoeschen(
            @QueryParam("cpuUUID") String cpuUUID
    ) {
        int httpstatus;
        String msg = "";
        if (cpuUUID.matches("[a-zA-Z0-9]{8}(-[a-zA-Z0-9]{4}){3}-[a-zA-Z0-9]{12}")) {
            httpstatus = 200;
            if (!DataHandler.cpuLoeschen(cpuUUID)) {
                httpstatus = 410;
                msg = "CPU mit dieser UUID existiert nicht";
            }
        } else {
            httpstatus = 400;
            msg = "CPU-UUID formal falsch";
        }

        return Response
                .status(httpstatus)
                .entity(msg)
                .build();
    }
}

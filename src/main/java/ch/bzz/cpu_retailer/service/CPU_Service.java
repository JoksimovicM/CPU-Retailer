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
    public Response listAllCPUs() {
        List<CPU> cpuList = DataHandler.readCPUs();
        return Response
                .status(200)
                .entity(cpuList)
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
    public Response readCPU(
            @QueryParam("uuid") String cpuUUID
    ) {
        int httpstatus;
        CPU cpu = DataHandler.readCPUbyUUID(cpuUUID);
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
    public Response createCPU (
            @Valid @BeanParam CPU cpu,
            @FormParam("seriesUUID") String seriesUUID
            ) {
        int httpstatus;
        String msg = "";
        cpu.setCpuUUID(UUID.randomUUID().toString());
        if (seriesUUID.matches("[a-zA-Z0-9]{8}(-[a-zA-Z0-9]{4}){3}-[a-zA-Z0-9]{12}")) {
            cpu.setSeriesUUID(seriesUUID);

            httpstatus = 200;

            DataHandler.addCPU(cpu);
        } else {
            httpstatus = 400;
            msg = "Error: invalid seriesUUID";
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
    public Response updateCPU(
            @Valid @BeanParam CPU cpu,
            @FormParam("seriesUUID") String seriesUUID
    ) {
        int httpstatus;
        String msg = "";
        if (seriesUUID.matches("[a-zA-Z0-9]{8}(-[a-zA-Z0-9]{4}){3}-[a-zA-Z0-9]{12}")) {
            CPU oldCPU = DataHandler.readCPUbyUUID(cpu.getCpuUUID());
            if (oldCPU != null) {
                oldCPU.setName(cpu.getName());
                oldCPU.setCoreCount(cpu.getCoreCount());
                oldCPU.setTDP(cpu.getTDP());
                oldCPU.setFrequency(cpu.getFrequency());
                oldCPU.setSocket(cpu.getSocket());
                oldCPU.setPrice(cpu.getPrice());
                oldCPU.setSeriesUUID(seriesUUID);
                DataHandler.updateCPU();
                httpstatus = 200;
            } else {
                httpstatus = 404;
                msg = "CPU with this UUID does not exist";
            }
        } else {
            httpstatus = 400;
            msg = "Error: invalid seriesUUID";
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
    public Response deleteCPU(
            @QueryParam("cpuUUID") String cpuUUID
    ) {
        int httpstatus;
        String msg = "";
        if (cpuUUID.matches("[a-zA-Z0-9]{8}(-[a-zA-Z0-9]{4}){3}-[a-zA-Z0-9]{12}")) {
            httpstatus = 200;
            if (!DataHandler.deleteCPU(cpuUUID)) {
                httpstatus = 410;
                msg = "CPU with this UUID does not exist";
            }
        } else {
            httpstatus = 400;
            msg = "CPU-UUID is invalid";
        }

        return Response
                .status(httpstatus)
                .entity(msg)
                .build();
    }
}

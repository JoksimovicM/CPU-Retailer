package ch.bzz.cpu_retailer.service;


import ch.bzz.cpu_retailer.data.DataHandler;
import ch.bzz.cpu_retailer.model.CPU;

import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("cpu")
public class CPU_Service {

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listCPUs() {
        List<CPU> cpuList = DataHandler.getInstance().readAllCpus();
        return Response
                .status(200)
                .entity(cpuList)
                .build();
    }

    /**
     * reads a cpu identified by the uuid
     * @param cpuUUID
     * @return cpu
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readCPU(
            @QueryParam("uuid") String cpuUUID
    ) {
        int httpstatus;
        CPU cpu = DataHandler.getInstance().readCPUByUUID(cpuUUID);
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
}

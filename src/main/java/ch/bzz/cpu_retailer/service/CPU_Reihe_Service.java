package ch.bzz.cpu_retailer.service;

import ch.bzz.cpu_retailer.data.DataHandler;
import ch.bzz.cpu_retailer.model.CPU;
import ch.bzz.cpu_retailer.model.CPU_Reihe;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("reihe")
public class CPU_Reihe_Service {

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listReihen() {
        List<CPU_Reihe> reiheList = DataHandler.getInstance().readAllReihen();
        return Response
                .status(200)
                .entity(reiheList)
                .build();
    }

    /**
     * reads a cpu identified by the uuid
     * @param reiheUUID
     * @return reihe
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readReihe(
            @QueryParam("uuid") String reiheUUID
    ) {
        int httpstatus;
        CPU_Reihe reihe = DataHandler.getInstance().readReiheByUUID(reiheUUID);
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
}

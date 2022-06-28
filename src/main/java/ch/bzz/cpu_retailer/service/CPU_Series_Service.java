package ch.bzz.cpu_retailer.service;

import ch.bzz.cpu_retailer.data.DataHandler;
import ch.bzz.cpu_retailer.model.CPU_Series;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Path("series")
public class CPU_Series_Service {

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllSeries() {
        List<CPU_Series> seriesList = DataHandler.readSeries();
        return Response
                .status(200)
                .entity(seriesList)
                .build();
    }

    /**
     * reads a cpu identified by the uuid
     * @param seriesUUID ReiheUUID nach der gesucht wird
     * @return reihe
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readSeries(
            @QueryParam("uuid") String seriesUUID
    ) {
        int httpstatus;
        CPU_Series series = DataHandler.readSeriesbyUUID(seriesUUID);
        if (series != null && seriesUUID.matches("[a-zA-Z0-9]{8}(-[a-zA-Z0-9]{4}){3}-[a-zA-Z0-9]{12}")) {
            httpstatus = 200;
        } else if (!seriesUUID.matches("[a-zA-Z0-9]{8}(-[a-zA-Z0-9]{4}){3}-[a-zA-Z0-9]{12}")){
            httpstatus = 410;
        } else {
            httpstatus = 404;
        }
        return Response
                .status(httpstatus)
                .entity(series)
                .build();
    }

    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createSeries (
            @Valid @BeanParam CPU_Series series,
            @FormParam("manufacturerUUID") String manufacturerUUID
    ) {
        int httpstatus;
        String msg = "";
        series.setSeriesUUID(UUID.randomUUID().toString());
        if (manufacturerUUID.matches("[a-zA-Z0-9]{8}(-[a-zA-Z0-9]{4}){3}-[a-zA-Z0-9]{12}")) {
            series.setManufacturerUUID(manufacturerUUID);

            httpstatus = 200;

            DataHandler.addSeries(series);
        } else {
            httpstatus = 400;
            msg = "Error: invalid manufacturerUUID";
        }

        Response response = Response
                .status(httpstatus)
                .entity(msg)
                .build();

        return response;
    }

    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateSeries(
            @Valid @BeanParam CPU_Series series,
            @FormParam("manufacturerUUID") String manufacturerUUID
    ) {
        int httpstatus;
        String msg = "";
        if (manufacturerUUID.matches("[a-zA-Z0-9]{8}(-[a-zA-Z0-9]{4}){3}-[a-zA-Z0-9]{12}")) {
            CPU_Series oldSeries = DataHandler.readSeriesbyUUID(series.getSeriesUUID());
            if (oldSeries != null) {
                oldSeries.setSeriesName(series.getSeriesName());
                oldSeries.setDescription(series.getDescription());
                oldSeries.setManufacturerUUID(manufacturerUUID);
                DataHandler.updateSeries();
                httpstatus = 200;
            } else {
                httpstatus = 404;
                msg = "Error: No series with this manufacturerUUID exists";
            }
        } else {
            httpstatus = 400;
            msg = "Error: invalid manufacturerUUID";
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
    public Response deleteSeries(
            @QueryParam("seriesUUID") String seriesUUID
    ) {
        int httpstatus;
        String msg = "";
        if (seriesUUID.matches("[a-zA-Z0-9]{8}(-[a-zA-Z0-9]{4}){3}-[a-zA-Z0-9]{12}")) {
            httpstatus = 200;
            if (!DataHandler.deleteSeries(seriesUUID)) {
                httpstatus = 410;
                msg = "Series with this UUID does not exist";
            }
        } else {
            httpstatus = 400;
            msg = "SeriesUUID invalid";
        }

        return Response
                .status(httpstatus)
                .entity(msg)
                .build();
    }
}

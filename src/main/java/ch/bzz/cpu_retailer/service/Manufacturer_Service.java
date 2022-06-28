package ch.bzz.cpu_retailer.service;

import ch.bzz.cpu_retailer.data.DataHandler;
import ch.bzz.cpu_retailer.model.Manufacturer;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Path("manufacturer")
public class Manufacturer_Service {

    /*
    * Service der alle verfügbaren Hersteller auflistet
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllManufacturers() {
        List<Manufacturer> manufacturerList = DataHandler.readManufacturers();
        return Response
                .status(200)
                .entity(manufacturerList)
                .build();
    }

    /*
    * Service der Hersteller mit der mitgegebenen UUID sucht
    * @param herstellerUUID HerstellerUUID mit der gesucht wird
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readManufacturer(
            @QueryParam("uuid") String manufacturerUUID
    ) {
        int httpstatus;
        Manufacturer manufacturer = DataHandler.readManufacturerbyUUID(manufacturerUUID);
        if (manufacturer != null && manufacturerUUID.matches("[a-zA-Z0-9]{8}(-[a-zA-Z0-9]{4}){3}-[a-zA-Z0-9]{12}")) {
            httpstatus = 200;
        } else if (!manufacturerUUID.matches("[a-zA-Z0-9]{8}(-[a-zA-Z0-9]{4}){3}-[a-zA-Z0-9]{12}")){
            httpstatus = 410;
        } else {
            httpstatus = 404;
        }
        return Response
                .status(httpstatus)
                .entity(manufacturer)
                .build();
    }

    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createManufacturer(
            @Valid @BeanParam Manufacturer manufacturer
    ) {
        manufacturer.setManufacturerUUID(UUID.randomUUID().toString());
        manufacturer.setManufacturerName(manufacturer.getManufacturerName());

        DataHandler.addManufacturer(manufacturer);

        Response response = Response
                .status(200)
                .entity("")
                .build();

        return response;
    }

    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateManufacturer(
            @Valid @BeanParam Manufacturer manufacturer
    ) {
        int httpstatus;
        String msg = "";
        if (manufacturer.getManufacturerUUID().matches("[a-zA-Z0-9]{8}(-[a-zA-Z0-9]{4}){3}-[a-zA-Z0-9]{12}")) {
            Manufacturer oldManufacturer = DataHandler.readManufacturerbyUUID(manufacturer.getManufacturerUUID());
            if (oldManufacturer != null) {
                oldManufacturer.setManufacturerName(manufacturer.getManufacturerName());

                DataHandler.updateManufacturer();

                httpstatus = 200;
            } else {
                httpstatus = 404;
                msg = "Manufacturer with this UUID does not exist";
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
    public Response deleteManufacturer(
            @QueryParam("manufacturerUUID") String manufacturerUUID
    ) {
        int httpstatus;
        String msg = "";
        if (manufacturerUUID.matches("[a-zA-Z0-9]{8}(-[a-zA-Z0-9]{4}){3}-[a-zA-Z0-9]{12}")) {
            httpstatus = 200;
            if (!DataHandler.deleteManufacturer(manufacturerUUID)) {
                httpstatus = 410;
                msg = "Manufacturer with this UUID does not exist";
            }
        } else {
            httpstatus = 400;
            msg = "Error: invalid manufacturerUUID";
        }

        return Response
                .status(httpstatus)
                .entity(msg)
                .build();
    }
}

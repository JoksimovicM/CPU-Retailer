package ch.bzz.cpu_retailer.model;

import ch.bzz.cpu_retailer.data.DataHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;

public class CPU_Series {
    @JsonIgnore
    private Manufacturer manufacturer;

    @FormParam("seriesUUID")
    @Pattern(regexp = "[a-zA-Z0-9]{8}(-[a-zA-Z0-9]{4}){3}-[a-zA-Z0-9]{12}")
    private String seriesUUID;

    @FormParam("seriesName")
    @NotEmpty
    @Size(min = 2, max = 15)
    private String seriesName;

    @FormParam("description")
    @NotEmpty
    @Size(min = 2, max = 30)
    private String description;

    public String getManufacturerUUID() {
        if (getManufacturer() == null) return null;
        return getManufacturer().getManufacturerUUID();
    }

    public void setManufacturerUUID(String manufacturerUUID) {
        setManufacturer(new Manufacturer());
        Manufacturer manufacturer = DataHandler.readManufacturerbyUUID(manufacturerUUID);
        getManufacturer().setManufacturerUUID(manufacturerUUID);
        getManufacturer().setManufacturerName(manufacturer.getManufacturerName());
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getSeriesUUID() {
        return seriesUUID;
    }

    public void setSeriesUUID(String seriesUUID) {
        this.seriesUUID = seriesUUID;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

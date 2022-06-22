package ch.bzz.cpu_retailer.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;

public class Manufacturer {

    @FormParam("manufacturerUUID")
    @Pattern(regexp = "[a-zA-Z0-9]{8}(-[a-zA-Z0-9]{4}){3}-[a-zA-Z0-9]{12}")
    private String manufacturerUUID;

    @FormParam("manufacturerName")
    @NotEmpty
    @Size(min = 2, max = 15)
    private String manufacturerName;

    /*
    * Holt herstellerUUID
    * @return herstellerUUID
     */
    public String getManufacturerUUID() {
        return manufacturerUUID;
    }

    /*
    * Setzt herstellerUUID
    * @param herstellerUUID Übergabeparameter
     */
    public void setManufacturerUUID(String manufacturerUUID) {
        this.manufacturerUUID = manufacturerUUID;
    }

    /*
    * Holt herstellerName
    * @return herstellerName
     */
    public String getManufacturerName() {
        return manufacturerName;
    }

    /*
    * Setzt herstellerName
    * @param herstellerName Übergabeparameter
     */
    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }
}

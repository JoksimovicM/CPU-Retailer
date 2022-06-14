package ch.bzz.cpu_retailer.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;

public class Hersteller {

    @FormParam("herstellerUUID")
    @Pattern(regexp = "[a-zA-Z0-9]{8}(-[a-zA-Z0-9]{4}){3}-[a-zA-Z0-9]{12}")
    private String herstellerUUID;

    @FormParam("herstellerName")
    @NotEmpty
    @Size(min = 2, max = 15)
    private String herstellerName;

    /*
    * Holt herstellerUUID
    * @return herstellerUUID
     */
    public String getHerstellerUUID() {
        return herstellerUUID;
    }

    /*
    * Setzt herstellerUUID
    * @param herstellerUUID Übergabeparameter
     */
    public void setHerstellerUUID(String herstellerUUID) {
        this.herstellerUUID = herstellerUUID;
    }

    /*
    * Holt herstellerName
    * @return herstellerName
     */
    public String getHerstellerName() {
        return herstellerName;
    }

    /*
    * Setzt herstellerName
    * @param herstellerName Übergabeparameter
     */
    public void setHerstellerName(String herstellerName) {
        this.herstellerName = herstellerName;
    }
}

package ch.bzz.cpu_retailer.model;

public class Hersteller {

    private String herstellerUUID;
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

package ch.bzz.cpu_retailer.model;

import ch.bzz.cpu_retailer.data.DataHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;
import java.util.List;

public class CPU_Reihe {
    @JsonIgnore
    private Hersteller hersteller;

    @FormParam("reiheUUID")
    @Pattern(regexp = "[a-zA-Z0-9]{8}(-[a-zA-Z0-9]{4}){3}-[a-zA-Z0-9]{12}")
    private String reiheUUID;

    @FormParam("nameReihe")
    @NotEmpty
    @Size(min = 2, max = 15)
    private String nameReihe;

    @FormParam("beschreibung")
    @NotEmpty
    @Size(min = 2, max = 30)
    private String beschreibung;

    public String getHerstellerUUID() {
        if (getHersteller() == null) return null;
        return getHersteller().getHerstellerUUID();
    }

    public void setHerstellerUUID(String herstellerUUID) {
        setHersteller(new Hersteller());
        Hersteller hersteller = DataHandler.leseHerstellerMitUUID(herstellerUUID);
        getHersteller().setHerstellerUUID(herstellerUUID);
        getHersteller().setHerstellerName(hersteller.getHerstellerName());
    }

    public Hersteller getHersteller() {
        return hersteller;
    }

    public void setHersteller(Hersteller hersteller) {
        this.hersteller = hersteller;
    }

    public String getReiheUUID() {
        return reiheUUID;
    }

    public void setReiheUUID(String reiheUUID) {
        this.reiheUUID = reiheUUID;
    }

    public String getNameReihe() {
        return nameReihe;
    }

    public void setNameReihe(String nameReihe) {
        this.nameReihe = nameReihe;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }
}

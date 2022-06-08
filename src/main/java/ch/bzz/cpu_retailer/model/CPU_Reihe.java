package ch.bzz.cpu_retailer.model;

import ch.bzz.cpu_retailer.data.DataHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class CPU_Reihe {
    @JsonIgnore
    private Hersteller hersteller;

    private String reiheUUID;
    private String nameReihe;
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

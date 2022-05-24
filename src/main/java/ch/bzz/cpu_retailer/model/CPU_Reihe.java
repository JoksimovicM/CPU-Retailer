package ch.bzz.cpu_retailer.model;

import ch.bzz.cpu_retailer.data.DataHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class CPU_Reihe {

    private String reiheUUID;
    private String nameReihe;
    private String beschreibung;

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

package ch.bzz.cpu_retailer.model;

import java.util.List;

public class CPU_Reihe {

    private String nameReihe;
    private String beschreibung;
    private List<CPU> cpus;

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

    public List<CPU> getCpus() {
        return cpus;
    }

    public void setCpus(List<CPU> cpus) {
        this.cpus = cpus;
    }
}

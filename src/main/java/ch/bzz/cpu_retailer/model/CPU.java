package ch.bzz.cpu_retailer.model;

import ch.bzz.cpu_retailer.data.DataHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;

public class CPU {
    @JsonIgnore
    private CPU_Reihe cpu_reihe;

    private String cpuUUID;
    private String name;
    private Integer anzahlKerne;
    private Integer stromverbrauch;
    private Double taktfrequenz;
    private String sockel;
    private BigDecimal preis;

    /**
     * Holt die reiheUUID des CPU-Reihe-Objekts
     * @return reiheUUID
     */
    public String getReiheUUID() {
        if (getReihe()== null) return null;
        return getReihe().getReiheUUID();
    }

    /**
     * Erstellt ein CPU-Reihe-Objekt ohne die CPU-Liste
     * @param reiheUUID the key
     */
    public void setReiheUUID(String reiheUUID) {
        setReihe(new CPU_Reihe());
        CPU_Reihe cpu_reihe = DataHandler.leseReiheMitUUID(reiheUUID);
        getReihe().setReiheUUID(reiheUUID);
        getReihe().setNameReihe(cpu_reihe.getNameReihe());
    }

    /**
     * Holt cpu_reihe
     *
     * @return Wert von cpu_reihe
     */
    public CPU_Reihe getReihe() {
        return cpu_reihe;
    }

    /**
     * Setzt cpu_reihe
     *
     * @param cpu_reihe der zu setzende Wert
     */
    public void setReihe(CPU_Reihe cpu_reihe) {
        this.cpu_reihe = cpu_reihe;
    }

    public String getCpuUUID() {
        return cpuUUID;
    }

    public void setCpuUUID(String cpuUUID) {
        this.cpuUUID = cpuUUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAnzahlKerne() {
        return anzahlKerne;
    }

    public void setAnzahlKerne(Integer anzahlKerne) {
        this.anzahlKerne = anzahlKerne;
    }

    public Integer getStromverbrauch() {
        return stromverbrauch;
    }

    public void setStromverbrauch(Integer stromverbrauch) {
        this.stromverbrauch = stromverbrauch;
    }

    public Double getTaktfrequenz() {
        return taktfrequenz;
    }

    public void setTaktfrequenz(Double taktfrequenz) {
        this.taktfrequenz = taktfrequenz;
    }

    public String getSockel() {
        return sockel;
    }

    public void setSockel(String sockel) {
        this.sockel = sockel;
    }

    public BigDecimal getPreis() {
        return preis;
    }

    public void setPreis(BigDecimal preis) {
        this.preis = preis;
    }
}

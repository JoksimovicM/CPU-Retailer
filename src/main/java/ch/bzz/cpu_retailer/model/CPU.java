package ch.bzz.cpu_retailer.model;

import java.math.BigDecimal;

public class CPU {

    private String name;
    private Integer anzahlKerne;
    private Integer stromverbrauch;
    private Double taktfrequenz;
    private String sockel;
    private BigDecimal preis;

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

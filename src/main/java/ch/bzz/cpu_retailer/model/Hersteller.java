package ch.bzz.cpu_retailer.model;

import java.util.List;

public class Hersteller {

    private String herstellerName;
    private List<CPU_Reihe> sortiment;

    public String getHerstellerName() {
        return herstellerName;
    }

    public void setHerstellerName(String herstellerName) {
        this.herstellerName = herstellerName;
    }

    public List<CPU_Reihe> getSortiment() {
        return sortiment;
    }

    public void setSortiment(List<CPU_Reihe> sortiment) {
        this.sortiment = sortiment;
    }
}

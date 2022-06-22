package ch.bzz.cpu_retailer.model;

import ch.bzz.cpu_retailer.data.DataHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.*;
import javax.ws.rs.FormParam;
import java.math.BigDecimal;

public class CPU {
    @JsonIgnore
    private CPU_Series cpu_series;

    @FormParam("cpuUUID")
    @Pattern(regexp = "[a-zA-Z0-9]{8}(-[a-zA-Z0-9]{4}){3}-[a-zA-Z0-9]{12}")
    private String cpuUUID;

    @FormParam("name")
    @NotEmpty
    @Size(min = 2, max = 10)
    private String name;

    @FormParam("coreCount")
    @DecimalMin(value = "1")
    @DecimalMax(value = "64")
    private Integer coreCount;

    @FormParam("tdp")
    @DecimalMin(value = "50")
    @DecimalMax(value = "4000")
    private Integer tdp;

    @FormParam("frequency")
    @DecimalMin(value = "1.0")
    @DecimalMax(value = "7.0")
    private BigDecimal frequency;

    @FormParam("socket")
    @NotEmpty
    @Size(min = 2, max = 10)
    private String socket;

    @FormParam("price")
    @DecimalMin(value = "20.00")
    @DecimalMax(value = "1000.00")
    private BigDecimal price;

    /**
     * Holt die reiheUUID des CPU-Reihe-Objekts
     * @return reiheUUID
     */
    public String getSeriesUUID() {
        if (getSeries()== null) return null;
        return getSeries().getSeriesUUID();
    }

    /**
     * Erstellt ein CPU-Reihe-Objekt ohne die CPU-Liste
     * @param seriesUUID the key
     */
    public void setSeriesUUID(String seriesUUID) {
        setSeries(new CPU_Series());
        CPU_Series cpu_series = DataHandler.readSeriesbyUUID(seriesUUID);
        getSeries().setSeriesUUID(seriesUUID);
        getSeries().setSeriesName(cpu_series.getSeriesName());
    }

    /**
     * Holt cpu_reihe
     *
     * @return Wert von cpu_reihe
     */
    public CPU_Series getSeries() {
        return cpu_series;
    }

    /**
     * Setzt cpu_reihe
     *
     * @param cpu_series der zu setzende Wert
     */
    public void setSeries(CPU_Series cpu_series) {
        this.cpu_series = cpu_series;
    }

    /**
     * @return
     */
    public String getCpuUUID() {
        return cpuUUID;
    }


    /**
     * @param cpuUUID
     */
    public void setCpuUUID(String cpuUUID) {
        this.cpuUUID = cpuUUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCoreCount() {
        return coreCount;
    }

    public void setCoreCount(Integer coreCount) {
        this.coreCount = coreCount;
    }

    public Integer getTDP() {
        return tdp;
    }

    public void setTDP(Integer tdp) {
        this.tdp = tdp;
    }

    public BigDecimal getFrequency() {
        return frequency;
    }

    public void setFrequency(BigDecimal frequency) {
        this.frequency = frequency;
    }

    public String getSocket() {
        return socket;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

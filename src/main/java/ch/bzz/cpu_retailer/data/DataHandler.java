package ch.bzz.cpu_retailer.data;

import ch.bzz.cpu_retailer.model.CPU;
import ch.bzz.cpu_retailer.model.CPU_Reihe;
import ch.bzz.cpu_retailer.service.Config;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataHandler {

    private static DataHandler instance = null;
    private List<CPU> cpuList;
    private List<CPU_Reihe> reiheList;

    private DataHandler() {
        setReiheList(new ArrayList<>());
        readReiheJSON();
        setCPUList(new ArrayList<>());
        readCpuJSON();
    }

    public static DataHandler getInstance() {
        if (instance == null) {
            instance = new DataHandler();
        }
        return instance;
    }

    public List<CPU> readAllCpus() {
        return getCPUList();
    }

    public List<CPU_Reihe> readAllReihen() {
        return getReiheList();
    }

    private void readCpuJSON() {
        try {
            String path = Config.getProperty("cpuJSON");
            byte[] jsonData = Files.readAllBytes(Paths.get(path));
            ObjectMapper objectMapper = new ObjectMapper();
            CPU[] cpus = objectMapper.readValue(jsonData, CPU[].class);
            for (CPU cpu : cpus) {
                getCPUList().add(cpu);
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void readReiheJSON() {
        try {
            String path = Config.getProperty("reiheJSON");
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(path)
            );
            ObjectMapper objectMapper = new ObjectMapper();
            CPU_Reihe[] reihen = objectMapper.readValue(jsonData, CPU_Reihe[].class);
            for (CPU_Reihe reihe : reihen) {
                getReiheList().add(reihe);
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public CPU readCPUByUUID(String cpuUUID) {
        CPU cpu = null;
        for (CPU entry : getCPUList()) {
            if (entry.getCpuUUID().equals(cpuUUID)) {
                cpu = entry;
            }
        }
        return cpu;
    }

    public CPU_Reihe readReiheByUUID(String reiheUUID) {
        CPU_Reihe reihe = null;
        for (CPU_Reihe entry : getReiheList()) {
            if (entry.getReiheUUID().equals(reiheUUID)) {
                reihe = entry;
            }
        }
        return reihe;
    }

    private List<CPU> getCPUList() {
        return cpuList;
    }

    private void setCPUList(List<CPU> cpuList) {
        this.cpuList = cpuList;
    }

    private List<CPU_Reihe> getReiheList() {
        return reiheList;
    }

    private void setReiheList(List<CPU_Reihe> reiheList) {
        this.reiheList = reiheList;
    }
}

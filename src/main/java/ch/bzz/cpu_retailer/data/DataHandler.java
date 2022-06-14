package ch.bzz.cpu_retailer.data;

import ch.bzz.cpu_retailer.model.CPU;
import ch.bzz.cpu_retailer.model.CPU_Reihe;
import ch.bzz.cpu_retailer.model.Hersteller;
import ch.bzz.cpu_retailer.service.Config;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataHandler {

    private static List<CPU> cpuListe;
    private static List<CPU_Reihe> reiheListe;
    private static List<Hersteller> herstellerListe;

    private DataHandler() {
    }

    public static void initListen() {
        DataHandler.setCPUListe(null);
        DataHandler.setReiheListe(null);
        DataHandler.setHerstellerListe(null);
    }

    public static List<CPU> leseAlleCPUs() {
        return getCPUListe();
    }

    public static CPU leseCPUMitUUID(String cpuUUID) {
        CPU cpu = null;
        for (CPU entry : getCPUListe()) {
            if (entry.getCpuUUID().equals(cpuUUID)) {
                cpu = entry;
            }
        }
        return cpu;
    }

    public static void cpuHinzu(CPU cpu) {
        getCPUListe().add(cpu);
        schreibeCpuJSON();
    }

    public static void cpuAktuell() {
        schreibeCpuJSON();
    }

    public static boolean cpuLoeschen(String cpuUUID) {
        CPU cpu = leseCPUMitUUID(cpuUUID);
        if (cpu != null) {
            getCPUListe().remove(cpu);
            schreibeCpuJSON();
            return true;
        } else {
            return false;
        }
    }

    public static List<CPU_Reihe> leseAlleReihen() {
        return getReiheListe();
    }

    public static CPU_Reihe leseReiheMitUUID(String reiheUUID) {
        CPU_Reihe reihe = null;
        for (CPU_Reihe entry : getReiheListe()) {
            if (entry.getReiheUUID().equals(reiheUUID)) {
                reihe = entry;
            }
        }
        return reihe;
    }

    public static void reiheHinzu(CPU_Reihe reihe) {
        getReiheListe().add(reihe);
        schreibeReiheJSON();
    }

    public static void reiheAktuell() {
        schreibeReiheJSON();
    }

    public static boolean reiheLoeschen(String reiheUUID) {
        CPU_Reihe reihe = leseReiheMitUUID(reiheUUID);
        if (reihe != null) {
            getReiheListe().remove(reihe);
            schreibeReiheJSON();
            return true;
        } else {
            return false;
        }
    }

    public static List<Hersteller> leseAlleHersteller() {
        return getHerstellerListe();
    }

    public static Hersteller leseHerstellerMitUUID(String herstellerUUID) {
        Hersteller hersteller = null;
        for (Hersteller entry : getHerstellerListe()) {
            if (entry.getHerstellerUUID().equals(herstellerUUID)) {
                hersteller = entry;
            }
        }
        return hersteller;
    }

    public static void herstellerHinzu(Hersteller hersteller) {
        getHerstellerListe().add(hersteller);
        schreibeHerstellerJSON();
    }

    public static void herstellerAktuell() {
        schreibeHerstellerJSON();
    }

    public static boolean herstellerLoeschen(String herstellerUUID) {
        Hersteller hersteller = leseHerstellerMitUUID(herstellerUUID);
        if (hersteller != null) {
            getHerstellerListe().remove(hersteller);
            schreibeHerstellerJSON();
            return true;
        } else {
            return false;
        }
    }

    private static void leseCpuJSON() {
        try {
            String path = Config.getProperty("cpuJSON");
            byte[] jsonData = Files.readAllBytes(Paths.get(path));
            ObjectMapper objectMapper = new ObjectMapper();
            CPU[] cpus = objectMapper.readValue(jsonData, CPU[].class);
            for (CPU cpu : cpus) {
                getCPUListe().add(cpu);
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void schreibeCpuJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String cpuPfad = Config.getProperty("cpuJSON");
        try {
            fileOutputStream = new FileOutputStream(cpuPfad);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getCPUListe());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void leseReiheJSON() {
        try {
            String path = Config.getProperty("reiheJSON");
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(path)
            );
            ObjectMapper objectMapper = new ObjectMapper();
            CPU_Reihe[] reihen = objectMapper.readValue(jsonData, CPU_Reihe[].class);
            for (CPU_Reihe reihe : reihen) {
                getReiheListe().add(reihe);
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void schreibeReiheJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String reihePfad = Config.getProperty("reiheJSON");
        try {
            fileOutputStream = new FileOutputStream(reihePfad);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getReiheListe());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void leseHerstellerJSON() {
        try {
            String path = Config.getProperty("herstellerJSON");
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(path)
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Hersteller[] alleHersteller = objectMapper.readValue(jsonData, Hersteller[].class);
            for (Hersteller hersteller : alleHersteller) {
                getHerstellerListe().add(hersteller);
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void schreibeHerstellerJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String herstellerPfad = Config.getProperty("herstellerJSON");
        try {
            fileOutputStream = new FileOutputStream(herstellerPfad);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getHerstellerListe());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static List<CPU> getCPUListe() {
        if (cpuListe == null) {
            setCPUListe(new ArrayList<>());
            leseCpuJSON();
        }
        return cpuListe;
    }

    private static void setCPUListe(List<CPU> cpuListe) {
        DataHandler.cpuListe = cpuListe;
    }

    private static List<CPU_Reihe> getReiheListe() {
        if (reiheListe == null) {
            setReiheListe(new ArrayList<>());
            leseReiheJSON();
        }
        return reiheListe;
    }

    private static void setReiheListe(List<CPU_Reihe> reiheListe) {
        DataHandler.reiheListe = reiheListe;
    }

    private static List<Hersteller> getHerstellerListe() {
        if (herstellerListe == null) {
            setHerstellerListe(new ArrayList<>());
            leseHerstellerJSON();
        }
        return herstellerListe;
    }

    private static void setHerstellerListe(List<Hersteller> herstellerListe) {
        DataHandler.herstellerListe = herstellerListe;
    }
}

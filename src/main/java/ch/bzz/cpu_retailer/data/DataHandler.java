package ch.bzz.cpu_retailer.data;

import ch.bzz.cpu_retailer.model.CPU;
import ch.bzz.cpu_retailer.model.CPU_Series;
import ch.bzz.cpu_retailer.model.Manufacturer;
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

    private static List<CPU> cpuList;
    private static List<CPU_Series> seriesList;
    private static List<Manufacturer> manufacturerList;

    private DataHandler() {
    }

    public static void initLists() {
        DataHandler.setCPUList(null);
        DataHandler.setSeriesList(null);
        DataHandler.setManufacturerList(null);
    }

    public static List<CPU> readCPUs() {
        return getCPUList();
    }

    public static CPU readCPUbyUUID(String cpuUUID) {
        CPU cpu = null;
        for (CPU entry : getCPUList()) {
            if (entry.getCpuUUID().equals(cpuUUID)) {
                cpu = entry;
            }
        }
        return cpu;
    }

    public static void addCPU(CPU cpu) {
        getCPUList().add(cpu);
        writeCPUJSON();
    }

    public static void updateCPU() {
        writeCPUJSON();
    }

    public static boolean deleteCPU(String cpuUUID) {
        CPU cpu = readCPUbyUUID(cpuUUID);
        if (cpu != null) {
            getCPUList().remove(cpu);
            writeCPUJSON();
            return true;
        } else {
            return false;
        }
    }

    public static List<CPU_Series> readSeries() {
        return getSeriesList();
    }

    public static CPU_Series readSeriesbyUUID(String seriesUUID) {
        CPU_Series series = null;
        for (CPU_Series entry : getSeriesList()) {
            if (entry.getSeriesUUID().equals(seriesUUID)) {
                series = entry;
            }
        }
        return series;
    }

    public static void addSeries(CPU_Series series) {
        getSeriesList().add(series);
        writeSeriesJSON();
    }

    public static void updateSeries() {
        writeSeriesJSON();
    }

    public static boolean deleteSeries(String seriesUUID) {
        CPU_Series series = readSeriesbyUUID(seriesUUID);
        if (series != null) {
            getSeriesList().remove(series);
            writeSeriesJSON();
            return true;
        } else {
            return false;
        }
    }

    public static List<Manufacturer> readManufacturers() {
        return getManufacturerList();
    }

    public static Manufacturer readManufacturerbyUUID(String manufacturerUUID) {
        Manufacturer manufacturer = null;
        for (Manufacturer entry : getManufacturerList()) {
            if (entry.getManufacturerUUID().equals(manufacturerUUID)) {
                manufacturer = entry;
            }
        }
        return manufacturer;
    }

    public static void addManufacturer(Manufacturer manufacturer) {
        getManufacturerList().add(manufacturer);
        writeManufacturerJSON();
    }

    public static void updateManufacturer() {
        writeManufacturerJSON();
    }

    public static boolean deleteManufacturer(String manufacturerUUID) {
        Manufacturer manufacturer = readManufacturerbyUUID(manufacturerUUID);
        if (manufacturer != null) {
            getManufacturerList().remove(manufacturer);
            writeManufacturerJSON();
            return true;
        } else {
            return false;
        }
    }

    private static void readCPUJSON() {
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

    private static void writeCPUJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String cpuPath = Config.getProperty("cpuJSON");
        try {
            fileOutputStream = new FileOutputStream(cpuPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getCPUList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void readSeriesJSON() {
        try {
            String path = Config.getProperty("seriesJSON");
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(path)
            );
            ObjectMapper objectMapper = new ObjectMapper();
            CPU_Series[] seriesC = objectMapper.readValue(jsonData, CPU_Series[].class);
            for (CPU_Series series : seriesC) {
                getSeriesList().add(series);
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void writeSeriesJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String seriesPath = Config.getProperty("seriesJSON");
        try {
            fileOutputStream = new FileOutputStream(seriesPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getSeriesList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void readManufacturerJSON() {
        try {
            String path = Config.getProperty("manufacturerJSON");
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(path)
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Manufacturer[] manufacturers = objectMapper.readValue(jsonData, Manufacturer[].class);
            for (Manufacturer manufacturer : manufacturers) {
                getManufacturerList().add(manufacturer);
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void writeManufacturerJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String manufacturerPath = Config.getProperty("manufacturerJSON");
        try {
            fileOutputStream = new FileOutputStream(manufacturerPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getManufacturerList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static List<CPU> getCPUList() {
        if (cpuList == null) {
            setCPUList(new ArrayList<>());
            readCPUJSON();
        }
        return cpuList;
    }

    private static void setCPUList(List<CPU> cpuList) {
        DataHandler.cpuList = cpuList;
    }

    private static List<CPU_Series> getSeriesList() {
        if (seriesList == null) {
            setSeriesList(new ArrayList<>());
            readSeriesJSON();
        }
        return seriesList;
    }

    private static void setSeriesList(List<CPU_Series> seriesList) {
        DataHandler.seriesList = seriesList;
    }

    private static List<Manufacturer> getManufacturerList() {
        if (manufacturerList == null) {
            setManufacturerList(new ArrayList<>());
            readManufacturerJSON();
        }
        return manufacturerList;
    }

    private static void setManufacturerList(List<Manufacturer> manufacturerList) {
        DataHandler.manufacturerList = manufacturerList;
    }
}

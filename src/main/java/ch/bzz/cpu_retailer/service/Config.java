package ch.bzz.cpu_retailer.service;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

@ApplicationPath("/resource")

public class Config extends Application {

    private static final String PROPERTIES_PATH = "/home/bzz/webapp/cpuList.properties";
    private static Properties properties = null;

    @Override
    public Set<Class<?>> getClasses() {
        HashSet providers = new HashSet<Class<?>>();
        providers.add(TestService.class);
        providers.add(CPU_Service.class);
        providers.add(CPU_Reihe_Service.class);
        providers.add(Hersteller_Service.class);
        return providers;
    }

    public static String getProperty(String property) {
        if (Config.properties == null) {
            setProperties(new Properties());
            readProperties();
        }
        String value = Config.properties.getProperty(property);
        if (value == null) return "";
        return value;
    }

    private static void readProperties() {

        InputStream inputStream;
        try {
            inputStream = new FileInputStream(PROPERTIES_PATH);
            properties.load(inputStream);
            if (inputStream != null) inputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException();
        }
    }

    private static void setProperties(Properties properties) {
        Config.properties = properties;
    }
}

package gr.aegean.book.client;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertyReader {
	
	private static final String ENV_CONFIG="BOOK_CONFIG_DIR";
    private static final String PROPERTY_FILENAME="utility-client.properties";
    private static final String propertyFilePath;
    private static String ip;
    private static String port;
	
    static {
    	propertyFilePath = retrieveConfigurationDirectoryFullPath();
    	getProperties();
    }

	
	private static String retrieveConfigurationDirectoryFullPath()
    {
        String propertyFilePath = System.getenv(ENV_CONFIG);
        System.out.println("Got path: " + propertyFilePath);
        
        if (propertyFilePath == null) {
          propertyFilePath = System.getProperty("gr.aegean.configdir");
          System.out.println("Got path: " + propertyFilePath);
        }
        
        if (propertyFilePath == null)
        {
        	Path currentPath = Paths.get(".");
            propertyFilePath = currentPath.toAbsolutePath().toString();
            System.out.println("Got path: " + propertyFilePath);
        }
        return propertyFilePath;
    }
	
    private static String retrievePropertiesFilePath(String propertiesFileName)
    {
        Path configPath = Paths.get(propertyFilePath);
        return configPath.resolve(propertiesFileName).toAbsolutePath().toString();
    }
    
    private static Properties loadPropertyFile()
    {
        String propertyPath = retrievePropertiesFilePath(PROPERTY_FILENAME);
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(propertyPath));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return props;
    }
    
    private static String getDefaultValueIfNull(String s, String defaultVal) {
    	if (s == null || s.trim().equals("")) return defaultVal;
    	else return s;
    }

	private static void getProperties(){
		Properties props = loadPropertyFile();
		ip = getDefaultValueIfNull(props.getProperty("ip"),"localhost");
		port = getDefaultValueIfNull(props.getProperty("port"),"8080");
	}
	
	public static String getIp() {
		return ip;
	}
	
	public static String getPort() {
		return port;
	}
	
}

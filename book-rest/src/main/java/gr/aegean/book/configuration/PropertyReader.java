package gr.aegean.book.configuration;

import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
	private static boolean sqlite;
    private static String dbName;
    private static String dbHost;
    private static String dbPort;
    private static String login;
    private static String pwd;
	
    static {
    	getProperties();
    }
	 
    private static Properties loadPropertyFile()
    {
    	try{
    			InputStream input = PropertyReader.class.getClassLoader().getResourceAsStream("service.properties");
    			Properties prop = new Properties(); 
    			if (input == null) { 
    				System.out.println("Sorry, unable to find service.properties"); 	
    				return null; 
    			}
    			prop.load(input); 
    			return prop;
    	} 
    	catch (Exception ex) { 
    		ex.printStackTrace();
    	}
    	return null;
    }
    
    private static String getDefaultValueIfNull(String s, String defaultVal) {
    	if (s == null || s.trim().equals("")) return defaultVal;
    	else return s;
    }
    
    private static boolean getDefaultValueIfNull(String s, boolean defaultVal) {
    	if (s == null || s.trim().equals("")) return defaultVal;
    	else if (s.equals("sqlite")) return true;
    	else return false;
    }

	private static void getProperties(){
		sqlite = getDefaultValueIfNull(System.getProperty("db.type"),false);
		Properties props = loadPropertyFile();
		if (props != null){
			dbName = getDefaultValueIfNull(props.getProperty("dbName"),"");
			dbHost = getDefaultValueIfNull(props.getProperty("dbHost"),"");
			dbPort = getDefaultValueIfNull(props.getProperty("dbPort"),"");
			login = getDefaultValueIfNull(props.getProperty("login"),"");
			pwd = getDefaultValueIfNull(props.getProperty("pwd"),"");
		}
		try {
			String altDbHost = System.getenv("DB_HOST");
			if (altDbHost != null && !altDbHost.equals("")) dbHost = altDbHost; 
		}
		catch(Exception e) {}
	}
	
	public static String getDbName() {
		return dbName;
	}
	
	public static String getDbHost() {
		return dbHost;
	}
	
	public static void setDbHost(String dbHost) {
		PropertyReader.dbHost = dbHost;
	}
	
	public static String getDbPort() {
		return dbPort;
	}

	public static String getLogin() {
		return login;
	}
	
	public static String getPwd() {
		return pwd;
	}

	public static boolean isSqlite() {
		return sqlite;
	}

	public static void setSqlite(boolean sqlite) {
		PropertyReader.sqlite = sqlite;
	}
}

package vn.com.vnptepay.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
	
	public static String partnerCode;
	public static String url;
	public static String privatekey;
	public static String publickey;
	public static String OPERATION_VERIFY;
	public static String OPERATION_TRANSFER;
	public static String OPERATION_QUERY_TRANS;
	public static String OPERATION_CHECK_BALANCE;
	public static String ACCTYPE_ACCNUM;
	public static String ACCTYPE_CARDNUM;
	
	private static Properties properties = new Properties();
	

	public static void loadProperties() throws IOException {

		String fileName = "./config/conf.cfg";
		try
		{
			FileInputStream propsFile = new FileInputStream(fileName);
			properties.load(propsFile);
			propsFile.close();

			partnerCode = properties.getProperty("partnerCode");
			url =  properties.getProperty("url");
			privatekey = properties.getProperty("privatekey");
			publickey = properties.getProperty("publickey");
			OPERATION_VERIFY = properties.getProperty("OPERATION_VERIFY");
			OPERATION_TRANSFER = properties.getProperty("OPERATION_TRANSFER");
			OPERATION_QUERY_TRANS = properties.getProperty("OPERATION_QUERY_TRANS");
			OPERATION_CHECK_BALANCE = properties.getProperty("OPERATION_CHECKBALANCE");
			ACCTYPE_ACCNUM = properties.getProperty("ACCTYPE_ACCNUM");
			ACCTYPE_CARDNUM = properties.getProperty("ACCTYPE_CARDNUM");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	static int getIntProperty(String propName, int defaultValue) {
		return Integer.parseInt(properties.getProperty(propName, Integer
				.toString(defaultValue)));
	}
	

	static boolean getBoolProperty(String propName, boolean defaultValue) {
		if (properties.getProperty(propName).equalsIgnoreCase("true")) return true;
		else if (properties.getProperty(propName).equalsIgnoreCase("false")) return false;
		else return defaultValue;
	}

	static {
		try {
			loadProperties();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

package org.bitbucket.nightsir.zuuladvanced.configuration;

import java.io.IOException;
import java.util.Properties;

/**
 * Class for the main text configurations of the Zuul-Game.<br>
 * The configuration are loaded from <i>zuul.properties</i>.
 * 
 * @author Christian Sami
 */
public class Configuration {
	private static Configuration configuration;
	
	private Properties properties;
	
	private Configuration() {
		properties = new Properties();
		try {
			properties.load(getClass().getClassLoader().getResourceAsStream("zuul.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the String corresponding to the given key
	 * 
	 * @param propertiesKey key for the property
	 * @return property for given key
	 */
	public static String get(String propertiesKey) {
		if (configuration == null) {
			configuration = new Configuration();
		}
		return configuration.properties.getProperty(propertiesKey);
	}
}

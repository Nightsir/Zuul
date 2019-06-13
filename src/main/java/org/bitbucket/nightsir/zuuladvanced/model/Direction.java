package org.bitbucket.nightsir.zuuladvanced.model;

/**
 * Enumeration for the direction of the game.<br>
 * A direction has also the key for the configuration.
 * 
 * @author Christian Sami
 */
public enum Direction {
	NORTH("dir_north"),
	EAST("dir_east"),
	SOUTH("dir_south"),
	WEST("dir_west");
	
	private String configurationKey;

	private Direction(String configurationKey) {
		this.configurationKey = configurationKey;
	}
	
	/**
	 * Gets the key used in the configuration for the direction.
	 * 
	 * @return key for configuration for direction
	 */
	public String getConfigurationKey() {
		return configurationKey;
	}
}

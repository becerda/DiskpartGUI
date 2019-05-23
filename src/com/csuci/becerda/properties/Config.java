package com.csuci.becerda.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Config {

	// Show All Volumes Property (or only Removable)
	private static boolean CONFIG_SHOW_ALL_VOLUMES = true;
	private static final String CONFIG_KEY_SHOW_ALL_VOLUMES = "show_all_volumes";

	// Version
	public static final String ACTUAL_VERSION = "0.4";
	public static final int VERSION_NUM = 4;
	private static final String CONFIG_KEY_VERSION = "current_version";

	// Auto Check For Updates
	private static boolean CONFIG_CHECK_FOR_UPDATE = false;
	private static final String CONFIG_KEY_CHECK_FOR_UPDATE = "check_for_updates";

	private static final String CONFIG_FILE_PATH = "dpg.config";

	private static final Properties props = new Properties();

	public static void loadConfig() {
		try {
			File f = new File(CONFIG_FILE_PATH);
			if (!f.exists()) {
				f.createNewFile();
				saveConfig();
			}
			InputStream input = new FileInputStream(CONFIG_FILE_PATH);

			props.load(input);

			String s;

			// Load Check For Updates Prop
			s = props.getProperty(CONFIG_KEY_CHECK_FOR_UPDATE, "false");
			if (s.equals("true"))
				CONFIG_CHECK_FOR_UPDATE = true;
			else
				CONFIG_CHECK_FOR_UPDATE = false;

			// Load Show All Volumes Prop
			s = props.getProperty(CONFIG_KEY_SHOW_ALL_VOLUMES, "false");
			if (s.equals("true"))
				CONFIG_SHOW_ALL_VOLUMES = true;
			else
				CONFIG_SHOW_ALL_VOLUMES = false;

		} catch (IOException e) {
			// TODO error for failed to load options
		}
	}

	public static void saveConfig() {
		try {
			// Open file stream
			OutputStream output = new FileOutputStream(CONFIG_FILE_PATH);

			// Save show_all_volumes
			if (CONFIG_SHOW_ALL_VOLUMES)
				props.setProperty(CONFIG_KEY_SHOW_ALL_VOLUMES, "true");
			else
				props.setProperty(CONFIG_KEY_SHOW_ALL_VOLUMES, "false");

			// Save Check For Updates
			if (CONFIG_CHECK_FOR_UPDATE)
				props.setProperty(CONFIG_KEY_CHECK_FOR_UPDATE, "true");
			else
				props.setProperty(CONFIG_KEY_CHECK_FOR_UPDATE, "false");

			// Save current_version
			props.setProperty(CONFIG_KEY_VERSION, ACTUAL_VERSION);

			// Write to file
			props.store(output, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean getShowAllVolumes() {
		return CONFIG_SHOW_ALL_VOLUMES;
	}

	public static void setShowAllVolumes(boolean b) {
		CONFIG_SHOW_ALL_VOLUMES = b;
	}

	public static String getVersion() {
		return ACTUAL_VERSION;
	}

	public static boolean getCheckForUpdate() {
		return CONFIG_CHECK_FOR_UPDATE;
	}

	public static void setCheckForUpdate(boolean set) {
		CONFIG_CHECK_FOR_UPDATE = set;
	}
}

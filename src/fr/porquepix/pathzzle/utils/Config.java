package fr.porquepix.pathzzle.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONObject;
import org.json.JSONTokener;

public class Config {

	private static Config _instance = null;
	
	public static final String MAX_BASE_LEVEL = "maxBaseLevel";
	public static final String MUSIC_LEVEL = "musicLevel";
	public static final String SOUNDS_LEVEL = "soundsLevel";
	
	private JSONObject config;
	private File configFile;
	
	protected Config() {
		configFile = new File("config.json");
		
		if (!configFile.exists()) {
			createConfigFile();
		}
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(configFile));
			config = new JSONObject(new JSONTokener(br));
		} catch (FileNotFoundException e) {}	
		
		loadMissingProp();
	}
	
	private void loadMissingProp() {
		Object o;
		
		o = config.opt(MAX_BASE_LEVEL);
		if (o == null) {
			config.put(MAX_BASE_LEVEL, 1);
		}
		
		o = config.opt(MUSIC_LEVEL);
		if (o == null) {
			config.put(MUSIC_LEVEL, 100);
		}
		
		o = config.opt(SOUNDS_LEVEL);
		if (o == null) {
			config.put(SOUNDS_LEVEL, 100);
		}
	}

	private void createConfigFile() {
		try {
			configFile.createNewFile();
			write("{}");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public static Config getInstance() {
		if (_instance == null) {
			_instance = new Config();
		}
		return _instance;
	}
	
	public Object get(String key) {
		return config.get(key);
	}
	
	public void put(String key, Object value) {
		config.put(key, value);
	}
	
	public void save() {
		try {
			write(config.toString());
		} catch (IOException e) {
			throw new IllegalStateException("Impossible to save configuration !");
		}
	}

	private void write(String string) throws IOException {
		BufferedWriter bf = new BufferedWriter(new FileWriter(configFile));
		bf.write(string);
		bf.flush();
		bf.close();
	}
	
}

package main.com.udcinc.udc.settings.saving;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import main.com.udcinc.udc.game.GameSettings;

public class DeserializeSettings {

	public static GameSettings load() {
		GameSettings settings = null;
		try {
			FileInputStream fileInput = new FileInputStream("settings.ser");
			ObjectInputStream objectInput = new ObjectInputStream(fileInput);
			settings = (GameSettings) objectInput.readObject();
			objectInput.close();
			fileInput.close();
		} catch (IOException i) {
			return new GameSettings();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("GameState not found");
		}
		return settings == null ? new GameSettings() : settings;
	}

}

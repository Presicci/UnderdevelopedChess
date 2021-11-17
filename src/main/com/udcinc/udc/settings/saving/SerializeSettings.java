package main.com.udcinc.udc.settings.saving;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import main.com.udcinc.udc.game.GameSettings;

public class SerializeSettings {
	
	public static void save(GameSettings settings) {
		try {
			FileOutputStream fileOutput = new FileOutputStream("settings.ser");
			ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
			objectOutput.writeObject(settings);
			objectOutput.close();
			fileOutput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
}

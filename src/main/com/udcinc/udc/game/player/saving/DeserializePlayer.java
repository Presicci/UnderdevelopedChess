package main.com.udcinc.udc.game.player.saving;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import main.com.udcinc.udc.game.player.Player;

public class DeserializePlayer {

	public static Player load(String filename) {
		Player player = null;
		try {
			FileInputStream fileInput = new FileInputStream("playersaves/" + filename + ".ser");
			ObjectInputStream objectInput = new ObjectInputStream(fileInput);
			player = (Player) objectInput.readObject();
			objectInput.close();
			fileInput.close();
		} catch (IOException i) {
			i.printStackTrace();
			System.out.println("Player save " + filename + " not found");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Class not found");
		}
		return player;
	}

}

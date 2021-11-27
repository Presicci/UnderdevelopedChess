package main.com.udcinc.udc.game.player.saving;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import main.com.udcinc.udc.game.player.Player;

public class SerializePlayer {

	public static void save(Player player) {
		try {
			FileOutputStream fileOutput = new FileOutputStream("playersaves/" + player.getName().replace(" ", "_") + ".ser");
			ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
			objectOutput.writeObject(player);
			objectOutput.close();
			fileOutput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
}

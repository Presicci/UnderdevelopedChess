package main.com.udcinc.udc.game.state;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SerializeState {

	public static void save(GameState gs) {
		try {
			FileOutputStream fileOutput = new FileOutputStream("savegame.ser");
			ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
			objectOutput.writeObject(gs);
			objectOutput.close();
			fileOutput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
}

package main.com.udcinc.udc.game.state;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javafx.scene.layout.GridPane;
import main.com.udcinc.udc.game.scene.GameSceneController;

public class DeserializeState {

	public static GameState load(GridPane boardPane) {
		GameState gs = null;
		try {
			FileInputStream fileInput = new FileInputStream("savegame.ser");
			ObjectInputStream objectInput = new ObjectInputStream(fileInput);
			gs = (GameState) objectInput.readObject();
			objectInput.close();
			fileInput.close();
		} catch (IOException i) {
			i.printStackTrace();
			System.out.println("Savegame not found");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("GameState not found");
		}
		return gs == null ? new GameState(new GameSceneController(boardPane)) : gs;
	}
}

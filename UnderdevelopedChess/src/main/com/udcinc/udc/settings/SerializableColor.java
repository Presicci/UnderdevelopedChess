package main.com.udcinc.udc.settings;

import java.io.Serializable;

import javafx.scene.paint.Color;

/**
 * rgba representation of a javafx color for the sake of serialization
 * 
 * @author Thomas Presicci
 */
public class SerializableColor implements Serializable {

	private static final long serialVersionUID = 5034448393372096395L;

	// RGBA values
	private double red, green, blue, alpha;
	
	/**
	 * Constructor that takes a javafx color and saves it as rgba
	 * @param color The javafx color
	 */
	public SerializableColor(Color color) {
		this.red = color.getRed();
		this.green = color.getGreen();
		this.blue = color.getBlue();
		this.alpha = color.getOpacity();
	}
	
	/**
	 * @return Javafx color with stored rgba values
	 */
	public Color getColor() {
		return new Color(red, green, blue, alpha);
	}
}

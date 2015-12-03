/**
 * Iñigo Alonso - 665959
 * Alejandro Dieste - 541892
 */
package trazadorrayos;

import javax.vecmath.Point4d;
import java.awt.Color;

/**
 * Clase que representa el foco de luz de la escena. Tiene posicion, intensidad,
 * y la intensidad ambiente
 *
 */
public class Foco {

	private Point4d posicion = null;

	private Color color = null;

	public Foco(Point4d posicion, Color color) {
		this.color = color;
		this.posicion = posicion;
	}

	public Point4d getPosicion() {
		return posicion;
	}



	public Color getColor() {
		return color;
	}

}

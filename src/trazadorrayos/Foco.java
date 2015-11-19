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
	private double intensidad = 0;
	private double intensidadAmbiente = 0;

	private Color color = null;

	public Foco(Point4d posicion, Color color, double intensidad,
			double intensidadAmbiente) {
		this.color = color;
		this.intensidad = intensidad;
		this.posicion = posicion;
		this.intensidadAmbiente = intensidadAmbiente;
	}

	public Point4d getPosicion() {
		return posicion;
	}

	public double getIntensidad() {
		return intensidad;
	}

	public double getIntensidadAmbiente() {
		return intensidadAmbiente;
	}

	public Color getColor() {
		return color;
	}

}

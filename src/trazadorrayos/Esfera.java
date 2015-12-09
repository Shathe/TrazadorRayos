/**
 * Iñigo Alonso - 665959
 * Alejandro Dieste - 541892
 */
package trazadorrayos;

import java.awt.Color;

import javax.vecmath.Point4d;
import javax.vecmath.Vector4d;

/**
 * Clase que representa el objeto esfera. Tiene radio y un radio.
 *
 */
public class Esfera extends Figura {

	private double radio = 0.0;
	private Point4d centro = null;

	public Esfera(Point4d centro, double refraccion, double reflexion,double transparencia,
			Color kd, Color ks, double radio, boolean ambiental) {
		super(refraccion, reflexion,transparencia, kd, ks, ambiental);
		this.radio = radio;
		this.centro = centro;

	}

	/**
	 * Dado un punto de la esfera, halla el vector normal
	 */
	@Override
	public Vector4d getNormal(Point4d puntoInterseccion) {
		Vector4d normal = new Vector4d(puntoInterseccion);
		normal.sub(centro);
		normal.w = 0;
		return normal;
	}

	public double getRadio() {
		return radio;
	}

	public Point4d getCentro() {
		return centro;
	}
}

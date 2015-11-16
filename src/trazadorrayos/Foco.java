/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trazadorrayos;

import javax.vecmath.Point4d;
import java.awt.Color;

/**
 *
 * @author shathe
 */
public class Foco {

	private Point4d posicion = null;
	private double intensidad = 0;
	private double intensidadAmbiente = 0;

	private Color color = null;

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

	public Foco(Point4d posicion, Color color, double intensidad,
			double intensidadAmbiente) {
		this.color = color;
		this.intensidad = intensidad;
		this.posicion = posicion;
		this.intensidadAmbiente = intensidadAmbiente;
	}

}

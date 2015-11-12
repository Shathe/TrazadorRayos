/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trazadorrayos;

import javax.vecmath.Point4d;

/**
 *
 * @author shathe
 */
public class Figura {
	private Point4d posicion = null;
	Color color = null;
	private double indiceRefraccion = 0;
	private double indiceReflectividad = 0;
	private double indiceEspecularKS = 0;
	private double indiceDifusionKD = 0;

	public Figura(Point4d posicion, Color color, double refraccion,
			double reflexion, double kd, double ks) {
		this.color = color;
		this.posicion = posicion;
		indiceRefraccion = refraccion;
		indiceReflectividad = reflexion;
		indiceEspecularKS = ks;
		indiceDifusionKD = kd;
	}
	
	

	public Point4d getPosicion() {
		return posicion;
	}

	public Color getColor() {
		return color;
	}

	public double getIndiceRefraccion() {
		return indiceRefraccion;
	}

	public double getIndiceReflectividad() {
		return indiceReflectividad;
	}

	public double getIndiceEspecularKS() {
		return indiceEspecularKS;
	}

	public double getIndiceDifusionKD() {
		return indiceDifusionKD;
	}

}

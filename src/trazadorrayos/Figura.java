/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trazadorrayos;
import java.awt.Color;

import javax.vecmath.Point4d;

/**
 *
 * @author shathe
 */
public class Figura {
	Color color = null;
	private double indiceRefraccion = 0;
	private double indiceReflectividad = 0;
	private double indiceEspecularKS = 0;
	private double indiceDifusionKD = 0;

	public Figura(Color color, double refraccion,
			double reflexion, double kd, double ks) {
		this.color = color;
		indiceRefraccion = refraccion;
		indiceReflectividad = reflexion;
		indiceEspecularKS = ks;
		indiceDifusionKD = kd;
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

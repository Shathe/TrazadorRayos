/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trazadorrayos;

import java.awt.Color;

import javax.vecmath.Point4d;
import javax.vecmath.Vector4d;

/**
 *
 * @author shathe
 */
public class Figura {
	Color kd = null;
	Color ks = null;
	private double indiceRefraccion = 0;
	private double indiceReflectividad = 0;

	public Figura(double refraccion, double reflexion, Color kd, Color ks) {
		this.kd = kd;
		this.ks = ks;
		indiceRefraccion = refraccion;
		indiceReflectividad = reflexion;
	}

	/**
	 * @return the kd
	 */
	public Color getKd() {
		return kd;
	}

	/**
	 * @param kd
	 *            the kd to set
	 */
	public void setKd(Color kd) {
		this.kd = kd;
	}

	/**
	 * @return the ks
	 */
	public Color getKs() {
		return ks;
	}

	/**
	 * @param ks
	 *            the ks to set
	 */
	public void setKs(Color ks) {
		this.ks = ks;
	}

	public double getIndiceRefraccion() {
		return indiceRefraccion;
	}

	public double getIndiceReflectividad() {
		return indiceReflectividad;
	}

	public Vector4d getNormal(Point4d puntoInterseccion) {
		return null;
	}
}

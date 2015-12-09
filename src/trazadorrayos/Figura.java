/**
 * Iñigo Alonso - 665959
 * Alejandro Dieste - 541892
 */
package trazadorrayos;

import java.awt.Color;

import javax.vecmath.Point4d;
import javax.vecmath.Vector4d;

/**
 * Clase que representa cualquier figura que se puede poner en la escena
 *
 */
public class Figura {
	Color kd = null;
	Color ks = null;
	private double indiceRefraccion = 0;
	private double indiceReflectividad = 0;
	private double transparencia =0;
	private boolean ambiental=false;

	public Figura(double refraccion, double reflexion,double transparencia, Color kd, Color ks, boolean ambiental) {
		this.kd = kd;
		this.ks = ks;
		indiceRefraccion = refraccion;
		indiceReflectividad = reflexion;
		this.transparencia=transparencia;
		this.ambiental=ambiental;
	}

	public Color getKd() {
		return kd;
	}

	public void setKd(Color kd) {
		this.kd = kd;
	}

	public Color getKs() {
		return ks;
	}

	public void setKs(Color ks) {
		this.ks = ks;
	}

	public double getIndiceRefraccion() {
		return indiceRefraccion;
	}

	public double getIndiceReflectividad() {
		return indiceReflectividad;
	}
	
	public double getTransparencia(){
		return transparencia;
	}

	public boolean getAmbiental(){
		return ambiental;
	}
	
	public void setAmbiental(boolean ambiental){
		this.ambiental=ambiental;
	}
	
	public Vector4d getNormal(Point4d puntoInterseccion) {
		return null;
	}
}

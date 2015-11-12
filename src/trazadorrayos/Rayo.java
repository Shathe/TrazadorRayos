package trazadorrayos;

import javax.vecmath.Point4d;
import javax.vecmath.Vector4d;

public class Rayo {

	private Vector4d direccion;
	private Point4d punto;
	private Color color;
	private double intensidad;

	public Rayo(Vector4d direccion, Point4d punto, Color color,
			double intensidad) {
		this.direccion = direccion;
		this.punto = punto;
		this.color=color;
		this.intensidad=intensidad;
	}

}

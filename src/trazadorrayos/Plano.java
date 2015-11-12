package trazadorrayos;

import javax.vecmath.Point4d;
import javax.vecmath.Vector4d;

public class Plano extends Figura {

	private Point4d punto = null;
	private Vector4d vector = null;

	public Plano(Point4d posicion, Color color, double refraccion,
			double reflexion, double kd, double ks, Vector4d vector) {
		super(posicion, color, refraccion, reflexion, kd, ks);
		this.punto = posicion;
		this.vector = vector;
	}
}

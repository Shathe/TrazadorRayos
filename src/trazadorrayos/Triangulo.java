package trazadorrayos;

import javax.vecmath.Point4d;
import javax.vecmath.Vector4d;

public class Triangulo extends Figura {
	private Point4d punto1 = null;
	private Point4d punto2 = null;
	private Point4d punto3 = null;

	public Triangulo(Point4d posicion, Color color, double refraccion,
			double reflexion, double kd, double ks, Point4d punto2, Point4d punto3) {
		super(posicion, color, refraccion, reflexion, kd, ks);
		this.punto1 = posicion;
		this.punto2 = punto2;
		this.punto3 = punto3;
	}
}

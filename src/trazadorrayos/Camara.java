/**
 * Iñigo Alonso - 665959
 * Alejandro Dieste - 541892
 */
package trazadorrayos;

import javax.vecmath.Matrix4d;
import javax.vecmath.Vector4d;
import javax.vecmath.Point4d;

/**
 * Clase que representa la camara, con su posicion, vector direccion y su
 * sistema de coordenadas propio
 *
 */
public class Camara {
	double intensidadAmbiente;
	private Point4d posicion = null; // e
	private int distanciaPantalla = 0;
	private Vector4d direccion = new Vector4d();// el vector g
	// vectores del sistema de coordenadas de la camara
	private Vector4d w = new Vector4d();
	private Vector4d u = new Vector4d();
	private Vector4d v = new Vector4d();
	private Matrix4d cambioBase = new Matrix4d();// [u v w posicion]

	public Camara(Vector4d direccion, int distanciaPantalla, Point4d posicion,
			double intensidadAmbiente) {
		this.intensidadAmbiente=intensidadAmbiente;
		this.posicion = posicion;
		this.distanciaPantalla = distanciaPantalla;
		this.direccion = direccion;
		// ahora se calcula el sistema de coordenadas
		// w = -g/|g|
		w = new Vector4d(direccion);
		w.negate();
		w.normalize();
		// u = up x w/|up x w|
		Vector4d up = new Vector4d(1, 0, 0, 0); // puede ser cualquiera
		u = Operaciones.crossProduct(up, w);
		u.normalize();
		// v = w x u
		v = Operaciones.crossProduct(w, u);

	}

	public Camara(Point4d posicion) {
		this.posicion = posicion;

	}

	/**
	 * Establece la matriz de cambio de base | ux uy uz uw | | vx vy vz vw | |
	 * wx wy wz ww | | ex ey ez ew |
	 * 
	 * @return
	 */
	public Matrix4d getCambioBase() {
		cambioBase.setRow(0, u);
		cambioBase.setRow(1, v);
		cambioBase.setRow(2, w);
		cambioBase.setRow(3, posicion.x, posicion.y, posicion.z, posicion.w);
		return cambioBase;
	}
	
	public double getIntensidadAmbiente(){
		return intensidadAmbiente;
	}

	public Point4d getPosicion() {
		return posicion;
	}

	public int getDistanciaPantalla() {
		return distanciaPantalla;
	}

	public Vector4d getDireccion() {
		return direccion;
	}

	public Vector4d getW() {
		return w;
	}

	public Vector4d getU() {
		return u;
	}

	public Vector4d getV() {
		return v;
	}

}

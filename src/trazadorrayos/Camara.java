/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trazadorrayos;

import javax.vecmath.Matrix3d;
import javax.vecmath.Matrix4d;
import javax.vecmath.Vector4d;
import javax.vecmath.Vector3d;
import javax.vecmath.Point4d;

/**
 *
 * @author shathe
 */
public class Camara {
	// figura:camara posicion:7,4,1 distanciaPantalla:2 direccion:-1,1,0

	private Point4d posicion = null;
	private int distanciaPantalla = 0;
	private Vector4d direccion = new Vector4d();
	// ahora vienen los vectores del sistema de coordenadas de la camara
	private Vector4d w = new Vector4d();
	private Vector4d u = new Vector4d();
	private Vector4d v = new Vector4d();
	private Matrix4d cambioBase = new Matrix4d();// [u v w posicion]

	public Camara(Vector4d direccion, int distanciaPantalla, Point4d posicion) {
		this.posicion = posicion;
		this.distanciaPantalla = distanciaPantalla;
		this.direccion = direccion;
		// ahora se calcula el sistema de coordenadas
		// w=normalize(-direccion)
		w.w = direccion.w;
		w.y = -direccion.y;
		w.x = -direccion.x;
		w.z = -direccion.z;
		w.normalize();
		// calculamos u: up x w/|up x w|
		Vector4d up = new Vector4d(1, 0, 0, 0);
		u = crossProduct(up, w);
		u.normalize();
		// calculamos v: w x u
		v = crossProduct(w, u);

	}

	public Camara(Point4d posicion) {
		this.posicion = posicion;

	}

	public Vector4d crossProduct(Vector4d a, Vector4d b) {
		Vector3d aux = new Vector3d(a.x, a.y, a.z);
		Vector3d bux = new Vector3d(b.x, b.y, b.z);
		aux.cross(aux, bux);
		return new Vector4d(aux.x, aux.y, aux.z, a.w);
	}

	public Matrix4d getCambioBase() {
		cambioBase.setRow(0, u);
		cambioBase.setRow(1, v);
		cambioBase.setRow(2, w);
		cambioBase.setRow(3, posicion.x, posicion.y, posicion.z, posicion.w);
		return cambioBase;
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

	public static void main(String[] args) {
		Camara camara = new Camara(new Vector4d(-1, -1, 0, 0), 2, new Point4d(
				7, 4, 1, 1));
		System.out.println(camara);
		Rayo rayo = new Rayo(new Vector4d(-1, -1, 0, 0),
				new Point4d(7, 4, 1, 1));
		for (int i = 0; i < 30; i++) {
			System.out.println(i);
			System.out.println(rayo.evaluar(i));
		}
		Triangulo triangulo = new Triangulo(new Point4d(8, 2, 1, 1),new Point4d(6, 6, 3, 1),
				new Point4d(4, 2, 1, 1),  null, 0.8, 1,
				0.5, 0.5);
		System.out.println(Interseccion.intersecta(rayo, triangulo));
		Vector4d reflejado=new Vector4d(1,1,0,0);
		Vector4d rayoAlOjo=new Vector4d(1,1,0,0);
		double coseno = reflejado.dot(rayoAlOjo) / rayoAlOjo.length()
				/ reflejado.length();
		System.out.println(coseno);
		// figura:triangulo punto1: 6 3 1 1 punto2: 5 -5 0 1 punto3: 10 -5 0 1
		// color: 255 0 0
		// indicerefraccion: 0.8 reflectividad: 1 ks: 0.5 kd: 0.5
		// figura:camara direccion: -1 -1 0 0 distanciaPantalla: 2 posicion: 7 4
		// figura:plano posicion: 2 -1 1 1 normal: 1 0 1 0 color: 255 0 0
		// indicerefraccion: 0.8
		// reflectividad: 1 ks: 0.5 kd: 0.5
		// 1 1
	}
}

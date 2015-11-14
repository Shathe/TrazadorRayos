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
		w.w = -direccion.w;
		w.y = -direccion.y;
		w.x = -direccion.x;
		w.z = -direccion.z;
		w.normalize();
		//calculamos u: up x w/|up x w|
		Vector4d up=new Vector4d(1,1,1,0);
		u=crossProduct(up,w);
		u.normalize();
		//calculamos v: w x u
		v=crossProduct(w,u);

	}

	public Vector4d crossProduct(Vector4d a, Vector4d b){
		Vector3d aux=new Vector3d(a.x, a.y, a.z);
		Vector3d bux=new Vector3d(b.x, b.y, b.z);
		aux.cross(aux, bux);
                Vector4d solucion=new Vector4d();
                solucion.x=aux.x; solucion.y=aux.y; solucion.z=aux.z; solucion.w=a.w;
		return new Vector4d(aux.x, aux.y, aux.z, a.w);
	}
	
	public Matrix4d getCambioBase() {
		cambioBase.setRow(0, u);
		cambioBase.setRow(1, v);
		cambioBase.setRow(2, w);
		cambioBase.setRow(3, posicion.x,posicion.y,posicion.z,posicion.w);
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
        
}

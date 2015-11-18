/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trazadorrayos;

import javax.vecmath.Point4d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;

/**
 *
 * @author shathe
 */
public class Interseccion {

	/**
	 * aux==1 con camara, aux==0 con foco
	 * 
	 * @param rayo
	 * @param figura
	 * @param aux
	 * @return
	 */
	public static Point4d intersecta(Rayo rayo, Figura figura, int aux) {
		Point4d interseccion = null;
		if (figura instanceof Esfera) {
			Esfera esfera = (Esfera) figura;
			/*
			 * Ahora lo que se hace es sacar la ecuacion
			 * (pRayo-centroEsfera)²=radioEsfera² y sacar los terminos a,b,c de
			 * la ecuacion cuadrática.
			 */
			Vector4d r1 = rayo.getDireccion();
			double a = r1.dot(rayo.getDireccion());
			Point4d p0 = rayo.getPunto();
			Vector4d ca = new Vector4d();
			ca.sub(p0, esfera.getCentro());
			double b = r1.dot(ca);
			Vector4d ca2 = ca;
			double c = ca.dot(ca2);
			c -= esfera.getRadio() * esfera.getRadio();
			double d = Math.pow(b, 2) - a * c;
			if (d < 0) {
				// no hay interseccion
			}
			else if (d == 0) {
				// un punto de interseccion
				double lambda = -2 * b / (double) (2 * a);
				interseccion = new Point4d();
				interseccion.x += rayo.getPunto().x + lambda
						* rayo.getDireccion().x;
				interseccion.y += rayo.getPunto().y + lambda
						* rayo.getDireccion().y;
				interseccion.z += rayo.getPunto().z + lambda
						* rayo.getDireccion().z;
			}
			else {
				double lambda1 = (-2 * b + Math.sqrt(4 * Math.pow(b, 2) - 4 * a
						* c))
						/ (double) (2 * a);
				double lambda2 = (-2 * b - Math.sqrt(4 * Math.pow(b, 2) - 4 * a
						* c))
						/ (double) (2 * a);
				if (lambda1 < 0 && lambda2 < 0) {

				}
				else if (lambda1 > 0 && lambda2 < 0) {
					interseccion = new Point4d();
					interseccion.x += rayo.getPunto().x + lambda1
							* rayo.getDireccion().x;
					interseccion.y += rayo.getPunto().y + lambda1
							* rayo.getDireccion().y;
					interseccion.z += rayo.getPunto().z + lambda1
							* rayo.getDireccion().z;

				}
				else if (lambda1 > lambda2 && lambda2 > 0) {
					interseccion = new Point4d();
					interseccion.x += rayo.getPunto().x + lambda2
							* rayo.getDireccion().x;
					interseccion.y += rayo.getPunto().y + lambda2
							* rayo.getDireccion().y;
					interseccion.z += rayo.getPunto().z + lambda2
							* rayo.getDireccion().z;
				}
				else if (lambda1 < 0 && lambda2 > 0) {
					interseccion = new Point4d();
					interseccion.x += rayo.getPunto().x + lambda2
							* rayo.getDireccion().x;
					interseccion.y += rayo.getPunto().y + lambda2
							* rayo.getDireccion().y;
					interseccion.z += rayo.getPunto().z + lambda2
							* rayo.getDireccion().z;
				}
				else {
					interseccion = new Point4d();
					interseccion.x += rayo.getPunto().x + lambda1
							* rayo.getDireccion().x;
					interseccion.y += rayo.getPunto().y + lambda1
							* rayo.getDireccion().y;
					interseccion.z += rayo.getPunto().z + lambda1
							* rayo.getDireccion().z;
				}
			}
		}
		else if (figura instanceof Plano) {
			Plano plano = (Plano) figura;

			// intersecta con el plano
			// calculamos el punto de la interseccion
			// numerador(n * a + D)
			// (p1 -a)n
			double numerador = puntoMenosPunto(plano.getPunto(),
					rayo.getPunto()).dot(plano.getNormal(null));
			double landa = 0.0;
			if (numerador != 0.0) {
				// denominador (d * n)
				// d*n
				double denominador = rayo.getDireccion().dot(
						plano.getNormal(null));
				landa = numerador / denominador;
			}
			// evaluamos t en la ecuacion del rayo
			double casos = rayo.getDireccion().dot(plano.getNormal(null));

			if (casos < 0.0) {
				if (landa >= 0.0) {
					interseccion = rayo.evaluar(landa);
				}
				// else no se ve
			}

			// else no intersecta
		}
		else if (figura instanceof Triangulo) {
			Triangulo triangulo = (Triangulo) figura;
			Vector4d N = triangulo.getNormal(null);
			// intersecta con el plano en el que esta el triangulo
			// calculamos el punto de interseccion de ese plano
			// numerador=(p1-a)* n
			Vector4d vectorTriangulo = puntoMenosPunto(triangulo.getPunto1(),
					rayo.getPunto());
			double numerador = vectorTriangulo.dot(N);
			double landa = 0.0;
			if (numerador != 0.0) {
				// denominador=(d * n)
				double denominador = rayo.getDireccion().dot(N);
				landa = numerador / denominador;
			}
			// comprobar si rayo(landa) se encuentra dentro
			// de los parametros del triangulo
			Point4d p = rayo.evaluar(landa);
			// comprobamos que tienen el mismo signo
			// S1=((p2-p1)x(p-p1)) * n

			double S1 = crossProduct(
					puntoMenosPunto(triangulo.getPunto2(),
							triangulo.getPunto1()),
					puntoMenosPunto(p, triangulo.getPunto1())).dot(N);

			// S2=((p3-p2)x(p-p2))* n
			double S2 = crossProduct(
					puntoMenosPunto(triangulo.getPunto3(),
							triangulo.getPunto2()),
					puntoMenosPunto(p, triangulo.getPunto2())).dot(N);
			// S1=((p1-p3)x(p-p3)) * n
			double S3 = crossProduct(
					puntoMenosPunto(triangulo.getPunto1(),
							triangulo.getPunto3()),
					puntoMenosPunto(p, triangulo.getPunto3())).dot(N);

			// else no intersecta con el triangulo
			double casos = rayo.getDireccion().dot(N);
			if (casos < 0.0) {
				if (landa >= 0.0) {
					if ((S1 > 0 && S2 > 0 && S3 > 0)
							|| (S1 < 0 && S2 < 0 && S3 < 0)) {
						// esta dentro del triangulo
						// System.out.println(landa);
						interseccion = rayo.evaluar(landa);
					}
					// else no da en el triangulo
				}
				// else no se ve
			}
			// else no intersecta
		}

		return interseccion;
	}

	public static Vector4d puntoMenosPunto(Point4d v1, Point4d v2) {
		return new Vector4d(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z, 1);
	}

	public static Vector4d crossProduct(Vector4d a, Vector4d b) {
		Vector3d aux = new Vector3d(a.x, a.y, a.z);
		Vector3d bux = new Vector3d(b.x, b.y, b.z);
		aux.cross(aux, bux);
		return new Vector4d(aux.x, aux.y, aux.z, a.w);
	}

}

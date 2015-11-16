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

	public static Point4d intersecta(Rayo rayo, Figura figura, Camara camara) {
		Point4d interseccion = null;
		if (figura instanceof Esfera) {
			Esfera esfera = (Esfera) figura;
			Vector4d direccion = rayo.getDireccion();
			Point4d puntoRayo = rayo.getPunto();
			Point4d centro = esfera.getCentro();
			/*
			 * Ahora lo que se hace es sacar la ecuacion
			 * (pRayo-centroEsfera)²=radioEsfera² y sacar los terminos a,b,c de
			 * la ecuacion cuadrática.
			 */
			double c = Math.pow(puntoRayo.x, 2) + Math.pow(puntoRayo.y, 2)
					+ Math.pow(puntoRayo.z, 2);
			c += Math.pow(centro.x, 2) + Math.pow(centro.y, 2)
					+ Math.pow(centro.z, 2);
			c -= 2 * centro.x * puntoRayo.x - 2 * centro.y * puntoRayo.y - 2
					* centro.z * puntoRayo.z;
			c -= Math.pow(esfera.getRadio(), 2);

			double b = 2 * direccion.x * puntoRayo.x + 2 * direccion.y
					* puntoRayo.y + 2 * direccion.z * puntoRayo.z;
			b -= 2 * centro.x * direccion.x - 2 * centro.y * direccion.y - 2
					* centro.z * direccion.z;

			double a = Math.pow(direccion.x, 2) + Math.pow(direccion.y, 2)
					+ Math.pow(direccion.z, 2);
			if (4 * a * c > 0) {
				double sol1 = (b * b + Math.sqrt(4 * a * c)) / (2 * a);
				double sol2 = (b * b - Math.sqrt(4 * a * c)) / (2 * a);
				Point4d primero = new Point4d();
				// Ahora calculas los dos puntos que intersecta si esque hay dos
				primero.x = sol1 * direccion.x + puntoRayo.x;
				primero.y = sol1 * direccion.y + puntoRayo.y;
				primero.z = sol1 * direccion.z + puntoRayo.z;
				Point4d segundo = new Point4d();
				segundo.x = sol2 * direccion.x + puntoRayo.x;
				segundo.y = sol2 * direccion.y + puntoRayo.y;
				segundo.z = sol2 * direccion.z + puntoRayo.z;
				// El de menor distancia con la camara es el que se tiene que
				// devolver
				if (sol1 > 0 && sol2 > 0) {
					if (camara.getPosicion().distanceSquared(primero) <= camara
							.getPosicion().distanceSquared(primero)) {
						interseccion = primero;
					}
					else {
						interseccion = segundo;
					}
				}
				else if (sol1 > 0 && sol2 < 0) {
					interseccion = primero;
				}
				// else no se ve
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
			double landa=0.0;
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
					if ((S1 >= 0 && S2 >= 0 && S3 >= 0) || (S1 <= 0 && S2 <= 0
							&& S3 <= 0)) {
						// esta dentro del triangulo
						interseccion = rayo.evaluar(landa);
					}
					// else no da en el triangulo
				}
				// else no se ve
			}
			else if(casos > 0.0){
				triangulo.getNormal(null).negate();
				N.negate();
				if (landa >= 0.0) {
					if ((S1 >= 0 && S2 >= 0 && S3 >= 0) || (S1 <= 0 && S2 <= 0 && S3 <= 0)) {
						// esta dentro del triangulo
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

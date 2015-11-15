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
				if (camara.getPosicion().distanceSquared(primero) <= camara
						.getPosicion().distanceSquared(primero)) {
					interseccion = primero;
				}
				else {
					interseccion = segundo;
				}

			}

		}
		else if (figura instanceof Plano) {
			Plano plano = (Plano) figura;

			// intersecta con el plano
			// calculamos el punto de la interseccion
			// numerador(n * a + D)
			double numerador = plano.getNormal().x * rayo.getPunto().x
					+ plano.getNormal().y * rayo.getPunto().y
					+ plano.getNormal().z * rayo.getPunto().z + plano.getD();
			// denominador (d * n)
			double denominador = plano.getNormal().x * rayo.getDireccion().x
					+ plano.getNormal().y * rayo.getDireccion().y
					+ plano.getNormal().z * rayo.getDireccion().z;
			double landa = -(numerador / denominador);
			// evaluamos t en la ecuacion del rayo
			double casos = vectorPorVector(plano.getNormal(),
					rayo.getDireccion());
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
			// intersecta con el plano en el que esta el triangulo
			// calculamos el punto de interseccion de ese plano
			// numerador=(p1-a)* n
			Point4d puntoTriangulo = puntoMenosPunto(triangulo.getPunto1(),
					rayo.getPunto());
			double numerador = vectorPorVector(puntoTriangulo,
					triangulo.getNormal());
			// denominador=(d * n)
			double denominador = vectorPorVector(rayo.getDireccion(),
					triangulo.getNormal());
			double landa = numerador / denominador;
			// comprobar si rayo(landa) se encuentra dentro
			// de los parametros del triangulo
			Point4d p = rayo.evaluar(landa);
			// comprobamos que tienen el mismo signo
			// S1=((p2-p1)x(p-p1)) * n

			double S1 = vectorPorVector(
					puntoMenosPunto(triangulo.getPunto2(),
							triangulo.getPunto1()),
					puntoMenosPunto(p, triangulo.getPunto1()));
			// S2=((p3-p2)x(p-p2))* n
			double S2 = vectorPorVector(
					puntoMenosPunto(triangulo.getPunto3(),
							triangulo.getPunto2()),
					puntoMenosPunto(p, triangulo.getPunto2()));
			// S1=((p1-p3)x(p-p3)) * n
			double S3 = vectorPorVector(
					puntoMenosPunto(triangulo.getPunto1(),
							triangulo.getPunto3()),
					puntoMenosPunto(p, triangulo.getPunto2()));

			// else no intersecta con el triangulo
			double casos = vectorPorVector(triangulo.getNormal(),
					rayo.getDireccion());
			if (casos < 0.0) {
				if (landa >= 0.0) {
					if (S1 >= 0 && S2 >= 0 && S3 >= 0 || S1 < 0 && S2 < 0
							&& S3 < 0) {
						// esta dentro del triangulo
						interseccion = rayo.evaluar(landa);
					}
					//else no da en el triangulo
				}
				// else no se ve
			}
			// else no intersecta
		}

		return interseccion;
	}

	public static double vectorPorVector(Vector4d v1, Vector4d v2) {
		return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
	}

	public static double vectorPorVector(Point4d v1, Vector4d v2) {
		return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
	}

	public static double vectorPorVector(Point4d v1, Point4d v2) {
		return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
	}

	public static Point4d puntoMenosPunto(Point4d v1, Point4d v2) {
		return new Point4d(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z, 1);
	}

	public static Point4d crossProduct(Point4d a, Point4d b) {
		Vector3d aux = new Vector3d(a.x, a.y, a.z);
		Vector3d bux = new Vector3d(b.x, b.y, b.z);
		aux.cross(aux, bux);
		return new Point4d(aux.x, aux.y, aux.z, a.w);
	}
}

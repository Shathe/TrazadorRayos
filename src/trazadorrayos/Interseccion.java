/**
 * Iñigo Alonso - 665959
 * Alejandro Dieste - 541892
 */
package trazadorrayos;

import javax.vecmath.Point4d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;

/**
 * Clase que las intersecciones de las figuras de la escena, con los rayos.
 *
 */
public class Interseccion {

	/**
	 * Dado un rayo y una figura, determina si hay interseccion. Si la hay,
	 * devuelve el punto de la interseccion, si no, devuelve null
	 * 
	 */
	public static Point4d intersecta(Rayo rayo, Figura figura) {
		Point4d interseccion = null;
		//si la figura es una esfera
		if (figura instanceof Esfera) {
			Esfera esfera = (Esfera) figura;
			/*
			 * ecuacion de la esfera:
			 * (p-centroEsfera)^2=radioEsfera^2 y sacar los terminos a,b,c de
			 * la ecuacion cuadrática.
			 */
			// A = d . d
			double A = rayo.getDireccion().dot(rayo.getDireccion());
			Point4d p = rayo.getPunto();
			Vector4d a = new Vector4d();
			// ( a - c )
			a.sub(p, esfera.getCentro());
			// B = d . ( a - c )
			double B = rayo.getDireccion().dot(a);
			// ( a - c ) . ( a - c ) 
			double C = a.dot(a);
			// ( a - c ) . ( a - c ) - r ^2
			C -= esfera.getRadio() * esfera.getRadio();
			// d = B ^ 2 - A * C
			double D = Math.pow(B, 2) - A * C;
			if (D < 0) {
				// no hay interseccion
			}
			else if (D == 0) {
				// hay una interseccion
				// lambda = -2B / 2A
				double lambda = -2 * B / (double) (2 * A);
				interseccion = new Point4d();
				interseccion.x += rayo.getPunto().x + lambda
						* rayo.getDireccion().x;
				interseccion.y += rayo.getPunto().y + lambda
						* rayo.getDireccion().y;
				interseccion.z += rayo.getPunto().z + lambda
						* rayo.getDireccion().z;
			}
			else {
				// hay dos intersecciones
				// -2B	+- (( 4B^2-4AC)^1/2)/2A
				double lambda1 = (-2 * B + Math.sqrt(4 * Math.pow(B, 2) - 4 * A
						* C))
						/ (double) (2 * A);
				double lambda2 = (-2 * B - Math.sqrt(4 * Math.pow(B, 2) - 4 * A
						* C))
						/ (double) (2 * A);
				if (lambda1 < 0 && lambda2 < 0) {
					//intersecciones entre pantalla y ojo
				}
				else if (lambda1 > 0 && lambda2 < 0) {
					//rayo(lambda1) visible, pero rayo(lambda2) no
					interseccion = new Point4d();
					interseccion.x += rayo.getPunto().x + lambda1
							* rayo.getDireccion().x;
					interseccion.y += rayo.getPunto().y + lambda1
							* rayo.getDireccion().y;
					interseccion.z += rayo.getPunto().z + lambda1
							* rayo.getDireccion().z;
					interseccion=rayo.evaluar(lambda1);

				}
				else if (lambda1 > lambda2 && lambda2 > 0) {
					//se ven las dos intersecciones, y lambda 2 esta mas cerca
					interseccion = new Point4d();
					interseccion.x += rayo.getPunto().x + lambda2
							* rayo.getDireccion().x;
					interseccion.y += rayo.getPunto().y + lambda2
							* rayo.getDireccion().y;
					interseccion.z += rayo.getPunto().z + lambda2
							* rayo.getDireccion().z;
				}
				else if (lambda1 < 0 && lambda2 > 0) {
					//rayo(lambda2) visible, pero rayo(lambda1) no
					interseccion = new Point4d();
					interseccion.x += rayo.getPunto().x + lambda2
							* rayo.getDireccion().x;
					interseccion.y += rayo.getPunto().y + lambda2
							* rayo.getDireccion().y;
					interseccion.z += rayo.getPunto().z + lambda2
							* rayo.getDireccion().z;
				}
				else {
					//se ven las dos intersecciones, y lambda 1 esta mas cerca
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
			double numerador = Operaciones.sub(plano.getPunto(),
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
			Vector4d vectorTriangulo = Operaciones.sub(triangulo.getPunto1(),
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

			double S1 = Operaciones.crossProduct(
					Operaciones.sub(triangulo.getPunto2(),
							triangulo.getPunto1()),
							Operaciones.sub(p, triangulo.getPunto1())).dot(N);

			// S2=((p3-p2)x(p-p2))* n
			double S2 = Operaciones.crossProduct(
					Operaciones.sub(triangulo.getPunto3(),
							triangulo.getPunto2()),
							Operaciones.sub(p, triangulo.getPunto2())).dot(N);
			// S1=((p1-p3)x(p-p3)) * n
			double S3 = Operaciones.crossProduct(
					Operaciones.sub(triangulo.getPunto1(),
							triangulo.getPunto3()),
							Operaciones.sub(p, triangulo.getPunto3())).dot(N);

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



}

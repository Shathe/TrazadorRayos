/**
 * Iñigo Alonso - 665959
 * Alejandro Dieste - 541892
 */
package trazadorrayos;

import java.awt.Color;
import java.util.ArrayList;

import javax.vecmath.Point4d;
import javax.vecmath.Vector4d;

public class OperacionesEscena {

	public static Figura FiguraMasCercana(ArrayList<Figura> figuras,
			Point4d punto, Rayo rayo, Camara camara, Figura desde) {
		Figura figura = null;
		double distanciaMenor = Double.MAX_VALUE;
		for (int i = 0; i < figuras.size(); i++) {
			Figura siguienteFigura = figuras.get(i);
			if (desde != siguienteFigura) {
				Point4d puntoInterseccion = Interseccion.intersecta(rayo,
						siguienteFigura);
				if (puntoInterseccion != null) {
					double distancia = punto.distanceSquared(puntoInterseccion);
					if (distancia < distanciaMenor) {
						distanciaMenor = distancia;
						figura = siguienteFigura;
					}

				}
			}
		}

		return figura;
	}

	public static Color colorPuntoPantalla(Point4d puntoPixelPantalla,
			Escena escena, int maxDepth, double minIntensity) {
		Color color = new Color(0, 0, 0);

		/*
		 * Ahora se crea un rayo que pase por el punto de la pantalla y con
		 * direccion (puntoCamara-puntopantalla)
		 */
		Vector4d direccion = new Vector4d();
		direccion.x = (puntoPixelPantalla.x - escena.getCamara().getPosicion().x);
		direccion.y = (puntoPixelPantalla.y - escena.getCamara().getPosicion().y);
		direccion.z = (puntoPixelPantalla.z - escena.getCamara().getPosicion().z);
		direccion.w = 0;
		Rayo rayo = new Rayo(direccion, escena.getCamara().getPosicion(),
				escena.getFoco().getColor());
		/*
		 * Ahora hay que ver si el rayo intersecta con alguno de los objetos de
		 * la escen, y si lo hace, quedarnos unicamente con el objeto mas
		 * cercano a pa pantalla
		 */
		color = colorDesdeRayo(escena, rayo, maxDepth, minIntensity, null,
				false);
		return color;
	}

	/*
	 * Devuelve el color que devuelve un rayo al golpear una figura en un punto
	 */
	public static Color colorDesdeRayo(Escena escena, Rayo rayo, int MaxDepth,
			double minIntensity, Figura desde, boolean refractadoParaSalir) {
		Color color = null;
		Figura figura = OperacionesEscena.FiguraMasCercana(escena.getFiguras(),
				rayo.getPunto(), rayo, escena.getCamara(), desde);
		if (figura != null && Interseccion.intersecta(rayo, figura) != null) {
			/*
			 * Ahora tenemos que intersecta con la figura y tenemos que obtener
			 * su color
			 */
			Point4d punto = Interseccion.intersecta(rayo, figura);
			double reflec = figura.getIndiceReflectividad();
			double refrac = 0;
			if (figura.getIndiceRefraccion() != 0)
				refrac = 1 / figura.getIndiceRefraccion();// suponemos que el
															// exterior es 1
			/*
			 * Ahora se calcula la direccion del angulo refractado y del angulo
			 * reflejado
			 */
			Vector4d normal = figura.getNormal(punto);
			normal.normalize();
			rayo.getDireccion().normalize();
			Vector4d reflejado = rayoReflejado(normal, rayo);
			Vector4d direccionRayo = new Vector4d(rayo.getDireccion());
			direccionRayo.negate();
			Vector4d refractado = rayoRefractado(refrac, normal, rayo);
			/*
			 * Ahora tienes el angulo refractado y el angulo reflejado, ahora
			 * para calcular el color, primero tienes que mirar el color que
			 * tiene tu figura y la intensidad del rayo que ha impacado contigo,
			 * y luego sumarle el color que te devuelvan los dos rayos que salen
			 * de ese punto (reflejado y refractado) y normalizar
			 */

			/*
			 * primero tienes que mirar si desde ese punto hasta el foco no hay
			 * inersecciones, si las hay, devuelves el llamado primer modelo de
			 * iliminacion, es decir solo la luz ambiental, sino hay
			 * intereseccion ya pasas al tercer modelo
			 */

			double noVisible = interseccionAFocoTapado(punto, escena.getFoco(),
					escena.getFiguras(), figura);

			if (refractadoParaSalir) {
				double intensidadRefractadoR = (1 - reflec)
						* escena.getFoco().getColor().getRed();
				double intensidadRefractadoG = (1 - reflec)
						* escena.getFoco().getColor().getGreen();
				double intensidadRefractadoB = (1 - reflec)
						* escena.getFoco().getColor().getBlue();
				Color colorRayoRefractado = new Color(
						(int) intensidadRefractadoR,
						(int) intensidadRefractadoG,
						(int) intensidadRefractadoB);
				Rayo rayoRefractado = new Rayo(refractado, punto,
						colorRayoRefractado);
				return colorDesdeRayo(escena, rayoRefractado, MaxDepth--,
						minIntensity, figura, false);
			}
			else {
				if (noVisible == 1) {
					double intensidad = escena.getFoco()
							.getIntensidadAmbiente();
					int red = (int) (intensidad * figura.kd.getRed());
					int blue = (int) (intensidad * figura.kd.getBlue());
					int green = (int) (intensidad * figura.kd.getGreen());
					color = new Color(red, green, blue);

				}
				else {
					Intensidad intensidadPunto = new Intensidad();
					//calculamos la intensidad en el punto, en RGB
					intensidadPunto.calcularIntensidadPunto(noVisible, punto,
							escena, figura);
					// intensidad reflejada
					int intensidadReflejadaR = (int) (reflec * intensidadPunto.IpartDifR);
					int intensidadReflejadaG = (int) (reflec * intensidadPunto.IpartDifG);
					int intensidadReflejadaB = (int) (reflec * intensidadPunto.IpartDifB);
					Color colorRayoReflejado = new Color(intensidadReflejadaR,
							intensidadReflejadaG, intensidadReflejadaB);
					Rayo rayoReflejado = new Rayo(reflejado, punto,
							colorRayoReflejado);
					// intensidad refractada
					double intensidadRefractadoR = (1 - reflec)
							* escena.getFoco().getColor().getRed();
					double intensidadRefractadoG = (1 - reflec)
							* escena.getFoco().getColor().getGreen();
					double intensidadRefractadoB = (1 - reflec)
							* escena.getFoco().getColor().getBlue();
					Color colorRayoRefractado = new Color(
							(int) intensidadRefractadoR,
							(int) intensidadRefractadoG,
							(int) intensidadRefractadoB);
					Rayo rayoRefractado = new Rayo(refractado, punto,
							colorRayoRefractado);

					//si hay suficiente intensidad reflejada, lanzamos el rayo
					if (MaxDepth > 0
							&& (intensidadReflejadaR + intensidadReflejadaG + intensidadReflejadaB) > minIntensity) {
						Color colorReflejado = colorDesdeRayo(escena,
								rayoReflejado, MaxDepth--, minIntensity,
								figura, false);
						intensidadPunto.red += colorReflejado.getRed();
						intensidadPunto.blue += colorReflejado.getBlue();
						intensidadPunto.green += colorReflejado.getGreen();
					}
					//si hay suficiente intensidad refractada, lanzamos el rayo
					if (MaxDepth > 0
							&& (intensidadRefractadoR + intensidadRefractadoB + intensidadRefractadoG) > minIntensity) {
						boolean esperarRefraccion = false;
						if (figura instanceof Esfera) {
							esperarRefraccion = true;
						}
						Color colorRefractado = colorDesdeRayo(escena,
								rayoRefractado, MaxDepth--, minIntensity,
								figura, esperarRefraccion);
						intensidadPunto.red += colorRefractado.getRed();
						intensidadPunto.blue += colorRefractado.getBlue();
						intensidadPunto.green += colorRefractado.getGreen();

					}

					color = normalizarColor(intensidadPunto.red, intensidadPunto.green,
							intensidadPunto.blue);

				}

			}
		}
		else {
			// No se intersecta con nada
			color = new Color(0, 0, 0);
		}
		return color;
	}

	public static Color normalizarColor(int red, int green, int blue) {

		int mayor = 0;
		if (blue > mayor) mayor = blue;
		if (red > mayor) mayor = red;
		if (green > mayor) mayor = green;
		if (mayor > 255) {

			double indiceReduccion = (double) mayor / 255;
			indiceReduccion = Math.sqrt(indiceReduccion);
			red = (int) (red / indiceReduccion);
			green = (int) (green / indiceReduccion);
			blue = (int) (blue / indiceReduccion);
		}

		if (red > 255) red = 255;
		if (green > 255) green = 255;
		if (blue > 255) blue = 255;
		// System.out.println(red+" "+green+" "+blue);
		return new Color(red, green, blue);

	}

	/**
	 * Devuelve el rayo reflejado del rayo incidente
	 * 
	 * @param normal
	 * @param rayo
	 * @return
	 */
	public static Vector4d rayoReflejado(Vector4d normal, Rayo rayo) {

		Vector4d reflejado = new Vector4d();
		// 2*(V*N)N
		double aux = 2 * normal.dot(rayo.getDireccion());
		reflejado.x = aux * normal.x;
		reflejado.y = aux * normal.y;
		reflejado.z = aux * normal.z;
		reflejado.w = normal.w;
		// V-2*(V*N)N
		reflejado.x = rayo.getDireccion().x - reflejado.x;
		reflejado.z = rayo.getDireccion().z - reflejado.z;
		reflejado.y = rayo.getDireccion().y - reflejado.y;
		reflejado.w = rayo.getDireccion().w - reflejado.w;
		reflejado.normalize();
		return reflejado;
	}

	/**
	 * Devuelve el rayo refractado del rayo incidente
	 * 
	 * @param refrac
	 * @param normal
	 * @param rayo
	 * @return
	 */
	public static Vector4d rayoRefractado(double refrac, Vector4d normal,
			Rayo rayo) {
		Vector4d refractado = new Vector4d();
		Vector4d direccionRayo = new Vector4d(rayo.getDireccion());
		direccionRayo.negate();

		double auxR = (refrac * (normal.dot(direccionRayo)))
				- Math.sqrt(1 - (refrac * refrac * (1 - (normal
						.dot(direccionRayo) * normal.dot(direccionRayo)))));

		refractado.x = auxR * normal.x - refrac * direccionRayo.x;
		refractado.y = auxR * normal.y - refrac * direccionRayo.y;
		refractado.z = auxR * normal.z - refrac * direccionRayo.z;
		refractado.w = 0;
		refractado.normalize();
		return refractado;
	}

	/**
	 * Funcion que devuelve true si el rayo que sale del foco intersecta con
	 * algo antes de llegar al punto, si no devuelve false
	 * 
	 * @param punto
	 * @param foco
	 * @param figuras
	 * @return
	 */
	public static double interseccionAFocoTapado(Point4d punto, Foco foco,
			ArrayList<Figura> figuras, Figura figura) {
		double intersecta = 0;
		Vector4d rayoAlFoco = new Vector4d();
		rayoAlFoco.x = foco.getPosicion().x - punto.x;
		rayoAlFoco.y = foco.getPosicion().y - punto.y;
		rayoAlFoco.z = foco.getPosicion().z - punto.z;
		rayoAlFoco.w = 0;
		rayoAlFoco.normalize();
		double cosenoDif = rayoAlFoco.dot(figura.getNormal(punto))
				/ figura.getNormal(punto).length() / rayoAlFoco.length();

		if (cosenoDif < 0) {
			// solo se ve si la figura refracta
			intersecta = figura.getIndiceReflectividad();
		}

		for (int i = 0; i < figuras.size() && intersecta != 1; i++) {

			Rayo rayoPuntoFoco = new Rayo(rayoAlFoco, punto);

			Point4d puntoInterseccion = Interseccion.intersecta(rayoPuntoFoco,
					figuras.get(i));
			if (puntoInterseccion != null) {
				double distanciaPuntoFoco = punto.distanceSquared(foco
						.getPosicion());
				double distanciaInterseccionFoco = puntoInterseccion
						.distanceSquared(foco.getPosicion());
				if (distanciaInterseccionFoco < distanciaPuntoFoco
						&& intersecta < figuras.get(i).getIndiceReflectividad()) {
					intersecta = figuras.get(i).getIndiceReflectividad();
				}
			}

		}

		return intersecta;
	}
}
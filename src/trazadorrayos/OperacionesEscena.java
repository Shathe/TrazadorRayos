/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trazadorrayos;

import java.awt.Color;
import java.util.ArrayList;

import javax.vecmath.Point4d;
import javax.vecmath.Vector4d;

/**
 *
 * @author shathe
 */
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
		Rayo rayo = new Rayo(direccion, escena.getCamara().getPosicion(), new Color(255, 255,
				255), 1);
		/*
		 * Ahora hay que ver si el rayo intersecta con alguno de los objetos de
		 * la escen, y si lo hace, quedarnos unicamente con el objeto mas
		 * cercano a pa pantalla
		 */
		color = colorDesdeRayo(escena, rayo, maxDepth, minIntensity, null);
                
		return color;

	}

	/*
	 * Devuelve el color que devuelve un rayo al golpear una figura en un punto
	 */
	public static Color colorDesdeRayo(Escena escena, Rayo rayo, int MaxDepth,
			double minIntensity, Figura desde) {
            			
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
			double refrac = figura.getIndiceRefraccion();

			/*
			 * Ahora se calcula la direccion del angulo refractado y del angulo
			 * reflejado
			 */

			Vector4d refractado = new Vector4d();

			Vector4d normal = figura.getNormal(punto);

			// reflejado => V-2*(V*N)N

			// aux=2*(V*N)
			normal.normalize();
			rayo.getDireccion().normalize();
			double aux = 2 * normal.dot(rayo.getDireccion());
			Vector4d reflejado = new Vector4d();
			// 2*(V*N)N
			reflejado.x = aux * normal.x;
			reflejado.y = aux * normal.y;
			reflejado.z = aux * normal.z;
			reflejado.w = normal.w;
			// V-2*(V*N)N
			reflejado.x = normal.x - reflejado.x;
			reflejado.z = normal.z - reflejado.z;
			reflejado.y = normal.y - reflejado.y;
			reflejado.w = normal.w - reflejado.w;
			reflejado.normalize();
			// ahora calculas el angulo reflejado
			// T=(Iref*(normal*Rayo)-Raiz(1-Iref²(1-(normal*rayo)²)))
			// *normal-Iref*rayo
			double auxR = refrac
					* (normal.dot(rayo.getDireccion()))
					- Math.sqrt(1
							- refrac
							* refrac
							* (1 - normal.dot(rayo.getDireccion())
									* normal.dot(rayo.getDireccion())));

			refractado.x = auxR * normal.x - refrac * rayo.getDireccion().x;
			refractado.y = auxR * normal.y - refrac * rayo.getDireccion().y;
			refractado.z = auxR * normal.z - refrac * rayo.getDireccion().z;
			refractado.w = auxR * normal.w - refrac * rayo.getDireccion().w;
			refractado.normalize();

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

			boolean noVisible = interseccionAFocoTapado(punto,
					escena.getFoco(), escena.getFiguras(), figura);

			if (noVisible) {
				double intensidad = escena.getFoco().getIntensidadAmbiente();
				int red = (int) (intensidad * figura.kd.getRed());
				int blue = (int) (intensidad * figura.kd.getBlue());
				int green = (int) (intensidad * figura.kd.getGreen());
				color = new Color(red, green, blue);

			}
			else {
				// Calculas la intensidad en tu punto
				Vector4d rayoAlOjo = new Vector4d();
				rayoAlOjo.x = escena.getCamara().getPosicion().x - punto.x;
				rayoAlOjo.y = escena.getCamara().getPosicion().y - punto.y;
				rayoAlOjo.z = escena.getCamara().getPosicion().z - punto.z;
				rayoAlOjo.w = 0;
				Vector4d rayoAlFoco = new Vector4d();
				rayoAlFoco.x = escena.getFoco().getPosicion().x - punto.x;
				rayoAlFoco.y = escena.getFoco().getPosicion().y - punto.y;
				rayoAlFoco.z = escena.getFoco().getPosicion().z - punto.z;
				rayoAlFoco.w = 0;
                                rayoAlFoco.normalize();
                                rayoAlOjo.normalize();
				// ambiental
				double intensidadR = escena.getFoco().getIntensidadAmbiente()* figura.kd.getRed();
				double intensidadG = escena.getFoco().getIntensidadAmbiente()* figura.kd.getGreen();
				double intensidadB = escena.getFoco().getIntensidadAmbiente()* figura.kd.getBlue();
				// difusa
				double cosenoDif = rayoAlFoco.dot(normal) / normal.length()
						/ rayoAlFoco.length();
				double Ipart2R = figura.kd.getRed() * rayo.getIntensidad() * cosenoDif;
				double Ipart2G = figura.kd.getGreen() * rayo.getIntensidad() * cosenoDif;
				double Ipart2B = figura.kd.getBlue() * rayo.getIntensidad() * cosenoDif;
				// especular
				double cosenoEsp = reflejado.dot(rayoAlOjo) / rayoAlOjo.length()
						/ reflejado.length();
                                cosenoEsp=Math.pow(cosenoEsp,150);
				double Ipart3R = figura.ks.getRed() * rayo.getIntensidad() * cosenoEsp;
				double Ipart3G = figura.ks.getGreen() * rayo.getIntensidad() * cosenoEsp;
				double Ipart3B = figura.ks.getBlue() * rayo.getIntensidad() * cosenoEsp;

				intensidadR += Ipart2R + Ipart3R;
				intensidadG += Ipart2G + Ipart3G;
				intensidadB += Ipart2B + Ipart3B;
				int red = (int) intensidadR;
				int green = (int) intensidadG ;
				int blue = (int) intensidadB ;

				Rayo rayoReflejado = new Rayo(reflejado, punto, color,
						rayo.getIntensidad() * reflec);
				Rayo rayoRefractado = new Rayo(refractado, punto, color,
						rayo.getIntensidad() * (1 - reflec));

				if (MaxDepth > 0
						&& rayoReflejado.getIntensidad() > minIntensity) {
					Color colorReflejado = colorDesdeRayo(escena,
							rayoReflejado, MaxDepth--, minIntensity, figura);
					red += colorReflejado.getRed();
					blue += colorReflejado.getBlue();
					green += colorReflejado.getGreen();
				}
				if (MaxDepth > 0
						&& rayoRefractado.getIntensidad() > minIntensity) {
					Color colorRefractado = colorDesdeRayo(escena,
							rayoRefractado, MaxDepth--, minIntensity, figura);
					red += colorRefractado.getRed();
					blue += colorRefractado.getBlue();
					green += colorRefractado.getGreen();

				}
				color = normalizarColor(red, green, blue);

			}

		}
		else {
			// No se intersecta con nada
			color = new Color(0, 0, 0);
		}
		return color;
	}

	public static Color normalizarColor(int red, int green, int blue) {
		/*/int mayor = 0;
		if (blue > mayor) mayor = blue;
		if (red > mayor) mayor = red;
		if (green > mayor) mayor = green;
		if (mayor <= 255) {
			System.out.println(red);
			return new Color(red, green, blue);
		}
		else {
			double indiceReduccion = (double) mayor / 255;
			double redreducido = red / indiceReduccion;
			double greenreducido = green / indiceReduccion;
			double bluereducido = blue / indiceReduccion;
			return new Color((int) redreducido, (int) greenreducido,
					(int) bluereducido);
		}*/
            if(red>255)red=255;
            if(green>255)green=255;
            if(blue>255)blue=255;
            return new Color(red,green,blue);

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
	public static boolean interseccionAFocoTapado(Point4d punto, Foco foco,
			ArrayList<Figura> figuras, Figura figura) {
		boolean intersecta = false;
                		Vector4d rayoAlFoco = new Vector4d();
				rayoAlFoco.x =foco.getPosicion().x - punto.x;
				rayoAlFoco.y = foco.getPosicion().y - punto.y;
				rayoAlFoco.z = foco.getPosicion().z - punto.z;
				rayoAlFoco.w = 0;
                                rayoAlFoco.normalize();
                                double cosenoDif = rayoAlFoco.dot(figura.getNormal(punto)) / 
                                        figura.getNormal(punto).length() / rayoAlFoco.length();
                                if(cosenoDif>0){
                                    	for (int i = 0; i < figuras.size() && !intersecta; i++) {

			 
				Vector4d direccionRayo = Interseccion.puntoMenosPunto(punto,
						foco.getPosicion());
				Rayo rayoPuntoFoco = new Rayo(direccionRayo, punto);
				Point4d puntoInterseccion = Interseccion.intersecta(
						rayoPuntoFoco, figuras.get(i));
				if (puntoInterseccion != null) {
					double distanciaPuntoFoco = punto.distanceSquared(foco
							.getPosicion());
					double distanciaInterseccionFoco = puntoInterseccion
							.distanceSquared(foco.getPosicion());
					if (distanciaInterseccionFoco < distanciaPuntoFoco) {
						intersecta = true;
					}
				}
			
		}
                                }
                              
                                else{
                                    intersecta=true;
                                }
		return intersecta;
	}
}
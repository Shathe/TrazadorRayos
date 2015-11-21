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
		color = colorDesdeRayo(escena, rayo, maxDepth, minIntensity, null,false);

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
                        if(figura.getIndiceRefraccion()!=0) refrac = 1/figura.getIndiceRefraccion();//suponemos que el exterior es 1

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
			reflejado.x = rayo.getDireccion().x - reflejado.x;
			reflejado.z = rayo.getDireccion().z - reflejado.z;
			reflejado.y = rayo.getDireccion().y - reflejado.y;
			reflejado.w = rayo.getDireccion().w - reflejado.w;
			reflejado.normalize();
			// ahora calculas el angulo reflejado
			// T=(Iref*(normal*Rayo)-Raiz(1-Iref²(1-(normal*rayo)²)))
			// *normal-Iref*rayo
                        Vector4d direccionRayo=new Vector4d(rayo.getDireccion());
                        direccionRayo.negate();
                       
			double auxR = (refrac
					* (normal.dot(direccionRayo)))
					- Math.sqrt(1
							- (refrac
							* refrac
							* (1 - (normal.dot(direccionRayo)
									* normal.dot(direccionRayo)))));

			refractado.x = auxR * normal.x - refrac * direccionRayo.x;
			refractado.y = auxR * normal.y - refrac * direccionRayo.y;
			refractado.z = auxR * normal.z - refrac * direccionRayo.z;
			refractado.w = 0;
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

			double noVisible = interseccionAFocoTapado(punto,
					escena.getFoco(), escena.getFiguras(), figura);

                        if(refractadoParaSalir){
                                    double intensidadRefractadoR=(1-reflec)*escena.getFoco().getColor().getRed();
                                      double  intensidadRefractadoG=(1-reflec)*escena.getFoco().getColor().getGreen();
                                      double intensidadRefractadoB=(1-reflec)*escena.getFoco().getColor().getBlue();
                                      Color colorRayoRefractado=new Color((int)intensidadRefractadoR,
                                      (int)intensidadRefractadoG,(int)intensidadRefractadoB);
                                      Rayo rayoRefractado = new Rayo(refractado, punto, colorRayoRefractado);
                            return  colorDesdeRayo(escena, rayoRefractado,
                                      MaxDepth--, minIntensity, figura,false);
                        }
                        else{
                            if (noVisible==1) {
                                    double intensidad = escena.getFoco().getIntensidadAmbiente();
                                    int red = (int) (intensidad * figura.kd.getRed());
                                    int blue = (int) (intensidad * figura.kd.getBlue());
                                    int green = (int) (intensidad * figura.kd.getGreen());
                                    color = new Color(red, green, blue);

                            }
                            else {
                                double TapanObjetosTranslucidos=1-noVisible;
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
                                    double intensidadR = escena.getFoco().getIntensidadAmbiente()
                                                    * figura.kd.getRed();
                                    double intensidadG = escena.getFoco().getIntensidadAmbiente()
                                                    * figura.kd.getGreen();
                                    double intensidadB = escena.getFoco().getIntensidadAmbiente()
                                                    * figura.kd.getBlue();
                                    // difusa
                                    double cosenoDif = rayoAlFoco.dot(normal) / normal.length()
                                                    / rayoAlFoco.length();
                                    //Caso en el que da negativo debido a que el objeto refracta por dentro 
                                    cosenoDif=Math.abs(cosenoDif);
                                    double IpartDifR = figura.kd.getRed()
                                                    * escena.getFoco().getColor().getRed() / 255
                                                    * cosenoDif*TapanObjetosTranslucidos;
                                    double IpartDifG = figura.kd.getGreen()
                                                    * escena.getFoco().getColor().getGreen() / 255
                                                    * cosenoDif*TapanObjetosTranslucidos;
                                    double IpartDifB = figura.kd.getBlue()
                                                    * escena.getFoco().getColor().getBlue() / 255
                                                    * cosenoDif*TapanObjetosTranslucidos;
                                    // especular
                                    rayoAlOjo.normalize();
                                    double aux2 = 2 * normal.dot(rayoAlFoco);

                                    Vector4d reflejado2 = new Vector4d();
                                    // 2*(l*N)N
                                    reflejado2.x = aux2 * normal.x;
                                    reflejado2.y = aux2 * normal.y;
                                    reflejado2.z = aux2 * normal.z;
                                    reflejado2.w = normal.w;

                                    // 2*(V*N)N - l
                                    reflejado2.x = rayoAlFoco.x - reflejado2.x;
                                    reflejado2.z = rayoAlFoco.z - reflejado2.z;
                                    reflejado2.y = rayoAlFoco.y - reflejado2.y;
                                    reflejado2.w = rayoAlFoco.w - reflejado2.w;
                                    reflejado2.normalize();
                                    reflejado2.negate();
                                    double cosenoEsp = reflejado2.dot(rayoAlOjo)
                                                    / rayoAlOjo.length() / reflejado2.length();

                                    if (cosenoEsp < 0) cosenoEsp = 0;

                                    cosenoEsp = Math.pow(cosenoEsp, 150);
                                    double IpartEspR = figura.ks.getRed()
                                                    * escena.getFoco().getColor().getRed() / 255
                                                    * cosenoEsp*TapanObjetosTranslucidos;
                                    double IpartEspG = figura.ks.getGreen()
                                                    * escena.getFoco().getColor().getGreen() / 255
                                                    * cosenoEsp*TapanObjetosTranslucidos;
                                    double IpartEspB = figura.ks.getBlue()
                                                    * escena.getFoco().getColor().getBlue() / 255
                                                    * cosenoEsp*TapanObjetosTranslucidos;

                                    intensidadR += IpartDifR + IpartEspR;
                                    intensidadG += IpartDifG + IpartEspG;
                                    intensidadB += IpartDifB + IpartEspB;
                                    int red = (int) (intensidadR);
                                    int green = (int) (intensidadG);
                                    int blue = (int) (intensidadB);

                                    int intensidadReflejadaR = (int)(reflec * IpartDifR);
                                    int intensidadReflejadaG = (int)(reflec * IpartDifG);
                                    int intensidadReflejadaB =(int)( reflec * IpartDifB);
                                    Color colorRayoReflejado = new Color(
                                                     intensidadReflejadaR, intensidadReflejadaG,
                                                    intensidadReflejadaB);
                                    Rayo rayoReflejado = new Rayo(reflejado, punto,
                                                    colorRayoReflejado);


                                      double intensidadRefractadoR=(1-reflec)*escena.getFoco().getColor().getRed();
                                      double  intensidadRefractadoG=(1-reflec)*escena.getFoco().getColor().getGreen();
                                      double intensidadRefractadoB=(1-reflec)*escena.getFoco().getColor().getBlue();
                                      Color colorRayoRefractado=new Color((int)intensidadRefractadoR,
                                      (int)intensidadRefractadoG,(int)intensidadRefractadoB);
                                      Rayo rayoRefractado = new Rayo(refractado, punto, colorRayoRefractado);


                                    if (MaxDepth > 0
                                                    && (intensidadReflejadaR + intensidadReflejadaG + intensidadReflejadaB) > minIntensity) {
                                            Color colorReflejado = colorDesdeRayo(escena,
                                                            rayoReflejado, MaxDepth--, minIntensity, figura,false);
                                            red += colorReflejado.getRed();
                                            blue += colorReflejado.getBlue();
                                            green += colorReflejado.getGreen();
                                    }

                                      if (MaxDepth > 0 &&
                                      (intensidadRefractadoR+intensidadRefractadoB
                                      +intensidadRefractadoG) > minIntensity) {
                                          boolean esperarRefraccion=false;
                                          if (figura instanceof Esfera) {
                                              esperarRefraccion=true;
                                          } 
                                          Color colorRefractado = colorDesdeRayo(escena, rayoRefractado,
                                      MaxDepth--, minIntensity, figura,esperarRefraccion);
                                          red += colorRefractado.getRed();
                                          blue += colorRefractado.getBlue();
                                          green += colorRefractado.getGreen();

                                      }

                                    color = normalizarColor(red, green, blue);

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
                    //solo se ve si la figura refracta
                    intersecta = figura.getIndiceReflectividad();
                }
                
			for (int i = 0; i < figuras.size() && intersecta!=1; i++) {

				Rayo rayoPuntoFoco = new Rayo(rayoAlFoco, punto);
                                
                                
				Point4d puntoInterseccion = Interseccion.intersecta(
						rayoPuntoFoco, figuras.get(i));
				if (puntoInterseccion != null) {
					double distanciaPuntoFoco = punto.distanceSquared(foco
							.getPosicion());
					double distanciaInterseccionFoco = puntoInterseccion
							.distanceSquared(foco.getPosicion());
					if (distanciaInterseccionFoco < distanciaPuntoFoco
                                                && intersecta<figuras.get(i).getIndiceReflectividad()) {
						intersecta = figuras.get(i).getIndiceReflectividad();
					}
				}

			}
		
		return intersecta;
	}
}
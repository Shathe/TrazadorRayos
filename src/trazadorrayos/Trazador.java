/**
 * Iñigo Alonso - 665959
 * Alejandro Dieste - 541892
 */
package trazadorrayos;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import javax.vecmath.Matrix4d;
import javax.vecmath.Point4d;
import javax.imageio.ImageIO;

public class Trazador {

	public static void main(String[] args) {
		int MaxDepth = 15;
		double minIntensity = 1;
		int antialiasing = 3;
		// TODO code application logic here
		// Cargas la escena
		Escena escena = Escena.leerEscena("escena.txt");
		// Se crea el bufer para crear la imagen
		BufferedImage imagen = new BufferedImage((int) escena.getPantalla()
				.getPixelesanchura(), (int) escena.getPantalla()
				.getPixelesaltura(), BufferedImage.TYPE_INT_RGB);
		// Para cada pixel
		Pantalla pantalla = escena.getPantalla();
		for (int i = 0; i < pantalla.getPixelesanchura(); i++) {
			for (int j = 0; j < pantalla.getPixelesaltura(); j++) {
				/*
				 * Pixel i,j pero no es lo mismo el pixel que lo que mide en
				 * distancia primero hay que calcular la distancia real entre
				 * pixeles y luego calcular los centros de los pixeles en el
				 * sistema de coordenadas de la camara, para luego pasarlo al
				 * sistema del mundo y trazar el rayo. hay que recordar que el
				 * pixel 0,0 es el que esta abajo a la izquierda pero en el
				 * sistema de la camara el 0,0 es el del centro.
				 */
				double centroAn = pantalla.getPixelesanchura() / 2;
				double centroAl = pantalla.getPixelesaltura() / 2;
				double diffAn = pantalla.getAnchura()
						/ (double) (pantalla.getPixelesanchura() - 1);
				double diffAl = pantalla.getAltura()
						/ (double) (pantalla.getPixelesaltura() - 1);

				Point4d puntoPantalla = new Point4d();
				/*
				 * i-centroAn es debido a que en el sistema de la camara el
				 * pixel que se esta calculando, por ejemplo el (0,0) es el
				 * (-totalAncho/2, -totalAlto/2)
				 */
				// xPantalla e yPantalla seran el centro del pixel
				double xPantalla = diffAn * (i - centroAn) + diffAn;
				double yPantalla = diffAl * (-j + centroAl) + diffAl;
				
				double diffAnAntialiasing = Math.abs(diffAn
						/ (double) (antialiasing - 1));
				double diffAlAntialiasing = Math.abs(diffAl
						/ (double) (antialiasing - 1));
				double red = 0.0;
				double green = 0.0;
				double blue = 0.0;
				for (int a = 0; a < antialiasing * antialiasing; a++) {
					for (int b = 0; b < antialiasing / antialiasing; b++) {
						if (xPantalla > 0) {
							puntoPantalla.x = (Math.random() * a*diffAnAntialiasing)
									+ xPantalla;
						}
						else {
							puntoPantalla.x = xPantalla
									- (diffAnAntialiasing * a* Math.random());
						}
						if (yPantalla > 0) {
							puntoPantalla.y = (-Math.random() * b*diffAlAntialiasing)
									+ yPantalla;
						}
						else {
							puntoPantalla.y = yPantalla
									- (diffAlAntialiasing * b*Math.random());
						}

						puntoPantalla.z = -escena.getCamara()
								.getDistanciaPantalla();
						puntoPantalla.w = 1;
						Matrix4d cambioBase = escena.getCamara()
								.getCambioBase();
						puntoPantalla = Escena.multiplyPointMatrix(
								puntoPantalla, cambioBase);
						Color color = OperacionesEscena.colorPuntoPantalla(
								puntoPantalla, escena, MaxDepth, minIntensity);
						red += color.getRed();
						green += color.getGreen();
						blue += color.getBlue();
					}
				}
				red = red / (antialiasing * antialiasing);
				green = green / (antialiasing * antialiasing);
				blue = blue / (antialiasing * antialiasing);
				Color color = new Color((int) red, (int) green, (int) blue);
				/*
				 * puntoPantalla.x = xPantalla; puntoPantalla.y = yPantalla;
				 * puntoPantalla.z = -escena.getCamara().getDistanciaPantalla();
				 * puntoPantalla.w = 1; // Aqui tienes el centro del pixel en
				 * coordenadas de la camara Matrix4d cambioBase =
				 * escena.getCamara().getCambioBase();
				 */
				/*
				 * Este es el punto del centro del pixel(i,j) en coordenadas del
				 * mundo
				 */
				// double
				// []a={puntoPantalla.x,puntoPantalla.y,puntoPantalla.z,1};
				// puntoPantalla = multiplicar(cambioBase, a
				/*
				 * puntoPantalla = Escena.multiplyPointMatrix(puntoPantalla,
				 * cambioBase); Color color =
				 * OperacionesEscena.colorPuntoPantalla( puntoPantalla, escena,
				 * MaxDepth, minIntensity);
				 */
				imagen.setRGB(i, j, color.getRGB());
			}
		}
		try {
			ImageIO.write(imagen, "jpg", new File("foto.jpg"));
		}
		catch (IOException e) {
			System.out.println("Error de escritura");
		}
	}
}

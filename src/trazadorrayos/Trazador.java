/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trazadorrayos;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Color;
import javax.vecmath.Matrix3d;
import javax.vecmath.Matrix4d;
import javax.vecmath.Point4d;
import javax.vecmath.Vector4d;
import javax.vecmath.Vector3d;

import java.util.Scanner;
import javax.imageio.ImageIO;

/**
 *
 * @author shathe
 */
public class Trazador {
	/**
	 * @param args
	 *            the command line arguments
	 */
	Escena escena = null;

	public void leerEscena(String fichero) {
		try {
			Foco foco = null;
			Camara camara = null;
			Pantalla pantalla = null;
			ArrayList<Figura> figuras = new ArrayList<Figura>();
			Scanner escenaFichero = new Scanner(new File(fichero));
			String figura = "";
			// recorremos el fichero
			while (escenaFichero.hasNextLine()) {
				figura = escenaFichero.next();
				switch (figura) {
				case "figura:foco":
					// posicion
					escenaFichero.next();
					Point4d punto = new Point4d(escenaFichero.nextInt(),
							escenaFichero.nextInt(), escenaFichero.nextInt(),
							escenaFichero.nextInt());
					// color
					escenaFichero.next();
					Color color = new Color(escenaFichero.nextShort(),
							escenaFichero.nextShort(),
							escenaFichero.nextShort());
					// intensidad
					escenaFichero.next();
					double intensidad = Float.parseFloat(escenaFichero.next());

					// intensidad aambiente
					escenaFichero.next();
					double intensidadAmbiente = Float.parseFloat(escenaFichero
							.next());
					foco = new Foco(punto, color, intensidad,
							intensidadAmbiente);

					System.out.println(foco);

					break;
				case "figura:camara":
					// direccion
					escenaFichero.next();
					Vector4d direccion = new Vector4d(escenaFichero.nextInt(),
							escenaFichero.nextInt(), escenaFichero.nextInt(),
							escenaFichero.nextInt());
					// distancia pantalla
					escenaFichero.next();
					int distanciaPantalla = escenaFichero.nextInt();
					// posicion
					escenaFichero.next();
					Point4d posicion = new Point4d(escenaFichero.nextInt(),
							escenaFichero.nextInt(), escenaFichero.nextInt(),
							escenaFichero.nextInt());
					camara = new Camara(direccion, distanciaPantalla, posicion);
					System.out.println(camara);
					break;
				case "figura:pantalla":
					// anchura
					escenaFichero.next();
					int anchuraPixel = escenaFichero.nextInt();
					// altura
					escenaFichero.next();
					int alturaPixel = escenaFichero.nextInt();
					escenaFichero.next();
					int anchura = escenaFichero.nextInt();
					// altura
					escenaFichero.next();
					int altura = escenaFichero.nextInt();
					pantalla = new Pantalla(anchuraPixel, alturaPixel, anchura,
							altura);
					System.out.println(pantalla);
					break;
				case "figura:esfera":
					// posicion
					escenaFichero.next();
					posicion = new Point4d(escenaFichero.nextInt(),
							escenaFichero.nextInt(), escenaFichero.nextInt(),
							escenaFichero.nextInt());
				 
					// refraccion
					escenaFichero.next();
					double refraccion = Float.parseFloat(escenaFichero.next());
					// reflectividad
					escenaFichero.next();
					double reflectividad = Float.parseFloat(escenaFichero
							.next());
					// kd
					escenaFichero.next();
					Color KD = new Color(escenaFichero.nextShort(),
							escenaFichero.nextShort(),
							escenaFichero.nextShort());
					// ks
					escenaFichero.next();
					Color KS = new Color(escenaFichero.nextShort(),
							escenaFichero.nextShort(),
							escenaFichero.nextShort());
					// radio
					escenaFichero.next();
					int radio = escenaFichero.nextInt();
					Esfera esfera = new Esfera(posicion, refraccion,
							reflectividad, KD, KS, radio);
					System.out.println(esfera);
					figuras.add(esfera);
					break;
				case "figura:plano":
					// puntoP
					escenaFichero.next();
					Point4d puntoP = new Point4d(escenaFichero.nextInt(),
							escenaFichero.nextInt(), escenaFichero.nextInt(),
							escenaFichero.nextInt());
					// normal
					escenaFichero.next();
					Vector4d normal = new Vector4d(escenaFichero.nextInt(),
							escenaFichero.nextInt(), escenaFichero.nextInt(),
							escenaFichero.nextInt());
					 
					// refraccion
					escenaFichero.next();
					double refraccionP = Float.parseFloat(escenaFichero.next());
					// reflectividad
					escenaFichero.next();
					double reflectividadP = Float.parseFloat(escenaFichero
							.next());
					// kd
					escenaFichero.next();
					KD = new Color(escenaFichero.nextShort(),
							escenaFichero.nextShort(),
							escenaFichero.nextShort());
					// ks
					escenaFichero.next();
					 KS = new Color(escenaFichero.nextShort(),
							escenaFichero.nextShort(),
							escenaFichero.nextShort());
					Plano plano = new Plano(puntoP, normal, refraccionP,
							reflectividadP, KD, KS);
					System.out.println(plano);
					figuras.add(plano);

					break;
				case "figura:triangulo":
					// punto1
					escenaFichero.next();
					Point4d punto1 = new Point4d(escenaFichero.nextInt(),
							escenaFichero.nextInt(), escenaFichero.nextInt(),
							escenaFichero.nextInt());
					// punto2
					escenaFichero.next();
					Point4d punto2 = new Point4d(escenaFichero.nextInt(),
							escenaFichero.nextInt(), escenaFichero.nextInt(),
							escenaFichero.nextInt());
					// punto3
					escenaFichero.next();
					Point4d punto3 = new Point4d(escenaFichero.nextInt(),
							escenaFichero.nextInt(), escenaFichero.nextInt(),
							escenaFichero.nextInt()); 
					// refraccion
					escenaFichero.next();
					double refraccionT = Float.parseFloat(escenaFichero.next());
					// reflectividad
					escenaFichero.next();
					double reflectividadT = Float.parseFloat(escenaFichero
							.next());
					/// kd
					escenaFichero.next();
					KD = new Color(escenaFichero.nextShort(),
							escenaFichero.nextShort(),
							escenaFichero.nextShort());
					// ks
					escenaFichero.next();
					 KS = new Color(escenaFichero.nextShort(),
							escenaFichero.nextShort(),
							escenaFichero.nextShort());
                                         
					Triangulo triangulo = new Triangulo(punto1, punto2, punto3,
							 refraccionT, reflectividadT, KD, KS);
					System.out.println(triangulo);
					figuras.add(triangulo);
					break;
				case "//":
					break;
				}
				// Cargar escena del fichero

				try {
					escenaFichero.nextLine();
				}
				catch (Exception e) {
				}

			}

			escenaFichero.close();
			escena = new Escena(foco, camara, pantalla);
			escena.anadirSetFiguras(figuras);

		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Multiplica el punto por la matriz
	 * 
	 * @param point
	 * @param matrix
	 * @return
	 */
	public static Point4d multiplyPointMatrix(Point4d point, Matrix4d matrix) {
		return new Point4d(point.x * matrix.getElement(0,0) + point.y * matrix.getElement(1,0) 
				+ point.z * matrix.getElement(2,0)  + point.w * matrix.getElement(3,0), point.x
				* matrix.getElement(0,1) + point.y * matrix.getElement(1,1) + point.z * matrix.getElement(2,1)
				+ point.w * matrix.getElement(3,1), point.x * matrix.getElement(0,2) + point.y
				* matrix.getElement(1,2) + point.z * matrix.getElement(2,2) + point.w * matrix.getElement(3,2),
				point.x * matrix.getElement(0,3) + point.y * matrix.getElement(1,3) + point.z
						* matrix.getElement(2,3) + point.w * matrix.getElement(3,3));
	}
     public static Point4d multiplicar(Matrix4d A, double[] x) {
        double[] y = new double[x.length];
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                y[i] += A.getElement(i, j) * x[j];
        return new Point4d(y[0], y[1], y[2],1);
                }
	public static void main(String[] args) {
		int MaxDepth = 15;
		double minIntensity = 0.05;
		// TODO code application logic here
		Trazador trazador = new Trazador();
		// Cargas la escena
		trazador.leerEscena("escena.txt");
		// Se crea el bufer para crear la imagen
		BufferedImage imagen = new BufferedImage((int) trazador.escena
				.getPantalla().getPixelesanchura(), (int) trazador.escena
				.getPantalla().getPixelesaltura(), BufferedImage.TYPE_INT_RGB);
		// Para cada pixel
		Pantalla pantalla = trazador.escena.getPantalla();
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
						/ (double)(pantalla.getPixelesanchura()-1);
				double diffAl = pantalla.getAltura()
						/ (double)(pantalla.getPixelesaltura()-1);
				Point4d puntoPantalla = new Point4d();
				/*
				 * i-centroAn es debido a que en el sistema de la camara el
				 * pixel que se esta calculando, por ejemplo el (0,0) es el
				 * (-totalAncho/2, -totalAlto/2)
				 */
                                  
				puntoPantalla.x = diffAn * (i - centroAn) + diffAn;
				puntoPantalla.y = diffAl * (-j + centroAl) + diffAl;
				puntoPantalla.z = -trazador.escena.getCamara()
						.getDistanciaPantalla();
				puntoPantalla.w = 1;
				// Aqui tienes el centro del pixel en coordenadas de la camara
				Matrix4d cambioBase = trazador.escena.getCamara()
						.getCambioBase();
				/*
				 * Este es el punto del centro del pixel(i,j) en coordenadas del
				 * mundo
				 */

                                  //  double []a={puntoPantalla.x,puntoPantalla.y,puntoPantalla.z,1};
				//puntoPantalla = multiplicar(cambioBase,  a);
                                
				puntoPantalla = multiplyPointMatrix(puntoPantalla, cambioBase);
				Color color = OperacionesEscena.colorPuntoPantalla(
						puntoPantalla, trazador.escena, MaxDepth, minIntensity);
                               
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

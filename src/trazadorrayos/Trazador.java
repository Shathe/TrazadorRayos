/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trazadorrayos;

import java.io.File;
import java.io.FileNotFoundException;

import javax.vecmath.Matrix3d;
import javax.vecmath.Matrix4d;
import javax.vecmath.Point4d;
import javax.vecmath.Vector4d;
import javax.vecmath.Vector3d;

import java.util.Scanner;

/**
 *
 * @author shathe
 */
public class Trazador {
	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		// TODO code application logic here
		new Trazador().leerEscena("escena.txt");
	}

	public void leerEscena(String fichero) {
		try {
			Scanner escena = new Scanner(new File(fichero));
			String figura = "";
			// recorremos el fichero
			while (escena.hasNextLine()) {
				figura = escena.next();
				switch (figura) {
				case "figura:foco":
					// posicion
					escena.next();
					Point4d punto = new Point4d(escena.nextInt(),
							escena.nextInt(), escena.nextInt(),
							escena.nextInt());
					// color
					escena.next();
					Color color = new Color(escena.nextShort(),
							escena.nextShort(), escena.nextShort());
					// intensidad
					escena.next();
					double intensidad = escena.nextDouble();
					Foco foco = new Foco(punto, color, intensidad);
					System.out.println(foco);
					break;
				case "figura:camara":
					// direccion
					escena.next();
					Vector4d direccion = new Vector4d(escena.nextInt(),
							escena.nextInt(), escena.nextInt(),
							escena.nextInt());
					// distancia pantalla
					escena.next();
					int distanciaPantalla = escena.nextInt();
					// posicion
					escena.next();
					Point4d posicion = new Point4d(escena.nextInt(),
							escena.nextInt(), escena.nextInt(),
							escena.nextInt());
					Camara camara = new Camara(direccion, distanciaPantalla,
							posicion);
					System.out.println(camara);
					break;
				case "figura:pantalla":
					// anchura
					escena.next();
					int anchura = escena.nextInt();
					// altura
					escena.next();
					int altura = escena.nextInt();
					Pantalla pantalla = new Pantalla(anchura, altura);
					System.out.println(pantalla);
					break;
				case "figura:esfera":
					// posicion
					escena.next();
					posicion = new Point4d(escena.nextInt(), escena.nextInt(),
							escena.nextInt(), escena.nextInt());
					// color
					escena.next();
					color = new Color(escena.nextShort(), escena.nextShort(),
							escena.nextShort());
					// refraccion
					escena.next();
					double refraccion = escena.nextDouble();
					// reflectividad
					escena.next();
					double reflectividad = escena.nextDouble();
					// ks
					escena.next();
					double ks = escena.nextDouble();
					// kd
					escena.next();
					double kd = escena.nextDouble();
					// radio
					escena.next();
					int radio = escena.nextInt();
					Esfera esfera = new Esfera(posicion, color, refraccion,
							reflectividad, ks, kd, radio);
					System.out.println(esfera);
					break;
				}
				// Cargar escena del fichero

				try {
					escena.nextLine();
				}
				catch (Exception e) {
				}

			}

			escena.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}

/**
 * Iñigo Alonso - 665959
 * Alejandro Dieste - 541892
 */
package trazadorrayos;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.vecmath.Matrix4d;
import javax.vecmath.Point4d;
import javax.vecmath.Vector4d;

/**
 * Clase que representa la escena con la camara, la pantalla, el foco de luz y
 * la lista de figuras
 *
 */
public class Escena {
	private ArrayList<Figura> figuras = new ArrayList<Figura>();
	private Pantalla pantalla = null;
	private Camara camara = null;
	private Foco foco = null;
	private static Matrix4d matrizTransformacionIdentidad = new Matrix4d(1, 0,
			0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);

	public Escena(Foco foco, Camara camara, Pantalla pantalla) {
		this.foco = foco;
		this.camara = camara;
		this.pantalla = pantalla;
	}

	public void anadirFigura(Figura figura) {
		figuras.add(figura);
	}

	public void anadirSetFiguras(ArrayList<Figura> figuras) {
		this.figuras = figuras;
	}

	public Pantalla getPantalla() {
		return pantalla;
	}

	public ArrayList<Figura> getFiguras() {
		return figuras;
	}

	public Camara getCamara() {
		return camara;
	}

	public Foco getFoco() {
		return foco;
	}

	public static Escena leerEscena(String fichero) {
		Escena escena = null;
		try {
			Foco foco = null;
			Camara camara = null;
			Pantalla pantalla = null;
			ArrayList<Figura> figuras = new ArrayList<Figura>();
			Scanner escenaFichero = new Scanner(new File(fichero));
			String figura = "";
			Point4d punto = null;
			Color color = null;
			double intensidad = 0.0;
			double intensidadAmbiente = 0.0;
			int distanciaPantalla = 0;
			Vector4d direccion = null;
			Point4d posicion = new Point4d(0, 0, 0, 0);
			int anchuraPixel = 0;
			int alturaPixel = 0;
			int anchura = 0;
			int altura = 0;
			double refraccion = 0.0;
			double reflectividad = 0.0;
			double transparencia = 0.0;
			Color KD = null;
			Color KS = null;
			double radio = 0;
			Vector4d normal = null;
			Point4d punto1 = null;
			Point4d punto2 = null;
			Point4d punto3 = null;
			// recorremos el fichero
			while (escenaFichero.hasNextLine()) {

				figura = escenaFichero.next();
				switch (figura) {

				case "figura:camara":
					String cadena = escenaFichero.next();
					while (!cadena.equals(".")) {
						if (cadena.equals("distanciaPantalla:")) {
							distanciaPantalla = escenaFichero.nextInt();
							cadena = escenaFichero.next();
						}
						else if (cadena.equals("direccion:")) {
							direccion = new Vector4d(Float.parseFloat(escenaFichero.next()),
									Float.parseFloat(escenaFichero.next()),
									Float.parseFloat(escenaFichero.next()),
									Float.parseFloat(escenaFichero.next()));
							cadena = escenaFichero.next();
						}
						else if (cadena.equals("posicion:")) {
							posicion = new Point4d(Float.parseFloat(escenaFichero.next()),
									Float.parseFloat(escenaFichero.next()),
									Float.parseFloat(escenaFichero.next()),
									Float.parseFloat(escenaFichero.next()));
							cadena = escenaFichero.next();
						}
					}
					camara = new Camara(direccion, distanciaPantalla, posicion);
					System.out.println(camara);
					break;
				case "figura:pantalla":
					cadena = escenaFichero.next();
					while (!cadena.equals(".")) {
						if (cadena.equals("pixelanchura:")) {
							anchuraPixel = escenaFichero.nextInt();

							cadena = escenaFichero.next();
						}
						else if (cadena.equals("pixelaltura:")) {
							alturaPixel = escenaFichero.nextInt();
							cadena = escenaFichero.next();
						}
						else if (cadena.equals("anchura:")) {
							anchura = escenaFichero.nextInt();
							cadena = escenaFichero.next();
						}
						else if (cadena.equals("altura:")) {
							altura = escenaFichero.nextInt();
							cadena = escenaFichero.next();
						}
					}
					pantalla = new Pantalla(anchuraPixel, alturaPixel, anchura,
							altura);
					System.out.println(pantalla);
					break;
				case "figura:foco":
					cadena = escenaFichero.next();
					while (!cadena.equals(".")) {
						if (cadena.equals("posicion:")) {
							punto = new Point4d(Float.parseFloat(escenaFichero.next()),
									Float.parseFloat(escenaFichero.next()),
									Float.parseFloat(escenaFichero.next()),
									Float.parseFloat(escenaFichero.next()));
							cadena = escenaFichero.next();
						}
						else if (cadena.equals("color:")) {
							color = new Color(escenaFichero.nextShort(),
									escenaFichero.nextShort(),
									escenaFichero.nextShort());
							cadena = escenaFichero.next();
						}
						else if (cadena.equals("intensidad:")) {
							intensidad = Float.parseFloat(escenaFichero.next());
							cadena = escenaFichero.next();

						}
						else if (cadena.equals("Iambiente:")) {
							intensidadAmbiente = Float.parseFloat(escenaFichero
									.next());
							cadena = escenaFichero.next();
						}

					}
					Matrix4d mTFoco = matrizTraslacion(punto);
					Point4d posicionFoco = new Point4d(0, 0, 0, 1);
					posicionFoco = multiplyPointMatrix(posicionFoco, mTFoco);
					posicionFoco = multiplyPointMatrix(posicionFoco,
							camara.getCambioBase());
					foco = new Foco(posicionFoco, color, intensidad,
							intensidadAmbiente);

					System.out.println(foco);

					break;
				case "figura:esfera":
					cadena = escenaFichero.next();
					while (!cadena.equals(".")) {
						if (cadena.equals("posicion:")) {
							posicion = new Point4d(Float.parseFloat(escenaFichero.next()),
									Float.parseFloat(escenaFichero.next()),
									Float.parseFloat(escenaFichero.next()),
									Float.parseFloat(escenaFichero.next()));

							cadena = escenaFichero.next();
						}
						else if (cadena.equals("indicerefraccion:")) {
							refraccion = Float.parseFloat(escenaFichero.next());
							cadena = escenaFichero.next();
						}
						else if (cadena.equals("reflectividad:")) {
							reflectividad = Float.parseFloat(escenaFichero
									.next());
							cadena = escenaFichero.next();
						}
						else if (cadena.equals("transparencia:")) {
							transparencia = Float.parseFloat(escenaFichero
									.next());
							cadena = escenaFichero.next();
						}
						else if (cadena.equals("kd:")) {
							KD = new Color(escenaFichero.nextShort(),
									escenaFichero.nextShort(),
									escenaFichero.nextShort());
							cadena = escenaFichero.next();
						}
						else if (cadena.equals("ks:")) {
							KS = new Color(escenaFichero.nextShort(),
									escenaFichero.nextShort(),
									escenaFichero.nextShort());
							cadena = escenaFichero.next();
						}
						else if (cadena.equals("radio:")) {
							radio = Float.parseFloat(escenaFichero.next());
							cadena = escenaFichero.next();
						}
					}
					Matrix4d mTEsfera = matrizTraslacion(posicion);
					Point4d posicionEsfera = new Point4d(0, 0, 0, 1);
					posicionEsfera = multiplyPointMatrix(posicionEsfera,
							mTEsfera);
					posicionEsfera = multiplyPointMatrix(posicionEsfera,
							camara.getCambioBase());
					Esfera esfera = new Esfera(posicionEsfera, refraccion,
							reflectividad, transparencia, KD, KS, radio);
					System.out.println(esfera);
					figuras.add(esfera);
					break;
				case "figura:plano":
					cadena = escenaFichero.next();
					while (!cadena.equals(".")) {
						if (cadena.equals("posicion:")) {
							posicion = new Point4d(Float.parseFloat(escenaFichero.next()),
									Float.parseFloat(escenaFichero.next()),
									Float.parseFloat(escenaFichero.next()),
									Float.parseFloat(escenaFichero.next()));

							cadena = escenaFichero.next();
						}
						else if (cadena.equals("indicerefraccion:")) {
							refraccion = Float.parseFloat(escenaFichero.next());
							cadena = escenaFichero.next();
						}
						else if (cadena.equals("reflectividad:")) {
							reflectividad = Float.parseFloat(escenaFichero
									.next());
							cadena = escenaFichero.next();
						}
						else if (cadena.equals("transparencia:")) {
							transparencia = Float.parseFloat(escenaFichero
									.next());
							cadena = escenaFichero.next();
						}
						else if (cadena.equals("kd:")) {
							KD = new Color(escenaFichero.nextShort(),
									escenaFichero.nextShort(),
									escenaFichero.nextShort());
							cadena = escenaFichero.next();
						}
						else if (cadena.equals("ks:")) {
							KS = new Color(escenaFichero.nextShort(),
									escenaFichero.nextShort(),
									escenaFichero.nextShort());
							cadena = escenaFichero.next();
						}
						else if (cadena.equals("normal:")) {
							normal = new Vector4d(Float.parseFloat(escenaFichero.next()),
									Float.parseFloat(escenaFichero.next()),
									Float.parseFloat(escenaFichero.next()),
									Float.parseFloat(escenaFichero.next()));
							cadena = escenaFichero.next();
						}
					}
					Matrix4d mTPlano = matrizTraslacion(posicion);
					Point4d posicionPlano = new Point4d(0, 0, 0, 1);
					posicionPlano = multiplyPointMatrix(posicionPlano, mTPlano);
					posicionPlano = multiplyPointMatrix(posicionPlano,
							camara.getCambioBase());

					Point4d auxNormal = multiplyPointMatrix(
							new Point4d(normal), camara.getCambioBase());
					normal = new Vector4d(auxNormal);
					normal.normalize();

					Plano plano = new Plano(posicionPlano, normal, refraccion,
							reflectividad, transparencia, KD, KS);
					System.out.println(plano);
					figuras.add(plano);

					break;
				case "figura:triangulo":
					cadena = escenaFichero.next();
					while (!cadena.equals(".")) {
						if (cadena.equals("punto1:")) {
							punto1 = new Point4d(Float.parseFloat(escenaFichero.next()),
									Float.parseFloat(escenaFichero.next()),
									Float.parseFloat(escenaFichero.next()),
									Float.parseFloat(escenaFichero.next()));

							cadena = escenaFichero.next();
						}
						else if (cadena.equals("punto2:")) {
							punto2 = new Point4d(Float.parseFloat(escenaFichero.next()),
									Float.parseFloat(escenaFichero.next()),
									Float.parseFloat(escenaFichero.next()),
									Float.parseFloat(escenaFichero.next()));

							cadena = escenaFichero.next();
						}
						else if (cadena.equals("punto3:")) {
							punto3 = new Point4d(Float.parseFloat(escenaFichero.next()),
									Float.parseFloat(escenaFichero.next()),
									Float.parseFloat(escenaFichero.next()),
									Float.parseFloat(escenaFichero.next()));

							cadena = escenaFichero.next();
						}
						else if (cadena.equals("indicerefraccion:")) {
							refraccion = Float.parseFloat(escenaFichero.next());
							cadena = escenaFichero.next();
						}
						else if (cadena.equals("reflectividad:")) {
							reflectividad = Float.parseFloat(escenaFichero
									.next());
							cadena = escenaFichero.next();
						}
						else if (cadena.equals("transparencia:")) {
							transparencia = Float.parseFloat(escenaFichero
									.next());
							cadena = escenaFichero.next();
						}
						else if (cadena.equals("kd:")) {
							KD = new Color(escenaFichero.nextShort(),
									escenaFichero.nextShort(),
									escenaFichero.nextShort());
							cadena = escenaFichero.next();
						}
						else if (cadena.equals("ks:")) {
							KS = new Color(escenaFichero.nextShort(),
									escenaFichero.nextShort(),
									escenaFichero.nextShort());
							cadena = escenaFichero.next();
						}

					}
					Matrix4d mTTriangulo = matrizTraslacion(punto1);
					Point4d punto1Triangulo = new Point4d(0, 0, 0, 1);
					punto1Triangulo = multiplyPointMatrix(punto1Triangulo,
							mTTriangulo);
					punto1Triangulo = multiplyPointMatrix(punto1Triangulo,
							camara.getCambioBase());
					mTTriangulo = matrizTraslacion(punto2);
					Point4d punto2Triangulo = new Point4d(0, 0, 0, 1);
					punto2Triangulo = multiplyPointMatrix(punto2Triangulo,
							mTTriangulo);
					punto2Triangulo = multiplyPointMatrix(punto2Triangulo,
							camara.getCambioBase());
					mTTriangulo = matrizTraslacion(punto3);
					Point4d punto3Triangulo = new Point4d(0, 0, 0, 1);
					punto3Triangulo = multiplyPointMatrix(punto3Triangulo,
							mTTriangulo);
					punto3Triangulo = multiplyPointMatrix(punto3Triangulo,
							camara.getCambioBase());
					Triangulo triangulo = new Triangulo(punto1Triangulo,
							punto2Triangulo, punto3Triangulo, refraccion,
							reflectividad, transparencia, KD, KS);
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
		return escena;
	}

	/**
	 * Multiplica el punto por la matriz
	 * 
	 * @param point
	 * @param matrix
	 * @return
	 */
	public static Point4d multiplyPointMatrix(Point4d point, Matrix4d matrix) {
		return new Point4d(point.x * matrix.getElement(0, 0) + point.y
				* matrix.getElement(1, 0) + point.z * matrix.getElement(2, 0)
				+ point.w * matrix.getElement(3, 0), point.x
				* matrix.getElement(0, 1) + point.y * matrix.getElement(1, 1)
				+ point.z * matrix.getElement(2, 1) + point.w
				* matrix.getElement(3, 1), point.x * matrix.getElement(0, 2)
				+ point.y * matrix.getElement(1, 2) + point.z
				* matrix.getElement(2, 2) + point.w * matrix.getElement(3, 2),
				point.x * matrix.getElement(0, 3) + point.y
						* matrix.getElement(1, 3) + point.z
						* matrix.getElement(2, 3) + point.w
						* matrix.getElement(3, 3));
	}

	/**
	 * Devuelve la matriz de transformacion de traslacion
	 * 
	 * @param posicion
	 * @return
	 */
	public static Matrix4d matrizTraslacion(Point4d posicion) {
		Matrix4d mT = new Matrix4d(matrizTransformacionIdentidad);
		mT.setElement(3, 0, posicion.x);
		mT.setElement(3, 1, posicion.y);
		mT.setElement(3, 2, posicion.z);
		return mT;
	}

}

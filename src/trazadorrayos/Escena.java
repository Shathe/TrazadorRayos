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
	static Scanner escenaFichero;
	static String cadena = "";

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
			escenaFichero = new Scanner(new File(fichero));
			Foco foco = null;
			Camara camara = null;
			Pantalla pantalla = null;
			ArrayList<Figura> figuras = new ArrayList<Figura>();
			String figura = "";
			// recorremos el fichero
			while (escenaFichero.hasNextLine()) {

				figura = escenaFichero.next();
				switch (figura) {
				case "figura:camara":
					camara = camara();
					System.out.println(camara);
					break;
				case "figura:pantalla":
					pantalla = pantalla(camara);
					System.out.println(pantalla);
					break;
				case "figura:foco":
					foco = foco(camara);
					System.out.println(foco);
					break;
				case "figura:esfera":
					Esfera esfera = esfera(camara);
					System.out.println(esfera);
					figuras.add(esfera);
					break;
				case "figura:plano":
					Plano plano = plano(camara);
					System.out.println(plano);
					figuras.add(plano);
					break;
				case "figura:triangulo":
					Triangulo triangulo = triangulo(camara);
					System.out.println(triangulo);
					figuras.add(triangulo);
					break;
				case "figura:compleja":
					ArrayList<Triangulo> lTriangulo = compleja(camara);
					figuras.addAll(lTriangulo);
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

	public static Camara camara() {
		String cadena = escenaFichero.next();
		int distanciaPantalla = 0;
		Vector4d direccion = null;
		Point4d posicion = null;
		while (!cadena.equals(".")) {
			if (cadena.equals("distanciaPantalla:")) {
				distanciaPantalla = escenaFichero.nextInt();
				cadena = escenaFichero.next();
			}
			else if (cadena.equals("direccion:")) {
				direccion = new Vector4d(
						Float.parseFloat(escenaFichero.next()),
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
		return new Camara(direccion, distanciaPantalla, posicion);
	}

	public static Pantalla pantalla(Camara camara) {
		int anchuraPixel = 0;
		int alturaPixel = 0;
		int anchura = 0;
		int altura = 0;
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
		return new Pantalla(anchuraPixel, alturaPixel, anchura, altura);
	}

	public static Foco foco(Camara camara) {
		Point4d punto = null;
		Color color = null;
		double intensidad = 0;
		double intensidadAmbiente = 0;
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
						escenaFichero.nextShort(), escenaFichero.nextShort());
				cadena = escenaFichero.next();
			}
			else if (cadena.equals("intensidad:")) {
				intensidad = Float.parseFloat(escenaFichero.next());
				cadena = escenaFichero.next();

			}
			else if (cadena.equals("Iambiente:")) {
				intensidadAmbiente = Float.parseFloat(escenaFichero.next());
				cadena = escenaFichero.next();
			}

		}
		Matrix4d mTFoco = Operaciones.matrizTraslacion(punto);
		Point4d posicionFoco = new Point4d(0, 0, 0, 1);
		posicionFoco = Operaciones.multiplyPointMatrix(posicionFoco, mTFoco);
		posicionFoco = Operaciones.multiplyPointMatrix(posicionFoco,
				camara.getCambioBase());
		return new Foco(posicionFoco, color, intensidad, intensidadAmbiente);
	}

	public static Esfera esfera(Camara camara) {
		Point4d posicion = null;
		double refraccion = 0.0;
		double reflectividad = 0.0;
		double transparencia = 0.0;
		Color KD = null;
		Color KS = null;
		double radio = 0;
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
				reflectividad = Float.parseFloat(escenaFichero.next());
				cadena = escenaFichero.next();
			}
			else if (cadena.equals("transparencia:")) {
				transparencia = Float.parseFloat(escenaFichero.next());
				cadena = escenaFichero.next();
			}
			else if (cadena.equals("kd:")) {
				KD = new Color(escenaFichero.nextShort(),
						escenaFichero.nextShort(), escenaFichero.nextShort());
				cadena = escenaFichero.next();
			}
			else if (cadena.equals("ks:")) {
				KS = new Color(escenaFichero.nextShort(),
						escenaFichero.nextShort(), escenaFichero.nextShort());
				cadena = escenaFichero.next();
			}
			else if (cadena.equals("radio:")) {
				radio = Float.parseFloat(escenaFichero.next());
				cadena = escenaFichero.next();
			}
		}
		Matrix4d mTEsfera = Operaciones.matrizTraslacion(posicion);
		Point4d posicionEsfera = new Point4d(0, 0, 0, 1);
		posicionEsfera = Operaciones.multiplyPointMatrix(posicionEsfera,
				mTEsfera);
		posicionEsfera = Operaciones.multiplyPointMatrix(posicionEsfera,
				camara.getCambioBase());
		return new Esfera(posicionEsfera, refraccion, reflectividad,
				transparencia, KD, KS, radio);
	}

	public static Plano plano(Camara camara) {
		Point4d posicion = new Point4d(0, 0, 0, 0);
		double refraccion = 0.0;
		double reflectividad = 0.0;
		double transparencia = 0.0;
		Color KD = null;
		Color KS = null;
		Vector4d normal = null;
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
				reflectividad = Float.parseFloat(escenaFichero.next());
				cadena = escenaFichero.next();
			}
			else if (cadena.equals("transparencia:")) {
				transparencia = Float.parseFloat(escenaFichero.next());
				cadena = escenaFichero.next();
			}
			else if (cadena.equals("kd:")) {
				KD = new Color(escenaFichero.nextShort(),
						escenaFichero.nextShort(), escenaFichero.nextShort());
				cadena = escenaFichero.next();
			}
			else if (cadena.equals("ks:")) {
				KS = new Color(escenaFichero.nextShort(),
						escenaFichero.nextShort(), escenaFichero.nextShort());
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
		Matrix4d mTPlano = Operaciones.matrizTraslacion(posicion);
		Point4d posicionPlano = new Point4d(0, 0, 0, 1);
		posicionPlano = Operaciones.multiplyPointMatrix(posicionPlano, mTPlano);
		posicionPlano = Operaciones.multiplyPointMatrix(posicionPlano,
				camara.getCambioBase());

		Point4d auxNormal = Operaciones.multiplyPointMatrix(
				new Point4d(normal), camara.getCambioBase());
		normal = new Vector4d(auxNormal);
		normal.normalize();

		return new Plano(posicionPlano, normal, refraccion, reflectividad,
				transparencia, KD, KS);
	}

	public static Triangulo triangulo(Camara camara) {
		double refraccion = 0.0;
		double reflectividad = 0.0;
		double transparencia = 0.0;
		Color KD = null;
		Color KS = null;
		Point4d punto1 = null;
		Point4d punto2 = null;
		Point4d punto3 = null;
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
				reflectividad = Float.parseFloat(escenaFichero.next());
				cadena = escenaFichero.next();
			}
			else if (cadena.equals("transparencia:")) {
				transparencia = Float.parseFloat(escenaFichero.next());
				cadena = escenaFichero.next();
			}
			else if (cadena.equals("kd:")) {
				KD = new Color(escenaFichero.nextShort(),
						escenaFichero.nextShort(), escenaFichero.nextShort());
				cadena = escenaFichero.next();
			}
			else if (cadena.equals("ks:")) {
				KS = new Color(escenaFichero.nextShort(),
						escenaFichero.nextShort(), escenaFichero.nextShort());
				cadena = escenaFichero.next();
			}

		}
		Matrix4d mTTriangulo = Operaciones.matrizTraslacion(punto1);
		Point4d punto1Triangulo = new Point4d(0, 0, 0, 1);
		punto1Triangulo = Operaciones.multiplyPointMatrix(punto1Triangulo,
				mTTriangulo);
		punto1Triangulo = Operaciones.multiplyPointMatrix(punto1Triangulo,
				camara.getCambioBase());
		mTTriangulo = Operaciones.matrizTraslacion(punto2);
		Point4d punto2Triangulo = new Point4d(0, 0, 0, 1);
		punto2Triangulo = Operaciones.multiplyPointMatrix(punto2Triangulo,
				mTTriangulo);
		punto2Triangulo = Operaciones.multiplyPointMatrix(punto2Triangulo,
				camara.getCambioBase());
		mTTriangulo = Operaciones.matrizTraslacion(punto3);
		Point4d punto3Triangulo = new Point4d(0, 0, 0, 1);
		punto3Triangulo = Operaciones.multiplyPointMatrix(punto3Triangulo,
				mTTriangulo);
		punto3Triangulo = Operaciones.multiplyPointMatrix(punto3Triangulo,
				camara.getCambioBase());
		return new Triangulo(punto1Triangulo, punto2Triangulo, punto3Triangulo,
				refraccion, reflectividad, transparencia, KD, KS);
	}

	public static ArrayList<Triangulo> compleja(Camara camara) {
		double refraccion = 0.0;
		double reflectividad = 0.0;
		double transparencia = 0.0;
		Color KD = null;
		Color KS = null;
		cadena = escenaFichero.next();
		String obj = "";
		while (!cadena.equals(".")) {
			if (cadena.equals("obj:")) {
				obj = escenaFichero.next();
				cadena = escenaFichero.next();
			}

			else if (cadena.equals("indicerefraccion:")) {
				refraccion = Float.parseFloat(escenaFichero.next());
				cadena = escenaFichero.next();
			}
			else if (cadena.equals("reflectividad:")) {
				reflectividad = Float.parseFloat(escenaFichero.next());
				cadena = escenaFichero.next();
			}
			else if (cadena.equals("transparencia:")) {
				transparencia = Float.parseFloat(escenaFichero.next());
				cadena = escenaFichero.next();
			}
			else if (cadena.equals("kd:")) {
				KD = new Color(escenaFichero.nextShort(),
						escenaFichero.nextShort(), escenaFichero.nextShort());
				cadena = escenaFichero.next();
			}
			else if (cadena.equals("ks:")) {
				KS = new Color(escenaFichero.nextShort(),
						escenaFichero.nextShort(), escenaFichero.nextShort());
				cadena = escenaFichero.next();
			}
		}
		ArrayList<Triangulo> lTriangulo = LeerObj.leerFigura(obj, refraccion,
				reflectividad, transparencia, KD, KS);
		Matrix4d mT = Operaciones.matrizTraslacion(new Point4d(0, 0, -5, 1));

		for (int i = 0; i < lTriangulo.size(); i++) {
			Matrix4d mRotacion = Operaciones.rotacionY(40);
			Point4d aux = Operaciones.multiplyPointMatrix(lTriangulo.get(i)
					.getPunto1(), mT);
			// aux = Operaciones.multiplyPointMatrix(aux, mRotacion);
			aux = Operaciones.multiplyPointMatrix(aux, camara.getCambioBase());

			Point4d aux2 = Operaciones.multiplyPointMatrix(lTriangulo.get(i)
					.getPunto2(), mT);
			// aux2 = Operaciones.multiplyPointMatrix(aux2, mRotacion);
			aux2 = Operaciones
					.multiplyPointMatrix(aux2, camara.getCambioBase());

			Point4d aux3 = Operaciones.multiplyPointMatrix(lTriangulo.get(i)
					.getPunto3(), mT);
			// aux3 = Operaciones.multiplyPointMatrix(aux3, mRotacion);
			aux3 = Operaciones
					.multiplyPointMatrix(aux3, camara.getCambioBase());

			lTriangulo.get(i).setPunto1(aux);
			lTriangulo.get(i).setPunto2(aux2);
			lTriangulo.get(i).setPunto3(aux3);
		}
		return lTriangulo;
	}
}

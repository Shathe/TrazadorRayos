package trazadorrayos;

import java.awt.Color;

import javax.vecmath.Point4d;
import javax.vecmath.Vector4d;

public class Intensidad {

	public int red = 0;
	public int green = 0;
	public int blue = 0;
	public double IpartDifR = 0;
	public double IpartDifG = 0;
	public double IpartDifB = 0;


	public void calcularIntensidadPunto(double noVisible, Point4d punto, Escena escena,
			Figura figura) {
		double TapanObjetosTranslucidos = 1 - noVisible;
		// Calculas la intensidad en tu punto
		Vector4d normal = figura.getNormal(punto);
		normal.normalize();
		Vector4d rayoAlOjo = new Vector4d();
		rayoAlOjo.sub(escena.getCamara().getPosicion(), punto);
		rayoAlOjo.w = 0;
		rayoAlOjo.normalize();
		Vector4d rayoAlFoco = new Vector4d();
		rayoAlFoco.sub(escena.getFoco().getPosicion(), punto);
		rayoAlFoco.w = 0;
		rayoAlFoco.normalize();
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
		// Caso en el que da negativo debido a que el objeto
		// refracta por dentro
		cosenoDif = Math.abs(cosenoDif);
		IpartDifR = figura.kd.getRed() * escena.getFoco().getColor().getRed()
				/ 255 * cosenoDif * TapanObjetosTranslucidos;
		IpartDifG = figura.kd.getGreen()
				* escena.getFoco().getColor().getGreen() / 255 * cosenoDif
				* TapanObjetosTranslucidos;
		IpartDifB = figura.kd.getBlue() * escena.getFoco().getColor().getBlue()
				/ 255 * cosenoDif * TapanObjetosTranslucidos;
		// especular

		Vector4d reflejoEspecular = new Vector4d(normal);
		// L - 2*(L*N)N
		double aux = 2 * normal.dot(rayoAlFoco);
		reflejoEspecular.scale(aux);
		reflejoEspecular.sub(rayoAlFoco, reflejoEspecular);
		reflejoEspecular.w = normal.w;
		reflejoEspecular.normalize();
		reflejoEspecular.negate();
		// coseno (V, R) ^ n
		double cosenoEsp = reflejoEspecular.dot(rayoAlOjo) / rayoAlOjo.length()
				/ reflejoEspecular.length();
		if (cosenoEsp < 0) cosenoEsp = 0;
		cosenoEsp = Math.pow(cosenoEsp, 150);
		// ks * cos * color foco * grado sombra
		double IpartEspR = figura.ks.getRed()
				* escena.getFoco().getColor().getRed() / 255 * cosenoEsp
				* TapanObjetosTranslucidos;
		double IpartEspG = figura.ks.getGreen()
				* escena.getFoco().getColor().getGreen() / 255 * cosenoEsp
				* TapanObjetosTranslucidos;
		double IpartEspB = figura.ks.getBlue()
				* escena.getFoco().getColor().getBlue() / 255 * cosenoEsp
				* TapanObjetosTranslucidos;
		// intensidad en el punto = ambiental + difusa + especular
		intensidadR += IpartDifR + IpartEspR;
		intensidadG += IpartDifG + IpartEspG;
		intensidadB += IpartDifB + IpartEspB;
		red = (int) (intensidadR);
		green = (int) (intensidadG);
		blue = (int) (intensidadB);
	}

}
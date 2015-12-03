package trazadorrayos;

import javax.vecmath.Point4d;
import javax.vecmath.Vector4d;

public class Intensidad {

	public int red = 0;
	public int green = 0;
	public int blue = 0;
	public double IpartDifR = 0;
	public double IpartDifG = 0;
	public double IpartDifB = 0;
	public double IpartEspR = 0;
	public double IpartEspG = 0;
	public double IpartEspB = 0;


	public void calcularIntensidadPunto(double noVisible, Point4d punto,
			Escena escena, Figura figura, Rayo rayo, int numFoco) {
		double TapanObjetosTranslucidos = 1 - noVisible;
		// Calculas la intensidad en tu punto
		Vector4d normal = figura.getNormal(punto);
		normal.normalize();
		Vector4d rayoAlOjo = new Vector4d();
		rayoAlOjo.sub(escena.getCamara().getPosicion(), punto);
		rayoAlOjo.w = 0;
		rayoAlOjo.normalize();
		Vector4d rayoAlFoco = new Vector4d();
		rayoAlFoco.sub(escena.getFocos().get(numFoco).getPosicion(), punto);
		rayoAlFoco.w = 0;
		rayoAlFoco.normalize();
		// ambiental
		double intensidadR = escena.getCamara().getIntensidadAmbiente()
				* figura.kd.getRed();
		double intensidadG = escena.getCamara().getIntensidadAmbiente()
				* figura.kd.getGreen();
		double intensidadB = escena.getCamara().getIntensidadAmbiente()
				* figura.kd.getBlue();
		// difusa
		double cosenoDif = rayoAlFoco.dot(normal) / normal.length()
				/ rayoAlFoco.length();
		// Caso en el que da negativo debido a que el objeto
		// refracta por dentro
		cosenoDif = Math.abs(cosenoDif);
		IpartDifR = figura.kd.getRed() * escena.getFocos().get(numFoco).getColor().getRed()
				/ 255 * cosenoDif * TapanObjetosTranslucidos;
		IpartDifG = figura.kd.getGreen()
				* escena.getFocos().get(numFoco).getColor().getGreen() / 255 * cosenoDif
				* TapanObjetosTranslucidos;
		IpartDifB = figura.kd.getBlue() * escena.getFocos().get(numFoco).getColor().getBlue()
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
		IpartEspR = figura.ks.getRed() * escena.getFocos().get(numFoco).getColor().getRed()
				/ 255 * cosenoEsp * TapanObjetosTranslucidos;
		IpartEspG = figura.ks.getGreen()
				* escena.getFocos().get(numFoco).getColor().getGreen() / 255 * cosenoEsp
				* TapanObjetosTranslucidos;
		IpartEspB = figura.ks.getBlue() * escena.getFocos().get(numFoco).getColor().getBlue()
				/ 255 * cosenoEsp * TapanObjetosTranslucidos;
		// intensidad en el punto = ambiental + difusa + especular
		intensidadR += IpartDifR + IpartEspR;
		intensidadG += IpartDifG + IpartEspG;
		intensidadB += IpartDifB + IpartEspB;
		red = (int) (intensidadR);
		green = (int) (intensidadG);
		blue = (int) (intensidadB);
	}

}

package trazadorrayos;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.vecmath.Point4d;

public class LeerObj {


	public static ArrayList<Triangulo> leerFigura(String fichero,
			double refraccion, double reflexion, double transparencia,
			Color kd, Color ks) {
		ArrayList<Triangulo> lTriangulos = new ArrayList<Triangulo>();
		ArrayList<Point4d> lV = new ArrayList<Point4d>();
		lV.add(null);
		try {
			Scanner obj = new Scanner(new File(fichero));
			while (obj.hasNextLine()) {
				String v = obj.next();
				switch (v) {
				// vertices
				case "v":
					lV.add(new Point4d(Float.parseFloat(obj.next()), Float
							.parseFloat(obj.next()), Float.parseFloat(obj
							.next()), 1));
					break;
				// face
				case "f":
					int v1 = leerV(obj.next());
					int v2 = leerV(obj.next());
					int v3 = leerV(obj.next());
					Triangulo t = new Triangulo(lV.get(v1), lV.get(v2),
							lV.get(v3), refraccion, reflexion, transparencia, kd, ks);
					lTriangulos.add(t);
					break;
				}
				try {
					obj.nextLine();
				}
				catch (Exception e) {
				}
			}
			obj.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return lTriangulos;
	}

	private static int leerV(String v1) {
		String lv1[] = v1.split("/");

		return Integer.parseInt(lv1[0]);

	}
}

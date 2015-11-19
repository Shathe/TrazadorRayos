/**
 * Iñigo Alonso - 665959
 * Alejandro Dieste - 541892
 */
package trazadorrayos;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;

public class OperacionesVectores {

	/**
	 * Realiza el producto vectorial del vector a con el vector b
	 * @param a
	 * @param b
	 * @return
	 */
	public static Vector4d crossProduct(Vector4d a, Vector4d b) {
		Vector3d aux = new Vector3d(a.x, a.y, a.z);
		Vector3d bux = new Vector3d(b.x, b.y, b.z);
		aux.cross(aux, bux);
		return new Vector4d(aux.x, aux.y, aux.z, a.w);
	}
}

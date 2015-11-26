/**
 * Iñigo Alonso - 665959
 * Alejandro Dieste - 541892
 */
package trazadorrayos;

import javax.vecmath.Matrix4d;
import javax.vecmath.Point4d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;

public class Operaciones {

	/**
	 * Realiza el producto vectorial del vector a con el vector b
	 * 
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
		Matrix4d matrizTransformacionIdentidad = new Matrix4d(1, 0, 0, 0, 0, 1,
				0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
		Matrix4d mT = new Matrix4d(matrizTransformacionIdentidad);
		mT.setElement(3, 0, posicion.x);
		mT.setElement(3, 1, posicion.y);
		mT.setElement(3, 2, posicion.z);
		return mT;
	}
	public static Vector4d sub(Point4d v1, Point4d v2) {
		return new Vector4d(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z, 1);
	}

}

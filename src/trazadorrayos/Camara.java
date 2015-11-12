/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trazadorrayos;
import javax.vecmath.Matrix3d;
import javax.vecmath.Matrix4d;
import javax.vecmath.Vector4d;
import javax.vecmath.Vector3d;
import javax.vecmath.Point4d;
/**
 *
 * @author shathe
 */
public class Camara {
    //figura:camara posicion:7,4,1 distanciaPantalla:2 direccion:-1,1,0

    private Point4d posicion=null;
    private int distanciaPantalla=0;
    private Vector4d direccion=new Vector4d();
    //ahora vienen los vectores del sistema de coordenadas de la camara
    private Vector4d w=new Vector4d();
    private Vector4d u=new Vector4d();
    private Vector4d v=new Vector4d();
    private Matrix4d cambioBase=new Matrix4d();// [u v w posicion]

    
    public Camara(Vector4d direccion, int distanciaPantalla, Point4d posicion) {
    this.posicion=posicion; this.distanciaPantalla=distanciaPantalla; this.direccion=direccion;
    //ahora se calcula el sistema de coordenadas
    //w=normalize(-direccion)
    w.w=-direccion.w;w.y=-direccion.y;w.x=-direccion.x;w.z=-direccion.z;
    w.normalize();

    }

    public Matrix4d getCambioBase() {
        return cambioBase;
    }
    
    
}

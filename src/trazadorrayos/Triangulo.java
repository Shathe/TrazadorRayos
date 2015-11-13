package trazadorrayos;

import javax.vecmath.Point4d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;

public class Triangulo extends Figura {
    private Point4d punto1 = null;
    private Point4d punto2 = null;
    private Point4d punto3 = null;

    public Triangulo(Color color, double refraccion,
                    double reflexion, double kd, double ks, Point4d punto1, Point4d punto2, Point4d punto3) {
            super(color, refraccion, reflexion, kd, ks);
            this.punto1 = punto1;
            this.punto2 = punto2;
            this.punto3 = punto3;
    }
    public Vector4d getNormal(Point4d puntoInterseccion){
        Vector4d vector1=new Vector4d();
        Vector4d vector2=new Vector4d();
        vector1.w=0;vector2.w=0;
        /*
        Calculas los dos vectores que salen del punto uno para poder 
        calcular la normal
        */
        vector1.x=punto2.x-punto1.x;
        vector1.y=punto2.y-punto1.y;
        vector1.z=punto2.z-punto1.z;
        vector2.x=punto3.x-punto1.x;
        vector2.y=punto3.y-punto1.y;
        vector2.z=punto3.z-punto1.z;
        /*
        Ahora el calculo de la normal es el producto vectorial de los dos 
        vectores recien calculados
        */
        Vector4d normal=new Vector4d();
        normal.w=0;
        Vector4d solucion=crossProduct(vector2,vector1);
        solucion.normalize();
        return solucion;
    }

    public Vector4d getNormal2(Point4d puntoInterseccion){
        Vector4d vector1=new Vector4d();
        Vector4d vector2=new Vector4d();
        vector1.w=0;vector2.w=0;
        /*
        Calculas los dos vectores que salen del punto uno para poder 
        calcular la normal
        */
        vector1.x=punto2.x-punto1.x;
        vector1.y=punto2.y-punto1.y;
        vector1.z=punto2.z-punto1.z;
        vector2.x=punto3.x-punto1.x;
        vector2.y=punto3.y-punto1.y;
        vector2.z=punto3.z-punto1.z;
        /*
        Ahora el calculo de la normal es el producto vectorial de los dos 
        vectores recien calculados
        */
        Vector4d normal=new Vector4d();
        normal.w=0;
        Vector4d solucion=crossProduct(vector1,vector2);
        solucion.normalize();
        return solucion;
    }
        public Vector4d crossProduct(Vector4d a, Vector4d b){
        Vector3d aux=new Vector3d(a.x, a.y, a.z);
        Vector3d bux=new Vector3d(b.x, b.y, b.z);
        aux.cross(aux, bux);
        Vector4d solucion=new Vector4d();
        solucion.x=aux.x; solucion.y=aux.y; solucion.z=aux.z; solucion.w=a.w;
        return new Vector4d(aux.x, aux.y, aux.z, a.w);
    }
}

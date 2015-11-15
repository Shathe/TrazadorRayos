/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trazadorrayos;

import javax.vecmath.Point4d;
import javax.vecmath.Vector4d;

/**
 *
 * @author shathe
 */
public class Interseccion {
    
    public static Point4d intersecta(Rayo rayo, Figura figura,Camara camara){
        Point4d interseccion=null;
        if(figura instanceof Esfera) {
            Esfera esfera=(Esfera)figura;
            Vector4d direccion=rayo.getDireccion();
            Point4d puntoRayo=rayo.getPunto();
            Point4d centro=esfera.getCentro();
            /*
            Ahora lo que se hace es sacar la ecuacion (pRayo-centroEsfera)²=radioEsfera²
            y sacar los terminos a,b,c de la ecuacion cuadrática.
            */
            double c=Math.pow(puntoRayo.x,2)+Math.pow(puntoRayo.y,2)+Math.pow(puntoRayo.z,2);
            c+=Math.pow(centro.x,2)+Math.pow(centro.y,2)+Math.pow(centro.z,2);
            c-=2*centro.x*puntoRayo.x-2*centro.y*puntoRayo.y-2*centro.z*puntoRayo.z;
            c-=Math.pow(esfera.getRadio(),2);

            double b=2*direccion.x*puntoRayo.x+2*direccion.y*puntoRayo.y+2*direccion.z*puntoRayo.z;   
            b-=2*centro.x*direccion.x-2*centro.y*direccion.y-2*centro.z*direccion.z;

            double a=Math.pow(direccion.x,2)+Math.pow(direccion.y,2)+Math.pow(direccion.z,2);
            if(4*a*c>0){
                double sol1=(b*b+Math.sqrt(4*a*c))/(2*a);
                double sol2=(b*b-Math.sqrt(4*a*c))/(2*a);
                Point4d primero=new Point4d();
                //Ahora calculas los dos puntos que intersecta si esque hay dos
                primero.x=sol1*direccion.x+puntoRayo.x;
                primero.y=sol1*direccion.y+puntoRayo.y;
                primero.z=sol1*direccion.z+puntoRayo.z;
                Point4d segundo=new Point4d();
                segundo.x=sol2*direccion.x+puntoRayo.x;
                segundo.y=sol2*direccion.y+puntoRayo.y;
                segundo.z=sol2*direccion.z+puntoRayo.z;
                //El de menor distancia con la camara es el que se tiene que devolver
                if(camara.getPosicion().distanceSquared(primero) 
                        <= camara.getPosicion().distanceSquared(primero) ){
                    interseccion=primero;
                }
                else{
                    interseccion=segundo;
                }
                    
            }
        

        }
        else if(figura instanceof Plano) {
            
        }
        else if(figura instanceof Triangulo) {
            
        }
        
        
        
        return interseccion;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trazadorrayos;

import java.awt.Color;
import java.util.ArrayList;
import javax.vecmath.Point4d;
import javax.vecmath.Vector4d;

/**
 *
 * @author shathe
 */
public class OperacionesEscena {
    
    public static Figura FiguraMasCercana(ArrayList<Figura> figuras,Point4d punto, Rayo rayo,Camara camara){
        Figura figura=null;
        double distanciaMenor=Double.MAX_VALUE;
         for (int i=0; i<figuras.size();i++){
             Figura siguienteFigura=figuras.get(i);
             Point4d puntoInterseccion=Interseccion.intersecta(rayo, siguienteFigura, camara);
             if(puntoInterseccion!=null){
                 double distancia=punto.distanceSquared(puntoInterseccion);
                 if(distancia<distanciaMenor){
                     distanciaMenor=distancia;
                     figura=siguienteFigura;
                 }
                 
             }
         }
        
        return figura;
    }
    
    public static Color colorPuntoPantalla(Point4d puntoPixelPantalla,Escena escena,
            int maxDepth,double minIntensity){
        Color color=new Color(0,0,0);

         /* Ahora se crea un rayo que pase por el punto de 
        la pantalla y con direccion (puntoCamara-puntopantalla)
        */
        Vector4d direccion=new Vector4d();
        direccion.x=(puntoPixelPantalla.x-escena.getCamara().getPosicion().x);
        direccion.y=(puntoPixelPantalla.y-escena.getCamara().getPosicion().y);
        direccion.z=(puntoPixelPantalla.z-escena.getCamara().getPosicion().z);
        direccion.w=0;
        Rayo rayo= new Rayo(direccion,puntoPixelPantalla,new Color(255,255,255),1);
        /*
        Ahora hay que ver si el rayo intersecta con alguno de los
        objetos de la escen, y si lo hace, quedarnos unicamente con el objeto
        mas cercano a pa pantalla
        */

        Figura figura=OperacionesEscena.FiguraMasCercana(escena.getFiguras(),
        puntoPixelPantalla,rayo,escena.getCamara());
        if(figura!=null){
            /*
            Ahora tenemos que intersecta con la figura y tenemos que obtener su
            color
            */
            Point4d puntoIntereccion=Interseccion.intersecta(rayo, figura, escena.getCamara());
            color=colorFigura(puntoIntereccion,figura,escena,rayo,maxDepth,minIntensity);
            
        }
        else{
            //No se intersecta con nada
            color=new Color(0,0,0);
        }

        return color;
        
    }
    
    /*
    Devuelve el color que devuelve un rayo al golpear una figura en un punto
    */
    public static Color colorFigura(Point4d punto, Figura figura,Escena escena,
            Rayo rayo,int MaxDepth,double minIntensity){
        
        double reflec=figura.getIndiceReflectividad();
        double refrac=figura.getIndiceRefraccion();
        /*
        Ahora se calcula la direccion del angulo refractado y del angulo reflejado
        */
        
        Vector4d refractado=new Vector4d();
        
        Vector4d normal=figura.getNormal(punto);
        
        //reflejado =>   V-2*(V*N)N
        
        //aux=2*(V*N)
        double aux=2*normal.dot(rayo.getDireccion());
        Vector4d reflejado=new Vector4d();
        //2*(V*N)N
        reflejado.x=aux*normal.x;reflejado.y=aux*normal.y;
        reflejado.z=aux*normal.z;reflejado.w=normal.w;
        //V-2*(V*N)N
        reflejado.x=normal.x-reflejado.x;
        reflejado.z=normal.z-reflejado.z;
        reflejado.y=normal.y-reflejado.y;
        reflejado.w=normal.w-reflejado.w;
        
        //ahora calculas el angulo reflejado
        
        

        return null;
    }
}

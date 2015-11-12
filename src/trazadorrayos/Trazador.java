/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trazadorrayos;

import java.io.File;
import java.io.FileNotFoundException;
import javax.vecmath.Matrix3d;
import javax.vecmath.Matrix4d;
import javax.vecmath.Vector4d;
import javax.vecmath.Vector3d;
import java.util.Scanner;

/**
 *
 * @author shathe
 */
public class Trazador {
        /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
    public void  leerEscena(String fichero){
        		try {
			Scanner relFile = new Scanner(new File(fichero));
			String oldNeed = "";
			String need = "";
			String doc = "";
			int relevancy = 0;
			// recorremos el fichero
			while (relFile.hasNextLine()) {
				need = relFile.next();
				// Cargar escena del fichero
				
                                
                                
                                
                                
                                
                                
                                
				try {
					relFile.nextLine();
				}
				catch (Exception e) {
				}

			}

                        relFile.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }
}

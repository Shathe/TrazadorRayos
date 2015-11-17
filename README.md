# TrazadorRayos

Los siguientes pasos que faltan son:

EL COLOR DEL RAYO AFECTA A KS*COLOR.RAYO
EL LOS NUEVO COLORES REL RAYO SERANEL COLOR QUE LLEVAS ACUMULADO (LO TENDRAS QUE NORMALIZAR PERO NO LO NORMALICES GAURDANDOLO, PORQUE
SINO, AL SUMAR LUEGO LOS ACUMULADOS LOS PESOS NO SERAN LOS MISMOS)

la intensidad que pasa al rayo reflejado no es reflec*rayo.getIntensidad es reflec*rayo.getIntensidad*cosenoModelo2
Kd es el color del objeto(hay que pensar tambien si se cuenta la luz del rayo)
Ks es como se comporta el objeto especular(la anerior luz 255,255,255)
Angulo refractado en la esfera hay que esperar a que vuelv a refractarse (devolver solo el rayo refactado)
que vaa la especular
//cosenos negatios?
//error de colores cambiado foco por ejemplo


 
Una vez hehco esto hay que mirar errores de codigo, he empezado a mirar y hace mal la interseccion de la esfera, el programa al ejecutar sale una imagen negra, y los punto sde la interseccion de la esfera parece que se calcula mal, eso ya lo hare yo, pero empezar a mirar mas errores

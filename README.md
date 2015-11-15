# TrazadorRayos

Los siguientes pasos que faltan son:



En el MAIN del trazador, estamos cuadno tienes el punto  de la pantall(centro del pixel al calcular)a en coordenadas de la camara y tienes que pasarlo al del mundo, pero no he comprobado que el nuevo putno en coordanadas del mudno tenga sentido y parezca que este bien (una vez secalcula hay que reescalarlo segun k, el 4º elemento del punto)


CUIDADO: el calculo de la normal para saber si ese punto se ve o no seve desde la camara sirve solo para la esfera(objeto 3d) qe tiene para su superficie uana cara visible y una oculta, para los triangulo y planos, ambas caras son visibles y no hay que hacer esa comprobacion


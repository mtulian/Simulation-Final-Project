package espera;

import java.util.ArrayList;

public class Tiempos {
public static char busca(Espera modelo){
    
    char evento = 'z';
    ArrayList<Espera.Lev> jeje = modelo.getLEV();
    int indice = 0;
    double hora = modelo.getTFS() + 100;
    
    for (int i = 0; i < jeje.size(); i++) {
        if (jeje.get(i).usado && jeje.get(i).hora < hora && modelo.getReloj() < jeje.get(i).hora) { 
            indice = i;
            hora = jeje.get(i).hora;
            }
    }
    jeje.get(indice).usado = false;
    modelo.setReloj(jeje.get(indice).hora);
    evento = jeje.get(indice).C;
    return evento;
}
}

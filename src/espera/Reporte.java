package espera;

import espera.Espera.Vta;
import generadores.Libreria;
import java.util.ArrayList;


public class Reporte {

public static double[] funcion(Espera e){
    double[] aux = new double[3];
    
    double q = e.getAreaBajoQ() / e.getT();
    //System.out.println("Número medio en cola: " + Libreria.darFormato(q, 3) +" areaBajoQ: "+Libreria.darFormato(e.getAreaBajoQ(), 3));//+"Tiempo final: "+e.getT()
    double b = e.getAreaBajoB() / e.getT();
    //System.out.println("Utilización del servidor: " + Libreria.darFormato(b, 3) + " areaBajoB: "+ Libreria.darFormato(e.getAreaBajoB(),3) + " Tiempo final: " + Libreria.darFormato(e.getT(),3));//
    double d = e.getDemoraTotal() / e.getCli_at();
    //System.out.println("Demora promedio: " + Libreria.darFormato(d, 3) + " Demora Total: " + Libreria.darFormato(e.getDemoraTotal(),3)+ " Clientes atendidos: "+ e.getCli_at());//
    
    aux[0] = q; //número medio en cola
    aux[1] = b; //utilización del servidor
    aux[2] = d; //demora promedio
    
    muestraSeparador();
    System.out.println("           Parametros utilizados");
    System.out.println("Media arribo: " + e.getMediaArribo());
    System.out.println("Media partida: " + e.getMediaPartida());
    System.out.println("Tiempo final de simulación: " + e.getTFS());
    muestraSeparador();
    System.out.println("           Medidas de rendimiento");
    muestra(q,b,d);
    muestraSeparador();
    System.out.println("           Valores para graficar");
    
    /*modelo, parametro, booleano para la media, booleano para la varianza
    parametro 0 demora media en sistema
    parametro 1 demora media en cola
    */
    graficar(e,0,false);
    graficar(e,1,false);
    
    muestraSeparador();

    return aux;
}
public static double calculaError (double valorExacto, double valorComparar) {
double aux = (valorExacto > valorComparar) ? (valorExacto - valorComparar) / valorExacto : (valorComparar - valorExacto) / valorExacto ;

return aux;
}
public static void muestra (double mediaCola, double mediaUtilizacionServidor, double mediaDemora){
    double errorCola = calculaError(0.5, mediaCola);
    double errorUtilizacionServidor = calculaError(0.5, mediaUtilizacionServidor);
    
    System.out.println("Número medio en cola: " + Libreria.darFormato(mediaCola, 5) + " Error: " + Libreria.darFormato(errorCola,5));
    System.out.println("Utilización del servidor: " + Libreria.darFormato(mediaUtilizacionServidor, 5)+ " Error: " + Libreria.darFormato(errorUtilizacionServidor,5));
    System.out.println("Demora promedio: " + Libreria.darFormato(mediaDemora, 5));
}
public static void muestraSeparador() {
    System.out.println("------------------------------------------");
}
public static void graficar(Espera e, int tipo, boolean v) {
    String auxMedia = "";
    String auxVarianza = "";
    ArrayList parametro = new ArrayList<Double>();
    double media = 0;
    double mediaVar = 0;
    
    switch(tipo){
        case 0: auxMedia += "Demora media en sistema \n {";
                auxVarianza += "Varianza de la demora en sistema \n {";
                parametro = e.getDemoraSis();
                mediaVar = e.getMediaArribo();
            break;
        case 1: auxMedia += "Demora media en cola \n {";
                auxVarianza += "Varianza de la demora cola \n {";
                parametro = e.getDemora();
                mediaVar = e.getMediaPartida();
            break;
        case 2: auxMedia += "Utilización esperada del servidor \n {";
                auxVarianza += "Varianza de la utilización del servidor \n {";
                parametro = e.getDemora();
                mediaVar = e.getMediaArribo();
            break;
    }
    for(int i = 0; i < parametro.size(); i += 1){
        if(i%500 == 0 && i != 0){
            auxMedia += "\n";
            auxVarianza += "\n";
        }
        
        media += (double) parametro.get(i);
        if(i != 0){
            auxMedia += Libreria.darFormato(media/i, 4);
            auxVarianza += Libreria.darFormato(Math.pow((media/i) - mediaVar,2), 4);
            if(i + 1 != parametro.size()){
                auxMedia += ", ";
                auxVarianza += ", ";
            }
        }
        else {
            auxMedia += Libreria.darFormato(media, 4) + ", ";
            auxVarianza += Libreria.darFormato(media, 4) + ", ";
        }
    }
    auxMedia += "}";
    auxVarianza += "}";
    
    if(v) {
        System.out.println(auxMedia + "\n" + auxVarianza);
    } else {
        System.out.println(auxMedia);
    }
    
}
public static void tiempoEA(Espera e){
    ArrayList<Vta> aux = e.getVTA();
    ArrayList<Double> tiempos = new ArrayList<>();
    tiempos.add(aux.get(0).getHora());
    for (int i = 1; i < aux.size(); i++) {
        double help = aux.get(i).hora - aux.get(i-1).hora;
        tiempos.add(help);
    }
}

}

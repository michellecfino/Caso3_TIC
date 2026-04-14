public class Broker extends Thread {
    private Buzon entrada;
    private Buzon alertas;
    private Buzon clasificacion;
    private int capacidad;

    public Broker(Buzon entrada, Buzon alertas, Buzon clasificacion, int capacidad ){
        this.entrada = entrada;
        this.alertas = alertas;
        this.clasificacion = clasificacion;
        this.capacidad = capacidad;

    }

    @Override
    public void run(){
        int procesados = 0;
        while(procesados<capacidad){

            Evento evento = entrada.retirar();
            //Esto es para saber si son anómalos, si es múltiplo de 8 entonces está mal
            if((int)Math.random()* 200 %8 == 0){
                alertas.almacenar(evento);
            }
            else{
                clasificacion.almacenar(evento);
            }
            procesados ++;
        }
        alertas.almacenar(new Evento("Fin",-1, 2));
    }

}

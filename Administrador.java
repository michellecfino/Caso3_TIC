public class Administrador extends Thread {
    private Buzon alertas;
    private Buzon clasificacion;
    private int numclasificadores;

    public Administrador(Buzon alertas, Buzon clasificacion, int numclasificadores){
        this.alertas = alertas;
        this.clasificacion = clasificacion;
        this.numclasificadores = numclasificadores;
    }

    @Override
    public void run(){
        boolean avanza = true;
        while(avanza){

            Evento evento = alertas.retirar();
            if(evento.getEstado()==2){
                avanza = false;
                for(int i = 0; i <numclasificadores; i++){
                    clasificacion.almacenar(new Evento("Fin",-1,2));
                }
                System.out.println("Mensaje de aviso");
            }
            else{
                int aleatorio = (int)(Math.random()*21);
                if(aleatorio%4==0){
                    clasificacion.almacenar(new Evento(evento.getId(), evento.getDestino(), 0));
                    System.out.println("Evento "+evento.getId()+" normal");
                }
                else{
                    System.out.println("Evento "+evento.getId()+" anómalo");
                }
            }
        }
    }
    
}

public class Sensor extends Thread {
    
    private int id;
    private int numbase;
    private int numservidor;
    private Buzon entrada;

    public Sensor(int id, int numbase, int numservidor, Buzon entrada){
        this.id = id;
        this.numbase = numbase;
        this.numservidor = numservidor;
        this.entrada = entrada;
    }

    @Override
    public void run(){
        int total = id*numbase;
        for(int i = 1; i<=total;i++){
            String idEvento = "Sensor"+id+"Evento-"+i;
            int destino = (int)(Math.random()*numservidor)+1;
            Evento evento = new Evento(idEvento, destino, 0);

            entrada.almacenar(evento);


        }
}
}

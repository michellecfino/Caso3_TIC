public class Clasificador extends Thread {

    private Buzon clasificacion;
    private Buzon[] consolidacion;
    private int id;

    public Clasificador(int id, Buzon clasificacion, Buzon[] consolidacion) {
        this.id = id;
        this.clasificacion = clasificacion;
        this.consolidacion = consolidacion;
    }

    @Override
    public void run() {
        boolean avanza = true;
        while (avanza) {

            Evento evento = clasificacion.retirar();
            if (evento.getEstado() == 2) {
                avanza = false;
                for (int i = 0; i < consolidacion.length; i++) {
                    consolidacion[i].almacenar(new Evento("FIN_SISTEMA", -1, 2));}
    
                System.out.println("Clasificador " + this.getName() + " ha finalizado");
            } else {
                
                System.out.println("Evento " + evento.getId() + " procesado por el clasificador " + id);
                int indiceBuzon = evento.getDestino() - 1;
                Buzon buzonDestino = consolidacion[indiceBuzon];
                
                while (buzonDestino.estaLleno()) {
                    Thread.yield();
                }
                
                buzonDestino.almacenar(evento);
            }

        }

    }

}
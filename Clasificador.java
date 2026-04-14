public class Clasificador extends Thread {

    private Buzon clasificacion;
    private Buzon[] consolidacion;
    private int id;
    private static int terminados = 0;
    private int numclasificadores;

    public Clasificador(int id, Buzon clasificacion, Buzon[] consolidacion, int numclasificadores) {
        this.id = id;
        this.clasificacion = clasificacion;
        this.consolidacion = consolidacion;
        this.numclasificadores = numclasificadores;
    }

    @Override
    public void run() {
        boolean avanza = true;
        while (avanza) {
            Evento evento = clasificacion.retirar();
            
            if (evento.getEstado() == 2) {
                avanza = false;
                synchronized (Clasificador.class) {
                    terminados++;
                    if (terminados == numclasificadores) {
                        for (int i = 0; i < consolidacion.length; i++) {
                            consolidacion[i].almacenar(new Evento("FIN", -1, 2));
                        }
                        System.out.println("Último clasificador (" + id + ") cerró los servidores.");
                    }
                }
            } else {
                System.out.println("Evento " + evento.getId() + " procesado por el clasificador " + id);
                int indiceBuzon = evento.getDestino() - 1;

                if (indiceBuzon >= 0 && indiceBuzon < consolidacion.length) {
                    Buzon buzonDestino = consolidacion[indiceBuzon];

                    while (buzonDestino.estaLleno()) {
                        Thread.yield();
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            System.out.println("Clasificador " + id + " interrumpido.");
                        }
                    }
                    
                    buzonDestino.almacenar(evento);
                }
            }
        }
    }
}
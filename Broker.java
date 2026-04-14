public class Broker extends Thread {
    private Buzon entrada;
    private Buzon alertas;
    private Buzon clasificacion;
    private int capacidad;

    public Broker(Buzon entrada, Buzon alertas, Buzon clasificacion, int capacidad) {
        this.entrada = entrada;
        this.alertas = alertas;
        this.clasificacion = clasificacion;
        this.capacidad = capacidad;

    }

    @Override
    public void run() {
        int procesados = 0;
        System.out.println("Broker iniciado. Esperando " + capacidad + " eventos.");
        while (procesados < capacidad) {
            Evento evento = entrada.retirar();

            if ((int) (Math.random() * 200) % 8 == 0) {
                alertas.almacenar(evento);
            } else {
                clasificacion.almacenar(evento);
            }
            procesados++;

            if (procesados % 10 == 0) {
                System.out.println(">>> Broker ha procesado " + procesados + " eventos...");
            }
        }
        System.out.println(">>> Broker TERMINÓ. Enviando señal de FIN.");
        alertas.almacenar(new Evento("Fin", -1, 2));
    }
}
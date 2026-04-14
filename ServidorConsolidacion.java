public class ServidorConsolidacion extends Thread {
    private int id;
    private Buzon miBuzon;

    public ServidorConsolidacion(int id, Buzon miBuzon) {
        this.id = id;
        this.miBuzon = miBuzon;
    }

    @Override
    public void run() {
        boolean encendido = true;

        while (encendido) {
            Evento e = miBuzon.retirar();

            if (e.getEstado() == 2) {
                encendido = false;
                System.out.println("Servidor " + id + ": Recibida señal de apagado. Finalizando...");
            } else {
                try {
                    long tiempoProcesamiento = (long) (Math.random() * 900) + 100;
                    Thread.sleep(tiempoProcesamiento);

                    System.out.println("Servidor " + id + ": Consolidando evento [" + e.getId() + "] - Destino: "
                            + e.getDestino());
                } catch (InterruptedException ex) {
                    System.out.println("Servidor " + id + " interrumpido: " + ex.getMessage());
                }
            }
        }
    }
}
import java.util.*;

public class Buzon{

    private Queue<Evento> cola = new LinkedList<>();
    private int capacidad;

    public Buzon(int capacidad){
        this.capacidad = capacidad;
    }

    public synchronized void almacenar(Evento evento){

        if (capacidad != -1){

            while( cola.size() == capacidad){

                try {
                    wait();
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
        cola.add(evento);
        notifyAll();
    }

    public synchronized Evento retirar(){
        while(cola.isEmpty()){
            try {
                wait();
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        Evento evento = cola.poll();
        return evento;
    }

    public synchronized boolean estaLleno(){
        return capacidad != -1 && cola.size() == capacidad;
    }

}

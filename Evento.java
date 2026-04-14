public class Evento {
    
    private String id;
    private int destino;
    private int estado;

    public Evento( String id, int destino, int estado){
        this.id = id;
        this.destino = destino;
        this.estado = estado;
    }

    public String getId(){
        return id;
    }

    public int getDestino(){
        return destino;
    }

    public int getEstado(){
        return estado;
    }
}

import java.io.BufferedReader;
import java.io.FileReader;

public class Simulador {

    public static void main(String[] args) {
        int numsensores = 0;
        int numbaseeventos = 0;
        int numclasificadores = 0;
        int numservidores = 0;
        int capconsolidacion = 0;
        int capclasificacion = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("config.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                if (linea.startsWith("numsensores")) {
                    numsensores = Integer.parseInt(linea.split("=")[1].trim());
                } 
                else if (linea.startsWith("numbaseeventos")) {
                    numbaseeventos = Integer.parseInt(linea.split("=")[1].trim());
                } 
                else if (linea.startsWith("numclasificadores")) {
                    numclasificadores = Integer.parseInt(linea.split("=")[1].trim());
                } 
                else if (linea.startsWith("numservidores")) {
                    numservidores = Integer.parseInt(linea.split("=")[1].trim());
                } 
                else if (linea.startsWith("capconsolidacion")) { // Aquí corregí el nombre
                    capconsolidacion = Integer.parseInt(linea.split("=")[1].trim());
                } 
                else if (linea.startsWith("capclasificacion")) {
                    capclasificacion = Integer.parseInt(linea.split("=")[1].trim());
                }
            }


            Buzon entrada = new Buzon(-1);
            Buzon alertas = new Buzon(-1);
            Buzon clasificacion = new Buzon(capclasificacion);
            
            Buzon[] consolidacion = new Buzon[numservidores];
            for (int i = 0; i < numservidores; i++) {
                consolidacion[i] = new Buzon(capconsolidacion);
            }
            int totalGlobal = 0;
            for (int i = 1; i <= numsensores; i++) {
                totalGlobal += (i * numbaseeventos);
            }

            for (int i = 0; i < numservidores; i++) {
                new ServidorConsolidacion(i + 1, consolidacion[i]).start();
            }

            for (int i = 0; i < numclasificadores; i++) {
                new Clasificador(i + 1, clasificacion, consolidacion).start();
            }

            new Administrador(alertas, clasificacion, numclasificadores).start();
            new Broker(entrada, alertas, clasificacion, totalGlobal).start();

            for (int i = 1; i <= numsensores; i++) {
                new Sensor(i, numbaseeventos, numservidores, entrada).start();
            }

            System.out.println(">>> Simulación iniciada exitosamente.");

        } catch (Exception e) {
            System.out.println("Error jeje: " + e.getMessage());
        }
    }
}
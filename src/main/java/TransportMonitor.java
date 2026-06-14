import Strategies.*;
import java.util.ArrayList;

public class TransportMonitor {

    private ArrayList<TransportObserver> observers = new ArrayList<>();
    private TransportStrategy transportStrategy;
    private boolean running = false; //Para controlar el estado del hilo

    public TransportMonitor(TransportStrategy transportStrategy) {
        this.transportStrategy = transportStrategy;
    }

    public void suscribe(TransportObserver transportObserver) {
        observers.add(transportObserver);
    }

    public void unsuscribe(TransportObserver transportObserver) {
        observers.remove(transportObserver);
    }

    public void setStrategy(TransportStrategy transportStrategy) {
        this.transportStrategy = transportStrategy;
    }

    // CORRECCIÓN DEL PROFE: Se extrae la creación del snapshot a un método privado
    private TransportSnapshot createSnapshot() {
        return new TransportSnapshot(
                transportStrategy.getName(),
                transportStrategy.getCost(),
                transportStrategy.getDistance(),
                transportStrategy.getETA()
        );
    }

    public void start(int intervalMS) {

        if(running) return; // Si ya está corriendo, no hacemos nada
        running = true; // Marcamos que el monitor está corriendo

        Thread monitorThread = new Thread(() -> {
            while(running){
                try{
                    Thread.sleep(intervalMS); //Manejamos la excepción obligatoria

                    // Ahora el código queda mucho más limpio llamando al método extraído
                    TransportSnapshot transportSnapshot = createSnapshot();

                    observers.forEach(item -> item.onUpdate(transportSnapshot)); //Se notifica a los observadores
                }catch (InterruptedException e) {
                    LoggerGlobal.getInstance().logError("El monitor de transporte fue interrumpido.");
                    Thread.currentThread().interrupt(); // Restaurar el estado de interrupción
                }
            }
        });

        monitorThread.start(); //Arranca el hilo de fondo
    }
}
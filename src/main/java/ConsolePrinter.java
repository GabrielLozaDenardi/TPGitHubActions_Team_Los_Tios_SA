public class ConsolePrinter implements TransportObserver {
    LoggerGlobal logger;
    public ConsolePrinter() {
        this.logger = LoggerGlobal.getInstance();
    }

    public void onUpdate(TransportSnapshot transportSnapshot) {
        logger.logInfo(String.format("Transporte %s - Ruta %d \n", transportSnapshot.name(), transportSnapshot.eta()));
        logger.logDebug(String.format("Distancia %f - Costo %f \n", transportSnapshot.distance(), transportSnapshot.cost()));
    }
}

public class AlertObserver implements TransportObserver {

    private AlertService alertService;
    private LoggerGlobal logger;

    public AlertObserver(AlertService alertService, LoggerGlobal logger) {
        this.alertService = alertService;
        this.logger = logger;
    }

    @Override
    public void onUpdate(TransportSnapshot snapshot) {
        if (alertService.shouldAlertCost(snapshot.cost())) {
            logger.logWarning("Alerta de Costo: El transporte " + snapshot.name() +
                    " superó el límite. Costo actual: $" + snapshot.cost());
        }

        if (alertService.shouldAlertETA(snapshot.eta())) {
            logger.logError("Alerta de Tiempo: El transporte " + snapshot.name() +
                    " superó el límite. ETA actual: " + snapshot.eta() + " min");
        }
    }
}
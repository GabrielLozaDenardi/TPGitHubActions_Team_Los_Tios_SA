import Strategies.*; // Importamos la carpeta donde están el Taxi, Colectivo, etc.
import java.util.Scanner;

public class Main
{
    public static void main (String[] args)
    {
        //1) Obtener la instancia del logger y loggear el inicio de la aplicación.
        LoggerGlobal logger = LoggerGlobal.getInstance();
        logger.logDebug ("Iniciando...");

        //2) Crear al menos 2 estrategias de transporte distintas.
        TransportStrategy taxi = new TaxiStrategy();
        TransportStrategy bicicleta = new BicicletaStrategy();
        TransportStrategy colectivo = new ColectivoStrategy();

        //3) Crear un TransportMonitor con una estrategia inicial.
        TransportMonitor monitoreo = new TransportMonitor(taxi);

        //4) Suscribir un ConsolePrinter y un AlertObserver.
        ConsolePrinter printer = new ConsolePrinter();

        //Creamos el servicio de alertas con los umbrales de su código (30000 y 30)
        AlertService alertService = new ThresholdAlertService(30000.0, 30);

        //Inyectamos el servicio y el logger al AlertObserver
        AlertObserver observer = new AlertObserver(alertService, logger);

        //ahora si las suscribo 
        monitoreo.suscribe(printer);
        monitoreo.suscribe(observer);

        //5) Llamar a start() y observar las actualizaciones en tiempo real por al menos 10 ciclos.
        monitoreo.start(2000); //2 segundos para no saturar la consola

        //6) Permitir al usuario cambiar la estrategia activa desde la consola sin detener el monitor.
        Scanner scanner = new Scanner(System.in);
        while (true)
        {
            System.out.println("\n--- MENÚ DE ESTRATEGIAS ---");
            System.out.println("1 - Cambia la estrategia a Taxi");
            System.out.println("2 - Cambiar estrategia a Colectivo");
            System.out.println("3 - Cambiar estrategia a Bicicleta");
            System.out.print("Seleccionar una opción: ");

            String opcion = scanner.nextLine();

            switch (opcion.trim())
            {
                case "1":
                    monitoreo.setStrategy(taxi);
                    logger.logDebug("Estrategia cambiada a Taxi.");
                    break;

                case "2":
                    monitoreo.setStrategy(colectivo);
                    logger.logDebug("Estrategia cambiada a Colectivo.");
                    break;

                case "3":
                    monitoreo.setStrategy(bicicleta);
                    logger.logDebug("Estrategia cambiada a Bicicleta.");
                    break;

                default:
                    logger.logWarning("Opción no válida.");
                    break;
            }
        }
    }
}
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggerGlobal {

    // campos
    private static LoggerGlobal instance;
    String amarillo = "\u001B[33m";
    String verde = "\u001B[32m";
    String gris = "\u001B[90m";
    String rojo = "\u001B[31m";
    String reset = "\u001B[0m";

    // constructor private para asegurar unica instancia
    private LoggerGlobal() {
    }

    // metodos
    // CORRECCIÓN DEL PROFE: Se agrega "synchronized" para seguridad en multihilos
    public static synchronized LoggerGlobal getInstance() {
        // si existe la instancia la devuelve, si no la crea
        if (instance == null) {
            instance = new LoggerGlobal();
        }
        return instance;
    }

    private String getTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void logWarning(String msg) {
        System.out.println(amarillo + "[" + getTimestamp() + "] [WARN] " + msg + reset);
    }

    public void logDebug(String msg) {
        System.out.println(verde + "[" + getTimestamp() + "] [DEBUG] " + msg + reset);
    }

    public void logInfo(String msg) {
        System.out.println(gris + "[" + getTimestamp() + "] [INFO] " + msg + reset);
    }

    public void logError(String msg) {
        System.out.println(rojo + "[" + getTimestamp() + "] [ERROR] " + msg + reset);
    }
}

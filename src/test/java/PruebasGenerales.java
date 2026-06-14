import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PruebasGenerales {

    @Test
    public void testUmbralCosto() {
        ThresholdAlertService prueba = new ThresholdAlertService(10, 10);

        assertFalse(prueba.shouldAlertCost(9), "No alerta si es menor el umbral");
        assertFalse(prueba.shouldAlertCost(10), "No deberia alertar si es igual");
        assertTrue(prueba.shouldAlertCost(11), "Alerta si supera el umbral");
    }

    @Test // ¡Faltaba esta etiqueta!
    public void testUmbralTiempo() {
        ThresholdAlertService prueba = new ThresholdAlertService(10, 10);

        assertTrue(prueba.shouldAlertETA(11), "Alerta si supera umbral");
        assertFalse(prueba.shouldAlertETA(9), "No alerta si se mantiene por debajo");
    }
}
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class AlertObserverFakeTest {

    @Test
    public void testAlertObserverLoggeaCuandoElServicioSiempreAlerta() {
        // 1. Arrange
        AlertService fakeSiempreAlerta = new AlwaysAlertService();

        // ¡ACÁ ESTÁ LA MAGIA! Creamos el mock en lugar del Fake
        LoggerGlobal mockLogger = mock(LoggerGlobal.class);

        AlertObserver observer = new AlertObserver(fakeSiempreAlerta, mockLogger);
        TransportSnapshot snapshot = new TransportSnapshot("Colectivo 50", 10.0, 12.5, 5);

        // 2. Act
        observer.onUpdate(snapshot);

        // 3. Assert (Le preguntamos a Mockito si los métodos fueron llamados)
        verify(mockLogger, times(1)).logWarning(anyString()); // Verifica que se llamó 1 vez al warning
        verify(mockLogger, times(1)).logError(anyString());   // Verifica que se llamó 1 vez al error
    }

    @Test
    public void testAlertObserverNoLoggeaNadaCuandoElServicioNuncaAlerta() {
        // 1. Arrange
        AlertService fakeNuncaAlerta = new NeverAlertService();
        LoggerGlobal mockLogger = mock(LoggerGlobal.class);

        AlertObserver observer = new AlertObserver(fakeNuncaAlerta, mockLogger);
        TransportSnapshot snapshot = new TransportSnapshot("Taxi VIP", 999999.0, 15.0, 500);

        // 2. Act
        observer.onUpdate(snapshot);

        // 3. Assert (Verificamos que NUNCA se llamó a los logs)
        verify(mockLogger, never()).logWarning(anyString());
        verify(mockLogger, never()).logError(anyString());
    }
}
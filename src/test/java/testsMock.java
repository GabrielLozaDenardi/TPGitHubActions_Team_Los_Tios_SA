import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.*;

public class testsMock {

    private AlertService alertServiceMock;
    private LoggerGlobal loggerMock; // Agregamos un mock del logger para que compile
    private AlertObserver observer;

    @BeforeEach // En JUnit 5, @Before se cambia a @BeforeEach
    public void setUp() {
        alertServiceMock = mock(AlertService.class);
        loggerMock = mock(LoggerGlobal.class); // Inicializamos el mock

        // Pasamos ambos mocks al observer para respetar su constructor
        observer = new AlertObserver(alertServiceMock, loggerMock);
    }

    @Test
    public void testAlertaCosto_LlamaLogWarning() {
        TransportSnapshot snapshot = new TransportSnapshot("Camion-A1", 150.0, 500.5, 10);

        when(alertServiceMock.shouldAlertCost(150.0)).thenReturn(true);
        when(alertServiceMock.shouldAlertETA(10)).thenReturn(false);

        observer.onUpdate(snapshot);

        // Verificamos la interacción con el mock
        verify(alertServiceMock, times(1)).shouldAlertCost(150.0);
        verify(loggerMock, times(1)).logWarning(anyString()); // Verifica que se intentó loggear
    }

    @Test
    public void testAlertaETA_LlamaLogError() {
        TransportSnapshot snapshot = new TransportSnapshot("Van-B2", 50.0, 20.0, 45);

        when(alertServiceMock.shouldAlertCost(50.0)).thenReturn(false);
        when(alertServiceMock.shouldAlertETA(45)).thenReturn(true);

        observer.onUpdate(snapshot);

        verify(alertServiceMock, times(1)).shouldAlertETA(45);
        verify(loggerMock, times(1)).logError(anyString()); // Verifica que se intentó loggear
    }

    @Test
    public void testSinAlertas_NoLlamaAlLogger() {
        TransportSnapshot snapshot = new TransportSnapshot("Moto-C3", 10.0, 5.0, 5);

        when(alertServiceMock.shouldAlertCost(10.0)).thenReturn(false);
        when(alertServiceMock.shouldAlertETA(5)).thenReturn(false);

        observer.onUpdate(snapshot);

        verify(alertServiceMock, times(1)).shouldAlertCost(10.0);
        verify(loggerMock, never()).logWarning(anyString()); // Verifica que NO se loggeó
        verify(loggerMock, never()).logError(anyString());
    }
}
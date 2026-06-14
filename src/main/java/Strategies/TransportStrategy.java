package Strategies;
public interface TransportStrategy {
    String getName(); // nombre del transporte
    double getCost(); // costo estimado del viaje en pesos
    double getDistance(); // distancia al origen en km
    int getETA(); // tiempo estimado de arribo en minutos
}

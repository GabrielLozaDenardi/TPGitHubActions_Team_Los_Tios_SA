package Strategies;
import java.util.Random;

public class ColectivoStrategy implements TransportStrategy {
    private final Random random = new Random();

    @Override
    public String getName() {
        return "Colectivo";
    }

    @Override
    public double getCost() {
        double minCost = 1720; //precio de pasaje
        double maxCost = 3440; // precio dos pasajes
        return minCost + (maxCost - minCost) * random.nextDouble();
    }

    @Override
    public double getDistance() {
        double minDist = 1;
        double maxDist = 25;
        return minDist + (maxDist - minDist) * random.nextDouble();
    }

    @Override
    public int getETA() {
        int minEta = 5;
        int maxEta = 50;
        return random.nextInt((maxEta - minEta) + 1) + minEta;
    }
}

package Strategies;
import java.util.Random;

public class BicicletaStrategy implements TransportStrategy {
    private final Random random = new Random();

    @Override
    public String getName() {
        return "Bicicleta";
    }

    @Override
    public double getCost() {
        double minCost = 1000;
        double maxCost = 25000;
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
        int maxEta = 30;
        return random.nextInt((maxEta - minEta) + 1) + minEta;
    }
}
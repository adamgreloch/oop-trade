package pl.edu.mimuw.trade;

import pl.edu.mimuw.trade.stock.Simulation;

import java.io.IOException;

public class Main {

  public static void main(String[] args) throws IOException {
/*
    Simulation simulation = new Simulation(capitalist);

    CareerStrategy conservative = new Conservative();

    StudyingStrategy workaholic = new Workaholic();
    StudyingStrategy economical = new Economical(500);

    PurchaseStrategy technophobe = new Technophobe();
    Occupation farmer = new Farmer();
    Occupation engineer = new Engineer();
    Occupation craftsman = new Craftsman();

    ProductionStrategy random = new Random();
    ProductionStrategy shortsighted = new Shortsighted(simulation.stock());

    Productivity productivity = new Productivity(100, 100,
            100, 100, 100);

    Worker w1 = new Worker(simulation, productivity,
            farmer, conservative, technophobe, shortsighted, economical);
    w1.giveStartingResources(100, 100, 100, 100, 100);

    Worker w2 = new Worker(simulation, productivity,
            engineer, conservative, technophobe, random, workaholic);
    w2.giveStartingResources(100, 100, 100, 100, 100);

    Worker w3 = new Worker(simulation, productivity,
            craftsman, conservative, technophobe, random, workaholic);
    w3.giveStartingResources(100, 100, 100, 100, 100);

    SpeculationStrategy average = new Average(simulation, 3);

    Speculator s1 = new Speculator(simulation, average);
    Speculator s2 = new Speculator(simulation, average);
    Speculator s3 = new Speculator(simulation, average);

    simulation.stock().setFallBackPrices(12.0, 10.0, 105.0, 10.0);

    simulation.addAgents(w1, w2, w3, s1, s2, s3);
    simulation.run(15);

*/
    Adapter adapter = new Adapter("in.json");
    Simulation simulation = adapter.readSimulationProperties();
    simulation.run(adapter.simulationDuration());
  }
}


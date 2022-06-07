package pl.edu.mimuw;

import pl.edu.mimuw.agents.Speculator;
import pl.edu.mimuw.agents.Worker;
import pl.edu.mimuw.agents.career.*;
import pl.edu.mimuw.agents.production.ProductionStrategy;
import pl.edu.mimuw.agents.production.Random;
import pl.edu.mimuw.agents.production.Shortsighted;
import pl.edu.mimuw.agents.productivity.Productivity;
import pl.edu.mimuw.agents.purchase.PurchaseStrategy;
import pl.edu.mimuw.agents.purchase.Technophobe;
import pl.edu.mimuw.agents.speculation.Average;
import pl.edu.mimuw.agents.speculation.SpeculationStrategy;
import pl.edu.mimuw.agents.studying.StudyingStrategy;
import pl.edu.mimuw.agents.studying.Workaholic;
import pl.edu.mimuw.stock.Capitalist;
import pl.edu.mimuw.stock.Simulation;
import pl.edu.mimuw.stock.StockStrategy;

public class Main {

  public static void main(String[] args) {
    StockStrategy capitalist = new Capitalist();
    Simulation simulation = new Simulation(capitalist);

    CareerStrategy conservative = new Conservative();
    StudyingStrategy workaholic = new Workaholic();
    PurchaseStrategy technophobe = new Technophobe();
    Occupation farmer = new Farmer();
    Occupation engineer = new Engineer();
    Occupation craftsman = new Craftsman();

    ProductionStrategy random = new Random();
    ProductionStrategy shortsighted = new Shortsighted(simulation.stock());

    Productivity productivity = new Productivity(100, 100,
            100, 100, 100);

    Worker w1 = new Worker(simulation, productivity,
            farmer, conservative, technophobe, shortsighted, workaholic);
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
  }
}

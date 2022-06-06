package pl.edu.mimuw;

import pl.edu.mimuw.agents.Worker;
import pl.edu.mimuw.agents.career.CareerStrategy;
import pl.edu.mimuw.agents.career.Conservatist;
import pl.edu.mimuw.agents.career.Farmer;
import pl.edu.mimuw.agents.career.Occupation;
import pl.edu.mimuw.agents.production.ProductionStrategy;
import pl.edu.mimuw.agents.production.Random;
import pl.edu.mimuw.agents.productivity.Productivity;
import pl.edu.mimuw.agents.purchase.PurchaseStrategy;
import pl.edu.mimuw.agents.purchase.Technophobe;
import pl.edu.mimuw.agents.studying.StudyingStrategy;
import pl.edu.mimuw.agents.studying.Workaholic;
import pl.edu.mimuw.stock.Capitalist;
import pl.edu.mimuw.stock.StockStrategy;

import java.util.Collections;

public class Main {

  public static void main(String[] args) {
    StockStrategy capitalist = new Capitalist();
    Simulation simulation = new Simulation(capitalist);

    CareerStrategy conservatist = new Conservatist();
    StudyingStrategy workaholic = new Workaholic();
    PurchaseStrategy technophobe = new Technophobe();
    Occupation farmer = new Farmer();
    ProductionStrategy random = new Random();
    Productivity productivity = new Productivity(100, 100,
            100, 100, 100);

    Worker w1 = new Worker(simulation, productivity,
            farmer, conservatist, technophobe, random, workaholic);
    w1.giveStartingResources(100, 100, 100, 100, 100);

    simulation.addAgents(Collections.singleton(w1));
    simulation.run(100);
  }
}

package pl.edu.mimuw.trade;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pl.edu.mimuw.trade.adapter.*;
import pl.edu.mimuw.trade.agents.Speculator;
import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.agents.career.*;
import pl.edu.mimuw.trade.agents.productivity.Productivity;
import pl.edu.mimuw.trade.bag.Bag;
import pl.edu.mimuw.trade.stock.Simulation;
import pl.edu.mimuw.trade.strategy.career.CareerStrategy;
import pl.edu.mimuw.trade.strategy.career.Conservative;
import pl.edu.mimuw.trade.strategy.production.ProductionStrategy;
import pl.edu.mimuw.trade.strategy.production.Random;
import pl.edu.mimuw.trade.strategy.production.Shortsighted;
import pl.edu.mimuw.trade.strategy.purchase.PurchaseStrategy;
import pl.edu.mimuw.trade.strategy.purchase.Technophobe;
import pl.edu.mimuw.trade.strategy.speculation.AverageSpeculation;
import pl.edu.mimuw.trade.strategy.speculation.SpeculationStrategy;
import pl.edu.mimuw.trade.strategy.stock.Capitalist;
import pl.edu.mimuw.trade.strategy.stock.StockStrategy;
import pl.edu.mimuw.trade.strategy.studying.Economical;
import pl.edu.mimuw.trade.strategy.studying.StudyingStrategy;
import pl.edu.mimuw.trade.strategy.studying.Workaholic;

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

//    Adapter adapter = new Adapter("in.json");
//    Simulation simulation = adapter.readSimulationProperties();
//    simulation.run(adapter.simulationDuration());

    StockStrategy capitalist = new Capitalist();

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

    Worker w1 = new Worker(42, simulation, productivity,
            farmer, 1, conservative, technophobe, shortsighted, economical);
    w1.giveStartingResources(100, 100, 100, 100, 100);

    GsonBuilder gsonBuilder = new GsonBuilder();

    Gson gson = gsonBuilder.registerTypeAdapter(CareerStrategy.class, new StrategyAdapter<>())
            .registerTypeAdapter(ProductionStrategy.class, new StrategyAdapter<>())
            .registerTypeAdapter(PurchaseStrategy.class, new StrategyAdapter<>())
            .registerTypeAdapter(SpeculationStrategy.class, new StrategyAdapter<>())
            .registerTypeAdapter(StockStrategy.class, new StrategyAdapter<>())
            .registerTypeAdapter(StudyingStrategy.class, new StrategyAdapter<>())
            .registerTypeAdapter(Occupation.class, new OccupationAdapter())
            .registerTypeAdapter(Career.class, new CareerAdapter())
            .registerTypeAdapter(Bag.class, new BagAdapter())
            .registerTypeAdapter(Productivity.class, new ProductivityAdapter())
            .setPrettyPrinting().create();

    String json = gson.toJson(w1);
    System.out.println(json);

    Worker w2 = gson.fromJson(json, Worker.class);
    System.out.println(w2);

    SpeculationStrategy average = new AverageSpeculation(simulation, 3);

    Speculator s1 = new Speculator(69, simulation, average);
    Speculator s2 = new Speculator(420, simulation, average);
    Speculator s3 = new Speculator(1337, simulation, average);

    simulation.addWorkers(w1);
    simulation.addSpeculators(s1, s2, s3);
    String json2 = gson.toJson(simulation);
    System.out.println(json2);

    Simulation xd = gson.fromJson(json2, Simulation.class);
  }
}


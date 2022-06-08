package pl.edu.mimuw.trade;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import pl.edu.mimuw.trade.agents.Agent;
import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.agents.career.*;
import pl.edu.mimuw.trade.agents.production.*;
import pl.edu.mimuw.trade.agents.productivity.Productivity;
import pl.edu.mimuw.trade.agents.purchase.*;
import pl.edu.mimuw.trade.agents.studying.*;
import pl.edu.mimuw.trade.stock.Simulation;
import pl.edu.mimuw.trade.stock.strategy.Balanced;
import pl.edu.mimuw.trade.stock.strategy.Capitalist;
import pl.edu.mimuw.trade.stock.strategy.Socialist;
import pl.edu.mimuw.trade.stock.strategy.StockStrategy;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Adapter {
  private Simulation simulation;
  private int duration;
  private StockStrategy stockStrategy;
  private final Set<Agent> agents;
  private final String input;

  public Adapter(String input) {
    this.simulation = null;
    this.duration = 0;
    this.stockStrategy = null;
    this.agents = new HashSet<>();
    this.input = input;
  }

  public Simulation readSimulationProperties() throws IOException {
    InputStream inputStream = new FileInputStream(input);
    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

    JsonReader jsonReader = new JsonReader(inputStreamReader);

    parseInfo(jsonReader);

    simulation.addAgents(agents);

    return simulation;
  }

  public int simulationDuration() {
    return duration;
  }

  private void parseInfo(JsonReader reader) throws IOException {
    while (reader.hasNext()) {
      JsonToken token = reader.peek();
      switch (token) {
        case BEGIN_OBJECT:
          reader.beginObject();
          break;
        case END_OBJECT:
          reader.endObject();
          break;
        case NAME:
          switch (reader.nextName()) {
            case "info":
              reader.beginObject();
              parseInfo(reader);
              reader.endObject();
              break;
            case "dlugosc":
              duration = reader.nextInt();
              break;
            case "gielda":
              parseStockStrategy(reader);
              break;
            case "ceny":
              parseFallBackPrices(reader);
              reader.endObject();
              break;
            case "robotnicy":
              parseWorkers(reader);
              break;
            case "spekulanci":
              parseSpeculators(reader);
              break;
          }
          break;
      }
    }
  }

  private void parseFallBackPrices(JsonReader reader) throws IOException {
    assert simulation != null;
    double food = 0, clothes = 0, tools = 0, programs = 0;
    while (reader.hasNext()) {
      JsonToken token = reader.peek();
      switch (token) {
        case BEGIN_OBJECT:
          reader.beginObject();
          break;
        case NAME:
          switch (reader.nextName()) {
            case "programy":
              programs = reader.nextDouble();
              break;
            case "jedzenie":
              food = reader.nextDouble();
              break;
            case "ubrania":
              clothes = reader.nextDouble();
              break;
            case "narzedzia":
              tools = reader.nextDouble();
              break;
          }
          break;
      }
    }
    simulation.stock().setFallBackPrices(food, clothes, tools, programs);
  }

  private void parseWorkers(JsonReader reader) throws IOException {
    while (reader.hasNext()) {
      JsonToken token = reader.peek();
      switch (token) {
        case BEGIN_ARRAY:
          reader.beginArray();
          agents.add(parseWorker(reader));
          break;
        case END_ARRAY:
          reader.endObject();
          break;
      }
    }
  }

  private Agent parseWorker(JsonReader reader) throws IOException {
    int id = 1, level = 1;
    double diamonds = 0;
    int programs = 0, food = 0, clothes = 0, tools = 0;

    Productivity productivity = null;
    CareerStrategy careerStrategy = null;
    ProductionStrategy productionStrategy = null;
    StudyingStrategy studyingStrategy = null;
    PurchaseStrategy purchaseStrategy = null;
    Occupation occupation = null;

    while (reader.hasNext()) {
      JsonToken token = reader.peek();
      switch (token) {
        case BEGIN_OBJECT:
          reader.beginObject();
          break;
        case END_OBJECT:
          reader.endObject();
          break;
        case NAME:
          switch (reader.nextName()) {
            case "id":
              id = reader.nextInt();
              break;
            case "poziom":
              level = reader.nextInt();
              break;
            case "kariera":
              occupation = parseOccupation(reader);
              break;
            case "kupowanie":
              purchaseStrategy = parsePurchaseStrategy(reader);
              break;
            case "uczenie":
              studyingStrategy = parseStudyingStrategy(reader);
              break;
            case "zmiana":
              careerStrategy = parseCareerStrategy(reader);
              break;
            case "produkcja":
              productionStrategy = parseProductionStrategy(reader);
              break;
            case "produktywnosc":
              productivity = parseProductivity(reader);
              break;
            case "zasoby":
              break;
            case "programy":
              programs = reader.nextInt();
              break;
            case "jedzenie":
              food = reader.nextInt();
              break;
            case "diamenty":
              diamonds = reader.nextDouble();
              break;
            case "ubrania":
              clothes = reader.nextInt();
              break;
            case "narzedzia":
              tools = reader.nextInt();
              break;
          }
          break;
      }
    }
    Worker worker = new Worker(id, simulation, productivity, occupation, level,
            careerStrategy, purchaseStrategy, productionStrategy, studyingStrategy);
    worker.giveStartingResources(food, clothes, tools, diamonds, programs);
    return worker;
  }

  private Occupation parseOccupation(JsonReader reader) throws IOException {
    switch (reader.nextString()) {
      case "rolnik":
        return new Farmer();
      case "gornik":
        return new Miner();
      case "rzemieslnik":
        return new Craftsman();
      case "inzynier":
        return new Engineer();
      case "programista":
        return new Programmer();
      default:
        throw new IOException("Requested occupation does not exist");
    }
  }

  private StudyingStrategy parseStudyingStrategy(JsonReader reader) throws IOException {
    StudyingStrategy res = null;
    int period = -1, margin = -1;
    while (reader.hasNext()) {
      JsonToken token = reader.peek();
      switch (token) {
        case BEGIN_OBJECT:
          reader.beginObject();
          break;
        case END_OBJECT:
          reader.endObject();
          break;
        case NAME:
          while (reader.hasNext()) {
            switch (reader.nextName()) {
              case "typ":
                reader.skipValue();
                break;
              case "okresowosc_nauki":
                period = reader.nextInt();
                break;
              case "zapas":
              case "limit_diamentow":
                margin = reader.nextInt();
                break;
            }
          }
          reader.endObject();
          if (margin > 0 && period > 0)
            return new Student(margin, period);
          if (margin > 0 && period < 0)
            return new Economical(margin);
          if (margin < 0 && period > 0)
            return new Periodical(period);
          break;
        case STRING:
          switch (reader.nextString()) {
            case "pracus":
              return new Workaholic();
            case "rozkladowy":
              return new Distributive();
          }
          break;
      }
    }
    return res;
  }

  private PurchaseStrategy parsePurchaseStrategy(JsonReader reader) throws IOException {
    switch (reader.nextString()) {
      case "technofob":
        return new Technophobe();
      case "czyscioszek":
        return new Stickler();
      case "zmechanizowany":
        return new Mechanized();
      case "gadzeciarz":
        return new Gadgeteer();
      default:
        throw new IOException("Requested strategy does not exist");
    }
  }

  private ProductionStrategy parseProductionStrategy(JsonReader reader) throws IOException {
    ProductionStrategy res = null;
    while (reader.hasNext()) {
      JsonToken token = reader.peek();
      switch (token) {
        case BEGIN_OBJECT:
          reader.beginObject();
          break;
        case END_OBJECT:
          reader.endObject();
          break;
        case NAME:
          if (reader.nextName().equals("typ"))
            switch (reader.nextString()) {
              case "sredniak":
                if (reader.nextName().equals("historia_sredniej_produkcji"))
                  res = new Average(reader.nextInt());
                break;
              case "perspektywiczny":
                if (reader.nextName().equals("historia_perspektywy"))
                  res = new Prospective(reader.nextInt());
                break;
            }
          reader.endObject();
          break;
        case STRING:
          switch (reader.nextString()) {
            case "krotkowzroczny":
              return new Shortsighted(simulation.stock());
            case "chciwy":
              return new Greedy();
            case "losowy":
              return new Random();
          }
          break;
      }
    }
    return res;
  }

  private CareerStrategy parseCareerStrategy(JsonReader reader) throws IOException {
    switch (reader.nextString()) {
      case "konserwatysta":
        return new Conservative();
      case "rewolucjonista":
        return new Revolutionist();
      default:
        throw new IOException("Requested strategy does not exist");
    }
  }

  private Productivity parseProductivity(JsonReader reader) throws IOException {
    int programs = 0, food = 0, clothes = 0, diamonds = 0, tools = 0;
    while (reader.hasNext()) {
      JsonToken token = reader.peek();
      switch (token) {
        case BEGIN_OBJECT:
          reader.beginObject();
          break;
        case END_OBJECT:
          reader.endObject();
          break;
        case NAME:
          switch (reader.nextName()) {
            case "programy":
              programs = reader.nextInt();
              break;
            case "jedzenie":
              food = reader.nextInt();
              break;
            case "diamenty":
              diamonds = reader.nextInt();
              break;
            case "ubrania":
              clothes = reader.nextInt();
              break;
            case "narzedzia":
              tools = reader.nextInt();
              break;
          }
          break;
      }
    }
    reader.endObject();

    return new Productivity(food, clothes, tools, diamonds, programs);
  }

  private void parseSpeculators(JsonReader reader) {

  }

  private void parseStockStrategy(JsonReader reader) throws IOException {
    switch (reader.nextString()) {
      case "socjalistyczna":
        stockStrategy = new Socialist();
        break;
      case "kapitalistyczna":
        stockStrategy = new Capitalist();
        break;
      case "zrownowazona":
        stockStrategy = new Balanced();
        break;
    }
    simulation = new Simulation(stockStrategy);
  }

}

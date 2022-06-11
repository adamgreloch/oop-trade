package pl.edu.mimuw.trade;

import pl.edu.mimuw.trade.io.GsonWrapper;
import pl.edu.mimuw.trade.simulation.SimulationWrapper;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {

  public static void main(String[] args) throws IOException {
    FileReader reader = new FileReader("in.json");

    SimulationWrapper wrapper = GsonWrapper.fromJson(reader, SimulationWrapper.class);
    wrapper.runSimulation();

    PrintWriter writer = new PrintWriter("out.json");
    writer.print(wrapper.getOutput());
    writer.close();
  }
}


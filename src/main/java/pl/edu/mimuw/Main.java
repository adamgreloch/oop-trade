package pl.edu.mimuw;

import pl.edu.mimuw.trade.io.GsonWrapper;
import pl.edu.mimuw.trade.simulation.SimulationWrapper;

import java.io.*;

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


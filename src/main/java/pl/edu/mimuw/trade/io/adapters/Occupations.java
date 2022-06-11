package pl.edu.mimuw.trade.io.adapters;

import pl.edu.mimuw.trade.agents.career.*;

import java.util.Map;
import java.util.TreeMap;

public class Occupations {
  static Map<String, Class> map = new TreeMap<String, Class>();

  static {
    map.put("rolnik", Farmer.class);
    map.put("rzemieslnik", Craftsman.class);
    map.put("inzynier", Engineer.class);
    map.put("gornik", Miner.class);
    map.put("programista", Programmer.class);
  }
}

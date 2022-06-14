package pl.edu.mimuw.trade.io.adapters;

import pl.edu.mimuw.trade.agents.occupations.*;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

public class Occupations {
  static final Map<String, Type> map = new TreeMap<>();

  static {
    map.put("rolnik", Farmer.class);
    map.put("rzemieslnik", Craftsman.class);
    map.put("inzynier", Engineer.class);
    map.put("gornik", Miner.class);
    map.put("programista", Programmer.class);
  }

}

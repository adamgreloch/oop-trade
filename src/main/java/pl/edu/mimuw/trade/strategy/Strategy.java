package pl.edu.mimuw.trade.strategy;

import com.google.gson.annotations.SerializedName;

public class Strategy {
  @SerializedName("typ")
  public final String name;

  public Strategy(String name) {
    this.name = name;
  }
}

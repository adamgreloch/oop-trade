package pl.edu.mimuw.trade.strategy.purchase;

import com.google.gson.annotations.SerializedName;
import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.products.ProductFactory;
import pl.edu.mimuw.trade.simulation.Offer;
import pl.edu.mimuw.trade.simulation.OfferFactory;

import java.util.HashSet;
import java.util.Set;

public class Mechanized extends PurchaseStrategy {
  @SerializedName("liczba_narzedzi")
  private int TOOLS_TO_BUY;
  private final Stickler stickler;

  public Mechanized() {
    super("zmechanizowany");
    this.stickler = new Stickler();
  }

  public Mechanized(int toolsToBuy) {
    this();
    this.TOOLS_TO_BUY = toolsToBuy;
  }

  public Set<Offer> purchasesToOffer(Worker worker) {
    Set<Offer> offers = new HashSet<>(stickler.purchasesToOffer(worker));
    offers.add(OfferFactory.workerPurchaseOffer(worker, ProductFactory.tool, TOOLS_TO_BUY));
    return offers;
  }
}

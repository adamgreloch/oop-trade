package pl.edu.mimuw.trade.strategy.purchase;

import com.google.gson.annotations.SerializedName;
import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.products.ProductFactory;
import pl.edu.mimuw.trade.simulation.Offer;
import pl.edu.mimuw.trade.simulation.OfferFactory;

import java.util.HashSet;
import java.util.Set;

public class Gadgeteer extends PurchaseStrategy {
  @SerializedName("liczba_narzedzi")
  private int TOOLS_TO_BUY;
  private Mechanized mechanized = null;

  public Gadgeteer() {
    super("gadzeciarz");
  }

  public Set<Offer> purchasesToOffer(Worker worker) {
    if (mechanized == null)
      this.mechanized = new Mechanized(TOOLS_TO_BUY);
    Set<Offer> offers = new HashSet<>(mechanized.purchasesToOffer(worker));
    offers.add(OfferFactory.workerPurchaseOffer(worker,
            ProductFactory.program, worker.quantityProduced()));
    return offers;
  }
}

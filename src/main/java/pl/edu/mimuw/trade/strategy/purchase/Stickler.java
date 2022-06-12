package pl.edu.mimuw.trade.strategy.purchase;

import pl.edu.mimuw.trade.agents.Worker;
import pl.edu.mimuw.trade.products.ProductFactory;
import pl.edu.mimuw.trade.simulation.Offer;
import pl.edu.mimuw.trade.simulation.OfferFactory;

import java.util.HashSet;
import java.util.Set;

public class Stickler extends PurchaseStrategy {
  private static final int MIN_CLOTHES_QUANTITY = 100;
  private final Technophobe technophobe;

  public Stickler() {
    super("czyscioszek");
    this.technophobe = new Technophobe();
  }

  public Set<Offer> purchasesToOffer(Worker worker) {
    Set<Offer> offers = new HashSet<>(this.technophobe.purchasesToOffer(worker));
    int toPurchase = Worker.DAILY_CLOTHES_CONSUMPTION + MIN_CLOTHES_QUANTITY
            - worker.ownedClothes();
    offers.add(OfferFactory.workerPurchaseOffer(worker, ProductFactory.clothes, toPurchase));
    return offers;
  }
}

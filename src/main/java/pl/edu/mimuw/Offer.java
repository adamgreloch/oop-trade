package pl.edu.mimuw;

import pl.edu.mimuw.products.TradeableProduct;

/**
 * Oferty kupna i sprzedaży, które będą następnie dopasowywane przez Giełdę.
 */
public class Offer {
  private boolean isPurchaseOffer;

  private double price;
  private int quantity;

  private int maxProductLevel;
  private int minProductLevel;

  private TradeableProduct product;


}

package pl.edu.mimuw.stock;

import pl.edu.mimuw.products.TradeableProduct;

import java.util.Map;

public interface StockStrategy {

  void processTransactions(Map<TradeableProduct, SalePurchaseOffers> offersPending);
}

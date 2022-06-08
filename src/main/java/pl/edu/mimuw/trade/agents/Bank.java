package pl.edu.mimuw.trade.agents;

import pl.edu.mimuw.trade.stock.Offer;
import pl.edu.mimuw.trade.stock.Simulation;

import static java.lang.Double.MAX_VALUE;

public class Bank extends Agent {

  public Bank(Simulation simulation) {
    super(Integer.MAX_VALUE, simulation);
  }

  public void act() {
  }

  public void makeOffers() {
  }

  public void finishDay() {
  }

  @Override
  public double diamonds() {
    return MAX_VALUE;
  }

  @Override
  public void earnDiamonds(double amount) {
  }

  @Override
  public void spendDiamonds(double amount) {
  }

  public Offer buyAll(Offer unmetOffer, double price) {
    return new Offer(this, unmetOffer, price);
  }

  @Override
  public String toString() {
    return "Bank";
  }
}
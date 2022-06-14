package pl.edu.mimuw.trade.agents;

import pl.edu.mimuw.trade.products.*;

import java.util.Arrays;

public class ProductivityVector {
  private final int PRODUCT_COUNT = 5;
  private int[] data;

  public ProductivityVector(int food, int clothes, int tools, int diamonds, int programs) {
    this.data = new int[]{food, clothes, tools, diamonds, programs};
  }

  private ProductivityVector(int[] data) {
    assert data.length == this.PRODUCT_COUNT;
    this.data = Arrays.copyOf(data, this.PRODUCT_COUNT);
  }

  public ProductivityVector() {
    this.data = new int[this.PRODUCT_COUNT];
  }

  public ProductivityVector(int constant) {
    this.data = new int[this.PRODUCT_COUNT];
    Arrays.fill(this.data, constant);
  }

  private static int indexOf(Product product) {
    if (product instanceof Food) return 0;
    if (product instanceof Clothes) return 1;
    if (product instanceof Tool) return 2;
    if (product instanceof Diamond) return 3;
    if (product instanceof Program) return 4;
    throw new IllegalArgumentException("Product outside of simulation scope");
  }

  public static int find(ProductivityVector vector, Product product) {
    return vector.data[indexOf(product)];
  }

  public void clear() {
    this.data = new int[this.PRODUCT_COUNT];
  }

  public ProductivityVector add(ProductivityVector other) {
    ProductivityVector res = new ProductivityVector();
    for (int i = 0; i < this.PRODUCT_COUNT; i++)
      res.data[i] = this.data[i] + other.data[i];
    return res;
  }

  public ProductivityVector add(int scalar) {
    ProductivityVector res = new ProductivityVector();
    for (int i = 0; i < this.PRODUCT_COUNT; i++)
      res.data[i] = this.data[i] + scalar;
    return res;
  }

  public int valueOf(Product product) {
    return this.data[indexOf(product)];
  }

  private ProductivityVector buff(int idx, int buff) {
    ProductivityVector res = this.copyOf();
    res.data[idx] += buff;
    return res;
  }

  public ProductivityVector buffProduct(Product product, int buff) {
    return this.buff(indexOf(product), buff);
  }

  public ProductivityVector copyOf() {
    return new ProductivityVector(this.data);
  }
}

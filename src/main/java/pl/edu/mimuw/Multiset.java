package pl.edu.mimuw;

import java.util.*;

public class Multiset<E> implements Iterable<E> {
  private final static int MAX_WHOLE_PRINT = 80;
  private final Map<E, Integer> elemCount;
  private int size;

  public Multiset() {
    this.elemCount = new HashMap<>();
    this.size = 0;
  }

  public Multiset(Iterable<? extends E> elements) {
    this();
    elements.forEach(this::add);
  }

  public int size() {
    return size;
  }

  public int count(E element) {
    return elemCount.getOrDefault(element, 0);
  }

  public int add(E element, int occurrences) {
    if (occurrences < 0) throw new IllegalArgumentException();
    int prev = count(element);
    if (occurrences == 0) return prev;
    this.elemCount.put(element, prev + occurrences);
    size += occurrences;
    return prev;
  }

  public boolean add(E element) {
    add(element, 1);
    return true;
  }

  public void addAll(Collection<E> elements) {
    for (E e : elements)
      add(e);
  }

  public int remove(E element, int occurrences) {
    if (occurrences < 0) throw new IllegalArgumentException();
    int prev = count(element);
    if (occurrences == 0) return prev;
    this.elemCount.put(element, Math.max(0, prev - occurrences));
    size -= Math.min(prev, occurrences);
    return prev;
  }

  public boolean remove(E element) {
    return remove(element, 1) > 0;
  }

  public void clear() {
    this.elemCount.clear();
  }

  public Set<E> elementSet() {
    return new HashSet<>(this.elemCount.keySet());
  }

  public boolean contains(E element) {
    if (!this.elemCount.containsKey(element)) return false;
    return this.elemCount.get(element) != 0;
  }

  public Iterator<E> iterator() {
    return this.toList().iterator();
  }

  public List<E> toList() {
    List<E> res = new LinkedList<>();

    for (E e : this.elemCount.keySet())
      for (int i = 0; i < count(e); i++)
        res.add(e);

    return res;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Multiset<?> multiset = (Multiset<?>) o;
    return size == multiset.size && elemCount.equals(multiset.elemCount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(elemCount, size);
  }

  @Override
  public String toString() {
    StringBuilder res = new StringBuilder("{");
    Iterator<E> iterator = this.size > MAX_WHOLE_PRINT ? this.elemCount.keySet().iterator() : iterator();
    MutableInteger i = new MutableInteger(0);

    iterator.forEachRemaining(e -> {
      String front = "", end = "";
      if (this.size > MAX_WHOLE_PRINT) {
        front = count(e) + "x \"";
        end = "\"";
      }
      if (i.increment() > 0) res.append(", ");
      res.append(front).append(e).append(end);
    });
    res.append("}");

    return res.toString();
  }
}

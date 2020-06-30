package Array;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CombinationTest{
  private final Combination solver = new Combination();

  @Test
  void addition() {
    assertAll(() -> solver.combine(1, 1));
  }

}

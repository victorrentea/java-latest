package victor.training.java.sealed.shapes;

import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Data
public sealed class Rectangle implements Shape
    permits R1, R2, R3 {
  private final int height;
  private final int width;
  @Override
  public double perimeter() {
    return 2 * (height + width);
  }
}
final class R1 extends Rectangle {
  public R1(int height, int width) {
    super(height, width);
  }
}
final class R2 extends Rectangle {
  public R2(int height, int width) {
    super(height, width);
  }
}
non-sealed class R3 extends Rectangle {
  public R3(int height, int width) {
    super(height, width);
  }
}

// oricine poate subclasa R3
class R3Hack extends R3 {
  public R3Hack(int height, int width) {
    super(height, width);
  }
}
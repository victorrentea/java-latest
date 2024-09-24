package victor.training.java.records;

import lombok.Data;

@Data
public class MutableData {

  private int a,b,c,d,e;

  // these are generated; see lombok.config!
//  public MutableData a(int a) {
//    this.a = a;
//    return this;
//  }

  public static void main(String[] args) {
    new MutableData().a(1).b(2).c(3).d(4).e(5);
  }
}

package victor.training.java.records;

import lombok.Builder;

@Builder
public record LargeImmutableObj(
    int a,
    int b,
    int c,
    String d,
    String e,
    String f,
    String g
    // Address efg
) {
  // or the equivalent of @Builder without lombok:
//  public static LargeImmutableObjBuilder builder() {
//    return new LargeImmutableObjBuilder();
//  }

//  public static class LargeImmutableObjBuilder {
//    private int a;
//    private int b;
//    private int c;
//    private String d;
//    private String e;
//    private String f;
//    private String g;
//
//    LargeImmutableObjBuilder() {
//    }
//
//    public LargeImmutableObjBuilder a(int a) {
//      this.a = a;
//      return this;
//    }
//
//    public LargeImmutableObjBuilder b(int b) {
//      this.b = b;
//      return this;
//    }
//
//    public LargeImmutableObjBuilder c(int c) {
//      this.c = c;
//      return this;
//    }
//
//    public LargeImmutableObjBuilder d(String d) {
//      this.d = d;
//      return this;
//    }
//
//    public LargeImmutableObjBuilder e(String e) {
//      this.e = e;
//      return this;
//    }
//
//    public LargeImmutableObjBuilder f(String f) {
//      this.f = f;
//      return this;
//    }
//
//    public LargeImmutableObjBuilder g(String g) {
//      this.g = g;
//      return this;
//    }
//
//    public LargeImmutableObj build() {
//      return new LargeImmutableObj(this.a, this.b, this.c, this.d, this.e, this.f, this.g);
//    }
//
//    public String toString() {
//      return "LargeImmutableObj.LargeImmutableObjBuilder(a=" + this.a + ", b=" + this.b + ", c=" + this.c + ", d=" + this.d + ", e=" + this.e + ", f=" + this.f + ", g=" + this.g + ")";
//    }
//  }
}


class Horror{
  public static void main(String[] args) {
//    LargeImmutableObj obj = new LargeImmutableObj(
//        1, 2, 3, "a", "b", "c", "d");

    LargeImmutableObj obj = LargeImmutableObj.builder()
        .a(1)
        .b(2)
        .c(3)
        .d("a")
        .e("b")
        .f("c")
        .g("d")
        .build();
    // in kotlin this would read
    // val obj = LargeImmutableObj(
    //   a=1, b=2, c=3, d="a", e="b", f="c", g="d")

    System.out.println(obj);

  }
}
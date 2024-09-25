package victor.training.java.patterns.template;

import lombok.RequiredArgsConstructor;
import victor.training.java.patterns.template.support.Order;
import victor.training.java.patterns.template.support.OrderRepo;
import victor.training.java.patterns.template.support.Product;
import victor.training.java.patterns.template.support.ProductRepo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class Template2_Export {
  public static final File FOLDER = new File("export");
  private final FileExporter exporter;
  private final ProductExporter productExporter;
  private final OrderExporter orderExporter;

  // it tricks the javac compiler to ignore checked exceptions
  // "checked" means checked by the compiler.
  // checked exceptions are a design mistake in Java
  // at runtime there's no difference between checked and unchecked exceptions - JVM doesn't care
//  @SneakyThrows // darkest lombok feature
  public void exportOrders() {
//    new OrderExporter(FOLDER,orderRepo).export("orders.csv");
//    exporter.export("orders.csv", orderExporter::writeContents);
    exporter.export("orders.csv",
        writer -> uncheck(() -> orderExporter.writeContents(writer)));
//    https://projectlombok.org/features/SneakyThrows
  }

  public void exportProducts() throws Exception {
    // TODO 'the same way you did the export of orders'
    // RUN UNIT TESTS!
//    new ProductExporter(FOLDER, productRepo).export("products.csv");
    exporter.export("products.csv",
        writer -> uncheck(() -> productExporter.writeContents(writer)));
  }

  @FunctionalInterface
  interface ThrowingRunnable {
    void run() throws Exception;
  }

  private void uncheck(ThrowingRunnable r) {
    try {
      r.run();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}


@RequiredArgsConstructor
class FileExporter {
  private final File exportFolder;

  public File export(String fileName, Consumer<Writer> writeContents) {
    File file = new File(exportFolder, fileName);
    long t0 = System.currentTimeMillis();
    try (Writer writer = new FileWriter(file)) { // java 7 try-with-resources
      System.out.println("Starting export to: " + file.getAbsolutePath());

      writeContents.accept(writer);

      System.out.println("File export completed: " + file.getAbsolutePath());
      return file;
    } catch (Exception e) {
      System.out.println("Pretend: Send Error Notification Email"); // TODO CR: only for export orders, not for products
      throw new RuntimeException("Error exporting data", e);
    } finally {
      long t1 = System.currentTimeMillis();
      System.out.println("Pretend: Metrics: Export finished in: " + (t1 - t0));
    }
  }

  // Java sucks because it allows you to override any public you inherited from your Super! types
  // other languages like C# or Kotlin don't allow this
//  protected abstract void writeContents(Writer writer) throws IOException;
  // in Java 8 this signature could be Consumer<Writer>

  // #1 reason for template method

}

class OrderExporter extends FileExporter {
  private final OrderRepo orderRepo;

  public OrderExporter(File exportFolder, OrderRepo orderRepo) {
    super(exportFolder);
    this.orderRepo = orderRepo;
  }

  //  @SneakyThrows // darkest lombok feature
  public void writeContents(Writer writer) throws IOException {
    writer.write("OrderID;CustomerId;Amount\n"); // header
    for (Order order : orderRepo.findByActiveTrue()) {// body
      String csv = order.id() + ";" + CSVUtil.escapeCell(order.customerId()) + ";" + order.amount() + "\n";
      writer.write(csv);
    }
  }
//  @Override
//  protected void encryptFile(File file) {
//    // fun only here, for orders
//  }

}

class ProductExporter extends FileExporter {
  private final ProductRepo productRepo;

  public ProductExporter(File exportFolder, ProductRepo productRepo) {
    super(exportFolder);
    this.productRepo = productRepo;
  }

  public void writeContents(Writer writer) throws IOException {
    writer.write("ProductID;Name;Price\n"); // header
    for (Product product : productRepo.findAll()) {
      String csv = product.id() + ";" + product.name() + ";" + product.price() + "\n";
      writer.write(csv);
    }
  }
}
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
import java.util.Objects;

@RequiredArgsConstructor
public class Template2_Export {
  public static final File FOLDER = new File("export");
  private final AbstractFileExporter exporter;
  private final OrderRepo orderRepo;
  private final ProductRepo productRepo;

  public void exportOrders() throws Exception {
    new OrderExporter(FOLDER,orderRepo).export("orders.csv");
  }

  public void exportProducts() throws Exception {
    // TODO 'the same way you did the export of orders'
    // RUN UNIT TESTS!
    new ProductExporter(FOLDER, productRepo).export("products.csv");
  }
}


@RequiredArgsConstructor
abstract class AbstractFileExporter {
  private final File exportFolder;

  public File export(String fileName) {
    File file = new File(exportFolder, fileName);
    long t0 = System.currentTimeMillis();
    try (Writer writer = new FileWriter(file)) { // java 7 try-with-resources
      System.out.println("Starting export to: " + file.getAbsolutePath());

      writeContents(writer);

      System.out.println("File export completed: " + file.getAbsolutePath());
      encryptFile(file);
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
  protected abstract void writeContents(Writer writer) throws IOException;
  // in Java 8 this signature could be Consumer<Writer>

  // #1 reason for template method
  /** Override this method if you want to encrypt the exported file */ // "Hook" method
  protected void encryptFile(File file) { /*NOOP*/ }

  // #2 reason for template method: when superclass provides some tools (methods)
  // that the sublclasses use to get their job done
  public String escapeCell(Object cellValue) {
    if (cellValue instanceof String s) {
      if (!s.contains("\n")) return s;
      // abasda|12314|He said ""Ok""| "multiline
      // content"
      return "\"" + s.replace("\"", "\"\"") + "\"";
    } else {
      return Objects.toString(cellValue);
    }
  }

}
class OrderExporter extends AbstractFileExporter {
  private final OrderRepo orderRepo;
  public OrderExporter(File exportFolder, OrderRepo orderRepo) {
    super(exportFolder);
    this.orderRepo = orderRepo;
  }

  protected void writeContents(Writer writer) throws IOException {
    writer.write("OrderID;CustomerId;Amount\n"); // header
    for (Order order : orderRepo.findByActiveTrue()) {// body
      String csv = order.id() + ";" + escapeCell(order.customerId()) + ";" + order.amount() + "\n";
      writer.write(csv);
    }
  }

  @Override
  protected void encryptFile(File file) {
    // fun only here, for orders
  }

 }

class ProductExporter extends AbstractFileExporter {
  private final ProductRepo productRepo;

  public ProductExporter(File exportFolder, ProductRepo productRepo) {
    super(exportFolder);
    this.productRepo = productRepo;
  }

  protected void writeContents(Writer writer) throws IOException {
    writer.write("ProductID;Name;Price\n"); // header
    for (Product product : productRepo.findAll()) {
      String csv = product.id() + ";" + product.name() + ";" + product.price() + "\n";
      writer.write(csv);
    }
  }
}
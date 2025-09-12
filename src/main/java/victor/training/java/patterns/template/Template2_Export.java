package victor.training.java.patterns.template;

import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class Template2_Export {
  private final AbstractFileExporter exporter;
  private final OrderRepo orderRepo;

  public void exportOrders() throws Exception {
    // loan pattern: acepti param un Consumer<Resursa> mangeuita de tine
    exporter.exportCsv();
  }

  private final ProductRepo productRepo;

  public void exportProducts() throws Exception {
//        exporter.exportCsv(writer -> {
//            writer.write("ProductID;Name;Price;ImageUrl\n");
//            for (Product product : productRepo.findAll()) {
//                writer.write(product.id() + ";" + product.name() + ";" + product.price() + ";" +
//                             exporter.escapeCell(product.imageUrl()) + "\n");
//            }
//        });
  }
}

abstract class AbstractFileExporter {
  private final File exportFolder;

  public AbstractFileExporter(File exportFolder) {
    this.exportFolder = exportFolder;
  }

  // f(header:string, Enum<?> columns, List<T> date); enum OrderColumns{ID("OrderId", Order::getId),..}

  public File exportCsv() {
    File file = new File(exportFolder, "orders.csv");
    long t0 = System.currentTimeMillis();
    try (Writer writer = new FileWriter(file)) {
      System.out.println("Starting export to: " + file.getAbsolutePath());

      writeContent(writer);

      System.out.println("File export completed: " + file.getAbsolutePath());
      return file;
    } catch (Exception e) {
      System.out.println("Pretend: Send Error Notification Email"); // TODO CR: only for export orders, not for products
      throw new RuntimeException("Error exporting data", e);
    } finally {
      System.out.println("Pretend: Metrics: Export finished in: " + (System.currentTimeMillis() - t0));
    }
  }

  protected abstract void writeContent(Writer writer) throws IOException;


  public String escapeCell(Object cellValue) {
    if (cellValue instanceof String s) {
      if (!s.contains("\n")) return s;
      return "\"" + s.replace("\"", "\"\"") + "\"";
    } else {
      return Objects.toString(cellValue);
    }
  }
}

class OrderExporter extends AbstractFileExporter {
  private final OrderRepo orderRepo;

  public OrderExporter(OrderRepo orderRepo, File exportFolder) {
    super(exportFolder);
    this.orderRepo = orderRepo;
  }

  @Override
  protected void writeContent(Writer writer) throws IOException {
    writer.write("OrderID;CustomerId;Amount\n");
    for (Order order : orderRepo.findByActiveTrue()) {
      String csv = order.id() + ";" + order.customerId() + ";" + order.amount() + "\n";
      writer.write(csv);
    }
  }
}

class ProductExporter extends AbstractFileExporter {
  private final ProductRepo productRepo;

  public ProductExporter(ProductRepo productRepo, File exportFolder) {
    super(exportFolder);
    this.productRepo = productRepo;
  }

  @Override
  protected void writeContent(Writer writer) throws IOException {
    writer.write("ProductID;Name;Price;ImageUrl\n");
    for (Product product : productRepo.findAll()) {
      String csv = STR."\{product.id()};\{product.name()};\{product.price()};\{escapeCell(product.imageUrl())} ";
      writer.write(csv);
      //`product ${product.id()}` in js
    }
  }
}


record Order(Long id, Long customerId, Double amount) {
}

interface OrderRepo {
  Iterable<Order> findByActiveTrue(); // 1 Mln orders ;)
}

interface ProductRepo {
  List<Product> findAll();
}

record Product(Long id, String name, Double price, String imageUrl) {
}

package victor.training.java.patterns.template;

import lombok.RequiredArgsConstructor;
import victor.training.java.patterns.template.support.Order;
import victor.training.java.patterns.template.support.OrderRepo;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Objects;

@RequiredArgsConstructor
public class Template2_Export {
  private final FileExporter exporter;

  public void exportOrders() throws Exception {
    exporter.exportOrders();
  }

  public void exportProducts() throws Exception {
    // TODO 'the same way you did the export of orders'
    // RUN UNIT TESTS!
  }
}


@RequiredArgsConstructor
class FileExporter {
  private final OrderRepo orderRepo;
  private final File exportFolder;

  public File exportOrders() {
    File file = new File(exportFolder, "orders.csv");
    long t0 = System.currentTimeMillis();
    try (Writer writer = new FileWriter(file)) { // java 7 try-with-resources
      System.out.println("Starting export to: " + file.getAbsolutePath());

      writer.write("OrderID;CustomerId;Amount\n"); // header
      for (Order order : orderRepo.findByActiveTrue()) {// body
        String csv = order.id() + ";" + order.customerId() + ";" + order.amount() + "\n";
        writer.write(csv);
      }

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

  public String escapeCell(Object cellValue) {
    if (cellValue instanceof String s) {
      if (!s.contains("\n")) return s;
      return "\"" + s.replace("\"", "\"\"") + "\"";
    } else {
      return Objects.toString(cellValue);
    }
  }
}

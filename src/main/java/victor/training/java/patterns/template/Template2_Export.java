package victor.training.java.patterns.template;

import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.List;
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


class FileExporter {
    private final OrderRepo orderRepo;
    private final File exportFolder;

    public FileExporter(OrderRepo orderRepo, File exportFolder) {
        this.orderRepo = orderRepo;
        this.exportFolder = exportFolder;
    }

    public File exportOrders() {
        File file = new File(exportFolder, "orders.csv");
        long t0 = System.currentTimeMillis();
        try (Writer writer = new FileWriter(file)) {
            System.out.println("Starting export to: " + file.getAbsolutePath());

            writer.write("OrderID;Date\n");

            for (Order order : orderRepo.findByActiveTrue()) {
                String csv = order.id() + ";" + order.customerId() + ";" + order.amount() + "\n";
                writer.write(csv);
            }

            System.out.println("File export completed: " + file.getAbsolutePath());
            return file;
        } catch (Exception e) {
            System.out.println("Pretend: Send Error Notification Email"); // TODO CR: only for export orders, not for products
            throw new RuntimeException("Error exporting data", e);
        } finally {
            System.out.println("Pretend: Metrics: Export finished in: " + (System.currentTimeMillis() - t0));
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
record Order(Long id, Long customerId, Double amount) {
}
interface OrderRepo {
    List<Order> findByActiveTrue(); // 1 Mln orders ;)
}

interface ProductRepo {
    List<Product> findAll();
}

record Product(Long id, String name, Double price, String imageUrl) {
}

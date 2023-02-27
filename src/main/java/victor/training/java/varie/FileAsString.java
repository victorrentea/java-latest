package victor.training.java.varie;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileAsString {
  public static void main(String[] args) throws IOException {
    File file = new File("pom.xml");
    System.out.println("File path = " + file.getAbsolutePath());
    System.out.println("File found = " + file.isFile());

    String fullContents = Files.readString(file.toPath());

    System.out.println(fullContents.length());
    System.out.println(fullContents.lines().count());
  }
}

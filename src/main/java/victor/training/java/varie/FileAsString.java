package victor.training.java.varie;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileAsString {
  public static void main(String[] args) throws IOException {
    File f = new File("pom.xml");
    System.out.println(f.isFile());

    String str = Files.readString(f.toPath());
    System.out.println(str.length());
    System.out.println(str.lines().count());
  }
}

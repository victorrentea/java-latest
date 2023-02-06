package victor.training.java.textblocks;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class Stream {


  public static void main(String[] args) throws IOException {

    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
//    numbers.stream().drop


    System.out.println(" ".repeat(10) + "Dupa 10 spatii");

    File f = new File("pom.xml");
    System.out.println(f.isFile());

    String str = Files.readString(f.toPath());
    System.out.println(str.length());
    System.out.println(str.lines().count());

    //    IOUtils.toString(file)


  }
}

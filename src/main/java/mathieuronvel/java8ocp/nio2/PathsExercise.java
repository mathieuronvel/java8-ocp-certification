package mathieuronvel.java8ocp.nio2;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathsExercise {

  public static void main(String s[]) throws IOException {
    fileSystem();
  }

  private static void paths() {
    Path p = Paths.get("/tmp", "/thing.txt");
    System.out.println(p.toAbsolutePath());
    System.out.println(p.relativize(Paths.get("/mathieuronvel")).normalize());
    System.out.println(p.normalize());
  }

  private static void fileSystem() throws IOException {
    FileSystem fileSystem = FileSystems.getFileSystem(URI.create("file:///"));
    Path p = fileSystem.getPath("/tmp/thesymboliclink");
    System.out.println(Files.isSymbolicLink(p));
    System.out.println(p.toRealPath(LinkOption.NOFOLLOW_LINKS));
    Files.lines(p).forEach(System.out::println);
  }

}

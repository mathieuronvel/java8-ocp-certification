package mathieuronvel.java8ocp.nio2;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.time.Instant;

public class AttributesExercise {

  public static void main(String[] args) throws IOException {
    Path path = Paths.get("/tmp/thesymboliclink");
    read(path);
  }

  private static void read(Path path) throws IOException {
    System.out.print("Creation time of linked file: ");
    System.out.println(Files.readAttributes(path, BasicFileAttributes.class).creationTime());
    System.out.print("Creation time of symbolic file: ");
    System.out.println(Files.readAttributes(path, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS).creationTime());
    System.out.println("Change time of symbolic file: ");
    PosixFileAttributeView v = Files.getFileAttributeView(path, PosixFileAttributeView.class);
    v.setTimes(FileTime.from(Instant.parse("2023-05-16T18:01:19.085807Z")), null, FileTime.from(Instant.parse("2024-05-16T18:01:19.085807Z")));
    System.out.print("Re-read creation time of symbolic file: ");
    System.out.println(Files.readAttributes(path, BasicFileAttributes.class).creationTime());
    System.out.print("Owner: ");
    System.out.println(v.getOwner().getName());
    UserDefinedFileAttributeView userDefinedFileAttributeView = Files.getFileAttributeView(path, UserDefinedFileAttributeView.class);
    System.out.println("User attributes: ");
    userDefinedFileAttributeView.list().forEach(System.out::println);
    userDefinedFileAttributeView.write("mathieu", Charset.defaultCharset().encode("ronvel"));
    System.out.println("User attributes for setting them ");
    userDefinedFileAttributeView = Files.getFileAttributeView(path, UserDefinedFileAttributeView.class);
    userDefinedFileAttributeView.list().forEach(AttributesExercise::printUserFileAttribute);

  }

  private static void printUserFileAttribute(String name) {
    System.out.println("Try to read attribute " + name);
    Path path = Paths.get("/tmp/thesymboliclink");
    try {
      UserDefinedFileAttributeView userDefinedFileAttributeView = Files
          .getFileAttributeView(path, UserDefinedFileAttributeView.class);
      ByteBuffer buffer = ByteBuffer.allocate(10000);
      int size = userDefinedFileAttributeView.read(name, buffer);
      System.out.println(name + " = " + new String(buffer.array(), Charset.defaultCharset()) + " - size = " + size);
    } catch (Exception e) {
      // TODO
    }
  }

}

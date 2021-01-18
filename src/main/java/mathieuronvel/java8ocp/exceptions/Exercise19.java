package mathieuronvel.java8ocp.exceptions;

import java.sql.SQLException;

public class Exercise19 {

  public void read() throws SQLException {
    try {
      readFromDatabase();
    } catch (RuntimeException e) {
      throw e;
    }
  }

  private void readFromDatabase() throws SQLException {}

}

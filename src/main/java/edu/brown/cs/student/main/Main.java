package edu.brown.cs.student.main;

import java.util.HashMap;


import edu.brown.cs.student.main.REPL.CommandMethod;
import edu.brown.cs.student.main.REPL.REPL;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import spark.Spark;

/**
 * The Main class of our project. This is where execution begins.
 */
public final class Main {

  // use port 4567 by default when running server
  private static final int DEFAULT_PORT = 4567;

  /**
   * The initial method called when execution begins.
   *
   * @param args An array of command line arguments
   */
  public static void main(String[] args) {
    new Main(args).run();
  }

  private String[] args;
  private static starLogic starCommands;
  private static studentLogic studentCommands;



  private Main(String[] args) {
    this.args = args;
  }

  private void run() {

    starCommands = new starLogic();
    studentCommands = new studentLogic();
    // set up parsing of command line flags
    OptionParser parser = new OptionParser();

    // "./run --gui" will start a web server
    parser.accepts("gui");

    // use "--port <n>" to specify what port on which the server runs
    parser.accepts("port").withRequiredArg().ofType(Integer.class)
        .defaultsTo(DEFAULT_PORT);

    OptionSet options = parser.parse(args);
    if (options.has("gui")) {
      runSparkServer((int) options.valueOf("port"));
    }

    HashMap<String, CommandMethod<?>> commands = new HashMap<>();
    commands.put("naive_neighbors", starCommands);
    commands.put("stars", starCommands);
    commands.put("load_kd", studentCommands);
    REPL repl = new REPL(commands);
    repl.run();

  }

  private void runSparkServer(int port) {
    // set port to run the server on
    Spark.port(port);

    // specify location of static resources (HTML, CSS, JS, images, etc.)
    Spark.externalStaticFileLocation("src/main/resources/static");
  }
}

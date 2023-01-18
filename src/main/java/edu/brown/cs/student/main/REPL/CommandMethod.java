package edu.brown.cs.student.main.REPL;
import java.util.List;

public interface CommandMethod<T> {
    List<T> run(String command);
}

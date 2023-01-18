package edu.brown.cs.student.main.REPL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class REPL {
    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    private HashMap<String, CommandMethod<?>> commandMap;

    public REPL(HashMap<String, CommandMethod<?>> inputCommands) {
        commandMap = inputCommands;
    }
    public void run() {
        while (true) {
            try {
                String line = input.readLine();
                if (line != null) {
                    String[] commands = line.split(" ");
                    if (line.equals("")) {
                        continue;
                    } else if (commandMap.containsKey(commands[0])) {
                        commandMap.get(commands[0]).run(line);
                    } else {
                        System.err.println("Invalid Input");
                    }
                } else {
                    break;
                }
            } catch (IOException e) {
                System.err.println("Failed to Read");
                break;
            }
        }
}
}

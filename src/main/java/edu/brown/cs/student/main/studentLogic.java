package edu.brown.cs.student.main;

import edu.brown.cs.student.main.KDTree.KDNode;
import edu.brown.cs.student.main.KDTree.KDTree;
import edu.brown.cs.student.main.REPL.CommandMethod;

import java.util.ArrayList;
import java.util.List;

public class studentLogic implements CommandMethod<Student> {
    private ArrayList<Student> studentList;
    private KDTree<Student> studentTree;
    studentLogic(){
        studentList = new ArrayList<>();
    }
    @Override
    public List<Student> run(String cmd) {
        String[] commands = cmd.split(" ");
        if (commands.length > 0) {
            switch(commands[0]){
                case "load_kd":
                    buildKD(commands);
            }
        }
        return new ArrayList<>();
    }

    private void buildKD(String[] commands) {
        if (commands.length != 2) {
            System.err.println("Invalid Number of Commands");
        } else {
            CSVParse csv = new CSVParse();
            String filename = commands[1];
            if (!csv.isValidFile(filename)) {
                System.err.println("No File Found");
                return;
            }
            ArrayList<List<String>> csvSave = csv.parseCSV(filename);
            if (studentList != null){studentList.clear();}
            csvSave.remove(0);
            for (List<String> row: csvSave) {
                try {
                    studentList.add(
                            new Student(row.get(0), row.get(1), Double.parseDouble(row.get(8)), Double.parseDouble(row.get(10)),
                                    Double.parseDouble(row.get(13))));
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid Input");
                    studentList.clear();
                    break;
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("Invalid Input");
                    studentList.clear();
                    break;
                }
            }
            studentTree = new KDTree(3,studentList);

            System.out.println("Read " + studentList.size()
                    + " students from " + filename);
        }
    }
}

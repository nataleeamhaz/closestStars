package edu.brown.cs.student.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.File;

public class CSVParse {
    private int rows = -1;
    private int cols = -1;
    private String file;

    public CSVParse() {
    }

    public ArrayList<List<String>> parseCSV(String filename) {
        setFileToParse(filename);
        ArrayList<List<String>> csvTable = new ArrayList<>();
        if (file != null) {
            try {
                BufferedReader csvReader = new BufferedReader(new FileReader(file));
                String row;
                while ((row = csvReader.readLine()) != null) {
                    rows++;
                    String[] data = row.split(",");
                    csvTable.add(Arrays.asList(data));
                    cols = Math.max(cols, data.length);
                }
            } catch (FileNotFoundException e) {
                System.err.println("Invalid File");
                return null;
            } catch (IOException e) {
                System.err.println("Invalid File Format");
                return null;
            }
        } else {
            System.err.println("No File Loaded");
            return null;
        }
        return csvTable;
    }


    public void setFileToParse(String filename) throws IllegalArgumentException {
        File csvFile = new File(filename);
        if (csvFile.isFile()) {
            file = filename;
        } else {
            throw new IllegalArgumentException("Invalid File");
        }
    }

    public boolean isValidFile(String filename) {
        File csvFile = new File(filename);
        return csvFile.isFile();
    }

    public int getRows() throws IllegalArgumentException {
        if (rows == -1) {
            throw new IllegalArgumentException("No File Loaded");
        }
        return rows;
    }

    public int getCols() throws IllegalArgumentException {
        if (cols == -1) {
            throw new IllegalArgumentException("No File Loaded");
        }
        return cols;
    }
}
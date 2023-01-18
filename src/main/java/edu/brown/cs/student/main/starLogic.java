package edu.brown.cs.student.main;
import edu.brown.cs.student.main.REPL.CommandMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Collections;

public class starLogic implements CommandMethod<Star> {
    private ArrayList<Star> starList;
    starLogic(){
        starList = new ArrayList<>();
    }
    @Override
    public List<Star> run(String cmd) {
        String[] commands = cmd.split(" ");
        if (commands.length > 0) {
            switch (commands[0]) {
                case "stars":
                    fillStarList(commands);
                    break;
                case "naive_neighbors":
                    neighbors(cmd, commands);
                    break;
                default:
                    System.err.println(commands[0] + " is not a valid command");
                    break;
            }
        }
        return new ArrayList<>();
    }


    public void fillStarList(String[] coms) {
        if (coms.length != 2) {
            System.err.println("Invalid Number of Commands");
        } else {
            CSVParse csv = new CSVParse();
            String filename = coms[1];
            if (!csv.isValidFile(filename)) {
                System.err.println("No File Found");
                return;
            }
            ArrayList<List<String>> csvSave = csv.parseCSV(filename);
            if (csvSave == null) {
                return;
            }
            if (csv.getCols() != 5) {
                System.err.println("Invalid Number of Columns");
                return;
            }

            List<String> heading = csvSave.get(0);
            if (!(heading.get(0).equals("StarID"))
                    || !(heading.get(1).equals("ProperName"))
                    || !(heading.get(2).equals("X"))
                    || !(heading.get(3).equals("Y"))
                    || !(heading.get(4).equals("Z"))) {
                System.err.println("Invalid File Header");
                return;
            }
            starList.clear();
            csvSave.remove(0);
            for (List<String> row: csvSave) {
                try {
                    starList.add(
                            new Star(row.get(0), row.get(1), row.get(2), row.get(3),
                                    row.get(4)));
                } catch (IllegalArgumentException e) {
                    //if it has malformed input, end clear the list and break
                    System.err.println("Invalid Input");
                    starList.clear();
                    break;
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("Invalid Input");
                    starList.clear();
                    break;
                }
            }
            System.out.println("Read " + starList.size()
                    + " stars from " + filename);
        }
    }


    public void neighbors(String command, String[] coms) {
        if (starList.isEmpty() ) {
            System.err.println("No File Loaded");
        }
        if (!isClassInt(coms)) {
            coms = processComs(command, coms);
            if (coms == null) {
                System.err.println("Invalid Input");
            }
        }
        List<Star> ret = new ArrayList<>();
        assert coms != null;
        if (coms.length != 3 && coms.length != 5) {
            System.err.println("Invalid Input Length");
        } else {
            String k = coms[1];
            try {
                int knn = Integer.parseInt(k);
                if (knn < 0) {
                    System.err.println("Integer must be non-negatice");
                }
                if (coms.length == 3) {
                    ret = naiveNearestNeighbors(knn, coms[2]);
                } else {
                    double x = Double.parseDouble(coms[2]);
                    double y = Double.parseDouble(coms[3]);
                    double z = Double.parseDouble(coms[4]);
                    ret = naiveNearestNeighbors(knn, x, y, z);

                }
                if (ret == null) {
                    System.err.println("Search Unsuccessful");
                } else if (!ret.isEmpty()) {
                    for (Star star : ret) {
                        String str = star.getID();
                        System.out.println(str);
                    }
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid Integer Supplied");
            }
        }
    }


    // Neighbors algo based on coordinate input
    public List<Star> naiveNearestNeighbors(int k, double x, double y, double z) {
        ArrayList<Star> ret = new ArrayList<>();
        if (k == 0) {
            return ret;
        }
        Star toFind = findStar(x, y, z);

        if (toFind == null) {
            System.err.println("Star not found");
            return null;
        }

        HashMap<Double, ArrayList<Star>> map = new HashMap<>();

        for (Star star: starList) {
            double distance = star.calculateDistance(toFind);
            if (!map.containsKey(distance)) {
                map.put(distance, new ArrayList<Star>());
            }
            map.get(distance).add(star);
        }

        ArrayList<Double> keys = new ArrayList<>(map.keySet());
        Collections.sort(keys);
        int start = 0;
        while (start < keys.size() && k > 0) {
            ArrayList<Star> nextClosestStars = map.get(keys.get(start));
            int size = nextClosestStars.size();
            k -= size;

            if (k < 0) {
                for (int i = 0; i < size - Math.abs(k); i++) {
                    int randomInt = (int) (Math.random()
                            * (nextClosestStars.size()));
                    ret.add(nextClosestStars.get(randomInt));
                    nextClosestStars.remove(randomInt);
                }
                break;
            } else {
                for (Star nextStar: nextClosestStars) {
                    ret.add(nextStar);
                }
                start++;
            }
        }
        return ret;
    }



    // Neighbors algo based on star name input
    public List<Star> naiveNearestNeighbors(int k, String name) {
        ArrayList<Star> ret = new ArrayList<>();
        if (k == 0) {
            return ret;
        }
        Star toFind = findStar(name);
        if (toFind == null) {
            System.err.println("Star not found");
            return null;
        }
        HashMap<Double, ArrayList<Star>> map = new HashMap<>();

        for (Star star: starList) {
            if (!star.getID().equals(toFind.getID())) {
                double distance = star.calculateDistance(toFind);
                if (!map.containsKey(distance)) {
                    map.put(distance, new ArrayList<Star>());
                }
                map.get(distance).add(star);
            }
        }
        ArrayList<Double> keys = new ArrayList<>(map.keySet());
        Collections.sort(keys);
        int start = 0;
        while (start < keys.size() && k > 0) {
            ArrayList<Star> nextClosestStars = map.get(keys.get(start));
            int size = nextClosestStars.size();
            k -= size;
            if (k < 0) {
                for (int i = 0; i < size - Math.abs(k); i++) {
                    int randomInt = (int) (Math.random()
                            * (nextClosestStars.size()));
                    ret.add(nextClosestStars.get(randomInt));
                    nextClosestStars.remove(randomInt);
                }
                break;
            } else {
                for (Star nextStar: nextClosestStars) {
                    ret.add(nextStar);
                }
                start++;
            }
        }
        //return our arrayList
        return ret;
    }

    public Star findStar(double x, double y, double z) {
        for (Star star: starList) {
            if (star.getX() == x && star.getY() == y && star.getZ() == z) {
                return star;
            }
        }
        return new Star(" ", " ", Double.toString(x), Double.toString(y), Double.toString(z));
    }

    public Star findStar(String name) {
        for (Star star: starList) {
            if (star.getName().equals(name)) {
                return star;
            }
        }
        return null;
    }

    public boolean isClassInt(String[] scale) {
        try {
            Double.parseDouble(scale[2]);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public String[] processComs(String coms, String[] cmd) {
        String s = coms.substring(coms.indexOf('"') + 1, coms.length() - 1);
        if (coms.charAt(coms.length() - 1) != '"') {
            return null;
        }
        if (s.indexOf('"') == -1) {
            return new String[]{cmd[0], cmd[1], s};
        }
        return null;
    }

}

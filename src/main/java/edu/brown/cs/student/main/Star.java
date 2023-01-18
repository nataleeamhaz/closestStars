package edu.brown.cs.student.main;
import edu.brown.cs.student.main.KDTree.KDInsert;

public class Star implements KDInsert {
    private String id;
    private String name;
    private double x;
    private double y;
    private double z;

    public Star(String starID, String name, String x,
                String y, String z) throws IllegalArgumentException {
        if (starID == null || x == null || y == null || z == null) {
            throw new
                    IllegalArgumentException("Invalid Arguments");
        }
        this.id = starID;
        this.name = name;

        this.x = Double.parseDouble(x);
        this.y = Double.parseDouble(y);
        this.z = Double.parseDouble(z);


    }

    @Override
    public String getID() {
        return id;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public double getX() {
        return this.x;
    }
    @Override
    public double getY() {
        return this.y;
    }
    @Override
    public double getZ() {
        return this.z;
    }
    @Override
    public double getCoordinate(int index) {
        if (index == 0) {
            return this.getX();
        } else if (index == 1) {
            return this.getY();
        } else {
            return this.getZ();
        }
    }

    @Override
    public int dimNum() {
        return 3;
    }

    @Override
    public double calculateDistance(KDInsert node) {
        return Math.sqrt(Math.pow(Math.abs(node.getX() - x), 2)
                + Math.pow(Math.abs(node.getY() - y), 2)
                + Math.pow(Math.abs(node.getZ() - z), 2));    }
}

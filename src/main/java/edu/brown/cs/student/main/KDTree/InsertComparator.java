package edu.brown.cs.student.main.KDTree;

import java.util.Comparator;

public class InsertComparator implements Comparator <KDInsert> {
    private int dim;
    public InsertComparator(int dimension){
        this.dim = dimension;
    }
    @Override
    public int compare(KDInsert o1, KDInsert o2) {
        return Double.compare(o1.getCoordinate(dim), o2.getCoordinate(dim));
    }
}
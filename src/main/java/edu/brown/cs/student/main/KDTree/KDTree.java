package edu.brown.cs.student.main.KDTree;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;



public class KDTree<E extends KDInsert> {
    int dim;
    KDNode root;
    ArrayList _lis;
    public KDTree(int dimension, ArrayList els ){
        this.dim = dimension;
        this.root = this.insert(els, 0);
        _lis = els;

    }

    /**
     * Constructs tree using list of elements and int depth reflecting depth of tree
     * @param lis of elements
     * @param depth level of height reached
     * @return the root of the tree
     */
    public KDNode<E> insert(List<E> lis, int depth){
        if(lis.size() == 0){
            return null;
        }
        int dimension;
        dimension = depth % this.dim;
        lis.sort(new InsertComparator(dimension));
        int mid = lis.size() / 2;
        //split original list into 2
        List<E> list1 = new ArrayList<>();
        List<E> list2 = new ArrayList<>();
        for (int i = 0; i < mid; i ++){
            list1.add(lis.get(i));
        }
        for (int i = mid + 1; i < lis.size(); i ++){
            list2.add(lis.get(i));
        }
        depth++;
        E el = lis.get(mid);



        KDNode<E> midNode = new KDNode<>(el, insert(list1, depth), insert(list2, depth));
        //return root
        return midNode;



    }

    /**
     * Boolean function to check if the list and therefore tree is empty
     * @return
     */
    public Boolean isEmpty(){
        if (_lis.isEmpty()){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Get method to return the root of tree
     * @return
     */
    public KDNode getRoot() {
        return this.root;
    }


}




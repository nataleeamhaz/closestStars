package edu.brown.cs.student.main.KDTree;


public class KDNode<E extends KDInsert>{

    private KDNode<E> _left;
    private KDNode<E> _right;
    private E _type;

    /**
     * A class for the KD nodes
     * @param type of element of node
     * @param left node of current node
     * @param right node of current node
     */
    public KDNode(E type, KDNode<E> left, KDNode<E> right){
        _left = left;
        _right = right;
        _type = type;



    }

    /**
     * Setter and getter methods for left and right side of tree
     * @return
     */
    public KDNode getLeft(){
        return _left;

    }
    public void setRight(KDNode right){
        _right = right;

    }
    public void setLeft(KDNode left){
        _left = left;

    }
    public KDNode getRight(){
        return _right;

    }
    public E getEl(){
        return _type;
    }


}

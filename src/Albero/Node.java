package Albero;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {
    private List<Node<T>> children = new ArrayList<Node<T>>();
    private Node<T> parent = null;
    private T data = null;
    private T dataAttribute = null;

    public Node(T data1, T data2 ) {
        this.data = data1;
        this.dataAttribute = data1;
    }
    public Node(T data) {
        this.data = data;
    }

    public Node(T data, Node<T> parent) {
        this.data = data;
        this.parent = parent;
    }

    public List<Node<T>> getChildren() {
        return children;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public void transferChildren(T data) {
        if (data instanceof Node) {
            Node<T> child = (Node) data;
            if(child.isLeaf()){
                this.addChild(child);
            }
            for (Node<T> i: child.getChildren()) {
                this.addChild(i);
            }
        }else{
            Node<T> child = new Node<T>(data);
            this.addChild(child);
        }
    }
    public void addChild(T data) {
        if(data instanceof Node){
            Node<T> child = (Node) data;
            child.setParent(this);
            this.children.add(child);
        }else {
            Node<T> child = new Node<T>(data);

            child.setParent(this);
            this.children.add(child);
        }
    }

    public void addChild(T data, T dataAttribute) {
        if(data instanceof Node){
            Node<T> child = (Node) data;
            child.setParent(this);
            this.children.add(child);
        }else {
            Node<T> child = new Node<T>(data, dataAttribute);

            child.setParent(this);
            this.children.add(child);
        }
    }

    public void addChild(Node<T> child) {
        child.setParent(this);
        this.children.add(child);
    }


    public T getData() {
        return this.data;
    }
    public T getDataAttribute() {
        return this.dataAttribute;
    }


    public void setData(T data) {
        this.data = data;
    }

    public boolean isRoot() {
        return (this.parent == null);
    }

    public boolean isLeaf() {
        return this.children.size() == 0;
    }

    public void removeParent() {
        this.parent = null;
    }
    public String toString(){
        return this.data.toString();
    }
}
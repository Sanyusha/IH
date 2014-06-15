package android.ih.piemenu;

import java.util.ArrayList;
import java.util.List;

public class BasicTree<T> {
	private Node<T> root;

    public Node<T> getRoot() {
		return root;
	}

	public void setRoot(Node<T> root) {
		this.root = root;
	}
	
	public BasicTree() {
		this(null);
	}
	
	public BasicTree(T rootData) {
        root = new Node<T>();
        root.setTouched(true);
        root.setData(rootData);
        root.setChildren(new ArrayList<Node<T>>());
    }
	
	public void makeTreeNotTouched(Node<T> n) {
		if (n == null) return;
		
		if (n != this.root) {
			n.setTouched(false);
		}
		
		for (Node<T> el: n.getChildren()) {
			makeTreeNotTouched(el);
		}
	}
	
	public void makeLevelNotTouched(Node<T> n) {
		if (n == this.root) {
			makeTreeNotTouched(n);
			return;
		}
		
		for (Node<T> el: n.getParent().getChildren()) {
			makeTreeNotTouched(el);
		}
	}
	
    public static class Node<T> {
        private T data;
        private Node<T> parent;
        private List<Node<T>> children;
        private boolean bmpDrawn;
        private boolean touched;
        
        public Node() {
        	this.data = null;
        	this.children = new ArrayList<Node<T>>();
        	this.bmpDrawn = false;
        	this.touched = false;
        }
        
        public Node(T data) {
        	this.data = data;
        	this.children = new ArrayList<Node<T>>();
        	this.bmpDrawn = false;
        	this.touched = false;
        }
        
        public T getData() {
			return data;
		}
		public void setData(T data) {
			this.data = data;
		}
		public Node<T> getParent() {
			return parent;
		}
		public void setParent(Node<T> parent) {
			this.parent = parent;
		}
		public List<Node<T>> getChildren() {
			return children;
		}
		public void setChildren(List<Node<T>> children) {
			this.children = children;
			
			for (Node<T> el: children) {
				el.setParent(this);
			}
		}
		
		public boolean getBmpDrawn() {
			return this.bmpDrawn;
		}
		
		public void setBmpDrawn(boolean bmpDrawn) {
			this.bmpDrawn = bmpDrawn;
		}
		
		public boolean getTouched() {
			return this.touched;
		}
		
		public void setTouched(boolean touched) {
			this.touched = touched;
		}
    }
}

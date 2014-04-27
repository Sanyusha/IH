package com.example.piemenu;

import java.util.ArrayList;
import java.util.List;

public class BasicTree<T> {
	private Node<T> root;

    public BasicTree(T rootData) {
        root = new Node<T>();
        root.setData(rootData);
        root.setChildren(new ArrayList<Node<T>>());
    }

    public static class Node<T> {
        private T data;
        private Node<T> parent;
        private List<Node<T>> children;

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
		}
    }
}

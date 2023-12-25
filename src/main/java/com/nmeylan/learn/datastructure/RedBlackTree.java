package com.nmeylan.learn.datastructure;


import java.util.Optional;

public class RedBlackTree {

    private RedBlackTreeNode root;

    public RedBlackTreeNode insert(long value) {
        RedBlackTreeNode newNode = new RedBlackTreeNode(value);
        if (root == null) {
            newNode.setColor(NodeColor.BLACK);
            root = newNode;
            return root;
        }
        RedBlackTreeNode node = root;
        while (node != null) {
            if (value < node.getValue()) {
                if (node.getLeftChild() == null) {
                    node.setLeftChild(newNode);
                    break;
                } else {
                    node = node.getLeftChild();
                }
            } else if (value > node.getValue()) {
                if (node.getRightChild() == null) {
                    node.setRightChild(newNode);
                    break;
                } else {
                    node = node.getRightChild();
                }
            } else {
                return null;
            }
        }
        if (newNode.isRed() && newNode.getParent().isRed()) {
            recolourNodes(newNode);
            root.setColor(NodeColor.BLACK);
        }

        return newNode;

    }

    private void recolourNodes(RedBlackTreeNode newNode) {
        if (newNode.getUncle().isPresent() && newNode.getUncle().get().isRed()) {
            newNode.getParent().setColor(NodeColor.BLACK);
            newNode.getUncle().get().setColor(NodeColor.BLACK);
            RedBlackTreeNode grandFather = newNode.getParent().getParent();
            grandFather.setColor(NodeColor.RED);
            if (grandFather.getParent() != null && grandFather.getParent().isRed() && grandFather.getUncle().isPresent()) {
                recolourNodes(grandFather);
            }
        } else if (
                newNode.isLeftChild() && newNode.getParent().isLeftChild() ||
                newNode.isRightChild() && newNode.getParent().isRightChild()) {
            RedBlackTreeNode parent = newNode.getParent();
            RedBlackTreeNode grandFather = newNode.getParent().getParent();
            NodeColor grandFatherColor = grandFather.getColor();
            NodeColor parentColor = parent.getColor();
            if (newNode.isLeftChild() && newNode.getParent().isLeftChild()) { // Left Left case
                RedBlackTreeNode right = parent.getRightChild();
                RedBlackTreeNode grandFatherParent = grandFather.getParent();
                if (grandFatherParent!= null && grandFather.isRightChild()) {
                    grandFatherParent.setRightChild(parent);
                } else if (grandFatherParent!= null && grandFather.isLeftChild()) {
                    grandFatherParent.setLeftChild(parent);
                }
                parent.setRightChild(grandFather);
                grandFather.setLeftChild(right);
            } else if (newNode.isRightChild() && newNode.getParent().isRightChild()) { // Right Right case
                RedBlackTreeNode left = parent.getLeftChild();
                RedBlackTreeNode grandFatherParent = grandFather.getParent();
                if (grandFatherParent!= null && grandFather.isRightChild()) {
                    grandFatherParent.setRightChild(parent);
                } else if (grandFatherParent!= null && grandFather.isLeftChild()) {
                    grandFatherParent.setLeftChild(parent);
                }
                parent.setLeftChild(grandFather);
                grandFather.setRightChild(left);
            }
            if (getRoot().equals(grandFather)) {
                root = parent;
            }
            grandFather.setColor(parentColor);
            parent.setColor(grandFatherColor);
        } else if (
                newNode.isRightChild() && newNode.getParent().isLeftChild()
               || newNode.isLeftChild() && newNode.getParent().isRightChild()) {
            RedBlackTreeNode parent = newNode.getParent();
            RedBlackTreeNode grandFather = newNode.getParent().getParent();
            if (newNode.isRightChild() && newNode.getParent().isLeftChild()) {
                RedBlackTreeNode left = newNode.getLeftChild();
                newNode.setLeftChild(parent);
                grandFather.setLeftChild(newNode);
                parent.setRightChild(left);
                recolourNodes(parent);
            }
            else if (newNode.isLeftChild() && newNode.getParent().isRightChild()) {
                RedBlackTreeNode right = newNode.getRightChild();
                newNode.setRightChild(parent);
                grandFather.setRightChild(newNode);
                parent.setLeftChild(right);
                recolourNodes(parent);
            }
        }
    }

    public Optional<RedBlackTreeNode> getNode(long value) {
        RedBlackTreeNode node = root;
        while(node != null) {
            if (node.getLeftChild() != null && value < node.value) {
                node = node.getLeftChild();
            } else if (node.getRightChild() != null && value > node.value) {
                node = node.getRightChild();
            } else if (node.value == value){
                return Optional.of(node);
            } else {
                break;
            }
        }
        return Optional.empty();
    }


    public RedBlackTreeNode getRoot() {
        return root;
    }

    public static class RedBlackTreeNode {
        private RedBlackTreeNode parent;
        private RedBlackTreeNode leftChild;
        private RedBlackTreeNode rightChild;
        private NodeColor color;
        private final long value;

        public RedBlackTreeNode(long value) {
            this.color = NodeColor.RED;
            this.value = value;
        }

        public void setParent(RedBlackTreeNode parent) {
            this.parent = parent;
        }

        public void setColor(NodeColor color) {
            this.color = color;
        }

        public void setLeftChild(RedBlackTreeNode leftChild) {
            if (leftChild != null) {
                leftChild.setParent(this);
            }
            this.leftChild = leftChild;
        }

        public void setRightChild(RedBlackTreeNode rightChild) {
            if (rightChild != null) {
                rightChild.setParent(this);
            }
            this.rightChild = rightChild;
        }

        public boolean isLeftChild() {
            return parent.getLeftChild() != null && parent.getLeftChild().equals(this);
        }

        public boolean isRightChild() {
            return parent.getRightChild() != null && parent.getRightChild().equals(this);
        }

        public RedBlackTreeNode getParent() {
            return parent;
        }

        public RedBlackTreeNode getLeftChild() {
            return leftChild;
        }

        public RedBlackTreeNode getRightChild() {
            return rightChild;
        }

        public NodeColor getColor() {
            return color;
        }

        public long getValue() {
            return value;
        }

        public boolean isRed() {
            return getColor().equals(NodeColor.RED);
        }

        public Optional<RedBlackTreeNode> getUncle() {
            if (getParent() == null || getParent().getParent() == null) {
                return Optional.empty();
            }
            if (getParent().isRightChild()) {
                return Optional.ofNullable(getParent().getParent().getLeftChild());
            }
            return Optional.ofNullable(getParent().getParent().getRightChild());
        }

        @Override
        public String toString() {
            return "RedBlackTreeNode{" +
                    "value=" + value +
                    ", color=" + color +
                    '}';
        }
    }

    public enum NodeColor {
        RED,
        BLACK
    }
}
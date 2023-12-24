package com.nmeylan.learn.datastructure;

import org.junit.jupiter.api.Test;

import static com.nmeylan.learn.datastructure.RedBlackTree.NodeColor.BLACK;
import static com.nmeylan.learn.datastructure.RedBlackTree.NodeColor.RED;
import static org.assertj.core.api.Assertions.assertThat;

public class RedBlackTreeTest {

    @Test
    public void insert_whenThereIsNoRoot_shouldInsertRoot() {
        // Given
        RedBlackTree redBlackTree = new RedBlackTree();
        // When
        redBlackTree.insert(10);
        // Then
        assertThat(redBlackTree.getRoot()).isNotNull();
        assertThat(redBlackTree.getRoot().getValue()).isEqualTo(10);
    }

    @Test
    public void insert_whenValueIsLessThanParent_shouldInsertAtLeft() {
        // Given
        RedBlackTree redBlackTree = new RedBlackTree();
        redBlackTree.insert(10);
        // When
        RedBlackTree.RedBlackTreeNode inserted = redBlackTree.insert(1);
        // Then
        assertThat(inserted).isEqualTo(redBlackTree.getRoot().getLeftChild());
        assertThat(inserted.getParent()).isEqualTo(redBlackTree.getRoot());
        assertThat(inserted.getColor()).isEqualTo(RED);
    }

    @Test
    public void insert_whenValueIsGreaterThanParent_shouldInsertAtRight() {
        // Given
        RedBlackTree redBlackTree = new RedBlackTree();
        redBlackTree.insert(10);
        // When
        RedBlackTree.RedBlackTreeNode inserted = redBlackTree.insert(11);
        // Then
        assertThat(inserted).isEqualTo(redBlackTree.getRoot().getRightChild());
        assertThat(inserted.getParent()).isEqualTo(redBlackTree.getRoot());
        assertThat(inserted.getColor()).isEqualTo(RED);
    }

    @Test
    public void insert_whenValueIsEqualToParent_shouldNotInsert() {
        // Given
        RedBlackTree redBlackTree = new RedBlackTree();
        RedBlackTree.RedBlackTreeNode root = redBlackTree.insert(10);
        // When
        RedBlackTree.RedBlackTreeNode inserted = redBlackTree.insert(10);
        // Then
        assertThat(inserted).isNull();
        assertThat(root.getLeftChild()).isNull();
        assertThat(root.getRightChild()).isNull();
    }

    @Test
    public void getUncle_whenLeftSide() {
        // Given
        RedBlackTree redBlackTree = new RedBlackTree();
        redBlackTree.insert(10);
        RedBlackTree.RedBlackTreeNode node1 = redBlackTree.insert(1);
        RedBlackTree.RedBlackTreeNode node11 = redBlackTree.insert(11);
        // When
        RedBlackTree.RedBlackTreeNode inserted = redBlackTree.insert(2);
        // Then
        assertThat(inserted.getParent()).isEqualTo(node1);
        assertThat(inserted).isEqualTo(node1.getRightChild());
        assertThat(inserted.getUncle()).isPresent().hasValue(node11);
    }

    @Test
    public void getUncle_whenRightSide() {
        // Given
        RedBlackTree redBlackTree = new RedBlackTree();
        redBlackTree.insert(10);
        RedBlackTree.RedBlackTreeNode node1 = redBlackTree.insert(1);
        RedBlackTree.RedBlackTreeNode node11 = redBlackTree.insert(11);
        // When
        RedBlackTree.RedBlackTreeNode inserted = redBlackTree.insert(12);
        // Then
        assertThat(inserted.getParent()).isEqualTo(node11);
        assertThat(inserted).isEqualTo(node11.getRightChild());
        assertThat(inserted.getUncle()).isPresent().hasValue(node1);
    }

    @Test
    public void insert_whenValueIsLessThanParent_shouldInsertAtLeftNested() {
        // Given
        RedBlackTree redBlackTree = new RedBlackTree();
        redBlackTree.insert(10);
        RedBlackTree.RedBlackTreeNode node1 = redBlackTree.insert(1);
        redBlackTree.insert(11);
        // When
        RedBlackTree.RedBlackTreeNode inserted = redBlackTree.insert(2);
        // Then
        assertThat(inserted.getParent()).isEqualTo(node1);
        assertThat(inserted).isEqualTo(node1.getRightChild());
    }

    @Test
    public void insert_whenChildAndParentAreRedAndGrandParentIsRoot_shouldDownBlackness() {
        // Given
        RedBlackTree redBlackTree = new RedBlackTree();
        RedBlackTree.RedBlackTreeNode root = redBlackTree.insert(10);
        RedBlackTree.RedBlackTreeNode node1 = redBlackTree.insert(1);
        redBlackTree.insert(11);
        // When
        RedBlackTree.RedBlackTreeNode inserted = redBlackTree.insert(2);
        // Then
        assertThat(inserted.getParent()).isEqualTo(node1);
        assertThat(inserted).isEqualTo(node1.getRightChild());
        assertThat(inserted.getColor()).isEqualTo(RED);
        assertThat(inserted.getParent().getColor()).isEqualTo(BLACK);
        assertThat(inserted.getUncle().get().getColor()).isEqualTo(BLACK);
        assertThat(root.getColor()).isEqualTo(BLACK);
    }

    @Test
    public void insert_shouldRotateRightRightRotation() {
        // Given
        RedBlackTree redBlackTree = new RedBlackTree();
        RedBlackTree.RedBlackTreeNode node3 = redBlackTree.insert(3);
        RedBlackTree.RedBlackTreeNode node21 = redBlackTree.insert(21);
        // When
        RedBlackTree.RedBlackTreeNode node32 = redBlackTree.insert(32);
        // Then
        assertThat(redBlackTree.getRoot()).isEqualTo(node21);
        assertThat(node21.getColor()).isEqualTo(BLACK);
        assertThat(redBlackTree.getRoot().getLeftChild()).isEqualTo(node3);
        assertThat(node3.getColor()).isEqualTo(RED);
        assertThat(redBlackTree.getRoot().getRightChild()).isEqualTo(node32);
        assertThat(node32.getColor()).isEqualTo(RED);
    }

    @Test
    public void insert_shouldRotateLeftLeftRotation() {
        // Given
        RedBlackTree redBlackTree = new RedBlackTree();
        RedBlackTree.RedBlackTreeNode node10 = redBlackTree.insert(10);
        RedBlackTree.RedBlackTreeNode node9 = redBlackTree.insert(9);
        // When
        RedBlackTree.RedBlackTreeNode node1 = redBlackTree.insert(1);
        // Then
        assertThat(redBlackTree.getRoot()).isEqualTo(node9);
        assertThat(node9.getColor()).isEqualTo(BLACK);
        assertThat(redBlackTree.getRoot().getRightChild()).isEqualTo(node10);
        assertThat(node10.getColor()).isEqualTo(RED);
        assertThat(redBlackTree.getRoot().getLeftChild()).isEqualTo(node1);
        assertThat(node1.getColor()).isEqualTo(RED);
    }

    @Test
    public void insert_shouldRotateRightLeftRotation() {
        // Given
        RedBlackTree redBlackTree = new RedBlackTree();
        RedBlackTree.RedBlackTreeNode node10 = redBlackTree.insert(10);
        RedBlackTree.RedBlackTreeNode node1 = redBlackTree.insert(1);
        RedBlackTree.RedBlackTreeNode node20 = redBlackTree.insert(20);
        RedBlackTree.RedBlackTreeNode node25 = redBlackTree.insert(25);
        // When
        RedBlackTree.RedBlackTreeNode node22 = redBlackTree.insert(22);
        // Then
        assertThat(redBlackTree.getRoot()).isEqualTo(node10);
        assertThat(redBlackTree.getRoot().getLeftChild()).isEqualTo(node1);
        assertThat(redBlackTree.getRoot().getRightChild()).isEqualTo(node22);
        assertThat(node22.getLeftChild()).isEqualTo(node20);
        assertThat(node22.getRightChild()).isEqualTo(node25);
        assertThat(node10.getColor()).isEqualTo(BLACK);
        assertThat(node1.getColor()).isEqualTo(BLACK);
        assertThat(node22.getColor()).isEqualTo(BLACK);
        assertThat(node20.getColor()).isEqualTo(RED);
        assertThat(node25.getColor()).isEqualTo(RED);
    }
    @Test
    public void insert_shouldRotateLeftRightRotation() {
        // Given
        RedBlackTree redBlackTree = new RedBlackTree();
        RedBlackTree.RedBlackTreeNode node20 = redBlackTree.insert(20);
        RedBlackTree.RedBlackTreeNode node10 = redBlackTree.insert(10);
        RedBlackTree.RedBlackTreeNode node30 = redBlackTree.insert(30);
        RedBlackTree.RedBlackTreeNode node7 = redBlackTree.insert(7);
        // When
        RedBlackTree.RedBlackTreeNode node9 = redBlackTree.insert(9);
        // Then
        assertThat(redBlackTree.getRoot()).isEqualTo(node20);
        assertThat(redBlackTree.getRoot().getLeftChild()).isEqualTo(node9);
        assertThat(redBlackTree.getRoot().getRightChild()).isEqualTo(node30);
        assertThat(node9.getLeftChild()).isEqualTo(node7);
        assertThat(node9.getRightChild()).isEqualTo(node10);
        assertThat(node20.getColor()).isEqualTo(BLACK);
        assertThat(node9.getColor()).isEqualTo(BLACK);
        assertThat(node30.getColor()).isEqualTo(BLACK);
        assertThat(node7.getColor()).isEqualTo(RED);
        assertThat(node10.getColor()).isEqualTo(RED);
    }

}

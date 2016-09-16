package com.marts;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by marts on 15.09.2016.
 */
public class Percolation {
    private WeightedQuickUnionUF wUF;
    private int virtualTopIndex = 0;
    private int virtualBottomIndex;
    int dimension;
    boolean[][] openedBlocks;
    boolean percolates;

    public Percolation(int n) {
        if (n < 1) {
            throw new java.lang.IllegalArgumentException("Dimension must be more than 0");
        }
        dimension = n;
        virtualBottomIndex = n * n + 1;
        wUF = new WeightedQuickUnionUF(n * n + 2);
        openedBlocks = new boolean[n][n];
    }

    public void open(int i, int j) {
        checkParams(i, j);
        if (isOpen(i, j)){
            return;
        }
        if (i == 1) {
            wUF.union(getWUFIndex(i, j), virtualTopIndex);
        }
        if (i == dimension){
            wUF.union(getWUFIndex(i, j), virtualBottomIndex);
        }

        if (i > 1 && isOpen(i - 1, j)) {
            wUF.union(getWUFIndex(i, j), getWUFIndex(i - 1, j));
        }
        if (j > 1 && isOpen(i, j - 1)) {
            wUF.union(getWUFIndex(i, j), getWUFIndex(i, j - 1));
        }
        if (i < dimension && isOpen(i + 1, j)) {
            wUF.union(getWUFIndex(i, j), getWUFIndex(i + 1, j));
        }
        if (j < dimension && isOpen(i, j + 1)) {
            wUF.union(getWUFIndex(i, j), getWUFIndex(i, j + 1));
        }

        openedBlocks[i - 1][j - 1] = true;
    }

    public boolean isOpen(int i, int j) {
        checkParams(i, j);

        return openedBlocks[i - 1][j - 1];
    }

    public boolean isFull(int i, int j) {
        checkParams(i, j);

        return wUF.connected(virtualTopIndex, getWUFIndex(i, j));
    }

    private int getWUFIndex(int i, int j) {
        return (i - 1) * dimension + j;
    }

    private void checkParams(int i, int j) {
        String message = "";
        boolean exceptionExists = false;
        if (i < 1 || i > dimension) {
            message += "i must be in bounds [1.." + dimension + "];";
            exceptionExists = true;
        }
        if (j < 1 || j > dimension) {
            message += "j must be in bounds [1.." + dimension + "];";
            exceptionExists = true;
        }
        if (exceptionExists) {
            throw new java.lang.IndexOutOfBoundsException(message);
        }
    }

    public boolean percolates() {
        if (percolates) {
            return true;
        } else if (wUF.connected(virtualBottomIndex, virtualTopIndex)) {
            percolates = true;
        }
        return percolates;
    }
}

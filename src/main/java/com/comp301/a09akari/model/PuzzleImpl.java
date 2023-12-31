package com.comp301.a09akari.model;

public class PuzzleImpl implements Puzzle {
  private int[][] board;

  public PuzzleImpl(int[][] board) {
    // Your constructor code here
    if (board == null) {
      throw new IllegalArgumentException("Null grid");
    }
    this.board = board;
  }

  @Override
  public int getWidth() {
    return this.board[0].length;
  }

  @Override
  public int getHeight() {
    return this.board.length;
  }

  @Override
  public CellType getCellType(int r, int c) {
    if (r > this.getHeight() - 1) {
      System.out.println("R too big");
      throw new IndexOutOfBoundsException("Index out of bounds");
    }
    if (c > this.getWidth() - 1) {
      System.out.println("C too big");
      throw new IndexOutOfBoundsException("Index out of bounds");
    }
    if (r < 0) {
      System.out.println("r too small");
      throw new IndexOutOfBoundsException("Index out of bounds");
    }
    if (c < 0) {
      System.out.println("c too small");
      throw new IndexOutOfBoundsException("Index out of bounds");
    }
    if (board[r][c] >= 0 && board[r][c] <= 4) {
      return CellType.CLUE;
    } else if (board[r][c] == 5) {
      return CellType.WALL;
    } else {
      return CellType.CORRIDOR;
    }
  }

  @Override
  public int getClue(int r, int c) {
    if (r > getHeight() - 1 || c > getWidth() - 1 || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException("Index out of bounds.");
    }
    if (!(getCellType(r, c) == CellType.CLUE)) {
      throw new IllegalArgumentException("Cell is not a clue");
    }
    if (board[r][c] >= 0 || board[r][c] <= 4) {
      return board[r][c];
    }
    return 0;
  }
}

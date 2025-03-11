package cs3500.pawnsboard;
import java.awt.*;

public class Board {
  private final int rows;
  private final int cols;
  private final Cell[][] grid;

  public Board(int rows, int cols) {
    this.rows = rows;
    this.cols = cols;
    this.grid = new Cell[rows][cols];

    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        grid[r][c] = new Cell();
      }
    }

    for (int r = 0; r < rows; r++) {
      grid[r][0].setPawns(1, Color.RED);
      grid[r][cols - 1].setPawns(1, Color.BLUE);
    }
  }

  public boolean placeCard(Player player, Card card, int row, int col) {
    if (!isValidMove(player, card, row, col)) {
      return false;
    }
    grid[row][col].setCard(card);
    applyInfluence(player, card, row, col);
    return true;
  }

  private void applyInfluence(Player player, Card card, int row, int col) {
    char[][] influence = player.getColor() == Color.RED ?
            card.getInfluenceGrid() : card.getMirroredInfluenceGrid();
    int center = 2;
    for (int r = 0; r < 5; r++) {
      for (int c = 0; c < 5; c++) {
        int targetRow = row + (r - center);
        int targetCol = col + (c - center);

        if (!isValidCell(targetRow, targetCol)) {
          grid[targetRow][targetCol].applyInfluence(player.getColor(), influence[r][c]);
        }
      }
    }
  }

  private boolean isValidCell(int row, int col) {
    return row >= 0 && row < rows && col >= 0 && col < cols;
  }

  boolean isValidMove(Player player, Card card, int row, int col) {
    return grid[row][col].canPlaceCard(player, card);
  }

  public void printTextView() {
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        System.out.print(grid[r][c].toTextualView() + " ");
      }
      System.out.println();
    }
  }

  public int calculateTotalScore(Color playerColor) {
    int totalScore = 0;

    for (int r = 0; r < rows; r++) {
      int redRowScore = 0, blueRowScore = 0;

      for (int c = 0; c < cols; c++) {
        Card card = grid[r][c].getCard();
        if (card != null) {
          if (grid[r][c].getOwner() == Color.RED) {
            redRowScore += card.getValue();
          } else {
            blueRowScore += card.getValue();
          }
        }
      }

      if (redRowScore > blueRowScore) {
        totalScore += (playerColor == Color.RED) ? redRowScore : 0;
      } else if (blueRowScore > redRowScore) {
        totalScore += (playerColor == Color.BLUE) ? blueRowScore : 0;
      }
    }

    return totalScore;
  }

  public int getRows() {
    return rows;
  }

  public int getCols() {
    return cols;
  }


  //for testing
  public Cell getCell(int row, int col) {
    if (row < 0 || row >= rows || col < 0 || col >= cols) {
      throw new IllegalArgumentException("Invalid board position: (" + row + ", " + col + ")");
    }
    return grid[row][col];
  } //oaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
}

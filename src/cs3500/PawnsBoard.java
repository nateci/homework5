package cs3500;

import java.awt.*;
import java.io.IOException;
import java.util.List;

import cs3500.pawnsboard.Player;
import cs3500.pawnsboard.Board;
import cs3500.pawnsboard.Card;
import cs3500.pawnsboard.DeckReader;
import cs3500.pawnsboard.Game;

public class PawnsBoard {
  public static void main(String[] args) {
    try {
      List<Card> redDeck = DeckReader.loadDeck("docs/deck.config", Color.RED);
      List<Card> blueDeck = DeckReader.loadDeck("docs/deck.config", Color.BLUE);

      Player redPlayer = new Player(Color.RED, redDeck);
      Player bluePlayer = new Player(Color.BLUE, blueDeck);
      Board board = new Board(3, 5); //wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww

      Game game = new Game(board, redPlayer, bluePlayer);
      game.play();
    } catch (IOException e) {
      System.out.println("Error loading deck: " + e.getMessage());
    }
  }
}



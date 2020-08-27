package com.ryankerr;

public class Card {
  private char rank;
  private char suit;

  public Card(char rank, char suit) {
    this.rank = rank;
    this.suit = suit;
  }

  public char getRank() {
    return this.rank;
  }

  public char getSuit() {
    return this.suit;
  }

  public boolean equals(Card card) {
    return card.getRank() == this.getRank() && card.getSuit() == this.getSuit();
  }

  public String toString() {
    return "" + this.getRank() + this.getSuit();
  }
}

package com.ryankerr;

public class Card implements Comparable<Card> {
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

  @Override
  public int compareTo(Card card) {
    // TODO: implement
    return 0;
  }
}

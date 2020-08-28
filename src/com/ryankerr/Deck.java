package com.ryankerr;

import java.util.ArrayList;
import java.util.Iterator;

public class Deck {
  private ArrayList<Card> cards = new ArrayList<>();

  public Deck() {
    char[] values = {'A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2'};
    char[] suits = {'S', 'D', 'C', 'H'};

    for (char value : values) {
      for (char suit : suits) {
        this.cards.add(new Card(value, suit));
      }
    }
  }

  public void remove(char value, char suit) {
    this.remove(new Card(value, suit));
  }

  public void remove(Card card) {
    Iterator<Card> iter = this.cards.iterator();
    while (iter.hasNext()) {
      Card next = iter.next();
      if (next.equals(card)) {
        iter.remove();
      }
    }
  }

  public int getNumCards() {
    return this.cards.size();
  }

  public ArrayList<Card> getCards() {
    return this.cards;
  }
}

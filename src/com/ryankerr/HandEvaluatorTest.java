package com.ryankerr;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

  @Test
  void findHighCard() {
    char[] ranks = {'4', '5', '2', '7', '9', 'T'};
    char[] noFlush = {'C', 'D', 'H', 'S', 'C', 'D'};

    ArrayList<Card> cards = createCardList(ranks, noFlush);
    FiveCardHand bestHand = findBestHand(cards);

  }

  private ArrayList<Card> createCardList(char[] ranks, char[] suits) {
    assert ranks.length == suits.length;
    ArrayList<Card> out = new ArrayList<>();
    for (int i = 0; i < ranks.length; i++) {
      out.add(new Card(ranks[i], suits[i]));
    }
    return out;
  }
}
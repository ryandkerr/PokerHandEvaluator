package com.ryankerr;

import com.sun.security.jgss.GSSUtil;
import org.junit.jupiter.api.Test;

import java.sql.SQLOutput;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class HandEvaluatorTest {

  @Test
  void findHighCard() {
    char[] ranks = {'4', '5', '2', '7', '9', 'T', 'Q'};
    char[] noFlush = {'C', 'D', 'H', 'S', 'C', 'D', 'H'};

    ArrayList<Card> cards = createCardList(ranks, noFlush);
    FiveCardHand bestHand = HandEvaluator.findBestHand(cards);
    String target = "00000000000000000CA975";
    assertEquals(bestHand.getEvaluation(), target);
  }

  @Test
  void findStraight() {
    char[] ranks = {'Q', 'Q', 'T', '9', '8', '7', '6'};
    char[] noFlush = {'C', 'D', 'H', 'S', 'C', 'D', 'H'};

    ArrayList<Card> cards = createCardList(ranks, noFlush);
    FiveCardHand bestHand = HandEvaluator.findBestHand(cards);
    System.out.println(bestHand);
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
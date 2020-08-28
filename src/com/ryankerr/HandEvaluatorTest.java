package com.ryankerr;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

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

  @Test
  void calculateWinProbabilityTest() {
    ArrayList<Card> h1 = new ArrayList<>(Arrays.asList(new Card('A', 'S'), new Card('K', 'D')));
    ArrayList<Card> h2 = new ArrayList<>(Arrays.asList(new Card('4', 'S'), new Card('4', 'D')));
    ArrayList<ArrayList<Card>> hands = new ArrayList<>(Arrays.asList(h1, h2));

    ArrayList<Card> tableCards = new ArrayList<>(Arrays.asList(
//            new Card('J', 'C')
//            new Card('T', 'H')
//            new Card('6', 'D')
//            new Card('4', 'S')
            ));
    float[] result = HandEvaluator.calculateWinProbability(hands, tableCards);

    for (float f : result) {
      System.out.println(f);
    }
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
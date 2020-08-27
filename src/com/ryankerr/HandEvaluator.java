package com.ryankerr;

import java.util.ArrayList;

public class HandEvaluator {

  // given a list of cards, recursively finds the best 5-card hand
  public static FiveCardHand findBestHand(ArrayList<Card> cards) {
    if (cards.size() == 5) {
      FiveCardHand hand = new FiveCardHand(cards);
      return hand;
    }

    FiveCardHand bestHand = null;
    for (int i = 0; i < cards.size(); i++) {
      ArrayList<Card> cardsClone = (ArrayList<Card>) cards.clone();
      cardsClone.remove(i);
      FiveCardHand hand = findBestHand(cardsClone);
      if (bestHand == null) {
        bestHand = hand;
      } else if (hand.compareTo(bestHand) > 0) {
        bestHand = hand;
      }
    }
    return bestHand;
  }
}

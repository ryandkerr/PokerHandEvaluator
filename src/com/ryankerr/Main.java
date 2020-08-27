package com.ryankerr;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class Main {

  public static void main(String[] args) {
    /*
    evaluateHands(hand h1, hand h2, table cards);
        - Generates all possible combinations of table cards
        - for each hand, generate best hand from hand + table cards
        - evaluate which hand wins & keep track of winner
        - return list of probability each hand wins

    General structure:
    tableCardCombinations = generateHands(handCards, tableCards)
    for tableCards in tableCardCombinations:
      bestHand
      for hand in handCards:
        best = findBestHand(hand, tableCards)
        if best > bestHand:
          bestHand = best
      keep track of which hand won

    do math to get percent that each hand won
    return list of percentages

    Methods:
    generateHands(handCards : list[card], tableCards : list[card]):  list[list[card]]
      deck = new Deck
      for card in handCards + tableCards:
         remove card from deck
      return combinations(deck)

    findBestHand(hand : list[card], tableCards : list[card]): list[cards]
      fiveCardCombinations = generate combinations of 5-card hands
      bestHand
      for hand in fiveCardCombinations:
        if hand is best:  TODO, compare hands
          bestHand = best

      return best hand

    isBetterHand(hand1 : list[card], hand2 : list[card]): byte (1 if true, 0 if tied, -1 if false)
      evaluation1 = Evaluation(hand1)
      evaluation2 = Evaluation(hand2)
      if evaluation1 == evaluation2:
        return 0
      else if evaluation1 > evaluation 2:
        return 1
      else:
        return -1


    Abstractions:
    - Card (suit, value)
    - Deck (list of cards, add, remove)
    - five-card hand (list of cards, evaluation)
    */

    char[] ranks = {'A', 'A', 'A', 'A', 'T'};
    char[] suits = {'S', 'D', 'H', 'C', 'S'};
    FiveCardHand hand1 = new FiveCardHand(ranks, suits);
    System.out.println(hand1.getEvaluation());
  }
}

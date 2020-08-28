package com.ryankerr;

import org.apache.commons.math3.util.CombinatoricsUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HandEvaluator {

  // given a list of cards, recursively finds the best 5-card hand
  public static FiveCardHand findBestHand(ArrayList<Card> cards) {
    if (cards.size() == 5) {
      return new FiveCardHand(cards);
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

  // given an array of hands and an array of table cards, returns an array with the win
  // probability of each hand
  public static float[] calculateWinProbability(ArrayList<ArrayList<Card>> hands,
                                                ArrayList<Card> tableCards) {
    assert tableCards.size() >= 0 && tableCards.size() < 5;  // TODO: allow for 5 table cards

    // set up the deck by removing cards that have already been dealt
    ArrayList<Card> cardsAlreadyDealt = new ArrayList<>();
    for (ArrayList<Card> hand : hands) {
      cardsAlreadyDealt.addAll(hand);
    }
    cardsAlreadyDealt.addAll(tableCards);

    Deck deck = new Deck();
    for (Card card : cardsAlreadyDealt) {
      deck.remove(card);
    }

    // iterate through all possible combinations of table cards and keep track of the winning hand
    ArrayList<Card> remainingCards = deck.getCards();

    int[] wins = countWins(hands, tableCards, remainingCards);
    int numCombinations = IntStream.of(wins).sum();
    float[] winProbabilities = new float[wins.length];
    for (int i = 0; i < wins.length; i++) {
      winProbabilities[i] = (float) wins[i] / numCombinations;
    }

    return winProbabilities;
  }

  private static int[] countWins(ArrayList<ArrayList<Card>> hands,
                                 ArrayList<Card> tableCards,
                                 ArrayList<Card> remainingCards) {
    int numCardsToDeal = 5 - tableCards.size();
    long totalCombinations = CombinatoricsUtils.binomialCoefficient(remainingCards.size(), numCardsToDeal);

    int[] wins = new int[hands.size() + 1];
    int maxIterations = 10_000;  // to prevent the program from taking too long we set a hard limit
    if (totalCombinations < maxIterations) {
      // when there are fewer combinations that our limit, check all of them
      Iterator<int[]> combinations = CombinatoricsUtils.combinationsIterator(remainingCards.size(),
              numCardsToDeal);
      int i = 0;
      while (combinations.hasNext() && i < maxIterations) {
        int[] combination = combinations.next();

        ArrayList<Card> tableCardsFinal = (ArrayList<Card>) tableCards.clone();
        for (int j : combination) {
          tableCardsFinal.add(remainingCards.get(j));
        }

        int bestHandIndex = findWinner(hands, tableCardsFinal);
        wins[bestHandIndex] += 1;
        i++;
      }
    } else {
      // when there are more combinations than out limit, check a random subset
      List<Integer> indices = IntStream.rangeClosed(0, remainingCards.size()-1)
              .boxed().collect(Collectors.toList());
      for (int i = 0; i < maxIterations; i++) {
        Collections.shuffle(indices);
        ArrayList<Card> tableCardsFinal = (ArrayList<Card>) tableCards.clone();
        for (int j = 0; j < numCardsToDeal; j++) {
          int randomIndex = indices.get(j);
          tableCardsFinal.add(remainingCards.get(randomIndex));
        }

        int bestHandIndex = findWinner(hands, tableCardsFinal);
        wins[bestHandIndex] += 1;
      }
    }

    return wins;
  }

  // Returns the index of the hand that won, or hands.size() if it's a draw
  private static int findWinner(ArrayList<ArrayList<Card>> hands,
                                ArrayList<Card> tableCardsFinal) {
    FiveCardHand bestHand = null;
    int bestHandIndex = -1;
    for (int i = 0; i < hands.size(); i++) {
      ArrayList<Card> hand = hands.get(i);
      ArrayList<Card> handOfSeven = (ArrayList<Card>) hand.clone();
      handOfSeven.addAll(tableCardsFinal);
      FiveCardHand best = findBestHand(handOfSeven);

      if (bestHand == null) {
        bestHand = best;
        bestHandIndex = i;
      } else if (best.compareTo(bestHand) > 0) {
        bestHand = best;
        bestHandIndex = i;
      } else if (best.compareTo(bestHand) == 0) {  // handle ties by returning hands.size()
        bestHandIndex = hands.size();
      }
    }

    return bestHandIndex;
  }
}

package com.ryankerr;

import java.util.ArrayList;

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

    // argument format: java com.ryankerr.Main [hand1] [hand2] [handN] -t [tableCards]
    // where hand cards are like: "ASKD"
    // -t signifies that the next cards are table cards
    // table cards are like: "JSQH9D"
    ArrayList<ArrayList<Card>> hands = parseHands(args);
    ArrayList<Card> tableCards = parseTableCards(args);
    float[] winProbabilities = HandEvaluator.calculateWinProbability(hands, tableCards);

    for (int i = 0; i < winProbabilities.length; i++) {
      String hand = "";
      if (i == winProbabilities.length - 1) {
        hand = "Tie";
      } else {
        hand = cardsToString(hands.get(i));
      }
      System.out.println(hand + " :" + winProbabilities[i]);
    }
  }

  // returns the list of 2-card hands from the command-line arguments
  private static ArrayList<ArrayList<Card>> parseHands(String[] args) {
    ArrayList<ArrayList<Card>> hands = new ArrayList<ArrayList<Card>>();
    for (String arg : args) {
      if (arg.equals("-t")) {
        break;
      }
      ArrayList<Card> hand = cardStringToCardList(arg);
      hands.add(hand);
    }
    return hands;
  }

  private static ArrayList<Card> parseTableCards(String[] args) {
    ArrayList<Card> tableCards = new ArrayList<>();
    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("-t") && i < args.length - 1) {
        tableCards = cardStringToCardList(args[i+1]);
      }
    }
    return tableCards;
  }

  // takes a string like "AHAD" and returns a list with two cards, the Ace of Hearts and Ace of
  // diamonds
  private static ArrayList<Card> cardStringToCardList(String cardString) {
    ArrayList<Card> out = new ArrayList<>();
    for (int i = 0; i < cardString.length(); i+=2) {
      Card c = new Card(cardString.charAt(i), cardString.charAt(i+1));
      out.add(c);
    }
    return out;
  }

  private static String cardsToString(ArrayList<Card> cards) {
    StringBuilder out = new StringBuilder();
    for (Card card : cards) {
      out.append(card.toString());
      out.append(" ");
    }
    return out.toString();
  }
}

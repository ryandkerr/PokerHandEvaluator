package com.ryankerr;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    ArrayList<ArrayList<Card>> hands = getHandsFromUser();
    ArrayList<Card> tableCards = getTableCardsFromUser();
    float[] winProbabilities = HandEvaluator.calculateWinProbability(hands, tableCards);

    System.out.println("\nWin Probabilities:\n");
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

  private static ArrayList<ArrayList<Card>> getHandsFromUser() {
    ArrayList<ArrayList<Card>> hands = new ArrayList<ArrayList<Card>>();
    int handNum = 1;
    while (true) {
      Scanner scanner = new Scanner(System.in);
      System.out.print("Hand " + handNum + ": ");
      String handString = scanner.nextLine();
      if (handString.length() == 0) {
        break;
      }
      ArrayList<Card> hand = cardStringToCardList(handString);
      hands.add(hand);
      handNum++;
    }
    return hands;
  }

  private static ArrayList<Card> getTableCardsFromUser() {
    ArrayList<Card> tableCards = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);
    System.out.print("Table Cards: ");
    return cardStringToCardList(scanner.nextLine());
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

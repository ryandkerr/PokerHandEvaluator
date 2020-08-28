package com.ryankerr;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FiveCardHandTest {

  @Test
  void flushGreaterThanStraight() {
    char[] flushRanks = {'T', '9', '7', '6', '2'};
    char[] flushSuits = {'D', 'D', 'D', 'D', 'D'};
    FiveCardHand flushHand = new FiveCardHand(flushRanks, flushSuits);

    char[] straightRanks = {'K', 'Q', '9', 'T', 'J'};
    char[] straightSuits = {'D', 'D', 'D', 'D', 'S'};
    FiveCardHand straightHand = new FiveCardHand(straightRanks, straightSuits);
    assertTrue(flushHand.compareTo(straightHand) > 0);
    assertTrue(straightHand.compareTo(flushHand) < 0);
  }

  @Test
  void straightAceLow() {
    char[] straightRanks = {'A', '5', '4', '3', '2'};
    char[] straightSuits = {'D', 'D', 'D', 'D', 'S'};
    FiveCardHand straightHand = new FiveCardHand(straightRanks, straightSuits);

    char[] tripsRanks = {'6', '6', '6', '4', '2'};
    char[] tripsSuits = {'D', 'D', 'D', 'D', 'S'};
    FiveCardHand tripsHand = new FiveCardHand(tripsRanks, tripsSuits);

    assertTrue(straightHand.compareTo(tripsHand) > 0);
    assertTrue(tripsHand.compareTo(straightHand) < 0);
  }

  @Test
  void tiedHands() {
    char[] hand1Ranks = {'K', 'K', 'Q', 'Q', 'J'};
    char[] hand1Suits = {'D', 'D', 'D', 'D', 'S'};
    FiveCardHand hand1 = new FiveCardHand(hand1Ranks, hand1Suits);

    char[] hand2Ranks = {'J', 'K', 'K', 'Q', 'Q'};
    char[] hand2Suits = {'C', 'S', 'S', 'S', 'H'};
    FiveCardHand hand2 = new FiveCardHand(hand2Ranks, hand2Suits);

    assertTrue(hand1.compareTo(hand2) == 0);
    assertTrue(hand2.compareTo(hand1) == 0);
  }

  @Test
  void handRatings() {
    // the R is meant to mean "Ranks"
    char[] straightR = {'6', '5', '4', '3', '2'};
    char[] quadsR = {'9', '9', '9', '9', '8'};
    char[] fullR = {'T', 'T', 'T', '7', '7'};
    char[] tripsR = {'T', 'T', 'T', '7', '6'};
    char[] twoPairR = {'J', 'J', '6', '6', '5'};
    char[] pairR = {'Q', 'Q', 'J', '8', '4'};
    char[] highCardR = {'K', 'J', '9', '7', '5'};

    // the S means "Suits"
    char[] flushS = {'D', 'D', 'D', 'D', 'D'};
    char[] nonFlushS = {'C', 'D', 'H', 'S', 'C'};

    FiveCardHand straightFlush = new FiveCardHand(straightR, flushS);
    FiveCardHand quads = new FiveCardHand(quadsR, nonFlushS);
    FiveCardHand fullHouse = new FiveCardHand(fullR, nonFlushS);
    FiveCardHand flush = new FiveCardHand(highCardR, flushS);
    FiveCardHand straight = new FiveCardHand(straightR, nonFlushS);
    FiveCardHand trips = new FiveCardHand(tripsR, nonFlushS);
    FiveCardHand twoPair = new FiveCardHand(twoPairR, nonFlushS);
    FiveCardHand pair = new FiveCardHand(pairR, nonFlushS);
    FiveCardHand highCard = new FiveCardHand(highCardR, nonFlushS);

    assertTrue(straightFlush.compareTo(quads) > 0);
    assertTrue(quads.compareTo(fullHouse) > 0);
    assertTrue(fullHouse.compareTo(flush) > 0);
    assertTrue(flush.compareTo(straight) > 0);
    assertTrue(straight.compareTo(trips) > 0);
    assertTrue(trips.compareTo(twoPair) > 0);
    assertTrue(twoPair.compareTo(pair) > 0);
    assertTrue(pair.compareTo(highCard) > 0);
  }

  @Test
  void getCardsTest() {
    char[] hand1Ranks = {'K', 'K', 'Q', 'Q', 'J'};
    char[] hand1Suits = {'D', 'D', 'D', 'D', 'S'};
    FiveCardHand hand1 = new FiveCardHand(hand1Ranks, hand1Suits);

    hand1.getCards();
  }
}
package com.ryankerr;

import java.util.*;
import java.util.stream.Collectors;

public class FiveCardHand implements Comparable<FiveCardHand> {
  private List<Card> cards;
  private StringBuilder evaluation;

  public FiveCardHand(ArrayList<Card> cards) {
    this.cards = (List<Card>) cards.clone();
    this.evaluation = new StringBuilder("0000000000000000000000");
    this.evaluateHand();
  }

  public FiveCardHand(char[] ranks, char[] suits) {
    assert ranks.length == 5;
    assert suits.length == 5;
    ArrayList<Card> out = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      out.add(new Card(ranks[i], suits[i]));
    }

    this.cards = (List<Card>) out.clone();
    this.evaluation = new StringBuilder("0000000000000000000000");
    this.evaluateHand();
  }

  @Override
  public int compareTo(FiveCardHand hand) {
    return this.evaluation.compareTo(new StringBuilder(hand.getEvaluation()));
  }

  public List<Card> getCards() {
    return this.cards;
  }

  public String getEvaluation() {
    return this.evaluation.toString();
  }

  public String toString() {
    List<String> cardStrings = this.cards.stream()
            .map(x -> x.toString())
            .collect(Collectors.toList());
    return String.join(", ", cardStrings);
  }


  private void evaluateHand() {
    // create a map that counts how many times each rank appears
    List<Integer> ranksSorted = this.cards.stream()
            .map(x -> rankToInt(x.getRank()))
            .collect(Collectors.toList());
    Collections.sort(ranksSorted, Collections.reverseOrder());
    HashMap<Integer, Integer> rankCounts = new HashMap<>();
    for (int rank : ranksSorted) {
      if (rankCounts.containsKey(rank)) {
        int oldCount = rankCounts.get(rank);
        rankCounts.put(rank, oldCount + 1);
      } else {
        rankCounts.put(rank, 1);
      }
    }

    ArrayList<Integer> counts = new ArrayList<>();
    for (int rank : rankCounts.keySet()) {
      counts.add(rankCounts.get(rank));
    }

    Collections.sort(counts, Collections.reverseOrder());
    Set<Integer> ranks = rankCounts.keySet();
    if (counts.get(0) == 4) {  // four-of-a-kind
      // update the evaluation number
      for (int rank : ranks) {
        if (rankCounts.get(rank) == 4) {
          updateEvaluation(1, rank);
        } else {
          updateEvaluation(2, rank);
        }
      }
    } else if (counts.get(0) == 3 && counts.get(1) == 2) {  // full house
      for (int rank : ranks) {
        if (rankCounts.get(rank) == 3) {
          updateEvaluation(3, rank);
        } else {
          updateEvaluation(4, rank);
        }
      }
    } else if (counts.get(0) == 3) {  // three-of-a-kind
      int threeOfAKindRank = 0;
      int higherRank = 0;
      int lowerRank = 0;
      for (int rank : ranks) {
        if (rankCounts.get(rank) == 3) {
          threeOfAKindRank = rank;
        } else if (rank > higherRank) {
          lowerRank = higherRank;
          higherRank = rank;
        } else {
          lowerRank = rank;
        }
      }

      updateEvaluation(7, threeOfAKindRank);
      updateEvaluation(8, higherRank);
      updateEvaluation(9, lowerRank);
    } else if (counts.get(0) == 2 && counts.get(1) == 2) {  // two pair
      int highPairRank = 0;
      int lowPairRank = 0;
      int singleRank = 0;
      for (int rank : ranks) {
        if (rankCounts.get(rank) == 1) {
          singleRank = rank;
        } else if (rank > highPairRank) {
          lowPairRank = highPairRank;
          highPairRank = rank;
        } else {
          lowPairRank = rank;
        }
      }

      updateEvaluation(10, highPairRank);
      updateEvaluation(11, lowPairRank);
      updateEvaluation(12, singleRank);
    } else if (counts.get(0) == 2) {  // pair
      int pairRank = 0;
      ArrayList<Integer> singleRanks = new ArrayList<>();
      for (int rank : ranks) {
        if (rankCounts.get(rank) == 2) {
          pairRank = rank;
        } else {
          singleRanks.add(rank);
        }
      }
      Collections.sort(singleRanks, Collections.reverseOrder());

      updateEvaluation(13, pairRank);
      updateEvaluation(14, singleRanks.get(0));
      updateEvaluation(15, singleRanks.get(1));
      updateEvaluation(16, singleRanks.get(2));
    } else {  // now we know that there are 5 unique ranks. Could be straight, flush, or high card
      HashSet<Integer> uniqueRanks = new HashSet<Integer>(ranksSorted);
      assert uniqueRanks.size() == 5;

      // check for straight
      boolean isStraight = false;
      int straightRank = 0;
      if ((ranksSorted.get(0) - ranksSorted.get(4) == 4)) {
        isStraight = true;
        straightRank = ranksSorted.get(0);
      } else if (ranksSorted.get(0) == 14 && ranksSorted.get(1) == 5) {  // catches Ace-to-5
        // straights
        isStraight = true;
        straightRank = 5;
      }


      // check for flush
      boolean isFlush = false;
      int flushRank = 0;
      List<Character> suits =
              this.cards.stream().map(x -> x.getSuit()).collect(Collectors.toList());
      HashSet<Character> uniqueSuits = new HashSet<Character>(suits);
      if (uniqueSuits.size() == 1) {
        isFlush = true;
        flushRank = ranksSorted.get(0);
      }

      if (isStraight && isFlush) {  // straight flush
        updateEvaluation(0, straightRank);
      } else if (isFlush) {  // flush
        updateEvaluation(5, flushRank);
      } else if (isStraight) {  // straight
        updateEvaluation(6, straightRank);
      }
      // high card. We also do this for flush and straight because flushes need tiebreakers
      updateEvaluation(17, ranksSorted.get(0));
      updateEvaluation(18, ranksSorted.get(1));
      updateEvaluation(19, ranksSorted.get(2));
      updateEvaluation(20, ranksSorted.get(3));
      updateEvaluation(21, ranksSorted.get(4));
    }
  }

  private int rankToInt(char rank) {
    switch (rank) {
      case 'A':
        return 14;
      case 'K':
        return 13;
      case 'Q':
        return 12;
      case 'J':
        return 11;
      case 'T':
        return 10;
      default:
        return Character.getNumericValue(rank);
    }
  }

  private char intToChar(int i) {
    switch (i) {
      case 10:
        return 'A';
      case 11:
        return 'B';
      case 12:
        return 'C';
      case 13:
        return 'D';
      case 14:
        return 'E';
      default:
        return (char) ('0' + i);
    }
  }

  /*
   * updates the internal evaluation attribute
   * The internal evaluation attribute is a base-14 number. Each position indicates whether a
   * hand has a given rating.
   * ex: index 0 is the rank of the high card if the hand is a straight flush
   * index 1 is the rank of the 4-of-a-kind if the hand has one, and index 2 is the rank of the
   * remaining card
   * index 3 is the rank of the 3-of-a-kind if the hand has a full house, and index 4 is the rank of
   * the pair if there is a full house
   */
  private void updateEvaluation(int position, int rank) {
    this.evaluation.setCharAt(position, intToChar(rank));
  }
}

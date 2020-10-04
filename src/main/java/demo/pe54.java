package demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import org.springframework.util.ResourceUtils;

public class pe54 {

	public static void main(String[] args) throws IOException {
		System.out.println("Player One wins " + readAndProcessFile() + " times");
	}

	private static File getFile() throws IOException {
		File file = ResourceUtils.getFile("classpath:data/p054_poker.txt");
		return file;
	}

	private static int readAndProcessFile() throws IOException {
		File file = getFile();
		int counter = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (p1Wins(line, counter)) {
					counter++;
				}
			}
		}
		return counter;
	}

	private static boolean p1Wins(String hand, int row) {
		
		boolean playerOneWins = false;
		String[] allCards = hand.split(" ");

		int[] p1Rank = new int[5];
		int[] p2Rank = new int[5];
		char[] p1Suit = new char[5];
		char[] p2Suit = new char[5];

		for (int i = 0; i < 5; i++) {
			Card card1 = new Card(allCards[i]);
			Card card2 = new Card(allCards[i + 5]);
			p1Rank[i] = card1.getRank();
			p1Suit[i] = card1.getSuit();
			p2Rank[i] = card2.getRank();
			p2Suit[i] = card2.getSuit();
		}
		
		int[] p1FrequencyArray = getFrequencyArray(p1Rank);
		int[] p2FrequencyArray = getFrequencyArray(p2Rank);
		
		int[] p1FrequencyCount = getFrequencyCount(p1FrequencyArray);
		int[] p2FrequencyCount = getFrequencyCount(p2FrequencyArray);
		
		int[] p1Sorted = sortCards(p1FrequencyCount, p1FrequencyArray);
		int[] p2Sorted = sortCards(p2FrequencyCount, p2FrequencyArray);
		
		int p1HandType = getHandType(p1Suit, p1Sorted, p1FrequencyCount);
		int p2HandType = getHandType(p2Suit, p2Sorted, p2FrequencyCount);
		int compareArraysResult = compareArrays(p1Sorted, p2Sorted);
		if (p1HandType > p2HandType) {
			playerOneWins = true;
		} else if (p1HandType < p2HandType) {
			playerOneWins = false;
		} else {
			if (compareArraysResult == 1) {
				playerOneWins = true;
			} else {
				playerOneWins = false;
			}
		}
		
		return playerOneWins;
	}

	private static boolean isFlush(char[] cardSuit) {
		boolean returnResult = true;
		int first = cardSuit[0];
		for (int i = 1; i < 5 && returnResult; i++) {
			if (cardSuit[i] != first)
				returnResult = false;
		}

		return returnResult;
	}
	
	private static boolean isStraight(int[] cardRank, int[] frequencyCount) {
		boolean returnResult = true;
		if (frequencyCount[1] == 5) {
			for (int i = 0;i < cardRank.length - 1; i++) {
				if (!(cardRank[i] == cardRank[i+1] + 1)) {
					returnResult = false;
					break;
				}
			}
		} else {
			returnResult = false;
		}

		return returnResult;
	}

	
	private static int[] getFrequencyArray(int[] cardRank) {
		int[] result = new int[15];

		for (int i = 0; i < 5; i++) {
			result[cardRank[i]]++;
		}

		return result;
	}
	
	private static int[] getFrequencyCount(int[] frequencyArray) {
		int[] result = new int[5];

		for (int i = 0; i < frequencyArray.length; i++) {
			result[frequencyArray[i]]++;
		}

		return result;
	}
	
	private static int[] sortCards(int[] frequencyCount, int[] frequencyArray) {
		int[] result = new int[5];
		int count = 0;
		for (int i = frequencyCount.length -1; i>=0; i--) {
			if (frequencyCount[i] != 0) {
				for (int j = frequencyArray.length-1; j >= 0; j--) {
					if (i == frequencyArray[j]) {
						for (int k = 0; k < i; k++) {
							result[count] = j;
							count++;
						}
					}
				}
			}
		}
		return result;
	}
	
	private static int getHandType(char[] cardSuit, int[] cardSorted, int[] frequencyCount) {
		int result = 0;
		boolean isFlush = isFlush(cardSuit);
		boolean isStright = isStraight(cardSorted, frequencyCount);
		
		if (isFlush && isStright) {
			result = 8;
		} else if (frequencyCount[4] == 1){
			result = 7;
		} else if (frequencyCount[3] == 1 && frequencyCount[2] == 1) {
			result = 6;
		} else if (isFlush) {
			result = 5;
		} else if (isStright) {
			result = 4;
		} else if (frequencyCount[3] == 1){
			result = 3;
		} else if (frequencyCount[2] == 2){
			result = 2;
		} else if (frequencyCount[2] == 1){
			result = 1;
		}
		
		return result;
	}
	
	private static int compareArrays(int[] array1, int[] array2) {
		int result = 0;
		//only makes sense if both arrays are the same length
		for (int i = 0; i < array1.length; i++) {
			if (array1[i] > array2[i]) {
				result = 1;
				break;
			} else if (array1[i] < array2[i]) {
				result = -1;
				break;
			}
		}
		return result;
	}

}

package week5;

import java.util.Scanner;

public class PokerAssignment {
    private static final int HEARTS = 0;
    private static final int DIAMONDS = 1;
    private static final int CLUBS = 2;
    private static final int SPADES = 3;
    private static final int NUM_SUITS = 4;
    private static final int NUM_FACES = 13;
    private static final int JACK = 11;
    private static final int QUEEN = 12;
    private static final int KING = 13;
    private static final int ACE = 14;
    private static final int STRAIGHT_FLUSH = 40;
	private static final int THREE_OF_A_KIND = 30;
	private static final int STRAIGHT = 6;
	private static final int FLUSH = 3;
	private static final int PAIR = 1;
    private static final int HIGH_CARD = 0;

    public static void main(String[] args) {
        Scanner in = new Scanner (System.in);
        int startingChips = 200;
        playPoker(startingChips, in);
        in.close();
    }

    private static void playPoker(int startingChips, Scanner in) {
        String card = getCard();
        
        String playerHand = dealCards();
        String dealerHand = dealCards();
        System.out.println("\nPlayer's hand: " + playerHand);
        System.out.println("Dealer's hand: ????????");

        System.out.println("Total chips: " + startingChips);

        int startingBet = getWager(in);
        int chipsAfterStartingBet = chipsAfterBet(startingChips, startingBet);
        int chipsAllInCheck = chipsAllCheck(chipsAfterStartingBet, startingBet, startingChips, in);
        int chipsAfterAllInCheck = chipsAfterBet(startingChips, chipsAllInCheck);

        System.out.println("\nTotal chips: " + chipsAfterAllInCheck);
        System.out.println("Pot: " + chipsAllInCheck);

        String cardSplit  = splitCardStr(1, playerHand);
        String cardMidSplit = splitCardStr(2, playerHand);
        String cardLastSplit = splitCardStr(3, playerHand);

        
        String cardDealerSplit = splitCardStr(1, dealerHand);   
        String cardDealerMid = splitCardStr(2, dealerHand);
        String cardDealerLast = splitCardStr(3, dealerHand);

        char cardSuitPlayer = getCardSuit(cardSplit);
        char cardSuitPlayerMid = getCardSuit(cardMidSplit);
        char cardSuitPlayerLast = getCardSuit(cardLastSplit);

        char cardSuitDealer = getCardSuit(cardDealerSplit);
        char cardSuitDealerMid = getCardSuit(cardDealerMid);
        char cardSuitDealerLast = getCardSuit(cardDealerLast);

        int cardNumOnePlayer = cardInNum(cardSplit);
        int cardNumTwoPlayer = cardInNum(cardMidSplit);
        int cardNumThreePlayer = cardInNum(cardLastSplit);

        int cardDealerNumOne = cardInNum(cardDealerSplit);
        int cardDealerNumTwo = cardInNum(cardDealerMid);
        int cardDealerNumThree = cardInNum(cardDealerLast);

        int playerComboCheck = checkForCombos(cardNumOnePlayer, cardNumTwoPlayer, cardNumThreePlayer, cardSuitPlayer, cardSuitPlayerMid, cardSuitPlayerLast);
        System.out.println(playerComboCheck);

        int dealerComboCheck = checkForCombos(cardDealerNumOne, cardDealerNumTwo, cardDealerNumThree, cardSuitDealer, cardSuitDealerMid, cardSuitDealerLast);

        String askForFold = checkFold(in);
        String checkFold = checkForFoldPlay(playerComboCheck, askForFold, in);
        String playAgain = checkForPlayAgain(in, chipsAfterAllInCheck, checkFold);
        String askPlayBet = checkPlayBetPair(in, playerComboCheck);
        int checkPairPlayBet = checkPairBetPlay(askPlayBet, in);
        

    }

    private static int checkPairBetPlay(String playBetAns, Scanner in){
        if (playBetAns == "y"){
            return askPlayBetValue(in);
        }else if (playBetAns == "n"){
            return 0;
        }else if (playBetAns == ""){
            return 0;
        }
        return 0;
    }

    private static int chipsAllCheck(int chipsAfterBet, int bet, int chips, Scanner in) {
        if (chipsAfterBet < 0 && bet > chips){
            allInCheck(in);
            return chips;
        }
        return bet;
    }

    private static String allInCheck(Scanner in){
        String temp = "";
        while (!(temp.equalsIgnoreCase("ok") || temp.equalsIgnoreCase("OK"))) {
        System.out.print("You must go all in now(type ok): ");
        temp = in.nextLine().toLowerCase();
    }
    return temp;
    }

    private static String checkPlayBetPair(Scanner in, int combo) {
        if (combo >= PAIR){
            return checkPlayBet(in);
        }
        return "";
    }
    
    private static String checkPlayBet(Scanner in) {
        String playBet = "";
        while (!(playBet.equalsIgnoreCase("y") || playBet.equalsIgnoreCase("n"))) {
        System.out.print("Would you like to place a play bet(you have a pair or higher)(y/n): ");
        playBet = in.nextLine().toLowerCase();
    }
    return playBet;
    }

    private static int askPlayBetValue(Scanner in) {
        int bet = 0;
        System.out.print("Please enter a valid play bet(50-100): ");
        String betInText = in.nextLine();
        boolean validInput = false;
        while(!validInput){
        try {
          bet = Integer.parseInt(betInText);
            if (bet >= 50 && bet <= 100)
                validInput = true;
            else {
                System.out.print("Please enter a valid play bet(50-100): ");
                betInText = in.nextLine();
            }
        } catch (NumberFormatException ex){
            System.out.print("Please enter a valid play bet(50-100): ");
            betInText = in.nextLine();
        }
        }
        return bet;
    }

    private static String checkForPlayAgain(Scanner in, int remainChips, String playAgainAns){
        char temp = playAgainAns.charAt(0);
        if (temp == 'y'){
            checkBankruptcy(in, remainChips);
        }else if (temp == 'n'){
            return "";
        }
        return "";
    }
    
    private static String checkBankruptcy(Scanner in, int chips){
        if (chips <= 0){
            System.out.println("GAME OVER!");
            return "";
        }else 
            System.out.println("\nPot: 0");
            playPoker(chips, in);
            return "";
    }

    private static String checkForFoldPlay(int combo, String foldAnswer, Scanner in) {
        char temp = foldAnswer.charAt(0);
        if (temp == 'y'){
            askForPlayAgain(in);
        }else if (temp == 'n'){
            return foldAnswer;
        }
         return foldAnswer;
    }

    private static String checkFold(Scanner in) {
        String fold = "";
        while (!(fold.equalsIgnoreCase("y") || fold.equalsIgnoreCase("n"))) {
        System.out.print("Would you like to fold(y/n): ");
        fold = in.nextLine().toLowerCase();
    }
    return fold;
    }

    private static String askForPlayAgain(Scanner in) {
        String temp = "";
        while (!(temp.equalsIgnoreCase("y") || temp.equalsIgnoreCase("n"))) {
        System.out.print("Would you like to play again?(y/n): ");
        temp = in.nextLine().toLowerCase();
    }
    return temp;
    }

    private static int checkForCombos(int cardFirstNum, int cardSecondNum, int cardThirdNum, char cardSuitFirst,
            char cardSuitSecond, char cardSuitLast) {
        int max = Math.max(cardFirstNum, Math.max(cardSecondNum, cardThirdNum));
        int min = Math.min(cardFirstNum, Math.min(cardSecondNum, cardThirdNum));
        int mid = (cardFirstNum + cardSecondNum + cardThirdNum) - (max + min);
        if (cardFirstNum == cardSecondNum || cardSecondNum == cardThirdNum || cardThirdNum == cardFirstNum){
            return PAIR;
        }else if (cardSuitFirst == cardSuitSecond && cardSuitSecond == cardSuitLast && cardSuitFirst == cardSuitLast){
            return FLUSH;
        }else if (min + 1 == mid && mid + 1 == max && min + 2 == max){
            return STRAIGHT;
        }else if (cardFirstNum == cardSecondNum && cardSecondNum == cardThirdNum && cardThirdNum == cardFirstNum){
            return THREE_OF_A_KIND;
        }else if (min + 1 == mid && mid + 1 == max && min + 2 == max && cardSuitFirst == cardSuitSecond && cardSuitSecond == cardSuitLast && cardSuitFirst == cardSuitLast){
            return STRAIGHT_FLUSH;
        }else return HIGH_CARD;
    
    }

    private static char getCardSuit(String card) {
        String spaceStr = card.substring(card.length()-1);
        char space = spaceStr.charAt(0);
        String suitWithoutSpace = card.substring(card.length()-2, card.length()-1);
        char suit = suitWithoutSpace.charAt(0);
        if (space == ' '){
            return suit;
        }else return space;
    }

    private static int cardInNum(String card) {
        String temp = card.substring(0,card.length()-1);
        char firstLetter = temp.charAt(0);
        if (firstLetter == 'J') {
            return 11;
        }else if (firstLetter == 'Q'){
            return 12;
        }else if (firstLetter == 'K'){
            return 13;
        }else if (firstLetter == 'A'){
            return 14;
        }else if (firstLetter == '1'){
            return 10;
        }else return Integer.parseInt(temp.substring(0,1));
    }
    
    private static String splitCardStr(int index, String hand) {
        if (index == 1)
            return hand.substring(0, hand.indexOf(" "));
        else {
            String temp = hand.substring(hand.indexOf(" ") + 1);
            if(index == 2)
            return temp.substring(0, temp.indexOf(" "));
            else if (index == 3)
            return temp.substring(temp.indexOf(" ") + 1);
            else {
                throw new IllegalArgumentException("Only have three cards!");
            }
        } 
    }

    private static int chipsAfterBet(int chipVal, int betVal) {
        int remainingChips = chipVal - betVal;
        return remainingChips; 
    }

    private static int getWager(Scanner in) {
        int bet = 0;
        System.out.print("Please enter a valid bet(50-100): ");
        String betInText = in.nextLine();
        boolean validInput = false;
        while(!validInput){
        try {
          bet = Integer.parseInt(betInText);
            if (bet >= 50 && bet <= 100)
                validInput = true;
            else {
                System.out.print("Please enter a valid bet(50-100): ");
                betInText = in.nextLine();
            }
        } catch (NumberFormatException ex){
            System.out.print("Please enter a valid bet(50-100): ");
            betInText = in.nextLine();
        }
        }
        return bet;
    }   
        
        private static String dealCards() {
        String hand = "";
        
        for (int i = 0; i < 3; i++) {
            Boolean hasCard= false;
            while(!hasCard){
                String cards = getCard();
                if (isUnique(hand, cards)){
                    hand += cards + " ";
                    hasCard= true;
                }
            }
        }
        return hand;
    }

    private static String getCard() {
        return getFace() + getSuit();
    }

    private static String getSuit() {
        int suit = (int) (Math.random() * NUM_SUITS);
        if (suit == HEARTS){
            return "H";
        }else if (suit == DIAMONDS){
            return "D";
        }else if (suit == CLUBS){
            return "C";
        }else if (suit == SPADES){
            return "S";
        }else return null;
        
    }

    private static String getFace() {
        int face = (int) (Math.random() * NUM_FACES + 2);
        if (face >= 2 && face <= 10){
            return "" + face;
        }else if (face == JACK){
            return "J";
        }else if (face == QUEEN){
            return "Q";
        }else if (face == KING){
            return "K";
        }else if (face == ACE){
            return "A";
        }else return null;
    }

    public static boolean isUnique(String hand, String card) {	
		return hand.indexOf(card) == -1;
    }

}

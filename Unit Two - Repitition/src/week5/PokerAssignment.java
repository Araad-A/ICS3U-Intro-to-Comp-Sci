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
        
        System.out.println("\nStarting chips: " + startingChips);

        int startingBet = getWager(in);
        int chipsAfterStartingBet = chipsAfterBet(startingChips, startingBet);
        int chipsAllInCheck = chipsAllCheck(chipsAfterStartingBet, startingBet, startingChips, in);
        int chipsAfterAllInCheck = chipsAfterBet(startingChips, chipsAllInCheck);

        String playWager = checkPairBet(in);
        int askPairWagerValue = checkPairBetPlay(playWager, in);

        int chipsAfterPair = chipsAfterBet(chipsAfterAllInCheck, askPairWagerValue);
        int chipsAllInPairBet = chipsAllCheck(chipsAfterPair, askPairWagerValue, chipsAfterAllInCheck, in);
        int chipsAllInPairBetCheck = chipsAfterBet(chipsAfterAllInCheck, chipsAllInPairBet);

        int pairBetPot = getPotVal(startingBet, askPairWagerValue);
    
        String writePairBetPotAndChips = displayPotAndChips(chipsAllInPairBetCheck, pairBetPot);
        System.out.println(writePairBetPotAndChips);

        String playerHand = dealCards();
        String dealerHand = dealCards();
        String dealerHandMystery = "??????????";
        String foldBeginningAnswer = "";
        String beginningCards = showCards(dealerHandMystery, playerHand, foldBeginningAnswer, "");
        System.out.println(beginningCards);

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
        System.out.println();

        int dealerComboCheck = checkForCombos(cardDealerNumOne, cardDealerNumTwo, cardDealerNumThree, cardSuitDealer, cardSuitDealerMid, cardSuitDealerLast);

        String askForFold = checkFold(in);
        String checkFold = checkForFoldPlay(playerComboCheck, askForFold, in);
        String playAgain = checkForPlayAgain(in, chipsAfterAllInCheck, checkFold);

        int askPlayBetWager = getPlayWager(startingBet, in);

        int chipsAfterPlayBet = chipsAfterBet(chipsAfterAllInCheck, askPlayBetWager);
        int chipsAllInPlayBet = chipsAllCheck(chipsAfterPlayBet, askPlayBetWager, chipsAfterAllInCheck, in);
        int chipsAllInPlayBetCheck = chipsAfterBet(chipsAfterAllInCheck, chipsAllInPlayBet);

        int playBetPotVal = getPotVal(chipsAllInPlayBetCheck, askPlayBetWager);
        String writePlayBetPotAndChips = displayPotAndChips(chipsAllInPairBetCheck, playBetPotVal);

        String displayFinalCards = showCards(dealerHand, playerHand, checkFold, writePlayBetPotAndChips);
        System.out.println(displayFinalCards);

        String checkForWinner = getWinner(cardDealerNumOne, cardDealerNumTwo, cardDealerNumThree, cardNumOnePlayer, cardNumTwoPlayer, cardNumThreePlayer, playerComboCheck, dealerComboCheck, playBetPotVal, askPlayBetWager, startingBet, askPairWagerValue); 
        System.out.println(checkForWinner);

        String checkPlayAgain = askForPlayAgain(in);
        String playAgainEnd = checkForPlayAgain(in, chipsAllInPlayBetCheck, checkPlayAgain);
    }

    private static int getPlayWager(int startingBet, Scanner in) {
        int bet = 0;
        System.out.print("Please enter a valid play bet(must be the same as starting bet): ");
        String betInText = in.nextLine();
        boolean validInput = false;
        while(!validInput){
        try {
          bet = Integer.parseInt(betInText);
            if (bet == startingBet)
                validInput = true;
            else {
                System.out.print("Please enter a valid play bet(must be the same as starting bet): ");
                betInText = in.nextLine();
            }
        } catch (NumberFormatException ex){
            System.out.print("Please enter a valid play bet(must be the same as starting bet): ");
            betInText = in.nextLine();
        }
        }
        return bet;
    }

    private static String getWinner(int dealerCardNum, int dealerCardNumTwo, int dealerCardNumThree, int playerCardNum,
            int playerCardNumTwo, int playerCardNumThree, int playerCombo, int dealerCombo, int pot, int playBet, int startingBet, int pairBet) {
                int playerMax = (Math.max(playerCardNum, Math.max(playerCardNumTwo, playerCardNumThree)));
                int dealerMax = (Math.max(dealerCardNum, Math.max(dealerCardNumTwo, dealerCardNumThree)));
                if (playerCombo > dealerCombo){
            System.out.println("\nYOU WIN!");
            return "";
        }else if (playerCombo < dealerCombo){
            System.out.println("\nYOU LOSE!");
            return "";
        }else if (playerCombo == dealerCombo && playerMax > dealerMax){
            System.out.println("\nYOU WIN!");
            return "";
        }else if (playerCombo == dealerCombo && dealerMax > playerMax){
            System.out.println("\nYOU LOSE!");
            return "";
        }else if (dealerCombo == 0 && dealerMax <= 11){
            System.out.println("\nYOU WILL LOSE YOUR STARTING BET BUT KEEP YOUR PLAY BET!");
            return "";
        }else return "";

        }
    

    private static String showCards(String dealerHand, String hand, String foldAns, String displayPotAndChips) {
        if (foldAns == ""){
            System.out.println(displayPotAndChips);
            String writeDealerHand = ("\nDealer's hand: " + dealerHand);
            String writePlayerHand = ("\nPlayer's hand: " + hand);
            return writePlayerHand + writeDealerHand;
        }else return "";
    }

    private static int getPotVal(int startingBet, int playBet){
        return startingBet + playBet;
    }

    private static String displayPotAndChips(int chips, int pot){
        String displayPot = ("\nPot: " + pot);
        String displayChips = ("\nTotal chips: " + chips);
        return displayChips + displayPot;
    }

    private static int checkPairBetPlay(String pairBetAns, Scanner in){
        if (pairBetAns == ""){
            return 0;
        }
        char temp = pairBetAns.charAt(0);
        if (temp == 'y'){
            return askPairBetValue(in);
        }else return 0;
    }

    private static int chipsAllCheck(int chipsAfterBet, int bet, int chips, Scanner in) {
        if (chipsAfterBet < 0 && bet > chips){
            allInCheck(in);
            return chips;
        }else return bet;
    }

    private static String allInCheck(Scanner in){
        String temp = "";
        while (!(temp.equalsIgnoreCase("ok") || temp.equalsIgnoreCase("OK"))) {
        System.out.print("You must go all in now(type ok): ");
        temp = in.nextLine().toLowerCase();
    }
    return temp;
    }
    
    private static String checkPairBet(Scanner in) {
        String pairBet = "";
        while (!(pairBet.equalsIgnoreCase("y") || pairBet.equalsIgnoreCase("n"))) {
        System.out.print("Would you like to place a pair plus bet(you are betting that you have a pair or higher)(y/n): ");
        pairBet = in.nextLine().toLowerCase();
    }
    return pairBet;
    }

    private static int askPairBetValue(Scanner in) {
        int bet = 0;
        System.out.print("Please enter a valid pair plus bet(50-100): ");
        String betInText = in.nextLine();
        boolean validInput = false;
        while(!validInput){
        try {
          bet = Integer.parseInt(betInText);
            if (bet >= 50 && bet <= 100)
                validInput = true;
            else {
                System.out.print("Please enter a valid pair plus bet(50-100): ");
                betInText = in.nextLine();
            }
        } catch (NumberFormatException ex){
            System.out.print("Please enter a valid pair plus bet(50-100): ");
            betInText = in.nextLine();
        }
        }
        return bet;
    }

    private static String checkForPlayAgain(Scanner in, int remainChips, String playAgainAns){
        if (playAgainAns == ""){
            return "";
        }
        char temp = playAgainAns.charAt(0);
        if (temp == 'y'){
            checkBankruptcy(in, remainChips);
        }else if (temp == 'n'){
            System.exit(0);
            return "";
        }return playAgainAns;
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
            return askForPlayAgain(in);
        }else if (temp == 'n'){
            return "";
        }else return "";
    }

    private static String checkFold(Scanner in) {
        String fold = "";
        while (!(fold.equalsIgnoreCase("y") || fold.equalsIgnoreCase("n"))) {
        System.out.print("Would you like to fold(if you continue, you must place another bet)(y/n): ");
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
        System.out.print("\nPlease enter a valid bet(50-100): ");
        String betInText = in.nextLine();
        boolean validInput = false;
        while(!validInput){
        try {
          bet = Integer.parseInt(betInText);
            if (bet >= 50 && bet <= 100)
                validInput = true;
            else {
                System.out.print("\nPlease enter a valid bet(50-100): ");
                betInText = in.nextLine();
            }
        } catch (NumberFormatException ex){
            System.out.print("\nPlease enter a valid bet(50-100): ");
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

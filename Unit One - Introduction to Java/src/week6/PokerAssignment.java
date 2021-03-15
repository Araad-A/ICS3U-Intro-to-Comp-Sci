package week6;

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
        
        int chipsAllInStartingBet = chipsAllInCheck(startingBet, startingChips, in);
        int chipsAfterStartingBet = chipsAfterBet(startingChips, chipsAllInStartingBet);

        String playWager = checkPairBet(in);
        int askPairWagerValue = checkPairBetPlay(playWager, in);

        int chipsAllInPairBet = chipsAllInCheck(askPairWagerValue, chipsAfterStartingBet, in);
        int chipsAfterPairBet = chipsAfterBet(chipsAfterStartingBet, chipsAllInPairBet);

        int pairBetPot = getPotVal(chipsAllInStartingBet, chipsAllInPairBet);
    
        String writePairBetPotAndChips = displayPotAndChips(chipsAfterPairBet, pairBetPot);
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
        String checkFold = checkForFoldPlay(askForFold, in);
        String playAgain = checkForPlayAgain(in, chipsAfterPairBet, checkFold);

        int askPlayBetWager = getPlayWager(startingBet, in);

        int chipsAllInPlayBet = chipsAllInCheck(askPlayBetWager, chipsAfterPairBet, in);
        int chipsAfterPlayBet = chipsAfterBet(chipsAfterPairBet, chipsAllInPlayBet);

        int playBetPotVal = getPotVal(pairBetPot, chipsAllInPlayBet);
        String writePlayBetPotAndChips = displayPotAndChips(chipsAfterPlayBet, playBetPotVal);

        String displayFinalCards = showCards(dealerHand, playerHand, checkFold, writePlayBetPotAndChips);
        System.out.println();
        System.out.println(displayFinalCards);

        int checkWinner = getWinner(cardDealerNumOne, cardDealerNumTwo, cardDealerNumThree, cardNumOnePlayer, cardNumTwoPlayer, 
        cardNumThreePlayer, playerComboCheck, dealerComboCheck, playBetPotVal, chipsAllInPlayBet, chipsAllInStartingBet, chipsAllInPairBet,
        chipsAfterPlayBet);

        String writeFinalPotAndChips = displayPotAndChips(checkWinner, 0);
        System.out.println(writeFinalPotAndChips);

        String checkPlayAgain = askForPlayAgain(in);
        String playAgainEnd = checkForPlayAgain(in, checkWinner, checkPlayAgain);
    }

    /**
     * @param dealerCardNum the number from the dealer's first card. 
     * @param dealerCardNumTwo the number from the dealer's second card. 
     * @param dealerCardNumThree the number from the dealer's third card. 
     * @param playerCardNum the number from the player's first card. 
     * @param playerCardNumTwo the number from the player's second card. 
     * @param playerCardNumThree the number from the player's third card.
     * @param playerCombo the combination of the player's matches with their own cards. 
     * @param dealerCombo the combination of the dealer's matches with their own cards. 
     * @param pot the total value of all the bets placed by the user. 
     * @param playBet the play bet that was inputted by the user. 
     * @param startingBet the starting bet that was inputted by the user. 
     * @param pairBet the pair plus bet that was inputted by the user. 
     * @param chips the total number of chips that the user currently has. 
     * @return the chips the user won or the chips that the user lost.
     */
    private static int getWinner(int dealerCardNum, int dealerCardNumTwo, int dealerCardNumThree, int playerCardNum,
            int playerCardNumTwo, int playerCardNumThree, int playerCombo, int dealerCombo, int pot, int playBet, int startingBet, int pairBet, int chips) {
            if (playerCombo < PAIR){
                pairBet = 0;
            }else if (playerCombo >= PAIR){
                pairBet = pairBet * playerCombo;
            }
            int playerMax = (Math.max(playerCardNum, Math.max(playerCardNumTwo, playerCardNumThree)));
            int dealerMax = (Math.max(dealerCardNum, Math.max(dealerCardNumTwo, dealerCardNumThree)));
            if (dealerMax <= JACK && dealerMax > playerMax && dealerCombo == HIGH_CARD && playerCombo == HIGH_CARD){
                System.out.println("YOU LOST!");
                return chips + playBet + pairBet;
            }else if (playerCombo > dealerCombo){
                System.out.println("YOU WON!");
                return chips + startingBet + playBet + pairBet;
            }else if (playerCombo == dealerCombo && playerMax > dealerMax){
                System.out.println("YOU WON!");
                return chips + startingBet + playBet + pairBet;
            }else if (dealerCombo > playerCombo){
                System.out.println("YOU LOST!");
                return chips + pairBet;
            }else if (dealerCombo == playerCombo && dealerMax > playerMax){
                System.out.println("YOU LOST!");
                return chips + pairBet;
            }else if (dealerCombo == playerCombo && dealerMax == playerMax){
                System.out.println("YOU TIED!");
                return chips + playBet + pairBet;
            }else return 0;
        }
    
    /**
     * @param cardFirstNum the first number of the card in question. 
     * @param cardSecondNum the second number of the card in question. 
     * @param cardThirdNum the third number of the card in question. 
     * @param cardSuitFirst the first suit of the card in question.
     * @param cardSuitSecond the second suit of the card in question.
     * @param cardSuitLast the third suit of the card in question.
     * @return the number identifying how each card relates to each other (high card, pair, etc.).
     */
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

    /**
     * @param dealerHand the hand of the dealer. 
     * @param hand the hand of the user. 
     * @param foldAns the answer to the question of would you like to fold. 
     * @param displayPotAndChips the display of the pot and the chips of the user. 
     * @return the hands for the dealer and the player, as well as the display for the pot and chips.
     */
    private static String showCards(String dealerHand, String hand, String foldAns, String displayPotAndChips) {
        if (foldAns == ""){
            System.out.println(displayPotAndChips);
            String writeDealerHand = ("\nDealer's hand: " + dealerHand);
            String writePlayerHand = ("Player's hand: " + hand);
            return writePlayerHand + writeDealerHand;
        }else return "";
    }

    /**
     * @param startingBet bet inputted by the user at the beginning of the game. 
     * @param playBet the play bet that was inputted by the user near the end of the game.
     * @return the total pot value. 
     */
    private static int getPotVal(int startingBet, int playBet){
        return startingBet + playBet;
    }

    /**
     * @param chips the number of chips that the user possesses. 
     * @param pot the value of the pot (cumulative of bets).
     * @return the display for the pot and the chips. 
     */

    /**
     * @param pairBetAns the answer from the function of the pair bet question.
     * @param in. the input from the user in the terminal.
     * @return if yes, a function that asks for the value of the pair bet. if no, nothing. 
     */
    private static int checkPairBetPlay(String pairBetAns, Scanner in){
        if (pairBetAns == ""){
            return 0;
        }
        char temp = pairBetAns.charAt(0);
        if (temp == 'y'){
            return askPairBetValue(in);
        }else return 0;
    }

    /**
     * @param chipsAfterBet the chips after the bet of the user. 
     * @param bet the value of the user's bet.
     * @param chips the value of chips that the user currently possesses. 
     * @param in. the input from the user in the terminal
     * @return if chips are less than or equal to zero, it forces player to go all in. if it isn't, nothing happens.
     */
    private static int chipsAllInCheck(int bet, int chips, Scanner in) {
        if (chips <= 0 && bet >= chips){
            allInRequest(in);
            return chips;
        }else return bet;
    }

    /**
     * @param in. the input from the user in the terminal.
     * @return the one answer from the forceful all in play.
     */
    private static String allInRequest(Scanner in){
        String temp = "";
        while (!(temp.equalsIgnoreCase("ok") || temp.equalsIgnoreCase("OK"))) {
        System.out.print("You must go all in now(type ok): ");
        temp = in.nextLine().toLowerCase();
    }
    return temp;
    }
    
    private static String displayPotAndChips(int chips, int pot){
        String displayPot = ("\nPot: " + pot);
        String displayChips = ("\nTotal chips: " + chips);
        return displayChips + displayPot;
    }

    /**
     * @param in. the input from the user in the terminal.
     * @return the value of the starting bet of the user.  
     */
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

    /**
     * @param in. the input from the user in the terminal.
     * @return the user's answer to the question of if they would like to place a pair plus bet
     */
    private static String checkPairBet(Scanner in) {
        String pairBet = "";
        while (!(pairBet.equalsIgnoreCase("y") || pairBet.equalsIgnoreCase("n"))) {
        System.out.print("Would you like to place a pair plus bet(you are betting that you have a pair or higher)(y/n): ");
        pairBet = in.nextLine().toLowerCase();
    }
    return pairBet;
    }

    /**
     * @param in. the input from the user in the terminal.
     * @return the value of the bet that was inputted by the user as part of their pair plus bet. 
     */
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

    /**
     * @param startingBet the bet that has been inputted by the user in the beginning of the game.
     * @param in. the input from the user in the terminal.
     * @return the value of the bet that was inputted by the user as part of their play bet.
     */
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

    /**
     * @param foldAns the answer inputted by the user to the question of would they like to fold.
     * @param in. the input from the user in the terminal.
     * @return if yes, calls another function to ask if they would like to play again. 
     * if no, nothing happens
     */
    private static String checkForFoldPlay(String foldAns, Scanner in) {
        char temp = foldAns.charAt(0);
        if (temp == 'y'){
            return askForPlayAgain(in);
        }else if (temp == 'n'){
            return "";
        }else return "";
    }

    /**
     * @param in. the input from the user in the terminal.
     * @return the user's input in string format.
     */
    private static String checkFold(Scanner in) {
        String fold = "";
        while (!(fold.equalsIgnoreCase("y") || fold.equalsIgnoreCase("n"))) {
        System.out.print("Would you like to fold(if you continue, you must place another bet)(y/n): ");
        fold = in.nextLine().toLowerCase();
    }
    return fold;
    }

    /**
     * @param in. the input from the user in the terminal.
     * @return the user's input in string format.
     */
    private static String askForPlayAgain(Scanner in) {
        String temp = "";
        while (!(temp.equalsIgnoreCase("y") || temp.equalsIgnoreCase("n"))) {
        System.out.print("Would you like to play again?(y/n): ");
        temp = in.nextLine().toLowerCase();
    }
    return temp;
    }

    /**
     * @param in. the input from the user in the terminal.
     * @param remainChips the amount of chips remaining after any bets.
     * @param playAgainAns the answer from the question of would you like to play again.
     * @return if yes, calls another function to check if user can play again and allows user to play again. 
     * if no, terminal closes and game ends.
     */
    private static String checkForPlayAgain(Scanner in, int remainChips, String playAgainAns){
        if (playAgainAns == ""){
            return "";
        }
        char temp = playAgainAns.charAt(0);
        if (temp == 'y'){
            checkBankruptcy(in, remainChips);
        }else if (temp == 'n'){
            System.exit(0);
        }return playAgainAns;
    }
    
    /**
     * @param in. the input from the user in the terminal.
     * @param chips the amount of chips that remian after any bets. 
     * @return if you have less chips than zero, the game will end. if not, the game will start again. 
     */
    private static String checkBankruptcy(Scanner in, int chips){
        if (chips <= 0){
            System.out.println("YOU ARE BROKE AND IN DEBT!");
            System.exit(0);
        }else 
            System.out.println("\nPot: 0");
            playPoker(chips, in);
            return "";
    }


    /**
     * @param card the specific card with only one number/letter and one suit.
     * @return the suit of the card in character format to be used for comparisons. 
     */
    private static char getCardSuit(String card) {
        String spaceStr = card.substring(card.length()-1);
        char space = spaceStr.charAt(0);
        String suitWithoutSpace = card.substring(card.length()-2, card.length()-1);
        char suit = suitWithoutSpace.charAt(0);
        if (space == ' '){
            return suit;
        }else return space;
    }

    /**
     * @param card the specific card with only one number/letter and one suit.
     * @return the number of the card in integer format to be used for comparisons. 
     */    
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
    

    /**
     * @param index which specific card in the series of the three cards.
     * @param hand the overall hand of the user/dealer.
     * @return only one individual card with a suit and a number.
     */
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

    /**
     * @param chipVal the value of the chips.
     * @param betVal the value of the bet inputted by the user.
     * @return the remaining chips after the bet has been inputted.
     */
    private static int chipsAfterBet(int chipVal, int betVal) {
        int remainingChips = chipVal - betVal;
        return remainingChips; 
    }  
        
    /**
     * @param nothing. just deals out three cards with the functions further below. 
     * @return three cards with a suit and a number. 
     */
    private static String dealCards() {
        String hand = "";
        for (int i = 0; i < 3; i++) {
            Boolean hasCard= false;
            while(!hasCard){
                String cards = getCard();
                if (isUnique(hand, cards)){
                    hand += cards + " ";
                    hasCard = true;
                }
            }
        }
        return hand;
    }

    public static boolean isUnique(String hand, String card) {	
		return hand.indexOf(card) == -1;
    }

    /**
     * @param nothing. combines the two functions to pick up cards.
     * @return a full card with a suit and a number. 
     */
    private static String getCard() {
        return getFace() + getSuit();
    }

    /**
     * @param nothing. uses a random number simulate picking up cards.
     * @return the suit of the card using a number that corresponds for each suit. 
     */
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

    /**
     * @param nothing. creates a random number to simulate a deck of cards.
     * @return the number of the cards that the player and dealer will use.
     */
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

}
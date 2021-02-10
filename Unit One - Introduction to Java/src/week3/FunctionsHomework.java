package week3;

public class FunctionsHomework {
    public static void main(String[] args) {
        
    double questionOne = getTotalPrice(985, 5.5);
    System.out.println(questionOne);

    double questionTwoOne = getPerimeter(4.5,2.3);
    double questionTwoTwo = getArea(4.5,2.3);
    System.out.println(questionTwoOne);
    System.out.println(questionTwoTwo);

    int questionThree = getTotalSecYear(1,60,60,24,365);
    System.out.println(questionThree);

    double questionFour = getSpeed(300000000,questionThree);
    System.out.println(questionFour);

    double questionFive = getWinPercentage(110,154);
    System.out.println(questionFive);

    int questionSix = getMomentum(10,12);
    System.out.println(questionSix);

    double questionSeven = findCelsius(98);
    System.out.println(questionSeven);

    double questionEight = sqrt(25);
    double questionEightTwo = square(25);
    System.out.println(questionEight);
    System.out.println(questionEightTwo);
    
    

}

    private static double getTotalPrice(int price, double salesTax) {
        return price + price * salesTax / 100;
    }

    private static double getPerimeter(double length, double width) {
        return (2*length) + (2*width);
    }

    private static double getArea(double length, double width) {
        double area = (length*width);
        return area;
    }

    private static int getTotalSecYear(int numSec, int numSecMin, int numMinHour, int numHourDay, int numDayYear) {
        return numSec*numSecMin*numMinHour*numHourDay*numDayYear;
    }

    private static double getSpeed(double speed, double year) {
		return speed*year;
	}

    private static double getWinPercentage(double wins, double totalGames) {
        return (wins/totalGames)*100;
    }

    private static int getMomentum(int objectWeight, int objectSpeed) {
        return objectWeight*objectSpeed;
    }

    private static double findCelsius(double farenheit) {
        return (farenheit-32)*5/9;
    }

    private static double sqrt(int positiveNumber) {
        return Math.sqrt(positiveNumber);
    }
    private static double square(int positiveNumber) {
        return Math.pow(positiveNumber, 2);
    }
}

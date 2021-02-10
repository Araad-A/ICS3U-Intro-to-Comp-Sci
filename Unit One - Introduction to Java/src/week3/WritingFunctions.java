package week3;

public class WritingFunctions {
    public static void main(String[] args) {
    //The purpose of a function is to encapsulate or to abstract logic and place it into a method. /function  
    //A function/method performs a specific task (and can return a value(not always))
    int x = 7;
    int y = 8;
    
    int z = sum(x,y);

    addOne(x);
    double x1, y1, x2, y2;
    x1 = 4;
    x2 = 7;
    y1 = -2;
    y2 = 6;
   double slope = getSlope(x1,y1,x2,y2);

    System.out.println(x);
    }

    private static double getSlope(double x1, double y1, double x2, double y2) {
        double deltaX = x2-x1;
        double deltaY = y2-y1;

        return deltaY/deltaX;
    }

    private static void addOne(int num1) {
    
    num1 = num1++;    //++ adds 1 -- subtracts 1
    //num1 is a copy of x and any changes to num1 does not affect x because parameters are primitive 
    }

    private static int sum(int num1, int num2) {
        int result = num1 + num2;

        return result;
    }

}

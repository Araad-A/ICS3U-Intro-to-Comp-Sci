package week1;

/**
 * HomeworkDayThree
 */
public class HomeworkDayThree {

    public static void main(String[] args) {
        
        //Code underneath calculates area of a circle. 

        double r = 5;
        double areaOfCircle = (Math.pow(r, 2)*Math.PI);

        System.out.println("\n");
        System.out.println(areaOfCircle);
    
        //Code underneath calculates volume of a sphere. 

        double volumeOfSphere = (Math.pow(r, 3)*4/3*Math.PI);
        
        System.out.println(volumeOfSphere);
    
        //Code underneath calculates equation y=ax^2+bx+c
    
        int a = 5;
        int b = 10;
        int c = 20;
        int x = 2;

        double y = (a*Math.pow(x, 2)+b*x+c);

        System.out.println(y);
        
        // Code underneath calculates slope of a given line

        int y2 = 15;
        int y1 = 10; 
        int x2 = 3;
        int x1 = 2;

        int slope = ((y2-y1)/(x2-x1));

        System.out.println(slope);
    
        // Code underneath calculates amount of money one would receive given the price and quantity of coins. 
        // Pennies = p, nickles = n, dimes = d, quarters = q, loonies = l, toonies = t. 

        double p = 0.01;
        double n = 0.05;
        double d = 0.1;
        double q = 0.25;
        int l = 1;
        int t = 2;
        int numP = 24;
        int numN = 4;
        int numD = 9;
        int numQ = 2;
        int numL = 8; 
        int numT = 3;

        double totalMoney = (p*numP+n*numN+d*numD+q*numQ+l*numL+t+numT);

        System.out.println("Total Value : "+totalMoney);

        //Code underneath calculates quadratic formula.

        double a1 = 5;
        double b1 = 7;
        double c1 = -57;
        
        double xPlus = ((-b1 + Math.sqrt(Math.pow(b1, 2)-(4*a1*c1)))/(2*a1));
        double xMinus = ((-b1 - Math.sqrt(Math.pow(b1, 2)-(4*a1*c1)))/(2*a1));

        System.out.println(xPlus + "  and  " + xMinus);
    }

}
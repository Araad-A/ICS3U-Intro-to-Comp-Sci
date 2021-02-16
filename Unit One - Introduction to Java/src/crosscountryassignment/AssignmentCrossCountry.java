package crosscountryassignment; 

import java.util.Scanner;

public class AssignmentCrossCountry {
    public static void main(String[] args) {
        Scanner in = new Scanner (System.in);

        processRunner(in);
        processRunner(in);
        processRunner(in);

        in.close();

    }

    private static void processRunner(Scanner in) {
        System.out.print("\nPlease enter runner's first name: ");
        String runnerFirstName = in.nextLine();
        
        System.out.print("Please enter runner's last name: ");
        String runnerLastName = in.nextLine();

        System.out.print("Please enter " + runnerFirstName + "'s time to complete the first mile of the race: ");
        String mileOneTime = in.nextLine();

        System.out.print("Please enter " + runnerFirstName + "'s time to the end of the second mile: ");
        String mileTwoTime = in.nextLine();

        System.out.print("Please enter " + runnerFirstName + "'s time to complete the 5 kilometer race: ");
        String raceCompletionTime = in.nextLine();    
        
        double timeInSecondsOne = convertStringToDouble(mileOneTime);
        
        double timeInSecondsTwo = convertStringToDouble(mileTwoTime);

        double timeInSecondsThree = convertStringToDouble(raceCompletionTime);

        double splitTwoSeconds = getSplitTimeSeconds(timeInSecondsOne, timeInSecondsTwo);
        
        double splitThreeSeconds = getSplitTimeSeconds(timeInSecondsTwo, timeInSecondsThree);
        
        String splitOneFinal = mileOneTime;
        
        String splitTwoFinal = getSplitInTimeString(splitTwoSeconds);
        
        String splitThreeFinal = getSplitInTimeString(splitThreeSeconds);

        System.out.print("\n\nCongratulations" + " " + runnerFirstName + " " + runnerLastName + "" + ", this is your summary:");

        System.out.println("\nYour split one time is: " + splitOneFinal);
        
        System.out.println("Your split two time is: " + splitTwoFinal);
        
        System.out.println("Your split three time is: " + splitThreeFinal);

    }
        /**
         * string to double to use for calculations.
        * @param mileTimes string inputed by the user in mm:ss.sss format.
        * @return double number in seconds and milliseconds (calculations).
        */

        private static double convertStringToDouble(String mileTimes) {
            int colon = mileTimes.indexOf(":");
            int minutesAsSeconds = Integer.parseInt(mileTimes.substring(0,colon)) * 60;
            double seconds = Double.parseDouble(mileTimes.substring(colon+1));
            return minutesAsSeconds + seconds;
        }

        /**
        * @param splitStart a number in seconds that indicates a starting time for a split.
        * @param splitFinish a number in seconds that indicates a finishing time for a split.
        * @return the second count between the start of the split and the end of the split. 
        */
        
        private static double getSplitTimeSeconds(double splitStart, double splitFinish) {
            return splitFinish - splitStart;
        }

        /**
        * @param splitTime the time in seconds between two certian splits
        * @return a string in the format of mm:ss.sss that is then displayed
        */
        
        private static String getSplitInTimeString(double splitTime){
            int splitMinutes = (int)(splitTime / 60);
            double splitSecondsMilli = (splitTime - (splitMinutes * 60));
            return  String.format("%d:%06.3f",splitMinutes,splitSecondsMilli);
        }
}
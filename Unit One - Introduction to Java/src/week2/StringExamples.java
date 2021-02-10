package week2;

public class StringExamples {
    public static void main(String[] args) {
    exampleOne();
    exampleThree();

}
    private static void exampleThree() {
        String s1 = new String ("Steve");
        String s2 = new String ("Steve");

        System.out.println(s1 == s2);
    }
    private static void exampleOne() {
    String simpleText = "This is a String.";

    System.out.println(simpleText.compareTo("bruh"));
    }

}

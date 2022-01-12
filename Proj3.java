import java.util.Scanner;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileOutputStream;

/**Lavinia Kong.
 * Project 3
 * 500.112 Gateway Computing Java
 * Fall 2021
 * This program prompts the user input for a movie review file. then displays t
 * he menu, giving the user the option to 1. quit, 2. input a score and calcula
 * te its average from the file. 3. enter a review line, calculate and average 
 * the score of each of the words from the review line. 4. enter a new detla va
 * lue that determine whether a review has a positive or negative sentiment
 */

public class Proj3 {
   public static void main(String[] args) throws IOException {
      final String GETFILE = "enter input filename: ";
      final String GETCHOICE = "enter choice by number --> ";
      final String INVALID = "invalid option, try again";
      //load movie review file
      
      System.out.print(GETFILE);
      Scanner keyboard = new Scanner(System.in);
      String fileName = keyboard.next();     
      System.out.println();
      //try to open file
      FileInputStream fbStream = new FileInputStream(fileName);
      Scanner inFS = new Scanner(fbStream);
      String[] reviews = new String[inFS.nextInt() + 1]; 
      double delta = 0;
      
      int j = 0;
      while (inFS.hasNext()) {
         reviews[j] = inFS.nextLine() + ", ";
         j++;
      }
      
      int userChoice = 0;
      
      while (userChoice != 1) {
         displayMenu();
         System.out.print(GETCHOICE);
         userChoice = keyboard.nextInt();
         
         if (userChoice == 1) {
            option1();
         } else if (userChoice == 2) {
            option2(reviews, keyboard);
         } else if (userChoice == 3) {
            option3(reviews, delta, keyboard); 
         } else if (userChoice == 4) {
            delta = option4(keyboard);    
         }
         else {
            System.out.println("invalid option, try again");
         }       
      }
      fbStream.close();
   }
      
   /**
   * prompts user to input a word to calculate the average for. 
   * @param reviews string array containing all reviews 
   * @param word scanner object that scans for user inputs from keyboard
   */
   public static void option2(String[] reviews, Scanner word) 
      throws IOException {
      
      final String GETWORD = "enter word to score --> ";
      System.out.print(GETWORD);
      String userWord = word.next().toLowerCase();
      int count = 0;
      double totalScore = 0;
      double average = 0;
      
      String[] newFile = new String[reviews.length];
      
      FileOutputStream fileStream = new FileOutputStream(userWord + ".txt");
      PrintWriter output = new PrintWriter(fileStream);
      
      String oneReview = "";
      for (int i = 0; i < reviews.length; i++) {
         //get one line in reviews
         oneReview = reviews[i].toLowerCase();
         if (oneReview.indexOf(userWord) >= 0) {
            count++;
            totalScore += Integer.parseInt(oneReview.substring(0, 1));
            output.println(oneReview);
         }
      }
      
      if (count != 0) {
         average = totalScore / count;
      } 
      System.out.println(userWord + " appears in " + count + " reviews");
      
      if (count >= 0) {
         System.out.printf("average score for those reviews is %.5f\n", 
                           average);
      }
      output.flush();
      output.close();  
      System.out.println();
   }  
   
   /**
   * prompts user for a review line; return the average score of all the words.
   * also give thumbs up and down depending on sentiment 
   * @param reviews string array containing all reviews 
   * @param delta defaults to 0, unless user changes it
   * @param keyboard scanner object that scans for user inputs from keyboard
   */
   public static void option3(String[] reviews, double delta, 
                               Scanner keyboard) {
      final String NOCOMPUTE = "review score can't be computed";
      final String DOWN = "thumbs down";
      final String UP = "thumbs up";
      final String GETREVIEW = "enter review line --> ";
      
      System.out.print(GETREVIEW);
      //gets each word individually
      keyboard.nextLine();
      String line = keyboard.nextLine();
      Scanner linescan = new Scanner(line);
      String word = "";
      double totalScore = 0;
      double wordScore = 0;
      
      int i = 0;
      while (linescan.hasNext()) {
         
         word = linescan.next().toLowerCase();
         wordScore = scoreForOneWord(reviews, word);
         totalScore += wordScore;
         
         if (wordScore > 0) {
            i++;
         }
      }
      double reviewScore = totalScore / i;
      if (i < 1) { 
         System.out.println(NOCOMPUTE);
      } else {
         System.out.printf("full review score is %.5f", reviewScore); 
         System.out.println();
      }
      if (reviewScore < (2 - delta)) {
         System.out.println(DOWN);
      } else if (reviewScore > (2 + delta)) {
         System.out.println(UP);
      } 
      System.out.println();
   }
   
   /**
   * given 1 word, return the average review score of that word as a double.
   * @param reviews array containing all the reviews
   * @param userWord the individual word that we are finding the ave score for
   * @return average score for that word
   */
   public static double scoreForOneWord(String[] reviews, String userWord) {
      int count = 0;
      double totalScore = 0;
      double average = 0;
             
      String oneReview = "";
      for (int i = 0; i < reviews.length; i++) {
         //get one line in reviews
         oneReview = reviews[i].toLowerCase();
         if (oneReview.indexOf(userWord) >= 0) {
            count++;
            totalScore += Integer.parseInt(oneReview.substring(0, 1));
         }
      }
      
      if (count != 0) {
         average = totalScore / count;
      }
      return average;
   }

   /**
   * prompts the user for a new delta, that is passed back to the main method.
   * @param keyboard scanner that takes users inout
   * @return the user inputted delta value
   */
   public static double option4(Scanner keyboard) {
      final String GETDELTA = "enter new delta [0,1] --> ";
      System.out.print(GETDELTA);
      
      double delta = keyboard.nextDouble();
      System.out.println();
      return delta;  
   }
   
   /**
   * if the user picked this option, print goodbye and end program. 
   */
   public static void option1() {
      final String BYE = "goodbye";
      System.out.println(BYE);
   }
   
   /**
   * present user with menu options w/ options 1-4.
   */
   public static void displayMenu() {
      final String OPTION1 = "1. quit program";
      final String OPTION2 = "2. word scores";
      final String OPTION3 = "3. full review";
      final String OPTION4 = "4. cutoff delta";
   
      System.out.println(OPTION1);
      System.out.println(OPTION2);
      System.out.println(OPTION3);
      System.out.println(OPTION4);
      
   }
   
}
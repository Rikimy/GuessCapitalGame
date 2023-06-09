import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

  static final ArrayList<Country> countries = new ArrayList<>();

  public static void main(String[] args) {
    // Get the countries & their capitals from the db countries
    readDatabase();
    // Start the game
    System.out.println("Please enter your name");
    String playerName = getPlayerName();
    System.out.println("******* Hello " + playerName + ", let the game begin ******");
    int finalScore = 0;
    while (continuePlaying()) {
      int expectedResponse = choose4Countries();
      int currentGameScore = readResponse(expectedResponse);
      finalScore = finalScore + currentGameScore;
    }
    System.out.println("You got " + finalScore + " point(s) in this game.");
    saveScoreOnDatabase(playerName, finalScore);
    System.out.println("*************************************");
    System.out.println("*********** Final scores ************");
    readScores();
  }

  private static void readDatabase() {
    try {
      Connection conn = DriverManager.getConnection("jdbc:h2:./src/countries", "sa", "");
      Statement stmt = conn.createStatement();

      ResultSet rs = stmt.executeQuery("SELECT * FROM countries");
      while (rs.next()) {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String capital = rs.getString("capital");
        // System.out.println("id = " + id + ", name = " + name + ", capital = " + capital);
        countries.add(new Country(id, name, capital));
      }
    } catch (Exception e) {
      System.out.println("Problem on connection to the BD " + e.getMessage());
    }
  }

  public static String getPlayerName() {
    Scanner keyboard = new Scanner(System.in);
    // Get player's name
    return keyboard.next();
  }

  private static int choose4Countries() {
    ArrayList<Country> chosenCountries = new ArrayList<>();
    List<Integer> randomIndexes = new ArrayList<>();
    while (chosenCountries.size() < 4) {
      Random random = new Random();
      int num = random.nextInt(countries.size());
      if (!randomIndexes.contains(num)) {
        randomIndexes.add(num);
        chosenCountries.add(countries.get(num));
      }
    }

    int randomIndexForQuestion = new Random().nextInt(4);
    String countryForQuestion = chosenCountries.get(randomIndexForQuestion).getCountryName();
    int indexForGoodAnswer = randomIndexForQuestion + 1;
    System.out.println("What's the capital for the country : " + countryForQuestion + "\n");
    for (int i = 1; i <= chosenCountries.size(); i++) {
      System.out.println(i + ". " + chosenCountries.get(i - 1).getCountryCapital() + "\n");
    }
    return indexForGoodAnswer;
  }

  private static int readResponse(final int expectedResponse) {
    Scanner keyboard = new Scanner(System.in);
    int score = 0;
    try {
      // Read the response number
      int number = keyboard.nextInt();
      while (number != expectedResponse) {
        System.out.println("Bad response, let's try again!");
        number = keyboard.nextInt();
        score--;
      }
      System.out.println("Good game, it's the correct response");
      score++;
      return score;
    } catch (InputMismatchException e) {
      System.out.println("You should enter the index of the chosen response");
      return readResponse(expectedResponse);
    }
  }

  public static boolean continuePlaying() {
    System.out.println("Do you want to continue playing (Y|N) ?");
    Scanner Keyboard = new Scanner(System.in);
    String continueGame = Keyboard.next();
    if (continueGame.equals("N")) {
      System.out.println("Game over!");
      return false;
    }
    return true;
  }

  public static void saveScoreOnDatabase(String playerName, int playerScore) {
    try {
      Connection conn = DriverManager.getConnection("jdbc:h2:./src/userScore", "sa", "");
      Statement stmt = conn.createStatement();

      // You should uncomment the following line if the table: userScore is not existing on the current project
      // stmt.execute("CREATE TABLE userScore (name VARCHAR(255), score INT)");
      ResultSet rs = stmt.executeQuery(
          "SELECT * FROM userScore WHERE (name = '" + playerName + "')");

      // Calculate the final score of the current player
      int finalScore = playerScore;
      while (rs.next()) {
        int savedScore = rs.getInt("score");
        finalScore = finalScore + savedScore;
      }

      // Delete the existing entries of the current player in the table userScore
      stmt.executeUpdate("DELETE FROM userScore WHERE (name = '" + playerName + "')");

      // Add an entry for the current player with its new final score
      stmt.executeUpdate(
          "INSERT INTO userScore (name, score) VALUES ('" + playerName + "', '" + finalScore
              + "' )");
      System.out.println(
          "Your total score is: " + finalScore + " point(s).");
      stmt.close();
      conn.close();
    } catch (Exception e) {
      System.out.println("Problem on saving the player's score. " + e.getMessage());
    }
  }

  private static void readScores() {
    try {
      Connection conn = DriverManager.getConnection("jdbc:h2:./src/userScore", "sa", "");
      Statement stmt = conn.createStatement();

      ResultSet rs = stmt.executeQuery("SELECT * FROM userScore");
      while (rs.next()) {
        String name = rs.getString("name");
        int score = rs.getInt("score");
        System.out.println("PlayerName: " + name + ", score: " + score);
      }
    } catch (Exception e) {
      System.out.println("Problem on reading the players scores. " + e.getMessage());
    }
  }
}
package main.java.com.thinkful.zcarter.objectexplorer;

/**
 * Created by JulianK on 09/02/2016.
 */
public class ApplicationSettings {
    private static ApplicationSettings ourInstance = new ApplicationSettings();
    public String gameDifficulty;
    public String homeTeamName;
    public String awayTeamName;
    int numberOfBallsInGame;


    public static ApplicationSettings getInstance() {
        return ourInstance;
    }

    private ApplicationSettings() {
        this.gameDifficulty = "easy";
        this.homeTeamName = "redSocks";
        this.awayTeamName = "blueSocks";
        this.numberOfBallsInGame = 1;
    }
}

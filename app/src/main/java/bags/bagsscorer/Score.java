package bags.bagsscorer;

/**
 * Created by joshh_000 on 6/18/2018.
 */

public class Score {
    public int scoreAmount;
    public char team;

    public Score(int score, char teamName) {
        this.scoreAmount = score;
        this.team = teamName;
    }

    public int getScore() {
        return this.scoreAmount;
    }

    public char getTeam() {
        return this.team;
    }

    public void setScore(int score) {
        this.scoreAmount = score;
    }
}

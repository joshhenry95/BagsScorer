package bags.bagsscorer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    public static boolean canGoOver;
    public static Stack<Score> pastScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        canGoOver = true;
        pastScores = new Stack<Score>();

        final Button plusThreeA = (Button) findViewById(R.id.plus_three_a_button);
        final Button plusOneA = (Button) findViewById(R.id.plus_one_a_button);
        final Button plusThreeB = (Button) findViewById(R.id.plus_three_b_button);
        final Button plusOneB = (Button) findViewById(R.id.plus_one_b_button);
        final Button undoButton = (Button) findViewById(R.id.undo_button);
        final Button newGameButton = (Button) findViewById(R.id.new_game_button);
        final Button goOverButton = (Button) findViewById(R.id.go_over_button);
        final TextView teamA = (TextView) findViewById(R.id.team_a_score);
        final TextView teamB = (TextView) findViewById(R.id.team_b_score);

        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pastScores = new Stack<Score>();
                teamA.setText("0");
                teamB.setText("0");
            }
        });

        goOverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canGoOver = !canGoOver;

                if (canGoOver) {
                    goOverButton.setText("CAN GO OVER 21");
                } else {
                    goOverButton.setText("CAN'T GO OVER 21");
                }
            }
        });

        plusThreeA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score(new Score(3, 'a'), teamA);
            }
        });

        plusOneA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score(new Score(1, 'a'), teamA);
            }
        });

        plusThreeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score(new Score(3, 'b'), teamB);
            }
        });

        plusOneB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score(new Score(1, 'b'), teamB);
            }
        });

        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Get the last score (last scored team's name, the score they got).
                    // Since this is an undo, we want to remove it from the stack.
                    Score s = pastScores.pop();
                    int currTeamScore = 0;

                    if (s.getTeam() == 'a') {
                        currTeamScore = Integer.parseInt(teamA.getText().toString());
                        currTeamScore -= s.getScore();
                        teamA.setText(Integer.toString(currTeamScore));
                    } else {
                        currTeamScore = Integer.parseInt(teamB.getText().toString());
                        currTeamScore -= s.getScore();
                        teamB.setText(Integer.toString(currTeamScore));
                    }

                } catch (EmptyStackException e) {
                } catch (NumberFormatException n) {}
            }
        });
    }

    private static void score(Score s, TextView teamLabel) {
        int currScore;

        currScore = Integer.parseInt(teamLabel.getText().toString());
        currScore += s.getScore();

        if (!canGoOver && currScore > 21) {
            // Push s
            pastScores.push(new Score(s.getScore(), s.getTeam()));

            // Get the difference:
            int diff = Math.abs(currScore - 15);

            // Add this difference to s, so we can undo it later:
            s.setScore(-diff);

            // Set the current score to 15:
            currScore = 15;
        }

        teamLabel.setText(Integer.toString(currScore));

        pastScores.push(s);
    }
}

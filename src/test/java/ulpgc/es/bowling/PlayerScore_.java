package ulpgc.es.bowling;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class PlayerScore_ {

    private PlayerScore PlayerScore(int... pins) {
        PlayerScore playerScore = new PlayerScore("Carlos");
        for (int pin : pins) playerScore.roll(pin);
        return playerScore;
    }

    @Test
    public void given_2_rolls_should_return_the_sum_of_them() {
        assertThat(PlayerScore(1, 2).score()).isEqualTo(3);
    }

    @Test
    public void if_player_dont_roll_should_return_0() {
        assertThat(PlayerScore().score()).isZero();
    }

    @Test
    public void given_1_roll_with_strike_frames_should_be_1() {
        assertThat(PlayerScore(10).frames().size()).isEqualTo(1);
    }

    @Test
    public void given_1_roll_with_strike_frame_score_should_be_null() {
        assertThat(PlayerScore(10).frame(0).score()).isEqualTo(null);
    }

    @Test
    public void given_2_rolls_frames_should_be_1() {
        assertThat(PlayerScore(1,2).frames().size()).isEqualTo(1);
    }

    @Test
    public void if_player_have_1_roll_cant_return_nothing() {

    }

    private class PlayerScore {
        private List<Integer> rolls = new ArrayList<>();

        public PlayerScore(String name) {
        }

        /**
         * Suponemos que un frame con un pleno solo tiene 1 roll no 2 rolles.
         */
        public List<Frame> frames() {
            List<Frame> frames = new ArrayList<>();
            for (int i = 0; i < rolls.size(); i++) {
                frames.add(new Frame(i));
                if (rolls.get(i) < 10) i++;
            }
            return frames;
        }

        public int score() {
            return frames().stream().filter(f -> f.score() != null).mapToInt(Frame::score).sum();
        }

        public PlayerScore roll(int pin) {
            rolls.add(pin);
            return this;
        }

        public Frame frame(int i) {
            return frames().get(i);
        }

        private class Frame {
            private int index;


            public Frame(int index) {
                this.index = index;
            }

            public Integer score() {
                if (this.index + 1 <= rolls.size() - 1) return rolls.get(index) + rolls.get(index + 1);
                return null;
            }
        }
    }
}

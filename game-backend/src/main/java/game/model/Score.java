package game.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Score {

   
    String player;
    int score;
     public Score(final String player, final int score) {
        this.player = player;
        this.score = score;
    }

    public Score() {
         player = "";
         this.score = 0;
    }

    public String getPlayer() {
        return player;
    }

    public int getScore() {
        return score;
    }

    /**
     *
     * @param newScore the newScore achieved by the player
     * @return the Score, if the newScore is inferior to the actual score of the player, it is not saved
     */
    public Score changeScore(final int newScore) {
        this.score = Math.max(this.score, newScore);
        return this;
    }

    public void setPlayer(final String player) {
        this.player = player;
    }

    @Override
    public boolean equals(final Object o) {
        if (o instanceof Score) {
            return ((Score) o).player.equals(this.player) && score == ((Score) o).score;

        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

package game.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Random;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerImpl implements Player {
    private String name;

    @JsonIgnore
    private final Inventaire inventaire;

    public PlayerImpl() {
        name = randomName();
        inventaire = new Inventaire();
    }

    public PlayerImpl(final String name) {
        this.name = name;
        inventaire = new Inventaire();
    }

    /**
     *
     * @return a random Name for the player, each type a new player enter the game
     */
    @Override
    public String randomName() {

        final int leftLimit = 97; // letter 'a'
        final int rightLimit = 122; // letter 'z'
        final int targetStringLength = 20;
        final Random random = new Random();


        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Inventaire getInventaire() {

        return inventaire;
    }
}

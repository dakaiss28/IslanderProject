package game.model;

/**
 * type of Block the player can put on a Tile
 */
public enum Block {
    HOUSE, FONTAIN, CIRCUS, WIND_TURBINE, NOTHING;

    /**
     *
     * @return the radius taken in to account to get neighbours of a tile on a map
     */
    int radiusEffect() {
        switch (this) {
            case CIRCUS: return 3;
            default: return 1;
        }
    }
}

package game.model;

import java.util.HashMap;
import java.util.Map;

public class Inventaire {

    Map<Block, Integer> stock;


    public Inventaire() {
        stock = new HashMap<>();
        stock.put(Block.HOUSE, 1);
        stock.put(Block.WIND_TURBINE, 0);
        stock.put(Block.CIRCUS, 0);
        stock.put(Block.FONTAIN, 0);

    }

    /**
     * at each turn, the stock of the player is updated
     */
    public void updateStock() {
        stock.keySet().stream().forEach(b -> stock.put(b, stock.get(b) + 1));

    }

    public void removeBlocks() {
        stock.keySet().stream().forEach(b -> stock.put(b, stock.get(b) - 1));
    }

    /**
     * when a player posts a block on the map, it's removed from the stock
     * @param b the block to remove
     */
    public void removeBlock(final Block b) {
        final int nbBlock = stock.get(b);
        if (nbBlock > 0) {
            stock.put(b, nbBlock - 1);
        } else {
            throw new UnsupportedOperationException("il y a 0 bloc de ce type dans le stock");

        }
    }

    /**
     * when the player undoes a PutBlock command, the block goes back to the stock
     * @param b the block to get back
     */
    public void addBlock(final Block b) {
        final int nbBlock = stock.get(b);
        stock.put(b, nbBlock + 1);
    }

    /**
     *
     * @param b the type of block
     * @return the number of block of type b in the player's stock
     */
    public int nbBlock(final Block b) {
        return stock.get(b);
    }
}

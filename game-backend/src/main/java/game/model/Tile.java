package game.model;


import java.util.Random;

public class Tile {
    private  boolean occupied;
    private final int x;
    private final int y;
    private final Type type;
    private Block block;

    /**
     *
     * @param x the coordinate x of the Tile
     * @param y the coordinate y of the Tile
     * @param t Type of the Tile, if it is something else than GRASS, the Tyle is set occupied
     * initially the block NOTHING is on all the Tiles ( help to compute score)
     */
    public Tile(final int x, final int y, final Type t) {
        this.x = x;
        this.y = y;
        this.type = t;
        occupied = (type != Type.GRASS);
        block = Block.NOTHING;

    }
    public Tile() {
        final Random r = new Random();
        x = r.nextInt(10);
        y = r.nextInt(10);
        type = getRandomType();
        occupied = type != Type.GRASS;
        block = Block.NOTHING;
    }

    public void setBlock(final Block b) {
        if(type == Type.GRASS) {
            block = b;
        }
    }

    public Block getBlock() {
        return block;
    }
    public boolean getOccupied() {
        return occupied;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setOccupied(final boolean b) {
        occupied = b;
    }

    public Type getType() {
        return type;
    }

    /**
     *
     * @return a random Type for a Tile
     */
    public static Type getRandomType() {
        final Random rand = new Random();
        final int r = rand.nextInt(3);
        switch (r) {
            case 0 :
                return Type.GRASS;
            case 1 :
                return Type.TREE;
            default:
                return Type.WATER;
        }
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Tile) {
            return x == ((Tile) obj).x && y == ((Tile) obj).y && type == ((Tile) obj).type;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}


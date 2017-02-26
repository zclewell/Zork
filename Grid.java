package cs.clewell;


/**
 * Created by Zach on 2/16/2017.
 */
public class Grid {
    private int dimension;
    private Position start;
    private Position end;
    private Position[] obstacles;
    public int getDimension()
    {
        return dimension;
    }

    public Position getStart() {
        return start;
    }

    public Position getEnd() {
        return end;
    }

    public Position[] getObstacles() {
        return obstacles;
    }
}
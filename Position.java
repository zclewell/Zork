package cs.clewell;

/**
 * Created by Zach on 2/16/2017.
 */
public class Position {
    private int x;
    private int y;

    public Position(int xPos, int yPos)
    {
        x=xPos;
        y=yPos;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void printCoords()
    {
        System.out.println("x="+getX()+", y="+getY());
    }

    public boolean equals(Position other)
    {
        if(other.getY() == y && other.getX() == x)
        {
            return true;
        }
        return false;
    }

    public int toInt()
    {
        return x*100+y; //encodes position in the form of XXYY
    } //encoding fails if dimension >99
}

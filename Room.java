import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zach on 2/13/2017.
 */
public class Room {
    private String name;
    private String description;
    private Direction[] directions;
    private String[] items = {};
    private List<String> itemList = new ArrayList<String>();
    private boolean isSetup = false;



    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public Direction[] getDirections()
    {
        return directions;
    }

    public List<String> getItemList()
    {
        return itemList;
    }

    public void setup() //initializes List itemList
    {
        if(!isSetup) {
            for (String curritem : items) {
                itemList.add(curritem);
            }
            isSetup = true;
        }
    }

    public void printDirections()
    {
        for(int i=0; i<directions.length; i++)
        {
            System.out.print(directions[i].getDirection()); //Didn't use a for each loop because needed to format "," and "or"
            if( i+2 == directions.length)
            {
                if(directions.length>1)
                {
                    System.out.print(" or ");
                }
            }
            else
            {
                if( i+1 != directions.length)
                {
                    System.out.print(", ");
                }
            }

        }
        System.out.println();
    }

    public boolean isValidDirection(String input) //checks that string corresponds to a direction in the direction arry
    {
        for(Direction index : directions)
        {
            if (input.equals(index.getDirection().toLowerCase()))
            {
                return true;
            }
        }
        return false;
    }

    public String getRoomDirName(String input) //Takes a direction and returns the corresponding room name
    {
        input = input.toLowerCase();
        for (Direction index : directions)
        {
            if (index.getDirection().toLowerCase().equals(input)) {
                return index.getRoom();
            }

        }
        return null; //Should never be reached
    }

    public void addItem(String input)
    {
        setup();
        itemList.add(input);
    }

    public void removeItem(String input)
    {
        setup();
        itemList.remove(input);
    }

    private boolean hasItems()
    {
        setup();
        if(!itemList.isEmpty())
        {
            return true;
        }
        return false;
    }

    public void printItems()
    {
        setup();
        if(hasItems()) {
                        if (itemList.size() < 1) {
                System.out.print("There are no objects near you");
            } else {
                System.out.print("Around you you find: ");
                for (int i = 0; i < itemList.size(); i++) {
                    System.out.print("a(n) " + itemList.get(i));
                    if (i + 2 == itemList.size()) {
                        if (itemList.size() > 1) {
                            System.out.print(" and ");
                        }
                    } else {
                        if (i + 1 != itemList.size()) {
                            System.out.print(", ");
                        }
                    }

                }
                System.out.println();
            }
        }
    }

    public boolean isValidItem(String input)
    {
        setup();
        for(String index : itemList)
        {
            if(index.equals(input))
            {
                return true;
            }
        }
        return false;
    }

}

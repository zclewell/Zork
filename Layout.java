import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Zach on 2/13/2017.
 */
public class Layout {
    private String initialRoom;
    private Room[] rooms;
    private Map<String, Room> map = new HashMap<String, Room>();

    private boolean isSetup = false;

    public String getInitialRoom()
    {
        return initialRoom;
    }

    public Room[] getRooms()
    {
        return rooms;
    }

    private void setup()
    {
        if(!isSetup) {
            for (Room index : rooms) {
                map.put(index.getName(), index);
            }
            isSetup = true;
        }
    }

    public Room getStart()
    {
        setup();
        return map.get(initialRoom);
    }

    public Room getRoom(String input) //Returns Room object given a room name
    {
        setup();
        return map.get(input);
    }


}

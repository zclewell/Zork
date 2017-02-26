/**
 * Created by Zach on 2/13/2017.
 */
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

public class Main {

    private static Layout layout;
    private static List<String> inventory = new ArrayList<String>();

    public static void main(String[] args)
    {
        setup();
        Room room = layout.getStart();
        Scanner reader  = new Scanner(System.in);
        String input;
        while(true)
        {
            System.out.print(room.getDescription()+"\nFrom here you can go: ");
            room.printDirections();
            room.printItems();
            printInventory();
            input = reader.nextLine();
            input = input.trim().toLowerCase();
            if(input.equals("quit"))
            {
                System.out.println("EXIT");
                break;
            }
            if(input.equals("newurl"))
            {
                System.out.println("Please enter new URL");
                String newURL = reader.nextLine();
                setupUrl(newURL);
                room = layout.getStart();
                continue;
            }
            if(input.equals("newfile"))
            {
                System.out.println("Please enter filename");
                String filename = reader.nextLine();
                setupFile(filename);
                room = layout.getStart();
                continue;
            }
            try {
                if (input.contains("go ")) //Didn't use switch here because of complications with go and other commands
                {
                    input = input.substring(3);
                    if (room.isValidDirection(input)) {
                        room = layout.getRoom(room.getRoomDirName(input));
                    } else {
                        System.out.println("I can't go " + input);
                    }
                }


                else if (input.contains("drop ")) {
                    input = input.substring(5);
                    if(inventory.contains(input))
                    {
                        room.addItem(input);
                        inventory.remove(input);
                    } else {
                        System.out.println("You don't have a "+input);
                    }
                }

                else if(input.contains("pick up ")) {
                    input = input.substring(8);
                    if(room.isValidItem(input))
                    {
                        inventory.add(input);
                        room.removeItem(input);
                    } else {
                        System.out.println("There is no "+input+" around you.");
                    }
                }

                else {
                    System.out.println("I can't " + input);
                }
            }
            catch (ArrayIndexOutOfBoundsException oub)
            {
                input = "";
            }
        }

    }

    /*
    @param
    @return
    @purpose Creates layout with default layout from default URL
     */
    private static void setup()
    {
        try
        {
            URL url = new URL("https://courses.engr.illinois.edu/cs126/resources/siebel.json");
            InputStream inStream = url.openStream();
            JsonReader jsonReader = new JsonReader(new InputStreamReader(inStream, Charset.forName("UTF-8")));
            Gson gson = new Gson();
            layout = gson.fromJson(jsonReader, Layout.class);
        }
        catch (Exception ex)
        {
            System.out.println("Error receiving layout");

        }
    }
    /*
    @param newUrl Take an URL corresponding to the location of the new JSON
    @return void
    @purpose Changes layout to a new Layout based on a JSON from newUrl
     */
    private static void setupUrl(String newUrl)
    {
        try
        {
            URL url = new URL(newUrl);
            InputStream inStream = url.openStream();
            JsonReader jsonReader = new JsonReader(new InputStreamReader(inStream, Charset.forName("UTF-8")));
            Gson gson = new Gson();
            layout = gson.fromJson(jsonReader, Layout.class);
        }
        catch (Exception ex)
        {
            System.out.println("Error receiving layout");

        }
    }
    /*
    @param fileName Name of local JSON
    @return void
    @purpose Changes layout to a new Layout based on the JSON found in fileName
     */
    private static void setupFile(String fileName)
    {
        try
        {
            JsonReader jsonReader = new JsonReader(new FileReader(fileName));
            Gson gson = new Gson();
            layout = gson.fromJson(jsonReader, Layout.class);
        }
        catch (Exception ex)
        {
            System.out.println("Error receiving layout");

        }
    }
    private static void printInventory()
    {
        if (!inventory.isEmpty())
        {
            System.out.print("You have:");
            for (String index: inventory)
            {
                System.out.print(" a "+index);
            }
            System.out.println();
        }
    }
}

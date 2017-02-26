package cs.clewell;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import javafx.geometry.Pos;

public class Main {

    private static final String GRID_URL = "https://courses.engr.illinois.edu/cs126/resources/grid.json";
    private static Grid grid;
    private static Set<Integer> closedSet = new HashSet<Integer>();
    private static Set<Integer> openSet = new HashSet<Integer>();
    private static Map<Integer, Integer> startScore = new HashMap<Integer, Integer>(); //Used Int as key because would have had to override hashCode()
    private static Map<Integer, Integer> endScore = new HashMap<Integer, Integer>();
    private static Map<Integer, Position> cameFrom = new HashMap<Integer, Position>();
    private static List<Position> route = new ArrayList<Position>();
    private static boolean isSetup = false;

    public static void main(String[] args) {
        setup();
        Position start = grid.getStart();
        Position end = grid.getEnd();
        AStar(start,end);
        for (Position position : route) {
            position.printCoords();
        }
    }

    private static void AStar(Position begin, Position finish)
    {
        startScore.put(begin.toInt(), 0);

        endScore.put(begin.toInt(), getManhattanDist(begin, finish));
        openSet.add(begin.toInt());
        while (!openSet.isEmpty()) {
            Position curr = getLowEndScore();
            if (curr.equals(finish)) {
                reconstructPath(curr);
                break;
            }
            openSet.remove(curr.toInt());
            closedSet.add(curr.toInt());
            List<Position> neighbors = new ArrayList<Position>(); //Makes positions in all four directions and adds valid ones to list
            Position north = new Position(curr.getX(), curr.getY() + 1);
            Position south = new Position(curr.getX(), curr.getY() - 1);
            Position east = new Position(curr.getX() + 1, curr.getY());
            Position west = new Position(curr.getX() - 1, curr.getY());
            if (isValidPos(north)) {
                neighbors.add(north);
            }
            if (isValidPos(south)) {
                neighbors.add(south);
            }
            if (isValidPos(east)) {
                neighbors.add(east);
            }
            if (isValidPos(west)) {
                neighbors.add(west);
            }
            for (Position neighbor : neighbors) {
                if (closedSet.contains(neighbor.toInt())) { //if neighbor already evaluated skip to next neighbor
                    continue;
                }
                int currPathValue = startScore.get(curr.toInt()) + getManhattanDist(curr, neighbor);
                if (currPathValue >= startScore.get(neighbor.toInt())) { //if path longer skip to next neighbor
                    continue;
                }
                if (!openSet.contains(neighbor.toInt())) { //if new point discovered add to openSet
                    openSet.add(neighbor.toInt());
                }
                cameFrom.put(neighbor.toInt(), curr);
                startScore.put(neighbor.toInt(), currPathValue);
                endScore.put(neighbor.toInt(), startScore.get(neighbor.toInt()) + getManhattanDist(neighbor, finish));
            }


        }
    }

    private static void reconstructPath(Position current) {
        setup();
        route.add(current);
        while (cameFrom.containsKey(current.toInt())) {
            current = cameFrom.get(current.toInt());
            route.add(current);
        }
    }

    private static void setup() {
        if(!isSetup) {
            try {

                URL url = new URL(GRID_URL);
                InputStream inStream = url.openStream();
                JsonReader jsonReader = new JsonReader(new InputStreamReader(inStream, Charset.forName("UTF-8")));
                Gson gson = new Gson();
                grid = gson.fromJson(jsonReader, Grid.class);
            } catch (Exception ex) {
                System.out.println("Error Reading from URL");
            }

            for (int xPos = 0; xPos < grid.getDimension(); xPos++) {
                for (int yPos = 0; yPos < grid.getDimension(); yPos++) {
                    Position temp = new Position(xPos, yPos);
                    startScore.put(temp.toInt(), Integer.MAX_VALUE); //intialize all values to infinity*
                    endScore.put(temp.toInt(), Integer.MAX_VALUE);
                }
            }
            isSetup = true;

        }
    }

    public static int getManhattanDist(Position first, Position second) {
        return Math.abs(first.getX() - second.getX()) + Math.abs(first.getY() - second.getY());
    }

    private static Position getLowEndScore() {
        setup();
        int low = Integer.MAX_VALUE;
        int lowPos = 0;
        for (Integer currPos : openSet) {
            if (endScore.get(currPos) < low) {
                low = endScore.get(currPos);
                lowPos = currPos;
            }
        }
        return toPosition(lowPos);
    }

    public static boolean isValidPos(Position input) {
        setup();
        for (Position blocked : grid.getObstacles()) { //Makes sure position is not occupied
            if (input.equals(blocked)) {
                return false;
            }
        }
        if (input.getX() >= grid.getDimension() || input.getX() < 0) { //ensures position is in bounds
            return false;
        }
        if (input.getY() >= grid.getDimension() || input.getY() < 0) {
            return false;
        }
        return true;
    }

    public static Position toPosition(int input)
    {
        int newY = input%100; //decodes from int representation
        int newX = (input-newY)/100;
        return new Position(newX,newY);
    }


}

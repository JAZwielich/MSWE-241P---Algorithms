import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class Graph {
    private List cityGraph;

    /**
     * Basic constructor
     */
    public Graph(){this.cityGraph = new ArrayList();}

    /**
     * Reads two text files and builds a graph from it
     * @param cities - Location of the first text file
     * @param connections - Location of the second text file
     */
    public void readText(String cities, String connections) {
        //Referenced code from https://www.w3schools.com/java/java_files_read.asp
        //Take two text files one to initially make the city and one to make the connections between the city
        File textCities = new File(cities);
        File textConnections = new File(connections);
        //Builds the list of cities but no connections
        buildCity(textCities);
        //Function call to build to connections of the cities to each other
        buildCityConnections(textConnections);
    }

    /**
     * Builds a list of City objects. The file should be in the following syntax
     * "City : Population"
     * If this is true then it will build a list of cities as the foundation for our graph
     * @param textCities - A File with a bunch of strings with the city name and population
     */
    public void buildCity (File textCities){
        try {
            //Create amd start the scanner for making the cities with their population
            Scanner scanner = new Scanner(textCities);
            while (scanner.hasNextLine()) {
                //Scan each line and take the two arguments and convert them into the City object
                String cityAndPop = scanner.nextLine();
                String temp[] = cityAndPop.split(" : ");
                boolean isThere = false;
                //Create a new city and add it to our graph filtering out any repeats
                for (int i = 0; i < cityGraph.size(); i++){
                    City tempCity = (City) cityGraph.get(i);
                    if (tempCity.getName().equals(temp[0] )){
                        isThere = true;
                    }
                }
                //Only records the first instance of a city
                if (!isThere){
                    City newCity = new City(temp[0], Integer.parseInt(temp[1]));
                    cityGraph.add(newCity);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not find city file" );
            e.printStackTrace();
        }
    }

    /**
     * Adds connections between the cities. Effectively creating pointers in the lists to each node
     * @param cityConnection - File containing all of the connections between the cities
     */
    public void buildCityConnections(File cityConnection){
        try {
            //Create amd start the scanner for making the connections between the cities
            Scanner scanner = new Scanner(cityConnection);
            while (scanner.hasNextLine()) {
                //Scan each line and take the two arguments and convert them into the City object
                String cityToCity = scanner.nextLine();
                String temp[] = cityToCity.split(" : ");
                Integer previousCityIndex = -1; //For when I find one city I save its index
                //Search the existing cityGraph for the cities mentioned. Add the other city to the list of connections
                for (int i = 0; i < cityGraph.size(); i ++){
                    City currentCity = (City) cityGraph.get(i);
                    //Check the first mentioned city and create a connection to the second mentioned city
                    if (temp[0].equals(currentCity.getName())){
                        //Save the index if we have only found one city
                        if (previousCityIndex == -1){
                            previousCityIndex = i;
                        } else{ // Else we must have found the other city
                            swapCityConnections(currentCity, previousCityIndex, i);
                        }
                    } else if (temp[1].equals(currentCity.getName())){ //Check the second mentioned city and create a connection to the first mentioned city
                        if (previousCityIndex == -1){
                            previousCityIndex = i;
                        } else{ // Else we must have found the other city
                            swapCityConnections(currentCity, previousCityIndex, i);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not find city file" );
            e.printStackTrace();
        }
    }

    /**
     * sets two city nodes to each other. Is a helper function for the buildCityConnection function
     * @param currentCity - Current cityNode
     * @param previousCityIndex - index point of the previously saved city
     * @param currentCityIndex - number of the loo[
     */
    public void swapCityConnections(City currentCity, int previousCityIndex, int currentCityIndex){
        // Else we must have found the other city
            City previousCity = (City) cityGraph.get(previousCityIndex);
            currentCity.addConnection(previousCity);
            previousCity.addConnection(currentCity);
            cityGraph.set(previousCityIndex, previousCity);
            cityGraph.set(currentCityIndex, currentCity);
    }

    /**
     * Prints out the entirety of the graph and each nodes connections
     */
    public void printGraph(){
        for (int i = 0; i < cityGraph.size(); i++){
            City currentCity = (City) cityGraph.get(i);
            System.out.println(currentCity.getName() + " : " + currentCity.getPop());
            System.out.print("[");
            for (int j = 0; j < currentCity.getConnectedCities().size(); j++){
                System.out.print(currentCity.getConnectedCities().get(j).getName() + ",");
            }
            System.out.print("]");
            System.out.println("\n______________________________________________");
        }
    }

    /**
     * Breadth first search
     * @param start - Starting Node to begin the search
     * @param end - Ending node to return the shortest Path
     * @return - List of cities on the shortest path
     */
    public List<City> bfs(City start, City end){
        //begin at start and do a breadth first search
        List<City> allConnections = allConnections(start);
        return reversePath(start, end, allConnections);
    }

    /**
     * Returns a list of all nodes that could be connected to from a given starting point and indicating the parent node of the
     * next node
     * @param start - Node to start
     * @return - list of available nodes and how we got there
     */
    public List<City> allConnections(City start){
        Queue<City> visitedCityQueue = new LinkedBlockingQueue<>();
        visitedCityQueue.add(start);
        //We create lists to keep track of the visited nodes and the history of the previous node visited
        List<Boolean> visited = new ArrayList();
        List<City> cityHistory = new ArrayList<>();
        //fill the visited list with all false (indicating we havn't visited anywhere yet)
        for (int i = 0; i < cityGraph.size(); i++){visited.add(false);}
        //Fill the visited history with null
        for (int i = 0; i < cityGraph.size(); i++){cityHistory.add(null);}
        //Set our starting point as true and indicate our starting node
        visited.set(cityGraph.indexOf(start),true);
        cityHistory.set(cityGraph.indexOf(start),start);
        //While we still have nodes to explore (based on whether our to visit queue is empty or not)
        while (!visitedCityQueue.isEmpty()){
            City origin = visitedCityQueue.remove();
            List<City> originNeighbors = origin.getConnectedCities();
            //iterate through all of our cities neighbors
            for (int i = 0; i < originNeighbors.size(); i++){
                //If we haven't already visited that city we should mark how we got here and that we have now visited this City node
                if (!visited.get(cityGraph.indexOf(originNeighbors.get(i)))){
                    visitedCityQueue.add(originNeighbors.get(i));
                    visited.set(cityGraph.indexOf(originNeighbors.get(i)), true);
                    cityHistory.set(cityGraph.indexOf(originNeighbors.get(i)), origin);                }
            }
        }
        return cityHistory;
    }

    /**
     * Figures out the shortest path needed to be taken to get to a given node
     * @param start - Starting node
     * @param end - Ending Node
     * @param allConnections - Taken from the allConnections function. It has all the nodes visited and their parent node
     * @return - The shortest path needed to be taken to get to a given node
     */
    public List<City> reversePath(City start, City end, List<City> allConnections){
        Stack<City> path = new Stack<>();
        City temp = end;
        //We check if we couldn't get to the specified end city from the start
        if (allConnections.get(cityGraph.indexOf(end)) == null){
            return path;
        }
        //Starting from the end we fill a stack with the cities required to get from one city to another (allConnections
        //has the all the nodes in the index corresponding to our cityGraph nodes with the original City we used to get there)
        while (!(temp == start)){
            path.push(temp);
            //for (int i = 0; i < path.size(); i++) {System.out.print(path.peek() == null ? "null" : path.peek().getName());}
            temp = allConnections.get(cityGraph.indexOf(temp));
        }
        List<City> shortestPath = new ArrayList<>();
        int pathSize = path.size();
        shortestPath.add(start);
        //We pop out all the cities in the stack to have the full city path
        for(int i = 0; i < pathSize; i++){
            shortestPath.add(path.pop());
        }
        return shortestPath;
    }

    /**
     * Getter for retrieving specific city object
     * @param name - Name of the city to be acquired
     * @return - the city object with the name of the string
     */
    public City getCity(String name){
        for (int i = 0; i < cityGraph.size(); i++){
            City temp = (City) cityGraph.get(i);
            if (temp.getName().contains(name)){
                return temp;
            }
        }
        throw new RuntimeException("Name does not exist in graph.");
    }

    /**
     * Total number of cities interconnected
     * @param start - Starting point to find all connections
     * @return - total number of cities interconnected to each other
     */
    public int numberOfCities (City start){
        List<City> allConnections = allConnections(start);
        int count = 0;
        for (int i = 0; i < allConnections.size(); i++) {
            if (allConnections.get(i) != null) {
                count++;
            }
        }
        return count;
    }

    /**
     * Returns the total number of islands that are independently connected from each other
     * @return - Total number of seperate islands
     */
    public int numberOfIslands (){
        //This is the number of islands
        int count = 1;
        if (cityGraph.size() == 0){
            return 0;
        }
        List<City> allConnections =allConnections((City) cityGraph.get(0));
        allConnections = replaceAllConnectionsWithReal(allConnections);
        //Loops through all combinations and counts them
        for (int i = 0 ; i < cityGraph.size(); i ++){
            List<City> newConnections = allConnections((City) cityGraph.get(i));
            newConnections = replaceAllConnectionsWithReal(newConnections);
            if (!allConnections.containsAll(newConnections)){
                allConnections.addAll(newConnections);
                count++;
            }
        }
        return count;
    }

    /**
     * Allconnections has a list the same size as cityGraph list but it does not have the actual cities. This method replaces
     * all the items generated from allConnections with the actual city at that list index location. But it also preserves null
     * for cities that are not connected to the island
     * @param allConnections - List generated from all connections
     * @return - List with the actual cities in the index point
     */
    public List<City> replaceAllConnectionsWithReal (List<City> allConnections){
        for (int i = 0; i < allConnections.size(); i++){
            if (allConnections.get(i) != null){
                allConnections.set(i, (City) cityGraph.get(i));
            }
        }
        return allConnections;
    }
    /**
     * Returns the total population of all of the nodes connected to the specified Start city, including the start city
     * @param start - Starting Node
     * @return - Total pop of all connected nodes
     */
    public int totalPopulation (City start){
        List<City> allConnections = allConnections(start);
        int count = 0;
        //Go through all the connections and take the population value and add it
        for (int i = 0; i < allConnections.size(); i++) {
            //Only count the indexs that are NOT null (Indicating we can get to that node)
            if (allConnections.get(i) != null) {
                City temp = (City) cityGraph.get(i);
                count += temp.getPop();
            }
        }
        return count;
    }

    /**
     * returns the total number of links needed to get to a specific node
     * @param start - Node to start at
     * @param end - Node to end at
     * @return shortest number of links needed to get between the two nodes
     */
    public int shortestPathConnections (City start, City end){
        List <City> shortestPath = bfs(start,end);
        if (shortestPath.size() == 0) {
            return 0;
        }
        //We need to reduce it by one since we are only counting the connections NOT the cities themselves
        return shortestPath.size() - 1;
    }

    /**
     * Returns the total population of all of the islands
     * @return - Total population of each island in a list
     */
    public List<Integer> populationOfIslands(){
        //This is the number of islands
        List<Integer> count = new ArrayList<>();
        if (cityGraph.size() == 0){
            return count;
        }
        List<City> allConnections =allConnections((City) cityGraph.get(0));
        count.add(totalPopulation((City) cityGraph.get(0)));
        int size = 0;
        allConnections = replaceAllConnectionsWithReal(allConnections);
        //Loops through all combinations and counts them
        for (int i = 0 ; i < cityGraph.size(); i ++){
            List<City> newConnections = allConnections((City) cityGraph.get(i));
            newConnections = replaceAllConnectionsWithReal(newConnections);
            if (!allConnections.containsAll(newConnections)){
                allConnections.addAll(newConnections);
                count.add(totalPopulation((City) cityGraph.get(i)));
            }
        }
        return count;
    }
}

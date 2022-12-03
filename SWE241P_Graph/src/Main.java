import java.util.List;

public class Main {
    public static void main(String[] args) {

        // Graph that uses a smaller test case
        Graph citys = new Graph();
        citys.readText("city_population.txt", "road_network.txt");
        System.out.println("Here is the shortest number of connections needed to get from Lancaster to San Marcos " +
                        citys.shortestPathConnections(citys.getCity("A"), citys.getCity("C")));
        System.out.println("Total Number of islands: " + citys.numberOfIslands());
        List<Integer> totalCitiesPerIsland = citys.populationOfIslands();
        System.out.println("Total Population on each city in each island: ");
        for (int i = 0; i < totalCitiesPerIsland.size(); i++) {System.out.print(totalCitiesPerIsland.get(i) + ",");}

        /**
         // Graph that uses a smaller test case
        Graph citys = new Graph();
        citys.readText("city_populationCopy.txt", "road_networkCopy.txt");
        citys.printGraph();
        List<City> shortPath = citys.bfs(citys.getCity("Tomato"), citys.getCity("Orange"));
        System.out.println(citys.numberOfCities(citys.getCity("BMW")));
        System.out.println(citys.totalPopulation(citys.getCity("BMW")));
        System.out.println(citys.shortestPathConnections(citys.getCity("Toyota"), citys.getCity("Ford")));
        System.out.println(citys.numberOfIslands());
         */
    }
}
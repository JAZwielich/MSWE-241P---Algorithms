import java.util.ArrayList;
import java.util.List;

public class City {
    private String name;
    private int pop;
    private List<City> connectedCities = new ArrayList<>();

    /**
     * Basic constructor of the city class. initializes pop to 0
     * and it also sets the name to null
     */
    public City(){
        name = null;
        pop = 0;
    }

    /**
     * Constructor that takes three arguments
     * @param name - Name of the city
     * @param pop - Population of the city
     */
    public City(String name, int pop){
        this.name = name;
        this.pop = pop;
    }

    /**
     * Getter for population
     * @return - population number
     */
    public int getPop() {
        return pop;
    }

    /**
     * getter for the connected cities list
     * @return - connected cities list
     */
    public List<City> getConnectedCities() {
        return connectedCities;
    }

    /**
     * getter for name
     * @return - the name if the city
     */
    public String getName() {
        return name;
    }

    /**
     * setter for the connected cities value
     * @param connectedCities - sets the list of connected cities
     */
    public void setConnectedCities(List<City> connectedCities) {
        this.connectedCities = connectedCities;
    }

    /**
     * setter for the name of the city
     * @param name - setter for the city name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * setter for population
     * @param pop - sets the population of the city
     */
    public void setPop(int pop) {
        this.pop = pop;
    }

    /**
     * adds a new connection to a city in the cityConnections list
     * @param newConnection - New connection to be added to the list of city connections
     */
    public void addConnection(City newConnection){this.connectedCities.add(newConnection);}

    /**
     * compares the name of another city
     * @param otherCity - a different city object
     * @return - true if the names match
     */
    public boolean equals (City otherCity){
        return this.name == otherCity.getName();
    }
}

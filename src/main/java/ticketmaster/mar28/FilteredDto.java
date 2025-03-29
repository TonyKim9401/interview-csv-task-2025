package ticketmaster.mar28;

public class FilteredDto {

    private String state;
    private int population;

    public FilteredDto(String state, int population) {
        this.state = state;
        this.population = population;
    }

    public String getState() {
        return state;
    }


    public int getPopulation() {
        return population;
    }
}

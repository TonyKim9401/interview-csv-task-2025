package ticketmaster.mar28;


import com.fasterxml.jackson.annotation.JsonProperty;

public class PopulationDto {

    @JsonProperty("ID County")
    private String idCountry;

    @JsonProperty("County")
    private String county;

    @JsonProperty("ID Year")
    private int idYear;

    @JsonProperty("Year")
    private String year;

    @JsonProperty("Population")
    private int population;

    @JsonProperty("Slug County")
    private String slogCounty;

    public PopulationDto() {
    }

    public PopulationDto(String idCountry, String county, int idYear, String year, int population,
            String slogCounty) {
        this.idCountry = idCountry;
        this.county = county;
        this.idYear = idYear;
        this.year = year;
        this.population = population;
        this.slogCounty = slogCounty;
    }

    public String getIdCountry() {
        return idCountry;
    }

    public String getCounty() {
        return county;
    }

    public int getIdYear() {
        return idYear;
    }

    public String getYear() {
        return year;
    }

    public int getPopulation() {
        return population;
    }

    public String getSlogCounty() {
        return slogCounty;
    }

    @Override
    public String toString() {
        return "PopulationDto{" +
                "idCountry='" + idCountry + '\'' +
                ", county='" + county + '\'' +
                ", idYear=" + idYear +
                ", year='" + year + '\'' +
                ", population=" + population +
                ", slogCounty='" + slogCounty + '\'' +
                '}';
    }
}

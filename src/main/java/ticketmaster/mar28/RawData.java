package ticketmaster.mar28;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class RawData {

    @JsonProperty("data")
    private List<PopulationDto> data;

    public RawData() {
    }

    public RawData(List<PopulationDto> data) {
        this.data = data;
    }

    public List<PopulationDto> getData() {
        return data;
    }
}

package ticketmaster.mar28;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;

public class PopulationDataParser {

    private final ObjectMapper objectMapper;
    private final String rawResponse;

    public PopulationDataParser(String rawResponse) {
        this.objectMapper = new ObjectMapper();
        this.rawResponse = rawResponse;
    }

    public List<PopulationDto> parseJsonToDtos() {
        try {

            JsonNode jsonNode = this.objectMapper.readTree(this.rawResponse);

            JsonNode arrayNode = jsonNode.get("data");

            List<PopulationDto> populationDtos = new ArrayList<>();
            for (JsonNode node : arrayNode) {
                populationDtos.add(new PopulationDto(node.get("ID County").asText(),
                        node.get("County").asText(),
                        node.get("ID Year").asInt(),
                        node.get("Year").asText(),
                        node.get("Population").asInt(),
                        node.get("Slug County").asText()));
            }
            return populationDtos;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

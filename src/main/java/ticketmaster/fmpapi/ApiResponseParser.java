package ticketmaster.fmpapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;

public class ApiResponseParser<T> {

    private final ObjectMapper objectMapper;
    private final String jsonResponse;
    private final Class<T> type;

    public ApiResponseParser(String jsonResponse, Class<T> type) {
        this.objectMapper = new ObjectMapper();
        this.jsonResponse = jsonResponse;
        this.type = type;
    }

    public List<T> parseJsonToDtos() {
        try {
            T[] dataArray = objectMapper.readValue(this.jsonResponse,
                    objectMapper.getTypeFactory().constructArrayType(type));
            return Arrays.asList(dataArray);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

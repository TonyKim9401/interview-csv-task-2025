package ticketmaster.fmpapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class ApiResponseParserTest {

    static class TestDto {

        @JsonProperty("symbol")
        private String symbol;

        @JsonProperty("price")
        private double price;

        public TestDto() {
        }

        public TestDto(String symbol, double price) {
            this.symbol = symbol;
            this.price = price;
        }

        public String getSymbol() {
            return symbol;
        }

        public double getPrice() {
            return price;
        }
    }

    @Test
    @DisplayName("String JSON response is parsed to TestDto Object")
    void shouldParse_jsonResponseToTestDto() throws Exception {
        // given
        String jsonResponse = "[{\"symbol\":\"AAPL\", "
                + "\"price\":150.0}, {\"symbol\":\"GOOGL\", \"price\":2800.0}]";
        ApiResponseParser<TestDto> testDtoApiResponseParser
                = new ApiResponseParser<>(jsonResponse, TestDto.class);

        // when
        List<TestDto> testDtos = testDtoApiResponseParser.parseJsonToDtos();

        // then
        assertThat(testDtos).isNotNull();
        assertThat(testDtos).hasSize(2);
        assertThat(testDtos)
                .extracting(TestDto::getSymbol, TestDto::getPrice)
                .containsExactly(
                        tuple("AAPL", 150.0),
                        tuple("GOOGL", 2800.0)
                );
    }

    @Test
    @DisplayName("Invalid String JSON response has parsing error")
    void shouldThrowException_whenJsonIsInvalid() throws Exception {
        // given
        String jsonResponse = "invalid json response";
        ApiResponseParser<TestDto> testDtoApiResponseParser
                = new ApiResponseParser<>(jsonResponse, TestDto.class);

        // when // then
        assertThatThrownBy(() -> testDtoApiResponseParser.parseJsonToDtos())
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Unrecognized token");
    }
}

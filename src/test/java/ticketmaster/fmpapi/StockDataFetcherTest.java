package ticketmaster.fmpapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StockDataFetcherTest {

    private FmpApiClient mockApiClient;
    private StockDataFetcher stockDataFetcher;

    @BeforeEach
    void setUp() {
        mockApiClient = mock(FmpApiClient.class);
        stockDataFetcher = new StockDataFetcher(mockApiClient);
    }

    @Test
    @DisplayName("Return JSON data data ")
    void shouldReturn_jsonResponseStockData() throws Exception {
        // given
        String mockResponse = "[{\"symbol\":\"AAPL\",\"price\":150.0}]";

        // when
        when(mockApiClient.getStockInfo()).thenReturn(mockResponse);
        String response = stockDataFetcher.fetchData();

        // then
        assertThat(response).isEqualTo(mockResponse);
        verify(mockApiClient, times(1)).getStockInfo();
    }

    @Test
    @DisplayName("Convert JSON data into FMP stock into list")
    void shouldConvert_jsonDataInfoFmpStockList() throws Exception {
        // given
        String jsonResponse = "[{\"symbol\":\"AAPL\",\"price\":150.0}]";

        // when
        List<FmpApiDto> result = stockDataFetcher.parseData(jsonResponse);

        // then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getSymbol()).isEqualTo("AAPL");
        assertThat(result.get(0).getPrice()).isEqualTo(150.0);
    }

    @Test
    @DisplayName("Invalid String JSON response has parsing error")
    void shouldThrowException_whenJsonIsInvalid() throws Exception {
        // given
        String invalidJson = "invalid json";

        // when // then
       assertThatThrownBy(() -> stockDataFetcher.parseData(invalidJson))
               .isInstanceOf(RuntimeException.class)
               .hasMessageContaining("Unrecognized token");
    }
}

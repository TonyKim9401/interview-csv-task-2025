package ticketmaster.fmpapi;

import java.util.List;

public class StockDataFetcher implements DataFetcher<FmpApiDto>{

    private final ApiClient apiClient;

    public StockDataFetcher(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public String fetchData() throws Exception {
        return apiClient.getStockInfo();
    }

    @Override
    public List<FmpApiDto> parseData(String data) {
        return new ApiResponseParser<>(data, FmpApiDto.class).parseJsonToDtos();
    }
}

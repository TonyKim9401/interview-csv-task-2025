package ticketmaster.fmpapi;

public class Main {

    public static void main(String[] args) throws Exception {

        /**
         * Integrate project
         * history call of Apple stock by using API
         * idx: AAPL
         */

        /**
         * difference between compile and implementation

         // Caused by: java.io.IOException: Server returned HTTP response code:
         429 for URL: https://query1.finance.yahoo.com/v7/finance/quote?symbols=AAPL
         Stock stockResult = YahooFinance.get("AAPL", true);

         System.out.println(stockResult.getHistory());
         */


        String symbol = "AAPL";
        ApiClient apiClient = new FmpApiClient(symbol);
        DataFetcher<FmpApiDto> dataFetcher = new StockDataFetcher(apiClient);

        ScheduledExecutor<FmpApiDto> executor = new ScheduledExecutor<>(dataFetcher);
        executor.execute();
    }
}

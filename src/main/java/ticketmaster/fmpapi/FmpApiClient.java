package ticketmaster.fmpapi;

import java.util.Objects;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FmpApiClient implements ApiClient {
    private static final String API_KEY = "zeuswS9JY7nSMKMw89Qank4o14Ti3p3o";
    private static final String BASE_URL = "https://financialmodelingprep.com/api/v3/quote/";
    private final String symbol;
    private final OkHttpClient client;

    public FmpApiClient(String symbol) {
        this.symbol = symbol;
        this.client = new OkHttpClient();
    }

    public String getStockInfo() throws Exception {
        Request request = new Request.Builder()
                .url(BASE_URL + this.symbol + "?apikey=" + API_KEY)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful())
                throw new RuntimeException("HTTP request fail: " + response.code());
            return Objects.requireNonNull(response.body()).string();
        }
    }
}

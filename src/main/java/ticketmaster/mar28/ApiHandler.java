package ticketmaster.mar28;

import java.util.Objects;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;

public class ApiHandler {

    private final OkHttpClient client;

    public ApiHandler() {
        client = new OkHttpClient();
    }

    public String getPopulationInfo() throws Exception {
        Request request = new Builder()
                .url("https://datausa.io/api/data?drilldowns=County&measures=Population")
                .get()
                .build();
        try(Response rawResponse = client.newCall(request).execute()) {
            if (!rawResponse.isSuccessful()) {
                throw new RuntimeException("Http request fail");
            }
            return Objects.requireNonNull(rawResponse.body()).string();
        }
    }
}

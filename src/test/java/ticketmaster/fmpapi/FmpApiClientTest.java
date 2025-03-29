package ticketmaster.fmpapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FmpApiClientTest {

    private MockWebServer mockWebServer;
    private FmpApiClient fmpApiClient;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        String baseUrl = mockWebServer.url("/").toString();
        fmpApiClient = new FmpApiClient("AAPL") {
            @Override
            public String getStockInfo() throws Exception {
                OkHttpClient client = new OkHttpClient();
                okhttp3.Request request =
                        new okhttp3.Request
                                .Builder()
                                .url(baseUrl + "quote/AAPL?apikey=dummyKey")
                                .get()
                                .build();


                try (okhttp3.Response response = client.newCall(request).execute()) {
                    if (!response.isSuccessful()) {
                        throw new RuntimeException("HTTP request fail: " + response.code());
                    }
                    return response.body().string();
                }
            }
        };
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    @DisplayName("Get company stock information using FMP API")
    void shouldReturn_fmpApiResponse() throws Exception {
        // given
        String mockResponseBody = "[{\"symbol\":\"AAPL\",\"price\":223.03}]";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .setResponseCode(200));

        // when
        String response = fmpApiClient.getStockInfo();

        // then
        assertThat(response).isNotNull();
        assertThat(response).contains("\"symbol\":\"AAPL\",\"price\":223.03");
    }

    @Test
    @DisplayName("FMP API Server request fail error code 500")
    void shouldThrowException_fmpApiResponseFail() throws Exception {
        // given
        mockWebServer.enqueue(new MockResponse().setResponseCode(500));

        // when // then
        assertThatThrownBy(() -> fmpApiClient.getStockInfo())
                .isInstanceOf(RuntimeException.class)
                .hasMessage("HTTP request fail: 500");
    }
}

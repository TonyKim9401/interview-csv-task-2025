package ticketmaster.fmpapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ScheduledExecutorTest {

    private DataFetcher<String> dataFetcherMock;
    private ScheduledExecutor<String> executor;

    @BeforeEach
    void setUp() {
        dataFetcherMock = mock(DataFetcher.class);
        executor = new ScheduledExecutor<>(dataFetcherMock);
    }

    @Test
    @DisplayName("Execute ScheduledExecutor with Cache")
    void shouldReturn_cachedData() throws Exception {
        // given
        String responseData = "[{\"symbol\":\"AAPL\"},{\"symbol\":\"GOOGL\"}]";
        List<String> parsedData = List.of("symbol:AAPL", "symbol:GOOGL");
        when(dataFetcherMock.fetchData()).thenReturn(responseData);
        when(dataFetcherMock.parseData(responseData)).thenReturn(parsedData);

        // when
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Thread testThread = new Thread(() -> {
            executor.execute();
            countDownLatch.countDown();
        });

        testThread.start();
        countDownLatch.await();

        // then
        verify(dataFetcherMock, timeout(2000).times(1)).fetchData();
        verify(dataFetcherMock, timeout(2000).times(1)).parseData(responseData);

        executor.execute();

        verify(dataFetcherMock, times(1)).fetchData();
        assertThat(executor.getCache()).isNotNull();
        assertThat(executor.getCache()).isEqualTo(parsedData);
    }

    @Test
    @DisplayName("Cache expires in custom time")
    void shouldExpire_cachedData() throws Exception {
        // given
        String responseData = "[{\"symbol\":\"AAPL\"},{\"symbol\":\"GOOGL\"}]";
        List<String> parsedData = List.of("symbol:AAPL", "symbol:GOOGL");
        when(dataFetcherMock.fetchData()).thenReturn(responseData);
        when(dataFetcherMock.parseData(responseData)).thenReturn(parsedData);

        // when
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Thread testThread = new Thread(() -> {
            executor.execute();
            countDownLatch.countDown();
        });

        testThread.start();
        countDownLatch.await();

        // then
        verify(dataFetcherMock, timeout(2000).times(1)).fetchData();
        verify(dataFetcherMock, timeout(2000).times(1)).parseData(responseData);

        Thread.sleep(executor.getCACHE_EXPIRATION()+1);

        executor.execute();
        verify(dataFetcherMock, times(2)).fetchData();
    }
}

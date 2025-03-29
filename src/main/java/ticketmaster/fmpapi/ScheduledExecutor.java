package ticketmaster.fmpapi;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutor<T> {

    private final ScheduledExecutorService executors;

    // cache data last for a couple of sec
    private final long CACHE_EXPIRATION = 5000;

    // every 3 ec
    private final long EXECUTE_PERIOD = 1;
    private final DataFetcher<T> dataFetcher;

    private long lastUpdate = 0;
    private List<T> cache;

    public ScheduledExecutor(DataFetcher<T> dataFetcher) {
        this.executors =  Executors.newScheduledThreadPool(1);
        this.dataFetcher = dataFetcher;
    }

    public void execute() {
        this.executors.scheduleAtFixedRate(() -> {
            try {
                long currentTime = System.currentTimeMillis();

                // using cache
                if (this.cache != null &&
                    (currentTime - lastUpdate) < CACHE_EXPIRATION) {
                    System.out.println("--- Print cached data: ");
                    this.cache.forEach(System.out::println);
                    return;
                }

                // update data and caching
                String responseData = this.dataFetcher.fetchData();
                List<T> parseData = this.dataFetcher.parseData(responseData);

                this.cache = parseData;
                lastUpdate = currentTime;

                System.out.println("--- Print update data: ");
                parseData.forEach(System.out::println);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }, 0, EXECUTE_PERIOD, TimeUnit.SECONDS);
    }

    public List<T> getCache() {
        return cache;
    }

    public long getCACHE_EXPIRATION() {
        return CACHE_EXPIRATION;
    }
}

package ticketmaster.fmpapi;

import java.util.List;

public interface DataFetcher<T> {

    String fetchData() throws Exception;
    List<T> parseData(String data);
}

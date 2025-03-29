package ticketmaster.mar28;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

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

        /**
        String symbol = "AAPL";
        ApiClient apiClient = new FmpApiClient(symbol);
        DataFetcher<FmpApiDto> dataFetcher = new StockDataFetcher(apiClient);

        ScheduledExecutor<FmpApiDto> executor = new ScheduledExecutor<>(dataFetcher);
        executor.execute();
        */

        /**
         * final outcome
         *
         * population by state for each year
         *
         * years / states / population followed by years and state
         * CSV file
         */

        /**
         *
         * PopDto
         * ApiClient
         * ApiResponseParser -> Jackson Lib
         *
         */

        ApiHandler apiHandler = new ApiHandler();

        // get raw json data
        String rawResponse = apiHandler.getPopulationInfo();

        // parse into dto
        PopulationDataParser populationDataParser = new PopulationDataParser(rawResponse);
        List<PopulationDto> populationDtos = populationDataParser.parseJsonToDtos();

        // raws: years collect
        // col: state collect
        // col: get total population

        // Map<Year, StateInfo>
        // Map<StateCode, Population>
        Map<String, Map<String, Integer>> map = new HashMap<>();

        populationDtos.forEach(populationDto -> {
            String year = populationDto.getYear();
            if (!map.containsKey(year)) {
                String fullCounty = populationDto.getCounty();
                String stateCode = fullCounty.substring(fullCounty.length() - 2);

                Map<String, Integer> stateMap = new HashMap<>();
                stateMap.put(stateCode, populationDto.getPopulation());
                map.put(year, stateMap);
            } else {
                Map<String, Integer> stateMap = map.get(year);

                String fullCounty = populationDto.getCounty();
                String stateCode = fullCounty.substring(fullCounty.length() - 2);

                if (!map.containsKey(stateCode)) {
                    stateMap.put(stateCode, populationDto.getPopulation());
                    map.put(year, stateMap);
                } else {
                    Integer statePopulation = stateMap.get(stateCode);
                    stateMap.put(stateCode, statePopulation + populationDto.getPopulation());
                }
            }
        });

        // sort by key value
        Map<String, Map<String, Integer>> sortedMap = new TreeMap<>(map);

        for (String year : sortedMap.keySet()) {
            System.out.print(year);
            Map<String, Integer> stateInfo = sortedMap.get(year);
            for (String stateCode : stateInfo.keySet()) {
                System.out.print(" " + stateCode);
                System.out.print(" " + stateInfo.get(stateCode));
            }
            System.out.println();
        }

        // [   ],DI,HI map.get(year)
        // 2019,221,123 map.keySet() + stateMap.get(stateCode)


        Set<String> stateCodes = new HashSet<>();
        for (String year : sortedMap.keySet()) {
            Map<String, Integer> stateMap = sortedMap.get(year);
            stateCodes.addAll(stateMap.keySet());
        }


        File file = new File("populationData.csv");
        try (PrintWriter pw = new PrintWriter(file)) {
            pw.print("    ,");
            String collect = stateCodes.stream().collect(Collectors.joining(","));
            pw.println(collect);

            for (String year : sortedMap.keySet())  {
                pw.print(year + ",");
                Map<String, Integer> stateInfo = sortedMap.get(year);
                for (String stateCode : stateCodes) {
                    Integer pop = stateInfo.get(stateCode);
                    pw.print(pop);
                    pw.print(",");
                }
                pw.println();
            }
        }
    }
}

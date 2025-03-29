package ticketmaster.fmpapi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FmpApiDto {

    private String symbol;
    private String name;
    private double price;
    private double changesPercentage;
    private double change;
    private double dayLow;
    private double dayHigh;
    private double yearHigh;
    private double yearLow;
    private long marketCap;
    private double priceAvg50;
    private double priceAvg200;
    private String exchange;
    private long volume;
    private long avgVolume;
    private double open;
    private double previousClose;
    private double eps;
    private int pe;
    private String earningsAnnouncement;
    private long sharesOutstanding;
    private long timestamp;

    public String getSymbol() {
        return symbol;
    }

    @JsonProperty("symbol")
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    @JsonProperty("price")
    public void setPrice(double price) {
        this.price = price;
    }

    public double getChangesPercentage() {
        return changesPercentage;
    }

    @JsonProperty("changesPercentage")
    public void setChangesPercentage(double changesPercentage) {
        this.changesPercentage = changesPercentage;
    }

    public double getChange() {
        return change;
    }

    @JsonProperty("change")
    public void setChange(double change) {
        this.change = change;
    }

    public double getDayLow() {
        return dayLow;
    }

    @JsonProperty("dayLow")
    public void setDayLow(double dayLow) {
        this.dayLow = dayLow;
    }

    public double getDayHigh() {
        return dayHigh;
    }

    @JsonProperty("dayHigh")
    public void setDayHigh(double dayHigh) {
        this.dayHigh = dayHigh;
    }

    public double getYearHigh() {
        return yearHigh;
    }

    @JsonProperty("yearHigh")
    public void setYearHigh(double yearHigh) {
        this.yearHigh = yearHigh;
    }

    public double getYearLow() {
        return yearLow;
    }

    @JsonProperty("yearLow")
    public void setYearLow(double yearLow) {
        this.yearLow = yearLow;
    }

    public long getMarketCap() {
        return marketCap;
    }

    @JsonProperty("marketCap")
    public void setMarketCap(long marketCap) {
        this.marketCap = marketCap;
    }

    public double getPriceAvg50() {
        return priceAvg50;
    }

    @JsonProperty("priceAvg50")
    public void setPriceAvg50(double priceAvg50) {
        this.priceAvg50 = priceAvg50;
    }

    public double getPriceAvg200() {
        return priceAvg200;
    }

    @JsonProperty("priceAvg200")
    public void setPriceAvg200(double priceAvg200) {
        this.priceAvg200 = priceAvg200;
    }

    public String getExchange() {
        return exchange;
    }

    @JsonProperty("exchange")
    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public long getVolume() {
        return volume;
    }

    @JsonProperty("volume")
    public void setVolume(long volume) {
        this.volume = volume;
    }

    public long getAvgVolume() {
        return avgVolume;
    }

    @JsonProperty("avgVolume")
    public void setAvgVolume(long avgVolume) {
        this.avgVolume = avgVolume;
    }

    public double getOpen() {
        return open;
    }

    @JsonProperty("open")
    public void setOpen(double open) {
        this.open = open;
    }

    public double getPreviousClose() {
        return previousClose;
    }

    @JsonProperty("previousClose")
    public void setPreviousClose(double previousClose) {
        this.previousClose = previousClose;
    }

    public double getEps() {
        return eps;
    }

    @JsonProperty("eps")
    public void setEps(double eps) {
        this.eps = eps;
    }

    public int getPe() {
        return pe;
    }

    @JsonProperty("pe")
    public void setPe(int pe) {
        this.pe = pe;
    }

    public String getEarningsAnnouncement() {
        return earningsAnnouncement;
    }

    @JsonProperty("earningsAnnouncement")
    public void setEarningsAnnouncement(String earningsAnnouncement) {
        this.earningsAnnouncement = earningsAnnouncement;
    }

    public long getSharesOutstanding() {
        return sharesOutstanding;
    }

    @JsonProperty("sharesOutstanding")
    public void setSharesOutstanding(long sharesOutstanding) {
        this.sharesOutstanding = sharesOutstanding;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @JsonProperty("timestamp")
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "FmpStockInfo{" +
                "symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", changesPercentage=" + changesPercentage +
                ", change=" + change +
                ", dayLow=" + dayLow +
                ", dayHigh=" + dayHigh +
                ", yearHigh=" + yearHigh +
                ", yearLow=" + yearLow +
                ", marketCap=" + marketCap +
                ", priceAvg50=" + priceAvg50 +
                ", priceAvg200=" + priceAvg200 +
                ", exchange='" + exchange + '\'' +
                ", volume=" + volume +
                ", avgVolume=" + avgVolume +
                ", open=" + open +
                ", previousClose=" + previousClose +
                ", eps=" + eps +
                ", pe=" + pe +
                ", earningsAnnouncement='" + earningsAnnouncement + '\'' +
                ", sharesOutstanding=" + sharesOutstanding +
                ", timestamp=" + timestamp +
                '}';
    }
}

package io.github.aaronfields.bitcoinpricetracker;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aaronfields on 9/20/16.
 */


public class Currency {

        @SerializedName("15m")
        private String fifteenMin;
        private String last;
        private String buy;
        private String sell;
        private String symbol;

        public String getFifteenMin() {
            return fifteenMin;
        }

        public void setFifteenMin(String fifteenMin) {
            this.fifteenMin = fifteenMin;
        }

        public String getLast() {
            return last;
        }

        public void setLast(String last) {
            this.last = last;
        }

        public String getBuy() {
            return buy;
        }

        public void setBuy(String buy) {
            this.buy = buy;
        }

        public String getSell() {
            return sell;
        }

        public void setSell(String sell) {
            this.sell = sell;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }
    }



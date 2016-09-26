package io.github.aaronfields.bitcoinpricetracker;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by aaronfields on 9/20/16.
 */
public interface BitcoinService {

    @GET("ticker")
    Call<Map<String, Currency>> getAll();
    //Call<CurrencyResponse> getRate(@Query("rate") String rate);
}

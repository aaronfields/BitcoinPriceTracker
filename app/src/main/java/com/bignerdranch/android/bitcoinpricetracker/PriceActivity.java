package com.bignerdranch.android.bitcoinpricetracker;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PriceActivity extends AppCompatActivity {

    private BitcoinService service;

    private SwipeRefreshLayout refreshLayout;
    private Map<String, Currency> data;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        setUpRetrofit();
        getBitcoinPrice();
        Log.d("CALLED", "onCreate: I've been called");

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(PriceActivity.this, R.color.colorAccent));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showProgress(true);
                getBitcoinPrice();
            }
        });

    }

    private void setUpRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://blockchain.info/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(BitcoinService.class);
    }

    private void showProgress(boolean show) {
        refreshLayout.setRefreshing(show);
    }

    private void getBitcoinPrice() {
        service.getAll().enqueue(new Callback<Map<String, Currency>>() {
            @Override
            public void onResponse(Call<Map<String, Currency>> call, Response<Map<String, Currency>> response) {
                showProgress(false);

                final Map<String, Currency> results = response.body();
                Log.d("RESULTS123", "onResponse: " + response.body().toString());

                //CurrencyResponse price = results;

                if (response.isSuccessful()) {
                    recyclerView.setAdapter(new PriceAdapter(results));

                    Log.d("SUCCESS", "onResponse: " + results);
                }  else {
                    Log.d("NOPE", "SIZE IS 0");
                }
            }

            @Override
            public void onFailure(Call<Map<String, Currency>> call, Throwable t) {
                Log.d("FAILED123", "onFailure: Failed");
                Log.d(getClass().getSimpleName(), t.getMessage(), t);
            }
        });
    }

    class PriceAdapter extends RecyclerView.Adapter<PriceAdapter.PriceViewHolder> {
        private List<Pair<String, Currency>> data;

        public PriceAdapter(Map<String, Currency> data) {
            this.data = new ArrayList<>();

            for(Map.Entry<String, Currency> entry : data.entrySet()){
                this.data.add(new Pair<String, Currency>(entry.getKey(), entry.getValue()));
            }

        }

        @Override
        public PriceAdapter.PriceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PriceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_price, parent, false));
        }

        @Override
        public void onBindViewHolder(PriceAdapter.PriceViewHolder holder, int position) {
            Pair<String, Currency> currencyPair = this.data.get(position);

            holder.currency.setText(currencyPair.first.toString());
            holder.symbol.setText(currencyPair.second.getSymbol());
            holder.price.setText(currencyPair.second.getLast());

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class PriceViewHolder extends RecyclerView.ViewHolder {
            TextView currency;
            TextView symbol;
            TextView price;

            public PriceViewHolder(View itemView) {
                super(itemView);

                currency = (TextView) itemView.findViewById(R.id.currency_text);
                symbol = (TextView) itemView.findViewById(R.id.currency_symbol);
                price = (TextView) itemView.findViewById(R.id.price_text);
            }
        }
    }


}

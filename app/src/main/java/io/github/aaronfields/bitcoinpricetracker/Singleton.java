package io.github.aaronfields.bitcoinpricetracker;

import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aaronfields on 10/11/16.
 */

public class Singleton {

    private List<Pair<String, Currency>> data;

    private static Singleton singleton;

    private Singleton() {
        data = new ArrayList<>();
    }

    public static Singleton getInstance() {
        if(singleton == null){
            singleton = new Singleton();
        }
        return singleton;
    }

    public List<Pair<String, Currency>> getData() {
        return data;
    }

    public void setData(List<Pair<String, Currency>> data) {
        this.data = data;
    }
}

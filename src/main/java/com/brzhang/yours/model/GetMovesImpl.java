package com.brzhang.yours.model;


import android.content.Context;
import android.nfc.Tag;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.brzhang.yours.App;
import com.brzhang.yours.listener.OnFinishedListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by brzhang on 15/12/27.
 * Description :搬运工，你懂的，专门处理数据的模块，
 * 负责1，从网络，从db拿数据
 * 负责2，处理数据，格式化数据
 */
public class GetMovesImpl implements GetMoves {
    private static final String Tag = GetMovesImpl.class.getName();

    public Observable<List<Move>> getMovesFromNetObservable(){
        return Observable.create(new Observable.OnSubscribe<List<Move>>() {
            @Override
            public void call(final Subscriber<? super List<Move>> subscriber) {
                String request_url = "http://test.igame.qq.com/am/index.php?of=json";
                StringRequest stringRequest = new StringRequest(request_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                        try {
                            List<Move> moveList = gson.fromJson(response,
                                    new TypeToken<List<Move>>() {
                                    }.getType());
                            subscriber.onNext(moveList);
                        } catch ( Exception e ) {
                            subscriber.onError(new Throwable("哎呀，json解析错误啊"));
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        subscriber.onError(new Throwable("你的网络尽然如此的渣啊！"));
                    }
                });
                Volley.newRequestQueue(App.getAppContext()).add(stringRequest);
            }
        });

    }

    @Override
    public Observable<RealmResults<Move>> getMovesFromCacheObservable(final Context context) {
        return Observable.create(new Observable.OnSubscribe<RealmResults<Move>>() {
            @Override
            public void call(Subscriber<? super RealmResults<Move>> subscriber) {
                RealmConfiguration configuration = new RealmConfiguration.Builder(context).build();
                Realm realm = Realm.getInstance(configuration);
                RealmQuery<Move> query = realm.where(Move.class);
                RealmResults<Move> moveList = query.findAll();
                subscriber.onNext(moveList);
            }
        });
    }
}

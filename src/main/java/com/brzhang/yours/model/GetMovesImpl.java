package com.brzhang.yours.model;


import android.nfc.Tag;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.brzhang.yours.App;
import com.brzhang.yours.listener.OnFinishedListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

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

    public Observable<List<Move>> getMovesObservable(){
        return Observable.create(new Observable.OnSubscribe<List<Move>>() {
            @Override
            public void call(final Subscriber<? super List<Move>> subscriber) {
                String request_url = "http://test.igame.qq.com/am/index.php?of=json";
                StringRequest stringRequest = new StringRequest(request_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
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
}

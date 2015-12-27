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
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.schedulers.HandlerScheduler;

/**
 * Created by brzhang on 15/12/27.
 * Description :搬运工，你懂的，专门处理数据的模块，
 * 负责1，从网络，从db拿数据
 * 负责2，处理数据，格式化数据
 */
public class GetMovesImpl implements GetMoves {
    private static final String Tag = GetMovesImpl.class.getName();
    @Override
    public void getMovesFromNet(final OnFinishedListener listener) {

        Observable.just("已", "经", "全", "部", "为","您","读","出","啦")
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        try {
                            Thread.sleep(1000);
                        } catch ( InterruptedException e ) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.e(Tag, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e(Tag, "onError");
                    }

                    @Override
                    public void onNext(String s) {
                        listener.onError(s);
                    }
                });
        String request_url = "http://test.igame.qq.com/am/index.php?of=json";

        StringRequest stringRequest = new StringRequest(request_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                try {
                    List<Move> moveList = gson.fromJson(response,
                            new TypeToken<List<Move>>() {
                            }.getType());
                    listener.onSuccess(moveList);
                } catch ( Exception e ) {
                    listener.onError("哎呀，json解析错误");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError("不好，你的网络环境如此之渣");
            }
        });
        Volley.newRequestQueue(App.getAppContext()).add(stringRequest);
    }
}

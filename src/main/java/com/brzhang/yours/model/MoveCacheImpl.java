package com.brzhang.yours.model;

import android.content.Context;
import android.provider.ContactsContract;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by brzhang on 16/1/13.
 * Description :
 */
public class MoveCacheImpl implements MoveCache{
    @Override
    public Observable<List<Move>> cacheMoves(final Context context, final List<Move> moveList) {
        return  Observable.create(new Observable.OnSubscribe<List<Move>>(){
            @Override
            public void call(Subscriber<? super List<Move>> subscriber) {
                try{
                    RealmConfiguration configuration = new RealmConfiguration.Builder(context).build();
                    Realm realm = Realm.getInstance(configuration);
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(moveList);
                    realm.commitTransaction();
                }catch ( Exception e ){
                    subscriber.onError(new Throwable("缓存电影出现错误！"));
                }

            }
        });

    }
}

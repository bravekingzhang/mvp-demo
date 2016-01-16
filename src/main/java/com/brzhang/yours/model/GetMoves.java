package com.brzhang.yours.model;

import android.content.Context;

import java.util.List;

import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by brzhang on 15/12/27.
 * Description :搬运工抽象接口定义
 */
public interface GetMoves {
    Observable<List<Move>> getMovesFromNetObservable();
    Observable<RealmResults<Move>> getMovesFromCacheObservable(Context context);//从缓存获取
}

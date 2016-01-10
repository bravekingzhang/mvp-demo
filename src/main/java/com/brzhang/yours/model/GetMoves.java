package com.brzhang.yours.model;

import com.brzhang.yours.listener.OnFinishedListener;

import java.util.List;

import rx.Observable;

/**
 * Created by brzhang on 15/12/27.
 * Description :搬运工抽象接口定义
 */
public interface GetMoves {
    public Observable<List<Move>> getMovesObservable();
}

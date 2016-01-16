package com.brzhang.yours.model;

import android.content.Context;

import java.util.List;

import rx.Observable;

/**
 * Created by brzhang on 16/1/13.
 * Description :
 */
public interface MoveCache {
    Observable<List<Move>> cacheMoves(Context context,List<Move> moveList);
}

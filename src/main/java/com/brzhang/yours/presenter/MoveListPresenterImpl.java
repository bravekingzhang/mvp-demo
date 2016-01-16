package com.brzhang.yours.presenter;

import android.util.Log;

import com.brzhang.yours.App;
import com.brzhang.yours.model.GetMoves;
import com.brzhang.yours.model.GetMovesImpl;
import com.brzhang.yours.model.Move;
import com.brzhang.yours.model.MoveCacheImpl;
import com.brzhang.yours.view.MoveListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by brzhang on 15/12/27.
 * Description :presenter实现类
 * 与activity，fragment接触最紧密，用户操作最新感知者
 * 通知搬运工去搬数据，搬运工搬好好通知他，然后他把他才告知viewer更新界面
 */
public class MoveListPresenterImpl implements MoveListPresenter {

    private final MoveCacheImpl cacheMoves;
    private       MoveListView  moveListViewer;
    private       GetMoves      getMoves;

    public MoveListPresenterImpl(MoveListView moveListViewer) {
        this.getMoves = new GetMovesImpl();
        this.cacheMoves = new MoveCacheImpl();
        this.moveListViewer = moveListViewer;
    }

    @Override
    public void onResume() {
        Log.e("realm", "onResume: ");
        moveListViewer.showProgress();
        //从缓存取电影列表
        Observable<List<Move>> listObservable1 = getMoves.getMovesFromCacheObservable(App.getAppContext())
                .flatMap(new Func1<RealmResults<Move>, Observable<List<Move>>>() {
            @Override
            public Observable<List<Move>> call(final RealmResults<Move> moves) {
                return Observable.create(new Observable.OnSubscribe<List<Move>>(){
                    @Override
                    public void call(Subscriber<? super List<Move>> subscriber) {
                        ArrayList<Move> moveList = new ArrayList<Move>();
                        try{
                            for (Move move : moves) {
                                moveList.add(move);
                            }
                            subscriber.onNext(moveList);
                        }catch ( Exception e ){
                            subscriber.onError(new Throwable("transfer errr"));
                        }

                    }
                });
            }
        });
        //从网络取电影，取到之后直接缓存了。flatmap真是好东东
        Observable<List<Move>> listObservable2 = getMoves.getMovesFromNetObservable()
                .flatMap(new Func1<List<Move>, Observable<List<Move>>>() {
                    @Override
                    public Observable<List<Move>> call(final List<Move> moveList) {
                        return Observable.create(new Observable.OnSubscribe<List<Move>>() {
                            @Override
                            public void call(Subscriber<? super List<Move>> subscriber) {
                                RealmConfiguration configuration = new RealmConfiguration.Builder(App.getAppContext()).build();
                                Realm realm = Realm.getInstance(configuration);
                                try {
                                    if (realm.isInTransaction()) {
                                        //调试过程中，我没有发现那里已经开启了transaction了
                                        realm.cancelTransaction();
                                    }
                                    realm.beginTransaction();
                                    //要把里面的null元素都清楚，不然realm批量插入会报错
                                    moveList.removeAll(Collections.singleton(null));
                                    realm.copyToRealmOrUpdate(moveList);
                                    realm.commitTransaction();
                                    subscriber.onNext(moveList);
                                } catch ( Exception e ) {
                                    subscriber.onError(new Throwable("缓存失败骆"));
                                }

                            }
                        });

                    }
                });

        Observable.merge(listObservable1,listObservable2).observeOn(AndroidSchedulers.mainThread())
                .distinct()
                .subscribe(new Action1<List<Move>>() {
                    @Override
                    public void call(List<Move> moveList) {
                        moveListViewer.setItems(moveList);
                        moveListViewer.hideProgress();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        moveListViewer.showMessage(throwable.getMessage());
                    }
                });
    }

}

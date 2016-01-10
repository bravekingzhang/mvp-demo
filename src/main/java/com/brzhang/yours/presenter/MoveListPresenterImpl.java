package com.brzhang.yours.presenter;

import com.brzhang.yours.listener.OnFinishedListener;
import com.brzhang.yours.model.GetMoves;
import com.brzhang.yours.model.GetMovesImpl;
import com.brzhang.yours.model.Move;
import com.brzhang.yours.view.MoveListView;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by brzhang on 15/12/27.
 * Description :presenter实现类
 * 与activity，fragment接触最紧密，用户操作最新感知者
 * 通知搬运工去搬数据，搬运工搬好好通知他，然后他把他才告知viewer更新界面
 *
 */
public class MoveListPresenterImpl implements MoveListPresenter {

    private MoveListView moveListViewer;
    private GetMoves     getMoves;

    public MoveListPresenterImpl(MoveListView moveListViewer) {
        this.getMoves = new GetMovesImpl();
        this.moveListViewer = moveListViewer;
    }

    @Override
    public void onResume() {
        moveListViewer.showProgress();
        getMoves.getMovesObservable()
        .observeOn(AndroidSchedulers.mainThread())
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

package com.brzhang.yours.presenter;

import com.brzhang.yours.listener.OnFinishedListener;
import com.brzhang.yours.model.GetMoves;
import com.brzhang.yours.model.GetMovesImpl;
import com.brzhang.yours.model.Move;
import com.brzhang.yours.view.MoveListView;

import java.util.List;

/**
 * Created by brzhang on 15/12/27.
 * Description :presenter实现类
 * 与activity，fragment接触最紧密，用户操作最新感知者
 * 通知搬运工去搬数据，搬运工搬好好通知他，然后他把他才告知viewer更新界面
 *
 */
public class MoveListPresenterImpl implements MoveListPresenter,OnFinishedListener {

    private MoveListView moveListViewer;
    private GetMoves     getMoves;

    public MoveListPresenterImpl(MoveListView moveListViewer) {
        this.getMoves = new GetMovesImpl();
        this.moveListViewer = moveListViewer;
    }

    @Override
    public void onResume() {
        moveListViewer.showProgress();
        this.getMoves.getMovesFromNet(this);
    }

    @Override
    public void onSuccess(List<Move> moveList) {
        moveListViewer.setItems(moveList);
        moveListViewer.hideProgress();
    }

    @Override
    public void onError(String errMsg) {
        moveListViewer.showMessage(errMsg);
    }

}

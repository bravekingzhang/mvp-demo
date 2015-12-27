package com.brzhang.yours.view;

import com.brzhang.yours.model.Move;

import java.util.List;

/**
 * Created by brzhang on 15/12/27.
 * Description :veiwer接口，更新界面需要操作接口
 */
public interface MoveListView {
    public void showProgress();

    public void hideProgress();

    public void setItems(List<Move> items);

    public void showMessage(String message);
}

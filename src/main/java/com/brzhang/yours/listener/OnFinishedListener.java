package com.brzhang.yours.listener;

import com.brzhang.yours.model.Move;

import java.util.List;

/**
 * Created by brzhang on 15/12/27.
 * Description :监听器
 * 负责监听异步操作结果。通知presenter
 */
public interface OnFinishedListener {
    void onSuccess(List<Move> moveList);
    void onError(String errMsg);
}

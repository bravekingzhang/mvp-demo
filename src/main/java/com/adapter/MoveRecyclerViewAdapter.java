package com.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.brzhang.yours.R;
import com.brzhang.yours.model.Move;
import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.utils.MyLinkMovementMethod;


import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Move} and makes a call to the
 * TODO: Replace the implementation with code for your data type.
 */
public class MoveRecyclerViewAdapter extends RecyclerView.Adapter<MoveRecyclerViewAdapter.ViewHolder> {

    public void setmValues(List<Move> mValues) {
        this.mValues = mValues;
    }

    private List<Move>                        mValues;
    private Activity mActivity;

    public MoveRecyclerViewAdapter(List<Move> items,Activity activity) {
        mValues = items;
        mActivity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Move move = mValues.get(position);
        if (move == null){
            return;
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append("<a href='").append(move.getUrl()).append("'>点击此处查看详情</a>");
        holder.mMoveSrc.setText(Html.fromHtml(spannableStringBuilder.toString()));
        holder.mMoveSrc.setMovementMethod(MyLinkMovementMethod.getInstance());
        holder.mMoveName.setText(move.getmMoveName());
        Glide.with(mActivity).load(move.getmMovePic()).into(holder.mMovePic);
        //防止重复点击
        RxView.clicks(holder.mMoveName)
                .debounce(1500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Log.e("debounce","I has been clicked!");
                    }
                });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.move_name) TextView  mMoveName;
        @Bind(R.id.move_pic) ImageView mMovePic;
        @Bind(R.id.move_src) TextView  mMoveSrc;

        /*@OnClick(R.id.move_name) void sayGetOffMe() {
            Log.e("debounce","I has been clicked!");
        }*/

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}

package com.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.brzhang.yours.R;
import com.brzhang.yours.model.Move;
import com.bumptech.glide.Glide;
import com.utils.MyLinkMovementMethod;


import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Move} and makes a call to the
 * TODO: Replace the implementation with code for your data type.
 */
public class MoveRecyclerViewAdapter extends RecyclerView.Adapter<MoveRecyclerViewAdapter.ViewHolder> {


    public List<Move> getmValues() {
        return mValues;
    }


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
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView  mMoveName;
        public final ImageView mMovePic;
        public final TextView  mMoveSrc;

        public ViewHolder(View view) {
            super(view);
            mMoveSrc = (TextView) view.findViewById(R.id.move_src);
            mMoveName = (TextView) view.findViewById(R.id.move_name);
            mMovePic = (ImageView) view.findViewById(R.id.move_pic);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}

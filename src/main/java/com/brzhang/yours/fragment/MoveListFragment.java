package com.brzhang.yours.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.adapter.MoveRecyclerViewAdapter;
import com.brzhang.yours.R;
import com.brzhang.yours.model.Move;
import com.brzhang.yours.presenter.MoveListPresenter;
import com.brzhang.yours.presenter.MoveListPresenterImpl;
import com.brzhang.yours.view.MoveListView;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class MoveListFragment extends Fragment implements MoveListView {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private              int    mColumnCount     = 1;
    private RecyclerView            mRecyclerView;
    private MoveRecyclerViewAdapter mMoveRecyclerViewAdapter;
    private ProgressBar             mProgressBar;

    private MoveListPresenter moveListPresenter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MoveListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MoveListFragment newInstance(int columnCount) {
        MoveListFragment fragment = new MoveListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        moveListPresenter = new MoveListPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress);
        setupView(view);
        return view;
    }

    private void setupView(View view) {
        // Set the adapter
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        if (mColumnCount <= 1) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), mColumnCount));
        }
        mMoveRecyclerViewAdapter = new MoveRecyclerViewAdapter(new ArrayList<Move>(), getActivity());
        mRecyclerView.setAdapter(mMoveRecyclerViewAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        moveListPresenter.onResume();
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setItems(List<Move> items) {
        Collections.shuffle(items);
        mMoveRecyclerViewAdapter.setmValues(items);
        mMoveRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }
}

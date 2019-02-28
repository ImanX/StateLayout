package com.github.imanx;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ImanX.
 * sample | Copyrights 2019 ZarinPal Crop.
 */
public class StateLayout extends FrameLayout {


    private List<View> viewCache = new ArrayList<>();
    private View[] stateViews = new View[3];
    private int defaultState;
    private View currentView;


    private static final int VIEW_TAG = 1001;
    public static final int STATE_LOADING = 0;
    public static final int STATE_FAILURE = 1;
    public static final int STATE_EMPTY = 2;

    public StateLayout(@NonNull Context context) {
        super(context);
    }

    public StateLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setup(context, attrs);
    }

    public StateLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(context, attrs);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public StateLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setup(context, attrs);

    }


    private void setup(Context context, AttributeSet attributeSet) {
        TypedArray array = context.obtainStyledAttributes(attributeSet, R.styleable.StateLayout);
        int loadingLayout = array.getResourceId(R.styleable.StateLayout_statelayout_loading_view, 0);
        int emptyLayout = array.getResourceId(R.styleable.StateLayout_statelayout_empty_view, 0);
        int failureLayout = array.getResourceId(R.styleable.StateLayout_statelayout_failure_view, 0);
        defaultState = array.getInt(R.styleable.StateLayout_statelayout_default_view, -1);


        LayoutParams params = new FrameLayout.LayoutParams
                (
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
        params.gravity = Gravity.CENTER;


        if (loadingLayout != 0) {
            View view = View.inflate(context, loadingLayout, null);
            view.setLayoutParams(params);
            view.setTag(VIEW_TAG);
            this.stateViews[STATE_LOADING] = view;
        }

        if (emptyLayout != 0) {
            View view = View.inflate(context, emptyLayout, null);
            view.setLayoutParams(params);
            view.setTag(VIEW_TAG);
            this.stateViews[STATE_EMPTY] = view;
        }

        if (failureLayout != 0) {
            View view = View.inflate(context, failureLayout, null);
            view.setLayoutParams(params);
            view.setTag(VIEW_TAG);
            this.stateViews[STATE_FAILURE] = view;
        }


        array.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.i("TAG", "onFinishInflate " + defaultState);
        if (defaultState != -1) {
            removeViews();
            addView(stateViews[defaultState], 0);
        }


    }

    public void setState(State state) {
        removeViews();
        currentView = stateViews[state.ordinal()];
        addView(stateViews[state.ordinal()]);

    }

    public View getStateView(State state) {
        return stateViews[state.ordinal()];
    }

    @Nullable
    public View getCurrentStateView() {
        return this.currentView;
    }


    private void removeViews() {
        int children = getChildCount();
        while (children >= 0) {
            View view = getChildAt(children);
            this.viewCache.add(view);
            removeView(view);
            children--;
        }
    }


    public enum State {
        Loading,
        Failure,
        Empty,
    }


}

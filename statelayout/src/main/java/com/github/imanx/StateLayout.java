package com.github.imanx;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by ImanX.
 * sample | Copyrights 2019 ZarinPal Crop.
 */
public class StateLayout extends FrameLayout {


    private View[] stateViews = new View[3];
    private Animation animation = new Animation();
    private int defaultState;
    private boolean hasFadeAnimation;
    private long fadAnimationDuration;
    private View currentView;


    private static final int VIEW_TAG = 1001;
    public static final int STATE_LOADING = 0;
    public static final int STATE_FAILURE = 1;
    public static final int STATE_EMPTY = 2;
    private OnChangeStateViewListener listener;


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
        this.defaultState = array.getInt(R.styleable.StateLayout_statelayout_default_view, -1);
        this.hasFadeAnimation = array.getBoolean(R.styleable.StateLayout_statelayout_has_fade, true);
        this.fadAnimationDuration = (long) array.getFloat(R.styleable.StateLayout_statelayout_fade_duration, 500);


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

        if (isInEditMode()) {
            return;
        }


        if (defaultState != -1) {
            setState(State.Normal);
        }


    }

    public void setState(State state) {

        int positionOfState = state.ordinal();

        if (listener != null) {
            listener.onChangeState(stateViews[positionOfState], state, positionOfState);
        }

        if (state == State.Normal) {
            visibleContentView();
            return;
        }


        invisibleContentViews();
        currentView = stateViews[positionOfState];
        addView(currentView);

    }

    public View getStateView(State state) {
        return stateViews[state.ordinal()];
    }

    public void setOnChangeStateListener(OnChangeStateViewListener listener) {
        this.listener = listener;
    }

    @Nullable
    public View getCurrentStateView() {
        return this.currentView;
    }


    private void invisibleContentViews() {
        int children = getChildCount() - 1;
        while (children >= 0) {
            View view = getChildAt(children);
            view.setVisibility(GONE);
            children--;
        }
    }

    private void visibleContentView() {

        int children = getChildCount() - 1;
        while (children >= 0) {
            View view = getChildAt(children);

            if (view == null) {
                continue;
            }

            if ((view.getTag() != null) && (view.getTag() instanceof Integer) && ((int) view.getTag() == VIEW_TAG)) {
                removeView(view);
                continue;
            }


            if (hasFadeAnimation) {
                animation.run(view, fadAnimationDuration);
            }

            view.setVisibility(VISIBLE);
            children--;
        }
    }


    @Override
    public void addView(final View child) {
        if (hasFadeAnimation) {
            animation.run(child, fadAnimationDuration);
        }

        super.addView(child);
    }

    public enum State {
        Loading,
        Failure,
        Empty,
        Normal
    }


}

package com.meng.rippleeffectlayout;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class RippleEffectLayout extends FrameLayout {

    private final int DEFAULT_COLOR = Color.BLACK;
    private final int DEFAULT_ALPHA = 50;
    private final int DEFAULT_MIN_RADIUS = 100;
    private final int DEFAULT_ANIMTION_DURATION = 1500;

    private int mColor = DEFAULT_COLOR;
    private int mAlpha = DEFAULT_ALPHA;
    private float mMinRadius = 0;
    private float mMaxRadius;
    private boolean useCenter;

    private Paint mPaint;
    private float mDownX, mDownY;
    private ValueAnimator mValueAnimator;
    private float mAnimatorValue;
    private int mCenterX, mCenterY;
    private View mChildView;

    public RippleEffectLayout(@NonNull Context context) {
        super(context);
        init(null);
    }

    public RippleEffectLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public RippleEffectLayout(@NonNull Context context, @Nullable AttributeSet attrs
            , @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RippleEffectLayout(@NonNull Context context, @Nullable AttributeSet attrs
            , @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    public void setParams(Params params) {

        if (params.maxRadius == 0) params.maxRadius = DEFAULT_MIN_RADIUS * 10;

        if (params.alpha < 0f || params.alpha > 1f) {
            throw new IllegalArgumentException("color alpha must between 0 and 1");
        }
        if (params.minRadius > params.maxRadius) {
            throw new IllegalArgumentException("maxRadius must be bigger than minRaidus");
        }

        mColor = params.color;
        mAlpha = (int) (255 * params.alpha);
        mMinRadius = params.minRadius;
        mMaxRadius = params.maxRadius;
        useCenter = params.useCenter;
        if (params.childView != null) {
            mChildView = params.childView;
            addView(mChildView);
        }

    }

    private void init(AttributeSet attrs) {

        setWillNotDraw(false);

        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.RippleEffectLayout);
            Params params = new Params();
            params.color = typedArray.getColor(R.styleable.RippleEffectLayout_color, DEFAULT_COLOR);
            params.alpha = typedArray.getFloat(R.styleable.RippleEffectLayout_colorAlpha, DEFAULT_ALPHA);
            params.minRadius = typedArray.getDimension(R.styleable.RippleEffectLayout_minRadius, DEFAULT_MIN_RADIUS);
            params.maxRadius = typedArray.getDimension(R.styleable.RippleEffectLayout_maxRadius, 0);
            params.useCenter = typedArray.getBoolean(R.styleable.RippleEffectLayout_useCenter, false);
            setParams(params);
        }

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(mColor);
        mPaint.setAlpha(mAlpha);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mAnimatorValue != 0f) {
            if (useCenter) {
                if (mCenterX == 0) {
                    mCenterX = getMeasuredWidth() / 2;
                    mCenterY = getMeasuredHeight() / 2;
                }
                canvas.drawCircle(mCenterX, mCenterY, (mMaxRadius - mMinRadius) * mAnimatorValue, mPaint);
            } else {
                canvas.drawCircle(mDownX, mDownY, (mMaxRadius - mMinRadius) * mAnimatorValue, mPaint);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getChildCount() > 1)
            throw new IllegalArgumentException("RippleEffectLayout can have only one child");
        if (getChildCount() != 0) mChildView = getChildAt(0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!useCenter) {
                    mDownX = event.getX();
                    mDownY = event.getY();
                }
                startAnimation();
                return true;
            case MotionEvent.ACTION_UP:
                if (mValueAnimator != null)
                    mValueAnimator.cancel();
                mDownX = -1f;
                mDownY = -1f;
                mAnimatorValue = 0f;

                if (mChildView != null) {
                    mChildView.performClick();
                }
                postInvalidate();
                break;
        }
        return super.onTouchEvent(event);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    private void startAnimation() {
        mValueAnimator = ValueAnimator.ofFloat(0f, 1f);
        mValueAnimator.setDuration(DEFAULT_ANIMTION_DURATION);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatorValue = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        mValueAnimator.start();
    }

    public static class Builder {

        private Params params;
        private Context context;

        public Builder(Context context) {
            params = new Params();
            this.context = context;

        }

        public Builder color(int color) {
            params.color = color;
            return this;
        }

        public Builder alpha(float alpha) {
            params.alpha = alpha;
            return this;
        }

        public Builder minRadius(float radius) {
            params.minRadius = radius;
            return this;
        }

        public Builder maxRadius(float radius) {
            params.maxRadius = radius;
            return this;
        }

        public Builder useCenter(boolean useCenter) {
            params.useCenter = useCenter;
            return this;
        }

        public Builder childView(View view) {
            params.childView = view;
            return this;
        }

        public RippleEffectLayout create() {
            RippleEffectLayout rippleEffectLayout = new RippleEffectLayout(context);
            rippleEffectLayout.setParams(params);
            return rippleEffectLayout;
        }
    }

    private static class Params {
        public int color;
        public float alpha;
        public float minRadius;
        public float maxRadius;
        public boolean useCenter;
        public View childView;
    }
}

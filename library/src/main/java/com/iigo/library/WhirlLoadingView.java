package com.iigo.library;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

/**
 * @author SamLeung
 * @e-mail 729717222@qq.com
 * @github https://github.com/samlss
 * @csdn https://blog.csdn.net/Samlss
 * @description A loading view.
 */
public class WhirlLoadingView extends View {
    private final static int DEFAULT_DURATION = 1000;
    private final static int DEFAULT_SIZE = 150;
    private final static int DEFAULT_COLOR = Color.WHITE;

    private int mColor = DEFAULT_COLOR;
    private float mStrokeWidth;

    private Path mOuterArcPath;
    private Path mOuterArcDstPath;
    private PathMeasure mOuterArcPathMeasure;

    private Path mInnerArcPath;
    private Path mInnerArcDstPath;
    private PathMeasure mInnerArcPathMeasure;

    private Path mDstPath;

    private Paint mPaint;

    private float mCenterX;
    private float mCenterY;

    private ValueAnimator mValueAnimator;
    private long mAnimatorPlayTime;
    private float mAnimatedValue;

    private Matrix mMatrix;

    private TimeInterpolator mInterpolator = new AccelerateDecelerateInterpolator();
    private long mDuration = DEFAULT_DURATION;

    public WhirlLoadingView(Context context) {
        this(context, null);
    }

    public WhirlLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WhirlLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        parseAttr(attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WhirlLoadingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        parseAttr(attrs);
        init();
    }

    private void parseAttr(AttributeSet attrs){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.WhirlLoadingView);
        mColor = typedArray.getColor(R.styleable.WhirlLoadingView_loadingColor, DEFAULT_COLOR);
        mDuration = typedArray.getInteger(R.styleable.WhirlLoadingView_duration, DEFAULT_DURATION);

        if (mDuration < 0){
            mDuration = DEFAULT_DURATION;
        }

        int interpolatorValue = typedArray.getInt(R.styleable.WhirlLoadingView_interpolator, 0);

        switch (interpolatorValue){
            case 0:
                mInterpolator = new AccelerateDecelerateInterpolator();
                break;

            case 1:
                mInterpolator = new AccelerateInterpolator();
                break;

            case 2:
                mInterpolator = new DecelerateInterpolator();
                break;

            case 3:
                mInterpolator = new BounceInterpolator();
                break;

            case 4:
                mInterpolator = new CycleInterpolator(0.5f);
                break;

            case 5:
                mInterpolator = new LinearInterpolator();
                break;

            case 6:
                mInterpolator = new AnticipateOvershootInterpolator();
                break;

            case 7:
                mInterpolator = new AnticipateInterpolator();
                break;

            case 8:
                mInterpolator = new OvershootInterpolator();
                break;

            default:
                break;
        }

        typedArray.recycle();
    }

    private void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);

        mOuterArcPath = new Path();
        mOuterArcDstPath = new Path();
        mOuterArcPathMeasure = new PathMeasure();

        mInnerArcPath = new Path();
        mInnerArcDstPath = new Path();
        mInnerArcPathMeasure = new PathMeasure();

        mDstPath = new Path();
        mMatrix = new Matrix();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize  = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int mWidth = DEFAULT_SIZE;
        int mHeight = DEFAULT_SIZE;

        boolean isWidthWrap  = getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT;
        boolean isHeightWrap = getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT;

        if (!isWidthWrap && !isHeightWrap){
            return;
        }

        setMeasuredDimension(isWidthWrap ? mWidth : widthSize, isHeightWrap ? mHeight : heightSize);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        stop();

        mCenterX = w / 2;
        mCenterY = h / 2;

        int minSize = Math.min(w, h);

        mStrokeWidth = minSize / 9f;

        float outerRadius = (minSize - 2 * mStrokeWidth) / 2;
        float innerRadius = outerRadius / 2;

        mOuterArcPath.reset();
        mOuterArcDstPath.reset();
        mOuterArcPath.addCircle(mCenterX, mCenterY, outerRadius, Path.Direction.CW);
        mOuterArcPathMeasure.setPath(mOuterArcPath, false);
        mOuterArcPathMeasure.getSegment(0, 0.75f * mOuterArcPathMeasure.getLength(), mOuterArcDstPath, true);

        mInnerArcPath.reset();
        mInnerArcDstPath.reset();
        mInnerArcPath.addCircle(mCenterX, mCenterY, innerRadius, Path.Direction.CW);
        mInnerArcPathMeasure.setPath(mInnerArcPath, false);
        mInnerArcPathMeasure.getSegment(0, 0.5f * mInnerArcPathMeasure.getLength(), mInnerArcDstPath, true);

        mPaint.setStrokeWidth(mStrokeWidth);

        setupAnimator();
    }

    private void setupAnimator(){
        mValueAnimator = ValueAnimator.ofFloat(0, 1);
        mValueAnimator.setDuration(mDuration);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mAnimatedValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });

        mValueAnimator.setInterpolator(mInterpolator);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
        mValueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mMatrix.reset();
        mMatrix.setRotate(360 * mAnimatedValue, mCenterX, mCenterY);

        mDstPath.rewind();
        mInnerArcDstPath.transform(mMatrix, mDstPath);
        canvas.drawPath(mDstPath, mPaint);

        mMatrix.reset();
        mMatrix.setRotate(-360 * mAnimatedValue, mCenterX, mCenterY);

        mDstPath.rewind();
        mOuterArcDstPath.transform(mMatrix, mDstPath);
        canvas.drawPath(mDstPath, mPaint);
    }

    /**
     * Set the loading bar color.
     * */
    public void setColor(int color) {
        this.mColor = color;
        mPaint.setColor(color);
        postInvalidate();
    }

    /**
     * Get the loading bar color.
     * */
    public int getColor() {
        return mColor;
    }

    /**
     * Pause the animation.
     * */
    public void pause(){
        if (mValueAnimator != null && mValueAnimator.isRunning()){
            mAnimatorPlayTime = mValueAnimator.getCurrentPlayTime();
            mValueAnimator.cancel();
        }
    }

    /**
     * Resume the animation.
     * */
    public void resume(){
        if (mValueAnimator != null && !mValueAnimator.isRunning()){
            mValueAnimator.setCurrentPlayTime(mAnimatorPlayTime);
            mValueAnimator.start();
        }
    }

    /**
     * Start the animation.
     * */
    public void start(){
        mAnimatorPlayTime = 0;
        if (mValueAnimator != null){
            mValueAnimator.start();
        }
    }

    /**
     * Cancel the animation.
     * */
    public void stop(){
        if (mValueAnimator != null){
            mValueAnimator.cancel();
        }
    }

    /**
     * Release this view when you do not need it.
     * */
    public void release(){
        stop();
        if (mValueAnimator != null){
            mValueAnimator.removeAllUpdateListeners();
        }
    }
}

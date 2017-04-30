package com.veon.rx;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;


public class ColorfulTextview extends AppCompatTextView {
    TransitionDrawable crossfader;

    public ColorfulTextview(Context context) {
        super(context);

        init();
    }

    public ColorfulTextview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public ColorfulTextview(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }


    public void init() {
        initDrawable();

        Observable.interval(2, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(@NonNull Long aLong) throws Exception {
                crossfader.reverseTransition(500);
            }
        });
    }


    public void initDrawable() {
        Drawable backgrounds[] = new Drawable[2];
        backgrounds[0] = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{0xFFFF0000, 0xFF0000FF, 0xFFFFFF00});
        backgrounds[1] = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{0xFFFFFF00, 0xFF0000FF, 0xFFFF0000});
        crossfader = new TransitionDrawable(backgrounds);
        setBackgroundDrawable(crossfader);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = this.getMeasuredWidth();
        int height = this.getMeasuredHeight();
        int size = Math.max(width, height);
        int widthSpec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);
        int heightSpec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);
        super.onMeasure(widthSpec, heightSpec);
    }


}

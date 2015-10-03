package com.ducnd.demoanimtor;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

/**
 * Created by ducnd on 22/09/2015.
 */
public class CustomRelaytiveLayout extends RelativeLayout {
    private ViewTreeObserver.OnPreDrawListener preDrawListener = null;
    private float yFraction;
    private float yyTransition;
    public CustomRelaytiveLayout(Context context) {
        super(context);
    }

    public CustomRelaytiveLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRelaytiveLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setZoom( float zoom ) {
        setScaleX(zoom);
        setScaleY(zoom);
//        setPivotX(getWidth()/2);
//        setPivotY(getHeight() / 2);
    }

//    public void setTransitionYY( float transitionYY ) {
//        this.yyTransition = yyTransition;
//        if ( getHeight() == 0 ) {
//            if ( preDrawListener == null ) {
//                preDrawListener = new ViewTreeObserver.OnPreDrawListener() {
//                    @Override
//                    public boolean onPreDraw() {
//                        getViewTreeObserver().removeOnPreDrawListener(preDrawListener);
//                        setTransitionYY(yyTransition);
//                        return true;
//                    }
//                };
//                getViewTreeObserver().addOnPreDrawListener(preDrawListener);
//            }
//        }
//        float translationY = getHeight() * yyTransition;
//        setTranslationY(translationY);
//    }

    public void setYFraction(float fraction) {
        this.yFraction = fraction;
        if (getHeight() == 0) {
            if (preDrawListener == null) {
                preDrawListener = new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        getViewTreeObserver().removeOnPreDrawListener(
                                preDrawListener);
                        setYFraction(yFraction);
                        return true;
                    }
                };
                getViewTreeObserver().addOnPreDrawListener(preDrawListener);
            }
            return;
        }
        float translationY = getHeight() * fraction;
        setTranslationY(translationY);
    }

}

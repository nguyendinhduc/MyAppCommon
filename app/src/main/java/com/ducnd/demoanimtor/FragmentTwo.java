package com.ducnd.demoanimtor;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ducnd.myappcommon.R;

public class FragmentTwo extends Fragment implements View.OnClickListener {
    private static final String TAG = "FragmentTwo";
    private View view;
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
   }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_two, container, false);
        view.findViewById(R.id.txtContainer).setOnClickListener(this);
        AnimatorSet animator = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity().getBaseContext(), R.animator.animator_main_in);
        animator.setTarget(view);
//        animator.setDuration(700);

        AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity().getBaseContext(), R.animator.animator_main_out);
        animatorSet.setTarget(getActivity().findViewById(R.id.customImage));
        animatorSet.start();
        animator.start();

        return view;
    }


    @Override
    public void onClick(View v) {
        ((MainAcivityAnimator)getActivity()).showFragmentThree();
    }
    @Override
    public void onDestroyView() {
////        ViewGroup mContainer = (ViewGroup) getActivity().findViewById(android.R.id.content);
////        mContainer.removeAllViews();
//        AnimatorSet animator = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity().getBaseContext(), R.animator.animator_main_out);
//        animator.setTarget(view);
////        animator.setDuration(700);
//        animator.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
////                ViewGroup mContainer = (ViewGroup) getActivity().findViewById(android.R.id.content);
////                mContainer.removeAllViews();
////                ((MainAcivityAnimator) getActivity()).removeFragemtOne();
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });
//        animator.start();
////        ((MainAcivityAnimator)getActivity()).removeFragment(this);

        super.onDestroyView();
    }
}

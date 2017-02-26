package vn.com.phongnguyen93.readmee;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import vn.com.phongnguyen93.readmee.ui_view.SlidingUpPanelLayout;

/**
 * Created by phongnguyen on 2/26/17.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP) public class AnimationUtility {

  public static void enterRevealSlidingPanelAnim(final View view,final SlidingUpPanelLayout panel,
      final Context context) {

    Animator animator = ViewAnimationUtils.createCircularReveal(view,
        context.getResources().getDisplayMetrics().widthPixels / 2,
        context.getResources().getDisplayMetrics().heightPixels / 2, 0, view.getWidth());
    panel.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
    //view.setVisibility(View.INVISIBLE);

    animator.setDuration(700);
    animator.addListener(new AnimatorListenerAdapter() {
      @Override public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);
        view.setVisibility(View.VISIBLE);
      }
    });
    animator.start();
  }

  public static void exitRevealAnim(final View view, final boolean isVisible, View parentView,
      final AnimationEndCallback callback) {
    // previously visible view
    Context context = ReadMeeApplication.getAppContext();
    // get the center for the clipping circle
    int cx = view.getMeasuredWidth();
    int cy = view.getMeasuredHeight();

    // get the initial radius for the clipping circle
    int initialRadius = view.getWidth();

    // create the animation (the final radius is zero)
    Animator anim = ViewAnimationUtils.createCircularReveal(view,
        context.getResources().getDisplayMetrics().widthPixels / 2,
        context.getResources().getDisplayMetrics().heightPixels / 2, initialRadius,
        parentView.getWidth() / 2);

    // make the view invisible when the animation is done
    anim.addListener(new AnimatorListenerAdapter() {
      @Override public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);
        view.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
        callback.onAnimationEnd();
      }
    });
    anim.setDuration(550);
    // start the animation
    anim.start();
  }

  public interface AnimationEndCallback {
    void onAnimationEnd();
  }

  public static void stub(final View view, Context context, ViewGroup root,
      final AnimationEndCallback callback) {
    Transition transition = TransitionInflater.from(context)
        .inflateTransition(R.transition.changebounds_with_arcmotion);
    transition.setDuration(400);
    transition.addListener(new Transition.TransitionListener() {
      @Override public void onTransitionStart(Transition transition) {
      }

      @Override public void onTransitionEnd(Transition transition) {
        callback.onAnimationEnd();
      }

      @Override public void onTransitionCancel(Transition transition) {
      }

      @Override public void onTransitionPause(Transition transition) {

      }

      @Override public void onTransitionResume(Transition transition) {

      }
    });
    TransitionManager.beginDelayedTransition(root, transition);
    CoordinatorLayout.LayoutParams layoutParams =
        new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.WRAP_CONTENT,
            CoordinatorLayout.LayoutParams.WRAP_CONTENT);
    layoutParams.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
    view.setLayoutParams(layoutParams);
  }

  public static void stub2(final View view, Context context, ViewGroup root,
      final AnimationEndCallback callback) {
    Transition transition = TransitionInflater.from(context)
        .inflateTransition(R.transition.changebounds_with_arcmotion_back);
    transition.setDuration(600);
    transition.addListener(new Transition.TransitionListener() {
      @Override public void onTransitionStart(Transition transition) {
      }

      @Override public void onTransitionEnd(Transition transition) {
        callback.onAnimationEnd();
      }

      @Override public void onTransitionCancel(Transition transition) {
      }

      @Override public void onTransitionPause(Transition transition) {

      }

      @Override public void onTransitionResume(Transition transition) {

      }
    });
    TransitionManager.beginDelayedTransition(root, transition);
    CoordinatorLayout.LayoutParams layoutParams =
        new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.WRAP_CONTENT,
            CoordinatorLayout.LayoutParams.WRAP_CONTENT);
    layoutParams.gravity = Gravity.BOTTOM | Gravity.END;
    int margin = context.getResources().getDimensionPixelSize(R.dimen.space_large);
    layoutParams.setMargins(margin,margin,margin,margin);
    view.setLayoutParams(layoutParams);
  }
}

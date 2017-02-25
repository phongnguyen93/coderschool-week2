package vn.com.phongnguyen93.readmee;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.Gravity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.RelativeLayout;

/**
 * Created by phongnguyen on 2/26/17.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class AnimationUtility {

  public static void enterRevealAnim(final View view, final Context context) {
    int cx = view.getMeasuredWidth();
    int cy = view.getMeasuredHeight();
    int finalRadius = Math.max(view.getWidth(), view.getHeight());
    Animator animator =
        ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);

    animator.setDuration(800);
    animator.addListener(new AnimatorListenerAdapter() {
      @Override public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);
        view.setVisibility(View.VISIBLE);

      }
    });
    animator.start();
  }

  public static void exitRevealAnim(final View view) {
    // previously visible view

    // get the center for the clipping circle
    int cx = view.getMeasuredWidth() / 2;
    int cy = view.getMeasuredHeight() / 2;

    // get the initial radius for the clipping circle
    int initialRadius = view.getWidth();

    // create the animation (the final radius is zero)
    Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0);

    // make the view invisible when the animation is done
    anim.addListener(new AnimatorListenerAdapter() {
      @Override public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);
        view.setVisibility(View.INVISIBLE);
      }
    });
    anim.setDuration(500);
    // start the animation
    anim.start();
  }

  public static ObjectAnimator interpolatorAnim(boolean isOut,View view, Context context) {
    Path path = null;
    if(!isOut){
      path = new Path();
      path.moveTo(0.2f, 0.2f);
      path.lineTo(1f, 1f);
    }else{
      path.moveTo(1f, 1f);
      path.lineTo(0.2f, 0.2f);
    }

    // This ObjectAnimator uses the path to change the x and y scale of the mView object.
    ObjectAnimator animator = ObjectAnimator.ofFloat(view, View.SCALE_X, View.SCALE_Y, path);

    // Set the duration and interpolator for this animation
    animator.setDuration(750);
    animator.setInterpolator(new FastOutSlowInInterpolator());

    animator.start();
    return animator;
  }
  
  public static void moveView(boolean mTopLeft,final View view, final Context context){
    // Capture current location of button
    final int oldLeft = view.getLeft();
    final int oldTop = view.getTop();

    // Change layout parameters of button to move it

    CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) view.getLayoutParams();
      if (mTopLeft) {
        params.anchorGravity = Gravity.TOP|Gravity.START;
      } else {
        params.anchorGravity = Gravity.BOTTOM|Gravity.END;

      }
      view.setLayoutParams(params);
    // Add OnPreDrawListener to catch button after layout but before drawing
    view.getViewTreeObserver().addOnPreDrawListener(
        new ViewTreeObserver.OnPreDrawListener() {
          public boolean onPreDraw() {
            view.getViewTreeObserver().removeOnPreDrawListener(this);

            // Capture new location
            int left = view.getLeft();
            int top = view.getTop();
            int deltaX = left - oldLeft;
            int deltaY = top - oldTop;
            // Set up path to new location using a B�zier spline curve
            AnimatorPath path = new AnimatorPath();
            path.moveTo(-deltaX, -deltaY);
            path.curveTo(-(deltaX/2), -deltaY, 0, -deltaY/2, 0, 0);

            // Set up the animation
            final ObjectAnimator anim = ObjectAnimator.ofObject(
                context, "buttonLoc",
                new PathEvaluator(), path.getPoints().toArray());
            anim.setInterpolator(new DecelerateInterpolator());
            anim.start();
            return true;
          }
        });
  }

}

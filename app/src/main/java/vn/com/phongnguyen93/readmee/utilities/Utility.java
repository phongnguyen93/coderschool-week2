package vn.com.phongnguyen93.readmee.utilities;

import android.content.res.Resources;

/**
 * Created by phongnguyen on 2/27/17.
 */

public class Utility {
  public static int getStatusBarHeight(Resources r) {
    int resourceId = r.getIdentifier("status_bar_height", "dimen", "android");
    if (resourceId > 0)
      return r.getDimensionPixelSize(resourceId);

    return 0;
  }
}

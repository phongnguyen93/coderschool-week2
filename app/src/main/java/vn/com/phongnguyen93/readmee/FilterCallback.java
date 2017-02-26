package vn.com.phongnguyen93.readmee;

import java.util.ArrayList;
import java.util.Date;
import vn.com.phongnguyen93.readmee.models.ArticleQuery;

/**
 * Created by phongnguyen on 2/26/17.
 */

public final class FilterCallback {

  public static interface FilterSetDateCallback {
    void onDateSet(Date date);
  }

  public static interface FilterSetTagCallback {
    void onTagSet(ArrayList<String> tags);
  }

  public static interface FilterSubmitCallback{
    void onSubmitFilter(ArticleQuery articleQuery);
  }

  public static interface FilterInteractionCallback {
    void onChildViewInteract();
  }
}

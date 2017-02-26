package vn.com.phongnguyen93.readmee;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.lang.reflect.Field;
import javax.inject.Inject;
import retrofit2.Retrofit;
import vn.com.phongnguyen93.readmee.network.interfaces.ReadMeeEndpoint;

import static vn.com.phongnguyen93.readmee.R.dimen.abc_action_button_min_width_material;
import static vn.com.phongnguyen93.readmee.R.dimen.abc_action_button_min_width_overflow_material;

/**
 * Created by phongnguyen on 2/23/17.
 */

public abstract class BaseActivity extends AppCompatActivity {
  @Inject ReadMeeEndpoint endpoint;
  @Inject Retrofit retrofit;
  private Menu searchMenu;
  private Toolbar searchToolbar;
  private MenuItem menuItem;
  private SearchViewQueryCallback searchViewQueryCallback;

  protected interface SearchViewQueryCallback {
    void onQuery(String queryString);
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ReadMeeApplication.getmMainComponent().inject(this);
    ArticleRepository.getInstance().init(this, endpoint);
  }

  protected void setSearchViewQueryCallback(SearchViewQueryCallback callback) {
    this.searchViewQueryCallback = callback;
  }

  protected void setSearchtollbar(final Toolbar searchToolbar) {
    this.searchToolbar = searchToolbar;
    if (searchToolbar != null) {
      searchToolbar.inflateMenu(R.menu.menu_search);
      searchMenu = searchToolbar.getMenu();

      searchToolbar.setNavigationOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            circleReveal(R.id.search_toolbar, 1, true, false);
          } else {
            searchToolbar.setVisibility(View.GONE);
          }
        }
      });
      menuItem = searchMenu.findItem(R.id.action_filter_search);

      MenuItemCompat.setOnActionExpandListener(menuItem,
          new MenuItemCompat.OnActionExpandListener() {
            @Override public boolean onMenuItemActionCollapse(MenuItem item) {
              // Do something when collapsed
              if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                circleReveal(R.id.search_toolbar, 1, true, false);
              } else {
                searchToolbar.setVisibility(View.GONE);
              }
              return true;
            }

            @Override public boolean onMenuItemActionExpand(MenuItem item) {
              // Do something when expanded
              return true;
            }
          });

      initSearchView();
    } else {
      Log.d("toolbar", "setSearchtollbar: NULL");
    }
  }

  public void initSearchView() {
    final SearchView searchView =
        (SearchView) searchMenu.findItem(R.id.action_filter_search).getActionView();

    // Enable/Disable Submit button in the keyboard

    searchView.setSubmitButtonEnabled(false);

    // Change search close button image
    ImageView closeButton = (ImageView) searchView.findViewById(R.id.search_close_btn);
    closeButton.setImageResource(R.drawable.ic_close_black_24dp);

    ImageView backButton = (ImageView) searchView.findViewById(R.id.search_button);
    backButton.setImageResource(R.drawable.ic_arrow_back_black_24dp);

    // set hint and the text colors

    EditText txtSearch =
        ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));
    txtSearch.setHint("Search..");
    txtSearch.setHintTextColor(Color.DKGRAY);
    txtSearch.setTextColor(getResources().getColor(R.color.primary));

    // set the cursor

    AutoCompleteTextView searchTextView = (AutoCompleteTextView) searchView.findViewById(
        android.support.v7.appcompat.R.id.search_src_text);
    searchTextView.setHintTextColor(ContextCompat.getColor(this,R.color.primary));
    try {
      Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
      mCursorDrawableRes.setAccessible(true);
      mCursorDrawableRes.set(searchTextView,
          R.drawable.ic_search_black_24dp); //This sets the cursor resource ID to 0 or @null which will make it visible on white background
    } catch (Exception e) {
      e.printStackTrace();
    }

    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override public boolean onQueryTextSubmit(String query) {
        searchViewQueryCallback.onQuery(query);
        searchView.clearFocus();
        return true;
      }

      @Override public boolean onQueryTextChange(String newText) {
        return true;
      }
    });
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  protected void circleReveal(int viewID, int posFromRight, boolean containsOverflow,
      final boolean isShow) {
    final View myView = findViewById(viewID);

    int width = myView.getWidth();

    if (posFromRight > 0) {
      width -= (posFromRight * getResources().getDimensionPixelSize(
          abc_action_button_min_width_material)) - (getResources().getDimensionPixelSize(
          abc_action_button_min_width_material) / 2);
    }
    if (containsOverflow) {
      width -= getResources().getDimensionPixelSize(abc_action_button_min_width_overflow_material);
    }

    int cx = width;
    int cy = myView.getHeight() / 2;

    Animator anim;
    if (isShow) {
      anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, (float) width);
    } else {
      anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, (float) width, 0);
    }

    anim.setDuration((long) 400);

    // make the view invisible when the animation is done
    anim.addListener(new AnimatorListenerAdapter() {
      @Override public void onAnimationEnd(Animator animation) {
        if (!isShow) {
          super.onAnimationEnd(animation);
          myView.setVisibility(View.INVISIBLE);
        }
      }
    });

    // make the view visible and start the animation
    if (isShow) myView.setVisibility(View.VISIBLE);

    // start the animation
    anim.start();
  }


  protected void applyFontForToolbarTitle(Toolbar toolbar){
    for(int i = 0; i < toolbar.getChildCount(); i++){
      View view = toolbar.getChildAt(i);
      if(view instanceof TextView){
        TextView tv = (TextView) view;
        tv.setTextSize(24);
        Typeface titleFont = Typeface.
            createFromAsset(getAssets(), "Lobster.ttf");
        if(tv.getText().equals(toolbar.getTitle())){
          tv.setTypeface(titleFont);
          break;
        }
      }
    }
  }
  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_home, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    // Handle item selection
    switch (item.getItemId()) {
      case R.id.action_search:
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
          circleReveal(R.id.search_toolbar, 1, true, true);
        } else {
          searchToolbar.setVisibility(View.VISIBLE);
        }
        menuItem.expandActionView();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  protected abstract void onError();

  protected abstract void onLoading();

  protected abstract void onSuccess();

  protected abstract void onNetworkError();
}

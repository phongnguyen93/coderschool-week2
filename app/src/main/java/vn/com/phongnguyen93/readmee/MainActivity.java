package vn.com.phongnguyen93.readmee;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import vn.com.phongnguyen93.readmee.models.Article;
import vn.com.phongnguyen93.readmee.models.ArticleQuery;
import vn.com.phongnguyen93.readmee.ui_view.SlideUp;

public class MainActivity extends BaseActivity
    implements ArticleRepository.ArticleQueryCallback, BaseActivity.SearchViewQueryCallback {
  private static final int DEFAULT_ITEM_SPAN = 2;
  private static final int DEFAULT_SPACE_SIZE = 24;
  @BindView(R.id.list_article) RecyclerView listArticle;
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.search_toolbar) Toolbar searchToolbar;
  @BindView(R.id.list_layout) CoordinatorLayout listLayout;
  @BindView(R.id.filter_layout) RelativeLayout filterLayout;
  @BindView(R.id.dim) FrameLayout background;
  @BindView(R.id.fab_filter) FloatingActionButton fabFilter;

  private ArticleAdapter articleAdapter;
  private SlideUp slideUp;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    applyFontForToolbarTitle(toolbar);
    setSearchViewQueryCallback(this);
    setSearchtollbar(searchToolbar);
    setupRecyclerView();
    setupFilterView();
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  private void initUI() {
    AnimationUtility.enterRevealAnim(listLayout,this);
  }

  private void setupFilterView() {
    slideUp = new SlideUp.Builder(filterLayout).withListeners(new SlideUp.Listener() {
      @Override public void onSlide(float percent) {
        background.setAlpha(1 - (percent / 100));
      }

       @Override public void onVisibilityChanged(int visibility) {
        if (visibility == View.GONE) {
          fabFilter.show();
        }
      }
    })
        .withStartGravity(Gravity.BOTTOM)
        .withLoggingEnabled(true)
        .withGesturesEnabled(true)
        .withStartState(SlideUp.State.HIDDEN)
        .build();

    final boolean[] isMove = { false };
    fabFilter.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        //slideUp.show();
        //AnimationUtility.interpolatorAnim(false,listLayout,MainActivity.this);
        //fabFilter.hide();
        isMove[0] =!isMove[0];
        AnimationUtility.moveView(isMove[0],fabFilter,MainActivity.this);
      }
    });
  }

  private void setupRecyclerView() {
    articleAdapter = new ArticleAdapter(this);
    listArticle.setAdapter(articleAdapter);
    StaggeredGridLayoutManager staggeredGridLayoutManager =
        new StaggeredGridLayoutManager(DEFAULT_ITEM_SPAN, StaggeredGridLayoutManager.VERTICAL);
    staggeredGridLayoutManager.setGapStrategy(
        StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
    listArticle.setLayoutManager(staggeredGridLayoutManager);

    SpaceItemDecoration decoration = new SpaceItemDecoration(DEFAULT_SPACE_SIZE);
    listArticle.addItemDecoration(decoration);
  }

  @Override protected void onResume() {
    super.onResume();
    ArticleRepository.getInstance()
        .searchArticle(new ArticleQuery(null, null, ArticleQuery.SORT_NEWEST, 0, null), this);
  }

  @Override public void onQuerySuccess(ArrayList<Article> articles) {
    initUI();
    articleAdapter.setArticles(articles);
  }

  @Override public void onQueryFail() {

  }

  @Override public boolean onCreateOptionsMenu(final Menu menu) {
    return super.onCreateOptionsMenu(menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    return super.onOptionsItemSelected(item);
  }

  @Override protected void onError() {

  }

  @Override protected void onLoading() {

  }

  @Override protected void onSuccess() {

  }

  @Override public void onQuery(String queryString) {
    ArticleRepository.getInstance()
        .searchArticle(new ArticleQuery(null, null, ArticleQuery.SORT_NEWEST, 0, queryString),
            this);
  }
}

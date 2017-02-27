package vn.com.phongnguyen93.readmee.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import vn.com.phongnguyen93.readmee.ArticleRepository;
import vn.com.phongnguyen93.readmee.FilterCallback;
import vn.com.phongnguyen93.readmee.R;
import vn.com.phongnguyen93.readmee.ui_view.SpaceItemDecoration;
import vn.com.phongnguyen93.readmee.adapters.ArticleAdapter;
import vn.com.phongnguyen93.readmee.fragmennts.FilterFragment;
import vn.com.phongnguyen93.readmee.models.Article;
import vn.com.phongnguyen93.readmee.models.ArticleQuery;
import vn.com.phongnguyen93.readmee.ui_view.SlidingUpPanelLayout;
import vn.com.phongnguyen93.readmee.utilities.AnimationUtility;
import vn.com.phongnguyen93.readmee.utilities.NetworkReceiver;

public class HomeActivity extends BaseActivity
    implements ArticleRepository.ArticleQueryCallback, BaseActivity.SearchViewQueryCallback,
    FilterCallback.FilterSubmitCallback, FilterCallback.FilterInteractionCallback,ArticleAdapter.OnArticleClickCallback {
  private static final int DEFAULT_ITEM_SPAN = 2;
  private static final int DEFAULT_SPACE_SIZE = 24;
  @BindView(R.id.list_article) RecyclerView listArticle;
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.search_toolbar) Toolbar searchToolbar;
  @BindView(R.id.list_layout) CoordinatorLayout listLayout;
  @BindView(R.id.filter_layout) FrameLayout filterLayout;
  @BindView(R.id.fab_filter) FloatingActionButton fabFilter;
  @BindView(R.id.sliding_layout) SlidingUpPanelLayout slidingUpPanelLayout;
  @BindView(R.id.loading_layout) RelativeLayout loadingLayout;
  @BindView(R.id.error_layout) RelativeLayout errorLayout;
  @BindView(R.id.network_error_layout) RelativeLayout netErrorLayout;

  private ArticleAdapter articleAdapter;
  private String currentQueryString;
  private ArticleQuery currentQuery;
  private int currentPage;
  private NetworkReceiver networkReceiver;
  private boolean isMergeData;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    initUI();
    currentPage = 0;
  }

  private void initUI() {
    applyFontForToolbarTitle(toolbar);
    setSearchViewQueryCallback(this);
    setSearchtollbar(searchToolbar);
    setupRecyclerView();
    setupFilterView();
  }



  private void setupFilterView() {

    getSupportFragmentManager().beginTransaction()
        .replace(R.id.filter_layout,
            FilterFragment.newInstance(HomeActivity.this, HomeActivity.this))
        .commit();
    slidingUpPanelLayout.setAnchorPoint(0.5f);
    //slidingUpPanelLayout.setTouchEnabled(false);
    slidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
      @Override public void onPanelSlide(View panel, float slideOffset) {

      }

      @Override
      public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState,
          SlidingUpPanelLayout.PanelState newState) {
        if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
          fabFilter.show();
          slidingUpPanelLayout.setTouchEnabled(true);
        }
        if (previousState == SlidingUpPanelLayout.PanelState.EXPANDED) {
          slidingUpPanelLayout.setTouchEnabled(false);
        }
      }
    });

    fabFilter.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        AnimationUtility.stub(fabFilter, HomeActivity.this, listLayout,
            new AnimationUtility.AnimationEndCallback() {
              @Override public void onAnimationEnd() {
                AnimationUtility.enterRevealSlidingPanelAnim(filterLayout, slidingUpPanelLayout,
                    HomeActivity.this);
              }
            });
      }
    });
  }

  private void setupRecyclerView() {
    articleAdapter = new ArticleAdapter(this,this);
    listArticle.setAdapter(articleAdapter);
    StaggeredGridLayoutManager staggeredGridLayoutManager =
        new StaggeredGridLayoutManager(DEFAULT_ITEM_SPAN, StaggeredGridLayoutManager.VERTICAL);
    staggeredGridLayoutManager.setGapStrategy(
        StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
    listArticle.setLayoutManager(staggeredGridLayoutManager);

    SpaceItemDecoration decoration = new SpaceItemDecoration(DEFAULT_SPACE_SIZE);
    listArticle.addItemDecoration(decoration);

    listArticle.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int visibleThreshold = 4;

        if (dy >= 0) { // only when scrolling up
          StaggeredGridLayoutManager layoutManager =
              (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
          int totalItemCount = layoutManager.getItemCount();
          int lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPositions(null)[0];

          if (totalItemCount <= (lastVisibleItem + visibleThreshold)) {
            ++currentPage;
            currentQuery.setPage(currentPage);
            query(currentQuery);
          }
        }
      }
    });
  }

  private void query(ArticleQuery articleQuery) {
    currentQuery = articleQuery;
    ArticleRepository.getInstance().searchArticle(articleQuery, this);
  }

  @Override protected void onResume() {
    super.onResume();
    onLoading();
    query(new ArticleQuery(null, null, ArticleQuery.SORT_NEWEST, 0, null));
  }

  @Override public void onQuerySuccess(ArrayList<Article> articles) {
    onSuccess();
    articleAdapter.setArticles(articles, isMergeData);
    isMergeData = true;
  }

  @Override public void onQueryEmptyResult() {
    onError();
  }

  @Override public void onQueryFail() {
    if (!isMergeData) onError();
  }

  @Override public boolean onCreateOptionsMenu(final Menu menu) {
    return super.onCreateOptionsMenu(menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    return super.onOptionsItemSelected(item);
  }

  @Override protected void onError() {
    errorLayout.setVisibility(View.VISIBLE);
    listLayout.setVisibility(View.GONE);
    loadingLayout.setVisibility(View.GONE);
    netErrorLayout.setVisibility(View.GONE);
  }

  @Override protected void onLoading() {
    errorLayout.setVisibility(View.GONE);
    listLayout.setVisibility(View.GONE);
    loadingLayout.setVisibility(View.VISIBLE);
    netErrorLayout.setVisibility(View.GONE);
  }

  @Override protected void onSuccess() {
    errorLayout.setVisibility(View.GONE);
    listLayout.setVisibility(View.VISIBLE);
    loadingLayout.setVisibility(View.GONE);
    netErrorLayout.setVisibility(View.GONE);
  }

  @Override protected void onNetworkError() {
    errorLayout.setVisibility(View.GONE);
    listLayout.setVisibility(View.GONE);
    loadingLayout.setVisibility(View.GONE);
    netErrorLayout.setVisibility(View.VISIBLE);
  }


  @Override public void onQuery(String queryString) {
    isMergeData = false;
    currentQueryString = queryString;
    currentQuery.setQuery(currentQueryString);
    currentQuery.setPage(0);
    currentQuery.setFilterQuery(null);
    query(currentQuery);
    onLoading();
  }

  @Override public void onSubmitFilter(final ArticleQuery articleQuery) {
    AnimationUtility.exitRevealAnim(filterLayout, true, fabFilter,
        new AnimationUtility.AnimationEndCallback() {
          @Override public void onAnimationEnd() {
            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            AnimationUtility.stub2(fabFilter, HomeActivity.this, listLayout,
                new AnimationUtility.AnimationEndCallback() {
                  @Override public void onAnimationEnd() {
                    if (articleQuery != null) {
                      isMergeData = false;
                      articleQuery.setQuery(currentQueryString);
                      query(articleQuery);
                      onLoading();
                    }
                  }
                });
          }
        });
  }

  @Override public void onChildViewInteract() {
    slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
  }

  @Override public void onConnected() {
    query(currentQuery);
    isMergeData = true;
  }

  @Override public void onDisconnected() {
    onNetworkError();
    isMergeData = false;
  }

  @Override public void onClick(Article article) {
    openWebViewDetail(article);
  }

  private void openWebViewDetail(Article article) {
    Intent t = new Intent(this,ArticleDetailActivity.class);
    t.putExtra(ArticleDetailActivity.ARTICLE_KEY,article);
    startActivity(t);
  }
}

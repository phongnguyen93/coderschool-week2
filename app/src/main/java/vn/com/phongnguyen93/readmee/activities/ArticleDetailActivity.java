package vn.com.phongnguyen93.readmee.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import vn.com.phongnguyen93.readmee.R;
import vn.com.phongnguyen93.readmee.models.Article;
import vn.com.phongnguyen93.readmee.ui_view.ObservableWebView;

/**
 * Created by phongnguyen on 2/27/17.
 */
public class ArticleDetailActivity extends BaseActivity {
  public static final String ARTICLE_KEY = "article";
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.wv_content) ObservableWebView wvDetail;
  @BindView(R.id.loading_layout) RelativeLayout loadingLayout;
  @BindView(R.id.error_layout) RelativeLayout errorLayout;
  @BindView(R.id.network_error_layout) RelativeLayout netErrorLayout;

  private static final String TAG = ArticleDetailActivity.class.getSimpleName();
  private Article currentArticle;
  private String articleURL;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_article_detail);
    ButterKnife.bind(this);

    if (getIntent() != null) {
      currentArticle = getIntent().getParcelableExtra(ARTICLE_KEY);
      articleURL = currentArticle.getWeb_url();
      if (currentArticle.getHeadline() != null && currentArticle.getHeadline().getMain() != null) {
        toolbar.setTitle(currentArticle.getHeadline().getMain());
      }
    }

    initUI();
  }

  private void initUI() {
    setupToolbar();
    setupWebView();
  }

  private void setupToolbar() {
    setSupportActionBar(toolbar);
    toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_black_24dp));
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        onBackPressed();
      }
    });
    toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.accent));
    toolbar.setTitle(currentArticle.getHeadline().getMain());
    toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.primary));
  }

  private void showSystemUI() {
    runOnUiThread(new Runnable() {
      public void run() {
        toolbar.animate()
            .translationY(0)
            .setInterpolator(new DecelerateInterpolator())
            .setDuration(300)
            .start();
        toolbar.setVisibility(View.VISIBLE);
      }
    });
  }

  private void hideSystemUI() {
    runOnUiThread(new Runnable() {
      public void run() {
        toolbar.animate()
            .translationY(-toolbar.getHeight())
            .setInterpolator(new AccelerateInterpolator())
            .setDuration(240)
            .start();
        toolbar.setVisibility(View.GONE);
      }
    });
  }

  private void setupWebView() {
    wvDetail.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    wvDetail.getSettings().setJavaScriptEnabled(true);
    wvDetail.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
    wvDetail.setWebViewClient(new WebViewClient() {
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
      }

      public void onPageFinished(WebView view, String url) {
        onSuccess();
      }

      public void onReceivedError(WebView view, int errorCode, String description,
          String failingUrl) {
        onError();
      }
    });
    wvDetail.setOnScrollChangedCallback(new ObservableWebView.OnScrollChangedCallback() {
      @Override public void onScroll(int l, int t) {
        if (wvDetail.getScrollY() == 0) {
          showSystemUI();
        } else {
          hideSystemUI();
        }
      }
    });
  }

  @Override protected void onError() {
    errorLayout.setVisibility(View.VISIBLE);
    wvDetail.setVisibility(View.GONE);
    loadingLayout.setVisibility(View.GONE);
    netErrorLayout.setVisibility(View.GONE);
  }

  @Override protected void onLoading() {
    errorLayout.setVisibility(View.GONE);
    wvDetail.setVisibility(View.GONE);
    loadingLayout.setVisibility(View.VISIBLE);
    netErrorLayout.setVisibility(View.GONE);
  }

  @Override protected void onSuccess() {
    errorLayout.setVisibility(View.GONE);
    wvDetail.setVisibility(View.VISIBLE);
    loadingLayout.setVisibility(View.GONE);
    netErrorLayout.setVisibility(View.GONE);
  }

  @Override protected void onNetworkError() {
    errorLayout.setVisibility(View.GONE);
    wvDetail.setVisibility(View.GONE);
    loadingLayout.setVisibility(View.GONE);
    netErrorLayout.setVisibility(View.VISIBLE);
  }

  @Override public void onConnected() {
    if (articleURL != null) wvDetail.loadUrl(articleURL);
  }

  @Override public void onDisconnected() {
    onNetworkError();
  }

  @Override protected void onResume() {
    super.onResume();
  }

  @Override protected void onPause() {
    super.onPause();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_detail, menu);
    MenuItem item = menu.findItem(R.id.action_share);

    return super.onCreateOptionsMenu(menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.action_mail) {
      sendEmail();
    } else if (item.getItemId() == R.id.action_share) {
      shareLink();
    }
    return true;
  }

  private void shareLink() {
    Intent shareIntent = new Intent();
    shareIntent.setAction(Intent.ACTION_SEND);
    shareIntent.setType("text/plain");
    shareIntent.putExtra(Intent.EXTRA_TEXT, articleURL);
    startActivity(Intent.createChooser(shareIntent, "Share link..."));
  }

  private void sendEmail() {
    Intent i = new Intent(Intent.ACTION_SEND);
    i.setType("message/rfc822");
    i.putExtra(Intent.EXTRA_EMAIL, new String[] { "phongnguyen180993@gmail.com" });
    i.putExtra(Intent.EXTRA_SUBJECT, currentArticle.getHeadline().getMain());
    i.putExtra(Intent.EXTRA_TEXT, articleURL);
    try {
      startActivity(Intent.createChooser(i, "Send mail..."));
    } catch (android.content.ActivityNotFoundException ex) {
      Toast.makeText(ArticleDetailActivity.this, "There are no email clients installed.",
          Toast.LENGTH_SHORT).show();
    }
  }
}
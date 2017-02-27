package vn.com.phongnguyen93.readmee;

import android.content.Context;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.phongnguyen93.readmee.models.ApiResponse;
import vn.com.phongnguyen93.readmee.models.Article;
import vn.com.phongnguyen93.readmee.models.ArticleQuery;
import vn.com.phongnguyen93.readmee.network.interfaces.ReadMeeEndpoint;

/**
 * Created by phongnguyen on 2/23/17.
 */

public class ArticleRepository {
  private static ArticleRepository instance;
  private ReadMeeEndpoint endpoint;
  private Context context;

  private Call<ApiResponse> searchApiCall;

  public static ArticleRepository getInstance() {
    if (instance == null) {
      synchronized (ArticleRepository.class) {
        if (instance == null) {
          instance = new ArticleRepository();
        }
      }
    }
    return instance;
  }

  public void init(Context context, ReadMeeEndpoint endpoint) {
    this.context = context;
    this.endpoint = endpoint;
  }

  public interface ArticleQueryCallback {
    void onQuerySuccess(ArrayList<Article> articles);
    void onQueryEmptyResult();
    void onQueryFail();
  }

  public void searchArticle(ArticleQuery articleQuery, final ArticleQueryCallback callback) {
    if (articleQuery != null && searchApiCall == null) {
      endpoint.searchArticle(context.getString(R.string.api_key), articleQuery.getQuery(),
          articleQuery.getBeginDate(), articleQuery.getFilterQuery(), articleQuery.getSort(),
          articleQuery.getPage()).enqueue(new Callback<ApiResponse>() {
        @Override public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
          if (response.isSuccessful()
              && response.body() != null
              && response.body().getResponse() != null
              && response.body().getResponse().getDocs() != null
              && response.body().getResponse().getDocs().size() > 0) {
            callback.onQuerySuccess(response.body().getResponse().getDocs());
          } else {
            callback.onQueryEmptyResult();
          }
        }

        @Override public void onFailure(Call<ApiResponse> call, Throwable t) {
          callback.onQueryFail();
        }
      });
    }
  }
}

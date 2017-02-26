package vn.com.phongnguyen93.readmee.network.interfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import vn.com.phongnguyen93.readmee.models.ApiResponse;
import vn.com.phongnguyen93.readmee.models.ResponseContent;

public interface ReadMeeEndpoint {

  @GET("articlesearch.json") Call<ApiResponse> searchArticle(@Query("api-key") String apiKey,
      @Query("q") String queryString, @Query("begin_date") String beginDate,
      @Query("fq") String filterString, @Query("sort") String sort, @Query("page") int page);
}
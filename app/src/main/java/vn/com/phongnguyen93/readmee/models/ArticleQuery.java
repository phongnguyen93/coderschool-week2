package vn.com.phongnguyen93.readmee.models;

import android.support.annotation.Nullable;

/**
 * Created by phongnguyen on 2/23/17.
 */

public class ArticleQuery {
  public static final String SORT_NEWEST = "newest";
  public static final String SORT_OLDEST = "oldest";

  private String begin_date;
  private String end_date;
  private String sort;
  private int page;
  private String query;

  public ArticleQuery(@Nullable String begin_date, @Nullable String end_date, String sort, int page,
      String query) {
    this.setBegin_date(begin_date);
    this.setEnd_date(end_date);
    this.setSort(sort);
    this.setPage(page);
    this.setQuery(query);
  }

  public String getBeginDate() {
    return begin_date;
  }

  public void setBegin_date(String begin_date) {
    this.begin_date = begin_date;
  }

  public String getEndDate() {
    return end_date;
  }

  public void setEnd_date(String end_date) {
    this.end_date = end_date;
  }

  public String getSort() {
    return sort;
  }

  public void setSort(String sort) {
    this.sort = sort;
  }

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public String getQuery() {
    return query;
  }

  public void setQuery(String query) {
    this.query = query;
  }
}

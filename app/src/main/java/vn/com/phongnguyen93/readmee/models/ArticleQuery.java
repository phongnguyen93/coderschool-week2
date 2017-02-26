package vn.com.phongnguyen93.readmee.models;

import android.support.annotation.IdRes;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;


/**
 * Created by phongnguyen on 2/23/17.
 */

public class ArticleQuery {


  public static final String SORT_NEWEST = "newest";
  public static final String SORT_OLDEST = "oldest";
  @Retention(SOURCE)
  @StringDef({
      SORT_OLDEST,
      SORT_NEWEST
  })
  public @interface ArticleSortType{};

  private String begin_date;
  private String fq;
  private String sort;
  private int page;
  private String query;

  public ArticleQuery(@Nullable String begin_date, @Nullable String fq,@ArticleSortType String sort, int page,
      String query) {
    this.setBegin_date(begin_date);
    this.setFilterQuery(fq);
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

  public String getFilterQuery() {
    return fq;
  }

  public void setFilterQuery(String fq) {
    this.fq = fq;
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

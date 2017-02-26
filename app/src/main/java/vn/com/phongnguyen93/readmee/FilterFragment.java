package vn.com.phongnguyen93.readmee;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SwitchCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import vn.com.phongnguyen93.readmee.adapters.FragmentAdapter;
import vn.com.phongnguyen93.readmee.models.ArticleQuery;

/**
 * Created by phongnguyen on 2/26/17.
 */
public class FilterFragment extends Fragment
    implements FilterCallback.FilterSetDateCallback, FilterCallback.FilterSetTagCallback {
  @BindView(R.id.tab_layout) SmartTabLayout tabLayout;
  @BindView(R.id.view_pager) ViewPager viewPager;
  @BindView(R.id.tv_begin_date) TextView tvBeginDate;
  @BindView(R.id.tv_tags) TextView tvTags;
  @BindView(R.id.switch_sort_by) SwitchCompat switchSortBy;
  @BindView(R.id.btn_close) ImageButton btnClose;
  @BindView(R.id.btn_submit) ImageButton btnSubmit;
  private static FilterCallback.FilterSubmitCallback submitCallback;
  private static FilterCallback.FilterInteractionCallback filterInteractionCallback;
  private String beginDate;
  private String selectedTags = "news_desk:(";
  SimpleDateFormat queryFormat = new SimpleDateFormat("yyyyMMdd");
  SimpleDateFormat displayFormat = new SimpleDateFormat("dd/MM/yyyy");

  private static final String TAG = FilterFragment.class.getSimpleName();

  public static FilterFragment newInstance(FilterCallback.FilterSubmitCallback callback,
      FilterCallback.FilterInteractionCallback callback1) {
    submitCallback = callback;
    filterInteractionCallback = callback1;
    FilterFragment fragment = new FilterFragment();
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  /**
   * Change the null parameter in {@code inflater.inflate()}
   * to a layout resource {@code R.layout.example}
   */
  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.filter_layout, container, false);
    ButterKnife.bind(this, rootView);

    FragmentAdapter adapter = new FragmentAdapter(getChildFragmentManager(), new Fragment[] {
        FilterTagViewFragment.newInstance(this, filterInteractionCallback),
        FilterDatePickFragment.newInstance(this, filterInteractionCallback)
    });
    viewPager.setAdapter(adapter);
    tabLayout.setViewPager(viewPager);

    btnClose.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        submitCallback.onSubmitFilter(null);
      }
    });

    btnSubmit.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        submitCallback.onSubmitFilter(new ArticleQuery(beginDate,selectedTags,
            switchSortBy.isChecked() ? ArticleQuery.SORT_NEWEST : ArticleQuery.SORT_OLDEST, 0,
            null));
      }
    });

    getDefaultFilter();
    return rootView;
  }

  private void getDefaultFilter() {
    tvTags.setText(Html.fromHtml(String.format(getString(R.string.tags_text), "All Categories")));
    tvBeginDate.setText(
        Html.fromHtml(String.format(getString(R.string.date_text), displayFormat.format(new Date()))));
  }

  @Override public void onResume() {
    super.onResume();
  }

  @Override public void onStop() {
    super.onStop();
  }

  @Override public void onDateSet(Date date) {

    beginDate = queryFormat.format(date);
    tvBeginDate.setText(
        Html.fromHtml(String.format(getString(R.string.date_text), displayFormat.format(date))));
  }

  @Override public void onTagSet(ArrayList<String> tags) {
    String tagString = "";
    for (int i = 0; i < tags.size(); i++) {

      if (i == 0) {
        tagString = tagString.concat(tags.get(i));
      } else {
        tagString = tagString.concat(", " + tags.get(i));
      }
    }
    tvTags.setText(Html.fromHtml(String.format(getString(R.string.tags_text), tagString)));
    selectedTags = selectedTags.concat(tagString)+")";
    selectedTags = selectedTags.replace(',',' ');
  }
}
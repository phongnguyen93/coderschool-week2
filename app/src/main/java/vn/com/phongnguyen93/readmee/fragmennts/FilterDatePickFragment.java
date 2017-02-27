package vn.com.phongnguyen93.readmee.fragmennts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.squareup.timessquare.CalendarPickerView;
import java.util.Calendar;
import java.util.Date;
import vn.com.phongnguyen93.readmee.FilterCallback;
import vn.com.phongnguyen93.readmee.R;

/**
 * Created by phongnguyen on 2/26/17.
 */
public class FilterDatePickFragment extends Fragment {

  @BindView(R.id.calendar_view) CalendarPickerView calendarView;

  private static final String TAG = FilterDatePickFragment.class.getSimpleName();
  private static FilterCallback.FilterSetDateCallback filterSetDateCallback;
  private static FilterCallback.FilterInteractionCallback interactionCallback;

  public static FilterDatePickFragment newInstance(FilterCallback.FilterSetDateCallback callback,
      FilterCallback.FilterInteractionCallback mInteractionCallback) {
    filterSetDateCallback = callback;
    interactionCallback = mInteractionCallback;
    FilterDatePickFragment fragment = new FilterDatePickFragment();
    return fragment;
  }


  @Override public void onCreate(Bundle savedInstanceState) {
    Log.d(TAG, "onCreate: hit");
    super.onCreate(savedInstanceState);
  }

  /**
   * Change the null parameter in {@code inflater.inflate()}
   * to a layout resource {@code R.layout.example}
   */
  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    Log.d(TAG, "onCreateView: hit");
    View rootView = inflater.inflate(R.layout.filter_datepicker_layout, container, false);
    ButterKnife.bind(this, rootView);

    Calendar nextYear = Calendar.getInstance();
    nextYear.add(Calendar.MONTH,1);

    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR,1900);


    Date today = new Date();

    calendarView.init(calendar.getTime(), nextYear.getTime()).withSelectedDate(today);
    calendarView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
      @Override public void onDateSelected(Date date) {
        if(FilterDatePickFragment.this.isVisible()){
          interactionCallback.onChildViewInteract();
        }
        filterSetDateCallback.onDateSet(date);
      }

      @Override public void onDateUnselected(Date date) {

      }
    });
    calendarView.setOnScrollListener(new AbsListView.OnScrollListener() {
      @Override public void onScrollStateChanged(AbsListView absListView, int i) {
          if(i == SCROLL_STATE_FLING || i == SCROLL_STATE_TOUCH_SCROLL){

              interactionCallback.onChildViewInteract();
          }
      }

      @Override public void onScroll(AbsListView absListView, int i, int i1, int i2) {

      }
    });
    return rootView;
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {

    super.onActivityCreated(savedInstanceState);
  }

  @Override public void onResume() {
    Log.d(TAG, "onResume: hit");
    super.onResume();
  }

  @Override public void onPause() {
    Log.d(TAG, "onPause: hit");
    super.onPause();
  }

  @Override public void onDestroyView() {
    Log.d(TAG, "onDestroyView: hit");
    super.onDestroyView();
  }

  @Override public void onDestroy() {
    Log.d(TAG, "onDestroy: hit");
    super.onDestroy();
  }
}
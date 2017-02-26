package vn.com.phongnguyen93.readmee;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.Arrays;
import vn.com.phongnguyen93.readmee.adapters.TagAdapter;

/**
 * Created by phongnguyen on 2/26/17.
 */
public class FilterTagViewFragment extends Fragment implements TagAdapter.TagSelectCallback {
  @BindView(R.id.rv_tags) RecyclerView rvTagList;
  private static FilterCallback.FilterSetTagCallback setTagCallback;
  private static final String TAG = FilterTagViewFragment.class.getSimpleName();
  private TagAdapter adapter;
  private boolean isVisible = false;
  private static FilterCallback.FilterInteractionCallback filterInteractionCallback;
  private final String[] tags = {
      "Cars", "Culture", "Arts", "Books", "Dining", "Business", "Education", "Environment",
      "Fashion", "Financial", "Food", "Health", "Jobs", "Magazine", "Media", "Movie", "National",
      "Museum", "Politics", "Science", "Society", "Technology", "Weather", "World", "Regionals",
      "Opinion", "Flight", "Market Place", "Foreign", "Retail", "Retirement", "Travel", "Universal",
      "Women's Health", "Men's Health", "Blogs", "Entrepreneurs"
  };

  public static FilterTagViewFragment newInstance(FilterCallback.FilterSetTagCallback callback,
      FilterCallback.FilterInteractionCallback callback1) {
    setTagCallback = callback;
    filterInteractionCallback = callback1;
    FilterTagViewFragment fragment = new FilterTagViewFragment();
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
    View rootView = inflater.inflate(R.layout.filter_tagview_layout, container, false);
    ButterKnife.bind(this, rootView);
    ArrayList<String> tagList = new ArrayList<String>(Arrays.asList(tags));
    adapter = new TagAdapter(getContext(), tagList,this);
    rvTagList.setAdapter(adapter);
    StaggeredGridLayoutManager staggeredGridLayoutManager =
        new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
    staggeredGridLayoutManager.setGapStrategy(
        StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
    rvTagList.setLayoutManager(staggeredGridLayoutManager);

    SpaceItemDecoration decoration = new SpaceItemDecoration(16);
    rvTagList.addItemDecoration(decoration);
    rvTagList.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if(newState == RecyclerView.SCROLL_STATE_DRAGGING){
          filterInteractionCallback.onChildViewInteract();
        }
      }
    });
    return rootView;
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    Log.d(TAG, "onActivityCreated: hit");
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

  @Override public void onTagSelected(ArrayList<String> tags) {
    if(FilterTagViewFragment.this.isVisible()){
      filterInteractionCallback.onChildViewInteract();
    }
    setTagCallback.onTagSet(tags);
  }

  @Override
  public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);
    if (isVisibleToUser) {
      isVisible = true;
    }
    else {
      isVisible = false;
    }
  }
}
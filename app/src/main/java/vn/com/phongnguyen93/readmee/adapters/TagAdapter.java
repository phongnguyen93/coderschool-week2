package vn.com.phongnguyen93.readmee.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.List;
import vn.com.phongnguyen93.readmee.R;

/**
 * Created by phongnguyen on 2/26/17.
 */
public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {

  private static final String TAG = TagAdapter.class.getSimpleName();

  private Context mContext;
  private ArrayList<String> mData;
  private ArrayList<String> selectedTags;
  private TagSelectCallback callback;

  public interface TagSelectCallback {
    void onTagSelected(ArrayList<String> tags);
  }

  /**
   * Change {@link List} type according to your needs
   */
  public TagAdapter(Context context, ArrayList<String> data, TagSelectCallback callback) {
    if (context == null) {
      throw new NullPointerException("context can not be NULL");
    }

    if (data == null) {
      throw new NullPointerException("data list can not be NULL");
    }

    if (callback == null) {
      throw new NullPointerException("select item callback can not be NULL");
    }

    this.callback = callback;
    this.mContext = context;
    this.mData = data;
    selectedTags = new ArrayList<>(5);
  }

  private void toggleSelected(View v, boolean isToggle) {
    if (v != null) {
      v.setSelected(isToggle);
    }
  }

  /**
   * Change the null parameter to a layout resource {@code R.layout.example}
   */
  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_item_layout, parent, false);
    return new ViewHolder(view);
  }

  @Override public void onBindViewHolder(final ViewHolder holder, final int position) {
    holder.tvTag.setText(mData.get(position));
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        if (selectedTags != null) {

          if (selectedTags.contains(mData.get(position))) {
            selectedTags.remove(mData.get(position));
            toggleSelected(holder.tvTag, false);
          } else {
            if (selectedTags.size() < 5) {
              selectedTags.add(mData.get(position));
              toggleSelected(holder.tvTag, true);
            } else {
              Toast.makeText(mContext, "Select only 5 tags!", Toast.LENGTH_SHORT).show();
            }
          }
          callback.onTagSelected(selectedTags);
        }
      }
    });
  }

  @Override public int getItemCount() {
    return mData.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_tag) TextView tvTag;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
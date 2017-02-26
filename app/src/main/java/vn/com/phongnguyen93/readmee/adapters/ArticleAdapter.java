package vn.com.phongnguyen93.readmee.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import vn.com.phongnguyen93.readmee.R;
import vn.com.phongnguyen93.readmee.models.Article;
import vn.com.phongnguyen93.readmee.models.Multimedia;

/**
 * Created by phongnguyen on 2/24/17.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {
  public static final int COMPACT_VIEW_TYPE = 0;
  public static final int FULL_VIEW_TYPE = 1;
  public static final int FOOTER_VIEW_TYPE = 2;

  private ArrayList<Article> articles;
  private Context context;

  public void setArticles(ArrayList<Article> data, boolean isMergeData) {
    if (data != null && data.size() > 0) {
      if (isMergeData) {
        if (this.articles == null) this.articles = new ArrayList<>();
        articles.addAll(articles.size(), data);
      } else {
        articles = data;
      }
      articles.add(new Article());
      notifyDataSetChanged();
    }
  }

  private int detectViewType(int mediaContentCount, int position) {
    if (position == articles.size() - 1) return FOOTER_VIEW_TYPE;

    if (mediaContentCount > 0) {
      if (mediaContentCount > 1) {
        return FULL_VIEW_TYPE;
      } else {
        return COMPACT_VIEW_TYPE;
      }
    } else {
      return COMPACT_VIEW_TYPE;
    }
  }

  private String getImageURL(ArrayList<Multimedia> multimediaArrayList) {
    String imageURL = "";
    switch (multimediaArrayList.size()) {
      case 1:
        imageURL = multimediaArrayList.get(0).getUrl();
        break;
      case 2:
      case 3:
        for (Multimedia multimedia : multimediaArrayList) {
          if (multimedia.getSubtype().equals(Multimedia.SUBTYPE_XLARGE) || multimedia.getSubtype()
              .equals(Multimedia.SUBTYPE_LARGE) || multimedia.getSubtype()
              .equals(Multimedia.SUBTYPE_WIDE)) {
            imageURL = multimedia.getUrl();
          }
        }
        break;
    }
    return imageURL;
  }

  @Override public int getItemViewType(int position) {
    return detectViewType(articles.get(position).getMultimedia() != null ? articles.get(position)
        .getMultimedia()
        .size() : 0, position);
  }

  public ArticleAdapter(Context context) {
    this.context = context;
  }

  @Override public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v;
    if (viewType == COMPACT_VIEW_TYPE) {
      v = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.article_compact_item_layout, parent, false);
    } else if (viewType == FULL_VIEW_TYPE) {
      v = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.article_full_item_layout, parent, false);
    } else {
      v = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.article_footer_item_layout, parent, false);
    }
    return new ArticleViewHolder(v, context);
  }

  @Override public void onBindViewHolder(ArticleViewHolder holder, int position) {
    if (getItemViewType(position) != FOOTER_VIEW_TYPE) {
      holder.bindItem(articles.get(position));
    }
  }

  @Override public int getItemCount() {
    return articles != null ? articles.size() : 0;
  }

  public class ArticleViewHolder extends RecyclerView.ViewHolder {
    @Nullable @BindView(R.id.tv_headline) TextView tvHeadline;
    @Nullable @BindView(R.id.tv_snippet) TextView tvSnippet;
    @Nullable @BindView(R.id.img_cover) ImageView imgCover;

    public ArticleViewHolder(View itemView, Context context) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    void bindItem(Article item) {
      if (item != null && tvHeadline != null && tvSnippet != null && imgCover != null) {
        if (item.getHeadline() != null) {
          tvHeadline.setText(item.getHeadline().getMain());
          if (!TextUtils.isEmpty(item.getHeadline().getName())) {
            tvSnippet.setText(item.getHeadline().getName());
          } else {
            tvSnippet.setVisibility(View.GONE);
            tvHeadline.setMaxLines(3);
          }
        }

        if (item.getMultimedia() != null && item.getMultimedia().size() > 0) {
          Glide.with(context)
              .load(context.getString(R.string.base_image_url) + getImageURL(item.getMultimedia()))
              .crossFade()
              .fitCenter()
              .error(R.drawable.placeholder_120x100)
              .placeholder(R.drawable.placeholder_120x100)
              .into(imgCover);
        } else if (item.getMultimedia() != null && item.getMultimedia().size() == 0) {
          Glide.with(context).load(R.drawable.placeholder_120x100).into(imgCover);
        }
      }
    }
  }
}

package vn.com.phongnguyen93.readmee.models;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

/**
 * Created by phongnguyen on 2/21/17.
 */

public class Multimedia implements Parcelable {
  public static final String SUBTYPE_THUMBNAIL = "thumbnail";
  public static final String SUBTYPE_LARGE = "large";
  public static final String SUBTYPE_XLARGE = "xlarge";
  public static final String SUBTYPE_WIDE = "wide";

  private String url;
  private int height;
  private String subtype;
  private String type;
  private int width;
  private String format;
  private String copyright;
  private String caption;

  protected Multimedia(Parcel in) {
    url = in.readString();
    height = in.readInt();
    subtype = in.readString();
    type = in.readString();
    width = in.readInt();
    format = in.readString();
    copyright = in.readString();
    caption = in.readString();
  }

  public static final Creator<Multimedia> CREATOR = new Creator<Multimedia>() {
    @Override public Multimedia createFromParcel(Parcel in) {
      return new Multimedia(in);
    }

    @Override public Multimedia[] newArray(int size) {
      return new Multimedia[size];
    }
  };

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public String getSubtype() {
    return subtype;
  }

  public void setSubtype(String subtype) {
    this.subtype = subtype;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  public String getCopyright() {
    return copyright;
  }

  public void setCopyright(String copyright) {
    this.copyright = copyright;
  }

  public String getCaption() {
    return caption;
  }

  public void setCaption(String caption) {
    this.caption = caption;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(url);
    parcel.writeInt(height);
    parcel.writeString(subtype);
    parcel.writeString(type);
    parcel.writeInt(width);
    parcel.writeString(format);
    parcel.writeString(copyright);
    parcel.writeString(caption);
  }
}

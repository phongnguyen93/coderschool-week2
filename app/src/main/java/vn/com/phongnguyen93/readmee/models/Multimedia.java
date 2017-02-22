package vn.com.phongnguyen93.readmee.models;

import java.util.ArrayList;

/**
 * Created by phongnguyen on 2/21/17.
 */

public class Multimedia {
  private String credit;
  private String url;
  private String rank;
  private int height;
  private String subtype;
  private String type;
  private int width;
  private String type_of_material;
  private String _id;
  private String word_count;
  private ArrayList<Multimedia> multimedia;
  private Headline headline;
  private Keyword keyword;


  public String getCredit() {
    return credit;
  }

  public void setCredit(String credit) {
    this.credit = credit;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getRank() {
    return rank;
  }

  public void setRank(String rank) {
    this.rank = rank;
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

  public String getType_of_material() {
    return type_of_material;
  }

  public void setType_of_material(String type_of_material) {
    this.type_of_material = type_of_material;
  }

  public String get_id() {
    return _id;
  }

  public void set_id(String _id) {
    this._id = _id;
  }

  public String getWord_count() {
    return word_count;
  }

  public void setWord_count(String word_count) {
    this.word_count = word_count;
  }

  public ArrayList<Multimedia> getMultimedia() {
    return multimedia;
  }

  public void setMultimedia(ArrayList<Multimedia> multimedia) {
    this.multimedia = multimedia;
  }

  public Headline getHeadline() {
    return headline;
  }

  public void setHeadline(Headline headline) {
    this.headline = headline;
  }

  public Keyword getKeyword() {
    return keyword;
  }

  public void setKeyword(Keyword keyword) {
    this.keyword = keyword;
  }

  public class Headline{
    private String main;
    private String name;

    public String getMain() {
      return main;
    }

    public void setMain(String main) {
      this.main = main;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }


  public class Keyword{
    private String rank;
    private String name;
    private String value;

    public String getRank() {
      return rank;
    }

    public void setRank(String rank) {
      this.rank = rank;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }
  }

}

package vn.com.phongnguyen93.readmee.models;

import java.util.ArrayList;

/**
 * Created by phongnguyen on 2/21/17.
 */

public class Article {
  private String web_url;
  private String snippet;
  private String lead_paragraph;
  private String source;
  private String pub_date;
  private String document_type;
  private String section_name;
  private String subsection_name;
  private String credit;
  private String rank;
  private String type_of_material;
  private String _id;
  private String word_count;
  private ArrayList<Multimedia> multimedia;
  private Article.Headline headline;
  private Article.Keyword keyword;


  public String getCredit() {
    return credit;
  }

  public void setCredit(String credit) {
    this.credit = credit;
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

  public Article.Headline getHeadline() {
    return headline;
  }

  public void setHeadline(Article.Headline headline) {
    this.headline = headline;
  }

  public Article.Keyword getKeyword() {
    return keyword;
  }

  public void setKeyword(Article.Keyword keyword) {
    this.keyword = keyword;
  }

  public String getWeb_url() {
    return web_url;
  }

  public void setWeb_url(String web_url) {
    this.web_url = web_url;
  }

  public String getSnippet() {
    return snippet;
  }

  public void setSnippet(String snippet) {
    this.snippet = snippet;
  }

  public String getLead_paragraph() {
    return lead_paragraph;
  }

  public void setLead_paragraph(String lead_paragraph) {
    this.lead_paragraph = lead_paragraph;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getPub_date() {
    return pub_date;
  }

  public void setPub_date(String pub_date) {
    this.pub_date = pub_date;
  }

  public String getDocument_type() {
    return document_type;
  }

  public void setDocument_type(String document_type) {
    this.document_type = document_type;
  }

  public String getSection_name() {
    return section_name;
  }

  public void setSection_name(String section_name) {
    this.section_name = section_name;
  }

  public String getSubsection_name() {
    return subsection_name;
  }

  public void setSubsection_name(String subsection_name) {
    this.subsection_name = subsection_name;
  }

  public String getRank() {
    return rank;
  }

  public void setRank(String rank) {
    this.rank = rank;
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

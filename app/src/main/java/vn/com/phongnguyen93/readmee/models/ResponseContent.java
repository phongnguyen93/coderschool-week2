package vn.com.phongnguyen93.readmee.models;

import java.util.ArrayList;

/**
 * Created by phongnguyen on 2/23/17.
 */

public class ResponseContent {
  private ArrayList<Article> docs;
  private Metadata metadata;

  public ArrayList<Article> getDocs() {
    return docs;
  }

  public void setDocs(ArrayList<Article> docs) {
    this.docs = docs;
  }

  public Metadata getMetadata() {
    return metadata;
  }

  public void setMetadata(Metadata metadata) {
    this.metadata = metadata;
  }

  public class Metadata{
    private int hits;
    private int time;
    private int offset;

    public int getHits() {
      return hits;
    }

    public void setHits(int hits) {
      this.hits = hits;
    }

    public int getTime() {
      return time;
    }

    public void setTime(int time) {
      this.time = time;
    }

    public int getOffset() {
      return offset;
    }

    public void setOffset(int offset) {
      this.offset = offset;
    }
  }
}

package vn.com.phongnguyen93.readmee.models;

/**
 * Created by phongnguyen on 2/24/17.
 */

public class ApiResponse {
    private ResponseContent response;
    private String status;
    private String copyright;

  public ResponseContent getResponse() {
    return response;
  }

  public void setResponse(ResponseContent response) {
    this.response = response;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getCopyright() {
    return copyright;
  }

  public void setCopyright(String copyright) {
    this.copyright = copyright;
  }
}

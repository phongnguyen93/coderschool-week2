package vn.com.phongnguyen93.readmee;

import android.app.Application;
import android.content.Context;
import vn.com.phongnguyen93.readmee.network.di.components.DaggerMainComponent;
import vn.com.phongnguyen93.readmee.network.di.components.DaggerNetComponent;
import vn.com.phongnguyen93.readmee.network.di.components.MainComponent;
import vn.com.phongnguyen93.readmee.network.di.components.NetComponent;
import vn.com.phongnguyen93.readmee.network.di.modules.ApiModule;
import vn.com.phongnguyen93.readmee.network.di.modules.AppModule;
import vn.com.phongnguyen93.readmee.network.di.modules.NetModule;
import vn.com.phongnguyen93.readmee.utilities.FontsOverride;

/**
 * Created by phongnguyen on 2/21/17.
 */

public class ReadMeeApplication extends Application {
  private static NetComponent mNetComponent;
  private static MainComponent mMainComponent;
  private static Context context;

  public static MainComponent getmMainComponent() {
    return mMainComponent;
  }

  public static void setmMainComponent(MainComponent mMainComponent) {
    ReadMeeApplication.mMainComponent = mMainComponent;
  }

  public static NetComponent getmNetComponent() {
    return mNetComponent;
  }

  public static void setmNetComponent(NetComponent mNetComponent) {
    ReadMeeApplication.mNetComponent = mNetComponent;
  }

  @Override public void onCreate() {
    super.onCreate();
    context = this;
    overrideFont();
    setmNetComponent(DaggerNetComponent.builder()
        .appModule(new AppModule(this))
        .netModule(new NetModule(getString(R.string.base_url)))
        .build());

    setmMainComponent(DaggerMainComponent.builder()
        .netComponent(getmNetComponent())
        .apiModule(new ApiModule())
        .build());
  }

  private void overrideFont() {
    FontsOverride.setDefaultFont(this, "SANS_SERIF", "Raleway-Regular.ttf");
    FontsOverride.setDefaultFont(this, "MONOSPACE", "Raleway-Bold.ttf");
    FontsOverride.setDefaultFont(this, "SERIF", "Lobster.ttf");
  }

  public static Context getAppContext(){
    return context;
  }
}

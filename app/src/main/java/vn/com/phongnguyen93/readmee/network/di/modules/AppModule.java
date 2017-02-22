package vn.com.phongnguyen93.readmee.network.di.modules;

import android.app.Application;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class AppModule {

    Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton Application providesApplication() {
        return mApplication;
    }


}

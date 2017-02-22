package vn.com.phongnguyen93.readmee.network.di.components;


import dagger.Component;
import javax.inject.Singleton;
import retrofit2.Retrofit;
import vn.com.phongnguyen93.readmee.network.di.modules.AppModule;
import vn.com.phongnguyen93.readmee.network.di.modules.NetModule;

@Singleton
@Component(modules={AppModule.class, NetModule.class})
public interface NetComponent {
    Retrofit retrofit();
    okhttp3.OkHttpClient okHttpClient();
}
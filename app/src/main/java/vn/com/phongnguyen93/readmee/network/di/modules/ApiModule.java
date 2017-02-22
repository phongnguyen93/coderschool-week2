package vn.com.phongnguyen93.readmee.network.di.modules;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import vn.com.phongnguyen93.readmee.network.di.AppScope;
import vn.com.phongnguyen93.readmee.network.interfaces.ReadMeeEndpoint;

@Module
@AppScope
public class ApiModule {

    @Provides
    public ReadMeeEndpoint providesApiInterface(Retrofit retrofit) {
        return retrofit.create(ReadMeeEndpoint.class);
    }

}

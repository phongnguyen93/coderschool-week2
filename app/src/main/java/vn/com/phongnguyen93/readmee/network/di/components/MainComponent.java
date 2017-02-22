package vn.com.phongnguyen93.readmee.network.di.components;



import dagger.Component;
import vn.com.phongnguyen93.readmee.network.di.AppScope;
import vn.com.phongnguyen93.readmee.network.di.modules.ApiModule;

@AppScope
@Component(dependencies = NetComponent.class, modules = ApiModule.class)
public interface MainComponent {

}

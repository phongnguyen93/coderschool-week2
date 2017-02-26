package vn.com.phongnguyen93.readmee;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * Created by phongnguyen on 9/18/16.
 */
public class NetworkReceiver extends BroadcastReceiver {
    public NetworkReceiver(){}

    private NetworkCheck networkCheck;

    public interface NetworkCheck{
        void onConnected();
        void onDisconnected();
    }

    public NetworkReceiver(NetworkCheck networkCheck){
        this.networkCheck = networkCheck;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(networkCheck!=null){
            if(checkNetworkConnection(context)){
                networkCheck.onConnected();
            }else
                networkCheck.onDisconnected();
        }
    }

    private static boolean checkNetworkConnection(Context context) {
        ConnectivityManager connMgr =
            (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        NetworkInfo.DetailedState detailedState;
        if (networkInfo != null) {
            detailedState = networkInfo.getDetailedState();
            //check network info type of connection
            if (detailedState == NetworkInfo.DetailedState.CONNECTED) {
                if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE
                    || networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    return true;
                }
            }
            //check disconnection
            if (detailedState == NetworkInfo.DetailedState.DISCONNECTED
                || detailedState == NetworkInfo.DetailedState.FAILED
                || detailedState == NetworkInfo.DetailedState.SUSPENDED
                || detailedState == NetworkInfo.DetailedState.BLOCKED) {
                return false;
            }
        }
        return false;
    }
}

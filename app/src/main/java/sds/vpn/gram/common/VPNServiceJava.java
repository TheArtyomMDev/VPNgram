

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import java.util.HashMap;
import java.util.Map;
import sds.vpn.gram.common.MyVpnTunnel;
import sds.vpn.gram.data.remote.dto.GetTrafficLimitResponse;
import sds.vpn.gram.ui.WebViewActivity;

public class VPNServiceJava extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Context context = null;
        Map<String, String> ads = null;
        MyVpnTunnel vpnTunnel = null;
        GetTrafficLimitResponse traffic = null;
        Boolean checkAlertSystemWindowsPermission = true;
        Boolean checkUsageStatsGranted = true;
        HashMap<String, Long> lastUsedAppsNonFiltered = new HashMap();
        HashMap<String, Long> lastUsedApps = new HashMap();
        long lastLaunched = 0L;

        long time = System.currentTimeMillis();
        long newLastLaunched = lastLaunched;
        if(checkUsageStatsGranted && checkAlertSystemWindowsPermission)
        {
            for(String app: lastUsedAppsNonFiltered.keySet())
            {
                if(ads.containsKey(app))
                {
                    lastUsedApps.put(app, lastUsedAppsNonFiltered.get(app));
                }
            }


            for(String app : lastUsedApps.keySet()) {
                if(lastUsedApps.get(app) != null )
                    if(time - lastLaunched > 10000 && time - (Long) lastUsedApps.get(app) < 2000) {
                        newLastLaunched = System.currentTimeMillis();

                    Intent appIntent = new Intent(context, WebViewActivity.class);
                    appIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                    appIntent.putExtra("url", ads.get(app));

                        if(traffic.getTrafficLimit() - traffic.getTrafficSpent() > 0) {
                        if(vpnTunnel.isVpnConnected()) { }
                        else {
                            appIntent.putExtra("openHome", true);
                            context.startActivity(appIntent);
                        }
                    }
                    else {
                        appIntent.putExtra("openPremium", true);
                        context.startActivity(appIntent);
                    }

                    break;
                }
            }
        }
        return START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

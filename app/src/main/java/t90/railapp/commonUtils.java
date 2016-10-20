package t90.railapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.HashMap;

public class commonUtils {

    public static void setStatusBarTheme(Activity activity){

        SystemBarTintManager sm = new SystemBarTintManager(activity);

        sm.setTintColor(R.color.primary_darkest);
        sm.setStatusBarTintEnabled(true);
        sm.setNavigationBarTintEnabled(true);
        sm.setStatusBarAlpha(1);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity, R.color.primary_darkest));
        }

    }

    public static String getUrl(Activity activity, String id){
        String baseUrl = "http://railapp.sudorootone.com/";
        String url = "";

        SharedPreferences pref = activity.getSharedPreferences("userCred", Context.MODE_PRIVATE);
        String userId = pref.getString("userId", "5800f0da70e0b7.94746660");

        switch (id){
            case "dashCard":
                url = "user/" + userId + "/dash/";
        }

        return baseUrl + url;
    }

    public static String getCommonAuth(){
        return "Basic cmFpbGFwcDpyYWlsYXBw";
    }
}

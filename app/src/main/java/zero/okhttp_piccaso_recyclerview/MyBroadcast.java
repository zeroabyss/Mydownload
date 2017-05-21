package zero.okhttp_piccaso_recyclerview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Aiy on 2017/5/21.
 */

public class MyBroadcast extends BroadcastReceiver {
    public static final String BROADCAST_NAME="my_intent_service";
    @Override
    public void onReceive(Context context, Intent intent) {
        String name=intent.getAction();
        if (BROADCAST_NAME.equals(name)){
            Toast.makeText(context,"下载完成",Toast.LENGTH_SHORT).show();;
        }
    }
}

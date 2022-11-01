package com.sim.myth;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

public class TestService extends Service {

    public static final String INTENT_ACTION = "com.sim.myth.TestService.Bind";
    private static final int MSG_WORK = 1;

    private final TestServiceImpl serviceImplementation = new TestServiceImpl();

    @Override
    public void onCreate() {
        Log.d("SSB_TEST", "Service is onCrreate..");

        handler.sendEmptyMessage(MSG_WORK);

        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.d("SSB_TEST", "Service is onDestory..");

        handler.removeMessages(MSG_WORK);

        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("SSB_TEST", "onBind..");
        if(intent.getAction() != null && intent.getAction().equals(INTENT_ACTION)){
            Log.d("SSB_TEST", "action is equals.. :: " + intent.getAction());
            return serviceImplementation;
        }
        Log.d("SSB_TEST", "action is not equals.. :: " + intent.getAction());
        return null;
    }

    private Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {

            switch (msg.what) {
                case MSG_WORK:

                    int N = serviceImplementation.callbacks.beginBroadcast();

                    for(int i = 0; i < N; i++){
                        try {
                            ((IRemoteServiceCallback)serviceImplementation.callbacks.getBroadcastItem(i)).valueChanged(System.currentTimeMillis());
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }

                    Log.d("SSB_TEST", "Handler work.. :: callbacks clients count is " + N);
                    serviceImplementation.callbacks.finishBroadcast();

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(MSG_WORK);

                    break;

                default:
                    break;
            }
            return false;
        }
    });
}

package com.sim.steel;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.sim.myth.IRemoteService;
import com.sim.myth.IRemoteServiceCallback;
import com.sim.myth.ServiceIntentBuilder;

public class MainActivity extends Activity {

    IRemoteServiceCallback mCallbcak = new IRemoteServiceCallback.Stub() {

        @Override
        public void valueChanged(long value) throws RemoteException {
            Log.i("SSB_TEST", "Activity Callback value : " + value);
        }
    };

    IRemoteService mService;
    ServiceConnection mConntection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {

            Log.i("SSB_TEST", "onServiceDisconnected..");
            if(mService != null){
                try {
                    mService.unregisterCallback(mCallbcak);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            Log.i("SSB_TEST", "onServiceConnected..");
            if(service != null){
                mService = IRemoteService.Stub.asInterface(service);
                try {
                    mService.registerCallback(mCallbcak);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private void startServiceBind(){
        Log.i("SSB_TEST", "startServiceBind..");
        bindService(ServiceIntentBuilder.buildTestBindIntent(), mConntection, Context.BIND_AUTO_CREATE);
    }

    private void stopServiceBind(){
        Log.i("SSB_TEST", "stopServiceBind..");
        unbindService(mConntection);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i("SSB_TEST", "onCreate..");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onStart() {
        Log.i("SSB_TEST", "onStart..");
        startServiceBind();
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.i("SSB_TEST", "onStop..");
        stopServiceBind();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i("SSB_TEST", "onDestroy..");
        super.onDestroy();
    }
}
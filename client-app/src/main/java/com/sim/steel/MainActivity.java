package com.sim.steel;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.sim.myth.IRemoteService;
import com.sim.myth.IRemoteServiceCallback;

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
        Intent service = new Intent("com.sim.myth.TestService.Bind").setPackage("com.sim.myth");
        bindService(service, mConntection, Context.BIND_AUTO_CREATE);
    }

    private void stopServiceBind(){
        unbindService(mConntection);
        stopService(new Intent("com.sim.myth.TestService.Bind").setPackage("com.sim.myth"));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume() {
        startServiceBind();
        super.onResume();
    }

    @Override
    protected void onStop() {
        stopServiceBind();
        super.onStop();
    }
}
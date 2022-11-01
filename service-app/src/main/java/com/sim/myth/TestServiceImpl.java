package com.sim.myth;

import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

public class TestServiceImpl extends IRemoteService.Stub {

    final RemoteCallbackList callbacks = new RemoteCallbackList();

    @Override
    public boolean registerCallback(IRemoteServiceCallback callback) throws RemoteException {
        boolean flag = false;

        if(callback != null){
            flag = callbacks.register(callback);
        }

        return flag;
    }

    @Override
    public boolean unregisterCallback(IRemoteServiceCallback callback) throws RemoteException {
        boolean flag = false;

        if(callback != null){
            flag = unregisterCallback(callback);
        }

        return flag;
    }

    @Override
    public IBinder asBinder() {
        return this;
    }
}
// IRemoteService.aidl
package com.sim.myth;

import com.sim.myth.IRemoteServiceCallback;

interface IRemoteService {
	boolean registerCallback(IRemoteServiceCallback callback);
	boolean unregisterCallback(IRemoteServiceCallback callback);
}
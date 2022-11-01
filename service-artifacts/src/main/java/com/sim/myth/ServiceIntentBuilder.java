package com.sim.myth;

import android.content.Intent;

public class ServiceIntentBuilder {
    public static Intent buildTestBindIntent() {
        // The acton is from the Service's intent-filter declaration in the manifest
        return new Intent("com.sim.myth.TestService.Bind").setPackage("com.sim.myth");
    }
}
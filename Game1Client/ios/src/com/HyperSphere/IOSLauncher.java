package com.HyperSphere;
import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.uikit.UIApplication;

import com.HyperSphere.Manager.Launcher;
import com.HyperSphere.Manager.DI;
import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;

public class IOSLauncher extends IOSApplication.Delegate {
    @Override
    protected IOSApplication createApplication() {
        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        return new IOSApplication(new Launcher(new DI() {
            @Override
            public void sendAnalytic(int code, String s) {
                System.out.println("Message:" + s + " Code:" + code);
            }

            @Override
            public void purchase(String s) {
                System.out.println("Attempted Purchase:" + s);
            }

            @Override
            public void verifyPurchase(String s) {
                System.out.println("Verify Purchase:" + s);
            }
        }), config);
    }

    public static void main(String[] argv) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);
        pool.close();
    }
}
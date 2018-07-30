package android.hardware.gpio;

/**
 * Created by Administrator on 2018/3/26 0026.
 */

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;

import java.io.IOException;

public class GpioManager {
    private static final String TAG = "GpioManager";
    private final Context mContext;
    private final IGpioManager mService;

    public GpioManager(Context context, IGpioManager service) {
        this.mContext = context;
        this.mService = service;
    }

    public String[] getSupportedGpioPorts() {
        try {
            return this.mService.getSupportedGpioPorts();
        } catch (RemoteException var2) {
            Log.e("GpioManager", "RemoteException in getSupportedGpioPorts", var2);
            return null;
        }
    }

    public int getGpioValue(String name) {
        try {
            return this.mService.getGpioValue(name);
        } catch (RemoteException var3) {
            Log.e("GpioManager", "RemoteException in getGpioValue", var3);
            return -1;
        }
    }

    public GpioPort obtainGpioPortControl(String name) throws IOException {
        try {
            if(this.mService.obtainGpioPortControl(name)) {
                GpioPort port = new GpioPort();
                port.gpio_init();
                return port;
            } else {
                throw new IOException("Could not open gpio port " + name);
            }
        } catch (RemoteException var3) {
            Log.e("GpioManager", "exception in UsbManager.openDevice", var3);
            return null;
        }
    }
}

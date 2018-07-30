package android.hardware.gpio;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/**
 * Created by Administrator on 2018/3/26 0026.
 */

public interface IGpioManager extends IInterface {
    String[] getSupportedGpioPorts() throws RemoteException;

    int getGpioValue(String var1) throws RemoteException;

    boolean obtainGpioPortControl(String var1) throws RemoteException;

    public abstract static class Stub extends Binder implements IGpioManager {
        private static final String DESCRIPTOR = "android.hardware.gpio.IGpioManager";
        static final int TRANSACTION_getSupportedGpioPorts = 1;
        static final int TRANSACTION_getGpioValue = 2;
        static final int TRANSACTION_obtainGpioPortControl = 3;

        public Stub() {
            this.attachInterface(this, "android.hardware.gpio.IGpioManager");
        }

        public static IGpioManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            } else {
                IInterface iin = obj.queryLocalInterface("android.hardware.gpio.IGpioManager");
                return (IGpioManager) (iin != null && iin instanceof IGpioManager ? (IGpioManager) iin : new Proxy(obj));
            }
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            String _arg0;
            switch (code) {
                case 1:
                    data.enforceInterface("android.hardware.gpio.IGpioManager");
                    String[] _result = this.getSupportedGpioPorts();
                    reply.writeNoException();
                    reply.writeStringArray(_result);
                    return true;
                case 2:
                    data.enforceInterface("android.hardware.gpio.IGpioManager");
                    _arg0 = data.readString();
                    int _result_i = this.getGpioValue(_arg0);
                    reply.writeNoException();
                    reply.writeInt(_result_i);
                    return true;
                case 3:
                    data.enforceInterface("android.hardware.gpio.IGpioManager");
                    _arg0 = data.readString();
                    boolean _result_b = this.obtainGpioPortControl(_arg0);
                    reply.writeNoException();
                    reply.writeInt(_result_b ? 1 : 0);
                    return true;
                case 1598968902:
                    reply.writeString("android.hardware.gpio.IGpioManager");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IGpioManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.hardware.gpio.IGpioManager";
            }

            public String[] getSupportedGpioPorts() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();

                String[] _result;
                try {
                    _data.writeInterfaceToken("android.hardware.gpio.IGpioManager");
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    _result = _reply.createStringArray();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }

                return _result;
            }

            public int getGpioValue(String name) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();

                int _result;
                try {
                    _data.writeInterfaceToken("android.hardware.gpio.IGpioManager");
                    _data.writeString(name);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    _result = _reply.readInt();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }

                return _result;
            }

            public boolean obtainGpioPortControl(String name) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();

                boolean _result;
                try {
                    _data.writeInterfaceToken("android.hardware.gpio.IGpioManager");
                    _data.writeString(name);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    _result = 0 != _reply.readInt();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }

                return _result;
            }
        }
    }
}

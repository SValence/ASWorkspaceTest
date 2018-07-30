package android.hardware.gpio;

/**
 * Created by Administrator on 2018/3/26 0026.
 */

public class GpioPort {
    private static final String TAG = "GpioPort";
    private int mNativeContext;

    public GpioPort() {
    }

    public boolean gpio_init() {
        boolean ret = false;
        ret = this.native_gpio_init();
        if(!ret) {
            throw new IllegalArgumentException("open /dev/mtgpio error");
        } else {
            return ret;
        }
    }

    public boolean gpio_deinit() {
        boolean ret = false;
        ret = this.native_gpio_deinit();
        if(!ret) {
            throw new IllegalArgumentException("close /dev/mtgpio error");
        } else {
            return ret;
        }
    }

    public int get_max_number() {
        return this.native_gpio_max_number();
    }

    public boolean set_mode(int gpio, int mode) {
        boolean ret = false;
        ret = this.native_gpio_set_mode(gpio, mode);
        if(!ret) {
            throw new IllegalArgumentException("set gpio mode(0/1/2/3) error");
        } else {
            return ret;
        }
    }

    public boolean set_dir(int gpio, int dir) {
        boolean ret = false;
        ret = this.native_gpio_set_dir(gpio, dir);
        if(!ret) {
            throw new IllegalArgumentException("set gpio dir input/output error");
        } else {
            return ret;
        }
    }

    public boolean set_data(int gpio, int data) {
        boolean ret = false;
        ret = this.native_gpio_set_data(gpio, data);
        if(!ret) {
            throw new IllegalArgumentException("set gpio value high/low error");
        } else {
            return ret;
        }
    }

    public boolean set_pullen(int gpio, int pullen) {
        boolean ret = false;
        ret = this.native_gpio_set_pull_en(gpio, pullen);
        if(!ret) {
            throw new IllegalArgumentException("set gpio pull enable error");
        } else {
            return ret;
        }
    }

    public boolean set_pull(int gpio, int pull) {
        boolean ret = false;
        ret = this.native_gpio_set_pull(gpio, pull);
        if(!ret) {
            throw new IllegalArgumentException("set gpio pull high/low error");
        } else {
            return ret;
        }
    }

    private native boolean native_gpio_init();

    private native boolean native_gpio_deinit();

    private native int native_gpio_max_number();

    private native boolean native_gpio_set_mode(int var1, int var2);

    private native boolean native_gpio_set_dir(int var1, int var2);

    private native boolean native_gpio_set_data(int var1, int var2);

    private native boolean native_gpio_set_pull_en(int var1, int var2);

    private native boolean native_gpio_set_pull(int var1, int var2);
}

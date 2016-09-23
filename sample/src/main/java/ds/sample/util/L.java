package ds.sample.util;

import timber.log.Timber;

public class L {

    private static Timber.Tree timber;

    private L() { }

    private static Timber.Tree getInstance() {
        if (timber == null) {
            timber = Timber.asTree();
        }
        return timber;
    }

    public static void d(String s, Object... objects) {
        getInstance().d(s, objects);
    }

    public static void d(Throwable throwable, String s, Object... objects) {
        getInstance().d(throwable, s, objects);
    }

    public static void i(String s, Object... objects) {
        getInstance().i(s, objects);
    }

    public static void i(Throwable throwable, String s, Object... objects) {
        getInstance().i(throwable, s, objects);
    }

    public static void v(String s, Object... objects) {
        getInstance().v(s, objects);
    }

    public static void v(Throwable throwable, String s, Object... objects) {
        getInstance().v(throwable, s, objects);
    }

    public static void w(String s, Object... objects) {
        getInstance().w(s, objects);
    }

    public static void w(Throwable throwable, String s, Object... objects) {
        getInstance().w(throwable, s, objects);
    }

    public static void e(String s, Object... objects) {
        getInstance().e(s, objects);
    }

    public static void e(Throwable throwable, String s, Object... objects) {
        getInstance().e(throwable, s, objects);
    }

}
package cn.payadd.mpos.util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhengzhen.wang on 2016/12/2.
 */

public class ThreadUtil {

    private static ExecutorService executor;

    /**
     * 执行任务
     * @param callable
     */
    public static void exec(Callable callable) {
        if (null == executor) {
            executor = Executors.newSingleThreadExecutor();
        }
        executor.submit(callable);
    }

    /**
     * 执行任务
     * @param runnable
     */
    public static void exec(Runnable runnable) {
        if (null == executor) {
            executor = Executors.newSingleThreadExecutor();
        }
        executor.submit(runnable);
    }

    /**
     * 关闭线程池
     */
    public static void close() {

        if (null != executor) {
            executor.shutdown();
            executor = null;
        }
    }

}

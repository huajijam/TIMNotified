package me.ketal.util

import nil.nadph.qnotified.util.Utils
import kotlin.concurrent.thread

object BaseUtil {
    /**
     * 执行回调函数, 无视它抛出的任何异常
     */
    @JvmStatic inline fun <T: Any>trySilently(default: T, func: () -> T): T {
        return try { func() } catch (t: Throwable) { default }
    }

    /**
     * 执行回调函数, 将它抛出的异常记录到 Xposed 的日志里
     */
    @JvmStatic inline fun <T: Any>tryVerbosely(default: T, func: () -> T): T {
        return try { func() } catch (t: Throwable) {
            Utils.log(t); default
        }
    }

    /**
     * 异步执行回调函数, 将它抛出的记录到 Xposed 的日志里
     *
     * WARN: 别忘了任何 UI 操作都必须使用 runOnUiThread
     */
    @JvmStatic inline fun tryAsynchronously(crossinline func: () -> Unit): Thread {
        return thread(start = true) { func() }.apply {
            setUncaughtExceptionHandler { _, t ->
                Utils.log(t)
            }
        }
    }
}
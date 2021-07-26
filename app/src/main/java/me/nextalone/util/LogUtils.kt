package me.nextalone.util

import nil.nadph.qnotified.util.Utils

internal fun logd(msg: String) {
    Utils.logd("NA: $msg")
}

internal fun logThrowable(msg: Throwable) {
    logd("Throwable: $msg")
}

internal fun logDetail(info: String, msg: String = "") {
    logd("$info--$msg")
}

internal fun logClass(msg: String = "") {
    logd("Class--$msg")
}

internal fun logMethod(msg: String = "") {
    logd("Method--$msg")
}

internal fun logStart(msg: String = "") {
    logd("Start--$msg")
}

internal fun logBefore(msg: String = "") {
    logd("Before--$msg")
}

internal fun logAfter(msg: String = "") {
    logd("After--$msg")
}
#ifndef ANDROID_XLOG_H
#define ANDROID_XLOG_H

#include<android/log.h>
static bool isAndroidDebug = false;
#define LOG_TAG "HiLeiaCore"
#define LOG_TAG_FLAG 1
#define LOGD(...) if (isAndroidDebug) __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG ,__VA_ARGS__) // 定义LOGD类型
#define LOGI(...) if (isAndroidDebug) __android_log_print(ANDROID_LOG_INFO,LOG_TAG ,__VA_ARGS__) // 定义LOGI类型
#define LOGW(...) if (isAndroidDebug) __android_log_print(ANDROID_LOG_WARN,LOG_TAG ,__VA_ARGS__) // 定义LOGW类型
#define LOGE(...) if (isAndroidDebug) __android_log_print(ANDROID_LOG_ERROR,LOG_TAG ,__VA_ARGS__) // 定义LOGE类型
#define LOGF(...) if (isAndroidDebug) __android_log_print(ANDROID_LOG_FATAL,LOG_TAG ,__VA_ARGS__) // 定义LOGF类型
#endif //ANDROID_LOG_H


#include <jni.h>
#include <string>
#include "iclient.h"
#include "signaling_client_event_handler.h"
#include "xLog.h"
#include "jni_load_util.h"
#include "pointer_convert_util.h"
#include <memory>
#include <mutex>

#define FUNCTION_INVOKE_SUCCESS 0
#define FUNCTION_INVOKE_FAIL 1
#define UNINIT_FAIL 2
using namespace signaling;
using namespace std;

mutex gMutex;
static SignalingClientEventHandler *gHandler = nullptr;
extern "C"
jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    LOGD("JNI_OnLoad...");
    JniLoadUtil::javaVm = vm;
    int attach = 0;
    JNIEnv *env = JniLoadUtil::AttachJava(&attach);
    jclass buildConfigClazz = env->FindClass("com/hiscene/signaling/BuildConfig");
    jfieldID jfieldId = env->GetStaticFieldID(buildConfigClazz,"DEBUG","Z");
    bool buildType =env->GetStaticBooleanField(buildConfigClazz,jfieldId);
    isAndroidDebug = !buildType;
    LOGD("isAndroidDebug=%s", to_string(isAndroidDebug).c_str());
    __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG ,"-----isAndroidDebug=%s",to_string(isAndroidDebug).c_str());
    env->DeleteLocalRef(buildConfigClazz);
    if (attach == 1) {
        JniLoadUtil::DetachCurrentThread();
    }
    return JNI_VERSION_1_6;
}
extern "C"
JNIEXPORT jlong JNICALL
Java_com_hiscene_signaling_SignalingClient_nativeInitialize(JNIEnv *env, jobject obj,
                                                            jobject client_context) {
    LOGD("Java_com_hiscene_signaling_SignalingClient_nativeInitialize");
    lock_guard<mutex> lockGuard(gMutex);
    if (gHandler != nullptr) {
        LOGD("delete gHandler");
        delete gHandler;
        gHandler = nullptr;
    }
    jclass clazz = env->GetObjectClass(client_context);
    jmethodID uidMd = env->GetMethodID(clazz, "getUid", "()Ljava/lang/String;");
    jmethodID mqttUrlMd = env->GetMethodID(clazz, "getMqttUrl", "()Ljava/lang/String;");
    jmethodID realmUrlMd = env->GetMethodID(clazz, "getRealmUrl", "()Ljava/lang/String;");
    jmethodID clientHandlerMd = env->GetMethodID(clazz, "getClientHandler",
                                                 "()Lcom/hiscene/signaling/ISignalingClientEventHandler;");
    jstring u_id = static_cast<jstring>(env->CallObjectMethod(client_context, uidMd));
    jstring mqtt_url = static_cast<jstring>(env->CallObjectMethod(client_context, mqttUrlMd));
    jstring realm_url = static_cast<jstring>(env->CallObjectMethod(client_context, realmUrlMd));
    jobject callback = env->CallObjectMethod(client_context, clientHandlerMd);
    const char *c_uid = env->GetStringUTFChars(u_id, nullptr);
    const char *c_mqtt_url = env->GetStringUTFChars(mqtt_url, nullptr);
    const char *c_realm_url = env->GetStringUTFChars(realm_url, nullptr);

    auto client = createClient();
    gHandler = new SignalingClientEventHandler(env, callback);
    ClientContext context;
    context.uid = c_uid;
    context.mqttUrl = c_mqtt_url;
    context.realmUrl = c_realm_url;
    context.clientHandler = gHandler;
    client->initialize(context);

    env->ReleaseStringUTFChars(u_id, c_uid);
    env->ReleaseStringUTFChars(mqtt_url, c_mqtt_url);
    env->ReleaseStringUTFChars(realm_url, c_realm_url);
    env->DeleteLocalRef(clazz);
    auto result = NativeToJavaPointer(client);
    return result;
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_hiscene_signaling_SignalingClient_nativeConnect(JNIEnv *env, jobject thiz,
                                                         jlong m_native_handle) {
    LOGD("Java_com_hiscene_signaling_SignalingEngine_nativeConnect");
    if (gHandler == nullptr) {
        LOGE("尚未未初始化...");
        return UNINIT_FAIL;
    }
    auto handler = reinterpret_cast<signaling::IClient *>(m_native_handle);
    auto result = handler->connect();
    if (result != FUNCTION_INVOKE_SUCCESS) {
        LOGE("Java_com_hiscene_signaling_SignalingClient_nativeConnect 调用失败");
    }
    return result;
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_hiscene_signaling_SignalingClient_nativeDisconnect(JNIEnv *env, jobject thiz,
                                                            jlong m_native_handle) {
    LOGD("Java_com_hiscene_signaling_SignalingEngine_nativeDisconnect");
    if (gHandler == nullptr) {
        LOGE("尚未未初始化...");
        return UNINIT_FAIL;
    }
    auto handler = reinterpret_cast<signaling::IClient *>(m_native_handle);
    auto result = handler->disconnect();
    if (result != FUNCTION_INVOKE_SUCCESS) {
        LOGE("Java_com_hiscene_signaling_SignalingClient_nativeDisconnect 调用失败");
    }
    return result;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_hiscene_signaling_SignalingClient_nativeRelease(JNIEnv *env, jobject thiz,
                                                         jlong m_native_handle) {
    LOGD("Java_com_hiscene_signaling_SignalingEngine_nativeRelease");
    if (gHandler != nullptr) {
        lock_guard<mutex> lockGuard(gMutex);
        if (gHandler != nullptr) {
            delete gHandler;
            gHandler = nullptr;
            auto handler = reinterpret_cast<signaling::IClient *>(m_native_handle);
            handler->release();
        }else{
            LOGD("gHandler==nullptr无需释放 lock_guard");
        }
    } else {
        LOGD("gHandler==nullptr无需释放");
    }
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_hiscene_signaling_SignalingClient_nativecGetCollaborationState(JNIEnv *env, jobject thiz,
                                                                        jlong m_native_handle,
                                                                        jstring collaboration_id) {
    LOGD("Java_com_hiscene_signaling_SignalingEngine_nativecGetCollaborationState");
    if (gHandler == nullptr) {
        LOGE("尚未未初始化...");
        return UNINIT_FAIL;
    }
    const char *c_collaboration_id = env->GetStringUTFChars(collaboration_id, nullptr);
    auto handler = reinterpret_cast<signaling::IClient *>(m_native_handle);
    CollaborationState outState;
    auto result = handler->getCollaborationState(c_collaboration_id, outState);
    env->ReleaseStringUTFChars(collaboration_id, c_collaboration_id);
    if (result != FUNCTION_INVOKE_SUCCESS) {
        LOGE("Java_com_hiscene_signaling_SignalingEngine_nativecGetCollaborationState 调用失败");
        return FUNCTION_INVOKE_FAIL;
    }
    return outState;
}
extern "C"
JNIEXPORT jobjectArray JNICALL
Java_com_hiscene_signaling_SignalingClient_nativeGetCollaborationMembers(JNIEnv *env, jobject thiz,
                                                                         jlong m_native_handle,
                                                                         jstring collaboration_id) {
    LOGD("Java_com_hiscene_signaling_SignalingEngine_nativeGetCollaborationMembers");
    if (gHandler == nullptr) {
        LOGE("尚未未初始化...");
        return nullptr;
    }
    const char *c_collaboration_id = env->GetStringUTFChars(collaboration_id, nullptr);
    auto handler = reinterpret_cast<signaling::IClient *>(m_native_handle);
    vector<CollaborationMemberInfo> members;
    auto result = handler->getCollaborationMembers(c_collaboration_id, members);
    if (result != FUNCTION_INVOKE_SUCCESS) {
        LOGE("Java_com_hiscene_signaling_SignalingEngine_nativeGetCollaborationMembers 调用失败");
        return nullptr;
    }
    auto size = members.size();
    auto clazz = env->FindClass("com/hiscene/signaling/entity/CollaborationMemberInfo");
    auto construtMethod = env->GetMethodID(clazz, "<init>", "(Ljava/lang/String;I)V");
    auto objArray = env->NewObjectArray(size, clazz, nullptr);
    for (int i = 0; i < size; ++i) {
        auto member = members[i];
        auto memberId = env->NewStringUTF(member.memberId.c_str());
        jint confirmState = member.confirmState;
        auto obj = env->NewObject(clazz, construtMethod, memberId, confirmState);
        env->SetObjectArrayElement(objArray, i, obj);
    }
    env->DeleteLocalRef(clazz);
    env->ReleaseStringUTFChars(collaboration_id, c_collaboration_id);
    return objArray;
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_hiscene_signaling_SignalingClient_nativeCreateCollaboration(JNIEnv *env, jobject thiz,
                                                                     jlong m_native_handle,
                                                                     jobject option) {
    LOGD("Java_com_hiscene_signaling_SignalingEngine_nativeCreateCollaboration");
    if (gHandler == nullptr) {
        LOGE("尚未未初始化...");
        return UNINIT_FAIL;
    }
    jclass clazz = env->GetObjectClass(option);
    jmethodID startTimeMd = env->GetMethodID(clazz, "getStartTime", "()Ljava/lang/String;");
    jmethodID endTimeMd = env->GetMethodID(clazz, "getEndTime", "()Ljava/lang/String;");

    jstring start_time = static_cast<jstring>(env->CallObjectMethod(option, startTimeMd));
    jstring end_time = static_cast<jstring>(env->CallObjectMethod(option, endTimeMd));
    const char *c_start_time = env->GetStringUTFChars(start_time, nullptr);
    const char *c_end_time = env->GetStringUTFChars(end_time, nullptr);

    jmethodID inviteesMd = env->GetMethodID(clazz, "getInvitees", "()Ljava/util/List;");
    jobject inviteesObj = env->CallObjectMethod(option, inviteesMd);
    jclass listClazz = env->GetObjectClass(inviteesObj);
    jmethodID arrayListGet = env->GetMethodID(listClazz, "get", "(I)Ljava/lang/Object;");
    jmethodID arrayListSize = env->GetMethodID(listClazz, "size", "()I");
    jint size = env->CallIntMethod(inviteesObj, arrayListSize);
    vector<string> c_u_ids;
    for (int i = 0; i < size; i++) {
        jobject obj = env->CallObjectMethod(inviteesObj, arrayListGet, i);
        const char *c_u_id = env->GetStringUTFChars((jstring) obj, nullptr);
        c_u_ids.emplace_back(string(c_u_id));
        env->ReleaseStringUTFChars((jstring) obj, c_u_id);
        env->DeleteLocalRef(obj);
    }
    CollaborationCreateOption c_option = {c_u_ids, c_start_time, c_end_time};
    auto handler = reinterpret_cast<signaling::IClient *>(m_native_handle);
    auto result = handler->createCollaboration(c_option);
    env->ReleaseStringUTFChars(start_time, c_start_time);
    env->ReleaseStringUTFChars(end_time, c_end_time);
    env->DeleteLocalRef(listClazz);
    env->DeleteLocalRef(inviteesObj);
    env->DeleteLocalRef(clazz);
    if (result != FUNCTION_INVOKE_SUCCESS) {
        LOGE("Java_com_hiscene_signaling_SignalingEngine_nativeCreateCollaboration");
        return result;
    }
    return result;
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_hiscene_signaling_SignalingClient_nativeUpdateCollaboration(JNIEnv *env, jobject thiz,
                                                                     jlong m_native_handle,
                                                                     jobject option) {
    LOGD("Java_com_hiscene_signaling_SignalingEngine_nativeUpdateCollaboration");
    if (gHandler == nullptr) {
        LOGE("尚未未初始化...");
        return UNINIT_FAIL;
    }
    jclass clazz = env->GetObjectClass(option);
    jmethodID idMd = env->GetMethodID(clazz, "getId", "()Ljava/lang/String;");
    jmethodID startTimeMd = env->GetMethodID(clazz, "getStartTime", "()Ljava/lang/String;");
    jmethodID endTimeMd = env->GetMethodID(clazz, "getEndTime", "()Ljava/lang/String;");
    jstring id = static_cast<jstring>(env->CallObjectMethod(option, idMd));
    jstring start_time = static_cast<jstring>(env->CallObjectMethod(option, startTimeMd));
    jstring end_time = static_cast<jstring>(env->CallObjectMethod(option, endTimeMd));
    const char *c_id = env->GetStringUTFChars(id, nullptr);
    const char *c_start_time = env->GetStringUTFChars(start_time, nullptr);
    const char *c_end_time = env->GetStringUTFChars(end_time, nullptr);
    jmethodID addMembersMd = env->GetMethodID(clazz, "getAddMembers", "()Ljava/util/List;");
    jobject addObj = env->CallObjectMethod(option, addMembersMd);
    jclass addClazz = env->GetObjectClass(addObj);
    jmethodID addMdGet = env->GetMethodID(addClazz, "get", "(I)Ljava/lang/Object;");
    jmethodID addMdSize = env->GetMethodID(addClazz, "size", "()I");
    jint addSize = env->CallIntMethod(addObj, addMdSize);
    vector<string> addMembers;
    for (int i = 0; i < addSize; i++) {
        jobject obj = env->CallObjectMethod(addObj, addMdGet, i);
        const char *a_id = env->GetStringUTFChars((jstring) obj, nullptr);
        addMembers.emplace_back(string(a_id));
        env->ReleaseStringUTFChars((jstring) obj, a_id);
        env->DeleteLocalRef(obj);
    }
    vector<string> deleteMembers;
    jmethodID deleteMembersMd = env->GetMethodID(clazz, "getDeleteMembers", "()Ljava/util/List;");
    jobject deleteObj = env->CallObjectMethod(option, deleteMembersMd);
    jclass deleteClazz = env->GetObjectClass(deleteObj);
    jmethodID deleteMdGet = env->GetMethodID(deleteClazz, "get", "(I)Ljava/lang/Object;");
    jmethodID deleteMdSize = env->GetMethodID(deleteClazz, "size", "()I");
    jint deleteSize = env->CallIntMethod(deleteObj, deleteMdSize);
    for (int i = 0; i < deleteSize; i++) {
        jobject obj = env->CallObjectMethod(deleteObj, deleteMdGet, i);
        const char *d_id = env->GetStringUTFChars((jstring) obj, nullptr);
        deleteMembers.emplace_back(string(d_id));
        env->ReleaseStringUTFChars((jstring) obj, d_id);
        env->DeleteLocalRef(obj);
    }
    CollaborationUpdateOption c_option = {c_id, c_start_time, c_end_time, addMembers,
                                          deleteMembers};
    auto handler = reinterpret_cast<signaling::IClient *>(m_native_handle);
    auto result = handler->updateCollaboration(c_option);
    env->ReleaseStringUTFChars(id, c_id);
    env->ReleaseStringUTFChars(start_time, c_start_time);
    env->ReleaseStringUTFChars(end_time, c_end_time);
    env->DeleteLocalRef(addObj);
    env->DeleteLocalRef(addClazz);
    env->DeleteLocalRef(deleteObj);
    env->DeleteLocalRef(deleteClazz);
    env->DeleteLocalRef(clazz);
    if (result != FUNCTION_INVOKE_SUCCESS) {
        LOGE("Java_com_hiscene_signaling_SignalingEngine_nativeUpdateCollaboration 调用失败");
        return result;
    }
    return result;
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_hiscene_signaling_SignalingClient_nativeaConfirmCollaborationInvite(JNIEnv *env,
                                                                             jobject thiz,
                                                                             jlong m_native_handle,
                                                                             jstring collaboration_id,
                                                                             jboolean is_accept) {
    LOGD("Java_com_hiscene_signaling_SignalingEngine_nativeaConfirmCollaborationInvite");
    if (gHandler == nullptr) {
        LOGE("尚未未初始化...");
        return UNINIT_FAIL;
    }
    const char *c_collaboration_id = env->GetStringUTFChars(collaboration_id, nullptr);
    auto handler = reinterpret_cast<signaling::IClient *>(m_native_handle);
    auto result = handler->confirmCollaborationInvite(c_collaboration_id, is_accept);
    env->ReleaseStringUTFChars(collaboration_id, c_collaboration_id);
    if (result != FUNCTION_INVOKE_SUCCESS) {
        LOGE("Java_com_hiscene_signaling_SignalingEngine_nativeaConfirmCollaborationInvite 调用失败");
    }
    return result;
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_hiscene_signaling_SignalingClient_nativeStartCollaboration(JNIEnv *env, jobject thiz,
                                                                    jlong m_native_handle,
                                                                    jstring collaboration_id) {
    LOGD("Java_com_hiscene_signaling_SignalingEngine_nativeStartCollaboration");
    if (gHandler == nullptr) {
        LOGE("尚未未初始化...");
        return UNINIT_FAIL;
    }
    const char *c_collaboration_id = env->GetStringUTFChars(collaboration_id, nullptr);
    auto handler = reinterpret_cast<signaling::IClient *>(m_native_handle);
    auto result = handler->startCollaboration(c_collaboration_id);
    env->ReleaseStringUTFChars(collaboration_id, c_collaboration_id);
    return result;

}
extern "C"
JNIEXPORT jint JNICALL
Java_com_hiscene_signaling_SignalingClient_nativeCreateInstantSession(JNIEnv *env, jobject thiz,
                                                                      jlong m_native_handle,
                                                                      jobjectArray user_ids) {
    LOGD("Java_com_hiscene_signaling_SignalingEngine_nativeCreateInstantSession");
    if (gHandler == nullptr) {
        LOGE("尚未未初始化...");
        return UNINIT_FAIL;
    }
    jint size = env->GetArrayLength(user_ids);
    vector<string> c_u_ids;
    for (int i = 0; i < size; i++) {
        jobject obj = env->GetObjectArrayElement(user_ids, i);
        const char *c_u_id = env->GetStringUTFChars((jstring) obj, nullptr);
        c_u_ids.emplace_back(string(c_u_id));
        env->ReleaseStringUTFChars((jstring) obj, c_u_id);
        env->DeleteLocalRef(obj);
    }
    auto handler = reinterpret_cast<signaling::IClient *>(m_native_handle);
    auto result = handler->createInstantSession(c_u_ids);
    return result;
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_hiscene_signaling_SignalingClient_nativeAnswerSession(JNIEnv *env, jobject thiz,
                                                               jlong m_native_handle,
                                                               jint call_id) {
    LOGD("Java_com_hiscene_signaling_SignalingEngine_nativeAnswerSession");
    if (gHandler == nullptr) {
        LOGE("尚未未初始化...");
        return UNINIT_FAIL;
    }
    auto handler = reinterpret_cast<signaling::IClient *>(m_native_handle);
    auto result = handler->answerSession(call_id);
    if (result != FUNCTION_INVOKE_SUCCESS) {
        LOGE("Java_com_hiscene_signaling_SignalingEngine_nativeAnswerSession 调用失败");
    }
    return result;
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_hiscene_signaling_SignalingClient_nativeDeclineSession(JNIEnv *env, jobject thiz,
                                                                jlong m_native_handle,
                                                                jint call_id) {
    LOGD("Java_com_hiscene_signaling_SignalingEngine_nativeDeclineSession");
    if (gHandler == nullptr) {
        LOGE("尚未未初始化...");
        return UNINIT_FAIL;
    }
    auto handler = reinterpret_cast<signaling::IClient *>(m_native_handle);
    auto result = handler->declineSession(call_id);
    if (result != FUNCTION_INVOKE_SUCCESS) {
        LOGE("Java_com_hiscene_signaling_SignalingEngine_nativeDeclineSession 调用失败");
    }
    return result;
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_hiscene_signaling_SignalingClient_nativeLeaveSession(JNIEnv *env, jobject thiz,
                                                              jlong m_native_handle, jint call_id) {
    LOGD("Java_com_hiscene_signaling_SignalingEngine_nativeLeaveSession");
    if (gHandler == nullptr) {
        LOGE("尚未未初始化...");
        return UNINIT_FAIL;
    }
    auto handler = reinterpret_cast<signaling::IClient *>(m_native_handle);
    auto result = handler->leaveSession(call_id);
    if (result != FUNCTION_INVOKE_SUCCESS) {
        LOGE("Java_com_hiscene_signaling_SignalingEngine_nativeLeaveSession 调用失败");
    }
    return result;
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_hiscene_signaling_SignalingClient_nativeEndSession(JNIEnv *env, jobject thiz,
                                                            jlong m_native_handle, jint call_id) {
    LOGD("Java_com_hiscene_signaling_SignalingEngine_nativeEndSession");
    if (gHandler == nullptr) {
        LOGE("尚未未初始化...");
        return UNINIT_FAIL;
    }
    auto handler = reinterpret_cast<signaling::IClient *>(m_native_handle);
    auto result = handler->endSession(call_id);
    if (result != FUNCTION_INVOKE_SUCCESS) {
        LOGE("Java_com_hiscene_signaling_SignalingEngine_nativeEndSession 调用失败");
    }
    return result;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_hiscene_signaling_SignalingClient_nativeSetJoinMediaResult(JNIEnv *env, jobject thiz,
                                                                    jlong m_native_handle,
                                                                    jint call_id,
                                                                    jboolean is_succeeded) {
    LOGD("Java_com_hiscene_signaling_SignalingEngine_nativeSetJoinMediaResult");
    if (gHandler == nullptr) {
        LOGE("尚未未初始化...");
        return;
    }
    auto handler = reinterpret_cast<signaling::IClient *>(m_native_handle);
    handler->setJoinMediaResult(call_id, is_succeeded);
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_hiscene_signaling_SignalingClient_nativeGetSessionState(JNIEnv *env, jobject thiz,
                                                                 jlong m_native_handle,
                                                                 jint call_id) {
    LOGD("Java_com_hiscene_signaling_SignalingEngine_nativeGetSessionState");
    if (gHandler == nullptr) {
        LOGE("尚未未初始化...");
        return UNINIT_FAIL;
    }
    auto handler = reinterpret_cast<signaling::IClient *>(m_native_handle);
    SessionState outState;
    auto result = handler->getSessionState(call_id, outState);
    if (result != FUNCTION_INVOKE_SUCCESS) {
        LOGE("Java_com_hiscene_signaling_SignalingEngine_nativeGetSessionState 调用失败");
        return result;
    }
    return outState;
}
extern "C"
JNIEXPORT jobjectArray JNICALL
Java_com_hiscene_signaling_SignalingClient_nativeGetSessionMembers(JNIEnv *env, jobject thiz,
                                                                   jlong m_native_handle,
                                                                   jint call_id) {
    LOGD("Java_com_hiscene_signaling_SignalingEngine_nativeGetSessionMembers");
    if (gHandler == nullptr) {
        LOGE("尚未未初始化...");
        return nullptr;
    }
    auto handler = reinterpret_cast<signaling::IClient *>(m_native_handle);
    vector<SessionMemberInfo> memberInfos;
    auto result = handler->getSessionMembers(call_id, memberInfos);
    if (result != FUNCTION_INVOKE_SUCCESS) {
        LOGE("Java_com_hiscene_signaling_SignalingEngine_nativeGetSessionState 调用失败");
        return nullptr;
    }
    auto size = memberInfos.size();
    auto clazz = env->FindClass("com/hiscene/signaling/entity/SessionMemberInfo");
    auto construtMethod = env->GetMethodID(clazz, "<init>", "(Ljava/lang/String;II[I)V");
    auto objArray = env->NewObjectArray(size, clazz, nullptr);
    for (int i = 0; i < size; ++i) {
        auto memberInfo = memberInfos[i];
        auto memberId = env->NewStringUTF(memberInfo.memberId.c_str());
        jint sessionState = memberInfo.sessionState;
        jint deviceType = memberInfo.deviceType;
        set<MediaDeviceType> activeMediaDevices = memberInfo.activeMediaDevices;
        jintArray j_activeMediaDevices = env->NewIntArray(activeMediaDevices.size());
        jint *c_activeMediaDevices = env->GetIntArrayElements(j_activeMediaDevices, NULL);
        int j = 0;
        for (const auto &item : activeMediaDevices) {
            c_activeMediaDevices[j] = item;
            j++;
        }
        auto obj = env->NewObject(clazz, construtMethod, memberId, sessionState, deviceType,
                                  j_activeMediaDevices);
        env->SetObjectArrayElement(objArray, i, obj);
        env->ReleaseIntArrayElements(j_activeMediaDevices, c_activeMediaDevices, NULL);
    }
    env->DeleteLocalRef(clazz);
    return objArray;
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_hiscene_signaling_SignalingClient_nativeGetSessionInviter(JNIEnv *env, jobject thiz,
                                                                   jlong m_native_handle,
                                                                   jint call_id) {
    LOGD("Java_com_hiscene_signaling_SignalingClient_nativeGetSessionInviter");
    if (gHandler == nullptr) {
        LOGE("尚未未初始化...");
        return nullptr;
    }
    auto handler = reinterpret_cast<signaling::IClient *>(m_native_handle);
    std::string outInviterId;
    auto result = handler->getSessionInviter(call_id,outInviterId);
    if (result != FUNCTION_INVOKE_SUCCESS) {
        LOGE("Java_com_hiscene_signaling_SignalingEngine_nativeMediaDeviceControl 调用失败");
        return nullptr;
    }
    return env->NewStringUTF(outInviterId.c_str());
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_hiscene_signaling_SignalingClient_nativeMediaDeviceControl(JNIEnv *env, jobject thiz,
                                                                    jlong m_native_handle,
                                                                    jint call_id, jstring member_id,
                                                                    jint type, jboolean open) {
    LOGD("Java_com_hiscene_signaling_SignalingEngine_nativeMediaDeviceControl");
    if (gHandler == nullptr) {
        LOGE("尚未未初始化...");
        return UNINIT_FAIL;
    }
    auto handler = reinterpret_cast<signaling::IClient *>(m_native_handle);
    const char *c_member_id = env->GetStringUTFChars(member_id, nullptr);
    enum MediaDeviceType {
        kMediaDeviceUnknow = 0,
        kMediaDeviceMicrophone = 1,
        kMediaDeviceSpeaker = 2,
        kMediaDeviceCamera = 3,
    };
    MediaDeviceType mediaDeviceType = MediaDeviceType::kMediaDeviceUnknow;
    switch (type) {
        case 1:
            mediaDeviceType = MediaDeviceType::kMediaDeviceMicrophone;
            break;
        case 2:
            mediaDeviceType = MediaDeviceType::kMediaDeviceSpeaker;
            break;
        case 3:
            mediaDeviceType = MediaDeviceType::kMediaDeviceCamera;
            break;
    }
    const signaling::MediaDeviceType deviceType = static_cast<const signaling::MediaDeviceType>(mediaDeviceType);
    auto result = handler->mediaDeviceControl(call_id, c_member_id, deviceType, open);
    env->ReleaseStringUTFChars(member_id, c_member_id);
    if (result != FUNCTION_INVOKE_SUCCESS) {
        LOGE("Java_com_hiscene_signaling_SignalingEngine_nativeMediaDeviceControl 调用失败");
    }
    return result;
}

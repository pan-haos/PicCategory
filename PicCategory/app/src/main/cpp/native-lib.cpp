#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_we_piccategory_util_NdkUtils_getEncrypt(JNIEnv *env, jclass type) {

    std::string word = "Hello from C++";

    return env->NewStringUTF(word.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_we_piccategory_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

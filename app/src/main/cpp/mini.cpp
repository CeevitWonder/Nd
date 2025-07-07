#include <jni.h>
#include <string.h>
#include <android/log.h>
#include <stdlib.h>

#define LOG(...) __android_log_print(ANDROID_LOG_INFO, "mini", __VA_ARGS__)
#define MAX_RULES 20000
static char* rules[MAX_RULES];
static int rule_count = 0;

extern "C" JNIEXPORT jboolean JNICALL
Java_com_example_minibrowser_Mini_shouldLoadUrl(JNIEnv* e, jclass, jstring ju) {
  const char* url = e->GetStringUTFChars(ju, 0);
  bool ok = strncasecmp(url, "https://", 8) == 0;
  if (ok) {
    for (int i = 0; i < rule_count; i++) {
      if (strstr(url, rules[i])) { ok = false; break; }
    }
  }
  LOG("URL %s -> %s", url, ok ? "load" : "block");
  e->ReleaseStringUTFChars(ju, url);
  return ok;
}

extern "C" JNIEXPORT void JNICALL
Java_com_example_minibrowser_Mini_handleDownload(JNIEnv* e, jclass, jstring ju) {
  const char* url = e->GetStringUTFChars(ju, 0);
  LOG("Download requested: %s", url);
  e->ReleaseStringUTFChars(ju, url);
}

extern "C" JNIEXPORT void JNICALL
Java_com_example_minibrowser_Mini_onFilterDownloadComplete(JNIEnv* e, jclass, jobjectArray ja) {
  for (int i=0; i<rule_count; ++i) free(rules[i]);
  rule_count = 0;
  int n = e->GetArrayLength(ja);
  for (int i=0; i<n && rule_count<MAX_RULES; ++i) {
    jstring js = (jstring)e->GetObjectArrayElement(ja, i);
    const char* s = e->GetStringUTFChars(js, 0);
    rules[rule_count++] = strdup(s);
    e->ReleaseStringUTFChars(js, s);
  }
  LOG("Native loaded %d rules", rule_count);
}

jint JNI_OnLoad(JavaVM* vm, void* _) {
  return JNI_VERSION_1_6;
}

#include <jni.h>
#include <string>
#include <sstream>
#include <logger.h>

#include "player/mp3_player.h"

extern "C" {
#include "libavutil/version.h"
#include "libavformat/avformat.h"
}

extern "C"
JNIEXPORT jstring

JNICALL
Java_cc_imorning_ffmpeg_MainActivity_getVideoInfo(JNIEnv *env, jobject thiz, jstring path) {
    const char *fp = env->GetStringUTFChars(path, nullptr);
    if (fopen(fp, "r") == nullptr) {
        LOG_E("file [%s] not exist", fp);
        return env->NewStringUTF("file not exist");
    }
    AVFormatContext *pFormatCtx = avformat_alloc_context();
    if (avformat_open_input(&pFormatCtx, fp, nullptr, nullptr) != 0) {
        return env->NewStringUTF("Couldn't open input stream");
    }
    if (avformat_find_stream_info(pFormatCtx, nullptr) < 0) {
        return env->NewStringUTF("Couldn't find stream information");
    }
    std::stringstream fmt;
    fmt << "duration = " << (double) pFormatCtx->duration / 1000000 << "\n" <<
        "bitrate = " << (double) pFormatCtx->bit_rate / 1000;
    return env->NewStringUTF(fmt.str().c_str());
}
cmake_minimum_required(VERSION 3.22.1)

project("ffmpeg")

if(ANDROID_ABI STREQUAL armeabi-v7a)
    set_target_properties(${TARGET} PROPERTIES COMPILE_FLAGS -mfpu=neon)
endif()

aux_source_directory(./libmp3lame/ LAME_SOURCES)
aux_source_directory(./player/ PLAYER_SOURCES)

add_library(ffmpeg
        SHARED
        ${PLAYER_SOURCES} ffmpeg.cpp)

add_library(mp3lame
        SHARED
        ${LAME_SOURCES} lame.cpp)

include_directories(${CMAKE_SOURCE_DIR}/include/)

# ffmpeg library
add_library(libavcodec SHARED IMPORTED)
add_library(libavfilter SHARED IMPORTED)
add_library(libavformat SHARED IMPORTED)
add_library(libavutil SHARED IMPORTED)
add_library(libpostproc SHARED IMPORTED)
add_library(libswresample SHARED IMPORTED)
add_library(libswscale SHARED IMPORTED)
set_target_properties(libavcodec PROPERTIES IMPORTED_LOCATION ${CMAKE_SOURCE_DIR}/../jniLibs/${ANDROID_ABI}/libavcodec.so)
set_target_properties(libavfilter PROPERTIES IMPORTED_LOCATION ${CMAKE_SOURCE_DIR}/../jniLibs/${ANDROID_ABI}/libavfilter.so)
set_target_properties(libavformat PROPERTIES IMPORTED_LOCATION ${CMAKE_SOURCE_DIR}/../jniLibs/${ANDROID_ABI}/libavformat.so)
set_target_properties(libavutil PROPERTIES IMPORTED_LOCATION ${CMAKE_SOURCE_DIR}/../jniLibs/${ANDROID_ABI}/libavutil.so)
set_target_properties(libpostproc PROPERTIES IMPORTED_LOCATION ${CMAKE_SOURCE_DIR}/../jniLibs/${ANDROID_ABI}/libpostproc.so)
set_target_properties(libswresample PROPERTIES IMPORTED_LOCATION ${CMAKE_SOURCE_DIR}/../jniLibs/${ANDROID_ABI}/libswresample.so)
set_target_properties(libswscale PROPERTIES IMPORTED_LOCATION ${CMAKE_SOURCE_DIR}/../jniLibs/${ANDROID_ABI}/libswscale.so)

find_library(log-lib
        log)

target_link_libraries(ffmpeg
        libavfilter
        libavformat
        libavcodec
        libswscale
        libswresample
        libpostproc
        libavutil
        ${log-lib}
        android)

target_link_libraries(mp3lame
        ${log-lib}
        android)
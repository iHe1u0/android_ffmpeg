cmake_minimum_required(VERSION 3.22.1)

project("ffmpeg")

aux_source_directory(./ SOURCES)
aux_source_directory(./player/ PLAYER)

add_library( # Sets the name of the library.
        ffmpeg
        SHARED
        ${SOURCES}
        ${PLAYER})

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
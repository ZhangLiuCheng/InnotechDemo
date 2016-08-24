LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_LDLIBS :=-llog

LOCAL_MODULE := nativeUtil

LOCAL_SRC_FILES := NativeUtil.cpp

include $(BUILD_SHARED_LIBRARY)

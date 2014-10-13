LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE    := HelloJni
LOCAL_SRC_FILES := com_example_androiddemo_activity_HelloJniActivity.c
include $(BUILD_SHARED_LIBRARY)
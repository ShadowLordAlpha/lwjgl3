/*
 * Copyright LWJGL. All rights reserved.
 * License terms: http://lwjgl.org/license.php
 * MACHINE GENERATED FILE, DO NOT EDIT
 */
#include "common_tools.h"
DISABLE_WARNINGS()
#ifdef LWJGL_WINDOWS
	__pragma(warning(disable : 4701 4702 4711 4738))
#endif
#define STB_IMAGE_RESIZE_IMPLEMENTATION
#define STB_IMAGE_RESIZE_STATIC
#define STBIR_ASSERT(x)
#define STBIR_MALLOC(size,c) lwjgl_malloc(size)
#define STBIR_FREE(ptr,c)    lwjgl_free(ptr)
#include "stb_image_resize.h"
ENABLE_WARNINGS()

EXTERN_C_ENTER

JNIEXPORT jint JNICALL Java_org_lwjgl_stb_STBImageResize_nstbir_1resize_1uint8(JNIEnv *__env, jclass clazz, jlong input_pixelsAddress, jint input_w, jint input_h, jint input_stride_in_bytes, jlong output_pixelsAddress, jint output_w, jint output_h, jint output_stride_in_bytes, jint num_channels) {
	const unsigned char *input_pixels = (const unsigned char *)(intptr_t)input_pixelsAddress;
	unsigned char *output_pixels = (unsigned char *)(intptr_t)output_pixelsAddress;
	UNUSED_PARAMS(__env, clazz)
	return (jint)stbir_resize_uint8(input_pixels, input_w, input_h, input_stride_in_bytes, output_pixels, output_w, output_h, output_stride_in_bytes, num_channels);
}

JNIEXPORT jint JNICALL Java_org_lwjgl_stb_STBImageResize_nstbir_1resize_1float(JNIEnv *__env, jclass clazz, jlong input_pixelsAddress, jint input_w, jint input_h, jint input_stride_in_bytes, jlong output_pixelsAddress, jint output_w, jint output_h, jint output_stride_in_bytes, jint num_channels) {
	const float *input_pixels = (const float *)(intptr_t)input_pixelsAddress;
	float *output_pixels = (float *)(intptr_t)output_pixelsAddress;
	UNUSED_PARAMS(__env, clazz)
	return (jint)stbir_resize_float(input_pixels, input_w, input_h, input_stride_in_bytes, output_pixels, output_w, output_h, output_stride_in_bytes, num_channels);
}

JNIEXPORT jint JNICALL Java_org_lwjgl_stb_STBImageResize_nstbir_1resize_1uint8_1srgb(JNIEnv *__env, jclass clazz, jlong input_pixelsAddress, jint input_w, jint input_h, jint input_stride_in_bytes, jlong output_pixelsAddress, jint output_w, jint output_h, jint output_stride_in_bytes, jint num_channels, jint alpha_channel, jint flags) {
	const unsigned char *input_pixels = (const unsigned char *)(intptr_t)input_pixelsAddress;
	unsigned char *output_pixels = (unsigned char *)(intptr_t)output_pixelsAddress;
	UNUSED_PARAMS(__env, clazz)
	return (jint)stbir_resize_uint8_srgb(input_pixels, input_w, input_h, input_stride_in_bytes, output_pixels, output_w, output_h, output_stride_in_bytes, num_channels, alpha_channel, flags);
}

JNIEXPORT jint JNICALL Java_org_lwjgl_stb_STBImageResize_nstbir_1resize_1uint8_1srgb_1edgemode(JNIEnv *__env, jclass clazz, jlong input_pixelsAddress, jint input_w, jint input_h, jint input_stride_in_bytes, jlong output_pixelsAddress, jint output_w, jint output_h, jint output_stride_in_bytes, jint num_channels, jint alpha_channel, jint flags, jint edge_wrap_mode) {
	const unsigned char *input_pixels = (const unsigned char *)(intptr_t)input_pixelsAddress;
	unsigned char *output_pixels = (unsigned char *)(intptr_t)output_pixelsAddress;
	UNUSED_PARAMS(__env, clazz)
	return (jint)stbir_resize_uint8_srgb_edgemode(input_pixels, input_w, input_h, input_stride_in_bytes, output_pixels, output_w, output_h, output_stride_in_bytes, num_channels, alpha_channel, flags, edge_wrap_mode);
}

JNIEXPORT jint JNICALL Java_org_lwjgl_stb_STBImageResize_nstbir_1resize_1uint8_1generic(JNIEnv *__env, jclass clazz, jlong input_pixelsAddress, jint input_w, jint input_h, jint input_stride_in_bytes, jlong output_pixelsAddress, jint output_w, jint output_h, jint output_stride_in_bytes, jint num_channels, jint alpha_channel, jint flags, jint edge_wrap_mode, jint filter, jint space, jlong alloc_contextAddress) {
	const unsigned char *input_pixels = (const unsigned char *)(intptr_t)input_pixelsAddress;
	unsigned char *output_pixels = (unsigned char *)(intptr_t)output_pixelsAddress;
	void *alloc_context = (void *)(intptr_t)alloc_contextAddress;
	UNUSED_PARAMS(__env, clazz)
	return (jint)stbir_resize_uint8_generic(input_pixels, input_w, input_h, input_stride_in_bytes, output_pixels, output_w, output_h, output_stride_in_bytes, num_channels, alpha_channel, flags, edge_wrap_mode, filter, space, alloc_context);
}

JNIEXPORT jint JNICALL Java_org_lwjgl_stb_STBImageResize_nstbir_1resize_1uint16_1generic(JNIEnv *__env, jclass clazz, jlong input_pixelsAddress, jint input_w, jint input_h, jint input_stride_in_bytes, jlong output_pixelsAddress, jint output_w, jint output_h, jint output_stride_in_bytes, jint num_channels, jint alpha_channel, jint flags, jint edge_wrap_mode, jint filter, jint space, jlong alloc_contextAddress) {
	const stbir_uint16 *input_pixels = (const stbir_uint16 *)(intptr_t)input_pixelsAddress;
	stbir_uint16 *output_pixels = (stbir_uint16 *)(intptr_t)output_pixelsAddress;
	void *alloc_context = (void *)(intptr_t)alloc_contextAddress;
	UNUSED_PARAMS(__env, clazz)
	return (jint)stbir_resize_uint16_generic(input_pixels, input_w, input_h, input_stride_in_bytes, output_pixels, output_w, output_h, output_stride_in_bytes, num_channels, alpha_channel, flags, edge_wrap_mode, filter, space, alloc_context);
}

JNIEXPORT jint JNICALL Java_org_lwjgl_stb_STBImageResize_nstbir_1resize_1float_1generic(JNIEnv *__env, jclass clazz, jlong input_pixelsAddress, jint input_w, jint input_h, jint input_stride_in_bytes, jlong output_pixelsAddress, jint output_w, jint output_h, jint output_stride_in_bytes, jint num_channels, jint alpha_channel, jint flags, jint edge_wrap_mode, jint filter, jint space, jlong alloc_contextAddress) {
	const float *input_pixels = (const float *)(intptr_t)input_pixelsAddress;
	float *output_pixels = (float *)(intptr_t)output_pixelsAddress;
	void *alloc_context = (void *)(intptr_t)alloc_contextAddress;
	UNUSED_PARAMS(__env, clazz)
	return (jint)stbir_resize_float_generic(input_pixels, input_w, input_h, input_stride_in_bytes, output_pixels, output_w, output_h, output_stride_in_bytes, num_channels, alpha_channel, flags, edge_wrap_mode, filter, space, alloc_context);
}

JNIEXPORT jint JNICALL Java_org_lwjgl_stb_STBImageResize_nstbir_1resize(JNIEnv *__env, jclass clazz, jlong input_pixelsAddress, jint input_w, jint input_h, jint input_stride_in_bytes, jlong output_pixelsAddress, jint output_w, jint output_h, jint output_stride_in_bytes, jint datatype, jint num_channels, jint alpha_channel, jint flags, jint edge_mode_horizontal, jint edge_mode_vertical, jint filter_horizontal, jint filter_vertical, jint space, jlong alloc_contextAddress) {
	const void *input_pixels = (const void *)(intptr_t)input_pixelsAddress;
	void *output_pixels = (void *)(intptr_t)output_pixelsAddress;
	void *alloc_context = (void *)(intptr_t)alloc_contextAddress;
	UNUSED_PARAMS(__env, clazz)
	return (jint)stbir_resize(input_pixels, input_w, input_h, input_stride_in_bytes, output_pixels, output_w, output_h, output_stride_in_bytes, datatype, num_channels, alpha_channel, flags, edge_mode_horizontal, edge_mode_vertical, filter_horizontal, filter_vertical, space, alloc_context);
}

JNIEXPORT jint JNICALL Java_org_lwjgl_stb_STBImageResize_nstbir_1resize_1subpixel(JNIEnv *__env, jclass clazz, jlong input_pixelsAddress, jint input_w, jint input_h, jint input_stride_in_bytes, jlong output_pixelsAddress, jint output_w, jint output_h, jint output_stride_in_bytes, jint datatype, jint num_channels, jint alpha_channel, jint flags, jint edge_mode_horizontal, jint edge_mode_vertical, jint filter_horizontal, jint filter_vertical, jint space, jlong alloc_contextAddress, jfloat x_scale, jfloat y_scale, jfloat x_offset, jfloat y_offset) {
	const void *input_pixels = (const void *)(intptr_t)input_pixelsAddress;
	void *output_pixels = (void *)(intptr_t)output_pixelsAddress;
	void *alloc_context = (void *)(intptr_t)alloc_contextAddress;
	UNUSED_PARAMS(__env, clazz)
	return (jint)stbir_resize_subpixel(input_pixels, input_w, input_h, input_stride_in_bytes, output_pixels, output_w, output_h, output_stride_in_bytes, datatype, num_channels, alpha_channel, flags, edge_mode_horizontal, edge_mode_vertical, filter_horizontal, filter_vertical, space, alloc_context, x_scale, y_scale, x_offset, y_offset);
}

JNIEXPORT jint JNICALL Java_org_lwjgl_stb_STBImageResize_nstbir_1resize_1region(JNIEnv *__env, jclass clazz, jlong input_pixelsAddress, jint input_w, jint input_h, jint input_stride_in_bytes, jlong output_pixelsAddress, jint output_w, jint output_h, jint output_stride_in_bytes, jint datatype, jint num_channels, jint alpha_channel, jint flags, jint edge_mode_horizontal, jint edge_mode_vertical, jint filter_horizontal, jint filter_vertical, jint space, jlong alloc_contextAddress, jfloat s0, jfloat t0, jfloat s1, jfloat t1) {
	const void *input_pixels = (const void *)(intptr_t)input_pixelsAddress;
	void *output_pixels = (void *)(intptr_t)output_pixelsAddress;
	void *alloc_context = (void *)(intptr_t)alloc_contextAddress;
	UNUSED_PARAMS(__env, clazz)
	return (jint)stbir_resize_region(input_pixels, input_w, input_h, input_stride_in_bytes, output_pixels, output_w, output_h, output_stride_in_bytes, datatype, num_channels, alpha_channel, flags, edge_mode_horizontal, edge_mode_vertical, filter_horizontal, filter_vertical, space, alloc_context, s0, t0, s1, t1);
}

EXTERN_C_EXIT

/*
 * Copyright LWJGL. All rights reserved.
 * License terms: http://lwjgl.org/license.php
 */
package org.lwjgl.opencl.templates

import org.lwjgl.generator.*
import org.lwjgl.opencl.extensionLink
import org.lwjgl.opencl.nativeClassCL

val intel_egl_image_yuv = dependsOn(Binding.EGL) {
	"INTELEGLImageYUV".nativeClassCL("intel_egl_image_yuv", INTEL) {
		documentation =
			"""
			Native bindings to the $extensionLink extension.

			The goal of this extension is to increase interoperability between OpenCL and EGL by introducing support for planar YUV images. Specifically, this
			extension adds the ability to create OpenCL memory objects representing individual planes of an EGL planar YUV image.

			Requires ${CL12.link} and ${khr_egl_image!!.link}.
			"""

		IntConstant(
			"""
			Accepted as property in {@code properties} parameter of function KHREGLImage#CreateFromEGLImageKHR() and as {@code param_name} parameter of function
			CL10#GetImageInfo().
			""",

			"EGL_YUV_PLANE_INTEL"..0x4107
		)
	}
}
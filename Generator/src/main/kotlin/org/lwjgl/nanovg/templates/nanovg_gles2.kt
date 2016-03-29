/*
 * Copyright LWJGL. All rights reserved.
 * License terms: http://lwjgl.org/license.php
 */
package org.lwjgl.nanovg.templates

import org.lwjgl.generator.*
import org.lwjgl.nanovg.*
import org.lwjgl.opengl.GLuint

val nanovg_gles2 = dependsOn(Binding.OPENGLES) {
	"NanoVGGLES2".nativeClass(packageName = NANOVG_PACKAGE, prefix = "NVG") {
		nativeDirective(
			"""#ifdef LWJGL_WINDOWS
	__pragma(warning(disable : 4710 4711))
#endif""", beforeIncludes = true)

		includeNanoVGAPI("""#define NANOVG_GLES2_IMPLEMENTATION
#include "nanovg.h"
#include "nanovg_gl.h"
#include "nanovg_gl_utils.h"""")

		documentation = "Implementation of the NanoVG API using OpenGL ES 2.0."

		val CreateFlags = EnumConstant(
			"Create flags.",

			"ANTIALIAS".enumExpr("Flag indicating if geometry based anti-aliasing is used (may not be needed when using MSAA).", "1<<0"),
			"STENCIL_STROKES".enumExpr(
				"""
				Flag indicating if strokes should be drawn using stencil buffer. The rendering will be a little slower, but path overlaps (i.e.
				self-intersecting or sharp turns) will be drawn just once.
				""",
				"1<<1"
			),
			"DEBUG".enumExpr("Flag indicating that additional debug checks are done.", "1<<2")
		).javaDocLinks

		EnumConstant(
			"These are additional flags on top of NVGimageFlags.",

			"IMAGE_NODELETE".enumExpr("Do not delete GL texture handle.", "1<<16")
		)

		val ctx = NVGcontext_p.IN("ctx", "the NanoVG context")

		int(
			"lCreateImageFromHandleGLES2",
			"Creates a NanoVG image from an OpenGL texture.",

			ctx,
			GLuint.IN("textureId", "the OpenGL texture id"),
			int.IN("w", "the image width"),
			int.IN("h", "the image height"),
			int.IN("flags", "the image flags")
		)

		GLuint(
			"lImageHandleGLES2",
			"Returns the OpenGL texture id associated with a NanoVG image.",

			ctx,
			int.IN("image", "the image handle")
		)

		Code(
			nativeCall = "\treturn (jlong)(intptr_t)nvgCreateGLES2($JNIENV, flags);"
		)..NVGcontext_p(
			"CreateGLES2",
			"""
			Creates a NanoVG context with an OpenGL ES 2.0 rendering back-end.

			An OpenGL ES 2.0+ context must be current in the current thread when this function is called and the returned NanoVG context may only be used in
			the thread in which that OpenGL context is current.
			""",

			int.IN("flags", "the context flags", CreateFlags)
		)

		void(
			"DeleteGLES2",
			"Deletes a NanoVG context created with #CreateGLES2().",

			ctx
		)

		NVGLUframebuffer_p(
			"luCreateFramebuffer",
			"Creates a framebuffer object to render to.",

			ctx,
			int.IN("w", "the framebuffer width"),
			int.IN("h", "the framebuffer height"),
			int.IN("imageFlags", "the image flags")
		)

		void(
			"luBindFramebuffer",
			"Binds the framebuffer object associated with the specified ##NVGLUFramebuffer.",

			ctx,
			nullable..NVGLUframebuffer_p.IN("fb", "the framebuffer to bind")
		)

		void(
			"luDeleteFramebuffer",
			"Deletes an ##NVGLUFramebuffer.",

			ctx,
			NVGLUframebuffer_p.IN("fb", "the framebuffer to delete")
		)
	}
}
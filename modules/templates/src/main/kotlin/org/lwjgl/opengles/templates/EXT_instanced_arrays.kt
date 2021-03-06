/*
 * Copyright LWJGL. All rights reserved.
 * License terms: http://lwjgl.org/license.php
 */
package org.lwjgl.opengles.templates

import org.lwjgl.generator.*
import org.lwjgl.opengles.*
import org.lwjgl.opengles.BufferType.*

val EXT_instanced_arrays = "EXTInstancedArrays".nativeClassGLES("EXT_instanced_arrays", postfix = EXT) {
	documentation =
		"""
		Native bindings to the $registryLink extension.

		A common use case in GL for some applications is to be able to draw the same object, or groups of similar objects that share vertex data, primitive
		count and type, multiple times. This extension provides a means of accelerating such use cases while reducing the number of API calls, and keeping the
		amount of duplicate data to a minimum.

		This extension introduces an array "divisor" for generic vertex array attributes, which when non-zero specifies that the attribute is "instanced." An
		instanced attribute does not advance per-vertex as usual, but rather after every {@code <divisor>} conceptual draw calls.

		(Attributes which aren't instanced are repeated in their entirety for every conceptual draw call.)

		By specifying transform data in an instanced attribute or series of instanced attributes, vertex shaders can, in concert with the instancing draw
		calls, draw multiple instances of an object with one draw call.

		Requires ${GLES20.core}.
		"""

	IntConstant(
		"Accepted by the {@code pname} parameters of GetVertexAttribfv and GetVertexAttribiv.",

		"VERTEX_ATTRIB_ARRAY_DIVISOR_EXT"..0x88FE
	)

	void(
		"DrawArraysInstancedEXT",
		"",

		GLenum.IN("mode", ""),
		GLint.IN("start", ""),
		GLsizei.IN("count", ""),
		GLsizei.IN("primcount", "")
	)

	void(
		"DrawElementsInstancedEXT",
		"",

		GLenum.IN("mode", ""),
		AutoSizeShr("GLESChecks.typeToByteShift(type)", "indices")..GLsizei.IN("count", ""),
		AutoType("indices", GL_UNSIGNED_BYTE, GL_UNSIGNED_SHORT, GL_UNSIGNED_INT)..GLenum.IN("type", ""),
		ELEMENT_ARRAY_BUFFER..const..void_p.IN("indices", ""),
		GLsizei.IN("primcount", "")
	)

	void(
		"VertexAttribDivisorEXT",
		"",

		GLuint.IN("index", ""),
		GLuint.IN("divisor", "")
	)
}
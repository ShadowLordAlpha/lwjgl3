/*
 * Copyright LWJGL. All rights reserved.
 * License terms: http://lwjgl.org/license.php
 */
package org.lwjgl.openal.templates

import org.lwjgl.generator.*
import org.lwjgl.openal.*

val AL_EXT_SOURCE_RADIUS = "EXTSourceRadius".nativeClassAL("EXT_SOURCE_RADIUS") {
	documentation =
		"""
		Native bindings to the $extensionName extension.

		This extension allows any mono source to be changed to be a "large" source with a radius. The source has a raised cosine shape.
		"""

	IntConstant(
		"""
		Can be used with AL10#Sourcef() to set the source radius. Units are consistent with the coordinate system in use. The value must be at least zero. Use
		a value of zero to reset to a point source.
		""",

		"SOURCE_RADIUS"..0x1031
	)
}
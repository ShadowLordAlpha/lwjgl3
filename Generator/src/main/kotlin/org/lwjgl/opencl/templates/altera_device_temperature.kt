/*
 * Copyright LWJGL. All rights reserved.
 * License terms: http://lwjgl.org/license.php
 */
package org.lwjgl.opencl.templates

import org.lwjgl.generator.*
import org.lwjgl.opencl.*

val altera_device_temperature = "ALTERADeviceTemperature".nativeClassCL("altera_device_temperature", ALTERA) {
	documentation =
		"""
		Native bindings to the $extensionName extension.

		This extension specifies an API for querying the current temperature of a given device. The device temperature may be used to diagnose problems with a
		deplyed system, or to enhance its reliability or useful lifetime.
		"""

	IntConstant(
		"Accepted by the {@code param_name} argument of CL10#GetDeviceInfo().",

		"DEVICE_CORE_TEMPERATURE_ALTERA"..0x40F3
	)
}
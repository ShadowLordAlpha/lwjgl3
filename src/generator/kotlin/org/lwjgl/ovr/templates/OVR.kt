/* 
 * Copyright LWJGL. All rights reserved.
 * License terms: http://lwjgl.org/license.php
 */
package org.lwjgl.ovr.templates

import org.lwjgl.generator.*
import org.lwjgl.ovr.*

val OVR = "OVR".nativeClass(packageName = OVR_PACKAGE, prefixMethod = "ovr_", prefixConstant = "ovr") {
	nativeDirective(
"""#ifdef LWJGL_WINDOWS
	__pragma(warning(disable : 4710))
#endif""", beforeIncludes = true)

	nativeDirective(
"""DISABLE_WARNINGS()
#ifdef LWJGL_WINDOWS
	__pragma(warning(disable : 4711 4738))
#endif
#include "OVR_CAPIShim.c"
ENABLE_WARNINGS()""")

	documentation =
		"""
		Native bindings to libOVR, using the <a href="https://developer.oculus.com/">Oculus SDK</a> C API.

		Overview of the API:

		<h3>Setup</h3>
		${ul(
			"#Initialize()",
			"#Create()(&hmd, &graphicsId)",
			"Use hmd members and #GetFovTextureSize() to determine graphics configuration and #GetRenderDesc() to get per-eye rendering parameters.",
			"""
			Allocate texture swap chains with {@code ovr_CreateTextureSwapChainDX()} or OVRGL#CreateTextureSwapChainGL(). Create any associated render target
			views or frame buffer objects.
			"""
		)}

		<h3>Application Loop</h3>
		${ul(
			"Call #GetPredictedDisplayTime() to get the current frame timing information.",
			"Call #GetTrackingState() and OVRUtil#CalcEyePoses() to obtain the predicted rendering pose for each eye based on timing.",
			"""
			Render the scene content into the current buffer of the texture swapchains for each eye and layer you plan to update this frame. If you render into
			a texture swap chain, you must call #CommitTextureSwapChain() on it to commit the changes before you reference the chain this frame (otherwise,
			your latest changes won't be picked up).
			""",
			"""
			Call #SubmitFrame() to render the distorted layers to and present them on the HMD. If #SubmitFrame() returns OVRErrorCode#Success_NotVisible, there
			is no need to render the scene for the next loop iteration. Instead, just call #SubmitFrame() again until it returns OVRErrorCode#Success.
			"""
		)}

		<h3>Shutdown</h3>
		${ul(
			"#Destroy()",
			"#Shutdown()"
		)}
		"""

	IntConstant(
		"Boolean values",

		"False"..0,
		"True"..1
	)

	EnumConstant(
		"Initialization flags. ({@code ovrInitFlags})",

		"Init_Debug".enum(
			"""
			When a debug library is requested, a slower debugging version of the library will run which can be used to help solve problems in the library and
			debug application code.
			""",
			0x00000001
		),
		"Init_RequestVersion".enum(
			"""
			When a version is requested, the LibOVR runtime respects the {@code RequestedMinorVersion} field and verifies that the
			{@code RequestedMinorVersion} is supported.
			""",
			0x00000004
		),
		"Init_WritableBits".enum("These bits are writable by user code.", 0x00ffffff)
	)

	val LogLevels = EnumConstant(
		"Logging levels. ({@code ovrLogLevel})",

		"LogLevel_Debug".enum("Debug-level log event."),
		"LogLevel_Info".enum("Info-level log event."),
		"LogLevel_Error".enum("Error-level log event.")
	).javaDocLinks

	EnumConstant(
		"HMD types. ({@code ovrHmdType})",

		"Hmd_None".enumExpr("", "0"),
		"Hmd_DK1".enumExpr("", "3"),
		"Hmd_DKHD".enum(""),
		"Hmd_DK2".enumExpr("", "6"),
		"Hmd_CB".enumExpr("", "8"),
		"Hmd_Other".enum(""),
		"Hmd_E3_2015".enum(""),
		"Hmd_ES06".enum(""),
		"Hmd_ES09".enum(""),
		"Hmd_ES11".enum(""),
		"Hmd_CV1".enum("")
	)

	EnumConstant(
		"HMD capability bits reported by device. ({@code ovrHmdCaps})",

		"HmdCap_DebugDevice".enum("(read only) Specifies that the HMD is a virtual debug device.", 0x0010)
	).javaDocLinks

	EnumConstant(
		"Tracking capability bits reported by the device. ({@code ovrTrackingCaps})",

		"TrackingCap_Orientation".enum("Supports orientation tracking (IMU).", 0x0010),
		"TrackingCap_MagYawCorrection".enum("Supports yaw drift correction via a magnetometer or other means.", 0x0020),
		"TrackingCap_Position".enum("Supports positional tracking.", 0x0040)
	).javaDocLinks

	val EyeType = EnumConstant(
		"""
		Specifies which eye is being used for rendering. ({@code ovrEyeType})

		This type explicitly does not include a third "NoStereo" monoscopic option, as such is not required for an HMD-centered API.
		""",

		"Eye_Left".enumExpr("The left eye, from the viewer's perspective.", "0"),
		"Eye_Right".enum("The right eye, from the viewer's perspective.")
	).javaDocLinks

	EnumConstant(
		"""
		Specifies the coordinate system ovrTrackingState returns tracking poses in. ({@code ovrTrackingOrigin})

		Used with #SetTrackingOriginType().
		""",

		"TrackingOrigin_EyeLevel".enumExpr(
			"""
			Tracking system origin reported at eye (HMD) height.

			Prefer using this origin when your application requires matching user's current physical head pose to a virtual head pose without any regards to a
			the height of the floor. Cockpit-based, or 3rd-person experiences are ideal candidates. When used, all poses in ##OVRTrackingState are reported as
			an offset transform from the profile calibrated or recentered HMD pose. It is recommended that apps using this origin type call
			#RecenterTrackingOrigin() prior to starting the VR experience, but notify the user before doing so to make sure the user is in a comfortable pose,
			facing a comfortable direction.
			""",
			"0"
		),
		"TrackingOrigin_FloorLevel".enum(
			"""
			Tracking system origin reported at floor height.

			Prefer using this origin when your application requires the physical floor height to match the virtual floor height, such as standing experiences.
			When used, all poses in ##OVRTrackingState are reported as an offset transform from the profile calibrated floor pose. Calling
			#RecenterTrackingOrigin() will recenter the X & Z axes as well as yaw, but the Y-axis (i.e. height) will continue to be reported using the floor
			height as the origin for all poses.
			"""
		)
	).javaDocLinks

	EnumConstant(
		"Bit flags describing the current status of sensor tracking. ({@code ovrStatusBits}",

		"Status_OrientationTracked".enum("Orientation is currently tracked (connected and in use).", 0x0001),
		"Status_PositionTracked".enum("Position is currently tracked (false if out of range).", 0x0002)
	)

	EnumConstant(
		"Specifies sensor flags. ({@code ovrTrackerFlags})",

		"Tracker_Connected".enum("The sensor is present, else the sensor is absent or offline.", 0x0020),
		"Tracker_PoseTracked".enum("The sensor has a valid pose, else the pose is unavailable. This will only be set if ovrTracker_Connected is set.", 0x0004)

	)

	EnumConstant(
		"The type of texture resource. ({@code ovrTextureType})",

		"Texture_2D".enum("2D textures"),
		"Texture_2D_External".enum("External 2D texture. Not used on PC"),
		"Texture_Cube".enum("Cube maps. Not currently supported on PC.")
	)

	EnumConstant(
		"""
		The bindings required for texture swap chain. ({@code ovrTextureBindFlags})
		
		All texture swap chains are automatically bindable as shader input resources since the Oculus runtime needs this to read them.
		""",

		"TextureBind_None".enum(""),
		"TextureBind_DX_RenderTarget".enum("The application can write into the chain with pixel shader", 0x0001),
		"TextureBind_DX_UnorderedAccess".enum("The application can write to the chain with compute shader", 0x0002),
		"TextureBind_DX_DepthStencil".enum("The chain buffers can be bound as depth and/or stencil buffers", 0x0004)
	)

	EnumConstant(
		"The format of a texture. ({@code ovrTextureFormat})",

		"OVR_FORMAT_UNKNOWN".enum(""),
		"OVR_FORMAT_B5G6R5_UNORM".enum("Not currently supported on PC. Would require a DirectX 11.1 device."),
		"OVR_FORMAT_B5G5R5A1_UNORM".enum("Not currently supported on PC. Would require a DirectX 11.1 device."),
		"OVR_FORMAT_B4G4R4A4_UNORM".enum("Not currently supported on PC. Would require a DirectX 11.1 device."),
		"OVR_FORMAT_R8G8B8A8_UNORM".enum(""),
		"OVR_FORMAT_R8G8B8A8_UNORM_SRGB".enum(""),
		"OVR_FORMAT_B8G8R8A8_UNORM".enum(""),
		"OVR_FORMAT_B8G8R8A8_UNORM_SRGB".enum("Not supported for OpenGL applications."),
		"OVR_FORMAT_B8G8R8X8_UNORM".enum("Not supported for OpenGL applications."),
		"OVR_FORMAT_B8G8R8X8_UNORM_SRGB".enum("Not supported for OpenGL applications."),
		"OVR_FORMAT_R16G16B16A16_FLOAT".enum(""),
		"OVR_FORMAT_D16_UNORM".enum(""),
		"OVR_FORMAT_D24_UNORM_S8_UINT".enum(""),
		"OVR_FORMAT_D32_FLOAT".enum(""),
		"OVR_FORMAT_D32_FLOAT_S8X24_UINT".enum("")
	).noPrefix()

	EnumConstant(
		"Misc flags overriding particular behaviors of a texture swap chain. ({@code ovrTextureFlags})",

		"TextureMisc_None".enum(""),
		"TextureMisc_DX_Typeless".enum(
			"""
	        DX only: The underlying texture is created with a TYPELESS equivalent of the format specified in the texture desc. The SDK will still access the
	        texture using the format specified in the texture desc, but the app can create views with different formats if this is specified.
            """,
			0x0001
		),

		"TextureMisc_AllowGenerateMips".enum(
			"DX only: Allow generation of the mip chain on the GPU via the GenerateMips call. This flag requires that RenderTarget binding also be specified.",
			0x0002
		)
	)

	EnumConstant(
		"Button input types. ({@code ovrTouch})",

		"Button_A".enum("Button A", 0x00000001),
		"Button_B".enum("Button B", 0x00000002),
		"Button_RThumb".enum("Button RThumb", 0x00000004),
		"Button_RShoulder".enum("Button RShoulder", 0x00000008),

		"Button_RMask".enumExpr("Bit mask of all buttons on the right Touch controller", "ovrButton_A | ovrButton_B | ovrButton_RThumb | ovrButton_RShoulder"),

		"Button_X".enum("Button X", 0x00000100),
		"Button_Y".enum("Button Y", 0x00000200),
		"Button_LThumb".enum("Button LThumb", 0x00000400),
		"Button_LShoulder".enum("Button LShoulder", 0x00000800),

		"Button_LMask".enumExpr("Bit mask of all buttons on the left Touch controller", "ovrButton_X | ovrButton_Y | ovrButton_LThumb | ovrButton_LShoulder"),

		// Navigation through DPad.
		"Button_Up".enum("Button Up", 0x00010000),
		"Button_Down".enum("Button Down", 0x00020000),
		"Button_Left".enum("Button Left", 0x00040000),
		"Button_Right".enum("Button Right", 0x00080000),
		"Button_Enter".enum("Button Enter", 0x00100000), // Start on XBox controller.
		"Button_Back".enum("Button Back", 0x00200000), // Back on Xbox controller.
		"Button_VolUp".enum("Button VolUp", 0x00400000), // only supported by Remote.
		"Button_VolDown".enum("Button VolDown", 0x00800000), // only supported by Remote.
		"Button_Home".enum("Button Home", 0x01000000),

		"Button_Private" expr "ovrButton_VolUp | ovrButton_VolDown | ovrButton_Home"
	)

	EnumConstant(
		"Touch input types.",

		"Touch_A".enumExpr("Touch A", "ovrButton_A"),
		"Touch_B".enumExpr("Touch B", "ovrButton_B"),
		"Touch_RThumb".enumExpr("Touch RThumb", "ovrButton_RThumb"),
		"Touch_RIndexTrigger".enum("Touch RIndexTrigger", 0x00000010),

		"Touch_RButtonMask".enumExpr("Bit mask of all the button touches on the right controller", "ovrTouch_A | ovrTouch_B | ovrTouch_RThumb | ovrTouch_RIndexTrigger"),

		"Touch_X".enumExpr("Touch X", "ovrButton_X"),
		"Touch_Y".enumExpr("Touch Y", "ovrButton_Y"),
		"Touch_LThumb".enumExpr("Touch LThumb", "ovrButton_LThumb"),
		"Touch_LIndexTrigger".enum("Touch LIndexTrigger", 0x00001000),

		"Touch_LButtonMask".enumExpr("Bit mask of all the button touches on the left controller", "ovrTouch_X | ovrTouch_Y | ovrTouch_LThumb | ovrTouch_LIndexTrigger"),

		// Finger pose state
		// Derived internally based on distance, proximity to sensors and filtering.
		"Touch_RIndexPointing".enum("TouchRIndexPointing", 0x00000020),
		"Touch_RThumbUp".enum("Touch RThumbUp", 0x00000040),

		"Touch_RPoseMask".enumExpr("Bit mask of all right controller poses", "ovrTouch_RIndexPointing | ovrTouch_RThumbUp"),

		"Touch_LIndexPointing".enum("Touch LIndexPointing", 0x00002000),
		"Touch_LThumbUp".enum("Touch LThumbUp", 0x00004000),

		"Touch_LPoseMask".enumExpr("Bit mask of all left controller poses.", "ovrTouch_LIndexPointing | ovrTouch_LThumbUp")
	)

	EnumConstant(
		"Which controller is connected; multiple can be connected at once. ({@code ovrControllerType})",

		"ControllerType_None".enum("", 0x00),
		"ControllerType_LTouch".enum(""),
		"ControllerType_RTouch".enum(""),
		"ControllerType_Touch".enum(""),
		"ControllerType_Remote".enum(""),
		"ControllerType_XBox".enum("", 0x10),

		"ControllerType_Active".enum("Operate on or query whichever controller is active.", 0xff)
	)

	EnumConstant(
		"Names for the left and right hand array indexes. ({@code ovrHandType})",

		"Hand_Left".enum("", 0),
		"Hand_Right".enum("")
	)

	ovrResult(
		"Initialize",
		"""
		Initialize LibOVR for application usage. This includes finding and loading the LibOVRRT shared library. No LibOVR API functions, other than
		#GetLastErrorInfo(), can be called unless #Initialize() succeeds. A successful call to {@code ovr_Initialize} must be eventually followed by a call to
		#Shutdown(). {@code ovr_Initialize} calls are idempotent. Calling {@code ovr_Initialize} twice does not require two matching calls to
		{@code ovr_Shutdown}. If already initialized, the return value is OVRErrorCode#Success.

		LibOVRRT shared library search order:
		${ol(
			"Current working directory (often the same as the application directory).",
			"Module directory (usually the same as the application directory, but not if the module is a separate shared library).",
			"Application directory",
			"Development directory (only if OVR_ENABLE_DEVELOPER_SEARCH is enabled, which is off by default).",
			"Standard OS shared library search location(s) (OS-specific)."
		)}
	    """,

		nullable..const..ovrInitParams_p.IN(
			"params",
			"an ##OVRInitParams struct that cpecifies custom initialization options. May be $NULL to indicate default options."
		),

		returnDoc =
		"""
		an {@code ovrResult} indicating success or failure. In the case of failure, use #GetLastErrorInfo() to get more information. Example failed results
		include:
		${ul(
			"OVRErrorCode#Error_Initialize: Generic initialization error.",
			"OVRErrorCode#Error_LibLoad: Couldn't load LibOVRRT.",
			"OVRErrorCode#Error_LibVersion: LibOVRRT version incompatibility.",
			"OVRErrorCode#Error_ServiceConnection: Couldn't connect to the OVR Service.",
			"OVRErrorCode#Error_ServiceVersion: OVR Service version incompatibility.",
			"OVRErrorCode#Error_IncompatibleOS: The operating system version is incompatible.",
			"OVRErrorCode#Error_DisplayInit: Unable to initialize the HMD display.",
			"OVRErrorCode#Error_ServerStart:  Unable to start the server. Is it already running?",
			"OVRErrorCode#Error_Reinitialization: Attempted to re-initialize with a different version."
		)}
		"""
	)

	void(
		"Shutdown",
		"""
		Shuts down LibOVR.

		A successful call to #Initialize() must be eventually matched by a call to {@code ovr_Shutdown}. After calling {@code ovr_Shutdown}, no LibOVR
		functions can be called except #GetLastErrorInfo() or another #Initialize(). {@code ovr_Shutdown} invalidates all pointers, references, and created
		objects previously returned by LibOVR functions. The LibOVRRT shared library can be unloaded by {@code ovr_Shutdown}.
		"""
	)

	void(
		"GetLastErrorInfo",
		"""
	    Returns information about the most recent failed return value by the current thread for this library.

	    This function itself can never generate an error. The last error is never cleared by LibOVR, but will be overwritten by new errors. Do not use this
	    call to determine if there was an error in the last API call as successful API calls don't clear the last ##OVRErrorInfo. To avoid any inconsistency,
	    #GetLastErrorInfo() should be called immediately after an API function that returned a failed {@code ovrResult}, with no other API functions called in
	    the interim.
	    """,

		ovrErrorInfo_p.OUT("errorInfo", "The last ##OVRErrorInfo for the current thread")
	)

	(const..charUTF8_p)(
		"GetVersionString",
		"""
		Returns the version string representing the LibOVRRT version.

		The returned string pointer is valid until the next call to #Shutdown().

		Note that the returned version string doesn't necessarily match the current OVR_MAJOR_VERSION, etc., as the returned string refers to the LibOVRRT
		shared library version and not the locally compiled interface version.

		The format of this string is subject to change in future versions and its contents should not be interpreted.
		""",

		returnDoc = "a UTF8-encoded null-terminated version string"
	)

	(const..charUTF8_p)(
		"TraceMessage",
		"""
		Writes a message string to the LibOVR tracing mechanism (if enabled).

		This message will be passed back to the application via the ##OVRLogCallback if it was registered.
		""",

		int.IN("level", "an {@code ovrLogLevel} constant", LogLevels),
		const..charUTF8_p.IN("message", "a UTF8-encoded null-terminated string"),

		returnDoc = "the {@code strlen} of the message or a negative value if the message is too large"
	)

	// ----------------
	// HMD Management

	ovrHmdDesc(
		"GetHmdDesc",
		"""
        Returns information about the current HMD.

        #Initialize() must have first been called in order for this to succeed, otherwise ovrHmdDesc::Type will be reported as #Hmd_None.
        """,

		nullable..ovrSession.IN(
			"session",
			"""
	        an {@code ovrSession} previously returned by #Create(), else $NULL in which case this function detects whether an HMD is present and returns its
	        info if so.
	        """
		),

		returnDoc = "an ##OVRHmdDesc. If the {@code hmd} is $NULL and ovrHmdDesc::Type is #Hmd_None then no HMD is present."
	)

	val session = ovrSession.IN("session", "an {@code ovrSession} previously returned by #Create()")


	unsigned_int(
		"GetTrackerCount",
		"""
		Returns the number of sensors.

		The number of sensors may change at any time, so this function should be called before use as opposed to once on startup.
		""",

		session
	)

	ovrTrackerDesc(
		"GetTrackerDesc",
		"""
		Returns a given sensor description.

		It's possible that sensor {@code desc [0]} may indicate a unconnnected or non-pose tracked sensor, but sensor {@code desc [1]} may be connected.

		#Initialize() must have first been called in order for this to succeed, otherwise the returned {@code trackerDescArray} will be zero-initialized. The
		data returned by this function can change at runtime.
		""",

		session,
		unsigned_int.IN("trackerDescIndex", "a sensor index. The valid indexes are in the range of 0 to the sensor count returned by #GetTrackerCount()."),

		returnDoc = "an ##OVRTrackerDesc. An empty {@code OVRTrackerDesc} will be returned if {@code trackerDescIndex} is out of range."
	)

	ovrResult(
		"Create",
		"""
	    Creates a handle to a VR session.

		Upon success the returned {@code ovrSession} must be eventually freed with #Destroy() when it is no longer needed. A second call to #Create() will result
		in an error return value if the previous {@code Hmd} has not been destroyed.
	    """,

		Check(1)..ovrSession_p.OUT("pSession", "a pointer to an {@code ovrSession} which will be written to upon success"),
		ovrGraphicsLuid_p.OUT(
			"luid",
			"""
		    a system specific graphics adapter identifier that locates which graphics adapter has the HMD attached. This must match the adapter used by the
		    application or no rendering output will be possible. This is important for stability on multi-adapter systems. An application that simply chooses
		    the default adapter will not run reliably on multi-adapter systems.
			"""
		),

		returnDoc = "an {@code ovrResult} indicating success or failure. Upon failure the returned {@code pHmd} will be $NULL."
	)

	void(
		"Destroy",
		"Destroys the HMD.",

		session
	)

	ovrResult(
		"GetSessionStatus",
		"Returns status information for the application.",

		session,
		ovrSessionStatus_p.OUT("sessionStatus", "an ##OVRSessionStatus that is filled in")
	)

	// ----------------
	// Tracking

	ovrResult(
		"SetTrackingOriginType",
		"""
		Sets the tracking origin type.

		When the tracking origin is changed, all of the calls that either provide or accept ovrPosef will use the new tracking origin provided.
		""",

		session,
		ovrTrackingOrigin.IN("origin", "an {@code ovrTrackingOrigin} to be used for all ##OVRPosef")
	)

	ovrTrackingOrigin(
		"GetTrackingOriginType",
		"Gets the tracking origin state.",

		session
	)

	ovrResult(
		"RecenterTrackingOrigin",
		"""
		Re-centers the sensor position and orientation.

		This resets the (x,y,z) positional components and the yaw orientation component. The Roll and pitch orientation components are always determined by
		gravity and cannot be redefined. All future tracking will report values relative to this new reference position. If you are using ##OVRTrackerPose then
		you will need to call #GetTrackerPose() after this, because the sensor position(s) will change as a result of this.

		The headset cannot be facing vertically upward or downward but rather must be roughly level otherwise this function will fail with
		OVRErrorCode#Error_InvalidHeadsetOrientation.

		For more info, see the notes on each {@code ovrTrackingOrigin} enumeration to understand how recenter will vary slightly in its behavior based on the
		current {@code ovrTrackingOrigin} setting.
		""",

		session
	)

	void(
		"ClearShouldRecenterFlag",
		"""
		Clears the ShouldRecenter status bit in ovrSessionStatus.

		Clears the {@code ShouldRecenter} status bit in ##OVRSessionStatus, allowing further recenter requests to be detected. Since this is automatically done
		by #RecenterTrackingOrigin(), this is only needs to be called when application is doing its own re-centering.
		""",

		session
	)

	ovrTrackingState(
		"GetTrackingState",
		"""
		Returns tracking state reading based on the specified absolute system time.

		Pass an {@code absTime} value of 0.0 to request the most recent sensor reading. In this case both {@code PredictedPose} and {@code SamplePose} will
		have the same value.

		This may also be used for more refined timing of front buffer rendering logic, and so on.

		This may be called by multiple threads.
	    """,

		session,
		double.IN("absTime", "the absolute future time to predict the return ##OVRTrackingState value. Use 0 to request the most recent tracking state."),
		ovrBool.IN(
			"latencyMarker",
			"""
			specifies that this call is the point in time where the "App-to-Mid-Photon" latency timer starts from. If a given {@code ovrLayer} provides
			"SensorSampleTimestamp", that will override the value stored here.
			"""
		),

		returnDoc = "the ##OVRTrackingState that is predicted for the given {@code absTime}"
	)

	ovrTrackerPose(
		"GetTrackerPose",
		"Returns the ##OVRTrackerPose for the given sensor.",

		session,
		unsigned_int.IN("trackerPoseIndex", "index of the sensor being requested.")
	)

	ovrResult(
		"GetInputState",
		"""
        Returns the most recent input state for controllers, without positional tracking info. Developers can tell whether the same state was returned by
        checking the {@code PacketNumber}.
        """,

		session,
		ovrControllerType.IN("controllerType", "which controllers the input will be returned for"),
		ovrInputState_p.IN("inputState", "the input state that will be filled in"),

		returnDoc = "OVRErrorCode#Success if the new state was successfully obtained"
	)

	unsigned_int(
		"GetConnectedControllerTypes",
		"Returns controller types connected to the system OR'ed together.",

		session
	)

	ovrResult(
		"SetControllerVibration",
		"""
        Turns on vibration of the given controller.

		To disable vibration, call #SetControllerVibration() with an amplitude of 0. Vibration automatically stops after a nominal amount of time, so if you
		want vibration to be continuous over multiple seconds then you need to call this function periodically.
        """,

		session,
		ovrControllerType.IN("controllerType", "the controller to apply the vibration to"),
		float.IN(
			"frequency",
			"""
			a vibration frequency in the range of 0.0 to 1.0. Currently the only valid values are 0.0, 0.5, and 1.0 and other values will be clamped to one of
			these.
			"""),
		float.IN("amplitude", "a vibration amplitude in the range of 0.0 to 1.0."),

		returnDoc = "OVRErrorCode#Success upon success"
	)


	// ----------------
	// Layers

	IntConstant(
		"Specifies the maximum number of layers supported by #SubmitFrame().",

		"MaxLayerCount"..16
	)

	EnumConstant(
		"""
		Describes layer types that can be passed to #SubmitFrame(). ({@code ovrLayerType})
		
		Each layer type has an associated struct, such as ##OVRLayerEyeFov.
		""",

		"LayerType_Disabled".enumExpr("Layer is disabled.", "0"),
		"LayerType_EyeFov".enum("Described by ##OVRLayerEyeFov."),
		"LayerType_Quad".enumExpr("Described by ##OVRLayerQuad.", "3"),
		"LayerType_EyeMatrix".enumExpr("Described by ##OVRLayerEyeMatrix.", "5")
	)

	EnumConstant(
		"Identifies flags used by ##OVRLayerHeader and which are passed to #SubmitFrame(). ({@code ovrLayerFlags})",

		"LayerFlag_HighQuality".enum(
			"""
			Enables 4x anisotropic sampling during the composition of the layer. The benefits are mostly visible at the periphery for high-frequency &amp;
			high-contrast visuals. For best results consider combining this flag with an {@code ovrTextureSwapChain} that has mipmaps and instead of using
			arbitrary sized textures, prefer texture sizes that are powers-of-two. Actual rendered viewport and doesn't necessarily have to fill the whole
			texture.
			""",
			0x01
		),
		"LayerFlag_TextureOriginAtBottomLeft".enum("the opposite is TopLeft. Generally this is false for D3D, true for OpenGL.", 0x02),
		"LayerFlag_HeadLocked".enum(
			"""
			Mark this surface as "headlocked", which means it is specified relative to the HMD and moves with it, rather than being specified relative to
			sensor/torso space and remaining still while the head moves. What used to be {@code ovrLayerType_QuadHeadLocked} is now #LayerType_Quad plus this
			flag. However the flag can be applied to any layer type to achieve a similar effect.
			""",
			0x04
		)
	)

	// ----------------
	// SDK Distortion Rendering

	ovrResult(
		"GetTextureSwapChainLength",
		"Gets the number of buffers in an {@code ovrTextureSwapChain}.",

		session,
		ovrTextureSwapChain.IN("chain", "the {@code ovrTextureSwapChain} for which the length should be retrieved"),
		Check(1)..int_p.OUT("out_Length", "returns the number of buffers in the specified chain")
	)

	ovrResult(
		"GetTextureSwapChainCurrentIndex",
		"Gets the current index in an {@code ovrTextureSwapChain}.",

		session,
		ovrTextureSwapChain.IN("chain", "the {@code ovrTextureSwapChain} for which the index should be retrieved"),
		Check(1)..int_p.OUT("out_Index", "returns the current (free) index in specified chain")
	)

	ovrResult(
		"GetTextureSwapChainDesc",
		"Gets the description of the buffers in an {@code ovrTextureSwapChain}.",

		session,
		ovrTextureSwapChain.IN("chain", "the {@code ovrTextureSwapChain} for which the description should be retrieved"),
		ovrTextureSwapChainDesc_p.OUT("out_Desc", "returns the description of the specified chain")
	)

	ovrResult(
		"CommitTextureSwapChain",
		"""
		Commits any pending changes to an {@code ovrTextureSwapChain}, and advances its current index.

		When Commit is called, the texture at the current index is considered ready for use by the runtime, and further writes to it should be avoided. The
		swap chain's current index is advanced, providing there's room in the chain. The next time the SDK dereferences this texture swap chain, it will
		synchronize with the app's graphics context and pick up the submitted index, opening up room in the swap chain for further commits.
		""",

		session,
		ovrTextureSwapChain.IN("chain", "the {@code ovrTextureSwapChain} to commit")
	)

	void(
		"DestroyTextureSwapChain",
		"Destroys an {@code ovrTextureSwapChain} and frees all the resources associated with it.",

		session,
		nullable..ovrTextureSwapChain.IN("chain", "the {@code ovrTextureSwapChain} to destroy. If it is $NULL then this function has no effect.")
	)

	void(
		"DestroyMirrorTexture",
		"Destroys a mirror texture previously created by one of the mirror texture creation functions.",

		session,
		nullable..ovrMirrorTexture.IN("mirrorTexture", "the {@code ovrTexture} to destroy. If it is $NULL then this function has no effect.")
	)

	ovrSizei(
		"GetFovTextureSize",
		"""
		Calculates the recommended viewport size for rendering a given eye within the HMD with a given FOV cone.

		Higher FOV will generally require larger textures to maintain quality. Apps packing multiple eye views together on the same texture should ensure there
		are at least 8 pixels of padding between them to prevent texture filtering and chromatic aberration causing images to leak between the two eye views.
		""",

		session,
		ovrEyeType.IN("eye", "which eye (left or right) to calculate for", EyeType),
		ovrFovPort.IN("fov", "the ##OVRFovPort to use"),
		float.IN(
			"pixelsPerDisplayPixel",
			"""
			the ratio of the number of render target pixels to display pixels at the center of distortion. 1.0 is the default value. Lower values can improve
			performance, higher values give improved quality.
			"""
		),

		returnDoc = "the texture width and height size"
	)

	ovrEyeRenderDesc(
		"GetRenderDesc",
		"Computes the distortion viewport, view adjust, and other rendering parameters for the specified eye.",

		session,
		ovrEyeType.IN("eyeType", "which eye (left or right) for which to perform calculations", EyeType),
		ovrFovPort.IN("fov", "the ##OVRFovPort to use."),

		returnDoc = "the computed ##OVREyeRenderDesc for the given {@code eyeType} and field of view"
	)

	ovrResult(
		"SubmitFrame",
		"""
	    Submits layers for distortion and display.

	    {@code ovr_SubmitFrame} triggers distortion and processing which might happen asynchronously. The function will return when there is room in the submission
	    queue and surfaces are available. Distortion might or might not have completed.
		${ul(
			"Layers are drawn in the order they are specified in the array, regardless of the layer type.",
			"""
		    Layers are not remembered between successive calls to {@code ovr_SubmitFrame}. A layer must be specified in every call to {@code ovr_SubmitFrame}
		    or it won't be displayed.
		    """,
			"""
		    If a {@code layerPtrList} entry that was specified in a previous call to {@code ovr_SubmitFrame} is passed as $NULL or is of type
		    #LayerType_Disabled, that layer is no longer displayed.
		    """,
			"""
		    A {@code layerPtrList} entry can be of any layer type and multiple entries of the same layer type are allowed. No {@code layerPtrList} entry may be
		    duplicated (i.e. the same pointer as an earlier entry).
		    """
		)}

		<h3>Example code</h3>
		${codeBlock("""
ovrLayerEyeFov  layer0;
ovrLayerQuad    layer1;
...
ovrLayerHeader* layers[2] = { &layer0.Header, &layer1.Header };
ovrResult result = ovr_SubmitFrame(hmd, frameIndex, nullptr, layers, 2);""")}
	    """,

		session,
		long_long.IN("frameIndex", "the targeted application frame index, or 0 to refer to one frame after the last time #SubmitFrame() was called"),
		nullable..const..ovrViewScaleDesc_p.IN(
			"viewScaleDesc",
			"""
			provides additional information needed only if {@code layerPtrList} contains an #LayerType_Quad. If $NULL, a default version is used based on the
			current configuration and a 1.0 world scale.
			"""
		),
		const..ovrLayerHeader_p_const_p.IN(
			"layerPtrList",
			"""
			a list of {@code ovrLayer} pointers, which can include $NULL entries to indicate that any previously shown layer at that index is to not be
			displayed. Each layer header must be a part of a layer structure such as ##OVRLayerEyeFov or ##OVRLayerQuad, with {@code Header.Type} identifying
			its type. A $NULL {@code layerPtrList} entry in the array indicates the absence of the given layer.
			"""
		),
		AutoSize("layerPtrList")..unsigned_int.IN(
			"layerCount",
			"""
			the number of valid elements in {@code layerPtrList}. The maximum supported {@code layerCount} is not currently specified, but may be specified in
			a future version.
			"""
		),

		returnDoc =
		"""
	    an {@code ovrResult} for which {@code OVR_SUCCESS(result)} is false upon error and true upon one of the possible success values:
	    ${ul(
			"OVRErrorCode#Success: rendering completed successfully.",
			"""
	        OVRErrorCode#Success_NotVisible: rendering completed successfully but was not displayed on the HMD, usually because another application currently
	        has ownership of the HMD. Applications receiving this result should stop rendering new content, but continue to call {@code ovr_SubmitFrame}
	        periodically until it returns a value other than OVRErrorCode#Success_NotVisible.
	        """,
			"""
	        OVRErrorCode#Error_DisplayLost: The session has become invalid (such as due to a device removal) and the shared resources need to be released
	        (#DestroyTextureSwapChain()), the session needs to destroyed (#Destroy()) and recreated (#Create()), and new resources need to be created
	        ({@code ovr_CreateTextureSwapChainXXX}). The application's existing private graphics resources do not need to be recreated unless the new
	        {@code ovr_Create} call returns a different {@code GraphicsLuid}.
	        """,
			"""
			OVRErrorCode#Error_TextureSwapChainInvalid: The {@code ovrTextureSwapChain} is in an incomplete or inconsistent state. Ensure
			#CommitTextureSwapChain() was called at least once first.
			"""
		)}
	    """
	)

	// ----------------
	// Frame Timing

	double(
		"GetPredictedDisplayTime",
		"""
		Gets the time of the specified frame midpoint.

		Predicts the time at which the given frame will be displayed. The predicted time is the middle of the time period during which the corresponding eye
		images will be displayed.

		The application should increment frameIndex for each successively targeted frame, and pass that index to any relevent OVR functions that need to apply
		to the frame identified by that index.

		This function is thread-safe and allows for multiple application threads to target their processing to the same displayed frame.

		In the event that prediction fails due to various reasons (e.g. the display being off or app has yet to present any frames), the return value will be
		current CPU time.
		""",

		session,
		long_long.IN("frameIndex", "the frame the caller wishes to target. A value of zero returns the next frame index."),

		returnDoc = "the absolute frame midpoint time for the given {@code frameIndex}"
	)

	double(
		"GetTimeInSeconds",
		"""
		Returns global, absolute high-resolution time in seconds.

		The time frame of reference for this function is not specified and should not be depended upon.
		""",

		returnDoc = "seconds as a floating point value"
	)

	EnumConstant(
		"""
		Performance HUD enables the HMD user to see information critical to the real-time operation of the VR application such as latency timing, and CPU & GPU
		performance metrics.

		App can toggle performance HUD modes as such:
		${codeBlock("""
ovrPerfHudMode PerfHudMode = ovrPerfHud_LatencyTiming;
ovr_SetInt(Hmd, OVR_PERF_HUD_MODE, (int)PerfHudMode);""")}
		""",

		"PerfHud_Off".enum("Turns off the performance HUD"),
		"PerfHud_PerfSummary".enum("Shows performance summary and headroom"),
		"PerfHud_LatencyTiming".enum("Shows latency related timing info"),
		"PerfHud_AppRenderTiming".enum("Shows render timing info for application"),
		"PerfHud_CompRenderTiming".enum("Shows render timing info for OVR compositor"),
		"PerfHud_VersionInfo".enum("Shows SDK & HMD version Info")
	)

	EnumConstant(
		"Layer HUD enables the HMD user to see information about a layer.",

		"LayerHud_Off" enum "Turns off the layer HUD",
		"LayerHud_Info" enum "Shows info about a specific layer"
	)

	IntConstant(
		"Visual properties of the stereo guide.",

		"DebugHudStereo_Off"..0, ///< Turns off the Stereo Debug HUD
		"DebugHudStereo_Quad"..1, ///< Renders Quad in world for Stereo Debugging
		"DebugHudStereo_QuadWithCrosshair"..2, ///< Renders Quad+crosshair in world for Stereo Debugging
		"DebugHudStereo_CrosshairAtInfinity"..3  ///< Renders screen-space crosshair at infinity for Stereo Debugging
	)

	// ----------------
	// Property Access

	ovrBool(
		"GetBool",
		"Reads a boolean property.",

		session,
		const..charASCII_p.IN("propertyName", "the name of the property, which needs to be valid for only the call"),
		ovrBool.IN("defaultVal", "specifes the value to return if the property couldn't be read"),

		returnDoc = "the property interpreted as a boolean value. Returns {@code defaultVal} if the property doesn't exist."
	)

	ovrBool(
		"SetBool",
		"""
		Writes or creates a boolean property.

		If the property wasn't previously a boolean property, it is changed to a boolean property.
		""",

		session,
		const..charASCII_p.IN("propertyName", "the name of the property, which needs to be valid only for the call"),
		ovrBool.IN("value", "the value to write"),

		returnDoc = "true if successful, otherwise false. A false result should only occur if the property name is empty or if the property is read-only."
	)

	int(
		"GetInt",
		"Reads an integer property.",

		session,
		const..charASCII_p.IN("propertyName", "the name of the property, which needs to be valid only for the call"),
		int.IN("defaultVal", "specifes the value to return if the property couldn't be read"),

		returnDoc = "the property interpreted as an integer value. Returns {@code defaultVal} if the property doesn't exist."
	)

	ovrBool(
		"SetInt",
		"""
		Writes or creates an integer property.

		If the property wasn't previously an integer property, it is changed to an integer property.
		""",

		session,
		const..charASCII_p.IN("propertyName", "the name of the property, which needs to be valid only for the call"),
		int.IN("value", "the value to write"),

		returnDoc = "true if successful, otherwise false. A false result should only occur if the property name is empty or if the property is read-only."
	)

	float(
		"GetFloat",
		"Reads a float property.",

		session,
		const..charASCII_p.IN("propertyName", "the name of the property, which needs to be valid only for the call"),
		float.IN("defaultVal", "specifes the value to return if the property couldn't be read"),

		returnDoc = "the property interpreted as a float value. Returns {@code defaultVal} if the property doesn't exist."
	)

	ovrBool(
		"SetFloat",
		"""
		Writes or creates a float property.

		If the property wasn't previously a float property, it's changed to a float property.
		""",

		session,
		const..charASCII_p.IN("propertyName", "the name of the property, which needs to be valid only for the call"),
		float.IN("value", "the value to write"),

		returnDoc = "true if successful, otherwise false. A false result should only occur if the property name is empty or if the property is read-only."
	)

	unsigned_int(
		"GetFloatArray",
		"Reads a float array property.",

		session,
		const..charASCII_p.IN("propertyName", "the name of the property, which needs to be valid only for the call"),
		float_p.OUT("values", "an array of float to write to"),
		AutoSize("values")..unsigned_int.IN("valuesCapacity", "the maximum number of elements to write to the values array"),

		returnDoc = "the number of elements read, or 0 if property doesn't exist or is empty"
	)

	ovrBool(
		"SetFloatArray",
		"Writes or creates a float array property.",

		session,
		const..charASCII_p.IN("propertyName", "the name of the property, which needs to be valid only for the call"),
		float_p.OUT("values", "an array of float to write from"),
		AutoSize("values")..unsigned_int.IN("valuesSize", "the number of elements to write"),

		returnDoc = "true if successful, otherwise false. A false result should only occur if the property name is empty or if the property is read-only."
	)

	(const..charUTF8_p)(
		"GetString",
		"""
		Reads a string property.

		Strings are UTF8-encoded and null-terminated.
		""",

		session,
		const..charASCII_p.IN("propertyName", "the name of the property, which needs to be valid only for the call"),
		nullable..const..charUTF8_p.IN("defaultVal", "specifes the value to return if the property couldn't be read"),

		returnDoc =
		"""
		the string property if it exists. Otherwise returns {@code defaultVal}, which can be specified as $NULL. The return memory is guaranteed to be valid
		until next call to {@code ovr_GetString} or until the HMD is destroyed, whichever occurs first.
		"""
	)

	ovrBool(
		"SetString",
		"""
		Writes or creates a string property.

		Strings are UTF8-encoded and null-terminated.
		""",

		ovrSession.IN("hmddesc", "an {@code ovrSession} previously returned by #Create()"),
		const..charASCII_p.IN("propertyName", "the name of the property, which needs to be valid only for the call"),
		const..charASCII_p.IN("value", "the string property, which only needs to be valid for the duration of the call"),

		returnDoc = "true if successful, otherwise false. A false result should only occur if the property name is empty or if the property is read-only."
	)
}
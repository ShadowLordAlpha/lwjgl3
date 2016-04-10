/*
 * Copyright LWJGL. All rights reserved.
 * License terms: http://lwjgl.org/license.php
 */
package org.lwjgl.opengl

import org.lwjgl.generator.*
import java.io.PrintWriter
import java.util.*

private val CAPABILITIES_CLASS = "GLXCapabilities"

val GLXBinding = Generator.register(object : APIBinding(OPENGL_PACKAGE, CAPABILITIES_CLASS) {

	init {
		javaImport("static org.lwjgl.system.APIUtil.*")
	}

	override val hasCapabilities: Boolean get() = true

	override fun generateFunctionAddress(writer: PrintWriter, function: NativeClassFunction) {
		writer.println("\t\tlong $FUNCTION_ADDRESS = GL.getCapabilitiesGLXClient().${function.name};")
	}

	override fun PrintWriter.generateFunctionSetup(nativeClass: NativeClass) {
		println("\n\tstatic boolean isAvailable($CAPABILITIES_CLASS caps) {")
		print("\t\treturn ")

		print("checkFunctions(")
		nativeClass.printPointers(this, { "caps.${it.name}" }) { !(it has IgnoreMissing) }
		println(");")
		println("\t}")
	}

	override fun PrintWriter.generateContent() {
		println("/** Defines the GLX capabilities of a connection. */")
		println("public final class $CAPABILITIES_CLASS {\n")

		val classes = super.getClasses { o1, o2 -> o1.templateName.compareTo(o2.templateName, ignoreCase = true) }

		val classesWithFunctions = classes.filter { it.hasNativeFunctions }

		val functions = classesWithFunctions
			.map { it.functions }
			.flatten()
			.toSortedSet(Comparator<NativeClassFunction> { o1, o2 -> o1.name.compareTo(o2.name) })

		println("\tpublic final long")
		println(functions.map { it.name }.joinToString(",\n\t\t", prefix = "\t\t", postfix = ";\n")
		)

		classes.forEach {
			val documentation = it.documentation
			if ( documentation != null )
				println((if ( it.hasBody ) "When true, {@link ${it.className}} is supported." else documentation).toJavaDoc())
			println("\tpublic final boolean ${it.capName};")
		}

		println("\n\t$CAPABILITIES_CLASS(FunctionProvider provider, Set<String> ext) {")

		println(
			functions.map {
				"${it.name} = provider.getFunctionAddress(${it.nativeName});"
			}.joinToString(prefix = "\t\t", separator = "\n\t\t", postfix = "\n")
		)

		for (extension in classes) {
			val capName = extension.capName
			println(if ( extension.hasNativeFunctions )
				"\t\t$capName = ext.contains(\"$capName\") && checkExtension(\"$capName\", ${if ( capName == extension.className ) "$OPENGL_PACKAGE.${extension.className}" else extension.className}.isAvailable(this));"
			else
				"\t\t$capName = ext.contains(\"$capName\");"
			)
		}
		println("\t}")
		println("""
	private static boolean checkExtension(String extension, boolean supported) {
		if ( supported )
			return true;

		apiLog("[GLX] " + extension + " was reported as available but an entry point is missing.");
		return false;
	}""")
		print("\n}")
	}

})

fun String.nativeClassGLX(templateName: String, postfix: String = "", init: (NativeClass.() -> Unit)? = null) = nativeClass(
	OPENGL_PACKAGE,
	templateName,
	prefix = "GLX",
	prefixMethod = "glX",
	postfix = postfix,
	binding = GLXBinding,
	init = init
)
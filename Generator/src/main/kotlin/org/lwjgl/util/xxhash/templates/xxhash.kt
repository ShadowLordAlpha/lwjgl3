/*
 * Copyright LWJGL. All rights reserved.
 * License terms: http://lwjgl.org/license.php
 */
package org.lwjgl.util.xxhash.templates

import org.lwjgl.generator.*
import org.lwjgl.util.xxhash.*

val xxhash = "XXHash".nativeClass(XXHASH_PACKAGE, prefix = "XXH", prefixMethod = "XXH") {
	nativeDirective(
"""DISABLE_WARNINGS()
#ifdef LWJGL_WINDOWS
	__pragma(warning(disable : 4711))
#endif
#define XXH_PRIVATE_API
#include "xxhash.c"
ENABLE_WARNINGS()""")

	documentation =
		"""
		Native bindings to <a href="https://github.com/Cyan4973/xxHash">xxhash</a>.

		xxHash is an extremely fast Hash algorithm, running at RAM speed limits. It also successfully passes all tests from the SMHasher suite.
		"""

	EnumConstant(
		"Error codes.",

	    "OK".enum,
	    "ERROR".enum
	)

	val major = 0
	val minor = 5
	val patch = 0

	IntConstant("The major version number.", "VERSION_MAJOR" expr "$major")
	IntConstant("The minor version number.", "VERSION_MINOR" expr "$minor")
	IntConstant("The release version number.", "VERSION_RELEASE" expr "$patch")

	IntConstant(
		"The version number",
		"VERSION_NUMBER".expr("(XXH_VERSION_MAJOR *100*100 + XXH_VERSION_MINOR *100 + XXH_VERSION_RELEASE)")
	)

	// Simple Hash Functions

	val XXH32 = XXH32_hash_t(
		"32",
		"Calculates the 32-bits hash of sequence {@code length} bytes stored at memory address {@code input}.",

		const..void_p.IN(
			"input",
			"the bytes to hash. The memory between {@code input} &amp; {@code input+length} must be valid (allocated and read-accessible)."
		),
		AutoSize("input")..size_t.IN("length", "the number of bytes stored at memory address {@code input}"),
		unsigned_int.IN("seed", "the seed that can be used to alter the result predictably")
	)

	XXH64_hash_t(
		"64",
		"""
		64-bit version of #32().

		This function runs faster on 64-bits systems, but slower on 32-bits systems.
		""",

		XXH32["input"],
		XXH32["length"],
		unsigned_long_long.IN("seed", "the seed that can be used to alter the result predictably")
	)

	// Advanced Hash Functions

	XXH32_state_t_p(
		"32_createState",
		"""
		Creates memory for {@code XXH32_state_t}. The state must then be initialized using #32_reset() before first use.

		<b>LWJGL note</b>: This function simply delegates to the system {@code malloc()} function.
		"""
	)

	XXH_errorcode(
		"32_freeState",
		"Frees the specified {@code XXH32_state_t}.",

		XXH32_state_t_p.IN("statePtr", "the state to free")
	)

	XXH64_state_t_p(
		"64_createState",
		"64-bit version of #32_createState()."
	)

	XXH_errorcode(
		"64_freeState",
		"64-bit version of #32_freeState().",

		XXH64_state_t_p.IN("statePtr", "the state to free")
	)

	XXH_errorcode(
		"32_reset",
		"Resets the specified {@code XXH32_state_t}.",

		XXH32_state_t_p.IN("statePtr", "the {@code XXH32_state_t} to reset"),
		unsigned_int.IN("seed", "the seed that can be used to alter the hashing result predictably")
	)

	XXH_errorcode(
		"32_update",
		"""
		Calculates the xxHash of an input provided in multiple segments, as opposed to provided as a single block.

		XXH state must first be allocated.

		Start a new hash by initializing state with a seed, using #32_reset().

		Then, feed the hash state by calling #32_update() as many times as necessary. Obviously, input must be valid, hence allocated and read accessible. The
		function returns an error code, with 0 meaning OK, and any other value meaning there is an error.

		Finally, a hash value can be produced anytime, by using #32_digest(). This function returns the 32-bits hash as an int.

		It's still possible to continue inserting input into the hash state after a digest, and later on generate some new hashes, by calling again
		#32_digest().

		When done, free XXH state space.
		""",

		XXH32_state_t_p.IN("statePtr", "the {@code XXH32_state_t} to use"),
		XXH32["input"],
		XXH32["length"]
	)

	XXH32_hash_t(
		"32_digest",
		"Returns the final 32-bits hash of the specified {@code XXH32_state_t}.",

		const..XXH32_state_t_p.IN("statePtr", "the {@code XXH32_state_t} to use")
	)

	XXH_errorcode(
		"64_reset",
		"64-bit version of #32_reset().",

		XXH64_state_t_p.IN("statePtr", "the {@code XXH64_state_t} to reset"),
		unsigned_long_long.IN("seed", "the seed that can be used to alter the hashing result predictably")
	)

	XXH_errorcode(
		"64_update",
		"64-bit version of #32_update().",

		XXH64_state_t_p.IN("statePtr", "the {@code XXH64_state_t} to use"),
		XXH32["input"],
		XXH32["length"]
	)

	XXH64_hash_t(
		"64_digest",
		"64-bit version of #32_digest().",

		const..XXH64_state_t_p.IN("statePtr", "the {@code XXH64_state_t} to use")
	)

	// Canonical representation

	void(
		"32_canonicalFromHash",
		"""
		Default result type for XXH functions are primitive unsigned 32 and 64 bits.

		The canonical representation uses human-readable write convention, aka big-endian (large digits first). These functions allow transformation of hash
		result into and from its canonical format. This way, hash values can be written into a file / memory, and remain comparable on different systems and
		programs.
		""",

		XXH32_canonical_t_p.OUT("dst", "the destination canonical representation"),
		XXH32_hash_t.IN("hash", "the source hash")
	)

	void(
		"64_canonicalFromHash",
		"64-bit version of #32_canonicalFromHash().",

		XXH64_canonical_t_p.OUT("dst", "the destination canonical representation"),
		XXH64_hash_t.IN("hash", "the source hash")
	)

	XXH32_hash_t(
		"32_hashFromCanonical",
		"Transforms the specified canonical representation to a primitive value.",

		const..XXH32_canonical_t_p.IN("src", "the source canonical representation")
	)

	XXH64_hash_t(
		"64_hashFromCanonical",
		"64-bit version of #32_hashFromCanonical().",

		const..XXH64_canonical_t_p.IN("src", "the source canonical representation")
	)
}
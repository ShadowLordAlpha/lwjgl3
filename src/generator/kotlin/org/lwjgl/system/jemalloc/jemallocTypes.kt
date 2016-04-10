/*
 * Copyright LWJGL. All rights reserved.
 * License terms: http://lwjgl.org/license.php
 */
package org.lwjgl.system.jemalloc

import org.lwjgl.generator.*

val JEMALLOC_PACKAGE = "org.lwjgl.system.jemalloc"

fun config() {
	packageInfo(
		JEMALLOC_PACKAGE,
		"""
		Contains bindings to the <a href="http://www.canonware.com/jemalloc/">jemalloc</a> library. jemalloc is a general purpose malloc implementation that
		emphasizes fragmentation avoidance and scalable concurrency support.

		The jemalloc documentation can be found <a href="http://www.canonware.com/download/jemalloc/jemalloc-latest/doc/jemalloc.html">here</a>. The jemalloc
		<a href="https://github.com/jemalloc/jemalloc/wiki">wiki</a> also contains useful information.
		"""
	)
}

val je_malloc_message_cb = "je_malloc_message_cb".callback(
	JEMALLOC_PACKAGE, void, "MallocMessageCallback",
	"Will be called by the #malloc_usable_size() method.",

	void_p.IN("cbopaque", "the opaque pointer passed to #malloc_usable_size()"),
	NullTerminated..const..charASCII_p.IN("s", "the message")
) {
	documentation = "Instances of this interface may be passed to the #malloc_usable_size() method."
	additionalCode = """
	/**
	 * Converts the specified {@link MallocMessageCallback} arguments to a String.
	 *
	 * <p>This method may only be used inside a MallocMessageCallback invocation.</p>
	 *
	 * @param s the MallocMessageCallback {@code s} argument
	 *
	 * @return the message as a String
	 */
	public static String getMessage(long s) {
		return memASCII(s);
	}

	/** A functional interface for {@link MallocMessageCallback}. */
	public interface SAMString {
		void invoke(long cbopaque, String s);
	}

	/**
	 * Creates a {@link MallocMessageCallback} that delegates the callback to the specified functional interface.
	 *
	 * @param sam the delegation target
	 *
	 * @return the {@link MallocMessageCallback} instance
	 */
	public static MallocMessageCallback createString(final SAMString sam) {
		return new MallocMessageCallback() {
			@Override
			public void invoke(long cbopaque, long s) {
				sam.invoke(cbopaque, getMessage(s));
			}
		};
	}
	"""
}

val chunk_alloc_t = "chunk_alloc_t".callback(
	JEMALLOC_PACKAGE, void_p, "ChunkAlloc",
	"Chunk allocation hook.",

	void_p.IN("new_addr", ""),
	size_t.IN("size", ""),
	size_t.IN("alignment", ""),
	bool_p.INOUT("zero", ""),
	bool_p.INOUT("commit", ""),
	unsigned_int.IN("arena_ind", "")
) {
	documentation = "Instances of this interface may be set to the ##ChunkHooks struct."
}

val chunk_dalloc_t = "chunk_dalloc_t".callback(
	JEMALLOC_PACKAGE, bool, "ChunkDalloc",
	"Chunk deallocation hook.",

	void_p.IN("chunk", ""),
	size_t.IN("size", ""),
	bool.IN("committed", ""),
	unsigned_int.IN("arena_ind", "")
) {
	documentation = "Instances of this interface may be set to the ##ChunkHooks struct."
}

val chunk_commit_t = "chunk_commit_t".callback(
	JEMALLOC_PACKAGE, bool, "ChunkCommit",
	"Chunk commit hook.",

	void_p.IN("chunk", ""),
	size_t.IN("size", ""),
	size_t.IN("offset", ""),
	size_t.IN("length", ""),
	unsigned_int.IN("arena_ind", "")
) {
	documentation = "Instances of this interface may be set to the ##ChunkHooks struct."
}

val chunk_decommit_t = "chunk_decommit_t".callback(
	JEMALLOC_PACKAGE, bool, "ChunkDecommit",
	"Chunk decommit hook.",

	void_p.IN("chunk", ""),
	size_t.IN("size", ""),
	size_t.IN("offset", ""),
	size_t.IN("length", ""),
	unsigned_int.IN("arena_ind", "")
) {
	documentation = "Instances of this interface may be set to the ##ChunkHooks struct."
}

val chunk_purge_t = "chunk_purge_t".callback(
	JEMALLOC_PACKAGE, bool, "ChunkPurge",
	"Chunk purge hook.",

	void_p.IN("chunk", ""),
	size_t.IN("size", ""),
	size_t.IN("offset", ""),
	size_t.IN("length", ""),
	unsigned_int.IN("arena_ind", "")
) {
	documentation = "Instances of this interface may be set to the ##ChunkHooks struct."
}

val chunk_split_t = "chunk_split_t".callback(
	JEMALLOC_PACKAGE, bool, "ChunkSplit",
	"Chunk split hook.",

	void_p.IN("chunk", ""),
	size_t.IN("size", ""),
	size_t.IN("size_a", ""),
	size_t.IN("size_b", ""),
	bool.IN("committed", ""),
	unsigned_int.IN("arena_ind", "")
) {
	documentation = "Instances of this interface may be set to the ##ChunkHooks struct."
}

val chunk_merge_t = "chunk_merge_t".callback(
	JEMALLOC_PACKAGE, bool, "ChunkMerge",
	"Chunk merge hook.",

	void_p.IN("chunk_a", ""),
	size_t.IN("size_a", ""),
	void_p.IN("chunk_b", ""),
	size_t.IN("size_b", ""),
	bool.IN("committed", ""),
	unsigned_int.IN("arena_ind", "")
) {
	documentation = "Instances of this interface may be set to the ##ChunkHooks struct."
}

val chunk_hooks_t = struct_p(JEMALLOC_PACKAGE, "ChunkHooks", nativeName = "chunk_hooks_t") {
	documentation =
	"""
	Comprises function pointers which are described individually below. jemalloc uses these functions to manage chunk lifetime, which starts off with
	allocation of mapped committed memory, in the simplest case followed by deallocation. However, there are performance and platform reasons to retain chunks
	for later reuse. Cleanup attempts cascade from deallocation to decommit to purging, which gives the chunk management functions opportunities to reject the
	most permanent cleanup operations in favor of less permanent (and often less costly) operations. The chunk splitting and merging operations can also be
	opted out of, but this is mainly intended to support platforms on which virtual memory mappings provided by the operating system kernel do not
	automatically coalesce and split, e.g. Windows.
	"""

	chunk_alloc_t.member("alloc", "the chunk allocation hook")
	chunk_dalloc_t.member("dalloc", "the chunk deallocation hook")
	chunk_commit_t.member("commit", "the chunk commit hook")
	chunk_decommit_t.member("decommit", "the chunk decommit hook")
	chunk_purge_t.member("purge", "the chunk purge hook")
	chunk_split_t.member("split", "the chunk split hook")
	chunk_merge_t.member("merge", "the chunk merge hook")
}
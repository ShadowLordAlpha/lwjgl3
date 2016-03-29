/*
 * Copyright LWJGL. All rights reserved.
 * License terms: http://lwjgl.org/license.php
 * MACHINE GENERATED FILE, DO NOT EDIT
 */
package org.lwjgl.opencl;

import java.nio.*;

import org.lwjgl.*;
import org.lwjgl.system.*;

import static org.lwjgl.system.Checks.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.system.MemoryStack.*;

/**
 * Bus address information used in {@link AMDBusAddressableMemory#clEnqueueMakeBuffersResidentAMD}.
 * 
 * <h3>Layout</h3>
 * 
 * <pre><code>struct cl_bus_address_amd {
    cl_long surfbusaddress;
    cl_long signalbusaddress;
}</code></pre>
 * 
 * <h3>Member documentation</h3>
 * 
 * <table class=lwjgl>
 * <tr><td>surfbusaddress</td><td>contains the page aligned physical starting address of the backing store preallocated by the application on a remote device</td></tr>
 * <tr><td>signalbusaddress</td><td>contains the page aligned physical starting address of preallocated signaling surface</td></tr>
 * </table>
 */
public class CLBusAddressAMD extends Struct {

	/** The struct size in bytes. */
	public static final int SIZEOF;

	public static final int ALIGNOF;

	/** The struct member offsets. */
	public static final int
		SURFBUSADDRESS,
		SIGNALBUSADDRESS;

	static {
		Layout layout = __struct(
			__member(8),
			__member(8)
		);

		SIZEOF = layout.getSize();
		ALIGNOF = layout.getAlignment();

		SURFBUSADDRESS = layout.offsetof(0);
		SIGNALBUSADDRESS = layout.offsetof(1);
	}

	CLBusAddressAMD(long address, ByteBuffer container) {
		super(address, container);
	}

	/**
	 * Creates a {@link CLBusAddressAMD} instance at the current position of the specified {@link ByteBuffer} container. Changes to the buffer's content will be
	 * visible to the struct instance and vice versa.
	 *
	 * <p>The created instance holds a strong reference to the container object.</p>
	 */
	public CLBusAddressAMD(ByteBuffer container) {
		this(memAddress(container), checkContainer(container, SIZEOF));
	}

	@Override
	public int sizeof() { return SIZEOF; }

	/** Returns the value of the {@code surfbusaddress} field. */
	public long surfbusaddress() { return nsurfbusaddress(address()); }
	/** Returns the value of the {@code signalbusaddress} field. */
	public long signalbusaddress() { return nsignalbusaddress(address()); }

	/** Sets the specified value to the {@code surfbusaddress} field. */
	public CLBusAddressAMD surfbusaddress(long value) { nsurfbusaddress(address(), value); return this; }
	/** Sets the specified value to the {@code signalbusaddress} field. */
	public CLBusAddressAMD signalbusaddress(long value) { nsignalbusaddress(address(), value); return this; }

	/** Initializes this struct with the specified values. */
	public CLBusAddressAMD set(
		long surfbusaddress,
		long signalbusaddress
	) {
		surfbusaddress(surfbusaddress);
		signalbusaddress(signalbusaddress);

		return this;
	}

	/** Unsafe version of {@link #set(CLBusAddressAMD) set}. */
	public CLBusAddressAMD nset(long struct) {
		memCopy(struct, address(), SIZEOF);
		return this;
	}

	/**
	 * Copies the specified struct data to this struct.
	 *
	 * @param src the source struct
	 *
	 * @return this struct
	 */
	public CLBusAddressAMD set(CLBusAddressAMD src) {
		return nset(src.address());
	}

	// -----------------------------------

	/** Returns a new {@link CLBusAddressAMD} instance allocated with {@link MemoryUtil#memAlloc memAlloc}. The instance must be explicitly freed. */
	public static CLBusAddressAMD malloc() {
		return create(nmemAlloc(SIZEOF));
	}

	/** Returns a new {@link CLBusAddressAMD} instance allocated with {@link MemoryUtil#memCalloc memCalloc}. The instance must be explicitly freed. */
	public static CLBusAddressAMD calloc() {
		return create(nmemCalloc(1, SIZEOF));
	}

	/** Returns a new {@link CLBusAddressAMD} instance allocated with {@link BufferUtils}. */
	public static CLBusAddressAMD create() {
		return new CLBusAddressAMD(BufferUtils.createByteBuffer(SIZEOF));
	}

	/** Returns a new {@link CLBusAddressAMD} instance for the specified memory address or {@code null} if the address is {@code NULL}. */
	public static CLBusAddressAMD create(long address) {
		return address == NULL ? null : new CLBusAddressAMD(address, null);
	}

	/**
	 * Returns a new {@link CLBusAddressAMD.Buffer} instance allocated with {@link MemoryUtil#memAlloc memAlloc}. The instance must be explicitly freed.
	 *
	 * @param capacity the buffer capacity
	 */
	public static Buffer malloc(int capacity) {
		return create(nmemAlloc(capacity * SIZEOF), capacity);
	}

	/**
	 * Returns a new {@link CLBusAddressAMD.Buffer} instance allocated with {@link MemoryUtil#memCalloc memCalloc}. The instance must be explicitly freed.
	 *
	 * @param capacity the buffer capacity
	 */
	public static Buffer calloc(int capacity) {
		return create(nmemCalloc(capacity, SIZEOF), capacity);
	}

	/**
	 * Returns a new {@link CLBusAddressAMD.Buffer} instance allocated with {@link BufferUtils}.
	 *
	 * @param capacity the buffer capacity
	 */
	public static Buffer create(int capacity) {
		return new Buffer(BufferUtils.createByteBuffer(capacity * SIZEOF));
	}

	/**
	 * Create a {@link CLBusAddressAMD.Buffer} instance at the specified memory.
	 *
	 * @param address  the memory address
	 * @param capacity the buffer capacity
	 */
	public static Buffer create(long address, int capacity) {
		return address == NULL ? null : new Buffer(address, null, -1, 0, capacity, capacity);
	}

	// -----------------------------------

	/** Returns a new {@link CLBusAddressAMD} instance allocated on the thread-local {@link MemoryStack}. */
	public static CLBusAddressAMD mallocStack() {
		return mallocStack(stackGet());
	}

	/** Returns a new {@link CLBusAddressAMD} instance allocated on the thread-local {@link MemoryStack} and initializes all its bits to zero. */
	public static CLBusAddressAMD callocStack() {
		return callocStack(stackGet());
	}

	/**
	 * Returns a new {@link CLBusAddressAMD} instance allocated on the specified {@link MemoryStack}.
	 *
	 * @param stack the stack from which to allocate
	 */
	public static CLBusAddressAMD mallocStack(MemoryStack stack) {
		return create(stack.nmalloc(ALIGNOF, SIZEOF));
	}

	/**
	 * Returns a new {@link CLBusAddressAMD} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
	 *
	 * @param stack the stack from which to allocate
	 */
	public static CLBusAddressAMD callocStack(MemoryStack stack) {
		return create(stack.ncalloc(ALIGNOF, 1, SIZEOF));
	}

	/**
	 * Returns a new {@link CLBusAddressAMD.Buffer} instance allocated on the thread-local {@link MemoryStack}.
	 *
	 * @param capacity the buffer capacity
	 */
	public static Buffer mallocStack(int capacity) {
		return mallocStack(capacity, stackGet());
	}

	/**
	 * Returns a new {@link CLBusAddressAMD.Buffer} instance allocated on the thread-local {@link MemoryStack} and initializes all its bits to zero.
	 *
	 * @param capacity the buffer capacity
	 */
	public static Buffer callocStack(int capacity) {
		return callocStack(capacity, stackGet());
	}

	/**
	 * Returns a new {@link CLBusAddressAMD.Buffer} instance allocated on the specified {@link MemoryStack}.
	 *
	 * @param stack the stack from which to allocate
	 * @param capacity the buffer capacity
	 */
	public static Buffer mallocStack(int capacity, MemoryStack stack) {
		return create(stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
	}

	/**
	 * Returns a new {@link CLBusAddressAMD.Buffer} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
	 *
	 * @param stack the stack from which to allocate
	 * @param capacity the buffer capacity
	 */
	public static Buffer callocStack(int capacity, MemoryStack stack) {
		return create(stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
	}

	// -----------------------------------

	/** Unsafe version of {@link #surfbusaddress}. */
	public static long nsurfbusaddress(long struct) { return memGetLong(struct + CLBusAddressAMD.SURFBUSADDRESS); }
	/** Unsafe version of {@link #signalbusaddress}. */
	public static long nsignalbusaddress(long struct) { return memGetLong(struct + CLBusAddressAMD.SIGNALBUSADDRESS); }

	/** Unsafe version of {@link #surfbusaddress(long) surfbusaddress}. */
	public static void nsurfbusaddress(long struct, long value) { memPutLong(struct + CLBusAddressAMD.SURFBUSADDRESS, value); }
	/** Unsafe version of {@link #signalbusaddress(long) signalbusaddress}. */
	public static void nsignalbusaddress(long struct, long value) { memPutLong(struct + CLBusAddressAMD.SIGNALBUSADDRESS, value); }

	// -----------------------------------

	/** An array of {@link CLBusAddressAMD} structs. */
	public static final class Buffer extends StructBuffer<CLBusAddressAMD, Buffer> {

		/**
		 * Creates a new {@link CLBusAddressAMD.Buffer} instance backed by the specified container.
		 *
		 * Changes to the container's content will be visible to the struct buffer instance and vice versa. The two buffers' position, limit, and mark values
		 * will be independent. The new buffer's position will be zero, its capacity and its limit will be the number of bytes remaining in this buffer divided
		 * by {@link CLBusAddressAMD#SIZEOF}, and its mark will be undefined.
		 *
		 * <p>The created buffer instance holds a strong reference to the container object.</p>
		 */
		public Buffer(ByteBuffer container) {
			super(container, container.remaining() / SIZEOF);
		}

		Buffer(long address, ByteBuffer container, int mark, int pos, int lim, int cap) {
			super(address, container, mark, pos, lim, cap);
		}

		@Override
		protected Buffer self() {
			return this;
		}

		@Override
		protected Buffer newBufferInstance(long address, ByteBuffer container, int mark, int pos, int lim, int cap) {
			return new Buffer(address, container, mark, pos, lim, cap);
		}

		@Override
		protected CLBusAddressAMD newInstance(long address) {
			return new CLBusAddressAMD(address, getContainer());
		}

		@Override
		protected int sizeof() {
			return SIZEOF;
		}

		/** Returns the value of the {@code surfbusaddress} field. */
		public long surfbusaddress() { return CLBusAddressAMD.nsurfbusaddress(address()); }
		/** Returns the value of the {@code signalbusaddress} field. */
		public long signalbusaddress() { return CLBusAddressAMD.nsignalbusaddress(address()); }

		/** Sets the specified value to the {@code surfbusaddress} field. */
		public CLBusAddressAMD.Buffer surfbusaddress(long value) { CLBusAddressAMD.nsurfbusaddress(address(), value); return this; }
		/** Sets the specified value to the {@code signalbusaddress} field. */
		public CLBusAddressAMD.Buffer signalbusaddress(long value) { CLBusAddressAMD.nsignalbusaddress(address(), value); return this; }

	}

}
/*
 * Copyright LWJGL. All rights reserved.
 * License terms: http://lwjgl.org/license.php
 */
package org.lwjgl.system;

import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.opengles.GLESCapabilities;

import java.lang.reflect.Field;

import static org.lwjgl.system.APIUtil.*;

/**
 * This class provides storage for all LWJGL objects that must be thread-local. [INTERNAL USE ONLY]
 *
 * <p>The default implementation uses a simple {@link ThreadLocal}. Alternative implementations may have better performance.</p>
 */
public final class ThreadLocalUtil {

	private static final State TLS = getInstance();

	private ThreadLocalUtil() {
	}

	public static TLS tlsGet() {
		return TLS.get();
	}

	public static class TLS implements Runnable {

		private Runnable target;

		public final MemoryStack stack;

		public GLCapabilities   glCaps;
		public GLESCapabilities glesCaps;

		public ALCapabilities alCaps;

		public TLS() {
			stack = new MemoryStack();
		}

		@Override
		public void run() {
			if ( target != null )
				target.run();
		}

	}

	private interface State {
		void set(TLS value);
		TLS get();
	}

	@SuppressWarnings("unchecked")
	private static State getInstance() {
		String tls = Configuration.THREAD_LOCAL_SPACE.get("unsafe");

		if ( "unsafe".equals(tls) ) {
			try {
				return new UnsafeState();
			} catch (Throwable t) {
				apiLog("[TLS] Failed to initialize unsafe implementation.");
				return new TLState();
			}
		} else if ( "ThreadLocal".equals(tls) ) {
			return new TLState();
		} else {
			throw new IllegalStateException("Invalid " + Configuration.THREAD_LOCAL_SPACE.getProperty() + " specified.");
		}
	}

	/** {@link ThreadLocal} implementation. */
	private static class TLState implements State {

		private static final ThreadLocal<TLS> STATE = new ThreadLocal<ThreadLocalUtil.TLS>() {
			@Override
			protected TLS initialValue() {
				return new TLS();
			}
		};

		@Override
		public void set(TLS value) {
			STATE.set(value);
		}

		@Override
		public TLS get() {
			return STATE.get();
		}

	}

	/**
	 * Unsafe implemenation.
	 *
	 * <p>Replaces {@link Thread}'s target runnable with an instance of {@link TLS}. The new runnable delegates to the original runnable.</p>
	 *
	 * <p>This implementation trades the {@code ThreadLocalMap} lookup with a plain field derefence, eliminating considerable overhead.</p>
	 */
	private static class UnsafeState implements State {

		private static final sun.misc.Unsafe UNSAFE = MemoryAccess.getUnsafeInstance();

		private static final long TARGET;

		static {
			try {
				Field field = Thread.class.getDeclaredField("target");
				if ( !Runnable.class.isAssignableFrom(field.getType()) )
					throw new IllegalStateException();

				TARGET = UNSAFE.objectFieldOffset(field);
			} catch (NoSuchFieldException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public TLS get() {
			Object target = UNSAFE.getObject(Thread.currentThread(), TARGET);
			return TLS.class.isInstance(target) ? (TLS)target : setInitialValue();
		}

		private TLS setInitialValue() {
			TLS tls = new TLS();
			set(tls);
			return tls;
		}

		@Override
		public void set(TLS value) {
			Thread t = Thread.currentThread();

			value.target = (Runnable)UNSAFE.getObject(t, TARGET);
			UNSAFE.putObject(t, TARGET, value);
		}

	}

}
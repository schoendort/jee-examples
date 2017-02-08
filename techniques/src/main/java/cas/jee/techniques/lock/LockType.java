package cas.jee.techniques.lock;

public enum LockType {

	/**
	 * For read-only operations. Allows simultaneous access to methods
	 * designated as <code>READ</code>, as long as no <code>WRITE</code> lock is
	 * held.
	 */
	READ,

	/**
	 * For exclusive access to the bean instance. A <code>WRITE</code> lock can
	 * only be acquired when no other method with either a <code>READ</code> or
	 * <code>WRITE</code> lock is currently held.
	 */
	WRITE;
}

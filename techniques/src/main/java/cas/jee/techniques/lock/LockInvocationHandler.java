package cas.jee.techniques.lock;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockInvocationHandler implements InvocationHandler {

	private final Object target;
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	public LockInvocationHandler(Object target) {
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Lock annotation = target.getClass().getMethod(method.getName(), getClass(args)).getAnnotation(Lock.class);
		if (annotation != null) {
			getLock(annotation.value()).lock();
			try {
				return method.invoke(target, args);
			} finally {
				getLock(annotation.value()).unlock();
			}
		} else {
			return method.invoke(target, args);
		}
	}

	private Class<?>[] getClass(Object[] args) {
		Class<?>[] clazz = new Class<?>[args.length];
		for (int i = 0; i < args.length; i++) {
			clazz[i] = args[i].getClass();
		}
		return clazz;
	}

	private java.util.concurrent.locks.Lock getLock(LockType value) {
		if (value == LockType.READ) {
			return lock.readLock();
		}
		return lock.writeLock();
	}

}

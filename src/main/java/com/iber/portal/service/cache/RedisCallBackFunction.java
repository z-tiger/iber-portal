package com.iber.portal.service.cache;

public interface RedisCallBackFunction<T , E> {
	
	public T callback(E e);
}

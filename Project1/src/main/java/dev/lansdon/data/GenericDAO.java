package dev.lansdon.data;

import java.util.Set;

public interface GenericDAO <T> {
	public T add(T t) throws Exception;
	public T getById(Integer id);
	public Set<T> getAll();
	public void update(T t);
	public void delete(T t);
}

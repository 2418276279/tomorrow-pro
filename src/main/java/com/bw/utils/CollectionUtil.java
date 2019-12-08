package com.bw.utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * 集合工具类
 * 
 * @author Lychie Fan
 */
public class CollectionUtil {

	
	
	
	/**
	 * 集合是否为空
	 * 
	 * @param collection
	 *            集合
	 * @return 若集合为null, 或集合大小为0, 则返回true, 否则返回false
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return collection == null || collection.size() == 0;
	}

	/**
	 * 集合是否不为空
	 * 
	 * @param collection
	 *            集合
	 * @return 若集合不为null, 而且集合大小不为0, 则返回true, 否则返回false
	 */
	public static boolean isNotEmpty(Collection<?> collection) {
		return !isEmpty(collection);
	}

	/**
	 * 参数转换为List集合
	 * 
	 * @param objs
	 *            参数对象
	 * @return
	 */
	public static <E> List<E> asList(E... objs) {
		return Arrays.asList(objs);
	}

	/**
	 * 参数集合转换为List集合
	 * 
	 * @param collection
	 *            集合参数
	 * @return
	 */
	public static <E> List<E> asList(Collection<E> collection) {
		if (collection == null) {
			return null;
		}
		if (collection instanceof List) {
			return (List<E>) collection;
		}
		return new ArrayList<E>(collection);
	}

	/**
	 * 参数转换为Set集合
	 * 
	 * @param objs
	 *            参数对象
	 * @return
	 */
	public static <E> Set<E> asSet(E... objs) {
		return new HashSet<E>(Arrays.asList(objs));
		
	}

	/**
	 * 参数转换为Set集合
	 * 
	 * @param collection
	 *            集合参数
	 * @return
	 */
	public static <E> Set<E> asSet(Collection<E> collection) {
		if (collection == null) {
			return null;
		}
		if (collection instanceof Set) {
			return (Set<E>) collection;
		}
		return new HashSet<E>(collection);
	}

	/**
	 * 将集合转换为数组
	 * 
	 * @param collection
	 *            集合
	 * @return
	 */
	public static <E> E[] toArray(Collection<E> collection) {
		return ArrayUtil.asArray(collection);
	}

	/**
	 * 创建ArrayList实例
	 * 
	 * @return
	 */
	public static <E> List<E> newList() {
		return new ArrayList<E>();
	}

	/**
	 * 创建一个指定容量的ArrayList实例
	 * 
	 * @param size
	 *            大小
	 * @return
	 */
	public static <E> List<E> newList(int size) {
		return new ArrayList<E>(size);
	}

	/**
	 * 创建一个HashSet实例
	 * 
	 * @return
	 */
	public static <E> Set<E> newSet() {
		return new HashSet<E>(16, .75f);
	}

	/**
	 * 创建一个指定容量大小的HashSet实例
	 * 
	 * @param initialCapacity
	 *            初始容量
	 * @param loadFactor
	 *            加载因子
	 * @return
	 */
	public static <E> Set<E> newSet(int initialCapacity, double loadFactor) {
		return new HashSet<E>(initialCapacity, (float) loadFactor);
	}

	/**
	 * 根据给定的Bean属性名称, 按升序顺序排序集合里面的对象
	 * 
	 * @param collection
	 *            集合
	 * @param propertyName
	 *            属性名称
	 */
	public static <E> void sort(Collection<E> collection, String propertyName) {
		sort(collection, propertyName, true);
	}

	/**
	 * 根据给定的Bean属性名称, 按降序顺序排序集合里面的对象
	 * 
	 * @param collection
	 *            集合
	 * @param propertyName
	 *            属性名称
	 */
	public static <E> void sortByDesc(Collection<E> collection,
			String propertyName) {
		sort(collection, propertyName, false);
	}

	/**
	 * 根据给定的Bean属性名称, 排序集合里面的对象
	 * 
	 * @param collection
	 *            集合
	 * @param key
	 *            排序关键字
	 * @param asc
	 *            是否升序
	 */
	private static <E> void sort(Collection<E> collection, String key,
			boolean asc) {
		List<E> list = new ArrayList<E>(collection);
		Class<?> type = collection.iterator().next().getClass();
		collection.clear();
		collection.addAll(list);
	}

	

	

}
package com.yc.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

public class BeanUtils {

	static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 灏嗚姹傚弬鏁拌浆鎹㈣缃埌bean鐨勫悇涓瓧娈典腑
	 * @param request
	 * @param t
	 * @return
	 */
	public static <T> T toBean(ServletRequest request, T t) {
		//杈撳叆鐨勫�煎璞涓虹┖鍒欓��鍑�
		if (t == null)
			return t;
		for (Field f : getAllFields(t.getClass())) {
			//鑾峰彇鍙傛暟鍊�
			String svalue = request.getParameter(f.getName());
			//绌哄�艰烦杩�
			if (svalue == null)
				continue;
			try {
				//澹版槑杞崲鍚庣殑鍊煎彉閲�
				Object ovalue = cast(svalue, f.getType());
				f.setAccessible(true);
				f.set(t, ovalue);
			} catch (Exception e) {
				System.err.println("璇锋眰鍙傛暟瑙ｆ瀽閿欒锛氬弬鏁板悕=" + f.getName() + "锛屽瓧娈电被鍨�=" + f.getType() + "锛屽弬鏁板��=" + svalue);
			}
		}

		return t;

	}

	public static <T> T asBean(ServletRequest request, Class<T> cls) {
		try {
			return toBean(request, cls.newInstance());
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public static Map<Object, Object> asMap(Object... objs) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		for (int i = 0; i < objs.length / 2; i++) {
			map.put(objs[i * 2], objs[i * 2 + 1]);
		}
		return map;
	}

	/**
	 * 灏嗗瓧绗︿覆杞垚鎸囧畾鐨勭被鍨嬶紝濡傛灉 杈撳叆鍊间负绌猴紝鎴栬�呰浆鎹㈣繃绋嬪嚭鐜板紓甯革紝鍒欒繑鍥為粯璁ゅ��
	 * @param svalue
	 * @param cls
	 * @param defaultValue 榛樿鍊�
	 * @return
	 */
	public static <T> T cast(String svalue, Class<T> cls, T defaultValue) {
		if (svalue == null) {
			return defaultValue;
		} else {
			try {
				T ret = cast(svalue, cls);
				return ret == null ? defaultValue : ret;
			} catch (Exception e) {
				System.err.println(e.getMessage());
				return defaultValue;
			}
		}
	}

	@SuppressWarnings("unchecked")
	/**
	 * 灏嗗彉閲忓�艰浆鎴愭寚瀹氱殑绫诲瀷
	 * @param svalue
	 * @param cls
	 * @return
	 */
	public static <T> T cast(Object value, Class<T> cls) {
		if (value == null) {
			return null;
		} else if (cls.isAssignableFrom(value.getClass())) {
			return (T) value;
		} else {
			switch (cls.getName()) {
			case "java.lang.String":
				return (T) value.toString();
			case "int":
			case "java.lang.Integer":
				return (T) Integer.valueOf("" + value);
			case "byte":
			case "java.lang.Byte":
				return (T) Byte.valueOf("" + value);
			case "short":
			case "java.lang.Short":
				return (T) Short.valueOf("" + value);
			case "long":
			case "java.lang.Long":
				return (T) Long.valueOf("" + value);
			case "float":
			case "java.lang.Float":
				return (T) Float.valueOf("" + value);
			case "double":
			case "java.lang.Double":
				return (T) Double.valueOf("" + value);
			case "boolean":
			case "java.lang.Boolean":
				return (T) Boolean.valueOf("" + value);
			case "char":
			case "java.lang.Character":
				String s = "" + value;
				return (T) Character.valueOf(s.length() == 0 ? null : s.charAt(0));
			case "java.sql.Date": // 娉ㄦ剰锛氭帴鏀舵棩鏈熺被鍨嬫湁鏍煎紡鐨勯棶棰�
				return (T) java.sql.Date.valueOf("" + value);
			case "java.sql.Timestamp": // 娉ㄦ剰锛氭帴鏀舵棩鏈熺被鍨嬫湁鏍煎紡鐨勯棶棰�
				return (T) java.sql.Timestamp.valueOf("" + value);
			default:
				return (T) value;
			}
		}
	}

	public static boolean canCast(Class<?> cls) {
		switch (cls.getName()) {
		case "java.lang.String":
		case "int":
		case "java.lang.Integer":
		case "byte":
		case "java.lang.Byte":
		case "short":
		case "java.lang.Short":
		case "long":
		case "java.lang.Long":
		case "float":
		case "java.lang.Float":
		case "double":
		case "java.lang.Double":
		case "boolean":
		case "java.lang.Boolean":
		case "char":
		case "java.lang.Character":
		case "java.sql.Date":
		case "java.sql.Timestamp":
			return true;
		}
		return false;
	}

	/**
	 * 鏍规嵁鏂规硶鍚嶈繑鍥炲尮閰嶇殑鏂规硶瀵硅薄
	 * @param cls
	 * @param methodName
	 * @return
	 */
	public static Method[] getMethodsByName(Class<?> cls, String methodName) {
		ArrayList<Method> list = new ArrayList<Method>();
		for (Method m : getAllMethods(cls)) {
			if (m.getName().equals(methodName)) {
				m.setAccessible(true);
				list.add(m);
			}
		}
		return list.toArray(new Method[list.size()]);

	}

	/**
	 * 杩斿洖绗竴涓笉涓虹┖鐨勫��
	 * @param values
	 * @return
	 */
	public static Object notNull(Object... values) {
		for (Object o : values) {
			if (o != null) {
				return o;
			}
		}
		return null;
	}

	/**
	 * 浠庡璞′腑鑾峰彇涓�涓瓧娈靛��
	 * @param bean		瀵硅薄
	 * @param fieldName	瀛楁鍚�
	 * @return			瀛楁鍊�
	 */
	public static Object getValue(Object bean, String fieldName) {
		Class<?> cls = bean.getClass();
		try {
			// 鑾峰彇瀛楁
			Field field = cls.getDeclaredField(fieldName);
			// 璁剧疆瀛楁鍙互鐩存帴璁块棶
			field.setAccessible(true);
			// 杩斿洖瀛楁鍊�
			return field.get(bean);
		} catch (NoSuchFieldException | SecurityException e) {
			System.out.printf("%s娌℃湁杩欎釜瀛楁锛�%s", cls.getName(), fieldName);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			System.out.printf("%s鏃犳硶鑾峰彇璇ュ瓧娈靛�硷細%s", cls.getName(), fieldName);
		}
		return null;
	}

	/**
	 * 浠庡璞′腑鑾峰彇涓�涓瓧娈靛��
	 * @param bean		瀵硅薄
	 * @param fieldName	瀛楁鍚�
	 * @return			瀛楁鍊�
	 */
	public static void setValue(Object bean, String fieldName, Object value) {
		Class<?> cls = bean.getClass();
		try {
			// 鑾峰彇瀛楁
			Field field = cls.getDeclaredField(fieldName);
			// 璁剧疆瀛楁鍙互鐩存帴璁块棶
			field.setAccessible(true);
			// 杩斿洖瀛楁鍊�
			field.set(bean, value);
		} catch (NoSuchFieldException | SecurityException e) {
			System.out.printf("%s娌℃湁杩欎釜瀛楁锛�%s", cls.getName(), fieldName);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			System.out.printf("%s鏃犳硶璁剧疆璇ュ瓧娈靛�硷細%s", cls.getName(), fieldName);
		}
	}

	/**
	 * 杩唬涓�涓璞★紝璇ュ璞″彲浠ユ槸锛氭暟缁勩�侀泦鍚堛�丮ap銆佸疄浣撳璞�
	 * @param items
	 * @param fields
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Iterable<Object> each(final Object items, Object... fields) {
		if (items instanceof Collection) {
			return ((Collection) items);
		}

		int tempCount;
		if (items instanceof Object[]) {
			tempCount = ((Object[]) items).length;
		} else if (fields.length == 1 && fields[0] != null && fields[0] instanceof Collection) {
			fields = ((Collection) fields[0]).toArray();
			tempCount = fields.length;
		} else {
			tempCount = fields.length;
		}

		final int _count = tempCount;
		final Object[] _fields = fields;

		/**
		 * 瀵� items 杩涜杩唬
		 */
		return new Iterable<Object>() {
			@Override
			public Iterator<Object> iterator() {
				return new Iterator<Object>() {
					int i = 0;

					@Override
					public boolean hasNext() {
						return i < _count;
					}

					@Override
					public Object next() {
						Object ret;
						if (items instanceof Object[]) {
							ret = ((Object[]) items)[i];
						} else if (items instanceof Map) {
							ret = ((Map) items).get(_fields[i]);
						} else {
							ret = getValue(items, "" + _fields[i]);
						}
						i++;
						return ret;
					}
				};
			}

		};
	}

	@SuppressWarnings("unchecked")
	public static <T> boolean contains(T obj, T... objs) {
		for (T o : objs) {
			if (obj == null) {
				if (o == null) {
					return true;
				}
			} else {
				if (obj.equals(o)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static Field getField(Class<?> cls, String fieldName) {
		for(Field f : getAllFields(cls)) {
			if(fieldName.equals(f.getName())) {
				return f;
			}
		}
		return null;
	}

	public static List<Field> getAllFields(Class<?> cls) {
		ArrayList<Field> fs = new ArrayList<Field>();
		getAllFields(cls, fs);
		return fs;
	}

	private static void getAllFields(Class<?> cls, ArrayList<Field> fs) {
		fs.addAll(Arrays.asList(cls.getDeclaredFields()));
		cls = cls.getSuperclass();
		if (cls != null) {
			getAllFields(cls, fs);
		}
	}

	public static List<Method> getAllMethods(Class<?> cls) {
		ArrayList<Method> ms = new ArrayList<Method>();
		getAllMethods(cls, ms);
		return ms;
	}

	private static void getAllMethods(Class<?> cls, ArrayList<Method> ms) {
		ms.addAll(Arrays.asList(cls.getDeclaredMethods()));
		cls = cls.getSuperclass();
		if (cls != null) {
			getAllMethods(cls, ms);
		}
	}

}

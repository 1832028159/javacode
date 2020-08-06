package test;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class GetListGeneric {

	private List<Character> list;

	public static void main(String[] args) {
		try {
			Field listField = GetListGeneric.class.getDeclaredField("list");
			ParameterizedType listGenericType = (ParameterizedType) listField.getGenericType();
			System.out.println(listGenericType.getActualTypeArguments()[0].getTypeName());
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

}

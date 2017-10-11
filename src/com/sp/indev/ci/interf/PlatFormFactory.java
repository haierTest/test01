package com.sp.indiv.ci.interf;

public class PlatFormFactory {
	/**
	 * 获取平台对象实例
	 * @param uri 平台接口实现类名(必须含包名)
	 * @return
	 */
	public static PlatFormService getInstance(String uri)
	{
		PlatFormService platFormService = null;
		try {
			Class c = Class.forName(uri);
			platFormService = (PlatFormService)c.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return platFormService;
	}
}

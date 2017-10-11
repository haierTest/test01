package com.sp.indiv.ci.interf;

public class PlatFormFactory {
	/**
	 * ��ȡƽ̨����ʵ��
	 * @param uri ƽ̨�ӿ�ʵ������(���뺬����)
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

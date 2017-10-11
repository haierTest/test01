package com.sp.customerreal.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.customerreal.blsvr.BLItmVisit;

/**
 * @ClassName: ItmVisit
 * @Description: �����طò���95510�ӿ�
 * @author
 * @date May 25, 2011 3:19:09 PM
 * 
 */
public class ItmVisitServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final Log logger = LogFactory.getLog(getClass());

	public void init() throws ServletException {


	}

	/**
	 * Title: doPost
	 * Description: �ֶ����������ط�д��95510
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("�����طö�ʱ���񣡿�ʼ������������������������������������");
		try {
			String postType = request.getParameter("flag");
			String strBDate = request.getParameter("strBDate");
			String strNDate = request.getParameter("strNDate");
			String policyNo = request.getParameter("policyNo");
			logger.info("postType: " + postType);
			logger.info("strBDate: " + strBDate);
			logger.info("strNDate: " + strNDate);
			logger.info("policyNo: " + policyNo);
			BLItmVisit blItmVisit = new BLItmVisit();
			if ("0".equals(postType)) {
				try {
					logger.info("ItmVisitServlet->doPost()->�����طð�ʱ���->��ʼ");
					
					List itmVisitDataDataList = blItmVisit.getItmVisitData(
							strBDate, strNDate,policyNo);
					
					blItmVisit.insertVisitDataData(itmVisitDataDataList);
					logger
							.info("CustomerRealServlet->doPost()->��XXXXX��ʵ�� ->ȡ����������->����");
				} catch (Exception e) {
					logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
				}
				logger.info("ItmVisitServlet->doPost()->�����طð�ʱ���->��ʼ");
			}

		} catch (Exception e) {
			logger.error("�����طô�����Ϣ��", e);
		}
		logger.info("�����طö�ʱ���񣡽���������������������������������������");
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
}

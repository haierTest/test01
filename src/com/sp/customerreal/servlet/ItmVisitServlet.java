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
 * @Description: 电销回访插入95510接口
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
	 * Description: 手动触发电销回访写入95510
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("电销回访定时任务！开始………………………………………………");
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
					logger.info("ItmVisitServlet->doPost()->电销回访按时间段->开始");
					
					List itmVisitDataDataList = blItmVisit.getItmVisitData(
							strBDate, strNDate,policyNo);
					
					blItmVisit.insertVisitDataData(itmVisitDataDataList);
					logger
							.info("CustomerRealServlet->doPost()->跑XXXXX真实性 ->取数，发短信->结束");
				} catch (Exception e) {
					logger.error("XXXXX真实性错误信息：", e);
				}
				logger.info("ItmVisitServlet->doPost()->电销回访按时间段->开始");
			}

		} catch (Exception e) {
			logger.error("电销回访错误信息：", e);
		}
		logger.info("电销回访定时任务！结束………………………………………………");
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
}

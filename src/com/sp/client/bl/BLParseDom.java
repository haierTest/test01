package com.sp.client.bl;

import org.w3c.dom.*;
import com.sp.client.Util.*;
import com.sp.client.dto.*;
import com.sp.sysframework.exceptionlog.UserException;
import java.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BLParseDom {
	static Log logger = LogFactory.getLog(BLParseDom.class.getName());
	public static ArrayList ParseDom(Element element) throws Exception {
		ArrayList policyList = new ArrayList();
		
		if (!element.getNodeName().equals("PACKET")) {
			throw new UserException(-98, -1007,
					"com.ygbx.singleInterface.bl.BLParseDom", "xml�ļ�����");
		}
		Element e1[] = XmlUtil.getChildElements(element);
		for (int k = 0; k < e1.length; k++) {
			Policy policy = new Policy();
			if (!e1[0].getNodeName().equals("POLICY")) {
				throw new UserException(-98, -1007,
						"com.ygbx.singleInterface.bl.BLParseDom", "xml�ļ�����");
			}
			Element e2[] = XmlUtil.getChildElements(e1[0]);
			for (int i = 0; i < e2.length; i++) {
				if (e2[i].getNodeName().equals("BASE_INFO")) {
					policy.setbaseInfo(getBaseInfo(e2[i]));
				}
				if (e2[i].getNodeName().equals("VHL_INFO")) {
					policy.setvhlInfoBean(getVhlInfo(e2[i]));
				}
				if (e2[i].getNodeName().equals("RDR_LIST")) {
					policy.setrdrList(getrdrList(e2[i]));
				}
				if (e2[i].getNodeName().equals("PAY_LIST")) {
					policy.setpayList(getpayList(e2[i]));
				}
				if (e2[i].getNodeName().equals("VCH_LIST")) {
					policy.setvchList(getvchList(e2[i]));
				}
			}
			policyList.add(policy);
		}
		return policyList;
	}

	public static BaseInfoBean getBaseInfo(Element element)
			throws UserException {
		BaseInfoBean baseInfoBean = new BaseInfoBean();
		Element e[] = XmlUtil.getChildElements(element);
		for (int i = 0; i < e.length; i++) {
			if (e[i].getNodeName().equals("C_PLY_APP_NO")) {
				
				baseInfoBean.setPlyAppNo(XmlUtil.getElenentValue(e[i]));
			}
			
			if (e[i].getNodeName().equals("C_PLY_NO")) {
				baseInfoBean.setPlyNo(XmlUtil.getElenentValue(e[i]));
			}
			
			if (e[i].getNodeName().equals("C_BSNS_TYP")) {
				baseInfoBean.setBsnsType(XmlUtil.getElenentValue(e[i]));
			}
			
			if (e[i].getNodeName().equals("C_DPT_CDE")) {
				baseInfoBean.setDptCde(XmlUtil.getElenentValue(e[i]));
			}
			
			if (e[i].getNodeName().equals("C_CRT_CDE")) {
				baseInfoBean.setC_CRT_CDE(XmlUtil.getElenentValue(e[i]));
			}
			
			if (e[i].getNodeName().equals("C_SLS_CDE")) {
				baseInfoBean.setSlsCde(XmlUtil.getElenentValue(e[i]));
			}
			
			if (e[i].getNodeName().equals("C_AGT_CDE")) {
				baseInfoBean.setAgtCde(XmlUtil.getElenentValue(e[i]));
			}
			
			if (e[i].getNodeName().equals("C_AGT_NO")) {
				baseInfoBean.setAgtNo(XmlUtil.getElenentValue(e[i]));
			}
			
			if (e[i].getNodeName().equals("C_APP_CNM")) {
				baseInfoBean.setAppCnm(XmlUtil.getElenentValue(e[i]));
			}
			
			if (e[i].getNodeName().equals("C_INSRNT_CNM")) {
				baseInfoBean.setInsrntCnm(XmlUtil.getElenentValue(e[i]));
			}
			
			if (e[i].getNodeName().equals("C_INSRNT_ADDR")) {
				baseInfoBean.setInsrntAdd(XmlUtil.getElenentValue(e[i]));
			}
			
			if (e[i].getNodeName().equals("C_INSRNT_ZIP")) {
				baseInfoBean.setInsrntZip(XmlUtil.getElenentValue(e[i]));
			}
			
			if (e[i].getNodeName().equals("C_INSRNT_TEL")) {
				baseInfoBean.setInsrntTel(XmlUtil.getElenentValue(e[i]));
			}
			
			if (e[i].getNodeName().equals("C_INSRNT_ID")) {
				baseInfoBean.setInsrntId(XmlUtil.getElenentValue(e[i]));
			}
			
			if (e[i].getNodeName().equals("C_OWN_CNM")) {
				baseInfoBean.setOwnCnm(XmlUtil.getElenentValue(e[i]));
			}
			
			if (e[i].getNodeName().equals("D_APP_DATE")) {
				String stAppDate = XmlUtil.getElenentValue(e[i]);
				if (!CheckUtil.bCheckDate(stAppDate)) {
					throw new UserException(-98, -1007,
							"com.ygbx.singleInterface.bl.BLParseDom", "XX����"
									+ stAppDate + "���Ͳ���ȷ��");
				}
				baseInfoBean.setAppDate(stAppDate);
			}
			
			if (e[i].getNodeName().equals("D_SIGN_DATE")) {
				String stSignDate = XmlUtil.getElenentValue(e[i]);
				if (!CheckUtil.bCheckDate(stSignDate)) {
					throw new UserException(-98, -1007,
							"com.ygbx.singleInterface.bl.BLParseDom", "ǩ������"
									+ stSignDate + "���Ͳ���ȷ��");
				}
				baseInfoBean.setSignDate(stSignDate);
			}
			
			if (e[i].getNodeName().equals("D_INSBGN_DATE")) {
				String stInsbgnDate = XmlUtil.getElenentValue(e[i]);
				if (!CheckUtil.bCheckDate(stInsbgnDate)) {
					throw new UserException(-98, -1007,
							"com.ygbx.singleInterface.bl.BLParseDom", "XX����"
									+ stInsbgnDate + "���Ͳ���ȷ��");
				}
				baseInfoBean.setInsbgnDate(stInsbgnDate);
			}
			
			if (e[i].getNodeName().equals("D_INSEND_DATE")) {
				String stInsendDate = XmlUtil.getElenentValue(e[i]);
				if (!CheckUtil.bCheckDate(stInsendDate)) {
					throw new UserException(-98, -1007,
							"com.ygbx.singleInterface.bl.BLParseDom", "XXֹ��"
									+ stInsendDate + "���Ͳ���ȷ��");
				}
				baseInfoBean.setInsrntDate(stInsendDate);
			}
			
			if (e[i].getNodeName().equals("N_AMT")) {
				try {
					baseInfoBean.setAmt(Double.parseDouble(XmlUtil
							.getElenentValue(e[i])));
					
					logger.info(XmlUtil.getElenentValue(e[i]));
					
				} catch (Exception ue) {
					throw new UserException(-98, -1007,
							"com.ygbx.singleInterface.bl.BLParseDom", "XX/�޶�ϼ�"
									+ XmlUtil.getElenentValue(e[i]) + "���Ͳ���ȷ��");
				}
			}
			
			if (e[i].getNodeName().equals("N_PRM")) {
				try {
					baseInfoBean.setPrm(Double.parseDouble(XmlUtil
							.getElenentValue(e[i])));
				} catch (Exception ue) {
					throw new UserException(-98, -1007,
							"com.ygbx.singleInterface.bl.BLParseDom", "XX�ϼ�"
									+ XmlUtil.getElenentValue(e[i]) + "���Ͳ���ȷ��");
				}
			}
			
			if (e[i].getNodeName().equals("N_CMM_PROP")) {
				try {
					baseInfoBean.setCmmProp(Double.parseDouble(XmlUtil
							.getElenentValue(e[i])));
				} catch (Exception ue) {
					
					logger.error(e[i].getNodeValue());
					
					throw new UserException(-98, -1007,
							"com.ygbx.singleInterface.bl.BLParseDom", "�����ѱ���"
									+ XmlUtil.getElenentValue(e[i]) + "���Ͳ���ȷ��");
				}
			}
			
			if (e[i].getNodeName().equals("N_RATIO")) {
				baseInfoBean.setRatio(Double.parseDouble(XmlUtil.getElenentValue(e[i])));
			}
			
			if (e[i].getNodeName().equals("C_SHORT_RSN")) {
				baseInfoBean.setShortRsn(XmlUtil.getElenentValue(e[i]));
			}
			
			if (e[i].getNodeName().equals("C_CUR_CDE")) {
				baseInfoBean.setCurCde(XmlUtil.getElenentValue(e[i]));
			}
			
			if (e[i].getNodeName().equals("C_STTL_CDE")) {
				baseInfoBean.setSttlCde(XmlUtil.getElenentValue(e[i]));
			}
			
			if (e[i].getNodeName().equals("C_APPNT_CDE")) {
				baseInfoBean.setC_APPNT_CDE(XmlUtil.getElenentValue(e[i]));
			}
			if (e[i].getNodeName().equals("C_APPNT")) {
				baseInfoBean.setAppnt(XmlUtil.getElenentValue(e[i]));
			}
			
			if (e[i].getNodeName().equals("C_NOTICE")) {
				baseInfoBean.setNotice(XmlUtil.getElenentValue(e[i]));
			}
			
			if (e[i].getNodeName().equals("C_PROPERTY")) {
				baseInfoBean.setC_PROPERTY(XmlUtil.getElenentValue(e[i]));
			}
			
			if (e[i].getNodeName().equals("C_SEX")) {
				baseInfoBean.setC_SEX(XmlUtil.getElenentValue(e[i]));
			}
			
			if (e[i].getNodeName().equals("N_AGE")) {
				baseInfoBean.setN_AGE(XmlUtil.getElenentValue(e[i]));
			}
			
			if (e[i].getNodeName().equals("C_MARRIAGE")) {
				baseInfoBean.setC_MARRIAGE(XmlUtil.getElenentValue(e[i]));
			}
			
			if (e[i].getNodeName().equals("C_VHL_REL")) {
				baseInfoBean.setC_VHL_REL(XmlUtil.getElenentValue(e[i]));
			}
			
			if (e[i].getNodeName().equals("C_TAT_CLNT")) {
				baseInfoBean.setC_TAT_CLNT(XmlUtil.getElenentValue(e[i]));
			}
		}
		return baseInfoBean;
	}

	public static VhlInfoBean getVhlInfo(Element element) throws UserException {
		VhlInfoBean vhlInfoBean = new VhlInfoBean();
		Element e[] = XmlUtil.getChildElements(element);
		for (int i = 0; i < e.length; i++) {
			if (e[i].getNodeName().equals("C_DRV_RGN")) {
				vhlInfoBean.setC_DRV_RGN(XmlUtil.getElenentValue(e[i]));
			}
			if (e[i].getNodeName().equals("D_BOOK_DATE")) {
				String stBookDate = XmlUtil.getElenentValue(e[i]);
				if (!CheckUtil.bCheckDate(stBookDate)) {
					throw new UserException(-98, -1007,
							"com.ygbx.singleInterface.bl.BLParseDom", "���εǼ���"
									+ stBookDate + "���Ͳ���ȷ��");
				}
				vhlInfoBean.setD_BOOK_DATE(stBookDate);
			}
			if (e[i].getNodeName().equals("C_CHKVHL_MEMO")) {
				vhlInfoBean.setC_CHKVHL_MEMO(XmlUtil.getElenentValue(e[i]));
			}
			if (e[i].getNodeName().equals("N_NEW_PRICE")) {
				String stNewPrice = XmlUtil.getElenentValue(e[i]);
				try {
					double dNewPrice = Double.parseDouble(stNewPrice);
				} catch (Exception ex) {
					throw new UserException(-98, -1007,
							"com.ygbx.singleInterface.bl.BLParseDom", "�³����ü�"
									+ stNewPrice + "���Ͳ���ȷ��");
				}
				vhlInfoBean.setN_NEW_PRICE(stNewPrice);
			}
			if (e[i].getNodeName().equals("C_VHL_VIN")) {
				vhlInfoBean.setC_VHL_VIN(XmlUtil.getElenentValue(e[i]));
			}
			if (e[i].getNodeName().equals("C_BRD_CDE")) {
				vhlInfoBean.setC_BRD_CDE(XmlUtil.getElenentValue(e[i]));
			}
			if (e[i].getNodeName().equals("C_USE_ATR")) {
				vhlInfoBean.setC_USE_ATR(XmlUtil.getElenentValue(e[i]));
			}
			if (e[i].getNodeName().equals("C_CHKVHL_CDE")) {
				vhlInfoBean.setC_CHKVHL_CDE(XmlUtil.getElenentValue(e[i]));
			}
			if (e[i].getNodeName().equals("N_TON")) {
				String stTon = XmlUtil.getElenentValue(e[i]);
				try {
					float fSeat = Float.parseFloat(stTon);
				} catch (Exception ex) {
					throw new UserException(-98, -1007,
							"com.ygbx.singleInterface.bl.BLParseDom", "��λ��"
									+ stTon + "���Ͳ���ȷ��");
				}
				vhlInfoBean.setN_TON(stTon);
			}
			if (e[i].getNodeName().equals("D_CHKVHL_DATE")) {
				String stChkvhlDate = XmlUtil.getElenentValue(e[i]);
				if (stChkvhlDate != null && !stChkvhlDate.equals("")) {
					if (!CheckUtil.bCheckDate(stChkvhlDate)) {
						throw new UserException(-98, -1007,
								"com.ygbx.singleInterface.bl.BLParseDom",
								"�鳵����" + stChkvhlDate + "���Ͳ���ȷ��");
					}
					vhlInfoBean.setD_CHKVHL_DATE(stChkvhlDate);
				}
			}
			if (e[i].getNodeName().equals("C_BRD_CNM")) {
				vhlInfoBean.setC_BRD_CNM(XmlUtil.getElenentValue(e[i]));
			}
			if (e[i].getNodeName().equals("C_REMARK")) {
				vhlInfoBean.setC_REMARK(XmlUtil.getElenentValue(e[i]));
			}
			if (e[i].getNodeName().equals("C_ENG_NO")) {
				vhlInfoBean.setC_ENG_NO(XmlUtil.getElenentValue(e[i]));
			}
			if (e[i].getNodeName().equals("C_CHKVHL_EMP")) {
				vhlInfoBean.setC_CHKVHL_EMP(XmlUtil.getElenentValue(e[i]));
			}
			if (e[i].getNodeName().equals("C_PLY_NO")) {
				vhlInfoBean.setC_PLY_NO(XmlUtil.getElenentValue(e[i]));
			}
			if (e[i].getNodeName().equals("C_DEV_CDE")) {
				vhlInfoBean.setC_DEV_CDE(XmlUtil.getElenentValue(e[i]));
			}
			if (e[i].getNodeName().equals("N_SEAT")) {
				String stSeat = XmlUtil.getElenentValue(e[i]);
				try {
					int iSeat = Integer.parseInt(stSeat);
				} catch (Exception ex) {
					throw new UserException(-98, -1007,
							"com.ygbx.singleInterface.bl.BLParseDom", "��λ��"
									+ stSeat + "���Ͳ���ȷ��");
				}
				vhlInfoBean.setN_SEAT(stSeat);
			}
			if (e[i].getNodeName().equals("C_LCN_COLOR")) {
				vhlInfoBean.setC_LCN_COLOR(XmlUtil.getElenentValue(e[i]));
			}
			if (e[i].getNodeName().equals("C_VHL_FRM")) {
				vhlInfoBean.setC_VHL_FRM(XmlUtil.getElenentValue(e[i]));
			}
			if (e[i].getNodeName().equals("C_LCN_NO")) {
				vhlInfoBean.setC_LCN_NO(XmlUtil.getElenentValue(e[i]));
			}
			if (e[i].getNodeName().equals("C_VHL_TYP")) {
				vhlInfoBean.setC_VHL_TYP(XmlUtil.getElenentValue(e[i]));
			}
		}
		return vhlInfoBean;
	}

	public static ArrayList getrdrList(Element element) throws UserException {
		ArrayList rdrList = new ArrayList();
		RdrInfoBean rdrInfoBean = null;
		Element e1[] = XmlUtil.getChildElements(element);
		for (int i = 0; i < e1.length; i++) {
			rdrInfoBean = new RdrInfoBean();
			Element e2[] = XmlUtil.getChildElements(e1[i]);
			for (int j = 0; j < e2.length; j++) {
				if (e2[j].getNodeName().equals("C_RDR_CDE")) {
					rdrInfoBean.setC_RDR_CDE(XmlUtil.getElenentValue(e2[j]));
				}
				if (e2[j].getNodeName().equals("N_BBSC_PRM")) {
					String stBbscPrm = XmlUtil.getElenentValue(e2[j]);
					try {
						double dGotPrm = Double.parseDouble(stBbscPrm);
					} catch (Exception ex) {
						throw new UserException(-98, -1007,
								"com.ygbx.singleInterface.bl.BLParseDom",
								"����XX" + stBbscPrm + "���Ͳ���ȷ��");
					}
					rdrInfoBean.setN_BBSC_PRM(stBbscPrm);
				}
				if (e2[j].getNodeName().equals("N_DIS_PRM")) {
					String stDisPrm = XmlUtil.getElenentValue(e2[j]);
					try {
						double dDisPrm = Double.parseDouble(stDisPrm);
					} catch (Exception ex) {
						throw new UserException(-98, -1007,
								"com.ygbx.singleInterface.bl.BLParseDom",
								"��ǰXX" + stDisPrm + "���Ͳ���ȷ��");
					}
					rdrInfoBean.setN_DIS_PRM(stDisPrm);
				}
				if (e2[j].getNodeName().equals("N_SEAT_DAY")) {
					String stSeatDay = XmlUtil.getElenentValue(e2[j]);
					try {
						int iGotPrm = Integer.parseInt(stSeatDay);
					} catch (Exception ex) {
						throw new UserException(-98, -1007,
								"com.ygbx.singleInterface.bl.BLParseDom", "��/��"
										+ stSeatDay + "���Ͳ���ȷ��");
					}
					rdrInfoBean.setN_SEAT_DAY(stSeatDay);
				}
				if (e2[j].getNodeName().equals("C_PLY_NO")) {
					rdrInfoBean.setC_PLY_NO(XmlUtil.getElenentValue(e2[i]));
				}
				if (e2[i].getNodeName().equals("N_COEF")) {
					String stCoef = XmlUtil.getElenentValue(e2[j]);
					try {
						double dCoef = Double.parseDouble(stCoef);
					} catch (Exception ex) {
						throw new UserException(-98, -1007,
								"com.ygbx.singleInterface.bl.BLParseDom",
								"����ϵ��" + stCoef + "���Ͳ���ȷ��");
					}
					rdrInfoBean.setN_COEF(stCoef);
				}
				if (e2[j].getNodeName().equals("N_FUND")) {
					String stFund = XmlUtil.getElenentValue(e2[j]);
					try {
						double dGotPrm = Double.parseDouble(stFund);
					} catch (Exception ex) {
						throw new UserException(-98, -1007,
								"com.ygbx.singleInterface.bl.BLParseDom", "����"
										+ stFund + "���Ͳ���ȷ��");
					}
					rdrInfoBean.setN_FUND(stFund);
				}
				if (e2[j].getNodeName().equals("N_RATIO")) {
					String stRatio = XmlUtil.getElenentValue(e2[j]);
					try {
						double dGotPrm = Double.parseDouble(stRatio);
					} catch (Exception ex) {
						throw new UserException(-98, -1007,
								"com.ygbx.singleInterface.bl.BLParseDom",
								"����ϵ��" + stRatio + "���Ͳ���ȷ��");
					}
					rdrInfoBean.setN_RATIO(stRatio);
				}
				if (e2[j].getNodeName().equals("N_FUND_PROP")) {
					String stFundProp = XmlUtil.getElenentValue(e2[j]);
					try {
						double dGotPrm = Double.parseDouble(stFundProp);
					} catch (Exception ex) {
						throw new UserException(-98, -1007,
								"com.ygbx.singleInterface.bl.BLParseDom",
								"�������" + stFundProp + "���Ͳ���ȷ��");
					}
					rdrInfoBean.setN_FUND_PROP(stFundProp);
				}
				if (e2[j].getNodeName().equals("N_PRM")) {
					String stPrm = XmlUtil.getElenentValue(e2[j]);
					try {
						double dGotPrm = Double.parseDouble(stPrm);
					} catch (Exception ex) {
						throw new UserException(-98, -1007,
								"com.ygbx.singleInterface.bl.BLParseDom",
								"ʵ��XX" + stPrm + "���Ͳ���ȷ��");
					}
					rdrInfoBean.setN_PRM(stPrm);
				}
				if (e2[j].getNodeName().equals("N_AMT")) {
					String stAmt = XmlUtil.getElenentValue(e2[j]);
					try {
						double dGotPrm = Double.parseDouble(stAmt);
					} catch (Exception ex) {
						throw new UserException(-98, -1007,
								"com.ygbx.singleInterface.bl.BLParseDom", "XX"
										+ stAmt + "���Ͳ���ȷ��");
					}
					rdrInfoBean.setN_AMT(stAmt);
				}
				if (e2[j].getNodeName().equals("N_RATE")) {
					String stRate = XmlUtil.getElenentValue(e2[j]);
					try {
						double dGotPrm = Double.parseDouble(stRate);
					} catch (Exception ex) {
						throw new UserException(-98, -1007,
								"com.ygbx.singleInterface.bl.BLParseDom", "����"
										+ stRate + "���Ͳ���ȷ��");
					}
					rdrInfoBean.setN_RATE(stRate);
				}
			}
			rdrList.add(rdrInfoBean);
		}
		return rdrList;
	}

	public static ArrayList getpayList(Element element) throws UserException {
		ArrayList payList = new ArrayList();
		PayInfoBean payInfoBean = null;
		Element e1[] = XmlUtil.getChildElements(element);
		for (int i = 0; i < e1.length; i++) {
			payInfoBean = new PayInfoBean();
			Element e2[] = XmlUtil.getChildElements(e1[i]);
			for (int j = 0; j < e2.length; j++) {
				
				
				
				
				
				
				
				
				
				if (e2[j].getNodeName().equals("D_PAY_DATE")) {
					String stPayDate = XmlUtil.getElenentValue(e2[j]);
					payInfoBean.setD_PAY_DATE(stPayDate);
				}
				if (e2[j].getNodeName().equals("C_PAY_MODE")) {
					payInfoBean.setC_PAY_MODE(XmlUtil.getElenentValue(e2[j]));
				}
				if (e2[j].getNodeName().equals("C_PAY_CNM")) {
					payInfoBean.setC_PAY_CNM(XmlUtil.getElenentValue(e2[j]));
				}
				if (e2[j].getNodeName().equals("N_PAY_TERM")) {
					payInfoBean.setN_PAY_TERM(XmlUtil.getElenentValue(e2[j]));
				}
				if (e2[j].getNodeName().equals("N_GOT_PRM")) {
					String stGotPrm = XmlUtil.getElenentValue(e2[j]);
					try {
						double dGotPrm = Double.parseDouble(stGotPrm);
					} catch (Exception ex) {
						throw new UserException(-98, -1007,
								"com.ygbx.singleInterface.bl.BLParseDom",
								"ʵ��XX" + stGotPrm + "���Ͳ���ȷ��");
					}
					payInfoBean.setN_GOT_PRM(stGotPrm);
				}
				if (e2[j].getNodeName().equals("N_GET_PRM")) {
					String stGetPrm = XmlUtil.getElenentValue(e2[j]);
					try {
						double dGetPrm = Double.parseDouble(stGetPrm);
					} catch (Exception ex) {
						throw new UserException(-98, -1007,
								"com.ygbx.singleInterface.bl.BLParseDom",
								"Ӧ��XX" + stGetPrm + "���Ͳ���ȷ��");
					}
					payInfoBean.setN_GET_PRM(stGetPrm);
				}
				if (e2[j].getNodeName().equals("C_PLY_NO")) {
					payInfoBean.setC_PLY_NO(XmlUtil.getElenentValue(e2[j]));
				}
			}
			payList.add(payInfoBean);
		}
		return payList;
	}

	public static ArrayList getvchList(Element element) throws UserException {
		ArrayList vchList = new ArrayList();
		VchInfoBean vchInfoBean = null;
		Element e1[] = XmlUtil.getChildElements(element);
		for (int i = 0; i < e1.length; i++) {
			vchInfoBean = new VchInfoBean();
			Element e2[] = XmlUtil.getChildElements(e1[i]);
			for (int j = 0; j < e2.length; j++) {
				if (e2[j].getNodeName().equals("C_VCH_TYP")) {
					vchInfoBean.setC_VCH_TYP(XmlUtil.getElenentValue(e2[j]));
				}
				if (e2[j].getNodeName().equals("C_PRN_STATUS")) {
					vchInfoBean.setC_PRN_STATUS(XmlUtil.getElenentValue(e2[j]));
				}
				if (e2[j].getNodeName().equals("D_USE_DATE")) {
					String stUseDate = XmlUtil.getElenentValue(e2[j]);
					if (!CheckUtil.bCheckDate(stUseDate)) {
						throw new UserException(-98, -1007,
								"com.ygbx.singleInterface.bl.BLParseDom",
								"����ʱ��" + stUseDate + "���Ͳ���ȷ��");
					}
					vchInfoBean.setD_USE_DATE(stUseDate);
				}
				if (e2[j].getNodeName().equals("C_PLY_NO")) {
					vchInfoBean.setC_PLY_NO(XmlUtil.getElenentValue(e2[j]));
				}
				if (e2[j].getNodeName().equals("C_VCH_NO")) {
					vchInfoBean.setC_VCH_NO(XmlUtil.getElenentValue(e2[j]));
				}
			}
			vchList.add(vchInfoBean);
		}
		return vchList;
	}
}

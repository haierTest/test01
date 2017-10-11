package com.sp.indiv.ci.schema;

import java.util.ArrayList;
import java.util.List;

import com.sp.utility.string.Str;

/**
 * 平台XXXXX信息查询schema
 * 
 * @author luogang
 * 
 */
public class CIQueryClaimInfoSchema {

	private String CarMark = "";

	private String VehicleType = "";

	private String EngineNo = "";

	private String RackNo = "";

	private List CIQueryClaimInfoList = new ArrayList(); 

	public String toString() {
		StringBuffer sb = new StringBuffer(400);
		sb.append("[CarMark=" + CarMark);
		sb.append(",VehicleType=" + VehicleType);
		sb.append(",EngineNo=" + EngineNo);
		sb.append(",RackNo=" + RackNo);
		for (int i = 0; i < CIQueryClaimInfoList.size(); i++) {
			sb.append(",CIQueryClaimInfoList:" + CIQueryClaimInfoList.get(i));
		}
		sb.append("]");
		return sb.toString();
	}

	public String getCarMark() {
		return Str.rightTrim(CarMark);
	}

	public void setCarMark(String carMark) {
		CarMark = Str.rightTrim(carMark);
	}

	public String getEngineNo() {
		return Str.rightTrim(EngineNo);
	}

	public void setEngineNo(String engineNo) {
		EngineNo = Str.rightTrim(engineNo);
	}

	public String getRackNo() {
		return Str.rightTrim(RackNo);
	}

	public void setRackNo(String rackNo) {
		RackNo = Str.rightTrim(rackNo);
	}

	public String getVehicleType() {
		return Str.rightTrim(VehicleType);
	}

	public void setVehicleType(String vehicleType) {
		VehicleType = Str.rightTrim(vehicleType);
	}

	public List getCIQueryClaimInfoList() {
		return CIQueryClaimInfoList;
	}

	public void setCIQueryClaimInfoList(List queryClaimInfoList) {
		CIQueryClaimInfoList = queryClaimInfoList;
	}

}

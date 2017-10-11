package com.sp.indiv.ci.schema;
  
import java.io.Serializable;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;

/**
 * ���彻�ܳ�����Ϣ��ѯ-CIInsurePmVehicleSchema�����ݴ��������
 * add by fanjiangtao 
 * create date 2014-01-24
 */
public class CIInsurePmVehicleSchema implements Serializable{
    
    private String PmVehicleID  = "";
    
    private String CarMark = "";
    
    private String VehicleType = "";
    
    private String RackNo = "";
    
    private String EngineNo = "";
    
    private String VehicleStyle = "";
    
    private String PmUseType = "";
    
    private String IneffectualDate = "";
    
    private String RejectDate = "";
    
    private String VehicleRegisterDate= "";
    
    private String LastCheckDate = "";
    
    private String TransferDate = "";
    
    private String WholeWeight = "";
    
    private String LimitLoadPerson = "";
    
    private String LimitLoad = "";
    
    private String Displacement = "";
    
    private String MadeFactory = "";
    
    private String VehicleModel = "";
    
    private String ProducerType = "";
    
    private String VehicleBrand1 = "";
    
    private String VehicleBrand2 = "";
    
    private String Haulage = "";
    
    private String VehicleColour = "";
    
    private String SalePrice = "";
    
    private String PmFuelType = "";
    
    private String Status = "";
    
    private String VehicleCategory = "";
    
    private String InsertDate = "";
    
    private String UpdateDate = "";
    
    private String ValidFlag = "";
    
    private String OperateSite = "";
    
    private String Flag = "";
    
    private String DemandNo="";
    
    private String OwnerName="";
    
    
    /**
     * ���캯��
     */       
    public CIInsurePmVehicleSchema(){
    }

    /**
     * �������Ժ��ƺ���
     * @param CarMark ���ƺ���
     */
    public void setCarMark(String CarMark){
        this.CarMark = Str.rightTrim(CarMark);
    }

    /**
     * ��ȡ���Ժ��ƺ���
     * @return ���ƺ���
     */
    public String getCarMark(){
        return Str.rightTrim(CarMark);
    }

    /**
     * �������Ժ�������
     * @param VehicleType ��������
     */
    public void setVehicleType(String VehicleType){
        this.VehicleType = Str.rightTrim(VehicleType);
    }

    /**
     * ��ȡ���Ժ�������
     * @return ��������
     */
    public String getVehicleType(){
        return Str.rightTrim(VehicleType);
    }

    /**
     * �������Գ���ʶ����ţ����ܺ�/VIN�룩
     * @param PolicyNo XX��
     */
    public void setRackNo(String RackNo){
        this.RackNo = Str.rightTrim(RackNo);
    }

    /**
     * ��ȡ���Գ���ʶ����ţ����ܺ�/VIN�룩
     * @return ����ʶ����ţ����ܺ�/VIN�룩
     */
    public String getRackNo(){
        return Str.rightTrim(RackNo);
    }

    /**
     * �������Է�������
     * @param EngineNo ��������
     */
    public void setEngineNo(String EngineNo){
        this.EngineNo = Str.rightTrim(EngineNo);
    }

    /**
     * ��ȡ���Է�������
     * @return ��������
     */
    public String getEngineNo(){
        return Str.rightTrim(EngineNo);
    }

    /**
     * �������Խ��ܳ�������
     * @param VehicleStyle ���ܳ�������
     */
    public void setVehicleStyle(String VehicleStyle){
        this.VehicleStyle = Str.rightTrim(VehicleStyle);
    }

    /**
     * ��ȡ���Խ��ܳ�������
     * @return ���ܳ�������
     */
    public String getVehicleStyle(){
        return Str.rightTrim(VehicleStyle);
    }

    /**
     * �������Խ���ʹ�����ʴ���
     * @param PmUseType ����ʹ�����ʴ���
     */
    public void setPmUseType(String PmUseType){
        this.PmUseType = Str.rightTrim(PmUseType);
    }

    /**
     * ��ȡ���Խ���ʹ�����ʴ���
     * @return ����ʹ�����ʴ���
     */
    public String getPmUseType(){
        return Str.rightTrim(PmUseType);
    }

    /**
     * �������Լ�����Ч����ֹ
     * @param IneffectualDate ������Ч����ֹ
     */
    public void setIneffectualDate(String IneffectualDate){
        ChgDate chgDate = new ChgDate(); 
        IneffectualDate = chgDate.toFormat(IneffectualDate);
        if  (IneffectualDate == null || IneffectualDate.length() == 0 ) 
        {
          this.IneffectualDate = "";
        }else
        {
         this.IneffectualDate = IneffectualDate.trim().substring(0,10);
        }
    }

    /**
     * ��ȡ���Լ�����Ч����ֹ
     * @return ������Ч����ֹ
     */
    public String getIneffectualDate(){
        return Str.rightTrim(IneffectualDate);
    }

    /**
     * ��������ǿ�Ʊ�����ֹ
     * @param RejectDate ǿ�Ʊ�����ֹ
     */
    public void setRejectDate(String RejectDate){
        ChgDate chgDate = new ChgDate(); 
        RejectDate = chgDate.toFormat(RejectDate);
        if  (RejectDate == null || RejectDate.length() == 0 ) 
        {
          this.RejectDate = "";
        }else
        {
         this.RejectDate = RejectDate.trim().substring(0,10);
        }
    }

    /**
     * ��ȡ����ǿ�Ʊ�����ֹ
     * @return ǿ�Ʊ�����ֹ
     */
    public String getRejectDate(){
        return Str.rightTrim(RejectDate);
    }

    /**
     * �������Գ�����ʼ�Ǽ�����
     * @param VehicleRegisterDate ������ʼ�Ǽ�����
     */
    public void setVehicleRegisterDate(String VehicleRegisterDate){
        ChgDate chgDate = new ChgDate(); 
        VehicleRegisterDate = chgDate.toFormat(VehicleRegisterDate);
        if  (VehicleRegisterDate == null || VehicleRegisterDate.length() == 0 ) 
        {
          this.VehicleRegisterDate = "";
        }else
        {
         this.VehicleRegisterDate = VehicleRegisterDate.trim().substring(0,10);
        }
    }

    /**
     * ��ȡ���Գ�����ʼ�Ǽ�����
     * @return ������ʼ�Ǽ�����
     */
    public String getVehicleRegisterDate(){
        return Str.rightTrim(VehicleRegisterDate);
    }

    /**
     * �������������������
     * @param LastCheckDate �����������
     */
    public void setLastCheckDate(String LastCheckDate){
        ChgDate chgDate = new ChgDate(); 
        LastCheckDate = chgDate.toFormat(LastCheckDate);
        if  (LastCheckDate == null || LastCheckDate.length() == 0 ) 
        {
          this.LastCheckDate = "";
        }else
        {
         this.LastCheckDate = LastCheckDate.trim().substring(0,10);
        }
    }

    /**
     * ��ȡ���������������
     * @return �����������
     */
    public String getLastCheckDate(){
        return Str.rightTrim(LastCheckDate);
    }

    /**
     * ��������ת�ƵǼ�����
     * @param TransferDate ת�ƵǼ�����
     */
    public void setTransferDate(String TransferDate){
      ChgDate chgDate = new ChgDate(); 
      TransferDate = chgDate.toFormat(TransferDate);
      if  (TransferDate == null || TransferDate.length() == 0 ) 
      {
        this.TransferDate = "";
      }else
      {
       this.TransferDate = TransferDate.trim().substring(0,10);
      }
    }

    /**
     * ��ȡ����ת�ƵǼ�����
     * @return ת�ƵǼ�����
     */
    public String getTransferDate(){
        return Str.rightTrim(TransferDate);
    }

    /**
     * ����������������
     * @param WholeWeight ��������
     */
    public void setWholeWeight(String WholeWeight){
        this.WholeWeight = Str.rightTrim(WholeWeight);
    }

    /**
     * ��ȡ������������
     * @return ��������
     */
    public String getWholeWeight(){
        return Str.rightTrim(WholeWeight);
    }

    /**
     * �������Ժ˶��ؿ�����
     * @param LimitLoadPerson �˶��ؿ�����
     */
    public void setLimitLoadPerson(String LimitLoadPerson){
        this.LimitLoadPerson = Str.rightTrim(LimitLoadPerson);
    }

    /**
     * ��ȡ���Ժ˶��ؿ�����
     * @return �˶��ؿ�����
     */
    public String getLimitLoadPerson(){
        return Str.rightTrim(LimitLoadPerson);
    }

    /**
     * �������Ժ˶���������ǧ�ˣ�
     * @param LimitLoad �˶���������ǧ�ˣ�
     */
    public void setLimitLoad(String LimitLoad){
        this.LimitLoad = Str.rightTrim(LimitLoad);
    }

    /**
     * ��ȡ���Ժ˶���������ǧ�ˣ�
     * @return �˶���������ǧ�ˣ�
     */
    public String getLimitLoad(){
        return Str.rightTrim(LimitLoad);
    }

    /**
     * ��������������������
     * @param Displacement ������������
     */
    public void setDisplacement(String Displacement){
        this.Displacement = Str.rightTrim(Displacement);
    }

    /**
     * ��ȡ��������������������
     * @return ����������������
     */
    public String getDisplacement(){
        return Str.rightTrim(Displacement);
    }

    /**
     * �����������쳧����
     * @param MadeFactory ���쳧����
     */
    public void setMadeFactory(String MadeFactory){
        this.MadeFactory = Str.rightTrim(MadeFactory);
    }

    /**
     * ��ȡ�������쳧����
     * @return ���쳧����
     */
    public String getMadeFactory(){
        return Str.rightTrim(MadeFactory);
    }

    /**
     * �������Գ����ͺ�
     * @param VehicleModel �����ͺ�
     */
    public void setVehicleModel(String VehicleModel){
        this.VehicleModel = Str.rightTrim(VehicleModel);
    }

    /**
     * ��ȡ���Գ����ͺ�
     * @return �����ͺ�
     */
    public String getVehicleModel(){
        return Str.rightTrim(VehicleModel);
    }

    /**
     * ����������������
     * @param ProducerType ��������
     */
    public void setProducerType(String ProducerType){
        this.ProducerType = Str.rightTrim(ProducerType);
    }

    /**
     * ��ȡ������������
     * @return ��������
     */
    public String getProducerType(){
        return Str.rightTrim(ProducerType);
    }

    /**
     * ������������Ʒ��
     * @param VehicleBrand1 ����Ʒ��
     */
    public void setVehicleBrand1(String VehicleBrand1){
        this.VehicleBrand1 = Str.rightTrim(VehicleBrand1);
    }

    /**
     * ��ȡ��������Ʒ��
     * @return ����Ʒ��
     */
    public String getVehicleBrand1(){
        return Str.rightTrim(VehicleBrand1);
    }

    /**
     * ��������Ӣ��Ʒ��
     * @param VehicleBrand2 Ӣ��Ʒ��
     */
    public void setVehicleBrand2(String VehicleBrand2){
        this.VehicleBrand2 = Str.rightTrim(VehicleBrand2);
    }

    /**
     * ��ȡ����Ӣ��Ʒ��
     * @return Ӣ��Ʒ��
     */
    public String getVehicleBrand2(){
        return Str.rightTrim(VehicleBrand2);
    }

    /**
     * ��������׼ǣ����������ǧ�ˣ�
     * @param Haulage ׼ǣ����������ǧ�ˣ�
     */
    public void setHaulage(String Haulage){
        this.Haulage = Str.rightTrim(Haulage);
    }

    /**
     * ��ȡ����׼ǣ����������ǧ�ˣ�
     * @return ׼ǣ����������ǧ�ˣ�
     */
    public String getHaulage(){
        return Str.rightTrim(Haulage);
    }

    /**
     * �������Գ�����ɫ
     * @param VehicleColour ������ɫ
     */
    public void setVehicleColour(String VehicleColour){
        this.VehicleColour = Str.rightTrim(VehicleColour);
    }

    /**
     * ��ȡ���Գ�����ɫ
     * @return ������ɫ
     */
    public String getVehicleColour(){
        return Str.rightTrim(VehicleColour);
    }

    /**
     * �����������ۼ۸�
     * @param SalePrice ���ۼ۸�
     */
    public void setSalePrice(String SalePrice){
        this.SalePrice = Str.rightTrim(SalePrice);
    }

    /**
     * ��ȡ�������ۼ۸�
     * @return ���ۼ۸�
     */
    public String getSalePrice(){
        return Str.rightTrim(SalePrice);
    }

    /**
     * �������Խ�����Դ����
     * @param PmFuelType ������Դ����
     */
    public void setPmFuelType(String PmFuelType){
        this.PmFuelType = Str.rightTrim(PmFuelType);
    }

    /**
     * ��ȡ���Խ�����Դ����
     * @return ������Դ����
     */
    public String getPmFuelType(){
        return Str.rightTrim(PmFuelType);
    }

    /**
     * �������Ի�����״̬����
     * @param Status ������״̬����
     */
    public void setStatus(String Status){
        this.Status = Str.rightTrim(Status);
    }

    /**
     * ��ȡ���Ի�����״̬����
     * @return ������״̬����
     */
    public String getStatus(){
        return Str.rightTrim(Status);
    }

    /**
     * ��������ƽ̨��������
     * @param VehicleCategory ƽ̨��������
     */
    public void setVehicleCategory(String VehicleCategory){
        this.VehicleCategory = Str.rightTrim(VehicleCategory);
    }

    /**
     * ��ȡ����ƽ̨��������
     * @return ƽ̨��������
     */
    public String getVehicleCategory(){
        return Str.rightTrim(VehicleCategory);
    }

    /**
     * �������Բ���ʱ��
     * @param InsertDate ����ʱ��
     */
    public void setInsertDate(String InsertDate){
        ChgDate chgDate = new ChgDate(); 
        System.out.println("InsertDate " + InsertDate);
        InsertDate = chgDate.toFormat(InsertDate);
        System.out.println("InsertDate " + InsertDate);
        if  (InsertDate == null || InsertDate.length() == 0 ) 
        {
          this.InsertDate = "";
        }else
        {
         this.InsertDate = InsertDate.trim().substring(0,18);
        }
    }

    /**
     * ��ȡ���Բ���ʱ��
     * @return ����ʱ��
     */
    public String getInsertDate(){
        return Str.rightTrim(InsertDate);
    }

    /**
     * �������Ա�־λ
     * @param DemandNo ��־λ
     */
    public void setDemandNo(String DemandNo){
        this.DemandNo = Str.rightTrim(DemandNo);
    }

    /**
     * ��ȡ���Ա�־λ
     * @return ��־λ
     */
    public String getDemandNo(){
        return Str.rightTrim(DemandNo);
    }
    /**
     * �������Ա�־λ
     * @param Flag ��־λ
     */
    public void setFlag(String Flag){
        this.Flag = Str.rightTrim(Flag);
    }

    /**
     * ��ȡ���Ա�־λ
     * @return ��־λ
     */
    public String getFlag(){
        return Str.rightTrim(Flag);
    }
    
    /**
     * �������Գ�������
     * @param OwnerName ��������
     */
    public void setOwnerName(String OwnerName){
        this.OwnerName = Str.rightTrim(OwnerName);
    }

    /**
     * ��ȡ���Գ�������
     * @return ��������
     */
    public String getOwnerName(){
        return Str.rightTrim(OwnerName);
    }

    public String getUpdateDate() {
		return Str.rightTrim(UpdateDate);
	}

	public void setUpdateDate(String UpdateDate) {
		ChgDate chgDate = new ChgDate();
		UpdateDate = chgDate.toFormat(UpdateDate);
		if  (UpdateDate == null || UpdateDate.length() == 0 )
		{
			this.UpdateDate = "";
		}else{
			this.UpdateDate = UpdateDate.trim().substring(0,10);
		}
	}

	public String getValidFlag() {
		return ValidFlag;
	}

	public void setValidFlag(String validFlag) {
		ValidFlag = validFlag;
	}

	public String getOperateSite() {
		return OperateSite;
	}

	public void setOperateSite(String operateSite) {
		OperateSite = operateSite;
	}
	
	public String getPmVehicleID() {
		return PmVehicleID;
	}

	public void setPmVehicleID(String PmVehicleID) {
		this.PmVehicleID = PmVehicleID;
	}

    
    /**
     * @param iSchema ����CIInsurePmVehicleSchema
     */       
    public void setSchema(CIInsurePmVehicleSchema iSchema)
    {
    	this.PmVehicleID = iSchema.getPmVehicleID();
        this.CarMark = iSchema.getCarMark();
        this.VehicleType = iSchema.getVehicleType();
        this.RackNo = iSchema.getRackNo();
        this.EngineNo = iSchema.getEngineNo();
        this.VehicleStyle = iSchema.getVehicleStyle();
        this.PmUseType = iSchema.getPmUseType();
        this.IneffectualDate = iSchema.getIneffectualDate();
        this.RejectDate = iSchema.getRejectDate();
        this.VehicleRegisterDate = iSchema.getVehicleRegisterDate();
        this.LastCheckDate = iSchema.getLastCheckDate();
        this.TransferDate = iSchema.getTransferDate();
        this.WholeWeight = iSchema.getWholeWeight();
        this.LimitLoadPerson = iSchema.getLimitLoadPerson();
        this.LimitLoad = iSchema.getLimitLoad();
        this.Displacement = iSchema.getDisplacement();
        this.MadeFactory = iSchema.getMadeFactory();
        this.VehicleModel = iSchema.getVehicleModel();
        this.ProducerType = iSchema.getProducerType();
        this.VehicleBrand1 = iSchema.getVehicleBrand1();
        this.VehicleBrand2 = iSchema.getVehicleBrand2();
        this.Haulage = iSchema.getHaulage();
        this.VehicleColour = iSchema.getVehicleColour();
        this.SalePrice = iSchema.getSalePrice();
        this.PmFuelType = iSchema.getPmFuelType();
        this.Status = iSchema.getStatus();
        this.VehicleCategory = iSchema.getVehicleCategory();
        this.InsertDate = iSchema.getInsertDate();
        this.UpdateDate = iSchema.getUpdateDate();
        this.ValidFlag = iSchema.getValidFlag();
        this.OperateSite = iSchema.getOperateSite();
        this.Flag = iSchema.getFlag();
        this.DemandNo = iSchema.getDemandNo();
        this.OwnerName = iSchema.getOwnerName();
    }
}

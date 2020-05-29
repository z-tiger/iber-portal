/*
 * 
 */
package com.iber.portal.vo.charging;

import java.util.Date;

/**
 * 充电桩财务报表VO
 * @author ouxx
 * @since 2017-4-10 上午10:10:47
 * 
 */
public class ChargingFinanceReportVo {
    private Integer id;//无特别意义 解决刷新跳转问题
	//订单开始时间
	private Date createTime;
	//车牌
	private String lpn;
	
	// 城市名称
	private String cityName;
	// 会员ID
	private Integer memberId;
	// 网点名
	private String parkName;
	// 充电桩编码
	private String equipmentCode;
	// 桩类型
	private Integer equipmentType;
	// 充电电量
	private Double chargingAmount;
	//充电费（元/度）
	private Double chargingPrice;
	//服务费（元/度）
	private Double servicePrice;
	//支付金额
	private Double payMoney;
	private Integer invoiceStatus;
	private Double invoiceMoney;
	//本年合计、总合计
	private Double chargingAmountThisYearSum;// 充电量本年合计
	private Double payMoneyThisYearSum;// 支付金额本年合计
	private Double invoiceMoneyYearSum;// 支付金额本年合计
	private Double chargingAmountSum;// 充电量总合计
	private Double payMoneySum;// 支付金额总合计
	private Double invoiceMoneySum;// 支付金额总合计

    private Date payTime;
    private String payStatus;
    private Double orderMoney;

    public Double getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(Double orderMoney) {
        this.orderMoney = orderMoney;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }



    public Double getInvoiceMoneyYearSum() {
		return invoiceMoneyYearSum;
	}
	public void setInvoiceMoneyYearSum(Double invoiceMoneyYearSum) {
		this.invoiceMoneyYearSum = invoiceMoneyYearSum;
	}
	public Double getInvoiceMoneySum() {
		return invoiceMoneySum;
	}
	public void setInvoiceMoneySum(Double invoiceMoneySum) {
		this.invoiceMoneySum = invoiceMoneySum;
	}
	public Integer getInvoiceStatus() {
		return invoiceStatus;
	}
	public void setInvoiceStatus(Integer invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}
	public Double getInvoiceMoney() {
		return invoiceMoney;
	}
	public void setInvoiceMoney(Double invoiceMoney) {
		this.invoiceMoney = invoiceMoney;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getLpn() {
		return lpn;
	}
	public void setLpn(String lpn) {
		this.lpn = lpn;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public String getParkName() {
		return parkName;
	}
	public void setParkName(String parkName) {
		this.parkName = parkName;
	}
	public String getEquipmentCode() {
		return equipmentCode;
	}
	public void setEquipmentCode(String equipmentCode) {
		this.equipmentCode = equipmentCode;
	}
	public Integer getEquipmentType() {
		return equipmentType;
	}
	public void setEquipmentType(Integer equipmentType) {
		this.equipmentType = equipmentType;
	}
	public Double getChargingAmount() {
		return chargingAmount;
	}
	public void setChargingAmount(Double chargingAmount) {
		this.chargingAmount = chargingAmount;
	}
	public Double getChargingPrice() {
		return chargingPrice;
	}
	public void setChargingPrice(Double chargingPrice) {
		this.chargingPrice = chargingPrice;
	}
	public Double getServicePrice() {
		return servicePrice;
	}
	public void setServicePrice(Double servicePrice) {
		this.servicePrice = servicePrice;
	}
	public Double getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(Double payMoney) {
		this.payMoney = payMoney;
	}
	public Double getChargingAmountThisYearSum() {
		return chargingAmountThisYearSum;
	}
	public void setChargingAmountThisYearSum(Double chargingAmountThisYearSum) {
		this.chargingAmountThisYearSum = chargingAmountThisYearSum;
	}
	public Double getPayMoneyThisYearSum() {
		return payMoneyThisYearSum;
	}
	public void setPayMoneyThisYearSum(Double payMoneyThisYearSum) {
		this.payMoneyThisYearSum = payMoneyThisYearSum;
	}
	public Double getChargingAmountSum() {
		return chargingAmountSum;
	}
	public void setChargingAmountSum(Double chargingAmountSum) {
		this.chargingAmountSum = chargingAmountSum;
	}
	public Double getPayMoneySum() {
		return payMoneySum;
	}
	public void setPayMoneySum(Double payMoneySum) {
		this.payMoneySum = payMoneySum;
	}

    @Override
    public String toString() {
        return "ChargingFinanceReportVo{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", lpn='" + lpn + '\'' +
                ", cityName='" + cityName + '\'' +
                ", memberId=" + memberId +
                ", parkName='" + parkName + '\'' +
                ", equipmentCode='" + equipmentCode + '\'' +
                ", equipmentType=" + equipmentType +
                ", chargingAmount=" + chargingAmount +
                ", chargingPrice=" + chargingPrice +
                ", servicePrice=" + servicePrice +
                ", payMoney=" + payMoney +
                ", invoiceStatus=" + invoiceStatus +
                ", invoiceMoney=" + invoiceMoney +
                ", chargingAmountThisYearSum=" + chargingAmountThisYearSum +
                ", payMoneyThisYearSum=" + payMoneyThisYearSum +
                ", invoiceMoneyYearSum=" + invoiceMoneyYearSum +
                ", chargingAmountSum=" + chargingAmountSum +
                ", payMoneySum=" + payMoneySum +
                ", invoiceMoneySum=" + invoiceMoneySum +
                ", payTime=" + payTime +
                ", orderMoney=" + orderMoney +
                ", payStatus='" + payStatus + '\'' +
                '}';
    }
}

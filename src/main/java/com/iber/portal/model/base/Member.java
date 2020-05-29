package com.iber.portal.model.base;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public class Member {
    private Integer id;

    private String name;

    private String sex;

    private String password;

    private String phone;

    private String email;

    private String idcard;

    private String driverIdcard;

    private String status;

    private String headPhotoUrl;

    private String driverIdcardPhotoUrl;

    @JSONField(serialize=false)
    private MultipartFile driverIdcardMultipartFile;

    private String idcardPhotoUrl;

    private String fingerPrint;

    private String tboxFingerPrint;

    private String registerIp;

    private String registerCategory;

    private String accoutStatus;

    private String cityCode;
    
    private String cityName;

    private Integer enterpriseId;

    private String memberLevel;

    private String enterpriseCheckStatus;

    private String isDrive;
    
    private Date createTime;
    
    private String remark;
    
    private String enterpriseName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date driverIdcardValidityTime ; //驾驶证有效期时间
    
    private Integer driverIdcardUpdate ; //驾驶证是否可以更新

    @JSONField(serialize=false)
    private MultipartFile idcardMultipartFile; //身份证相片
    
    private  Integer latestContributedVal;//上月累计贡献值
    
    private Integer levelCode;//会员等级code

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date driverIdCardTime;
    
    private Date examineTime;
    
    private Integer contributedVal;
    
    private Integer examineId;
    
    private Date uploadTime;
    
    private String refuseReason;

    private Integer drewOnceCoupon;//同一会员是否已领取了只有一次参与机会的优惠券(1：是，0：否)
    
    private String inviter;
    
    private Integer deposit;
	private Integer refundMoney;
	private Integer requireDeposit;

	private String faceId; //人脸识别id

    private Integer channel;//渠道 1-注册审核；2-资料变更审核

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public Integer getDeposit() {
		return deposit;
	}
	public void setDeposit(Integer deposit) {
		this.deposit = deposit;
	}
	public Integer getRefundMoney() {
		return refundMoney;
	}
	public void setRefundMoney(Integer refundMoney) {
		this.refundMoney = refundMoney;
	}
	public Integer getRequireDeposit() {
		return requireDeposit;
	}
	public void setRequireDeposit(Integer requireDeposit) {
		this.requireDeposit = requireDeposit;
	}

    public String getInviter() {
		return inviter;
	}

	public void setInviter(String inviter) {
		this.inviter = inviter;
	}

	public Integer getDrewOnceCoupon() {
        return drewOnceCoupon;
    }

    public void setDrewOnceCoupon(Integer drewOnceCoupon) {
        this.drewOnceCoupon = drewOnceCoupon;
    }
    
    public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public Integer getExamineId() {
		return examineId;
	}

	public void setExamineId(Integer examineId) {
		this.examineId = examineId;
	}

	public Date getExamineTime() {
		return examineTime;
	}

	public void setExamineTime(Date examineTime) {
		this.examineTime = examineTime;
	}

	public Date getDriverIdCardTime() {
		return driverIdCardTime;
	}

	public void setDriverIdCardTime(Date driverIdCardTime) {
		this.driverIdCardTime = driverIdCardTime;
	}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard == null ? null : idcard.trim();
    }

    public String getDriverIdcard() {
        return driverIdcard;
    }

    public void setDriverIdcard(String driverIdcard) {
        this.driverIdcard = driverIdcard == null ? null : driverIdcard.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getHeadPhotoUrl() {
        return headPhotoUrl;
    }

    public void setHeadPhotoUrl(String headPhotoUrl) {
        this.headPhotoUrl = headPhotoUrl == null ? null : headPhotoUrl.trim();
    }

    public String getDriverIdcardPhotoUrl() {
        return driverIdcardPhotoUrl;
    }

    public void setDriverIdcardPhotoUrl(String driverIdcardPhotoUrl) {
        this.driverIdcardPhotoUrl = driverIdcardPhotoUrl == null ? null : driverIdcardPhotoUrl.trim();
    }

    public String getIdcardPhotoUrl() {
        return idcardPhotoUrl;
    }

    public void setIdcardPhotoUrl(String idcardPhotoUrl) {
        this.idcardPhotoUrl = idcardPhotoUrl == null ? null : idcardPhotoUrl.trim();
    }

    public String getFingerPrint() {
        return fingerPrint;
    }

    public void setFingerPrint(String fingerPrint) {
        this.fingerPrint = fingerPrint == null ? null : fingerPrint.trim();
    }

    public String getRegisterIp() {
        return registerIp;
    }

    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp == null ? null : registerIp.trim();
    }

    public String getRegisterCategory() {
        return registerCategory;
    }

    public void setRegisterCategory(String registerCategory) {
        this.registerCategory = registerCategory == null ? null : registerCategory.trim();
    }

    public String getAccoutStatus() {
        return accoutStatus;
    }

    public void setAccoutStatus(String accoutStatus) {
        this.accoutStatus = accoutStatus == null ? null : accoutStatus.trim();
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }
    
    public String getCityName() {
		return cityName;
	}
    
    public void setCityName(String cityName) {
		this.cityName = cityName;
	}

    public Integer getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Integer enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(String memberLevel) {
        this.memberLevel = memberLevel == null ? null : memberLevel.trim();
    }

    public String getEnterpriseCheckStatus() {
        return enterpriseCheckStatus;
    }

    public void setEnterpriseCheckStatus(String enterpriseCheckStatus) {
        this.enterpriseCheckStatus = enterpriseCheckStatus == null ? null : enterpriseCheckStatus.trim();
    }

    public String getIsDrive() {
        return isDrive;
    }

    public void setIsDrive(String isDrive) {
        this.isDrive = isDrive == null ? null : isDrive.trim();
    }
    
    public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
    
    public Date getCreateTime() {
		return createTime;
	}
    
    public String getRemark() {
		return remark;
	}
    
    public void setRemark(String remark) {
		this.remark = remark;
	}
    
    public void setDriverIdcardMultipartFile(MultipartFile driverIdcardMultipartFile) {
		this.driverIdcardMultipartFile = driverIdcardMultipartFile;
	}
    
    public MultipartFile getDriverIdcardMultipartFile() {
		return driverIdcardMultipartFile;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public Date getDriverIdcardValidityTime() {
		return driverIdcardValidityTime;
	}

	public void setDriverIdcardValidityTime(Date driverIdcardValidityTime) {
		this.driverIdcardValidityTime = driverIdcardValidityTime;
	}

	public Integer getDriverIdcardUpdate() {
		return driverIdcardUpdate;
	}

	public void setDriverIdcardUpdate(Integer driverIdcardUpdate) {
		this.driverIdcardUpdate = driverIdcardUpdate;
	}

	public MultipartFile getIdcardMultipartFile() {
		return idcardMultipartFile;
	}

	public void setIdcardMultipartFile(MultipartFile idcardMultipartFile) {
		this.idcardMultipartFile = idcardMultipartFile;
	}

	public Integer getLatestContributedVal() {
		return latestContributedVal;
	}

	public void setLatestContributedVal( Integer latestContributedVal) {
		this.latestContributedVal = latestContributedVal;
	}

	public Integer getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(Integer levelCode) {
		this.levelCode = levelCode;
	}

	public Integer getContributedVal() {
		return contributedVal;
	}

	public void setContributedVal(Integer contributedVal) {
		this.contributedVal = contributedVal;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

    public String getTboxFingerPrint() {
        return tboxFingerPrint;
    }

    public void setTboxFingerPrint(String tboxFingerPrint) {
        this.tboxFingerPrint = tboxFingerPrint;
    }
}
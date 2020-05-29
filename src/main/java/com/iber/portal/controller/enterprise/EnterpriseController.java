package com.iber.portal.controller.enterprise;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.MD5;
import com.iber.portal.common.OSSClientAPI;
import com.iber.portal.common.Pager;
import com.iber.portal.controller.MainController;
import com.iber.portal.model.base.Member;
import com.iber.portal.model.base.MemberCard;
import com.iber.portal.model.enterprise.Enterprise;
import com.iber.portal.model.enterprise.EnterpriseChargeLog;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.base.MemberCardService;
import com.iber.portal.service.base.MemberChargeLogService;
import com.iber.portal.service.base.MemberService;
import com.iber.portal.service.enterprise.EnterpriseChargeLogService;
import com.iber.portal.service.enterprise.EnterpriseExceesApplyService;
import com.iber.portal.service.enterprise.EnterpriseService;
import com.iber.portal.service.enterprise.EnterpriseUseCarApplyService;
import com.iber.portal.service.sys.SysParamService;
import com.iber.portal.vo.enterprise.EnterpriseExcessApplyVo;
import com.iber.portal.vo.enterprise.EnterpriseMemberVo;
import com.iber.portal.vo.enterprise.EnterpriseUseCarApplyVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Controller
public class EnterpriseController extends MainController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private SysParamService sysParamService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberCardService memberCardService;

    @Autowired
    private MemberChargeLogService memberChargeLogService;
    @Autowired
    private EnterpriseChargeLogService enterpriseChargeLogService;


    @Autowired
    private EnterpriseUseCarApplyService enterpriseUseCarApplyService;

    @Autowired
    private EnterpriseExceesApplyService enterpriseExceesApplyService;

    /**
     * @describe 企业页面
     */
    @SystemServiceLog(description = "企业页面")
    @RequestMapping(value = "/enterprise_page", method = {RequestMethod.GET})
    public String enterprise(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        log.info("企业页面");
        return "enterprise/enterprise";
    }
    @SystemServiceLog(description = "获取所有企业列表")
    @RequestMapping(value = "/getAllEnterpriseList", method = {RequestMethod.GET})
    @ResponseBody
    public List<Map<String,Object>> getAllEnterpriseList(){
        return enterpriseService.enterpriseList();
    }

    /**
     * @describe 企业列表
     */
    @SystemServiceLog(description = "企业列表")
    @RequestMapping(value = "/enterprise_list", method = {RequestMethod.GET, RequestMethod.POST})
    public String enterpriseList(int page, int rows, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        log.info("企业列表");
        response.setContentType("text/html;charset=utf-8");
        Map<String, Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
        int from = pageInfo.get("first_page");
        int to = pageInfo.get("page_size");
        String enterpriseName = request.getParameter("enterprise_name");
        String city_code = request.getParameter("city_code");
        String parentId = request.getParameter("parentId");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(city_code) && !StringUtils.equals(city_code, "00")) {
            paramMap.put("cityCode", city_code);
        } else {
            //城市过滤
            SysUser user = (SysUser) request.getSession().getAttribute("user");
            String cityCode = null;
            if (!"00".equals(user.getCityCode())) {
                cityCode = user.getCityCode();
            }
            paramMap.put("cityCode", cityCode);
        }
        paramMap.put("parentId", parentId);
        paramMap.put("from", from);
        paramMap.put("to", to);
        paramMap.put("enterpriseName", enterpriseName);
        Pager<Enterprise> pager = enterpriseService.getAll(paramMap);
        response.getWriter().print(Data2Jsp.Json2Jsp(pager));
        return null;
    }
    /**
     * @describe 企业列表
     */
    @SystemServiceLog(description = "分公司列表")
    @RequestMapping(value = "/get_branch_company", method = {RequestMethod.GET, RequestMethod.POST})
    public String findBranchCompanyList(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("分公司列表");
        response.setContentType("text/html;charset=utf-8");
        Map<String, Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
        int from = pageInfo.get("first_page");
        int to = pageInfo.get("page_size");
        String enterpriseName = request.getParameter("enterprise_name");
        String parentId = request.getParameter("parentId");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("from", from);
        paramMap.put("to", to);
        paramMap.put("enterpriseName", enterpriseName);
        paramMap.put("parentId", parentId);
        Pager<Enterprise> pager = enterpriseService.getBranchCompanyPage(paramMap);
        response.getWriter().print(Data2Jsp.Json2Jsp(pager));
        return null;
    }


    /**
     * @describe 保存更新企业
     */
    @SystemServiceLog(description = "保存更新企业")
    @RequestMapping(value = "/enterprise_saveOrUpdate", method = {RequestMethod.GET, RequestMethod.POST})
    public String enterpriseSaveOrUpdate(HttpServletRequest request, HttpServletResponse response, MultipartFile license_file_url)
            throws Exception {
        log.info("保存更新企业");
        String returnValue = "success";
        String filename = license_file_url.getOriginalFilename();
        InputStream is = license_file_url.getInputStream();
        String newFileName = UUID.randomUUID().toString() + "." + filename.substring(filename.lastIndexOf(".") + 1);
        //文件上传到CDN
        String endpoint = sysParamService.selectByKey("endpoint").getValue();
        String accessKeyId = sysParamService.selectByKey("accessKeyId").getValue();
        String accessKeySecret = sysParamService.selectByKey("accessKeySecret").getValue();
        String bucketName = sysParamService.selectByKey("bucketName").getValue();
        String dns = sysParamService.selectByKey("dns").getValue();
        OSSClientAPI oss = new OSSClientAPI(endpoint, accessKeyId, accessKeySecret, bucketName, dns);
        String licenseFileUrl = oss.putObject(newFileName, is, "businessLicense/");

        response.setContentType("text/html;charset=utf-8");
        String id = request.getParameter("id");
        String cityCode = request.getParameter("city_code");
//		String level = request.getParameter("level");
        String enterpriseName = request.getParameter("enterprise_name");
        String admin = request.getParameter("admin");
        String adminMobile = request.getParameter("admin_mobile");
        String legalPerson = request.getParameter("legal_person");
        String enterpriseTel = request.getParameter("enterprise_tel");
        String address = request.getParameter("address");
        String businessLicense = request.getParameter("business_license");

        String depositLimit = request.getParameter("depositLimit");
        String overdraftMoney = request.getParameter("overdraftMoney");
        String overdraftNum = request.getParameter("overdraftNum");


        SysUser user = getUser(request);
        if (id != null && !id.equals("")) {
            Enterprise currObj = enterpriseService.selectByPrimaryKey(Integer.parseInt(id));
            if (currObj != null) {
                currObj.setAddress(address);
                currObj.setAdmin(admin);
                currObj.setAdminMobile(adminMobile);
                currObj.setBusinessLicense(businessLicense);
                currObj.setCityCode(cityCode);
                currObj.setEnterpriseName(enterpriseName);
                currObj.setEnterpriseTel(enterpriseTel);
                currObj.setLegalPerson(legalPerson);
//				currObj.setLevel(level);
                currObj.setLicenseFileUrl(licenseFileUrl);
                currObj.setUpdateTime(new Date());
                currObj.setUpdateUser(user.getName());


                currObj.setDepositLimit((int) (Double.valueOf(depositLimit) * 100));
                Integer money =(int) (Double.parseDouble(overdraftMoney) * 100);
                Integer num =Integer.parseInt(overdraftNum);


                if (currObj.getCanOverdraftMoney() + (money - currObj.getOverdraftMoney()) < 0) {
                    currObj.setCanOverdraftMoney(0);
                }else {
                    currObj.setCanOverdraftMoney(currObj.getCanOverdraftMoney() + (money - currObj.getOverdraftMoney()));
                }

                if (currObj.getCanOverdraftNum() + (num - currObj.getOverdraftNum()) < 0) {
                    currObj.setCanOverdraftNum(0);

                }else {
                    currObj.setCanOverdraftNum(currObj.getCanOverdraftNum() + (num - currObj.getOverdraftNum()));
                }

                currObj.setOverdraftMoney(money);
                currObj.setOverdraftNum(num);

                int r = enterpriseService.updateByPrimaryKeySelective(currObj);
                if (r <= 0) {
                    returnValue = "fail";
                }
            }
        } else {

            Enterprise obj = new Enterprise();
            obj.setAddress(address);
            obj.setAdmin(admin);
            obj.setAdminMobile(adminMobile);
            obj.setBusinessLicense(businessLicense);
            obj.setCityCode(cityCode);
            obj.setCreateTime(new Date());
            obj.setCreateUser(user.getName());
            obj.setEnterpriseName(enterpriseName);
            obj.setEnterpriseTel(enterpriseTel);
            obj.setLegalPerson(legalPerson);
//			obj.setLevel(level);
            obj.setLicenseFileUrl(licenseFileUrl);

            obj.setDepositLimit((int) (Double.valueOf(depositLimit) * 100));
            obj.setCanOverdraftMoney((int) (Double.parseDouble(overdraftMoney) * 100));
            obj.setOverdraftMoney((int) (Double.parseDouble(overdraftMoney) * 100));
            obj.setOverdraftNum(Integer.parseInt(overdraftNum));
            obj.setCanOverdraftNum(Integer.parseInt(overdraftNum));
//            int r = enterpriseService.saveEnterpriseInfo(obj);
            int r = enterpriseService.saveEnterprise(obj);
           /* Enterprise objbranch= obj;
            objbranch.setpId(obj.getId());
            objbranch.setId(obj.getId()+1);
            enterpriseService.saveEnterprise(objbranch);*/

            if (r <= 0) {
                returnValue = "fail";
            }

        }
        response.getWriter().print(returnValue);
        return null;
    }

    /**
     * @describe 删除企业
     */
    @SystemServiceLog(description = "删除企业")
    @RequestMapping(value = "/enterprise_del", method = {RequestMethod.GET, RequestMethod.POST})
    public String enterpriseDel(String id, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        log.info("删除企业");
        String returnValue = "success";
        response.setContentType("text/html;charset=utf-8");
        if (id != null && !id.equals("")) {
            Enterprise currObj = enterpriseService.selectByPrimaryKey(Integer.parseInt(id));
            currObj.setIsDelete("1");
            int r = enterpriseService.updateByPrimaryKeySelective(currObj);
            if (r <= 0) {
                returnValue = "fail";
            }
        }
        response.getWriter().print(returnValue);
        return null;
    }

    /**
     * @describe 根据企业ID查询本企业会员列表
     */
    @SystemServiceLog(description = "根据企业ID查询本企业会员列表")
    @RequestMapping(value = "/enterprise_member_list_by_id", method = {RequestMethod.GET, RequestMethod.POST})
    public String enterpriseMemberListById(int page, int rows, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        log.info("根据企业ID查询本企业会员列表");
        response.setContentType("text/html;charset=utf-8");
        Map<String, Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
        int from = pageInfo.get("first_page");
        int to = pageInfo.get("page_size");
        String enterpriseId = request.getParameter("enterpriseId");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("from", from);
        paramMap.put("to", to);
        paramMap.put("enterpriseId", enterpriseId);
        Pager<EnterpriseMemberVo> pager = enterpriseService.getEnterpriseMemberListById(paramMap);
        response.getWriter().print(Data2Jsp.Json2Jsp(pager));
        return null;
    }

    /**
     * @describe 设置企业会员额度
     */
    @SystemServiceLog(description = "设置企业会员额度")
    @RequestMapping(value = "/enterprise_member_quota", method = {RequestMethod.GET, RequestMethod.POST})
    public String enterpriseMemberQuota(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        log.info("设置企业会员额度");
        String returnValue = "success";
        response.setContentType("text/html;charset=utf-8");
        String memberId = request.getParameter("member_id");
        String quota = request.getParameter("quota");
        String quotaMonth = request.getParameter("quota_month");
        if (memberId != null && !memberId.equals("")) {
            MemberCard currObj = memberCardService.selectByMemberId(Integer.parseInt(memberId));
            currObj.setQuota(Integer.parseInt(quota) * 100);
            currObj.setQuotaMonth(Integer.parseInt(quotaMonth));
            currObj.setQuotaDateTime(new Date());
            currObj.setQuotaUseMoney(0);//清除已用额度
            int r = memberCardService.updateByPrimaryKeySelective(currObj);
            if (r <= 0) {
                returnValue = "fail";
            }
        }
        response.getWriter().print(returnValue);
        return null;
    }

    /**
     * @describe 设置管理员
     */
    @SystemServiceLog(description = "设置管理员")
    @RequestMapping(value = "/enterprise_set_manager", method = {RequestMethod.GET, RequestMethod.POST})
    public String enterpriseSetManager(String id, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        log.info("设置管理员");
        String returnValue = "success";
        response.setContentType("text/html;charset=utf-8");
        if (id != null && !id.equals("")) {
            Member currObj = memberService.selectByPrimaryKey(Integer.parseInt(id));
            currObj.setMemberLevel("manager");
            int r = memberService.updateByPrimaryKeySelective(currObj);
            if (r <= 0) {
                returnValue = "fail";
            }
        }
        response.getWriter().print(returnValue);
        return null;
    }

    /**
     * @describe 取消管理员
     */
    @SystemServiceLog(description = "取消管理员")
    @RequestMapping(value = "/enterprise_cancel_manager", method = {RequestMethod.GET, RequestMethod.POST})
    public String enterpriseCancelManager(String id, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        log.info("取消管理员");
        String returnValue = "success";
        response.setContentType("text/html;charset=utf-8");
        if (id != null && !id.equals("")) {
            Member currObj = memberService.selectByPrimaryKey(Integer.parseInt(id));
            currObj.setMemberLevel("person");
            int r = memberService.updateByPrimaryKeySelective(currObj);
            if (r <= 0) {
                returnValue = "fail";
            }
        }
        response.getWriter().print(returnValue);
        return null;
    }

    /**
     * @describe 企业账户充值
     */
    @SystemServiceLog(description = "企业账户充值")
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/enterprise_money_add", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> enterpriseMoneyAdd(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html;charset=utf-8");
        log.info("企业账户充值");
//		String returnValue = "success";
        String enterpriseId = request.getParameter("enterpriseId");
        int chargeMoney= 0;
        chargeMoney = (int)(Double.parseDouble( request.getParameter("chargeMoney"))*100);
        String remark = request.getParameter("remark");
        SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
        Integer userId = sysUser.getId();

        if (org.apache.commons.lang3.StringUtils.isBlank(enterpriseId) || chargeMoney == 0) {
            return fail("参数不正确，请刷新后重试");
        }

        Enterprise enterprise = enterpriseService.selectByPrimaryKey(Integer.parseInt(enterpriseId));
        Integer accountMoney = enterprise.getAccountMoney();
        //如果是退款的话判断帐户余额是否充足
        if (accountMoney + chargeMoney < 0) {
            return fail("帐户余额不足，帐户余额为:" + accountMoney.doubleValue() / 100);
        }
        enterprise.setAccountMoney(accountMoney + chargeMoney);
        //如果chargeMoney是负数 表示退款 钱加在退款总额里面，否则钱加在充值总额里面
        if (chargeMoney < 0) {
            enterprise.setTotalRefundSum(-chargeMoney + enterprise.getTotalRefundSum());
        } else {
            enterprise.setTotalRecharge(chargeMoney + enterprise.getTotalRecharge());
        }
        //若余额大于0，且是冲值 则更新‘可透支金额’值为‘透支金额’的值，‘可透支次数’值为‘透支次数’的值；
        if (accountMoney + chargeMoney > 0 && chargeMoney > 0) {
            enterprise.setCanOverdraftNum(enterprise.getOverdraftNum());
            enterprise.setCanOverdraftMoney(enterprise.getOverdraftMoney());

        }

        int num = enterpriseService.updateByPrimaryKeySelective(enterprise);

        if (num > 0) {
            EnterpriseChargeLog enterpriseChargeLog = new EnterpriseChargeLog();
            enterpriseChargeLog.setChargeMoney(chargeMoney);
            enterpriseChargeLog.setUserId(userId);
            enterpriseChargeLog.setRemark(remark);
            enterpriseChargeLog.setEnterpriseId(Integer.parseInt(enterpriseId));
            if (chargeMoney < 0) {
                enterpriseChargeLog.setChargeType(3);//3表示余额退款
            } else {
                enterpriseChargeLog.setChargeType(1);//1 表示余额充值
            }


            enterpriseChargeLogService.insertSelective(enterpriseChargeLog);
            return success();

        }
        return fail("充值失败！");


		/*if (moneyAddId!=null && !moneyAddId.equals("")) {

			Member m = memberService.selectEnterpriseAccount(Integer.parseInt(moneyAddId));
			MemberCard mc = memberCardService.selectByMemberId(m.getId());
			mc.setMoney(mc.getMoney()+ymoney_m);
			if(ymoney_m>0){
				mc.setTotalChargeMoney(mc.getTotalChargeMoney()+ymoney_m);
			}else{
				mc.setTotalRefundMoney(mc.getTotalRefundMoney()-ymoney_m);
			}
			
			int r = memberCardService.updateByPrimaryKeySelective(mc);
			
			MemberChargeLog memberChargeLog = new MemberChargeLog();
			memberChargeLog.setMemberId(m.getId());
			memberChargeLog.setMoney(ymoney_m);
			memberChargeLog.setCreateTime(new Date());
			memberChargeLog.setTradeStatus("0");
			memberChargeLog.setTradeNo(trade_no_m);
			memberChargeLog.setChargeCategory("B");
			int r2 = memberChargeLogService.insertSelective(memberChargeLog);
			
			//插入消费日志信息
			MoneyLog moneyLog = new MoneyLog();
			moneyLog.setCreateTime(new Date());
			moneyLog.setMemberId(mc.getMemberId());
			if(ymoney_m>0){
				moneyLog.setMoney(ymoney_m);
				moneyLog.setType("+");
			}else{
				moneyLog.setMoney(-ymoney_m);
				moneyLog.setType("-");
			}
			moneyLog.setCategory("balance");
			moneyLog.setObjId(trade_no_m);
			int r3 = enterpriseService.insertMoneyLog(moneyLog);
			if( r<= 0 || r2<= 0 || r3<= 0){
				returnValue = "fail";
			}
		}*/
//		response.getWriter().print(returnValue);
    }

    /**
     * @describe 企业押金充值
     */
    @SystemServiceLog(description = "企业押金充值")
    @RequestMapping(value = "/enterprise_deposit_add", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> enterpriseDepositAdd(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        log.info("企业押金充值");
        response.setContentType("text/html;charset=utf-8");
        String depositAddId = request.getParameter("deposit_add_id");
        String money_d = request.getParameter("money_d");
        String trade_no_d = request.getParameter("trade_no_d");
        SysUser user = (SysUser) request.getSession().getAttribute("user");

        Integer userId = user.getId();

        if (StringUtils.isBlank(depositAddId) || StringUtils.isBlank(money_d)) {
            return fail("参数错误,请刷新重试！");
        }
        int ymoney_d = (int)(Double.parseDouble(money_d) * 100);
        Enterprise enterprise = enterpriseService.selectByPrimaryKey(Integer.parseInt(depositAddId));

//        Integer depositLimit = enterprise.getDepositLimit();
        Integer depositLimit= enterprise.getTotalDeposit();

        if (ymoney_d + depositLimit < 0) {
            return fail("押金余额不足，您的押金余额为:" + depositLimit.doubleValue() / 100);
        }
        if (ymoney_d < 0) {
            enterprise.setTotalRefundSum(-ymoney_d + enterprise.getTotalRefundSum());
        }

        enterprise.setTotalDeposit(ymoney_d + enterprise.getTotalDeposit());
//        enterprise.setDepositLimit(ymoney_d + depositLimit);


        int num = enterpriseService.updateByPrimaryKeySelective(enterprise);

        if (num > 0) {
            EnterpriseChargeLog enterpriseChargeLog = new EnterpriseChargeLog();
            enterpriseChargeLog.setChargeMoney(ymoney_d);
            enterpriseChargeLog.setUserId(userId);
            enterpriseChargeLog.setRemark(trade_no_d);
            enterpriseChargeLog.setEnterpriseId(Integer.parseInt(depositAddId));
            if (ymoney_d < 0) {
                enterpriseChargeLog.setChargeType(2);//2表示押金退款
            } else {
                enterpriseChargeLog.setChargeType(0);//0 表示押金充值
            }

            enterpriseChargeLogService.insertSelective(enterpriseChargeLog);
            return success();

        }
        return fail("充值失败！");

		/*if (depositAddId!=null && !depositAddId.equals("")) {
			Member m = memberService.selectEnterpriseAccount(Integer.parseInt(depositAddId));
			MemberCard mc = memberCardService.selectByMemberId(m.getId());
			mc.setDeposit(mc.getDeposit()+ymoney_d);
			if(ymoney_d>0){
				mc.setTotalChargeMoney(mc.getTotalChargeMoney()+ymoney_d);
			}else{
				mc.setTotalRefundMoney(mc.getTotalRefundMoney()-ymoney_d);
			}
			int r = memberCardService.updateByPrimaryKeySelective(mc);

			MemberChargeLog memberChargeLog = new MemberChargeLog();
			memberChargeLog.setMemberId(m.getId());
			memberChargeLog.setMoney(ymoney_d);
			memberChargeLog.setCreateTime(new Date());
			memberChargeLog.setTradeStatus("0");
			memberChargeLog.setTradeNo(trade_no_d);
			memberChargeLog.setChargeCategory("D");
			int r2 = memberChargeLogService.insertSelective(memberChargeLog);

			//插入消费日志信息
			MoneyLog moneyLog = new MoneyLog();
			moneyLog.setCreateTime(new Date());
			moneyLog.setMemberId(mc.getMemberId());
			if(ymoney_d>0){
				moneyLog.setMoney(ymoney_d);
				moneyLog.setType("+");
			}else{
				moneyLog.setMoney(-ymoney_d);
				moneyLog.setType("-");
			}
			moneyLog.setCategory("deposit");
			moneyLog.setObjId(trade_no_d);
			int r3 = enterpriseService.insertMoneyLog(moneyLog);
			if( r<= 0 || r2<= 0 || r3<= 0){
				returnValue = "fail";
			}
		}
		response.getWriter().print(returnValue);
		return null;*/
    }

    /**
     * @describe 企业会员添加
     */
    @SystemServiceLog(description = "企业会员添加")
    @RequestMapping(value = "/enterprise_member_add", method = {RequestMethod.GET, RequestMethod.POST})
    public String enterpriseMemberAdd(HttpServletRequest request, HttpServletResponse response, MultipartFile m_driver_idcard_url)
            throws Exception {
        log.info("企业会员添加");

        String filename = m_driver_idcard_url.getOriginalFilename();
        InputStream is = m_driver_idcard_url.getInputStream();
        String newFileName = UUID.randomUUID().toString() + "." + filename.substring(filename.lastIndexOf(".") + 1);
        //文件上传到CDN
        String endpoint = sysParamService.selectByKey("endpoint").getValue();
        String accessKeyId = sysParamService.selectByKey("accessKeyId").getValue();
        String accessKeySecret = sysParamService.selectByKey("accessKeySecret").getValue();
        String bucketName = sysParamService.selectByKey("bucketName").getValue();
        String dns = sysParamService.selectByKey("dns").getValue();
        OSSClientAPI oss = new OSSClientAPI(endpoint, accessKeyId, accessKeySecret, bucketName, dns);
        String driverIdcardPhotoUrl = oss.putObject(newFileName, is, "driverIdcard/");

        String returnValue = "success";
        response.setContentType("text/html;charset=utf-8");
        String id = request.getParameter("m_id");
        String phone = request.getParameter("m_phone");
        String name = request.getParameter("m_name");
        String sex = request.getParameter("m_sex");
        String email = request.getParameter("m_email");
        String idcard = request.getParameter("m_idcard");
        String driverIdcard = request.getParameter("m_driver_idcard");
        if (id != null && !id.equals("")) {
            Enterprise e = enterpriseService.selectByPrimaryKey(Integer.parseInt(id));
            Member m = new Member();
            m.setCityCode(e.getCityCode());
            m.setPassword(MD5.toMD5(phone.substring(5)));
            m.setStatus("experience");
            m.setMemberLevel("person");
            m.setRegisterCategory("platform");
            m.setAccoutStatus("0");
            m.setIsDrive("0");
            m.setCreateTime(new Date());
            m.setEnterpriseId(Integer.parseInt(id));
            m.setPhone(phone);
            m.setName(name);
            m.setSex(sex);
            m.setEmail(email);
            m.setIdcard(idcard);
            m.setDriverIdcard(driverIdcard);
            m.setDriverIdcardPhotoUrl(driverIdcardPhotoUrl);
            int r = enterpriseService.saveEnterpriseMember(e, m);
            if (r <= 0) {
                returnValue = "fail";
            }
        }
        response.getWriter().print(returnValue);
        return null;
    }

    /**
     * @describe 检查企业名称唯一性
     */
    @SystemServiceLog(description = "检查企业名称唯一性")
    @RequestMapping(value = "/enterprise_name_check", method = {RequestMethod.GET, RequestMethod.POST})
    public String enterpriseNameCheck(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        log.info("检查企业名称唯一性");
        String returnValue = "success";
        String enterpriseName = request.getParameter("enterpriseName");
        response.setContentType("text/html;charset=utf-8");
        Enterprise enterprise = enterpriseService.selectByEnterpriseName(enterpriseName);
        if (null != enterprise) {
            returnValue = "enterpriseNameExist";
        }
        response.getWriter().print(returnValue);
        return null;
    }

    /**
     * @describe 检查手机号码唯一性
     */
    @SystemServiceLog(description = "检查手机号码唯一性")
    @RequestMapping(value = "/enterprise_check_phone", method = {RequestMethod.GET, RequestMethod.POST})
    public String enterpriseCheckPhone(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        log.info("检查手机号码唯一性");
        String returnValue = "success";
        String m_phone = request.getParameter("m_phone");
        response.setContentType("text/html;charset=utf-8");
        if (m_phone != null && !m_phone.equals("")) {
            Member m = memberService.selectDetailByPhone(m_phone);
            if (null != m) {
                returnValue = "phoneExist";
            }
        }
        response.getWriter().print(returnValue);
        return null;
    }

    /**
     * @describe 检查手机号码是否是企业管理员
     */
    @SystemServiceLog(description = "检查手机号码是否是企业管理员")
    @RequestMapping(value = "/enterprise_check_manager", method = {RequestMethod.GET, RequestMethod.POST})
    public String enterpriseCheckManager(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        log.info("检查手机号码是否是企业管理员");
        String returnValue = "success";
        String m_phone = request.getParameter("m_phone");
        response.setContentType("text/html;charset=utf-8");
        if (m_phone != null && !m_phone.equals("")) {
            Member m = memberService.selectDetailByPhone(m_phone);
            if (null != m) {
                if ("manager".equals(m.getMemberLevel())) {
                    returnValue = "managerExist";
                } else {
                    returnValue = "phoneExist";
                }
            }
        }
        response.getWriter().print(returnValue);
        return null;
    }


    /**
     * @describe 检查身份证号码唯一性
     */
    @SystemServiceLog(description = "检查身份证号码唯一性")
    @RequestMapping(value = "/enterprise_check_idcard", method = {RequestMethod.GET, RequestMethod.POST})
    public String enterpriseCheckIdcard(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        log.info("检查身份证号码唯一性");
        String returnValue = "success";
        response.setContentType("text/html;charset=utf-8");
        String m_idcard = request.getParameter("m_idcard");
        if (m_idcard != null && !m_idcard.equals("")) {
            Member m = memberService.selectDetailByIdcard(m_idcard);
            if (null != m) {
                returnValue = "idcardExist";
            }
        }
        response.getWriter().print(returnValue);
        return null;
    }

    /**
     * @describe 检查email唯一性
     */
    @SystemServiceLog(description = "检查email唯一性")
    @RequestMapping(value = "/enterprise_check_email", method = {RequestMethod.GET, RequestMethod.POST})
    public String enterpriseCheckEmail(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        log.info("检查email唯一性");
        String returnValue = "success";
        String m_email = request.getParameter("m_email");
        response.setContentType("text/html;charset=utf-8");
        if (m_email != null && !m_email.equals("")) {
            Member m = memberService.selectDetailByEmail(m_email);
            if (null != m) {
                returnValue = "emailExist";
            }
        }
        response.getWriter().print(returnValue);
        return null;
    }

    /**
     * @describe 企业会员批量导入
     */
    @SystemServiceLog(description = "企业会员批量导入")
    @RequestMapping(value = "/enterprise_improt_member", method = {RequestMethod.GET, RequestMethod.POST})
    public String enterpriseImportMember(HttpServletRequest request, HttpServletResponse response, MultipartFile improt_url)
            throws Exception {
        log.info("企业会员批量导入");
        int returnValue = 0;
        response.setContentType("text/html;charset=utf-8");
        String enterpriseId = request.getParameter("import_member_id");
        InputStream is = improt_url.getInputStream();
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        // Read the Sheet
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // Read the Row
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                    Map<String, String> map = new HashMap<String, String>();
                    HSSFCell name = hssfRow.getCell(0);
                    HSSFCell sex = hssfRow.getCell(1);
                    HSSFCell phone = hssfRow.getCell(2);
                    HSSFCell idcard = hssfRow.getCell(3);
                    map.put("name", String.valueOf(name));
                    map.put("sex", String.valueOf(sex));
                    map.put("phone", String.valueOf(phone));
                    map.put("idcard", String.valueOf(idcard));
                    list.add(map);
                }
            }
        }

        if (enterpriseId != null && !enterpriseId.equals("")) {
            Enterprise e = enterpriseService.selectByPrimaryKey(Integer.parseInt(enterpriseId));
            if (null != e) {
                returnValue = enterpriseService.saveImpEnterpriseMember(list, e);
            }
        }
        response.getWriter().print(list.size() + "," + returnValue);
        return null;
    }

    /**
     * 根据memberid 移除企业会员
     */
    @SystemServiceLog(description = "移除企业会员")
    @RequestMapping(value = "/enterprise_cancel_member", method = {RequestMethod.GET, RequestMethod.POST})
    public String removeMember(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        log.info("移除企业会员");
        response.setContentType("text/html;charset=utf-8");
        String id = request.getParameter("id");
        Member currObj = memberService.selectByPrimaryKey(Integer.parseInt(id));
        if (!currObj.getMemberLevel().equals("person")) {
            currObj.setMemberLevel("person");
            memberService.updateByPrimaryKeySelective(currObj);
        }
        int num = memberService.removeEnterpriseId(Integer.valueOf(id));

        if (num > 0) {
            response.getWriter().print("success");
        }
        return null;
    }

    @SystemServiceLog(description = "用车申请记录页面")
    @RequestMapping("/enterprise_car_apply_record")
    public String toEnterpriseUseCarApply() {
        return "enterprise/enterpriseCarApplyRecord";
    }

    @SystemServiceLog(description = "用车超额申请记录页面")
    @RequestMapping("/enterprise_over_apply_record")
    public String toEnterpriseOverApply() {
        return "enterprise/enterpriseOverApplyRecord";
    }

    @SystemServiceLog(description = "用车申请记录")
    @RequestMapping(value = "/enterpriseCarApplyRecordList.do",  method = {RequestMethod.GET, RequestMethod.POST})
    public void getEnterpriseCarApplyRecordList(int rows ,int page,HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String enterpriseName = request.getParameter("enterpriseName");
        String memberName = request.getParameter("memberName");
        String phone = request.getParameter("phone");
        String orderType = request.getParameter("orderType");
        String checkStatus = request.getParameter("checkStatus");

        Map<String, Integer> pageInfor = Data2Jsp.getFristAndPageSize(page, rows);
        Integer pageSize = pageInfor.get("page_size");
        Integer pageNum = pageInfor.get("first_page");

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("pageSize", pageSize);
        paramMap.put("pageNumber", pageNum);
        paramMap.put("enterpriseName", enterpriseName);
        paramMap.put("memberName", memberName);
        paramMap.put("orderType", orderType);
        paramMap.put("checkStatus",checkStatus);
        paramMap.put("phone", phone);

        Pager<EnterpriseUseCarApplyVo> pager = enterpriseUseCarApplyService.getEnterpriseUseCarApplyPage(paramMap);
        response.getWriter().print(Data2Jsp.Json2Jsp(pager));

    }
    @SystemServiceLog(description = "超额申请记录")
    @RequestMapping(value = "/enterpriseOverApplyRecordList.do", method = {RequestMethod.GET, RequestMethod.POST})
    public void  getEnterpriseOverApplyRecordList(int rows ,int page,HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String enterpriseName = request.getParameter("enterpriseName");
        String memberName = request.getParameter("memberName");
        String phone = request.getParameter("phone");
        String orderType = request.getParameter("orderType");
        String checkStatus = request.getParameter("checkStatus");

        Map<String, Integer> pageInfor = Data2Jsp.getFristAndPageSize(page, rows);
        Integer pageSize = pageInfor.get("page_size");
        Integer pageNum = pageInfor.get("first_page");

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("pageSize", pageSize);
        paramMap.put("pageNumber", pageNum);
        paramMap.put("enterpriseName", enterpriseName);
        paramMap.put("memberName", memberName);
        paramMap.put("orderType", orderType);
        paramMap.put("checkStatus",checkStatus);
        paramMap.put("phone", phone);

        Pager<EnterpriseExcessApplyVo> pager = enterpriseExceesApplyService.getEnterpriseExcessApplyPage(paramMap);
        response.getWriter().print(Data2Jsp.Json2Jsp(pager));

    }



}

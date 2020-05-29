package com.iber.portal.controller;

import com.alibaba.fastjson.JSONObject;
import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.*;
import com.iber.portal.dao.base.MemberMapper;
import com.iber.portal.dao.base.SystemMsgLogMapper;
import com.iber.portal.dao.base.SystemMsgMapper;
import com.iber.portal.dao.coupon.CouponItemMapper;
import com.iber.portal.dao.coupon.CouponMapper;
import com.iber.portal.dao.member.MemberFreezeLogMapper;
import com.iber.portal.enums.MemberBehaviorNameEnum;
import com.iber.portal.getui.PushClient;
import com.iber.portal.getui.PushCommonBean;
import com.iber.portal.model.base.*;
import com.iber.portal.model.car.CarRun;
import com.iber.portal.model.charging.ChargingOrderInfo;
import com.iber.portal.model.charging.ChargingPile;
import com.iber.portal.model.coupon.Coupon;
import com.iber.portal.model.coupon.CouponItem;
import com.iber.portal.model.dayRent.DayRentOrderExtend;
import com.iber.portal.model.member.MemberContributeItemVo;
import com.iber.portal.model.member.MemberContributedDetail;
import com.iber.portal.model.member.MemberDeleteFinger;
import com.iber.portal.model.sys.SysDic;
import com.iber.portal.model.sys.SysParam;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.model.timeShare.TimeSharePay;
import com.iber.portal.service.base.*;
import com.iber.portal.service.car.CarRunService;
import com.iber.portal.service.charging.ChargingOrderInfoService;
import com.iber.portal.service.dayRent.DayRentOrderExtendService;
import com.iber.portal.service.deposit.DepositService;
import com.iber.portal.service.member.MemberContributedDetailService;
import com.iber.portal.service.member.MemberDeleteFingerService;
import com.iber.portal.service.sys.SysDicService;
import com.iber.portal.service.sys.SysParamService;
import com.iber.portal.service.timeShare.TimeSharePayService;
import com.iber.portal.util.BatchSetterUtil;
import com.iber.portal.util.BuildCouponNo;
import com.iber.portal.util.DateTime;
import com.iber.portal.util.SendSMS;
import com.iber.portal.util.sign.Base64;
import com.iber.portal.vo.base.MemberCardVo;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Controller
public class MemberController extends MainController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberCardService memberCardService;

    @Autowired
    private SysParamService sysParamService;

    @Autowired
    private CarRunService carRunService;

    @Autowired
    private SystemMsgLogMapper systemMsgLogMapper;

    @Autowired
    private SystemMsgMapper systemMsgMapper;

    @Autowired
    private SystemMsgLogService systemMsgLogService;

    @Autowired
    private MemberChargeLogService memberChargeLogService;

    @Autowired
    private TimeSharePayService timeSharePayService;

    @Autowired
    private ChargingOrderInfoService chargingOrderInfoService;

    @Autowired
    private DayRentOrderExtendService dayRentOrderExtendService;

    @Autowired
    private SysDicService sysDicService;

    @Autowired
    private DepositService depositService;

    @Autowired
    private MemberDeleteFingerService memberDeleteFingerService;

    @Autowired
    private MemberRefundLogService memberRefundLogService;

    @Autowired
    private MemberContributedDetailService memberContributedDetailService;

    @Autowired
    private CouponItemMapper couponItemMapper;

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private MemberFreezeLogMapper memberFreezeLogMapper;

    /**
     * 会员信息页面
     */
    @SystemServiceLog(description = "会员信息页面")
    @RequestMapping(value = "/member_page", method = {RequestMethod.GET})
    public String member(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "member/member";
    }

    /**
     * 企业会员信息页面
     */
    @SystemServiceLog(description = "企业会员信息页面")
    @RequestMapping(value = "/enterpriseMember", method = {RequestMethod.GET})
    public String enterpriseMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "enterprise/enterpriseMember";
    }

    @SystemServiceLog(description = "企业会员管理")
    @RequestMapping(value = "/enterpriseMember_list", method = {RequestMethod.GET, RequestMethod.POST})
    public String enterpriseMemberList(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        Map<String, Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("offset", pageInfo.get("first_page"));
        map.put("rows", pageInfo.get("page_size"));
        String city_code = request.getParameter("cityCode");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String enterpriseName = request.getParameter("enterpriseName");
        if (StringUtils.isNotBlank(city_code) && !StringUtils.equals("00", city_code)) {
            map.put("cityCode", city_code);
        } else {
            //城市过滤
            SysUser user = (SysUser) request.getSession().getAttribute("user");
            String cityCode = null;
            if (!user.getCityCode().equals("00")) {
                cityCode = user.getCityCode();
            }
            map.put("cityCode", cityCode);
        }
        map.put("name", name);
//		map.put("cityCode", cityCode);
        map.put("phone", phone);
        map.put("enterpriseName", enterpriseName);

        Pager<Member> pager = memberService.selectEnterpriseMemberPage(map);

//        List<Member> data = memberService.selectEnterpriseAll(map);
//        int totalRecords = memberService.selectEnterpriseAllRecords(map);
//        String json = Data2Jsp.Json2Jsp(data, totalRecords);
//        response.getWriter().print(json);
        response.getWriter().print(Data2Jsp.Json2Jsp(pager));
        return null;
    }

    @SystemServiceLog(description = "会员管理")
    @RequestMapping(value = "/member_list", method = {RequestMethod.GET, RequestMethod.POST})
    public String memberList(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        Map<String, Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("offset", pageInfo.get("first_page"));
        map.put("rows", pageInfo.get("page_size"));

        //String cityCode = request.getParameter("cityCode");
        String name = request.getParameter("name");
        String status = request.getParameter("status");
        String phone = request.getParameter("phone");
        String bt = request.getParameter("bt");
        String et = request.getParameter("et");
		//默认不展示全部
        if(StringUtils.isBlank(name) && StringUtils.isBlank(phone)){
			response.getWriter().print("");
			return null;
		}
        SysUser user = (SysUser) request.getSession().getAttribute("user");
        if (user.getCityCode().equals("00")) {
            if (!StringUtils.isBlank(request.getParameter("cityCode"))) {
                if (request.getParameter("cityCode").equals("00")) {
                    map.put("cityCode", null);
                } else {
                    map.put("cityCode", request.getParameter("cityCode"));
                }
            }
        } else {
            map.put("cityCode", user.getCityCode());
        }
        map.put("name", name);
        map.put("status", status);
        //map.put("cityCode", cityCode);
        map.put("phone", phone);
        map.put("bt", bt);
        map.put("et", et);
        String driverIdcardValidityTime = request.getParameter("driverIdcardValidityTime");
        if (driverIdcardValidityTime != null && driverIdcardValidityTime.equals("driverIdcardValidityTime")) { //进行到期排序
            map.put("driverIdcardValidityTime", "driverIdcardValidityTime");
        }
        List<Member> data = memberService.selectAll(map);
        int totalRecords = memberService.selectAllRecords(map);
        String json = Data2Jsp.Json2Jsp(data, totalRecords);
        response.getWriter().print(json);
        return null;
    }
    
    @SystemServiceLog(description="数据导出")
	@RequestMapping(value = "/export_member_list", method = { RequestMethod.GET , RequestMethod.POST })
	public List<ChargingPile> exportExecl(String cityCode,String name,String status,String phone,String bt,String et,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		String fileName = "MemberReport" ;
		//列名充电桩编码	
		String columnNames [] = {"所属城市", "姓名",  "性别", "手机号码", "驾驶证有效期", "驾驶证初次领证日期", 
				"身份证号","驾驶证号码","资料上传时间","驾驶证照片","指纹","三代指纹","状态","注册IP","注册终端","资金状态","会员类型","会员等级","会员贡献值","注册时间"};
		
		String keys[] = { "cityName", "name","sex", 
				"phone", "driverIdcardValidityTime", "driverIdCardTime", "idcard",
				"driverIdcard","uploadTime","driverIdcardPhotoUrl","fingerPrint","tboxFingerPrint","status","registerIp","registerCategory",
				"accoutStatus","enterpriseId","levelCode","contributedVal","createTime"};
		Map<String, Object> map = new HashMap<String, Object>();
		SysUser user = (SysUser) request.getSession().getAttribute("user");
        if (user.getCityCode().equals("00")) {
            if (!StringUtils.isBlank(cityCode)) {
                if (cityCode.equals("00")) {
                    map.put("cityCode", null);
                } else {
                    map.put("cityCode", cityCode);
                }
            }
        } else {
            map.put("cityCode", user.getCityCode());
        }
        map.put("name", name);
        map.put("status", status);
        map.put("phone", phone);
        map.put("bt", bt);
        map.put("et", et);
        map.put("offset", null);
        map.put("rows", null);
        List<Member> datas = memberService.selectAll(map);
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> sheetNameMap = new HashMap<String, Object>();
		sheetNameMap.put("sheetName", "会员信息数据报表");
		list.add(sheetNameMap);
		list.addAll(createData2(datas));
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try{
			ExcelUtil.createWorkBook(list, keys, columnNames).write(os);
		}catch (Exception e) {
			e.printStackTrace();
		}
		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
		ServletOutputStream out = response.getOutputStream();
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			// Simple read/write loop.
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			throw e;
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}	
		
		return null;
		
	}
	
	private List<Map<String, Object>> createData2(
			List<Member> data) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;// 避免重复创建对象
		String keys[] = { "cityName", "name","sex", 
				"phone", "driverIdcardValidityTime", "driverIdCardTime", "idcard",
				"driverIdcard","uploadTime","driverIdcardPhotoUrl","fingerPrint","tboxFingerPrint","status","registerIp","registerCategory",
				"accoutStatus","enterpriseId","levelCode","contributedVal","createTime"};
		for (Member member : data) {
			map = new HashMap<String, Object>();
			map.put(keys[0], member.getCityName());
			map.put(keys[1], member.getName());
			map.put(keys[2], member.getSex());
			map.put(keys[3], member.getPhone());
			map.put(keys[4], member.getDriverIdcardValidityTime()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(member.getDriverIdcardValidityTime()):"");
			map.put(keys[5], member.getDriverIdCardTime()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(member.getDriverIdCardTime()):"");
			map.put(keys[6], member.getIdcard());
			map.put(keys[7], member.getDriverIdcard()); 
			map.put(keys[8], member.getUploadTime()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(member.getUploadTime()):"");
			if(member.getDriverIdcardPhotoUrl()!=null){
				map.put(keys[9], "已上传");
			}else{
				map.put(keys[9], "未上传");
			}
			if(member.getFingerPrint() != null){
				map.put(keys[10], "已录入");
			}else{
				map.put(keys[10], "未录入");
			}
			if(member.getTboxFingerPrint() != null && !"".equals(member.getTboxFingerPrint())){
				map.put(keys[11], "已录入");
			}else{
				map.put(keys[11], "未录入");
			}
			if(member.getStatus() != null ){
				if(member.getStatus().equals("ready")) map.put(keys[12], "就绪" );
				if(member.getStatus().equals("experience")){
					if(member.getRefuseReason() !=null && !member.getRefuseReason().equals("")){
						map.put(keys[12], "体验"+member.getRefuseReason() );
					}else{
						map.put(keys[12], "体验" );
					}
				} 
				if(member.getStatus().equals("ordered")) map.put(keys[12], "预约" );
				if(member.getStatus().equals("useCar")) map.put(keys[12], "用车" );
				if(member.getStatus().equals("planReturn")) map.put(keys[12], "计划还车" );
				if(member.getStatus().equals("waitQueue")) map.put(keys[12], "排队等待" );
				if(member.getStatus().equals("wait")) map.put(keys[12], "等待用车" );
				if(member.getStatus().equals("return")) map.put(keys[12], "还车" );
			}else{
				map.put(keys[12], member.getStatus());
			}
			map.put(keys[13], member.getRegisterIp());
			if(member.getRegisterCategory() != null && !"".equals(member.getRegisterCategory())){
				if(member.getRegisterCategory().equals("platform")){ map.put(keys[14], "平台注册");}
				else if(member.getRegisterCategory().equals("member")) {map.put(keys[14], "安卓手机终端");}
				else if(member.getRegisterCategory().equals("weixin")){ map.put(keys[14], "微信");}
				else if(member.getRegisterCategory().equals("ios")) {map.put(keys[14], "IOS手机终端");}
				else if(member.getRegisterCategory().equals("weibo")) {map.put(keys[14], "微博");}
				else if(member.getRegisterCategory().equals("qq")) {map.put(keys[14], "QQ");}else{
				map.put(keys[14], "其他" );
				}
			}else{
				map.put(keys[14], "" );
			}
			if(member.getAccoutStatus() != null ){
				if(member.getAccoutStatus().equals("0")) map.put(keys[15], "正常");
				if(member.getAccoutStatus().equals("1")){
					if(member.getRefundMoney() != null && member.getRequireDeposit() != null && member.getDeposit() != null){
						Integer d1 = member.getRefundMoney();
						Integer d2 = member.getRequireDeposit();
						Integer d3 = member.getDeposit();
						if((d1+d2)>d3){
							map.put(keys[15], "冻结");
						}else{
							map.put(keys[15], "正常");
						}
					}
				} 
				if(member.getAccoutStatus().equals("5")) map.put(keys[15], "冻结");
			}
			Integer enterpriseId = member.getEnterpriseId();
			if(enterpriseId!=null){
				if(enterpriseId == 0){
					map.put(keys[16], "个人会员");
				}else{
					map.put(keys[16], "政企会员");
				}
			}else{
				map.put(keys[16], "个人会员");
			}
			if(member.getLevelCode() != null ){
				switch (member.getLevelCode()) {
				case 0:
					map.put(keys[17], "黑名单");
					break;
				case 1:
					map.put(keys[17], "一星会员");
					break;
				case 2:
					map.put(keys[17], "二星会员");
					break;
				case 3:
					map.put(keys[17], "三星会员");
					break;
				case 4:
					map.put(keys[17], "四星会员");
					break;
				case 5:
					map.put(keys[17], "五星会员");
					break;
				default:
					break;
				}
			}else{
				map.put(keys[17], member.getLevelCode());
			}
			map.put(keys[18], member.getContributedVal());
			map.put(keys[19], member.getCreateTime()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(member.getCreateTime()):"");
			list.add(map);
		}
		return list;
	}
    @SystemServiceLog(description = "会员手机号或身份证号码是否有重复")
    @RequestMapping(value = "/isExists", method = {RequestMethod.GET, RequestMethod.POST})
    public String isExists(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        Integer id = Integer.valueOf(request.getParameter("id"));
        Integer type = Integer.valueOf(request.getParameter("type"));//1表示电话，2表示身份证号码
        String value = request.getParameter("value");
        int count = memberService.queryExists(id, type, value);
        response.getWriter().print((0 != count ? true : false));
        return null;
    }
    @SystemServiceLog(description = "删除人脸信息")
    @RequestMapping(value = "member_del_face" , method = { RequestMethod.GET , RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> delFace(int id,HttpServletRequest request) {
        HttpSession session = request.getSession();
        String baiduToken = (String) session.getAttribute("BAIDU_ACCESS_TOKEN");
        if (StringUtils.isBlank(baiduToken)) {
            baiduToken= BaiduTokenUtils.getToken();
            session.setAttribute("BAIDU_ACCESS_TOKEN",baiduToken);
        }

        Member member = memberService.selectByPrimaryKey(id);

        if (StringUtils.isBlank(member.getFaceId())) {
            return fail("用户没有录入人脸信息！");
        }
        String uid = MD5.MD5toUpper32(member.getName(), "utf-8");

        String uid2 = Base64.encode(member.getName().getBytes());
        System.out.println("uid>>>" + uid);
        System.out.println("uid2>>>"+uid2);
        System.out.println("phone>>>:" + member.getPhone());
        //第一个版是把name加摘要做uid或base64加密,第二个版本是把电话号码做uid
        if (!memberService.deleteFace(id, baiduToken,uid)&&!memberService.deleteFace(id,baiduToken,member.getPhone())&&
                !memberService.deleteFace(id,baiduToken,uid2)) {
            return fail("用户人脸识别信息删除失败,请刷新后再试！");
        }
        /**保存到指纹删除记录表中*/
        SysUser user = (SysUser) request.getSession().getAttribute("user");
        MemberDeleteFinger record = new MemberDeleteFinger();
        record.setMemberId(id);
        record.setCreateId(user.getId());
        record.setDeleteType(2);//2代表人脸删除
        if(!StringUtils.isBlank(request.getParameter("cityCode"))){
            record.setCityCode(request.getParameter("cityCode"));
        }
        record.setCreateTime(new Date());
        if(!StringUtils.isBlank(request.getParameter("reason"))){
            record.setReason(request.getParameter("reason"));
        }
        memberDeleteFingerService.insertSelective(record);
        return success();

    }

    /**删除用户指纹信息*/
	@SystemServiceLog(description="删除用户指纹信息")
	@RequestMapping(value = "/member_del_finger", method = { RequestMethod.GET , RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> delFinger(int id, HttpServletRequest request)throws Exception{
		//在删除指纹成功后，给车载端推送信息
		String idcard = request.getParameter("idcard");
		String clearAll = request.getParameter("clearAll");

		List<CarRun> carRunList = carRunService.queryCarRunByMemberId(id);
		if(CollectionUtils.isEmpty(carRunList)){
			return fail("会员没有用车！");
		}
		for(CarRun carRun : carRunList){
			final String orderId = carRun.getOrderId();
			final String lpn = carRun.getLpn();
			final String imei = carRun.getCid();
			if (StringUtils.isNotBlank(orderId)){
				// 有订单
				if ("true".equals(clearAll)){
					// 清除所有指纹
					memberService.deleteFingerPrint(id,lpn,idcard);
					memberMapper.deleteTboxFingerPrint(id);
				}else {
					// 看tbox版本
					if (StringUtils.isNotBlank(imei)){
						// 三代
						memberService.deleteTboxFingerPrint(id,lpn);
					}else {
						memberService.deleteFingerPrint(id,lpn,idcard);
					}

				}
				/**保存到指纹删除记录表中*/
				SysUser user = (SysUser) request.getSession().getAttribute("user");
				MemberDeleteFinger record = new MemberDeleteFinger();
				record.setMemberId(id);
				record.setCreateId(user.getId());
				if(!StringUtils.isBlank(request.getParameter("cityCode"))){
					record.setCityCode(request.getParameter("cityCode"));
				}
				record.setCreateTime(new Date());
				if(!StringUtils.isBlank(request.getParameter("reason"))){
					record.setReason(request.getParameter("reason"));
				}
				memberDeleteFingerService.insertSelective(record);
				return success();
			}
		}
		return fail("会员没有用车！");
	}
	
	/**会员重置密码*/
	@SystemServiceLog(description="会员重置密码")
	@RequestMapping(value = "/member_rest_password", method = { RequestMethod.GET , RequestMethod.POST })
	public String restPassword(int id, HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		memberService.restPassword(id);
		response.getWriter().print("succ");
		return null;
	}


    /**
     * 添加新的会员信息
     */
	Pattern pattern = Pattern.compile("^[1][0-9]{10}$");
    @SystemServiceLog(description = "添加新的会员信息")
    @RequestMapping(value = "/member_save", method = {RequestMethod.GET, RequestMethod.POST})
    public String saveMember(Member member, HttpServletRequest request, HttpServletResponse response, MultipartFile file) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        Member oldMember = memberService.selectDetailByPhone(member.getPhone());
        String returnValue = "succ";
        if (null != oldMember) {
            returnValue = "phoneExist";
        } else {
            
            Matcher matcher = pattern.matcher(member.getPhone());
            if (!matcher.matches()) {
                returnValue = "phoneFormatErr";
            } else if (!StringUtils.isBlank(member.getIdcard())) {
                //如果身份证号码不为空的情况下，校验身份证的唯一性及其身份证号码的正确性
                if (!IdcardValidator.isValidatedAllIdcard(member.getIdcard())) {
                    returnValue = "idCardFormatErr";
                } else {
                    member.setRegisterIp(getRemortIP(request));
                    List<Member> listData = memberService.selectDetailByIdcard4List(member.getIdcard());
                    if (listData.size() > 0) {
                        returnValue = "idCardExistsErr";
                    } else {
                        if (!member.getDriverIdcardMultipartFile().isEmpty()) {
                            String driverIdcardPhotoUrl = uploadDriverIdcardMultipartFile(member, request, "dirverIdcard");
                            member.setDriverIdcardPhotoUrl(driverIdcardPhotoUrl);
                        }
                        if (!member.getIdcardMultipartFile().isEmpty()) {
                            String idcardPhotoUrl = uploadDriverIdcardMultipartFile(member, request, "idCard");
                            member.setIdcardPhotoUrl(idcardPhotoUrl);
                        }
                        int r = memberService.saveMemberInfo(member);
                        //注册加上800分
                        SysParam sysParam = sysParamService.selectByKey("http_url");
                        String url = "";
                        if (sysParam != null) url = sysParam.getValue();
                        insertMemberContributedDetailByBehavior(
                                url, "{'memberId':'" + member.getId() + "','objId':'','typeEnum':'" + MemberBehaviorNameEnum.REGISTER.getName() + "','multiplicand':'','createId':''}",
                                member.getId() + "", member.getPhone(), "insertMemberContributedDetailByBehavior");
                        if (r <= 0) {
                            returnValue = "fail";
                        }
                        //如果驾龄大于1年，加1500分
                        Calendar instance = Calendar.getInstance();
                        instance.setTime(member.getDriverIdCardTime());
                        instance.add(Calendar.YEAR, 1);
                        //判读驾龄是否大于1年 驾龄≥365，贡献值=1500；驾龄<365，贡献值=0
                        if (instance.getTime().getTime() < new Date().getTime()) {
                            sysParam = sysParamService.selectByKey("http_url");
                            if (sysParam != null) url = sysParam.getValue();
                            insertMemberContributedDetailByBehavior(
                                    url, "{'memberId':'" + member.getId() + "','objId':'','typeEnum':'" + MemberBehaviorNameEnum.DRIVING_1_YEAR.getName() + "','multiplicand':'','createId':''}",
                                    member.getId() + "", member.getPhone(), "insertMemberContributedDetailByBehavior");
                        }
                    }
                }
            } else {
                member.setRegisterIp(getRemortIP(request));
                /*if(!member.getDriverIdcardMultipartFile().isEmpty()){
                    String driverIdcardPhotoUrl = uploadDriverIdcardMultipartFile(member,request,"dirverIdcard");
					member.setDriverIdcardPhotoUrl(driverIdcardPhotoUrl);
				}
				if(!member.getIdcardMultipartFile().isEmpty()){
					String idcardPhotoUrl = uploadDriverIdcardMultipartFile(member,request,"idCard");
					member.setIdcardPhotoUrl(idcardPhotoUrl);
				}*/
                int r = memberService.saveMemberInfo(member);
                SysParam sysParam = sysParamService.selectByKey("http_url");
                String url = "";
                if (sysParam != null) url = sysParam.getValue();
                insertMemberContributedDetailByBehavior(
                        url, "{'memberId':'" + member.getId() + "','objId':'','typeEnum':'" + MemberBehaviorNameEnum.REGISTER.getName() + "','multiplicand':'','createId':''}",
                        member.getId() + "", member.getPhone(), "insertMemberContributedDetailByBehavior");
                if (r <= 0) {
                    returnValue = "fail";
                }
                //如果驾龄大于1年，加1500分
				/*Calendar instance = Calendar.getInstance();
				instance.setTime(member.getDriverIdCardTime());
				instance.add(Calendar.YEAR, 1);
				//判读驾龄是否大于1年 驾龄≥365，贡献值=1500；驾龄<365，贡献值=0
				if(instance.getTime().getTime()<new Date().getTime()){
					sysParam = sysParamService.selectByKey("http_url");
					if(sysParam != null)url = sysParam.getValue();
					insertMemberContributedDetailByBehavior(
							url,"{'memberId':'"+member.getId()+"','objId':'','typeEnum':'"+MemberBehaviorNameEnum.DRIVING_1_YEAR.getName()+"','multiplicand':'','createId':''}",
							member.getId()+"", member.getPhone(),"insertMemberContributedDetailByBehavior");
				}*/
                if (r <= 0) {
                    returnValue = "fail";
                }
            }
        }

        response.getWriter().print(returnValue);
        return null;
    }

    private String uploadDriverIdcardMultipartFile(Member member, HttpServletRequest request, String useFile) throws Exception {
        MultipartFile uploadFile = null;
        if (useFile.equals("idCard")) {
            uploadFile = member.getIdcardMultipartFile();
        } else {
            uploadFile = member.getDriverIdcardMultipartFile();
        }
        String filename = uploadFile.getOriginalFilename();
        InputStream is = uploadFile.getInputStream();
        String newFileName = UUID.randomUUID().toString() + "." + filename.substring(filename.lastIndexOf(".") + 1);
        //文件上传到CDN
        String endpoint = sysParamService.selectByKey("endpoint").getValue();
        String accessKeyId = sysParamService.selectByKey("accessKeyId").getValue();
        String accessKeySecret = sysParamService.selectByKey("accessKeySecret").getValue();
        String bucketName = sysParamService.selectByKey("bucketName").getValue();
        String dns = sysParamService.selectByKey("dns").getValue();
        OSSClientAPI oss = new OSSClientAPI(endpoint, accessKeyId, accessKeySecret, bucketName, dns);
        String fileURL = oss.putObject(newFileName, is, useFile + "/");
        return fileURL;
    }

    @SystemServiceLog(description = "修改会员信息")
    @RequestMapping(value = "/member_edit", method = {RequestMethod.GET, RequestMethod.POST})
    public String memberEdit(Member member, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");

        //校验手机号、身份证号码是否跟其他会员的重复。1表示电话，2表示身份证号码
        int phoneCount = memberService.queryExists(member.getId(), 1, member.getPhone());
        if (0 != phoneCount) {
            response.getWriter().print("手机号重复:" + member.getPhone());
            return null;
        }
        int idcardCount = memberService.queryExists(member.getId(), 2, member.getIdcard());
        if (0 != idcardCount) {
            response.getWriter().print("身份证号码重复:" + member.getIdcard());
            return null;
        }
        if (!member.getDriverIdcardMultipartFile().isEmpty()) {
            String driverIdcardPhotoUrl = uploadDriverIdcardMultipartFile(member, request, "dirverIdcard");
            member.setDriverIdcardPhotoUrl(driverIdcardPhotoUrl);
        }
        if (!member.getIdcardMultipartFile().isEmpty()) {
            String idcardPhotoUrl = uploadDriverIdcardMultipartFile(member, request, "idCard");
            member.setIdcardPhotoUrl(idcardPhotoUrl);
        }
        memberService.updateByPrimaryKeySelective(member);
        response.getWriter().print("succ");
        return null;
    }

    /**
     * 会员账号管理页面
     */
    @SystemServiceLog(description = "会员账号管理页面")
    @RequestMapping(value = "/card", method = {RequestMethod.GET})
    public String card(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "member/card";
    }

    /**
     * 会员资金信息
     */
    @SystemServiceLog(description = "会员资金信息")
    @RequestMapping(value = "/member_card_list", method = {RequestMethod.GET, RequestMethod.POST})
    public String memberCardList(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        Map<String, Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("offset", pageInfo.get("first_page"));
        map.put("rows", pageInfo.get("page_size"));
        String cityCode = request.getParameter("cityCode");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        map.put("name", name);
        map.put("cityCode", cityCode);
        map.put("phone", phone);
        //默认不展示全部
        if(StringUtils.isBlank(name) && StringUtils.isBlank(phone)){
			response.getWriter().print("");
			return null;
		}
        if (!StringUtils.isBlank(request.getParameter("debit"))) { //查询欠款会员
            map.put("debit", request.getParameter("debit"));
        }

        List<MemberCardVo> data = memberCardService.selectAll(map);
        int totalRecords = memberCardService.selectAllRecords(map);
        String json = Data2Jsp.Json2Jsp(data, totalRecords);
        response.getWriter().print(json);
        return null;
    }
    @SystemServiceLog(description="数据导出")
	@RequestMapping(value = "/export_card_list", method = { RequestMethod.GET , RequestMethod.POST })
	public List exportExecl(String name,String cityCode,String phone,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		String fileName = "CardReport" ;
		//列名充电桩编码	
		String columnNames [] = {"所属城市", "姓名",  "性别", "手机号码", "资金状态","余额（元）","押金（元）","会员应缴押金","用车额度（元）",
				"用车期限（月）","已用额度（元）","累计充值金额（元）","累计退款金额（元）","未使用优惠券总额度（元）","冻结原因","芝麻信用分","备注" };
		
		String keys[] = { "cityName", "name","sex", 
				"phone", "accoutStatus","money","deposit","requiredDeposit","quota","quotaMonth","quotaUseMoney","totalChargeMoney",
				"totalRefundMoney","totalNotUsecoupon","blockingReason","zhimaScore","remark" };
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
        map.put("cityCode", cityCode);
        map.put("phone", phone);
		map.put("offset", null);
		map.put("rows", null);
		List<MemberCardVo> datas = memberCardService.selectAll(map);
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> sheetNameMap = new HashMap<String, Object>();
		sheetNameMap.put("sheetName", "会员账号数据报表");
		list.add(sheetNameMap);
		list.addAll(createData(datas));
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try{
			ExcelUtil.createWorkBook(list, keys, columnNames).write(os);
		}catch (Exception e) {
			e.printStackTrace();
		}
		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
		ServletOutputStream out = response.getOutputStream();
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			// Simple read/write loop.
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			throw e;
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}	
		
		return null;
		
	}
	
	private List<Map<String, Object>> createData(
			List<MemberCardVo> data) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;// 避免重复创建对象
		String keys[] = { "cityName", "name","sex", 
				"phone", "accoutStatus","money","deposit","requiredDeposit","quota","quotaMonth","quotaUseMoney","totalChargeMoney",
				"totalRefundMoney","totalNotUsecoupon","blockingReason","zhimaScore","remark" };
		for (MemberCardVo card : data) {
			map = new HashMap<String, Object>();
			map.put(keys[0], card.getCityName());
			map.put(keys[1], card.getName());
			map.put(keys[2], card.getSex());
			map.put(keys[3], card.getPhone());
			if(card.getAccoutStatus() != null ){
				if(card.getAccoutStatus().equals("0")) map.put(keys[4], "正常");
				if(card.getAccoutStatus().equals("1")){
					if(card.getRefundMoney() != null && card.getRequiredDeposit() != null && card.getDeposit() != null){
						Integer d1 = card.getRefundMoney();
						Integer d2 = card.getRequiredDeposit();
						Integer d3 = card.getDeposit();
						if((d1+d2)>d3){
							map.put(keys[4], "冻结");
						}else{
							map.put(keys[4], "正常");
						}
					}
				} 
				if(card.getAccoutStatus().equals("5")) map.put(keys[4], "冻结");
			}
			map.put(keys[5], card.getMoney()!=null?new DecimalFormat("0.00").format(card.getMoney()/100.0):0.00);
			map.put(keys[6], card.getDeposit()!=null?new DecimalFormat("0.00").format(card.getDeposit()/100.0):0.00);
			map.put(keys[7], card.getRequiredDeposit()!=null?new DecimalFormat("0.00").format(card.getRequiredDeposit()/100.0):0.00);
			map.put(keys[8], card.getQuota()!=null?new DecimalFormat("0.00").format(card.getQuota()/100.0):0.00);
			map.put(keys[9], card.getQuotaMonth());
			map.put(keys[10], card.getQuotaUseMoney()!=null?new DecimalFormat("0.00").format(card.getQuotaUseMoney()/100.0):0.00);
			map.put(keys[11], card.getTotalChargeMoney()!=null?new DecimalFormat("0.00").format(card.getTotalChargeMoney()/100.0):0.00);
			map.put(keys[12], card.getTotalRefundMoney()!=null?new DecimalFormat("0.00").format(card.getTotalRefundMoney()/100.0):0.00);
			map.put(keys[13], card.getTotalNotUsecoupon()!=null?new DecimalFormat("0.00").format(card.getTotalNotUsecoupon()/100.0):0.00);
			if(StringUtils.isNotBlank(card.getAccoutStatus())){
				Integer refundMoney = card.getRefundMoney();
				Integer requiredDeposit = card.getRequiredDeposit();
				Integer deposit = card.getDeposit();
				if(card.getAccoutStatus().equals("1")){
					if(refundMoney != null && requiredDeposit != null && deposit != null){
						if(refundMoney+requiredDeposit>deposit){
							map.put(keys[14], "会员退押金中，押金不满足条件");
						}
					}
				}else if(card.getAccoutStatus().equals("5")){
					map.put(keys[14], card.getBlockingReason());
				}
			}
			map.put(keys[15], card.getZhimaScore());
			map.put(keys[16], card.getRemark());
			
			list.add(map);
		}
		return list;
	}
    @SystemServiceLog(description = "解冻或冻结资金")
    @RequestMapping(value = "/member_card_frozen_thaw", method = {RequestMethod.GET, RequestMethod.POST})
    public String memberCardFrozenThaw(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        String memberId = request.getParameter("memberId");
        if (StringUtils.isNotEmpty(memberId)){
            //查询用户是否有审核中的退款，如果有，不允许解冻
            MemberRefundLog memberRefundLog = memberRefundLogService.selectRefundLogByMemberId(Integer.parseInt(memberId));
            if (memberRefundLog != null) {
                response.getWriter().print("hadRefund");
                return null;
            }
        }

        String accoutStatus = request.getParameter("accoutStatus");
        String blockingReason = request.getParameter("blockingReason");
//        String unBlockingReason = request.getParameter("unBlockingReason");
//        String uMemberId = request.getParameter("uMemberId");
//        String uAccoutStatus = request.getParameter("uAccoutStatus");
        String memberPhone = memberService.getPhoneById(Integer.valueOf(memberId));//获取会员手机号
        if ("0".equals(accoutStatus)) { //会员资金解绑
            memberCardService.memberCardFrozenThaw(Integer.valueOf(memberId), accoutStatus);
            memberCardService.deleteBlockingReason(Integer.valueOf(memberId));
            //保存会员资金解绑日志
            Integer status = SysConstant.UN_FREEZE_CARD;
            new InsertFreezeLogUtil(memberFreezeLogMapper).insertFreezeLog(request, memberId, blockingReason,status);
            //保存到信息表中
            SystemMsgLog systemMsgLog = new SystemMsgLog();
            systemMsgLog.setMsgType("defrost_capital");
            systemMsgLog.setCreateTime(new Date());
            systemMsgLog.setMemberId(Integer.valueOf(memberId));
            systemMsgLog.setMsgTitle("解冻资金");
            systemMsgLog.setMsgContent("尊敬的会员：您的资金已解冻，欢迎您再次使用。");
            systemMsgLogService.insertSelective(systemMsgLog);
            //推送到客户端
            pushBindSuccMsg(systemMsgLog, memberPhone);
            response.getWriter().print("succ");
            return null;
        }
        int num = memberCardService.memberCardFrozenThaw(Integer.valueOf(memberId), accoutStatus);
        Integer status = SysConstant.FREEZE_CARD;
        //保存会员资金冻结日志
        new InsertFreezeLogUtil(memberFreezeLogMapper).insertFreezeLog(request, memberId, blockingReason,status);
        //当资金冻结以后 推送消息给用户客户端，且发送短信告知
        if (num > 0) {
            //将消息保存
            SystemMsgLog systemMsgLog = new SystemMsgLog();
            systemMsgLog.setMsgType("block_capital");
            systemMsgLog.setCreateTime(new Date());
            systemMsgLog.setMemberId(Integer.valueOf(memberId));
            systemMsgLog.setMsgTitle("冻结资金");
            systemMsgLog.setMsgContent("尊敬的会员：您的资金被冻结，冻结原因：" + blockingReason + "。如有疑问，请联系客服4000769755。");
            systemMsgLogService.insertSelective(systemMsgLog);
            //推送到客户端
            pushBindSuccMsg(systemMsgLog, memberPhone);
            //发送短信

            //生成token
			/*String encryptToken = SendSMS.encryptBySalt(memberPhone);

			Map<String, String> params = new HashMap<String, String>();  
			String param ="{\"telephoneNo\":\""+memberPhone+"\",\"ipAddress\":\""+getRemortIP(request)+"\",\"templateId\":\"2670\",\"contentParam\":\""+new String((blockingReason).getBytes("utf-8"),"ISO-8859-1")+"\",\"token\":\""+encryptToken+"\"}";
			params.put("msgContentJson", param); 
			HttpsClientUtil.post(sysParamService.selectByKey("send_sms_url").getValue()+"",params);*/
            SendSMS.send(memberPhone, "", 2670, blockingReason);

        }
        memberCardService.updateBlockingReason(Integer.valueOf(memberId), blockingReason);
        response.getWriter().print("succ");
        return null;
    }

    //推送消息给顾客

    private void pushBindSuccMsg(SystemMsgLog systemMsgLog, String memberPhone) {
        PushCommonBean push = new PushCommonBean(SysConstant.SERVER_PUSH_CAPITAL_BLOCK,
                "1", systemMsgLog.getMsgContent(), "");
        List<String> cidList = PushClient.queryClientId(memberPhone);
        for (String memberCid : cidList) {
            PushClient.push(memberCid, push);
        }
    }

    /**
     * 实名认证
     */
    @SystemServiceLog(description = "实名认证")
    @RequestMapping(value = "/member_examine", method = {RequestMethod.GET, RequestMethod.POST})
    public String memberExamine(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        final String idcard = request.getParameter("idcard");
        final String phone = request.getParameter("phone");
//        final String driverIdcard = request.getParameter("driverIdcard");
        final String name = request.getParameter("name");

        final PrintWriter writer = response.getWriter();
        if (StringUtils.isBlank(name)) {
            writer.print("2");
            return null;
        }

        // 一个有效的国人身份证
        if (IdcardValidator.isValidatedAllIdcard(idcard)) {
            // 判断是一个合法的手机号码，不在虚拟号段
            if (Validate.isPhoneNotVirtual(phone)) {
                // 手机实名认证
                if (Validate.checkPhone(phone, name, idcard)) {
                    writer.print("1");
                    return null;
                }
            }
            // 身份证实名认证
            if (Validate.checkIdCard(name, idcard)) {
                writer.print("1");
                return null;
            }
        }

        writer.print("2");
        writer.flush();
        return null;
    }

    /**
     * 通过身份证获取性别
     *
     * @param value
     * @return
     */
    public String execute(String value) {
        value = value.trim();
        if (value == null || (value.length() != 15 && value.length() != 18)) {
            return "";
        }
        if (value.length() == 15 || value.length() == 18) {
            String lastValue = value.substring(value.length() - 2, value.length() - 1);
            int sex;
            if (lastValue.trim().toLowerCase().equals("x") || lastValue.trim().toLowerCase().equals("e")) {
                return "男";
            } else {
                sex = Integer.parseInt(lastValue) % 2;
                return sex == 0 ? "女" : "男";
            }
        } else {
            return "";
        }
    }

    @SystemServiceLog(description = "会员资料变更审核")
    @RequestMapping(value = "/lost_phone_member_examine", method = {RequestMethod.GET, RequestMethod.POST})
    public String lostPhoneMemberExamine( Integer id,String examineName, String phone,String examineIdcard, String examineDriverIdcard, String examineDriverIdcardValidityTime, String examineDriverIdcardTime, String remark,
                                         HttpServletResponse response)  throws Exception {
        response.setContentType("text/html;charset=utf-8");
        String url = null;
        SysParam sysParam = sysParamService.selectByKey("http_url");
        if (sysParam != null) url = sysParam.getValue();
//        String url = "http://192.168.1.83:8888/services/i/e/";
        String json = null;
        Integer userId = null;
        HttpSession session = request.getSession();
        SysUser sysUser = (SysUser) session.getAttribute("user");
        if (isNotEmpty(sysUser)) {
            userId = sysUser.getId();
        }
        String refuseReason = "";//拒绝原因
        String verifiedStatus =null; //审核状态 1 通过 2不通过
        if (isEmpty(remark)) {
            verifiedStatus = "1";
        }else {
            verifiedStatus ="2";
            refuseReason = remark;
        }
        StringBuffer sb = new StringBuffer("{") ;
        sb.append("\"cId\":\"platForm\",\"memberId\":\"\",") ;
        sb.append("\"method\":\"examineLostPhone\",") ;
        sb.append("\"param\":{'lostId':'"+id+"','userId':'"+userId+"','phone':'"+phone+"','verifiedStatus':'"+
                verifiedStatus+"','refuseReason':'"+refuseReason+"'},") ;
        sb.append("\"phone\":\"\",\"type\":\"platForm\",\"version\":\"1\"") ;
        sb.append("}") ;

        if(url.indexOf("https") == 0){ //https接口
            json = HttpsClientUtil.get(url, sb.toString()) ;
        }else{
            json = HttpUtils.commonSendUrl(url, sb.toString()) ;
        }
        System.out.println("json>>>"+json);
        JSONObject jsonObject = JSONObject.parseObject(json);
        if (!"00".equals(jsonObject.getString("code"))) {
            response.getWriter().print("fail");
            return null;

        }
        if (isNotEmpty(remark)) {
            response.getWriter().print("no_change");
            return null;
        }


        Member member = memberMapper.selectDetailByPhone(phone) ;
        if (isEmpty(member)) {
            response.getWriter().print("fail");
            return null;
        }
        return memberExamineSave(member.getId(), examineName, examineIdcard, examineDriverIdcard, examineDriverIdcardValidityTime, examineDriverIdcardTime, remark, response);

    }

    @SystemServiceLog(description = "会员审核")
    @RequestMapping(value = "/member_examine_save", method = {RequestMethod.GET, RequestMethod.POST})
    public String memberExamineSave(Integer id, String examineName, String examineIdcard, String examineDriverIdcard, String examineDriverIdcardValidityTime, String examineDriverIdcardTime, String remark,
                                    HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        try {
            if (!"".equals(remark)&&remark.length()>15){
                response.getWriter().print("remark_to_long");
                return null;
            }
            Member memeber = memberService.selectByPrimaryKey(id);
            if (memeber != null && !StringUtils.equals(memeber.getStatus(), "experience")) {
                response.getWriter().print("already");
				return null;
            }
            String member_status="ready";
			if(examineIdcard.trim().length()>15){
				String birthdayString=examineIdcard.substring(6,14);
				int age=DateTime.getAge(birthdayString,"yyyyMMdd");
				if(age<18){
					if("".equals(remark.trim())){
						response.getWriter().print("age_fail");
						return null;
					}
					member_status="experience";
				}
			}
			if(!StringUtils.equals(examineIdcard,examineDriverIdcard)){
				if("".equals(remark.trim())){
					response.getWriter().print("atypism");
					return null;
				}
				member_status="experience";
			}
            final Date examineDriverIdcardValidity = DateTime.getDate(examineDriverIdcardValidityTime);
            final Date examineDriverIdcardDate = DateTime.getDate(examineDriverIdcardTime);
            memeber.setName(examineName);
            memeber.setIdcard(examineIdcard);
            memeber.setDriverIdcard(examineDriverIdcard);
            memeber.setDriverIdcardValidityTime(examineDriverIdcardValidity);
			memeber.setDriverIdcardUpdate(0); //0 不更新 1需要更新
            memeber.setDriverIdCardTime(examineDriverIdcardDate);
            memeber.setExamineTime(new Date());
            memeber.setExamineId(getUser(request).getId());
            String sex = execute(examineIdcard);
            //String sendUrl = sysParamService.selectByKey("send_sms_url").getValue();
            String ip = getRemortIP(request);
            String param = "";

//			int count=0;
//			if(StringUtils.isNotBlank(examineIdcard))
//				count=memberService.examineIdcardRecord(examineIdcard);
            //		if(count==0){
            //			List<NameValuePair> params = new ArrayList<NameValuePair>();
            Map<String, String> params = new HashMap<String, String>();
            //生成token
            String encryptToken = SendSMS.encryptBySalt(memeber.getPhone());

            PushCommonBean push = null;
            if (StringUtils.isBlank(remark)) {

                Integer count = memberService.examineIdcardRecord(examineIdcard);
                if (null != count && 0 < count) {
                    response.getWriter().print("res");
                    return null;
                }
                memberService.memberExamineSave("", id,member_status, "", examineName, examineIdcard, examineDriverIdcard, examineDriverIdcardValidity, 0, sex, examineDriverIdcardDate, new Date(), getUser(request).getId());
                //会员通过审核后，需要向会员发送短信
                param = "{\"telephoneNo\":\"" + memeber.getPhone() + "\",\"ipAddress\":\"" + ip + "\",\"templateId\":\"1674\",\"contentParam\":\"" + new String(memeber.getName().getBytes("utf-8"), "ISO-8859-1") + "\",\"token\":\"" + encryptToken + "\"}";
                SystemMsg systemMsg = systemMsgMapper.selectByPrimaryKey(3);
                if (null != systemMsg) {
                    SystemMsgLog systemMsgLog = new SystemMsgLog();
                    systemMsgLog.setMsgType("member");
                    systemMsgLog.setCreateTime(new Date());
                    systemMsgLog.setMemberId(memeber.getId());
                    systemMsgLog.setMsgTitle(systemMsg.getMsgTitle());
                    systemMsgLog.setMsgContent(systemMsg.getMsgContent());
                    systemMsgLogMapper.insertSelective(systemMsgLog);
                    push = new PushCommonBean("push_server_system_msg_log", "1", "系统通知消息", systemMsgLog);
                }
                Calendar instance = Calendar.getInstance();
                instance.setTime(examineDriverIdcardDate);
                instance.add(Calendar.YEAR, 1);
                //判读驾龄是否大于1年 驾龄≥365，贡献值=1500；驾龄<365，贡献值=0
                if (instance.getTime().getTime() < new Date().getTime()) {
                    int records = memberContributedDetailService.selectRecordsByTypeAndMemberId(MemberBehaviorNameEnum.DRIVING_1_YEAR.getName(), id + "");
                    if (records == 0) {
                        SysParam sysParam = sysParamService.selectByKey("http_url");
                        String url = "";
                        if (sysParam != null) url = sysParam.getValue();
                        insertMemberContributedDetailByBehavior(
                                url, "{'memberId':'" + id + "','objId':'','typeEnum':'" + MemberBehaviorNameEnum.DRIVING_1_YEAR.getName() + "','multiplicand':'','createId':''}",
                                id + "", memeber.getPhone(), "insertMemberContributedDetailByBehavior");
                    }
                }
                List<CouponItem> item = couponItemMapper.queryItemByCode("doneMemberInfo","00");
                if (null != item && 0 < item.size()) {
                    long nowTimeStamp = System.currentTimeMillis();
                    for (CouponItem couponItem : item) {
                        long startTimeStamp = couponItem.getStartTime().getTime();
                        long endTimeStamp = couponItem.getEndTime().getTime();
                        if (1 == couponItem.getStatus() && 0 >= startTimeStamp - nowTimeStamp && 0 <= endTimeStamp - nowTimeStamp) { //判断状态是否启用,启用就赠送优惠券
                            if (0 != couponItem.getBalance() && 0 != couponItem.getNumber()) {
                                giveCoupon(couponItem, memeber, systemMsgMapper, systemMsgLogMapper);
                            }
                        }
                    }
                }
               //判断是否有绑定邀请码 如果有则赠送相应的优惠 没有跳过
               sendInvitationCoupon(memeber); 
            } else {
                memberService.memberExamineSave(remark, id, null, remark, examineName, examineIdcard, examineDriverIdcard, examineDriverIdcardValidity, 1, sex, examineDriverIdcardDate, new Date(), getUser(request).getId());
                param = "{\"telephoneNo\":\"" + memeber.getPhone() + "\",\"ipAddress\":\"" + ip + "\",\"templateId\":\"2677\",\"contentParam\":\"" + new String((memeber.getName() + "|" + remark).getBytes("utf-8"), "ISO-8859-1") + "\",\"token\":\"" + encryptToken + "\"}";
                SystemMsg systemMsg = systemMsgMapper.selectByPrimaryKey(17);
                if (null != systemMsg) {
                    SystemMsgLog systemMsgLog = new SystemMsgLog();
                    systemMsgLog.setMsgType("member");
                    systemMsgLog.setCreateTime(new Date());
                    systemMsgLog.setMemberId(memeber.getId());
                    systemMsgLog.setMsgTitle(systemMsg.getMsgTitle());
                    systemMsgLog.setMsgContent(systemMsg.getMsgContent().replace("{0}", remark));
                    systemMsgLogMapper.insertSelective(systemMsgLog);
                    push = new PushCommonBean("push_server_system_msg_log", "1", "系统通知消息", systemMsgLog);
                }
            }

            if (push != null) {
                List<String> alias = PushClient.queryClientId(memeber.getPhone());
                if (!alias.isEmpty() && alias.size() > 0) {
                    for (String cid : alias) {
                        JsonConfig jsonConfig = new JsonConfig();
                        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
                        PushClient.push(cid, net.sf.json.JSONObject.fromObject(push, jsonConfig));
                    }
                }
            }
				/*params.put("msgContentJson", param);
				HttpsClientUtil.post(sendUrl+"",params) ;*/
            SendSMS.send(param);
            response.getWriter().print("succ");
            //		}
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().print("fail");
        }
        return null;
    }

	private void sendInvitationCoupon(Member memeber) {
		if (StringUtils.isNotBlank(memeber.getInviter())) {
			int records = couponMapper.selectRecordByItemCodeAndMemberId("beInviteRegister", memeber.getId());
			if (records <= 0) {
				List<CouponItem> item = couponItemMapper.queryValidItemByCode("beInviteRegister", null);
				if (null != item && 0 < item.size()) {
					long nowTimeStamp = System.currentTimeMillis();
					for (CouponItem couponItem : item) {
						long startTimeStamp = couponItem.getStartTime().getTime();
						long endTimeStamp = couponItem.getEndTime().getTime();
						if (1 == couponItem.getStatus() && 0 >= startTimeStamp - nowTimeStamp
								&& 0 <= endTimeStamp - nowTimeStamp) { // 判断状态是否启用,启用就赠送优惠券
							if (0 != couponItem.getBalance() && 0 != couponItem.getNumber()) {
								giveCoupon(couponItem, memeber, systemMsgMapper, systemMsgLogMapper);
							}
						}
					}
				}
				List<CouponItem> item1 = couponItemMapper.queryValidItemByCode("inviteRegister", null);
				Member inviter = memberService.selectByPrimaryKey(Integer.parseInt(memeber.getInviter()));
				if (null != item1 && 0 < item1.size()) {
					long nowTimeStamp = System.currentTimeMillis();
					for (CouponItem couponItem : item1) {
						long startTimeStamp = couponItem.getStartTime().getTime();
						long endTimeStamp = couponItem.getEndTime().getTime();
						if (1 == couponItem.getStatus() && 0 >= startTimeStamp - nowTimeStamp
								&& 0 <= endTimeStamp - nowTimeStamp) { // 判断状态是否启用,启用就赠送优惠券
							if (0 != couponItem.getBalance() && 0 != couponItem.getNumber()) {
								giveCoupon(couponItem, inviter, systemMsgMapper, systemMsgLogMapper);
							}
						}
					}
				}
			}
		}

	}

	private void giveCoupon(CouponItem couponItem, Member memeber, SystemMsgMapper systemMsgMapper2,
                            SystemMsgLogMapper systemMsgLogMapper2) {
        DecimalFormat df=new DecimalFormat("0.00");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String batchNo = sdf.format(Calendar.getInstance().getTime());
        int num = couponItem.getNumber();
        Date now = new Date();

        final List<Coupon> couponLists = new ArrayList<Coupon>();
        for (int i = 0; i < num; i++) {
            Calendar calendar = Calendar.getInstance();
            String couponNo = BuildCouponNo.generateShortUuid();
            Coupon obj = new Coupon();
            obj.setBatchNo(batchNo);
            obj.setCityCode(couponItem.getCityCode());
            obj.setCouponNo(couponNo);
            obj.setCreateTime(new Date());
            obj.setDescription(memeber.getName() + "通过" + couponItem.getItemname() + "获取" + num + "张" + df.format((float)couponItem.getBalance() / 100) + "元(折)面值优惠券，批次号:" + batchNo);
            obj.setBalance(Double.parseDouble(couponItem.getBalance() + ""));
            obj.setUseStatus("0");
            calendar.setTime(now);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 000);
            obj.setStartTime(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, couponItem.getDeadline());
            calendar.set(Calendar.HOUR, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 58);
            calendar.set(Calendar.MILLISECOND, 999);
            obj.setEndTime(calendar.getTime()); //根据配置项获取优惠券截至日期
            obj.setTitle(couponItem.getItemname());
            obj.setItemCode(couponItem.getItemcode());
            obj.setType("1"); //1 自动 0 手动
            obj.setStatus("1");
            obj.setIssueAuthority(-1);
            if (null != couponItem.getUseType() &&  couponItem.getUseType() >0) {// 1是抵扣券
                obj.setMinUseValue(couponItem.getMinUseValue());
                obj.setMaxDeductionValue(couponItem.getMaxDeductionValue());
            } else {
                obj.setMinUseValue(0);
            }
            obj.setUseType(couponItem.getUseType());
            obj.setMemberId(memeber.getId());
            couponLists.add(obj);
        }
        String sql = "INSERT INTO x_coupon(coupon_no,title,description,start_time,end_time,status,create_time,member_id," +
                "use_status,balance,batch_no,city_code,type,item_code,use_type,issue_authority,min_use_value,max_deduction_value) " +
                "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.batchUpdate(sql, new BatchSetterUtil<Coupon>(couponLists) {
            public void setValues(PreparedStatement ps, int i)
                    throws SQLException {
                Coupon coupon = couponLists.get(i);
                ps.setString(1, coupon.getCouponNo());
                ps.setString(2, coupon.getTitle());
                ps.setString(3, coupon.getDescription());
                ps.setTimestamp(4, new Timestamp(coupon.getStartTime().getTime()));
                ps.setTimestamp(5, new Timestamp(coupon.getEndTime().getTime()));
                ps.setString(6, coupon.getStatus());
                ps.setTimestamp(7, new Timestamp(coupon.getCreateTime().getTime()));
                ps.setInt(8, coupon.getMemberId());
                ps.setString(9, coupon.getUseStatus());
                ps.setDouble(10, coupon.getBalance());
                ps.setString(11, coupon.getBatchNo());
                ps.setString(12, coupon.getCityCode());
                ps.setString(13, coupon.getType());
                ps.setString(14, coupon.getItemCode());
                ps.setInt(15, coupon.getUseType());
                ps.setInt(16, coupon.getIssueAuthority());
                ps.setInt(17, coupon.getMinUseValue());
                ps.setInt(18, coupon.getMaxDeductionValue()==null?0:coupon.getMaxDeductionValue());
            }
        });
        pushMsg(couponItem, memeber, systemMsgMapper, systemMsgLogMapper);
    }

    public static void pushMsg(CouponItem item, Member member, SystemMsgMapper systemMsgMapper, SystemMsgLogMapper systemMsgLogMapper) {
        //消息推送会员绑定优惠券成功
        DecimalFormat df=new DecimalFormat("0.00");
        SystemMsg systemMsg = systemMsgMapper.selectByPrimaryKey(15);
        SystemMsgLog systemMsgLog = new SystemMsgLog();
        systemMsgLog.setMsgType("bingCoupon");
        systemMsgLog.setCreateTime(new Date());
        systemMsgLog.setMemberId(member.getId());
        systemMsgLog.setMsgTitle(systemMsg.getMsgTitle());
        systemMsgLog.setMsgContent(systemMsg.getMsgContent().replace("{0}", item.getNumber() + "").replace("{1}", df.format((float)item.getBalance() / 100)  + ""));
        systemMsgLogMapper.insertSelective(systemMsgLog);
        PushCommonBean systemPush = new PushCommonBean("push_server_system_msg_log", "1", "系统通知消息", systemMsgLog);
        List<String> cidMemberList = PushClient.queryClientId(member.getPhone());
        for (String memberCid : cidMemberList) {
            PushClient.push(memberCid,
                    JSONObject.toJSONString(systemPush));
        }
    }

    @SystemServiceLog(description = "设置企业会员")
    @RequestMapping(value = "/member_set_enterprise", method = {RequestMethod.GET, RequestMethod.POST})
    public String memberSetEnterprise(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        String memberId = request.getParameter("set_enterprise_id");
        String enterpriseId = request.getParameter("enterpriseId");
        Member m = memberService.selectByPrimaryKey(Integer.parseInt(memberId));
        m.setEnterpriseId(Integer.parseInt(enterpriseId));
        m.setMemberLevel("person");
        m.setDriverIdcardValidityTime(null);
        memberService.updateByPrimaryKeySelective(m);
        response.getWriter().print("success");
        return null;
    }

    @Autowired
    private MemberMapper memberMapper;

    @SystemServiceLog(description = "会员审核页面跳转")
    @RequestMapping(value = "/member_d_examine", method = {RequestMethod.GET})
    public String memberExaminePage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "member/examine";
    }

    @SystemServiceLog(description = "待审核列表")
    @RequestMapping(value = "/member_examine_d_list", method = {RequestMethod.GET, RequestMethod.POST})
    public String memberExamineDList(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        Map<String, Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("offset", pageInfo.get("first_page"));
        map.put("rows", pageInfo.get("page_size"));
        String cityCode = request.getParameter("cityCode");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String bt = request.getParameter("bt");
        String et = request.getParameter("et");
        map.put("name", name);
        map.put("cityCode", cityCode);
        map.put("phone", phone);
        map.put("bt", bt);
        map.put("et", et);
        List<Member> data = memberMapper.selectExamineAll(map);
        if (null != data && 0 < data.size()) {
            for (Member member : data) {
					member.setRegisterCategory(StringUtils.isNotEmpty(member.getRegisterCategory())?member.getRegisterCategory().toLowerCase():"");
            }
        }
        List<Map<String, Object>> lostPhoneNo = memberMapper.selectLostNoExamineList(map);
        //将手机号丢失的数据整合到member里面
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(lostPhoneNo)) {
            for (Map lost : lostPhoneNo) {
                Member member = new Member();
                member.setId(Integer.parseInt(lost.get("id").toString()));
                member.setName(lost.get("name").toString());
                member.setPhone(lost.get("phone").toString());
                member.setIdcard(lost.get("idcard").toString());
                member.setDriverIdcard(lost.get("driver_idcard").toString());
                member.setDriverIdcardPhotoUrl(lost.get("driver_idcard_photo_url").toString());
                member.setIdcardPhotoUrl(lost.get("idcard_photo_url").toString());
                member.setCreateTime(DateTime.StringToDate(lost.get("create_time").toString()));
                member.setStatus("experience");
                member.setChannel(2);//资料变更审核

                data.add(member);
            }
        }


        int totalRecords = memberMapper.selectExamineAllRecords(map) + memberMapper.selectLostNoExamineTotal(map);
        String json = Data2Jsp.Json2Jsp(data, totalRecords);
        response.getWriter().print(json);
        return null;
    }

    @SystemServiceLog(description = "查看图片")
    @RequestMapping(value = "/drierCarImgOpen", method = {RequestMethod.GET, RequestMethod.POST})
    public String memberExamineImgDispayPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        request.setAttribute("img", request.getParameter("img"));
        return "member/drierCarImgOpen";
    }

	@SystemServiceLog(description = "查看图片")
	@RequestMapping(value = "/idcardPhotoOpen", method = {RequestMethod.GET, RequestMethod.POST})
	public String memberExamineIdCardImgDispayPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setAttribute("img", request.getParameter("img"));
		return "member/idcardPhotoOpen";
	}

    @SystemServiceLog(description = "查看图片")
    @RequestMapping(value = "/lpnImgOpen", method = {RequestMethod.GET, RequestMethod.POST})
    public String lpnImgOpen(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        request.setAttribute("img", request.getParameter("img"));
        return "member/lpnImgOpen";
    }

    @SystemServiceLog(description = "待审核会员数量统计")
    @RequestMapping(value = "audit_member_total", method = {RequestMethod.GET, RequestMethod.POST})
    public String getTotalAuditMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        HashMap<String, Object> map = new HashMap<String, Object>();
        String cityCode = request.getParameter("cityCode");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String bt = request.getParameter("bt");
        String et = request.getParameter("et");
        map.put("name", name);
        map.put("cityCode", cityCode);
        map.put("phone", phone);
        map.put("bt", bt);
        map.put("et", et);
        int totalRecords = memberMapper.selectExamineAllRecords(map);
        response.getWriter().print(totalRecords);
        return null;
    }

    @SystemServiceLog(description = "会员贡献值页面")
    @RequestMapping(value = "member_contribute_item", method = {RequestMethod.GET, RequestMethod.POST})
    public String memberContributeItem() {
        return "member/memberContributeItem";
    }


    /**
     * 会员贡献值明细查询
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @SystemServiceLog(description = "会员贡献值明细查询")
    @RequestMapping(value = "query_member_contribute_item", method = {RequestMethod.GET, RequestMethod.POST})
    public String queryMemberContributeItem(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        //获得贡献值类型
        String type = request.getParameter("dicCode");
        Map<String, Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
        int from = pageInfo.get("first_page");
        int to = pageInfo.get("page_size");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("from", from);
        paramMap.put("to", to);
        paramMap.put("type", type);

        Pager<MemberContributedDetail> pager = memberService.selectMemberContributeItemByType(paramMap);
        List<MemberContributeItemVo> itemVos = new ArrayList<MemberContributeItemVo>();
        for (MemberContributedDetail memberContributedDetail : pager.getDatas()) {
            MemberContributeItemVo vo = new MemberContributeItemVo();
            if (memberContributedDetail.getType().contains("ORDER") || memberContributedDetail.getType().contains("BALANCE")) {
                //根据obj_id查询订单金额
                //如果是充值订单
                if (memberContributedDetail.getType().contains("BALANCE")) {
                    List<MemberChargeLog> logs = memberChargeLogService.selectByOrderId(memberContributedDetail.getObjId());
                    if (logs != null) {
                        Integer money = 0;
                        for (MemberChargeLog memberChargeLog : logs) {
                            money = money + memberChargeLog.getMoney();
                        }
                        vo.setOrderAmount(money / 100);
                    }
                }
                //如果是充电订单或者用车订单
                if (memberContributedDetail.getType().contains("ORDER")) {
                    //如果objId含有TS或者DR，说明是用车订单
                    if (memberContributedDetail.getObjId().contains("TS") || memberContributedDetail.getObjId().contains("DR")) {
                        //如果是时租订单
                        if (memberContributedDetail.getObjId().contains("TS")) {
                            TimeSharePay pay = timeSharePayService.selectByOrderId(memberContributedDetail.getObjId());
                            if (pay != null) {
                                vo.setOrderAmount(pay.getTotalPayMoney() / 100);
                            }
                        } else {//如果是日租订单
                            List<DayRentOrderExtend> orderExtends = dayRentOrderExtendService.selectByOrderId(memberContributedDetail.getObjId());
                            Integer OrderMoney = 0;
                            for (DayRentOrderExtend dayRentOrderExtend : orderExtends) {
                                OrderMoney += dayRentOrderExtend.getOrderMoney();
                            }
                            vo.setOrderAmount(OrderMoney / 100);
                        }

                    } else {//如果是充电订单
                        ChargingOrderInfo info = chargingOrderInfoService.selectByChargeSeq(memberContributedDetail.getObjId());
                        if (info != null) {
                            vo.setOrderAmount(info.getOrderMoney() / 100);
                        }

                    }
                }
            }
            vo.setContributedValDelta(memberContributedDetail.getContributedValDelta());
            vo.setCreateTime(memberContributedDetail.getCreateTime());
            //查询会员姓名
            Member member = memberService.selectByPrimaryKey(memberContributedDetail.getMemberId());
            if (member != null) {
                vo.setMemberName(member.getName());
            }
            //查询贡献值来源
            List<SysDic> sysDics = sysDicService.selectListByDicCode(memberContributedDetail.getType());
            if (sysDics.size() > 0) {
                vo.setType(sysDics.get(0).getName());
            }
            itemVos.add(vo);
        }
        Pager<MemberContributeItemVo> newPager = new Pager<MemberContributeItemVo>();
        newPager.setDatas(itemVos);
        newPager.setPageNumber(pager.getPageNumber());
        newPager.setPageSize(pager.getPageSize());
        newPager.setTotalCount(pager.getPageSize());
        response.getWriter().print(Data2Jsp.Json2Jsp(newPager));
        return null;
    }

    @SystemServiceLog(description = "会员贡献值来源")
    @RequestMapping(value = "sys_dicMemberContribute", method = {RequestMethod.GET, RequestMethod.POST})
    public String sysDicMemebrContribute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        String dicPartCode = request.getParameter("dicPartCode");
        List<SysDic> dics = sysDicService.selectListByDicPartCode(dicPartCode);
        JSONArray array = JSONArray.fromObject(dics);
        response.getWriter().print(array.toString());
        return null;
    }

    public static void main(String[] args) {
        //String url = "http://api.chinadatapay.com/communication/personal/1882?key=5c0525f28affd779f619120ec32c4f53&name=刘晓杰&idcard=440582199211180094";
        //String str = HttpTool.sendGet(url);
        //System.out.println(str);

        String url = "http://api.chinadatapay.com/communication/personal/1882";
        StringBuffer sb = new StringBuffer();
        sb.append("key=").append("5c0525f28affd779f619120ec32c4f53");
        sb.append("&name=").append("曾庆辉");
        sb.append("&idcard=").append("441900199701010192");

        String str = HttpTool.sendPost(url, sb.toString());
        System.out.println(str);
        JSONObject jsonObject = JSONObject.parseObject(str);
				/*String code = jsonObject.getString("code");
				if ("10000".equals(code)){
					response.getWriter().print("1");
				}else {
					response.getWriter().print("2");
				}*/
    }
}

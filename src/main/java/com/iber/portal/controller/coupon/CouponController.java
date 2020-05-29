package com.iber.portal.controller.coupon;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.ExcelUtil;
import com.iber.portal.common.HttpUtils;
import com.iber.portal.common.HttpsClientUtil;
import com.iber.portal.common.Pager;
import com.iber.portal.common.SysConstant;
import com.iber.portal.controller.MainController;
import com.iber.portal.dao.base.SystemMsgLogMapper;
import com.iber.portal.getui.PushClient;
import com.iber.portal.getui.PushCommonBean;
import com.iber.portal.model.base.Member;
import com.iber.portal.model.base.SystemMsgLog;
import com.iber.portal.model.coupon.Coupon;
import com.iber.portal.model.coupon.CouponLog;
import com.iber.portal.model.sys.SysParam;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.base.MemberService;
import com.iber.portal.service.coupon.CouponLogService;
import com.iber.portal.service.coupon.CouponService;
import com.iber.portal.service.sys.SysParamService;
import com.iber.portal.util.BatchSetterUtil;
import com.iber.portal.util.BuildCouponNo;
import com.iber.portal.util.DateTime;

import jxl.Sheet;
import jxl.Workbook;


@Controller
public class CouponController extends MainController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CouponService couponService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    CouponLogService couponLogService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private SysParamService sysParamService;

    @Autowired
    private SystemMsgLogMapper systemMsgLogService;

    /**
     * @describe 优惠券页面
     */
    @SystemServiceLog(description = "优惠券页面")
    @RequestMapping(value = "/coupon_page", method = {RequestMethod.GET})
    public String coupon(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        log.info("优惠券页面");
        return "coupon/coupon";
    }

    @SystemServiceLog(description = "优惠券模板下载")
    @RequestMapping(value = "/coupon_template.xls", method = {RequestMethod.GET})
    public void coupon_template_download(HttpServletResponse response) throws Exception {
		File file = new File(this.getClass().getResource("/").getPath() + "template/coupon_template.xls");
		InputStream is = new FileInputStream(file);
		// 设置response参数，可以打开下载页面
		response.reset();
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
		
    }
    /**
     * @describe 优惠券列表
     */
    @SystemServiceLog(description = "优惠券列表")
    @RequestMapping(value = "/coupon_list", method = {RequestMethod.GET, RequestMethod.POST})
    public String couponList(int page, int rows, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        log.info("优惠券列表");
        response.setContentType("text/html;charset=utf-8");
        Map<String, Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
        int from = pageInfo.get("first_page");
        int to = pageInfo.get("page_size");
        String minAmount = request.getParameter("minAmount");
        String maxAmount = request.getParameter("maxAmount");
        String title = request.getParameter("title");
        String batch_no = request.getParameter("batch_no");
        String coupon_no = request.getParameter("coupon_no");
        String status = request.getParameter("status");
        String use_status = request.getParameter("use_status");
        String city_code = request.getParameter("city_code");
        String memberName = request.getParameter("memberName");
        String applyDep = request.getParameter("applyDep");
        String memberPhone = request.getParameter("memberPhone");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        SysUser user = (SysUser) request.getSession().getAttribute("user");
        
//        if (user.getCityCode().equals("00")) {
//            if (null == city_code || "00".equals(city_code)) {
//                paramMap.put("cityCode", null);
//            } else {
//                paramMap.put("cityCode", city_code);
//            }
//        } else {
//            paramMap.put("cityCode", user.getCityCode());
//        }
        paramMap.put("cityCode", city_code);
        // 判断传入的额度范围参数是不是数字类型且是否符合一个数字格式
        if (regularCheck(minAmount)) {
            paramMap.put("minAmount", (null == minAmount || "".equals(minAmount)) ? "" : Double.valueOf(minAmount) * 100);
        } else {
            Pager<Coupon> coupons = new Pager<Coupon>();
            coupons.setTotalCount(0);
            response.getWriter().print(Data2Jsp.Json2Jsp(coupons));
            return null;
        }
        if (regularCheck(maxAmount)) {
            paramMap.put("maxAmount", (null == maxAmount || "".equals(maxAmount)) ? "" : Double.valueOf(maxAmount) * 100);
        } else {
            Pager<Coupon> coupons = new Pager<Coupon>();
            coupons.setTotalCount(0);
            response.getWriter().print(Data2Jsp.Json2Jsp(coupons));
            return null;
        }
        paramMap.put("from", from);
        paramMap.put("to", to);
        paramMap.put("title", title);
        paramMap.put("batchNo", batch_no);
        paramMap.put("couponNo", coupon_no);
        paramMap.put("status", status);
        paramMap.put("useStatus", use_status);
        paramMap.put("memberName", memberName);
        paramMap.put("memberPhone", memberPhone);
        paramMap.put("sysUserId", user.getId());
        paramMap.put("applyDep", applyDep);
        if (user.getId().equals(1)) {
            Pager<Coupon> pager = couponService.getAll(paramMap);
            response.getWriter().print(Data2Jsp.Json2Jsp(pager));
        } else {
            //根据登录id 查看对应的数据
            Pager<Coupon> pager = couponService.getAllUserId(paramMap);
            response.getWriter().print(Data2Jsp.Json2Jsp(pager));
        }
        return null;
    }

    private boolean regularCheck(String str) {
        if (null == str) {
            return true;
        }
        int index = str.lastIndexOf(".");
        if (-1 != index) {
            String temp = str.substring(0, index) + str.substring(index + 1, str.length());
            return temp.matches("\\d*");
        } else {
            return str.matches("\\d*");
        }
    }


    /**
     * @describe 保存更新优惠券
     */
    @SystemServiceLog(description = "保存更新优惠券")
    @RequestMapping(value = "/coupon_saveOrUpdate", method = {RequestMethod.GET, RequestMethod.POST})
    public String couponSaveOrUpdate(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        log.info("保存更新优惠券");
        response.setContentType("text/html;charset=utf-8");
        String id = request.getParameter("id");
        String applyDep = request.getParameter("applyDep");
        String applyPerson = request.getParameter("applyPerson");
        String cityCode = request.getParameter("city_code");
        String title = request.getParameter("title");
        String balance = request.getParameter("balance");
        String discount = request.getParameter("discount");
        String number = request.getParameter("number");
        String startTime = request.getParameter("start_time");
        String endTime = request.getParameter("end_time");
        String description = request.getParameter("description");
        String minUseValue = request.getParameter("minUseValue");
        String useType = request.getParameter("useType");
        String maxDeductionValue = request.getParameter("maxDeductionValue");
        String issueAuthority = request.getParameter("issueAuthority");//用户id
        SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String batchNo = sdf.format(Calendar.getInstance().getTime());
        Integer min_use_value = 0;
        if ("1".equals(useType) || "2".equals(useType)) {
            if (StringUtils.isNotBlank(minUseValue)) {
                if (minUseValue.contains("-")) {
                    response.getWriter().print("num-wrong");
                    return null;
                } else {
                    try {
                        if (minUseValue.contains(".")) {
                            double d = Double.parseDouble(minUseValue) * 100;
                            min_use_value = (new Double(d)).intValue();
                        } else {
                            min_use_value = Integer.valueOf(minUseValue) * 100;
                        }
                    } catch (Exception e) {
                        response.getWriter().print("num-wrong");
                        return null;
                    }
                }
            }
        }
        if (StringUtils.isNotBlank(id)) {
//            Coupon currObj = couponService.selectByPrimaryKey(Integer.parseInt(id));
//            if (currObj != null) {
//                couponService.updateByPrimaryKeySelective(currObj);
//            }
        } else {
            int num = Integer.parseInt(number);
            final List<Coupon> lists = new ArrayList<Coupon>();
            for (int i = 0; i < num; i++) {
                String couponNo = BuildCouponNo.generateShortUuid();
                Coupon obj = new Coupon();
                if(StringUtils.equals(useType, "2")) {
                	obj.setBalance(Double.parseDouble(discount) * 100);
                }else {
                	obj.setBalance(Double.parseDouble(balance) * 100);
                }
                obj.setBatchNo(batchNo);
                obj.setCityCode(cityCode);
                obj.setCouponNo(couponNo);
                obj.setStatus("0");
                obj.setCreateTime(new Date());
                if ("1".equals(useType) || "2".equals(useType)) {// 满减券
                    obj.setUseType(Integer.parseInt(useType));
                    obj.setMinUseValue(Integer.valueOf(min_use_value));
                } else {
                    obj.setUseType(0);
                    obj.setMinUseValue(0);
                }
                if (StringUtils.isNotBlank(maxDeductionValue)) {
                    Double mdv = Double.parseDouble(maxDeductionValue) * 100;
                    obj.setMaxDeductionValue(mdv.intValue());
                } else {
                    obj.setMaxDeductionValue(0);
                }
                obj.setUseStatus("0");
                obj.setDescription(description);
                obj.setStartTime(DateTime.getStartTimeOfDay(DateTime.StringToDate(startTime)));
                obj.setEndTime(DateTime.getEndTimeOfDay(DateTime.StringToDate(endTime)));
                obj.setTitle(title);
                obj.setType("0");
                obj.setCreateId(sysUser.getId());
                obj.setApplyDep(applyDep);
                obj.setApplyUser(applyPerson);
                if (!issueAuthority.equals("")) {
                    obj.setIssueAuthority(Integer.valueOf(issueAuthority));
                } else {
                    obj.setIssueAuthority(Integer.valueOf(-1));
                }
                lists.add(obj);
            }
            String sql = "insert into x_coupon(coupon_no,title,description,start_time,end_time,status,create_time," +
                    "use_status,balance,batch_no,city_code,type,item_code,use_type,issue_authority,min_use_value,create_id,max_deduction_value,apply_dep,apply_user) " +
                    "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            jdbcTemplate.batchUpdate(sql, new BatchSetterUtil<Coupon>(lists) {
                public void setValues(PreparedStatement ps, int i)
                        throws SQLException {
                    Coupon coupon = lists.get(i);
                    ps.setString(1, coupon.getCouponNo());
                    ps.setString(2, coupon.getTitle());
                    ps.setString(3, coupon.getDescription());
                    ps.setTimestamp(4, new Timestamp(coupon.getStartTime().getTime()));
                    ps.setTimestamp(5, new Timestamp(coupon.getEndTime().getTime()));
                    ps.setString(6, coupon.getStatus());
                    ps.setTimestamp(7, new Timestamp(coupon.getCreateTime().getTime()));
                    ps.setString(8, coupon.getUseStatus());
                    ps.setDouble(9, coupon.getBalance());
                    ps.setString(10, coupon.getBatchNo());
                    ps.setString(11, coupon.getCityCode());
                    ps.setString(12, coupon.getType());
                    ps.setString(13, coupon.getItemCode());
                    ps.setInt(14, coupon.getUseType());
                    ps.setInt(15, coupon.getIssueAuthority());
                    ps.setInt(16, coupon.getMinUseValue());
                    ps.setInt(17, coupon.getCreateId());
                    ps.setInt(18, coupon.getMaxDeductionValue() == null ? 0 : coupon.getMaxDeductionValue());
                    ps.setString(19, coupon.getApplyDep());
                    ps.setString(20, coupon.getApplyUser());
                }
            });
        }
        response.getWriter().print("success");
        return null;
    }

    /**
     * @describe 删除优惠券
     */
    @SystemServiceLog(description = "删除优惠券")
    @RequestMapping(value = "/coupon_del", method = {RequestMethod.GET, RequestMethod.POST})
    public String couponDel(String id, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        log.info("删除优惠券");
        response.setContentType("text/html;charset=utf-8");
        if (id != null && !id.equals("")) {
            couponService.deleteByPrimaryKey(Integer.parseInt(id));
        }
        response.getWriter().print("success");
        return null;
    }

	/**
	 * @describe重发优惠券
	 */
	@SystemServiceLog(description = "绑定优惠券")
	@ResponseBody
	@RequestMapping(value = "/reSendCoupon", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> reSendCoupon(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		SysUser user = getUser(request);
		String id = request.getParameter("id");
		String memberPhone = request.getParameter("memberPhone");
		String batchno = request.getParameter("batchno");
		String couponNo = request.getParameter("couponno");
		Integer couponNum = request.getParameter("couponNum") == null ? 1
				: Integer.parseInt(request.getParameter("couponNum"));
		boolean isSend = true;
		Date now = new Date();
		Double balance = 0.0;
		Member member = memberService.selectDetailByPhone(memberPhone);
		if (member == null) {
			map.put("status", "fail");
			map.put("msg", "不存在手机号码为 ：" + memberPhone + " 的会员");
			return map;
		}
		
		//	若所选记录优惠券编码有值，则直接将该优化编码与会员进行绑定：
		if (StringUtils.isNotBlank(couponNo)) {
			Coupon coupon = couponService.selectCouponByCouponNo(couponNo);
			if(StringUtils.equals(coupon.getStatus(), "1")) {
				map.put("status", "fail");
				map.put("msg", "绑定优惠券失败：优惠券已被领用！");
				return map;
			}
			if(coupon.getEndTime().getTime()<now.getTime()) {
				map.put("status", "fail");
				map.put("msg", "绑定优惠券失败：优惠券已失效");
				return map;
			}
			//绑定优惠券
			coupon.setStatus("1");
			coupon.setMemberId(member.getId());
			int rst = couponService.updateByPrimaryKeySelective(coupon);
			
			//保存绑定记录
			CouponLog couLog = couponLogService.selectByPrimaryKey(Integer.parseInt(id));
			couLog.setBatchno(batchno);
			couLog.setCouponno(coupon.getCouponNo());
			couLog.setCreateid(user.getId());
			couLog.setCreatetime(now);
			couLog.setMemberid(member.getId());
			if (rst > 0) {
				balance = coupon.getBalance();
				couLog.setCouponNum(1);
				couLog.setStatus(1);// 成功
				couponLogService.updateByPrimaryKeySelective(couLog);
			} else {
				// 记录剩下没有发送成功的优惠券
				isSend = false;
				map.put("status", "fail");
				map.put("msg", "手机号码为 ：" + memberPhone + " 的会员有(1)张优惠券发送失败");
				return map;
			}
			
		} else {
			//	若所选记录优惠券编码为空，则随机从对应批次号中选择对应数量的优惠券编码进行绑定：
			List<Coupon> uncollectedCouponList = couponService.getUncollectedByBatchNo(batchno);// 未领取的、有效的优惠券
			if (uncollectedCouponList == null || couponNum > uncollectedCouponList.size()) {
				map.put("status", "fail");
				map.put("msg", "绑定优惠券失败：优惠券已领完！");
				return map;
			}
			for (int i = 0; i < couponNum; i++) {
				Coupon coupon = uncollectedCouponList.get(i);
				if(coupon.getEndTime().getTime()<now.getTime()) {
					map.put("status", "fail");
					map.put("msg", "绑定优惠券失败：优惠券已失效");
					return map;
				}
				coupon.setStatus("1");
				coupon.setMemberId(member.getId());
				int rst = couponService.updateByPrimaryKeySelective(coupon);
				CouponLog couLog = new CouponLog();
				couLog.setBatchno(batchno);
				couLog.setCouponno(coupon.getCouponNo());
				couLog.setCreateid(user.getId());
				couLog.setCreatetime(now);
				couLog.setMemberid(member.getId());
				if (rst > 0) {
					balance = coupon.getBalance();
					couLog.setCouponNum(1);
					couLog.setStatus(1);// 成功
					couponLogService.insert(couLog);
				} else {
					// 记录剩下没有发送成功的优惠券
					couLog = couponLogService.selectByPrimaryKey(Integer.parseInt(id));
					couLog.setCouponNum(couponNum - i);
					couLog.setStatus(0);// 成功
					couponLogService.updateByPrimaryKeySelective(couLog);
					isSend = false;
					map.put("status", "fail");
					map.put("msg", "手机号码为 ：" + memberPhone + " 的会员有(" + (couponNum - i) + ")张优惠券发送失败");
					return map;
				}
			}
			if(isSend) {
				couponLogService.deleteByPrimaryKey(Integer.parseInt(id));
			}
		}
		if (isSend) {
			String content = "尊敬的会员，" + couponNum + "张" + (balance / 100) + "元(折)面值优惠券已经送到您账户，请注意查收！";
			SystemMsgLog systemMsgLog = new SystemMsgLog();
			systemMsgLog.setMsgType("coupon");
			systemMsgLog.setCreateTime(new Date());
			systemMsgLog.setMemberId(member.getId());
			systemMsgLog.setMsgTitle("优惠券");
			systemMsgLog.setMsgContent(content);
			systemMsgLogService.insertSelective(systemMsgLog);
			PushCommonBean push = new PushCommonBean(SysConstant.SERVER_PUSH_BING_COUPON, "1", content, "");
			List<String> cidList = PushClient.queryClientId(memberPhone);
			for (String memberCid : cidList) {
				PushClient.push(memberCid, JSONObject.fromObject(push));
			}
		}
		map.put("status", "success");
		map.put("msg", "发送成功");
		return map;
	}
	
    /**
     * @describe 绑定优惠券
     */
    @SystemServiceLog(description = "绑定优惠券")
    @RequestMapping(value = "/bindCoupon", method = {RequestMethod.GET, RequestMethod.POST})
    public String bindCoupon(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        log.info("绑定优惠券");
        response.setContentType("text/html;charset=utf-8");
        String phone = request.getParameter("phone"); //会员手机号
        String couponNo = request.getParameter("couponNo"); //优惠券编号
        String category = request.getParameter("category"); //类型 1 个人 2 企业
        String id = request.getParameter("id"); //优惠券ID
        String msgToResponse = setCoupon(phone, couponNo, category, id);
		response.getWriter().print(msgToResponse);
        return null;
    }

    private static final String RST_NOT_MEMBER = "notMember";
    private static final String RST_SUCC = "succ";
    private static final String RST_OVER = "over";//填写的手机号码数多于可用优惠券张数

    
    private List<String> readExcel(MultipartFile file) throws Exception {
    	InputStream is = file.getInputStream();
    	 // jxl提供的Workbook类
        Workbook wb = Workbook.getWorkbook(is);
        // Excel的页签数量
        int sheet_size = wb.getNumberOfSheets();
        List<String> list = new ArrayList<String>();
        for (int index = 0; index < sheet_size; index++) {
        	 Sheet sheet = wb.getSheet(index);
        	 for (int i = 1; i < sheet.getRows(); i++) {
        		// sheet.getColumns()返回该页的总列数
                 String cellinfo = sheet.getCell(2, i).getContents();
                 if(cellinfo.isEmpty()){
                    continue;
                 }
                 list.add(cellinfo);
        	 }
        }
		return list;
	}
    
    /**
     * 绑定团体优惠券
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @author ouxx
     * @date 2016-9-28 下午4:15:53
     */
	@SystemServiceLog(description = "绑定团体优惠券")
	@RequestMapping(value = "/bindGroupCoupon", method = { RequestMethod.GET, RequestMethod.POST })
	public String bindGroupCoupon(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false) MultipartFile file) throws Exception {
		log.info("绑定团体优惠券");
		response.setContentType("text/html;charset=utf-8");
		SysUser user = getUser(request);
		String batchNo = request.getParameter("batchNo"); // 优惠券批次号
		String phones = request.getParameter("phoneList"); // 会员手机号列表
		int num = Integer.valueOf(request.getParameter("couponNum"));// 优惠券数量
		List<String> phoneList = new ArrayList<String>();
		if (StringUtils.isNotBlank(phones)) {
			phones = phones.replaceAll("\r\n", ",");
			String[] ph = phones.split(",");// 会员手机号列表
			if (ph != null && ph.length > 0) {
				for (String phone : ph) {
					if (StringUtils.isNotBlank(phone))
						phoneList.add(phone);
				}
			}
		}
		if (file != null && !file.isEmpty()) {
			List<String> list = readExcel(file);
			phoneList.addAll(list);
		}
		
		if (phoneList.isEmpty()) {
			response.getWriter().print("member_empty");
			return null;
		}
		
		int memberCount = phoneList.size();
		List<Coupon> uncollectedCouponList = couponService.getUncollectedByBatchNo(batchNo);// 未领取的、有效的优惠券

		// 如果填写的手机号码数多于批次号为batchNo的未领取的优惠券张数，则提示前台
		if (uncollectedCouponList == null || (num * memberCount) > uncollectedCouponList.size() || num == 0) {
			response.getWriter().print(RST_OVER);
			return null;
		}

		// 错误信息
		StringBuffer sendFailMsg = new StringBuffer();// 没有此会员的信息

		Date now = new Date();
		// 给每一个号码绑定优惠券
		for (String phone : phoneList) {
			Double balance = 0.0;
			boolean isSend = true;
			// 1.通过手机号查询会员信息，如果不存在，则不发给该号码
			Member member = memberService.selectDetailByPhone(phone);
			if (member == null) {
				sendFailMsg.append("不存在手机号码为 ：" + phone + " 的会员\r\n");
				continue;
			}
			//判断优惠券是否还够用
			if (uncollectedCouponList.size() >= num) {
				for (int i = 0; i < num; i++) {
					Coupon coupon = uncollectedCouponList.get(0);
					//绑定优惠券
					coupon.setStatus("1");
					coupon.setMemberId(member.getId());
					int rst = couponService.updateByPrimaryKeySelective(coupon);
					
					//保存发放记录
					CouponLog couLog = new CouponLog();
					couLog.setBatchno(batchNo);
					couLog.setCouponno(coupon.getCouponNo());
					couLog.setCreateid(user.getId());
					couLog.setCreatetime(now);
					couLog.setMemberid(member.getId());
					if (rst > 0) {
						balance = coupon.getBalance();
						couLog.setCouponNum(1);
						couLog.setStatus(1);// 成功
						couponLogService.insert(couLog);
					} else {
						// 记录剩下没有发送成功的优惠券
						couLog.setCouponNum(num - i);
						couLog.setStatus(0);
						couponLogService.insert(couLog);
						isSend = false;
						sendFailMsg.append("手机号码为 ：" + phone + " 的会员有("+(num-i)+")张优惠券发送失败\r\n");
						break;
					}
					//删除已经发放的优惠券
					uncollectedCouponList.remove(0);
				}
			}
			
			//成功绑定优惠券 发送推送消息提醒优惠
			if (isSend) {
				String content = "尊敬的会员，" + num + "张" + (balance / 100) + "元(折)面值优惠券已经送到您账户，请注意查收！";
				SystemMsgLog systemMsgLog = new SystemMsgLog();
				systemMsgLog.setMsgType("coupon");
				systemMsgLog.setCreateTime(new Date());
				systemMsgLog.setMemberId(member.getId());
				systemMsgLog.setMsgTitle("优惠券");
				systemMsgLog.setMsgContent(content);
				systemMsgLogService.insertSelective(systemMsgLog);
				PushCommonBean push = new PushCommonBean(SysConstant.SERVER_PUSH_BING_COUPON, "1", content, "");
				List<String> cidList = PushClient.queryClientId(phone);
				for (String memberCid : cidList) {
					PushClient.push(memberCid, JSONObject.fromObject(push));
				}
			}
		}

		// 没有绑定不成功的情况，就返回succ，否则传给前台错误信息汇总
		if (StringUtils.isBlank(sendFailMsg.toString())) {
			response.getWriter().print(RST_SUCC);
		} else {
			response.getWriter().print(sendFailMsg.toString());
		}
		return null;
	}

	/**
     * 推送信息给绑定优惠券成功的用户
     *
     * @param coupon
     * @author ouxx
     * @date 2016-9-29 下午8:28:13
     */
    private void pushBindSuccMsg(Coupon coupon, int num) {
        String msg = "尊敬的会员，" + (coupon.getBalance() / 100) + "元(折)面值优惠券已经送到您账户，请注意查收！";
        if (num > 0) {
            msg = "尊敬的会员，" + num + "张" + (coupon.getBalance() / 100) + "元(折)面值优惠券已经送到您账户，请注意查收！";
        }
        PushCommonBean push = new PushCommonBean(SysConstant.SERVER_PUSH_BING_COUPON, "1", msg, "");
        String memberPhone = memberService.getPhoneById(coupon.getMemberId());// 获取会员手机号
        List<String> cidList = PushClient.queryClientId(memberPhone);
        for (String memberCid : cidList) {
            PushClient.push(memberCid, JSONObject.fromObject(push));
        }
    }

    /**
     * 设置单个用户的优惠券，先查用户是否存在，给存在的用户调用dubbo的绑定优惠券服务，再推送信息给用户
     *
     * @param phone
     * @param couponNo 优惠券编号
     * @param category "1"：个人，"2"：企业
     * @param id       优惠券id
     * @throws IOException
     * @author ouxx
     * @date 2016-9-28 下午4:08:21
     */
    private String setCoupon(String phone, String couponNo, String category, String id)
            throws IOException {
        String msgToResponse = "";
        SysParam sysParam = sysParamService.selectByKey("http_url");

        /**1.通过身份证查询会员信息*/
        Member member = memberService.selectDetailByPhone(phone);
        if (member != null) {
            //调用duboo接口绑定优惠券
            StringBuffer sb = new StringBuffer("{");
            sb.append("\"cId\":\"platForm\",\"memberId\":\"" + member.getId() + "\",");
            if (category.equals("1")) { //个人
                sb.append("\"method\":\"memberBindingCoupon\",");
            } else { //企业
                sb.append("\"method\":\"bindEnterpriseCoupon\",");
            }
            sb.append("\"param\":\"{'couponNo':'" + couponNo + "'}\",");
            sb.append("\"phone\":\"" + phone + "\",\"type\":\"platForm\",\"version\":\"1\"");
            sb.append("}");
            String json = "";
            if (sysParam.getValue().indexOf("https") == 0) { //https接口
                json = HttpsClientUtil.get(sysParam.getValue(), sb.toString());
            } else {
                json = HttpUtils.commonSendUrl(sysParam.getValue(), sb.toString());
            }
            JSONObject jsonObject = JSONObject.fromObject(json);
            String code = jsonObject.getString("code");
            if (code.equals("00")) {
                //给会员推送绑定优惠券成功
                Coupon coupon = couponService.selectByPrimaryKey(Integer.parseInt(id));
                
                CouponLog couLog = new CouponLog();
        		couLog.setBatchno(coupon.getBatchNo());
        		couLog.setCouponno(couponNo);
        		couLog.setCreateid(getUserId());
        		couLog.setCreatetime(new  Date());
        		couLog.setMemberid(member.getId());
        		couLog.setCouponNum(1);
        		couLog.setStatus(1);// 成功
        		couponLogService.insert(couLog);
                //将消息保存
                SystemMsgLog systemMsgLog = new SystemMsgLog();
                systemMsgLog.setMsgType("coupon");
                systemMsgLog.setCreateTime(new Date());
                systemMsgLog.setMemberId(member.getId());
                systemMsgLog.setMsgTitle("优惠券");
                systemMsgLog.setMsgContent("尊敬的会员，" + (coupon.getBalance() / 100) + "元(折)面值优惠券已经送到您账户，请注意查收！");
                systemMsgLogService.insertSelective(systemMsgLog);
                this.pushBindSuccMsg(coupon, 0);
                msgToResponse = RST_SUCC;
            } else {
                msgToResponse = jsonObject.getString("msg");
            }
        } else {
            msgToResponse = RST_NOT_MEMBER;
        }
        return msgToResponse;
    }

    /**
     * 修改优惠券状态
     */
    @SystemServiceLog(description = "修改优惠券状态")
    @RequestMapping("/coupon_Update")
    public void saveOrUpdate(Coupon entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("更新优惠券状态");
        String msg = "success";
        String id = request.getParameter("uid");
        String status = request.getParameter("status");
        entity.setId(Integer.parseInt(id));
        entity.setStatus(status);
        couponService.updateStatus(entity);
        response.getWriter().print(msg);
    }

}

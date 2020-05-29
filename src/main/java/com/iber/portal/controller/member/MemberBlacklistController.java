package com.iber.portal.controller.member;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iber.portal.enums.MemberBehaviorNameEnum;
import net.sf.json.JsonConfig;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
 
import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.ExcelUtil;
import com.iber.portal.common.JsonDateValueProcessor;
import com.iber.portal.common.Pager;
import com.iber.portal.controller.MainController;
import com.iber.portal.dao.base.SystemMsgLogMapper;
import com.iber.portal.dao.base.SystemMsgMapper;
import com.iber.portal.getui.PushClient;
import com.iber.portal.getui.PushCommonBean;
import com.iber.portal.model.base.Member;
import com.iber.portal.model.base.MemberCard;
import com.iber.portal.model.base.MemberLevel;
import com.iber.portal.model.base.SystemMsg;
import com.iber.portal.model.base.SystemMsgLog;
import com.iber.portal.model.employee.RescuerEmployeeInfo;
import com.iber.portal.model.member.MemberBlacklist;
import com.iber.portal.model.member.MemberBlacklistLog;
import com.iber.portal.model.member.MemberContributedDetail;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.base.MemberCardService;
import com.iber.portal.service.base.MemberLevelService;
import com.iber.portal.service.base.MemberService;
import com.iber.portal.service.member.MemberBlacklistLogService;
import com.iber.portal.service.member.MemberBlacklistService;
import com.iber.portal.service.member.MemberContributedDetailService;

/**
 * 
 * <br>
 * <b>功能：</b>MemberBlacklistController<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2013，www.jeecg.org<br>
 */ 
@Controller
public class MemberBlacklistController extends MainController {
	
	private final static Logger log= Logger.getLogger(MemberBlacklistController.class);
	
	// Servrice start
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private MemberBlacklistService memberBlacklistService; 
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MemberLevelService memberLevelService;
	
	@Autowired
	private MemberBlacklistLogService memberBlacklistLogService;
	
	@Autowired
	private MemberCardService memberCardService;
	
	@Autowired
	private SystemMsgMapper systemMsgMapper;
	
	@Autowired
	private SystemMsgLogMapper systemMsgLogMapper;
	
	@Autowired
	MemberContributedDetailService memberContributedDetailService;
	/**
	 * @return
	 * @throws Exception 
	 */
	@SystemServiceLog(description=" 黑名单页面")
	@RequestMapping("/memberBlacklist_page") 
	public String memberBlacklist_page(HttpServletRequest request, HttpServletResponse response) {
		log.info("黑名单页面");
		return "member/memberBlacklist" ;
	}
	
	
	/**
	 * 
	 * @param page
	 * @param rows
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description=" 查询黑名单")
	@RequestMapping("/dataListMemberBlacklist") 
	public void  datalist(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("查询黑名单");
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
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
		map.put("offset", pageInfo.get("first_page"));
		map.put("rows", pageInfo.get("page_size"));
		 
		Pager<MemberBlacklist> pager = memberBlacklistService.queryPageList(map);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
	}
	
	@SystemServiceLog(description="数据导出")
	@RequestMapping(value = "/export_blacklist_report", method = { RequestMethod.GET , RequestMethod.POST })
	public List exportExecl(String cityCode,String name,String phone,String bt ,String et,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		String fileName = "blacklistReport" ;
		//列名充电桩编码	
		String columnNames [] = {"所属城市","姓名", "性别","手机号", "身份证号","资金状态",
				"会员等级","会员贡献值","原因","创建时间","操作人" };
		
		String keys[] = {"cityName", "name", "sex", "phone","idCard","accoutStatus", "levelCode", "contributedVal",
				"reason","createTime","creator"};
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("cityCode", cityCode);
		map.put("phone", phone);
		map.put("bt", bt);
		map.put("et", et);
		map.put("offset", null);
		map.put("rows", null);
		
		Pager<MemberBlacklist> pager = memberBlacklistService.queryPageList(map);
		
		List<MemberBlacklist> datas = pager.getDatas();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> sheetNameMap = new HashMap<String, Object>();
		sheetNameMap.put("sheetName", "黑名单数据报表");
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
			List<MemberBlacklist> data) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;// 避免重复创建对象
		String keys[] = {"cityName", "name", "sex", "phone","idCard","accoutStatus", "levelCode", "contributedVal",
				"reason","createTime","creator"};
		for (MemberBlacklist mb : data) {
			map = new HashMap<String, Object>();
			map.put(keys[0], mb.getCityName());
			map.put(keys[1], mb.getName());
			if(mb.getSex() != null && !mb.getSex().equals("")){
				if(mb.getSex().equals("1")) map.put(keys[2], "男");
				if(mb.getSex().equals("2")) map.put(keys[2], "女");
			}
			map.put(keys[3], mb.getPhone());
			map.put(keys[4], mb.getIdCard());
			if(mb.getAccoutStatus() != null && !"".equals(mb.getAccoutStatus())){
				if(mb.getAccoutStatus().equals("0")) map.put(keys[5], "正常");
				if(mb.getAccoutStatus().equals("5")) map.put(keys[5], "冻结");
			}
			if(mb.getLevelCode() != null){
				switch (mb.getLevelCode()) {
				case 0:
					map.put(keys[6], "黑名单");
					break;
				case 1:
					map.put(keys[6], "一星会员");
					break;
				case 2:
					map.put(keys[6], "二星会员");
					break;
				case 3:
					map.put(keys[6], "三星会员");
					break;
				case 4:
					map.put(keys[6], "四星会员");
					break;
				case 5:
					map.put(keys[6], "五星会员");
					break;
				default:
					break;
				}
			}else{
				map.put(keys[6], mb.getLevelCode());
			}
			map.put(keys[7], mb.getContributedVal()); 
			if(mb.getIsAuto() != null){
				map.put(keys[8], mb.getIsAuto()==0?"贡献值过低，系统自动拉入黑名单":mb.getReason());
			}
			map.put(keys[9], mb.getCreateTime()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(mb.getCreateTime()):"");
			map.put(keys[10], mb.getCreator());
			
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 撤销黑名单
	 * @param MemberBlacklist
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description="撤销黑名单")
	@RequestMapping("/cancel_blacklist")
	@Transactional
	public String cancelBlacklist(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		response.setContentType("text/html;charset=utf-8");
		String isError = request.getParameter("isError");
		String cancelReason = request.getParameter("reason");
		String id = request.getParameter("id");
		String memberId = request.getParameter("memberId");
		String contributeDetailId = request.getParameter("contributeDetailId");
		String contributeVal = request.getParameter("contributeVal");
		if ("1".equals(isError) && StringUtils.isBlank(contributeDetailId)) {//如果是误判，但未选择贡献值明细
			response.getWriter().print("noContributeVal");
			return null;
		}
		Member member = memberService.selectByPrimaryKey(Integer.parseInt(memberId));
		MemberCard memberCard = memberCardService.selectByMemberId(Integer.parseInt(memberId));
		if ("0".equals(isError)) {//如果不是误判
			Integer levelCode = 1;//一星会员
			MemberLevel memberLevel =memberLevelService.selectByMemberLevel(levelCode);
			memberBlacklistService.deleteByPrimaryKey(Integer.parseInt(id));//将会员从黑名单删除
			member.setLevelCode(levelCode);
			memberCard.setContributedVal(memberLevel.getIntegralDownlimit());
			memberCardService.updateByPrimaryKey(memberCard);
			memberService.updateByPrimaryKey(member);
		}else {//是误判
			memberBlacklistService.deleteByPrimaryKey(Integer.parseInt(id));//将会员从黑名单删除
			//memberBlacklistService.deleteByPrimaryKey(Integer.parseInt(id));
			MemberContributedDetail detail = memberContributedDetailService.selectByPrimaryKey(Integer.parseInt(contributeDetailId));
			detail.setHadCancel(1);//将该条记录标记为已经被撤销过
			memberContributedDetailService.updateByPrimaryKey(detail);
			memberCard.setContributedVal(memberCard.getContributedVal() + Integer.parseInt(contributeVal));
			memberCardService.updateByPrimaryKey(memberCard);
			Integer memberLevel = memberLevelService.selectMemberLevelByContributeValue(Integer.parseInt(memberId));
			member.setLevelCode(memberLevel);
			member.setAccoutStatus("0");
			memberService.updateByPrimaryKey(member);
			MemberContributedDetail memberContributedDetail = new MemberContributedDetail();
			memberContributedDetail.setContributedValDelta(detail.getContributedValDelta());
			memberContributedDetail.setType(MemberBehaviorNameEnum.CANCEL_BLACKLIST.toString());
			memberContributedDetail.setCreateTime(new Date());
			memberContributedDetail.setMemberId(detail.getMemberId());
			memberContributedDetail.setObjId(detail.getObjId());
			memberContributedDetail.setHadCancel(0);
			memberContributedDetailService.insertSelective(memberContributedDetail);
		}
		//会员撤销黑名单后，给会员发送消息
		SystemMsg systemMsg = systemMsgMapper.selectByPrimaryKey(22);
		if(null != systemMsg){
			SystemMsgLog systemMsgLog = new SystemMsgLog();
			systemMsgLog.setMsgType("cancelMemberBlacklist");
			systemMsgLog.setCreateTime(new Date());
			systemMsgLog.setMemberId(Integer.parseInt(memberId));
			systemMsgLog.setMsgTitle(systemMsg.getMsgTitle());
			systemMsgLog.setMsgContent(systemMsg.getMsgContent());
			systemMsgLogMapper.insertSelective(systemMsgLog);
			PushCommonBean push = new PushCommonBean("push_server_system_msg_log","1","系统通知消息",systemMsgLog);
			if(push != null){
				List<String> alias = PushClient.queryClientId(member.getPhone());
				if(!alias.isEmpty() && alias.size()> 0){
					for(String cid : alias){
						JsonConfig jsonConfig = new JsonConfig();
						jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
						PushClient.push(cid, net.sf.json.JSONObject.fromObject(push, jsonConfig));
					}
				}
			}
		}
		MemberBlacklistLog memberBlacklistLog = new MemberBlacklistLog();
		memberBlacklistLog.setMemberId(Integer.parseInt(memberId));
		memberBlacklistLog.setReason(cancelReason);
		memberBlacklistLog.setCreateId(getUser(request).getId());
		memberBlacklistLog.setCreateTime(new Date());
		memberBlacklistLog.setOperate(1);//撤销黑名单
		memberBlacklistLogService.insert(memberBlacklistLog);
		response.getWriter().print("succ");
		return null;
	}

}

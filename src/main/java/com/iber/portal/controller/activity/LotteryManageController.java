package com.iber.portal.controller.activity;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iber.portal.dao.activity.LotteryDrawCouponMapper;
import com.iber.portal.model.activity.LotteryDrawCoupon;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.ResultMsg;
import com.iber.portal.dao.activity.LotteryDrawItemMapper;
import com.iber.portal.dao.activity.LotteryDrawLogMapper;
import com.iber.portal.dao.activity.LotteryDrawMapper;
import com.iber.portal.model.activity.LotteryDraw;
import com.iber.portal.model.activity.LotteryDrawItem;
import com.iber.portal.model.activity.LotteryDrawLog;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.util.DateTime;


@Controller
public class LotteryManageController {
	
	private static final Logger logger = LoggerFactory.getLogger(LotteryManageController.class);
	
	@Autowired
	private LotteryDrawMapper lotteryDrawMapper ;
	@Autowired
	private LotteryDrawItemMapper lotteryDrawItemMapper ;
	@Autowired
	private LotteryDrawLogMapper lotteryDrawLogMapper ;
	@Autowired
	private LotteryDrawCouponMapper lotteryDrawCouponMapper;
	
	@SystemServiceLog(description="抽奖管理页面")
	@RequestMapping(value={"/lottery_manage"},method=RequestMethod.GET)
	public String LotteryPage() throws Exception{
		logger.info("抽奖活动管理页面");
		return "activity/lotteryManagePage" ;
	}
	
	@SystemServiceLog(description="抽奖管理列表")
	@RequestMapping(value={"/lottery_draw_list"},method = { RequestMethod.GET , RequestMethod.POST })
	public String lotteryDrawList(Integer rows,Integer page,HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("utf-8");
		int p = (page == null) ? 1 : page ;
		int r = (rows == null) ? 100 : rows ;
		Map<String, Integer> pageInfo = Data2Jsp.getFristAndPageSize(p, r);
		Map<String,Object> map = new HashMap<String, Object>();
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		String cityCode = request.getParameter("cityCode");
		if(StringUtils.equals("00", cityCode)){
			map.put("cityCode", null) ;
		}else{
			map.put("cityCode", cityCode);
		}
		String title = request.getParameter("title") ;
		map.put("title", title) ;
		String activityType = request.getParameter("activityType") ;
		map.put("activityType", activityType) ;
		String status = request.getParameter("status") ;
		map.put("status", status) ;
		map.put("from", from);
		map.put("to", to);
		List<LotteryDraw> list = lotteryDrawMapper.selectAllLotteryDraw(map);
		int count = lotteryDrawMapper.selectCount(map);
		String json = Data2Jsp.Json2Jsp(list, count);
		response.getWriter().print(json);
		return null;
	}
	
	@SystemServiceLog(description="抽奖项目详情列表")
	@RequestMapping(value={"/getPrizeNameInfo"},method = { RequestMethod.GET , RequestMethod.POST })
	public String getPrizeNameInfo(Integer page, Integer rows, HttpServletRequest request, HttpServletResponse response)throws Exception {
		response.setContentType("text/html;charset=utf-8");
		int p = (page == null) ? 1 : page ;
		int r = (rows == null) ? 100 : rows ;
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(p, r);
		Map<String,Object> map = new HashMap<String, Object>();
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		String lotteryDrawId = request.getParameter("id") ;
		String prizeName = request.getParameter("prizeName") ;
		map.put("lotteryDrawId", lotteryDrawId) ;
		request.getSession().setAttribute("lotteryDrawId", lotteryDrawId);
		map.put("prizeName", prizeName) ;
		map.put("from", from);
		map.put("to", to);
		List<LotteryDrawItem> list = lotteryDrawItemMapper.selectAllLotteryDrawItem(map);
		int count = lotteryDrawItemMapper.selectCount(map);
		String json = Data2Jsp.Json2Jsp(list, count) ;
		response.getWriter().print(json) ;
		return null ;
	}
	
	@SystemServiceLog(description="抽奖项目详情添加修改")
	@RequestMapping(value={"/saveOrUpdateLottery"},method = { RequestMethod.GET , RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> saveOrUpdateLottery(LotteryDrawItem lotteryDrawItem ,HttpServletRequest request, HttpServletResponse response)throws Exception {
		Integer lotteryDrawId = Integer.valueOf((String) request.getSession().getAttribute("lotteryDrawId"));
		LotteryDrawItem drawItem = lotteryDrawItemMapper.selectByPrimaryKey(lotteryDrawItem.getId());
		if(drawItem != null){
			drawItem.setPrizeName(lotteryDrawItem.getPrizeName());
			drawItem.setPrizeAmount(lotteryDrawItem.getPrizeAmount());
			drawItem.setPrizeRestAmount(lotteryDrawItem.getPrizeRestAmount());
			drawItem.setPrizeWeight(lotteryDrawItem.getPrizeWeight());
			drawItem.setCreateTime(new Date());
			if(lotteryDrawItem.getPrizeName().indexOf("券") != -1){
				drawItem.setIsCoupon((byte) 1);
			}else{
				drawItem.setIsCoupon((byte) 0);
			}
			int record = lotteryDrawItemMapper.updateByPrimaryKeySelective(drawItem);
			if(record == 0){
				return ResultMsg.fialResult("更改数据失败,请重试！");
			}
			return ResultMsg.succResult("修改成功！");
		}else{
			lotteryDrawItem.setLotteryDrawId(lotteryDrawId);
			lotteryDrawItem.setCreateTime(new Date());
			if(lotteryDrawItem.getPrizeName().indexOf("券") != -1){
				lotteryDrawItem.setIsCoupon((byte) 1);
			}else{
				lotteryDrawItem.setIsCoupon((byte) 0);
			}
			//sort 最大加1
			int sort = lotteryDrawItemMapper.selectMaxSort();
			sort += 1;
			lotteryDrawItem.setSort(sort);
			int count = lotteryDrawItemMapper.insertSelective(lotteryDrawItem);
			if(count == 0){
				return ResultMsg.fialResult("添加数据失败,请重试！");
			}
			return ResultMsg.succResult("添加成功！");
		}
	}
	
	@SystemServiceLog(description = "抽奖管理添加修改")
	@RequestMapping(value = { "/editLotteryManage" }, method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> editLotteryManage(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		String cityCode = request.getParameter("cityCode");
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		String shareText = request.getParameter("shareText");
		String activityType = request.getParameter("activityType");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String shareUrl = request.getParameter("shareUrl");
		LotteryDraw draw = null;
		if (StringUtils.isNotBlank(id)) {
			draw = lotteryDrawMapper.selectByPrimaryKey(Integer.parseInt(id));
		}
		if (draw == null)
			draw = new LotteryDraw();
		draw.setCityCode(cityCode);
		draw.setShareText(shareText);
		draw.setActivityType(activityType);
		draw.setCreateTime(new Date());
		if (StringUtils.isNotBlank(startTime))
			draw.setStartTime(DateTime.StringToDate(startTime));
		if (StringUtils.isNotBlank(endTime))
			draw.setEndTime(DateTime.StringToDate(endTime));
		// 根据时间设置活动状态
		Date start = DateTime.StringToDate(startTime);
		Date end = DateTime.StringToDate(endTime);
		if (null != start && null != end) {
			Date now = Calendar.getInstance().getTime();
			if (now.after(start) && now.before(end)) {
				draw.setStatus((byte) 1);
			} else {
				draw.setStatus((byte) 0);
			}
		}
		draw.setShareUrl(shareUrl);
		draw.setCreateId(user.getId());
		if (draw.getId() != null) {
			int record = lotteryDrawMapper.updateByPrimaryKeySelective(draw);
			if (record == 0) {
				return ResultMsg.fialResult("更改数据失败,请重试！");
			}
			return ResultMsg.succResult("修改成功！");
		} else {
			int count = lotteryDrawMapper.insertSelective(draw);
			if (count == 0) {
				return ResultMsg.fialResult("添加数据失败,请重试！");
			}
			return ResultMsg.succResult("添加成功！");
		}
	}

//	@SystemServiceLog(description="抽奖项目删除")
//	@RequestMapping(value={"/LotteryItemDel"},method = { RequestMethod.GET , RequestMethod.POST })
//	@ResponseBody
//	public Map<String,Object> LotteryItemDel(Integer id ,HttpServletRequest request, HttpServletResponse response)throws Exception{
//		if(id == null ){
//			return ResultMsg.fialResult("更改id失败,请重试！");
//		}else{
//			int record = lotteryDrawItemMapper.deleteByPrimaryKey(id);
//			if(record == 0){
//				return ResultMsg.fialResult("删除失败,请重试！");
//			}
//			return ResultMsg.succResult("删除成功！");
//		}
//	}
	
	@SystemServiceLog(description="抽奖记录明细")
	@RequestMapping(value={"/Lottery_Detailed"},method=RequestMethod.GET)
	public String LotteryDetailed() throws Exception{
		logger.info("抽奖活动管理页面");
		return "activity/lotteryDetailedPage" ;
	}
	
	@SystemServiceLog(description="中奖订单记录管理列表")
	@RequestMapping(value={"/lottery_draw_log_list"},method = { RequestMethod.GET , RequestMethod.POST })
	public String lotteryDrawLogList(Integer rows,Integer page,HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("utf-8");
		int p = (page == null) ? 1 : page ;
		int r = (rows == null) ? 100 : rows ;
		Map<String, Integer> pageInfo = Data2Jsp.getFristAndPageSize(p, r);
		Map<String,Object> map = new HashMap<String, Object>();
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		String cityCode = request.getParameter("cityCode");
		if(StringUtils.equals("00", cityCode)){
			map.put("cityCode", null) ;
		}else{
			map.put("cityCode", cityCode);
		}
		String activityType = request.getParameter("activityType") ;
		map.put("activityType", activityType) ;
		String name = request.getParameter("memberName");
		map.put("memberName", name);
		String phone = request.getParameter("memberPhone");
		map.put("memberPhone", phone);
		String st = request.getParameter("bt");
		map.put("bt", st);
		String et = request.getParameter("et");
		map.put("et", et);
		String prizeName = request.getParameter("prizeName");
		map.put("prizeName", prizeName);
		map.put("from", from);
		map.put("to", to);
		List<LotteryDrawLog> list = lotteryDrawLogMapper.selectLotteryLog(map);
		int count = lotteryDrawLogMapper.selectCount(map);
		String json = Data2Jsp.Json2Jsp(list, count);
		response.getWriter().print(json);
		return null;
	}
	@SystemServiceLog(description="抽奖项目优惠券策略配置")
	@RequestMapping(value={"/getPrizeCoupnConfigInfo"},method = { RequestMethod.GET , RequestMethod.POST })
	public String getPrizeCoupnConfigInfo(Integer page, Integer rows, HttpServletRequest request, HttpServletResponse response)throws Exception {
		response.setContentType("text/html;charset=utf-8");
		int p = (page == null) ? 1 : page ;
		int r = (rows == null) ? 100 : rows ;
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(p, r);
		Map<String,Object> map = new HashMap<String, Object>();
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		String id = request.getParameter("id") ;
		map.put("id", id) ;
		map.put("from", from);
		map.put("to", to);
		List<LotteryDrawCoupon> list = lotteryDrawCouponMapper.queryCouponStrategy(map);
		int count = lotteryDrawCouponMapper.selectCount(map);
		String json = Data2Jsp.Json2Jsp(list, count) ;
		response.getWriter().print(json) ;
		return null ;
	}

	@SystemServiceLog(description="配置抽奖项目优惠券策略")
	@RequestMapping(value={"/saveOrUpdateDrawCouponStrategy"},method = { RequestMethod.GET , RequestMethod.POST })
	@ResponseBody
	public String saveOrUpdateDrawCouponStrategy (LotteryDrawCoupon lotteryDrawCoupon ,HttpServletRequest request, HttpServletResponse response)throws Exception {
		response.setContentType("text/html;charset=utf-8");
		lotteryDrawCoupon.setMaxBalance(Optional.ofNullable(lotteryDrawCoupon.getMaxBalance()).orElse(0.0)*100);
		lotteryDrawCoupon.setMinBalance(Optional.ofNullable(lotteryDrawCoupon.getMinBalance()).orElse(0.0)*100);
		lotteryDrawCoupon.setMinUseValue(Optional.ofNullable(lotteryDrawCoupon.getMinUseValue()).orElse(0.0)*100);
		lotteryDrawCoupon.setMaxDeductionValue(Optional.ofNullable(lotteryDrawCoupon.getMaxDeductionValue()).orElse(0.0)*100);
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		lotteryDrawCoupon.setEditor(user.getName());
		if (lotteryDrawCoupon.getId()==null){
			lotteryDrawCoupon.setCreateTime(new Date());
			lotteryDrawCouponMapper.insertSelective(lotteryDrawCoupon);
		}else{
			lotteryDrawCouponMapper.updateByPrimaryKeySelective(lotteryDrawCoupon);
		}
		response.getWriter().print("ok") ;
		return null;
	}

	@SystemServiceLog(description="抽奖项目优惠券策略删除")
	@RequestMapping(value={"/delete_lottery_coupon"},method = { RequestMethod.GET , RequestMethod.POST })
	@ResponseBody
	public Map<String,Object> deleteLotteryCoupon(Integer id ,HttpServletRequest request, HttpServletResponse response)throws Exception{
		if(id == null ){
			return ResultMsg.fialResult("更改id失败,请重试！");
		}else{
			int record = lotteryDrawCouponMapper.deleteByPrimaryKey(id);
			if(record == 0){
				return ResultMsg.fialResult("删除失败,请重试！");
			}
			return ResultMsg.succResult("删除成功！");
		}
	}
}

package com.iber.portal.service.car;

import com.iber.portal.common.HttpUtils;
import com.iber.portal.common.HttpsClientUtil;
import com.iber.portal.common.Pager;
import com.iber.portal.dao.base.MemberMapper;
import com.iber.portal.dao.car.CarRescueMapper;
import com.iber.portal.dao.member.MemberBehaviorMapper;
import com.iber.portal.dao.task.TaskMapper;
import com.iber.portal.model.base.Member;
import com.iber.portal.model.car.CarRescue;
import com.iber.portal.model.car.CarRescueProblem;
import com.iber.portal.model.member.EvidenceRelation;
import com.iber.portal.model.member.MemberBehavior;
import com.iber.portal.model.sys.SysParam;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.model.task.TaskInfo;
import com.iber.portal.service.sys.SysParamService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service("carRescueService")
public class CarRescueService{

	private final static Logger log= Logger.getLogger(CarRescueService.class);
	
	@Autowired
    private CarRescueMapper  carRescueMapper;
	@Autowired
	private TaskMapper taskMapper;
	@Autowired
	private SysParamService sysParamService;
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private MemberBehaviorMapper behaviorMapper;
	@Autowired
	private JdbcTemplate jdbcTemplate;
		
	public int insert(CarRescue record){
		return carRescueMapper.insert(record) ;
	}
	
	public int deleteByPrimaryKey(Integer id){
		/*CarRescue carRescue = carRescueMapper.selectByPrimaryKey(id);
		if(!carRescue.getStatus().equals("0")){
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("lpn", lpn);
			map.put("status", "empty");
			int r1 = carRunMapper.updateCarRunStatusByLpn(map);
		}*/
		int r2 = carRescueMapper.deleteByPrimaryKey(id);
		return r2;
	}
	
	public int updateByPrimaryKey(CarRescue record){
		return carRescueMapper.updateByPrimaryKey(record) ;
	}
	
	public int updateByPrimaryKeySelective(CarRescue record){
		return carRescueMapper.updateByPrimaryKeySelective(record) ;
	}
	
	public CarRescue selectByPrimaryKey(Integer id){
		return carRescueMapper.selectByPrimaryKey(id) ;
	}
	
	public int getAllNum(Map<String, Object> paramMap){
		return carRescueMapper.getAllNum(paramMap) ;
	}
	
	public Pager<CarRescue> queryPageList(Map<String, Object> paramMap){
		List<CarRescue> listObj = carRescueMapper.queryPageList(paramMap);
		Pager<CarRescue> pager = new Pager<CarRescue>();
		pager.setDatas(listObj);
		pager.setTotalCount(getAllNum(paramMap));
		return pager;
	}
	
	public int carResume(int id, String user,String result){
		CarRescue CarRescue = carRescueMapper.selectByPrimaryKey(id);
		CarRescue.setEndTime(new Date());
		CarRescue.setUpdateTime(new Date());
		CarRescue.setUpdateUser(user);
		CarRescue.setResult(result);
		CarRescue.setStatus("0");
		/*HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("lpn", CarRescue.getLpn());
		map.put("status", "empty");
		int r1 = carRunMapper.updateCarRunStatusByLpn(map);*/
		int r2 = carRescueMapper.updateByPrimaryKeySelective(CarRescue);
		if( r2 > 0){
			return 1;
		}else{
			return -1;
		}
	}
	/**保存车辆救援信息*/
	public int saveCarRescueInfo(CarRescue carRescue){
		/*//更新车辆状态
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("lpn", carRescue.getLpn());
		map.put("status","rescue");
		int r1 = carRunMapper.updateCarRunStatusByLpn(map);*/
		//插入车辆维修信息
		int r2 = carRescueMapper.insertSelective(carRescue);
		if(r2 > 0){
			return 1;
		}else{
			return -1;
		}
	}
	@Transactional(rollbackFor=Exception.class)
	public int carResumeRepair(HttpServletRequest request,CarRescue carRescue,List<EvidenceRelation> urlLists) {
		if(0<urlLists.size()){
			carRescueMapper.insertRescuePicUrls(urlLists);
		}
		TaskInfo taskInfo = taskMapper.getRescueRecordByLpn("5", carRescue.getLpn());
		SysParam sysParam = sysParamService.selectByKey("http_url");
		String url = "";
		if(sysParam != null){
			url = sysParam.getValue();
		}
		Integer memberId = null==taskInfo?null:taskInfo.getMemberId();
        if(null!=memberId){
        	Member mem = memberMapper.selectByPrimaryKey(memberId);
        	SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
        	String type = "";
        	if(!StringUtils.isBlank(carRescue.getResponsibleType())){
        		MemberBehavior behavior = behaviorMapper.selectByPrimaryKey(Integer.valueOf(carRescue.getResponsibleType()));
        		type = behavior.getType();
        	}
            insertMemberContributedDetailByBehavior(url,
					"{'memberId':'"+memberId.toString()+"','objId':'','typeEnum':'"+type+"','multiplicand':'0','createId':'"+sysUser.getId()+"'}",
					memberId.toString(),mem.getPhone(),"insertMemberContributedDetailByBehavior");
        }
		// 平台结束了车辆救援,该车对应的救援任务也结束,5代表救援任务
		taskMapper.updateTaskStatusToFinish("5",taskInfo.getStatus(),carRescue.getLpn(),carRescue.getResult());
		return carRescueMapper.carResumeRepair(carRescue);
	}
	
	private String insertMemberContributedDetailByBehavior(String url,String jsonParam, String memberId, String phone, String method) {
		String json = "{}";
		StringBuffer sb = new StringBuffer("{") ;
		sb.append("\"cId\":\"platForm\",\"memberId\":\""+memberId+"\",") ;
		sb.append("\"method\":\""+method+"\",") ;
		sb.append("\"param\":\""+jsonParam+"\",") ;
		sb.append("\"phone\":\""+phone+"\",\"type\":\"platForm\",\"version\":\"1\"") ;
		sb.append("}") ;
		if(url.indexOf("https") == 0){ //https接口
			json = HttpsClientUtil.get(url, sb.toString()) ;
		}else{
			json = HttpUtils.commonSendUrl(url, sb.toString()) ;
		}
		return json;
	}

	public int selectByLpn(Map<String, Object> paramMap){
		return carRescueMapper.selectByLpn(paramMap) ;
	}
	
	public List<String> getRescueEviPicsByReportId(Integer reportId){
		return carRescueMapper.getRescueEviPicsByReportId(reportId);
	}

	public void updateRescueRecordCharge(String lpn, Integer employeeId,String sysUserAccount) {
		 jdbcTemplate.execute("call p_updateRescueRecord('"+lpn+"',"+employeeId+",'"+sysUserAccount+"')");
	}
	public Integer selectUnfinishedRecordByLpn(Map<String, Object> paramMap){
		return carRescueMapper.selectUnfinishedRecordByLpn(paramMap);
	}

	public Integer insertCarRescueProblems(List<CarRescueProblem> problems) {
		return carRescueMapper.insertCarRescueProblems(problems);
	}
	public List<CarRescueProblem> selectCarProblemsDetailByRecId(Integer recId){
		return carRescueMapper.selectCarProblemsDetailByRecId(recId);
	}
}

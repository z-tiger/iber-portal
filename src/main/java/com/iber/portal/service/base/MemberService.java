package com.iber.portal.service.base;

import com.google.common.collect.Maps;
import com.iber.portal.common.CommonUtil;
import com.iber.portal.common.HttpsClientUtil;
import com.iber.portal.common.MD5;
import com.iber.portal.common.Pager;
import com.iber.portal.dao.base.MemberCardMapper;
import com.iber.portal.dao.base.MemberMapper;
import com.iber.portal.dao.order.UnpaidOrderMapper;
import com.iber.portal.dao.sys.SysParamMapper;
import com.iber.portal.getui.PushClient;
import com.iber.portal.getui.PushCommonBean;
import com.iber.portal.model.base.Member;
import com.iber.portal.model.base.MemberCard;
import com.iber.portal.model.base.MemberLevel;
import com.iber.portal.model.member.MemberContributedDetail;
import com.iber.portal.model.member.MemberTotalVo;
import com.iber.portal.model.sys.SysParam;
import com.iber.portal.vo.order.UnpaidMemberVo;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MemberService {

	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private MemberCardMapper memberCardMapper;
	
	@Autowired
	private UnpaidOrderMapper unpaidOrderMapper;

	@Autowired
	private SysParamMapper sysParamMapper;
	/*private String deleteUrl= "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/delete";
    //百度access_token
	private  static String token;
	//获取百度access_token
	static {
        Map<String, String> params = new HashMap<>();
        String tokenUrl="https://aip.baidubce.com/oauth/2.0/token";
        params.put("grant_type", "client_credentials");
        params.put("client_id",SysConstant.BAIDU_API_KEY);
        params.put("client_secret", SysConstant.BAIDU_SECRET_KEY);
        token = com.alibaba.fastjson.JSONObject.parseObject(HttpsClientUtil.post(tokenUrl, params)).getString("access_token");
        System.out.println("token>>>>:"+token);

    }*/


	public int deleteByPrimaryKey(Integer id) {
		return memberMapper.deleteByPrimaryKey(id);
	}

	public int insert(Member record) {
		return memberMapper.insert(record);
	}

	public int insertSelective(Member record) {
		return memberMapper.insertSelective(record);
	}

	public Member selectByPrimaryKey(Integer id) {
		return memberMapper.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKeySelective(Member record) {
		return memberMapper.updateByPrimaryKeySelective(record);
	}

	public int updateByPrimaryKey(Member record) {
		return memberMapper.updateByPrimaryKey(record);
	}
	
	public Member selectDetailByPhone(String phone) {
		return memberMapper.selectDetailByPhone(phone) ;
	}
	
	public Member selectDetailByIdcard(String idcard) {
		return memberMapper.selectDetailByIdcard(idcard) ;
	}
	
	public Member selectDetailByEmail(String email) {
		return memberMapper.selectDetailByEmail(email) ;
	}
	
	public Member selectEnterpriseAccount(int enterpriseId) {
		return memberMapper.selectEnterpriseAccount(enterpriseId) ;
	}
	
	public List<Member> selectAll(Map<String, Object> map){
		return memberMapper.selectAll(map);
	}
	
	public int queryExists(Integer id, Integer type, String value){
		return memberMapper.queryExists(id, type, value);
	}
	    
	public    int selectAllRecords(Map<String, Object> map){
		return memberMapper.selectAllRecords(map);
	}
	  
	public List<Member> selectDetailByIdcard4List(String idcard){
		return memberMapper.selectDetailByIdcard4List(idcard);
	}
	
	public   int restPassword(int id ){
		Member member = memberMapper.selectByPrimaryKey(id);
		String phone = member.getPhone();
		String newPwd = MD5.toMD5(phone.substring(5));
		return memberMapper.restPassword(id, newPwd);
	}
	    
	public   int deleteFingerprint(int id){
		return memberMapper.deleteFingerprint(id);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int saveMemberInfo(Member member){
		String pwd = MD5.toMD5(member.getPhone().substring(5));
		member.setStatus("experience");
		member.setPassword(pwd);
		if(StringUtils.isBlank(member.getName())){
			member.setName(member.getPhone());
		}
		member.setRegisterCategory("platform");
		member.setAccoutStatus("0");
		member.setIsDrive("0");
		member.setCreateTime(new Date());
		int r1  = memberMapper.insertSelective(member);
		MemberCard memberCard = new MemberCard();
		memberCard.setMemberId(member.getId());
		memberCard.setMoney(0);
		memberCard.setTotalChargeMoney(0);
		memberCard.setTotalConsumeMoney(0);
		memberCard.setTotalRefundMoney(0);
		memberCard.setQuota(0);
		memberCard.setCreateTime(new Date());
		memberCard.setDeposit(0);
		memberCard.setIntegral(0);
		memberCard.setQuota(0);
		memberCard.setQuotaMonth(0);
		memberCard.setQuotaUseMoney(0);
		int r2 = memberCardMapper.insertSelective(memberCard);
		if(r1 >0 && r2 >0){
			return 1;
		}else{
			return -1;
		}
	}
	
	public int memberExamineSave(String refuseReason,int id, String status, String remark,String name , String idcard , String driverIdcard , Date driverIdcardValidityTime,int driverIdcardUpdate,String sex, Date driverIdcardTime, Date examineTime, Integer examineId){
		return memberMapper.memberExamineSave(refuseReason, id, status, remark, name ,  idcard ,  driverIdcard ,  driverIdcardValidityTime,driverIdcardUpdate,sex,driverIdcardTime,examineTime,examineId);
	}

	public List<Member> selectEnterpriseAll(HashMap<String, Object> map) {
		return memberMapper.selectEnterpriseAll(map);
	}

	public int selectEnterpriseAllRecords(HashMap<String, Object> map) {
		return memberMapper.selectEnterpriseAllRecords(map);
	}
	
	/**
	 * 根据id查会员手机号
	 * @param id
	 * @return
	 * @author ouxx
	 * @date 2016-9-29 下午2:42:22
	 */
	public String getPhoneById(int id) {
		return memberMapper.getPhoneById(id);
	}
	
	public int removeEnterpriseId(int id){
		return memberMapper.removeEnterpriseId(id);
	}
	
	public List<Member> selectMemberDriverIdcardValidity(){
		return memberMapper.selectMemberDriverIdcardValidity() ;
	}
	
	/**
	 * 每月1号更新会员的贡献值
	 * @return
	 * @author ouxx
	 * @date 2016-12-27 下午2:03:20
	 */
	public int updateContributedVal(){
		return memberMapper.updateContributedVal();
	}
	
	/**
	 * 更新会员等级编码，latest_contributed_val在x_member_level的区间中：
	 *  [integral_down_limit, integral_up_limit)
	 * @return
	 * @author ouxx
	 * @date 2016-12-27 下午3:51:59
	 */
	public int updateLevelCode(List<MemberLevel> levelList){
		return memberMapper.updateLevelCode(levelList);
	}
	
	/**
	 * 查时租、日租、充电 结束后7天还未支付的会员
	 * @return
	 * @author ouxx
	 * @date 2016-12-30 下午7:07:48
	 */
	public List<UnpaidMemberVo> queryMemberWithUnpaidOrder(Integer days){
		return this.unpaidOrderMapper.queryMemberWithUnpaidOrder(days);
	}
	
	/**
	 * 查时租、日租、充电 结束后6天还未支付的会员
	 * @return
	 * @author ouxx
	 * @date 2016-12-30 下午7:07:48
	 */
	public List<UnpaidMemberVo> queryNoPayMemberByBetwentDay(Integer startDay, Integer endDay){
		return this.unpaidOrderMapper.queryNoPayMemberByBetwentDay(startDay, endDay);
	}
	
	/**查询各省的会员数*/
	public List<MemberTotalVo> queryAllMemberNums(String cityCode){
		return memberMapper.queryAllMemberNums(cityCode) ;
	}
	
	/**查询省下级城市的会员数*/
	public List<MemberTotalVo> queryCityAllMemberNums(String cityCode){
		return memberMapper.queryCityAllMemberNums(cityCode) ;
	}
	
	public List<MemberTotalVo> getMemberTotal(String cityCode){
		return memberMapper.getMemberTotal(cityCode) ;
	}
	
	public  List<MemberTotalVo> getMemberTotalByLevel(Map<String, Object> paramMap){
		return memberMapper.getMemberTotalByLevel(paramMap);
	}
	
	public  List<MemberTotalVo> getMemberTotalByType(Map<String, Object> paramMap){
		return memberMapper.getMemberTotalByType(paramMap);
	}
	
	public  List<MemberTotalVo> getMemberTotalByStatus(Map<String, Object> paramMap){
		return memberMapper.getMemberTotalByStatus(paramMap);
	}
	
	public  List<MemberTotalVo> getExpenseMember(Map<String, Object> paramMap){
		return memberMapper.getExpenseMember(paramMap);
	}
	
	public  List<MemberTotalVo> getExpenseMemberByType(Map<String, Object> paramMap){
		return memberMapper.getExpenseMemberByType(paramMap);
	}


    public Pager<Member> selectEnterpriseMemberPage(Map<String, Object> map) {
	    List<Member> members = memberMapper.selectEnterpriseAll(map);
        Pager<Member> pager = new Pager<Member>();
        pager.setDatas(members);
        pager.setTotalCount(memberMapper.selectEnterpriseAllRecords(map));
        return pager;
    }

    public Pager<MemberContributedDetail> selectMemberContributeItemByType(Map<String, Object> paramMap) {
		List<MemberContributedDetail> listObj = memberMapper.selectMemberContributeItemByType(paramMap);
		Pager<MemberContributedDetail> pager = new Pager<MemberContributedDetail>();
		pager.setDatas(listObj);
		pager.setTotalCount(getAllNum(paramMap));
		return pager;
	}
	
	public int getAllNum(Map<String, Object> paramMap){
		return memberMapper.getAllNum(paramMap);
	}
	
	public  List<MemberTotalVo> getExpenseMemberByLevel(Map<String, Object> paramMap){
		return memberMapper.getExpenseMemberByLevel(paramMap);
	}
	
	public  List<MemberTotalVo> getExpenseMemberByStatus(Map<String, Object> paramMap){
		return memberMapper.getExpenseMemberByStatus(paramMap);
	}
	
	public int examineIdcardRecord(String idcard) {
		return memberMapper.examineIdcardRecord(idcard);
	}

	public List<Member> selectByName(String name) {
		return memberMapper.selectByName(name);
	}

	public List<Member> queryMemberDriving2Year() {
		return memberMapper.queryMemberDriving2Year();
	}
	//四星、五星
	public List<Member> selectFourFiveStar(){
		return memberMapper.selectFourFiveStar();
	}

    /**
     * 删除人脸
     * @param id
     */
    public boolean deleteFace(Integer id,String accessToken,String uid) {

        String deleteUrl= "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/delete";
        String queryUrl = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/get";
        System.out.println(uid);

//      删除百度人脸库里面的人脸信息
        Map<String, String> param = new HashMap<String, String>();
        param.put("access_token",accessToken);
        param.put("uid", uid);

        String queryResult = HttpsClientUtil.post(queryUrl, param);
        if (StringUtils.isBlank(queryResult)||com.alibaba.fastjson.JSONObject.parseObject(queryResult).getInteger("error_code") !=null) {
            return false;
        }

        String result = HttpsClientUtil.post(deleteUrl, param);
        System.out.println(result);
        if(result==null||com.alibaba.fastjson.JSONObject.parseObject(result).getInteger("error_code")!=null){
            return false;
        }
        memberMapper.deleteFace(id);
        return true;


    }


	/**
	 * 删除指纹
	 * @param id
	 * @param lpn
	 * @param idcard
	 */
	public void deleteFingerPrint(Integer id,String lpn,String idcard){
		Map<String,String> map = new HashMap<String, String>() ;
		map.put("identifyNo", idcard) ;
		PushCommonBean push = new PushCommonBean("server_push_delete_customer_finger","1","删除指纹",map);
		memberMapper.deleteFingerprint(id);
		List<String> alias = PushClient.queryClientId(lpn);
		if(!CollectionUtils.isEmpty(alias)){
			for(String cid : alias){
				PushClient.push(cid, net.sf.json.JSONObject.fromObject(push));
			}
		}
	}

	/**
	 * 删除指纹
	 * @param id
	 * @param lpn
	 */
	public Map<String,Object> deleteTboxFingerPrint(Integer id,String lpn){
		memberMapper.deleteTboxFingerPrint(id);
		// 调用接口
		SysParam sysParam = sysParamMapper.selectByKey("http_url_tbox") ;
		JSONObject object = new JSONObject();
		object.put("id",id);
		object.put("lpn",lpn);

		Map<String,Object> params = Maps.newHashMap();
		params.put("method","clearFinger");
		params.put("data",object.toString());
		final String url = sysParam.getValue();
		return CommonUtil.httpInterfacePost(url, params);
	}

    public List<Map<String, Object>> selectByCityCode(String cityCode) {
		return memberMapper.selectByCityCode(cityCode);
    }

	public List<Map<String,Object>> selectByPhoneList(List<String> phones) {
		return memberMapper.selectByPhoneList(phones);
	}
}

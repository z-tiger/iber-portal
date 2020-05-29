package com.iber.portal.service.timeShare;

import com.alibaba.fastjson.JSON;
import com.iber.portal.common.OSSClientAPI;
import com.iber.portal.common.Pager;
import com.iber.portal.common.Picture;
import com.iber.portal.common.SysConstant;
import com.iber.portal.dao.base.MemberMapper;
import com.iber.portal.dao.base.SystemMsgLogMapper;
import com.iber.portal.dao.base.SystemMsgMapper;
import com.iber.portal.dao.timeShare.TimeShareOrderAttachedDetailMapper;
import com.iber.portal.dao.timeShare.TimeShareOrderAttachedMapper;
import com.iber.portal.getui.PushClient;
import com.iber.portal.getui.PushCommonBean;
import com.iber.portal.model.base.Member;
import com.iber.portal.model.base.SystemMsg;
import com.iber.portal.model.base.SystemMsgLog;
import com.iber.portal.model.timeShare.TimeShareOrderAttached;
import com.iber.portal.model.timeShare.TimeShareOrderAttachedDetail;
import com.iber.portal.service.sys.SysParamService;
import com.iber.portal.util.DateTime;
import com.iber.portal.util.SendSMS;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TimeShareOrderAttachedService {

    @Autowired
    private TimeShareOrderAttachedMapper timeShareOrderAttachedMapper;

    @Autowired
    private TimeShareOrderAttachedDetailMapper timeShareOrderAttachedDetailMapper;

    @Autowired
    private SysParamService sysParamService;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private SystemMsgLogMapper systemMsgLogMapper;

    @Autowired
    private SystemMsgMapper systemMsgMapper;


    /**
     * 保存附属订单 和附属订单的收费项目
     * 步骤：
     * 1、获取List
     * 2、保存附属订单
     * 3、批量保存附属订单的收费项目
     * 4、发送推送消息
     * @param timeShareOrderAttached
     */
    @Transactional
    public String saveOrderAttached(TimeShareOrderAttached timeShareOrderAttached, MultipartFile repairUriFile, MultipartFile carDamageUriFile) throws Exception{
        Integer orderCount = 0;
        if (timeShareOrderAttached.getOrderId().indexOf("TS")>-1){
            orderCount = this.findOrder(timeShareOrderAttached.getOrderId());
        }else if (timeShareOrderAttached.getOrderId().indexOf("DR")>-1){
            orderCount = this.findLongRentOrder(timeShareOrderAttached.getOrderId());
        }

        if (orderCount>=1){
            List<TimeShareOrderAttachedDetail> details = timeShareOrderAttached.getItem();
            Iterator<TimeShareOrderAttachedDetail> it = details.iterator();
            float allMoney = 0;//初始总金额
            while(it.hasNext()){
                TimeShareOrderAttachedDetail x = it.next();
                allMoney = allMoney + (x.getMoney()!=null?x.getMoney()*100:0); //收费项目金额累积
                if(x.getIscheck()==null||!x.getIscheck()){
                    it.remove();
                }else{
                    switch (x.getItem()){
                        case RESCUE:
                            if (x.getExplanation()!=null&&x.getMoney()!=null){
                                x.setExplanation(x.getExplanation()+" "+x.getMoney()+"/次");
                            }
                            break;
                    }
                }
            }
            if (allMoney==0&&timeShareOrderAttached.getToUser()==1){ //用户订单并且为零的不给保存
                return "订单金额为零!";
            }
            //维修单据、车辆损毁照片上传到CDN
            OSSClientAPI oss = makeUploadFileOss();
            //维修单据
            String repairFileUri = uploadImg(repairUriFile, oss);
            //车辆损毁照片
            String drivingLicenseFileUri = uploadImg(carDamageUriFile, oss);

            timeShareOrderAttached.setRepairPictureUrl(repairFileUri);
            timeShareOrderAttached.setCarDamagePictureUrl(drivingLicenseFileUri);

            timeShareOrderAttachedMapper.saveTimeShareOrderAttached(timeShareOrderAttached);
            if (details!=null&&details.size()>0&&timeShareOrderAttached.getToUser()==1){
                timeShareOrderAttachedDetailMapper.saveOrderAttachedDetail(details,timeShareOrderAttached);
            }
            //查找用户
            List<Member> list = memberMapper.queryMemberByOrderId(timeShareOrderAttached.getOrderId());
            //
            if (list.size()>=1&&timeShareOrderAttached.getToUser()==1&&allMoney>0){
                /**发送短信**/
                Map<String, String> params = new HashMap<String, String>();
                //获取加密token
                String encryptToken = SendSMS.encryptBySalt(list.get(0).getPhone());
                Map map = new HashMap();
                map.put("telephoneNo",list.get(0).getPhone());
                map.put("ipAddress","");
                map.put("templateId","2697");
                map.put("contentParam",new String(timeShareOrderAttached.getType().toString().getBytes("utf-8"),"ISO-8859-1"));
                map.put("token",encryptToken);
                params.put("msgContentJson", JSON.toJSONString(map));
                //发送短信
                SendSMS.send(params);

                /**保存到系统**/
                SystemMsg systemMsg = systemMsgMapper.selectByPrimaryKey(SysConstant.ATTACHEDORDER);
                SystemMsgLog systemMsgLog = new SystemMsgLog();
                systemMsgLog.setMsgType("refund");
                systemMsgLog.setCreateTime(new Date());
                systemMsgLog.setMemberId(list.get(0).getId());
                systemMsgLog.setMsgTitle(systemMsg.getMsgTitle().replace("{0}",timeShareOrderAttached.getType().toString()));
                String content = systemMsg.getMsgContent().replace("{0}",timeShareOrderAttached.getType().toString());
                systemMsgLog.setMsgContent(content);
                systemMsgLogMapper.insertSelective(systemMsgLog);

                /**个推消息**/
                PushCommonBean push = new PushCommonBean(SysConstant.SERVER_PUSH_CAPITAL_BLOCK,
                        "1", "【宜步出行】尊敬的会员，您的（{0}）订单已生成，请及时支付。如有疑问，请联系客服4000769755".replace("{0}",timeShareOrderAttached.getType().toString()),"") ;
                List<String> cidList = PushClient.queryClientId(list.get(0).getPhone());
                for (String memberCid : cidList) {
                    PushClient.push(memberCid,push);
                }
            }
           return"success";
        }else{
            return "订单不存在";
        }

    }

    private Integer findLongRentOrder(String orderId) {
        return timeShareOrderAttachedMapper.findLongRentOrder(orderId);
    }

    /**
     * 附属订单的totalcount
     * @param map
     * @return
     */
    public int getAllNum(Map<String,Object> map){
        return timeShareOrderAttachedMapper.getAllNum(map);
    }

    /**
     * 附属订单收入报表totalcount
     * @param map
     * @return
     */
    public int getAllNumReport(Map<String,Object> map){
        return timeShareOrderAttachedMapper.getAllNumReport(map);
    }

    /**
     * 分页查找附属订单
     * @param map
     * @return
     */
    public Pager< HashMap> findPage(Map<String,Object> map){
        List<HashMap> listObj = timeShareOrderAttachedMapper.getPage(map);
        Pager< HashMap> pager = new Pager< HashMap>();
        pager.setDatas(listObj);
        pager.setTotalCount(getAllNum(map));
        return pager;
    }

    /**
     * 查找该订单是否存在
     * @param order
     * @return
     */
    public Integer findOrder(String order) {
        return timeShareOrderAttachedMapper.findOrder(order);
    }
    /**
     * 根据附属订单id查找附属订单收费项目list
     * @param id
     * @return
     */
    public Pager<HashMap>  queryAttachedOrderDetail(String id) {
        List<HashMap> listObj = timeShareOrderAttachedMapper.queryAttachedOrderDetail(id);
        Pager< HashMap> pager = new Pager< HashMap>();
        pager.setDatas(listObj);
        pager.setTotalCount(0);
        return pager;
    }

    /**
     * 查询附属订单收入报表
     * @param map
     * @return
     */
    public Pager<HashMap> findPageReport(Map<String, Object> map) {
        List<HashMap> listObj = timeShareOrderAttachedMapper.getPageReport(map);
        for (HashMap hashMap : listObj) {
            hashMap.put("lostIncome",0);
            hashMap.put("maintainCost",0);
            hashMap.put("rescueCost",0);
            hashMap.put("illegalProcessingCost",0);
            hashMap.put("insurancePremiumCost",0);
            Object itemInfor = hashMap.get("itemInfor");
            if (itemInfor==null) {
                continue;
            }
            String [] strs = itemInfor.toString().split(",");
//            System.out.println(Arrays.toString(strs));
            for (String str : strs) {
                if (str.startsWith("误工费")) {
                    hashMap.put("lostIncome", str.split(":")[1]);
                }
                if (str.startsWith("救援费")) {
                    hashMap.put("rescueCost", str.split(":")[1]);
                }
                if (str.startsWith("维修费")) {
                    hashMap.put("maintainCost", str.split(":")[1]);
                }
                if (str.startsWith("违章处理")) {
                    hashMap.put("illegalProcessingCost", str.split(":")[1]);
                }
                if (str.startsWith("保险上涨费")) {
                    hashMap.put("insurancePremiumCost", str.split(":")[1]);
                }
            }
        }


        Pager< HashMap> pager = new Pager< HashMap>();
        pager.setTotalCount(getAllNumReport(map));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //当前时间，根据当前时间获取当月的开始和结束时间，当年的开始和结束时间
        Date currtime = new Date();
       /* String monthStart = simpleDateFormat.format(DateTime.getStartTimeOfMonth(currtime));
        String monthEnd = simpleDateFormat.format(DateTime.getEndTimeOfMonth(currtime));*/

        String yearStart = simpleDateFormat.format(DateTime.getStartTimeOfYear(currtime));
        String yearEnd = simpleDateFormat.format(DateTime.getEndTimeOfYear(currtime));

       /* map.put("queryDateFrom", monthStart);
        map.put("queryDateTo", monthEnd);*/
        map.put("searchType",0);
        HashMap totalMonth = timeShareOrderAttachedMapper.countTotalToReport(map);
        totalMonth.put("lostIncome", timeShareOrderAttachedMapper.countlostIncomeTotalToReport(map));
        totalMonth.put("maintainCost", timeShareOrderAttachedMapper.countMaintainTotalToReport(map));
        totalMonth.put("rescueCost", timeShareOrderAttachedMapper.countRescueTotalToReport(map));
        totalMonth.put("illegalProcessingCost", timeShareOrderAttachedMapper.countIllegalTotalToReport(map));
        totalMonth.put("insurancePremiumCost", timeShareOrderAttachedMapper.countInsuranceTotalToReport(map));

        map.remove("offset");
        map.remove("rows");
        map.put("queryDateFrom", yearStart);
        map.put("queryDateTo", yearEnd);
        map.put("searchType",1);
        HashMap totalYear = timeShareOrderAttachedMapper.countTotalToReport(map);
        totalYear.put("lostIncome", timeShareOrderAttachedMapper.countlostIncomeTotalToReport(map));
        totalYear.put("maintainCost", timeShareOrderAttachedMapper.countMaintainTotalToReport(map));
        totalYear.put("rescueCost", timeShareOrderAttachedMapper.countRescueTotalToReport(map));
        totalYear.put("illegalProcessingCost", timeShareOrderAttachedMapper.countIllegalTotalToReport(map));
        totalYear.put("insurancePremiumCost", timeShareOrderAttachedMapper.countInsuranceTotalToReport(map));

        map.remove("queryDateFrom");
        map.remove("queryDateTo");
        map.put("searchType",2);
        HashMap total = timeShareOrderAttachedMapper.countTotalToReport(map);
        total.put("lostIncome", timeShareOrderAttachedMapper.countlostIncomeTotalToReport(map));
        total.put("maintainCost", timeShareOrderAttachedMapper.countMaintainTotalToReport(map));
        total.put("rescueCost", timeShareOrderAttachedMapper.countRescueTotalToReport(map));
        total.put("illegalProcessingCost", timeShareOrderAttachedMapper.countIllegalTotalToReport(map));
        total.put("insurancePremiumCost", timeShareOrderAttachedMapper.countInsuranceTotalToReport(map));



        //end
        listObj.add(totalMonth);
        listObj.add(totalYear);
        listObj.add(total);

        pager.setDatas(listObj);

        return pager;
    }

    public List<Map<String, Object>> findPageReportAll(Map<String,Object> map) {
        List<Map<String, Object>> listObj = timeShareOrderAttachedMapper.getPageReportAll(map);
        for (Map hashMap : listObj) {
            hashMap.put("lostIncome",0);
            hashMap.put("maintainCost",0);
            hashMap.put("rescueCost",0);
            hashMap.put("illegalProcessingCost",0);
            hashMap.put("insurancePremiumCost",0);
            Object itemInfor = hashMap.get("itemInfor");
            if (itemInfor==null) {
                continue;
            }
            String [] strs = itemInfor.toString().split(",");
            for (String str : strs) {
                if (str.startsWith("误工费")) {
                    hashMap.put("lostIncome", str.split(":")[1]);
                }
                if (str.startsWith("救援费")) {
                    hashMap.put("rescueCost", str.split(":")[1]);
                }
                if (str.startsWith("维修费")) {
                    hashMap.put("maintainCost", str.split(":")[1]);
                }
                if (str.startsWith("违章处理")) {
                    hashMap.put("illegalProcessingCost", str.split(":")[1]);
                }
                if (str.startsWith("保险上涨费")) {
                    hashMap.put("insurancePremiumCost", str.split(":")[1]);
                }
            }
        }


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //当前时间，根据当前时间获取当月的开始和结束时间，当年的开始和结束时间
        Date currtime = new Date();
       /* String monthStart = simpleDateFormat.format(DateTime.getStartTimeOfMonth(currtime));
        String monthEnd = simpleDateFormat.format(DateTime.getEndTimeOfMonth(currtime));*/

        String yearStart = simpleDateFormat.format(DateTime.getStartTimeOfYear(currtime));
        String yearEnd = simpleDateFormat.format(DateTime.getEndTimeOfYear(currtime));

       /* map.put("queryDateFrom", monthStart);
        map.put("queryDateTo", monthEnd);*/
        map.put("searchType",0);
        HashMap totalCurr = timeShareOrderAttachedMapper.countTotalToReport(map);
        totalCurr.put("lostIncome", timeShareOrderAttachedMapper.countlostIncomeTotalToReport(map));
        totalCurr.put("maintainCost", timeShareOrderAttachedMapper.countMaintainTotalToReport(map));
        totalCurr.put("rescueCost", timeShareOrderAttachedMapper.countRescueTotalToReport(map));
        totalCurr.put("illegalProcessingCost", timeShareOrderAttachedMapper.countIllegalTotalToReport(map));
        totalCurr.put("insurancePremiumCost", timeShareOrderAttachedMapper.countInsuranceTotalToReport(map));

        map.put("queryDateFrom", yearStart);
        map.put("queryDateTo", yearEnd);
        map.put("searchType",1);
        HashMap totalYear = timeShareOrderAttachedMapper.countTotalToReport(map);
        totalYear.put("lostIncome", timeShareOrderAttachedMapper.countlostIncomeTotalToReport(map));
        totalYear.put("maintainCost", timeShareOrderAttachedMapper.countMaintainTotalToReport(map));
        totalYear.put("rescueCost", timeShareOrderAttachedMapper.countRescueTotalToReport(map));
        totalYear.put("illegalProcessingCost", timeShareOrderAttachedMapper.countIllegalTotalToReport(map));
        totalYear.put("insurancePremiumCost", timeShareOrderAttachedMapper.countInsuranceTotalToReport(map));


        map.remove("queryDateFrom");
        map.remove("queryDateTo");
        map.put("searchType",2);
        HashMap total = timeShareOrderAttachedMapper.countTotalToReport(map);
        total.put("lostIncome", timeShareOrderAttachedMapper.countlostIncomeTotalToReport(map));
        total.put("maintainCost", timeShareOrderAttachedMapper.countMaintainTotalToReport(map));
        total.put("rescueCost", timeShareOrderAttachedMapper.countRescueTotalToReport(map));
        total.put("illegalProcessingCost", timeShareOrderAttachedMapper.countIllegalTotalToReport(map));
        total.put("insurancePremiumCost", timeShareOrderAttachedMapper.countInsuranceTotalToReport(map));
        //end
        listObj.add(totalCurr);
        listObj.add(totalYear);
        listObj.add(total);
        return listObj;
    }

    private OSSClientAPI makeUploadFileOss() {
        String endpoint = sysParamService.selectByKey("endpoint").getValue();
        String accessKeyId = sysParamService.selectByKey("accessKeyId").getValue();
        String accessKeySecret = sysParamService.selectByKey("accessKeySecret").getValue();
        String bucketName = sysParamService.selectByKey("bucketName").getValue();
        String dns = sysParamService.selectByKey("dns").getValue();
        OSSClientAPI oss = new OSSClientAPI(endpoint, accessKeyId, accessKeySecret, bucketName, dns);
        return oss;
    }

    private String uploadImg(MultipartFile file, OSSClientAPI oss) throws Exception{
        String filename = file.getOriginalFilename();
        if(!filename.equals("")){
            CommonsMultipartFile cf= (CommonsMultipartFile)file; //这个myfile是MultipartFile的
            DiskFileItem fi = (DiskFileItem)cf.getFileItem();
            File file1 = fi.getStoreLocation();
            InputStream is =   Picture.resizePngNoCompress(file1, filename.substring(filename.lastIndexOf(".") +1)) ;
            String newFileName = UUID.randomUUID().toString() + "." + filename.substring(filename.lastIndexOf(".") +1);

            return oss.putObject(newFileName, is, "timeShareOrderAttached/");
        }
        return null;
    }
}

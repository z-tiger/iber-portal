package com.iber.portal.service.longRent;
import com.iber.portal.dao.car.CarRunMapper;
import com.iber.portal.dao.dayRent.DayLongRentOrderMapper;
import com.iber.portal.dao.longRent.LongRentExchangeCarLogMapper;
import com.iber.portal.model.car.CarRun;
import com.iber.portal.model.dayRent.LongRentOrder;
import com.iber.portal.model.longRent.LongRentExchangeCarLog;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.car.CarRunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author zengfeiyue
 */
@Service
public class LongRentExChangeCarLogService {
    @Autowired
    private LongRentExchangeCarLogMapper longRentExchangeCarLogMapper;
    @Autowired
    private DayLongRentOrderMapper dayLongRentOrderMapper;
    @Autowired
    private CarRunMapper carRunMapper;

    @Autowired
    private CarRunService carRunService;

    /**
     * 保存换车记录
     * 修改车辆状态
     * @param orderId
     * @param carRun
     * @param afterLpn
     * @param sysUser
     * @param reason
     * @param longRentOrder
     */
    @Transactional(rollbackFor=Exception.class)
    public void saveLongRentExchangeCarLog(String orderId, CarRun carRun,String  afterLpn, SysUser sysUser,String reason,LongRentOrder longRentOrder) throws Exception{
        //最近一次换车记录
        LongRentExchangeCarLog queryLog =longRentExchangeCarLogMapper.selectLatestByOrderId(orderId);
        //最早一天日租记录
        LongRentOrder longRentOrderbefor = dayLongRentOrderMapper.selectLatestByOrderIdAndLpn(orderId,carRun.getLpn());
        Date beginTime = null;
        Date endTime = new Date();
        double mileage = carRun.getTotalKg()==null?0:carRun.getTotalKg();
        if (queryLog==null){
            beginTime = longRentOrderbefor.getBeginTime();
        }else{
            beginTime = queryLog.getEndTime();
        }
        LongRentExchangeCarLog longRentExchangeCarLog = new LongRentExchangeCarLog();
        longRentExchangeCarLog.setOrderId(orderId);
        longRentExchangeCarLog.setAddress(carRun.getAddress());
        longRentExchangeCarLog.setAfterLpn(afterLpn);
        longRentExchangeCarLog.setBeforeLpn(carRun.getLpn());
        longRentExchangeCarLog.setCreateTime(new Date());
        longRentExchangeCarLog.setCreateUser(sysUser.getName());
        longRentExchangeCarLog.setBeginTime(beginTime);
        longRentExchangeCarLog.setEndTime(endTime);
        longRentExchangeCarLog.setMileage(mileage);
        longRentExchangeCarLog.setReason(reason);
        longRentExchangeCarLogMapper.insertSelective(longRentExchangeCarLog);

        //更新车牌
        longRentOrder.setLpn(afterLpn);
        longRentOrder.setLatestChangedCarTime(new Date());
        longRentOrder.setChangedCarTimes(longRentOrder.getChangedCarTimes()==null?1:longRentOrder.getChangedCarTimes()+1);
        //dayLongRentOrderMapper.updateByPrimaryKeySelective(longRentOrder);

        //保存换车信息
        dayLongRentOrderMapper.updateChangeCarMsg(longRentOrder);
        System.err.println(longRentOrder.getMemberId());
        //换车后的car_run
        CarRun afterCar = carRunService.getCarInfo(afterLpn);
        int record = carRunMapper.updateVersionIsEmpy(afterCar);
        if (record > 0){

            //更新carRun为useCar状态
            Map status = new HashMap(3);
            status.put("status","useCar");
            status.put("orderId",orderId);
            status.put("lpn",afterCar.getLpn());
            status.put("memberId",longRentOrder.getMemberId());
            carRunMapper.updateCarRunStatusAndOrderIdByLpn(status);

            //把换车前的车辆订单置为空
            Map before = new HashMap(3);
            before.put("orderId","");
            before.put("lpn",carRun.getLpn());
            before.put("memberId",null);
            carRunMapper.updateCarRunStatusAndOrderIdByLpn(before);
        }else{
            throw new Exception("car_run status is not empty");
        }


    }

    public List<LongRentExchangeCarLog> queryAllHistory(String orderId) {
        return this.longRentExchangeCarLogMapper.queryAllHistory(orderId);
    }
}

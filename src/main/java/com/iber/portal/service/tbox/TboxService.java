package com.iber.portal.service.tbox;

import com.google.common.collect.Maps;
import com.iber.portal.common.CommonUtil;
import com.iber.portal.dao.car.CarMapper;
import com.iber.portal.dao.sys.SysParamMapper;
import com.iber.portal.model.car.Car;
import com.iber.portal.model.sys.SysParam;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TboxService {

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private SysParamMapper sysParamMapper;

    private static final String CLOSE_DOOR = "closeDoor";
    private static final String OPEN_DOOR = "openDoor";
    private static final String OPEN_ACC = "openACC";
    private static final String START_CAR = "startCar";
    private static final String SHUTDOWN = "shutDown";

    /**
     * 车辆的tbox版本
     *  2 二代 3 三代
     * @param lpn
     * @return
     */
    public Integer tboxVersion(String lpn){
        final Car car = carMapper.selectByLpn(lpn);
        return car == null ? 0 : car.getTboxVersion();
    }

    /**
     * 关门
     * @param lpn
     */
    public Map<String, Object> closeCarDoor(String lpn){
        return controlCar(lpn,CLOSE_DOOR);
    }

    /**
     * 开门
     * @param lpn
     */
    public Map<String, Object> openCarDoor(String lpn){
        return controlCar(lpn,OPEN_DOOR);
    }

    /**
     * 启动
     * @param lpn
     */
    public Map<String, Object> startCarEngine(String lpn){
        return controlCar(lpn,OPEN_ACC);
    }

    /**
     * 熄火
     * @param lpn
     */
    public Map<String, Object> carFireOff(String lpn){
        return controlCar(lpn,SHUTDOWN);
    }


    /**
     * 一键启动
     * lf
     * 2017年11月27日
     * @param lpn
     */
    public Map<String, Object> startCar(String lpn) {
        return controlCar(lpn,START_CAR);
    }

    /**
     * 设置车型
     * lf
     * 2017年11月27日
     * @param lpn 车牌
     * @param carType 车型
     * @return
     */
    public Map<String, Object> setCarType(String lpn,String carType) {
        SysParam sysParam = sysParamMapper.selectByKey("http_url_tbox") ;
        JSONObject object = new JSONObject();
        object.put("lpn",lpn);
        object.put("carType",carType);
        Map<String,Object> params = Maps.newHashMap();
        params.put("method","portalSetCarType");
        params.put("data",object.toString());
        final String url = sysParam.getValue();
        return CommonUtil.httpInterfacePost(url, params);
    }

    /**
     * tbox 升级
     * lf
     * 2017年11月28日
     * @param lpn 车牌
     * @param versionUrl 地址
     * @return
     */
    public Map<String, Object> boxUpgrade(String lpn, String versionUrl) {
        SysParam sysParam = sysParamMapper.selectByKey("http_url_tbox") ;
        JSONObject object = new JSONObject();
        object.put("lpn",lpn);
        object.put("versionUrl",versionUrl);
        Map<String,Object> params = Maps.newHashMap();
        params.put("method","boxUpgrade");
        params.put("data",object.toString());
        final String url = sysParam.getValue();
        return CommonUtil.httpInterfacePost(url, params);
    }

    /**
     * 重启tbox
     * @param lpn
     * @return
     */
    public Map<String, Object> rebootSystem(String lpn) {
        SysParam sysParam = sysParamMapper.selectByKey("http_url_tbox") ;
        JSONObject object = new JSONObject();
        object.put("lpn",lpn);
        Map<String,Object> params = Maps.newHashMap();
        params.put("method","rebootSystem");
        params.put("data",object.toString());
        final String url = sysParam.getValue();
        return CommonUtil.httpInterfacePost(url, params);
    }

    /**
     * 三代车辆控制
     * @param lpn 车牌
     * @param cmd 命令
     * @return
     */
    private Map<String, Object> controlCar(String lpn,String cmd){
        SysParam sysParam = sysParamMapper.selectByKey("http_url_tbox") ;
        JSONObject object = new JSONObject();
        object.put("lpn",lpn);
        object.put("cmd",cmd);
        Map<String,Object> params = Maps.newHashMap();
        params.put("method","controlCar");
        params.put("data",object.toString());
        final String url = sysParam.getValue();
        return CommonUtil.httpInterfacePost(url, params);
    }

}

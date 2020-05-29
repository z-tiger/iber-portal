package com.iber.portal.service.car;

import com.google.common.collect.Maps;
import com.iber.portal.common.CommonUtil;
import com.iber.portal.common.Pager;
import com.iber.portal.dao.base.ParkMapper;
import com.iber.portal.dao.car.*;
import com.iber.portal.dao.charging.CarBrandMapper;
import com.iber.portal.dao.enterprise.EnterpriseMapper;
import com.iber.portal.dao.sys.SysParamMapper;
import com.iber.portal.dao.tbox.TboxImeiMapper;
import com.iber.portal.model.base.Park;
import com.iber.portal.model.car.Car;
import com.iber.portal.model.car.CarPreOffline;
import com.iber.portal.model.car.CarRun;
import com.iber.portal.model.charging.CarBrand;
import com.iber.portal.model.enterprise.Enterprise;
import com.iber.portal.model.sys.SysParam;
import com.iber.portal.vo.car.CarMrgVo;
import com.iber.portal.vo.car.CarUpgradeVo;
import com.iber.portal.vo.car.CarVersionVo;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class CarService {

	@Autowired
	private CarMapper carMapper;
	
	@Autowired
	private CarRunMapper carRunMapper;
	
	@Autowired
	private ParkMapper parkMapper;
	
	@Autowired
	private CarTypeMapper carTypeMapper ;
	
	@Autowired
	private CarBrandMapper carBrandMapper ;

	@Autowired
	private CarPreOfflineMapper preOfflineMapper ;

	@Autowired
	private TboxImeiMapper tboxImeiMapper ;

	@Autowired
	private SysParamMapper sysParamMapper;

	@Autowired
	private CarOfflineApplyMapper applyMapper;

	@Autowired
    private EnterpriseMapper enterpriseMapper;
	
	@Transactional(rollbackFor=Exception.class)
	public int updateCarPreOfflineStatus(CarRun carRun,CarPreOffline offline){
		int count = 0;
		if(null!=carRun){
			count = count + carRunMapper.updateByPrimaryKeySelective(carRun);
		}
        if(null!=offline){
    		if("1".equals(carRun.getPreOffline())){
    			count = count + preOfflineMapper.insertSelective(offline);
    		}else{
    			count = count + preOfflineMapper.updateByPrimaryKeySelective(offline);
    		}
        }
        return count;
	}

	public int deleteByPrimaryKey(Integer id) {
		return carMapper.deleteByPrimaryKey(id);
	}

	public int insert(Car record) {
		return carMapper.insert(record);
	}

	public int insertSelective(Car record) {
		return carMapper.insertSelective(record);
	}

	public Car selectByPrimaryKey(Integer id) {
		return carMapper.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKeySelective(Car record) {
		CarRun run = carRunMapper.queryCarRun(record.getLpn().replace("•", "")) ;
		run.setCityCode(record.getCityCode()) ;
		carRunMapper.updateByPrimaryKey(run) ;
		record.setLpn(record.getLpn().replace("•", ""));
		return carMapper.updateByPrimaryKeySelective(record);
	}

	public int updateByPrimaryKey(Car record) {
		return carMapper.updateByPrimaryKey(record);
	}

	public List<CarMrgVo> selectAllCarMrg(Map<String, Object> map) {
		return carMapper.selectAllCarMrg(map);
	}

	public int selectAllCarMrgRecords(Map<String, Object> map) {
		return carMapper.selectAllCarMrgRecords(map);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int deleteCarInfo(int id){
		Car car = carMapper.selectByPrimaryKey(id);
		//根据车牌号码删除x_car_run中的信息
		int r1 = carRunMapper.deleteByLpn(car.getLpn());
		//删除车辆基本信息
		int r2 = carMapper.deleteByPrimaryKey(id);
		if(r1 >= 0 && r2 > 0){
			return 1;
		}else{
			return -1;
		}
	}
	
	@Transactional(rollbackFor=Exception.class)
	public synchronized int saveCarInfo(Car car,  Integer parkId){
		final String lpn = car.getLpn();
		// 查询车辆信息是否存在
		CarRun carRun = carRunMapper.getCarInfo(lpn);
		if (carRun != null){
			return -1;
		}
		carRun = new CarRun();
		carRun.setLpn(lpn);
		carRun.setStatus("empty");
		carRun.setSpeed(car.getSpeed());
		carRun.setCityCode(car.getCityCode());
		if(parkId != null){
			//查询出网点信息
			Park park = parkMapper.selectByPrimaryKey(parkId);
			carRun.setParkId(parkId);
			carRun.setLatitude(park.getLatitude());
			carRun.setLongitude(park.getLongitude());
		}
		//保存car run 信息
		int r2 = carRunMapper.insertSelective(carRun);
		//save car info
		car.setBrandName(carTypeMapper.selectByPrimaryKey(car.getModelId()).getBrance()) ;
		int r3 = carMapper.insertSelective(car);
		if(r2 >0 && r3>0){
			return 1;
		}else{
			return -1;
		}
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int editCarInfo(Car car, Integer parkId){
//		CarRun carRun = new CarRun();
//		carRun.setLpn(car.getLpn());
//		//carRun.setStatus("empty");
//		carRun.setSpeed(car.getSpeed());
//		carRun.setCityCode(car.getCityCode());
//		if(parkId != null){
//			//查询出网点信息
//			Park park = parkMapper.selectByPrimaryKey(parkId);
//			carRun.setLatitude(park.getLatitude());
//			carRun.setLongitude(park.getLongitude());
//			carRun.setParkId(parkId);
//		}
//		//插入的时候，先删除信息
//		int r1 = carRunMapper.deleteByLpn(car.getLpn());
//		//保存car run 信息
//		int r2 = carRunMapper.insertSelective(carRun);
//		int r3 = carMapper.updateByPrimaryKeySelective(car);
//		if(r1 >=0 && r2 >0 && r3>0){
//			return 1;
//		}else{
//			return -1;
//		}
		return carMapper.updateByPrimaryKeySelective(car);
	}
	
	public Pager<CarVersionVo> queryCarVersion(Map param ) {
		List<CarVersionVo> carVersionList = carMapper.queryCarVersion(param) ;
		Pager< CarVersionVo> pager = new Pager< CarVersionVo>();
		pager.setDatas(carVersionList);
		pager.setTotalCount(carMapper.queryCarCount(param));
		return pager ;
	}
	
	public List<CarUpgradeVo> queryCarBoxUpgradeVo(Map<String,Object> map){
    	return carMapper.queryCarBoxUpgradeVo(map) ;
    }
    
	public List<CarUpgradeVo> queryCarRearviewUpgradeVo(Map<String,Object> map) {
		return carMapper.queryCarRearviewUpgradeVo(map) ;
	}
	
	public Car selectByLpn(String lpn){
		return carMapper.selectByLpn(lpn) ; 
	}

	public List<Car> selectAllPushCar(HashMap<String, Object> map) {
		return carMapper.selectAllPushCar(map);
	}
	
	public List<CarBrand> getBrandNameList(){
		return carBrandMapper.getBrandNameList();
	}
	public CarRun queryCarRunInfo(String lpn){
	    return carRunMapper.queryCarRun(lpn);	
	}
	public Set<String> selectAllCarLpns(){
		return carMapper.selectAllCarLpns();
	}

	/**
	 * 查询所有未绑定的imei
	 * lf
	 * 2017年11月13日
	 */
	public List<Map<String, String>> selectNotBindImeis() {
		return tboxImeiMapper.selectAll();
	}

	/**
	 * 绑定tboximei
	 * lf
	 * 2017年11月13日
	 * @param lpn 车牌
	 * @param imei imei
	 * @return
	 */
    public synchronized Map<String,Object> bindTboxImei(String lpn, String imei) {
    	// 查看车辆是否是三代产品
		final Car car = carMapper.selectByLpn(lpn);
		if (car == null){
			return CommonUtil.fail("车牌号不存在！");
		}
		final String tboxImei = car.getTboxImei();
		final Integer tboxVersion = car.getTboxVersion();
		if (tboxVersion == null || tboxVersion != 3 ){
			// 不是三代产品车辆
			return CommonUtil.fail("不是三代产品车辆!");
		}

		if (StringUtils.isNotBlank(tboxImei)){
			return CommonUtil.fail("车辆已经绑定imei！");
		}

		// 调用接口
		SysParam sysParam = sysParamMapper.selectByKey("http_url_tbox") ;
		JSONObject object = new JSONObject();
		object.put("imei",imei);
		object.put("lpn",lpn);

		Map<String,Object> params = Maps.newHashMap();
		params.put("method","bindLpn");
		params.put("data",object.toString().replaceAll("\"","'"));
		final String url = sysParam.getValue();
		return CommonUtil.httpInterfacePost(url, params);
    }

	/**
	 * tbox解除绑定
	 * lf
	 * 2017年11月14日
	 * @param lpn 车牌
	 * @param imei imei
	 * @return
	 */
	public synchronized Map<String,Object> unBindTboxImei(String lpn, String imei) {
		// 调用接口
		SysParam sysParam = sysParamMapper.selectByKey("http_url_tbox") ;
		JSONObject object = new JSONObject();
		object.put("imei",imei);
		object.put("lpn",lpn);

		Map<String,Object> params = Maps.newHashMap();
		params.put("method","unBindLpn");
		params.put("data",object.toString());
		final String url = sysParam.getValue();
		return CommonUtil.httpInterfacePost(url, params);
	}

    /**
     * 根据enterpriseID查询车辆信息
     * @param enterpriseID
     * @return
     */
	public Pager<CarMrgVo> getCarListByEnterpriseID(Map<String,Object> param){
       List<CarMrgVo> carMrgVoList=  carMapper.getCayListByEnterpriseID(param);
       Pager<CarMrgVo> pager = new Pager<CarMrgVo>();
        pager.setDatas(carMrgVoList);
        pager.setTotalCount(carMapper.getCarListByEnterpriseIDNumber(param));
        return pager;

    }
    /**
     * 查询没有关联企业的车辆
     * @return
     */
    public Pager<Car> getUnusedEnterpriseCarsList(Map<String,Object> map) {
        List<Car> cars = carMapper.getUnusedEnterpriseCarsList(map);
        Pager<Car> pager = new Pager<Car>();
        pager.setDatas(cars);
        pager.setTotalCount(carMapper.getUnsedEnterpriseCarsTotal());
        return pager;
    }

    /**
     * 添加关联企业的车辆
     * @param param
     * @return
     */
    public  int addEnterpriseCar(Map<String, Integer> param) {
        return carMapper.addEnterpriseCar(param);
    }

    public Map<String,Object> enterpriseCarRelationMap(String enterpriseId,String carId){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("enterpriseId", enterpriseId);
        map.put("carId", carId);
        return carMapper.getEnterpriseCarRelationMap(map);
    }


    /**
     * 删除企业所属的车辆
     *
     * @param id
     * @return
     */
    public int removeEnterpriseCar(int id) {
        return carMapper.removeEnterpriseCar(id);
    }

    /**
     * 批量删除企业所属的车辆
     *
     * @param idList
     * @return
     */
    public int batchRemoveEnterpriseCar(String[] idList,String enterpriseID) {
        int num = carMapper.batchRemoveEnterpriseCar(idList);
        List<Map<String,Object> >map = carMapper.selectEnterpriseCarRelations(Integer.parseInt(enterpriseID));
        /*for (String id : idList) {
            Car car
        }*/
        if(CollectionUtils.isEmpty(map)){
            Enterprise enterprise = enterpriseMapper.selectByPrimaryKey(Integer.parseInt(enterpriseID));
            enterprise.setBindCars(false);
            enterpriseMapper.updateByPrimaryKeySelective(enterprise);
        }

        return num;
    }

    public List<Map<String, Object>> getcarRelaitonList(Integer carid) {
        return carMapper.selectEnterpriseCarRelationsByCarID(carid);
    }


}

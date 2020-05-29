package com.iber.portal.controller.sys;
import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.controller.MainController;
import com.iber.portal.dao.sys.SysParamMapper;
import com.iber.portal.model.sys.SysParam;
import com.iber.portal.util.SendSMS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SysParamController extends MainController{


	@Autowired
	private SysParamMapper sysParamMapper;

	@SystemServiceLog(description="车辆类型")
	@RequestMapping(value = "/sys_run_param", method = { RequestMethod.GET })
	public String carType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "system/sysRunParam";
	}

	@SystemServiceLog(description="获取短信运行参数")
	@RequestMapping(value = "/sys_param_init", method = { RequestMethod.GET,RequestMethod.POST })
	public String sysParamInit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SysParam sysParam = sysParamMapper.selectByKey("sms_param_key");
		response.getWriter().print(sysParam.getValue());
		return null;
	}

	@SystemServiceLog(description="保存短信运行参数")
	@RequestMapping(value = "/sys_param_save", method = { RequestMethod.GET,RequestMethod.POST })
	public String sysParamSave(String value,HttpServletRequest request, HttpServletResponse response) throws Exception {
		SysParam sysParam = sysParamMapper.selectByKey("sms_param_key");
		sysParam.setValue(value);
		sysParamMapper.updateByPrimaryKeySelective(sysParam);
		response.getWriter().print("ok");
		return null;
	}

	@SystemServiceLog(description="发送测试短信")
	@RequestMapping(value = "/sys_param_send", method = { RequestMethod.GET,RequestMethod.POST })
	public String sysParamSend(String phone,HttpServletRequest request, HttpServletResponse response) throws Exception {
		SendSMS.send(phone,"",3001,"");
		response.getWriter().print("ok");
		return null;
	}

}

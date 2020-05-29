package com.iber.portal.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.JsonDateValueProcessor;
import com.iber.portal.common.MD5;
import com.iber.portal.common.Pager;
import com.iber.portal.dao.sys.SysRoleMapper;
import com.iber.portal.dao.sys.SysUserMapper;
import com.iber.portal.model.base.City;
import com.iber.portal.model.base.MemberLevel;
import com.iber.portal.model.car.CarType;
import com.iber.portal.model.sys.*;
import com.iber.portal.service.base.CityService;
import com.iber.portal.service.car.CarTypeService;
import com.iber.portal.service.sys.*;
import com.iber.portal.util.BatchSetterUtil;
import com.iber.portal.vo.city.CityVo;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

@Controller
public class SystemController extends MainController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SysButtonService sysButtonService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysModuService sysModuService;

    @Autowired
    private SysDicService sysDicService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysRoleModuService sysRoleModuService;

    @Autowired
    private SysUserModuService sysUserModuService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysDicTypeService sysDicTypeService;

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private CityService cityService;

    @Autowired
    private CarTypeService carTypeService;

    @Autowired
    private SysOperateLogService sysOperateLogService;


    @SystemServiceLog(description = "修改密码")
    @RequestMapping(value = "/sys_modify_pwd", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> modifyPwd(int uid, String oldPwd, String newPwd, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
//        response.setContentType("text/html;charset=utf-8");
        SysUser sysUser = sysUserService.getSysUserByIdAndPassword(uid, oldPwd);
        if (sysUser == null) {
            return fail("原密码错误");
        }
        sysUser.setPassword(MD5.toMD5(newPwd));
        sysUser.setPasswordUpdateTime(new Date());
        int num = sysUserService.updateByPrimaryKeySelective(sysUser);
        if (num > 0) {
            return success();
        }else {
            return fail("操作失败，请刷新重试");
        }



       /* if (!getUser(request).getPassword().equals(MD5.toMD5(oldPwd))) {
            response.getWriter().print("oldFail");
        } else {
            int r = sysUserMapper.updatePwd(uid, MD5.toMD5(newPwd));
            if (r > 0) {
                response.getWriter().print("succ");
            } else {
                response.getWriter().print("fail");
            }
        }*/

    }

    @SystemServiceLog(description = "冻结账户")
    @RequestMapping(value = "/frozen_account", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> frozenAccount(HttpServletRequest request, HttpServletResponse response) {
        SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
        SysUser sysUserUpdate = new SysUser();
        sysUserUpdate.setId(sysUser.getId());
        sysUserUpdate.setStatus("0");
        int num = sysUserService.updateByPrimaryKeySelective(sysUserUpdate);
        if (num > 0) {
            return success();
        }else {
            return fail("操作失败，请刷新重试");
        }
    }

    /**
     * @throws Exception
     * @describe 获取字典类型
     * @auther yangguiwu
     * @date 2016年3月28日
     */
    @SystemServiceLog(description = "获取字典类型")
    @RequestMapping(value = "/sys_dic", method = {RequestMethod.GET, RequestMethod.POST})
    public String sysDictType(String dicCode, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html;charset=utf-8");

        List<SysDic> dictTypeList = sysDicService.selectListByDicCode(dicCode);
        JSONArray dictTypeListJson = JSONArray.fromObject(dictTypeList);
        response.getWriter().print(dictTypeListJson.toString());
        return null;
    }

    @SystemServiceLog(description = "获取字典类型页")
    @RequestMapping(value = "/sys_dic_page", method = {RequestMethod.GET})
    public String sysDic(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return "system/dic";
    }

    @SystemServiceLog(description = "字典树")
    @RequestMapping(value = "/sys_dic_tree", method = {RequestMethod.GET, RequestMethod.POST})
    public String sysDicTree(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        response.setContentType("text/html;charset=utf-8");

        try {
            // 转换数据
            List<SysDicType> dictionariesList = sysDicService.selectDicTypeAll();
            StringBuffer json = new StringBuffer("");
            json.append("[{");
            json.append("\"id\":\"-1\",");
            json.append("\"text\":\"字典\",");
            json.append("\"children\":[");
            for (int i = 0; i < dictionariesList.size(); i++) {
                json.append("{");
                json.append("\"id\":" + dictionariesList.get(i).getId() + ",");
                json.append("\"text\":\"" + dictionariesList.get(i).getName() + "[" + dictionariesList.get(i).getCode() + "]\"");
                if (i < dictionariesList.size() - 1) {
                    json.append("},");
                } else {
                    json.append("}");
                }
            }
            json.append("]");
            json.append(" }]");
            response.getWriter().print(json.toString().trim());
        } catch (Exception e) {
            e.printStackTrace();
            StringBuffer bf1 = new StringBuffer();
            int recc = 0;
            bf1.append("{\"total\":").append(recc + ",");
            bf1.append("\"rows\":[]}");
            response.getWriter().print(bf1.toString());
        }

        return null;
    }

    @SystemServiceLog(description = "字典列表")
    @RequestMapping(value = "/sys_dic_list", method = {RequestMethod.GET, RequestMethod.POST})
    public String sysDicList(Integer dicId, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html;charset=utf-8");
        int dicIdInt = 0;
        if (dicId != null) {
            dicIdInt = dicId.intValue();
        }
        List<SysDic> list = sysDicService.getAll(dicIdInt);

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        StringBuffer bf = new StringBuffer();
        JSONArray json = JSONArray.fromObject(list, jsonConfig);
        bf.append("{\"total\":").append(list.size() + ",");
        bf.append("\"rows\":");
        bf.append(json.toString());
        bf.append("}");

        response.getWriter().print(bf.toString());
        return null;
    }

    @SystemServiceLog(description = "增加或修改字典")
    @RequestMapping(value = "/sys_dic_saveOrUpdate", method = {RequestMethod.GET, RequestMethod.POST})
    public String sysDicSaveOrUpdate(SysDic sysDic, String moduIds, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        response.setContentType("text/html;charset=utf-8");
        Map<String, Object> map = new HashMap<String, Object>();
        SysDicType dic = sysDicTypeService.selectByPrimaryKey(sysDic.getDicId());
        map.put("dicCode", dic.getCode());
        map.put("itemCode", sysDic.getCode());
        map.put("activityName", sysDic.getName());
        if (sysDic.getId() == null) {
            SysDic sDic = sysDicService.selectByCode(map);
            if (null != sDic) {
                response.getWriter().print("noCheck");
                return null;
            } else {
                sDic = sysDicService.selectByName(map);
                if (null != sDic) {
                    response.getWriter().print("noCheck");
                    return null;
                }
            }
            sysDic.setCreateTime(new Date());
            sysDic.setCreateUser(getUser(request).getAccount());
            sysDic.setDicCode(dic.getCode());
            sysDicService.insertSelective(sysDic);
        } else {
            map.put("id", sysDic.getId());
            Integer count = new Integer(0);
            count = sysDicService.selectRecordsByCode(map);
            if (0 < count) {
                response.getWriter().print("noCheck");
                return null;
            } else {
                count = sysDicService.selectRecordsByName(map);
                if (0 < count) {
                    response.getWriter().print("noCheck");
                    return null;
                }
            }
            sysDicService.updateByPrimaryKeySelective(sysDic);
        }
        response.getWriter().print("succ");
        return null;
    }

    @SystemServiceLog(description = "删除字典")
    @RequestMapping(value = "/sys_dic_del", method = {RequestMethod.GET, RequestMethod.POST})
    public String sysDicDel(Integer id, String moduIds, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        response.setContentType("text/html;charset=utf-8");

        sysDicService.deleteByPrimaryKey(id);

        response.getWriter().print("succ");

        return null;
    }

    @SystemServiceLog(description = "按钮管理")
    @RequestMapping(value = "/sys_button", method = {RequestMethod.GET})
    public String sysButton(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "system/buttonlist";
    }

    @SystemServiceLog(description = "更改密码页")
    @RequestMapping(value = "/modify_pwd_page", method = {RequestMethod.GET})
    public String modifyPwdPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "system/mofiyPwd";
    }

    @SystemServiceLog(description = "所属城市")
    @RequestMapping(value = "/sys_cityCombobox", method = {RequestMethod.GET, RequestMethod.POST})
    public String sysCityCombobox(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html;charset=utf-8");
        HttpSession sess = request.getSession();
        List<City> cityList = (List<City>) sess.getAttribute("city");
        if (null == cityList) {
            cityList = sysUserService.selectAllCity();
        }
        StringBuffer tree = new StringBuffer();
        tree.append("[");
        if (cityList != null && cityList.size() > 0) {
            for (int i = 0; i < cityList.size(); i++) {
                City city = cityList.get(i);
                tree.append("{");
                tree.append("\"id\":\"" + city.getCode() + "\",");
                tree.append("\"text\":\"" + city.getName() + "\"");
                if (i < cityList.size() - 1) {
                    tree.append("},");
                } else {
                    tree.append("}");
                }
            }
        }
        tree.append("]");

        response.getWriter().print(tree.toString());
        return null;
    }

    @SystemServiceLog(description = "所属地区")
    @RequestMapping(value = "/sys_areaCodeCombobox", method = {RequestMethod.GET, RequestMethod.POST})
    public String sys_areaCodeCombobox(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html;charset=utf-8");
        String areaCode = request.getParameter("areaCode");
        List<CityVo> cityList = cityService.queryCityAreaByCode(areaCode);

        StringBuffer tree = new StringBuffer();
        tree.append("[");
        if (cityList != null && cityList.size() > 0) {
            for (int i = 0; i < cityList.size(); i++) {
                CityVo city = cityList.get(i);
                tree.append("{");
                tree.append("\"id\":\"" + city.getCode() + "\",");
                tree.append("\"text\":\"" + city.getName() + "\"");
                if (i < cityList.size() - 1) {
                    tree.append("},");
                } else {
                    tree.append("}");
                }
            }
        }
        tree.append("]");

        response.getWriter().print(tree.toString());
        return null;
    }

    @SystemServiceLog(description = "按钮列表")
    @RequestMapping(value = "/sys_button_list", method = {RequestMethod.GET, RequestMethod.POST})
    public String sysButtonList(int page, int rows, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html;charset=utf-8");
        Map<String, Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
        Pager<SysButton> pager = sysButtonService.getAll(pageInfo.get("first_page"), pageInfo.get("page_size"));
        response.getWriter().print(Data2Jsp.Json2Jsp(pager));
        return null;
    }

    @SystemServiceLog(description = "按钮数据明细")
    @RequestMapping(value = "/sys_button_allDataGrid", method = {RequestMethod.GET, RequestMethod.POST})
    public String sysButtonAllDataGrid(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html;charset=utf-8");

        List<SysButton> btnList = sysButtonService.getAllButton();

        JSONObject obj = new JSONObject();
        obj.put("total", sysButtonService.getAllNum());
        obj.put("rows", btnList);

        response.getWriter().print(obj.toString());
        return null;
    }

    @SystemServiceLog(description = "更新或添加按钮数据")
    @RequestMapping(value = "/sys_button_saveOrUpdate", method = {RequestMethod.GET, RequestMethod.POST})
    public String sysButtonSaveOrUpdate(String id, String name, String code, String img, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        response.setContentType("text/html;charset=utf-8");

        if (id != null && !id.equals("")) {

            SysButton currBtnObj = sysButtonService.selectByPrimaryKey(Integer.parseInt(id));
            if (currBtnObj != null) {
                currBtnObj.setCode(code);
                currBtnObj.setName(name);
                currBtnObj.setImg(img);
                sysButtonService.updateByPrimaryKeySelective(currBtnObj);
            }

        } else {
            SysButton btnObj = new SysButton();
            btnObj.setName(name);
            btnObj.setCode(code);
            btnObj.setImg(img);
            sysButtonService.insertSelective(btnObj);
        }

        response.getWriter().print("succ");
        return null;
    }

    @SystemServiceLog(description = "删除按钮")
    @RequestMapping(value = "/sys_button_del", method = {RequestMethod.GET, RequestMethod.POST})
    public String sysButtonDel(String id, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        response.setContentType("text/html;charset=utf-8");

        if (id != null && !id.equals("")) {
            sysButtonService.deleteByPrimaryKey(Integer.parseInt(id));
        }

        response.getWriter().print("succ");
        return null;
    }

    @SystemServiceLog(description = "用户登陆")
    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public String login(String vcode, String username, String pwd, HttpServletRequest request, HttpServletResponse response) {
        String returnVal = "index";
        HttpSession sess = request.getSession();
        SysUser user = (SysUser) sess.getAttribute("user");
        if(null == user) {
            String certCode = "";

            try {
                certCode = (String) sess.getAttribute("certCode");
            } catch (Exception e) {
                e.printStackTrace();
            }

            request.setAttribute("username", username);
            request.setAttribute("pwd", pwd);
            if (certCode == null) {
                return returnVal;
            }
            if (!certCode.equalsIgnoreCase(vcode)) {
                request.setAttribute("error", "验证码错误！");
            } else {

                SysUser userObj = sysUserService.selectByAccount(username);

                if (userObj != null) {
                    if (userObj.getEnterpriseId() != null && userObj.getEnterpriseId() != 0) {
                        request.setAttribute("error", "该账户没有运营管理平台权限");
                        return returnVal;
                    }
                    //增加用户状态判断是否为启用
                    if (!userObj.getStatus().equals("1")) {
                        request.setAttribute("error", "用户未启用请联系管理员！");
                    } else {
                        if (MD5.toMD5(pwd).equals(userObj.getPassword())) {

                            sess.setAttribute("login_status", "success");
                            sess.setAttribute("user", userObj);

                            //根据用户id获得用户拥有的角色id
                            List<SysUserModu> sysUserModus = sysUserModuService.selectRoleIdsByUserId(userObj.getId());
                            List<Integer> roleIds = new ArrayList<Integer>();
                            for (SysUserModu sysUserModu : sysUserModus) {
                                if (sysUserModu != null) {
                                    roleIds.add(sysUserModu.getSysUserRole().getRoleId());
                                }
                            }
                            //根据用户id和roleid获得用户拥有的功能
                            List<String> funcList = null;
                            try {
                                funcList = sysUserModuService.selectFunctionByUserId(userObj.getId(), roleIds);
                            } catch (Exception e) {
                                log.error("获取功能权限异常==" + e.getMessage());
                            }

                            List<String> roleFunctions = new ArrayList<String>();
                            if (roleIds.size() > 0) {
                                roleFunctions = sysRoleModuService.selectFuntionByRoleIds(roleIds);
                            }
                            //根据roleId获得角色的所有功能
                            sess.setAttribute("roleFunctions", roleFunctions);
                            sess.setAttribute("funcList", funcList);
                            //query user modu
                            if (userObj.getAccount().equalsIgnoreCase("admin") || userObj.getAccount().equalsIgnoreCase("system")) {
                                sess.setAttribute("moduData", sysModuService.getAllNot());
                            } else {
                                sess.setAttribute("moduData", sysModuService.selectUserModuByUserId(userObj.getId()));
                            }

                            //存放city
                            List<City> cityList = sysUserService.selectAllCity();
                            sess.setAttribute("city", cityList);

                            City city = getCity(request, userObj.getCityCode());
                            sess.setAttribute("latitude", city.getLatitude());
                            sess.setAttribute("longitude", city.getLongitude());

                            //存放用户角色信息
                            List<SysRole> roleData = sysRoleMapper.selectByUserId(userObj.getId());
                            sess.setAttribute("roleData", roleData);
                            StringBuffer roleDataStr = new StringBuffer();
                            if (roleData != null && !roleData.isEmpty()) {
                                if (userObj.getAccount().equalsIgnoreCase("admin") || userObj.getAccount().equalsIgnoreCase("system")) {
                                    sess.setAttribute("roleName_1", "超级管理员");
                                } else {
                                    sess.setAttribute("roleName_1", roleData.get(0).getName());
                                }
                                for (int i = 0; i < roleData.size() - 1; i++) {
                                    roleDataStr.append(roleData.get(i).getId()).append(",");
                                }
                                roleDataStr.append(roleData.get(roleData.size() - 1).getId());
                            }
                            sess.setAttribute("roleDataStr", roleDataStr.toString());
                            returnVal = "main";

                            //存放sessionChange
                            String uuid = UUID.randomUUID().toString(); //获取UUID并转化为String对象
                            uuid = uuid.replace("-", ""); //因为UUID本身为32位只是生成时多了“-”，所以将它们去点就可
                            SysUser sysUser = new SysUser();
                            sysUser.setSessionChange(uuid);
                            sysUser.setId(userObj.getId());
                            sess.setAttribute("sessionChange", sysUser.getSessionChange());
                            sysUserService.updateByPrimaryKeySelective(sysUser);
                        } else {
                            request.setAttribute("error", "密码不正确！");
                        }
                    }

                } else {
                    request.setAttribute("error", "用户不存在！");
                }

            }
        }else{
            String sessionChange = (String) sess.getAttribute("sessionChange");
            SysUser userDb = sysUserMapper.selectByPrimaryKey(user.getId());
            if(!sessionChange.equals(userDb.getSessionChange())){
                request.setAttribute("error", "该账号已在其他地方登录，您被迫下线");
            }else{
                returnVal = "main";
            }
        }
        return returnVal;
    }

    public static void main(String[] args) {
        System.out.println(MD5.toMD5("admin"));
    }

    @SystemServiceLog(description = "用户退出")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        SysUser sysUser = (SysUser) session.getAttribute("user");
        if (sysUser != null) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("userId", sysUser.getId());
            map.put("name", sysUser.getName());
            map.put("ip", request.getRemoteAddr());
            map.put("inParam", "");
            map.put("methodDescribe", "用户退出");
            map.put("methodName", "logout");
            map.put("createTime", System.currentTimeMillis());
            // 保存操作日志
            sysOperateLogService.savePlatformLog(map);
        }
        session.invalidate();
        return "index";
    }

    @SystemServiceLog(description = "用户角色")
    @RequestMapping(value = "/sys_role", method = {RequestMethod.GET})
    public String sysRole(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return "system/role";
    }

    @SystemServiceLog(description = "用户角色列表")
    @RequestMapping(value = "/sys_role_list", method = {RequestMethod.GET, RequestMethod.POST})
    public String sysRoleList(int page, int rows, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html;charset=utf-8");

        Map<String, Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
        Pager<SysRole> pager = sysRoleService.getAll(pageInfo.get("first_page"), pageInfo.get("page_size"));

        response.getWriter().print(Data2Jsp.Json2Jsp(pager));
        return null;
    }

    @SystemServiceLog(description = "角色树")
    @RequestMapping(value = "/sys_role_buildRoleTree", method = {RequestMethod.GET, RequestMethod.POST})
    public String sysRoleBuildRoleTree(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html;charset=utf-8");

        StringBuffer tree = new StringBuffer();
        try {
            List<SysRole> roleList = sysRoleService.getAllRole();
            tree.append("[");
            if (roleList != null && roleList.size() > 0) {
                for (int i = 0; i < roleList.size(); i++) {
                    SysRole role = roleList.get(i);
                    tree.append("{");
                    tree.append("\"id\":" + role.getId() + ",");
                    tree.append("\"text\":\"" + role.getName() + "\"");
                    if (i < roleList.size() - 1) {
                        tree.append("},");
                    } else {
                        tree.append("}");
                    }
                }
            }
            tree.append("]");
            response.getWriter().print(tree.toString());
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().print("[{}]");
        }

        return null;
    }
    @SystemServiceLog(description = "企业用户管理角色树")
    @RequestMapping(value = "/enterprise_sys_role_buildRoleTree", method = {RequestMethod.GET, RequestMethod.POST})
    public String enterpriseSysRoleBuildRoleTree(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html;charset=utf-8");

        StringBuffer tree = new StringBuffer();
        try {
            List<SysRole> roleList = sysRoleService.getEnterpriseAllRole();
            tree.append("[");
            if (roleList != null && roleList.size() > 0) {
                for (int i = 0; i < roleList.size(); i++) {
                    SysRole role = roleList.get(i);
                    tree.append("{");
                    tree.append("\"id\":" + role.getId() + ",");
                    tree.append("\"text\":\"" + role.getName() + "\"");
                    if (i < roleList.size() - 1) {
                        tree.append("},");
                    } else {
                        tree.append("}");
                    }
                }
            }
            tree.append("]");
            response.getWriter().print(tree.toString());
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().print("[{}]");
        }

        return null;
    }

    @SystemServiceLog(description = "新增或修改角色")
    @RequestMapping(value = "/sys_role_saveOrUpdate", method = {RequestMethod.GET, RequestMethod.POST})
    public String sysRoleSaveOrUpdate(SysRole roleObj, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        response.setContentType("text/html;charset=utf-8");

//        if (roleObj.getId() != null && !roleObj.getId().equals("")) {
        if (null != roleObj.getId()) {
            roleObj.setUpdateTime(new Date());
            sysRoleService.updateByPrimaryKeySelective(roleObj);
        } else {
            roleObj.setCreateTime(new Date());
            sysRoleService.insertSelective(roleObj);
        }

        response.getWriter().print("succ");
        return null;
    }

    @SystemServiceLog(description = "删除角色")
    @RequestMapping(value = "/sys_role_del", method = {RequestMethod.GET, RequestMethod.POST})
    public String sysRoleDel(String id, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        response.setContentType("text/html;charset=utf-8");

        if (id != null && !id.equals("")) {
            sysRoleService.deleteByPrimaryKey(Integer.parseInt(id));
        }

        response.getWriter().print("succ");
        return null;
    }

    @SystemServiceLog(description = "分配角色")
    @RequestMapping(value = "/sys_modu_checkRoleModu", method = {RequestMethod.GET, RequestMethod.POST})
    public String sysModuCheckRoleModu(int roleId, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        response.setContentType("text/html;charset=utf-8");
        StringBuffer bf = new StringBuffer();
        try {
            bf.append("[");
            List<SysRoleModu> list = sysRoleModuService.selectByRoleId(roleId);
            //获得角色对应的功能
            List<String> values = new ArrayList<String>();
            for (SysRoleModu sysRoleModu : list) {
                if (sysRoleModu.getFunction() != null && !"".equals(sysRoleModu.getFunction())) {
                    JSONArray jsonArray = JSONArray.fromObject(sysRoleModu.getFunction());
                    List<Map<String, String>> mapListJson = (List) jsonArray;
                    for (Map<String, String> map : mapListJson) {
                        Set<String> keySet = map.keySet();
                        for (String str : keySet) {
                            values.add(map.get(str));
                        }
                    }
                }
            }
            List<SysModu> sysModus = null;
            if (0 < values.size()) {
                sysModus = sysModuService.selectModuByLinks(values);
            }
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    SysRoleModu sysRoleModu = list.get(i);
                    bf.append("{");
                    bf.append("\"modu_id\":" + sysRoleModu.getModuId());
                    if (i <= list.size() - 1) {
                        bf.append("},");
                    } /*else {
                        bf.append("}");
					}*/
                }
            }
            if (null != sysModus && sysModus.size() > 0) {
                for (int i = 0; i < sysModus.size(); i++) {
                    SysModu sysModu = sysModus.get(i);
                    bf.append("{");
                    bf.append("\"modu_id\":" + sysModu.getId());
                    if (i < sysModus.size() - 1) {
                        bf.append("},");
                    } else {
                        bf.append("}");
                    }
                }
            }
            bf.append("]");
            response.getWriter().print(bf.toString());
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().print("[{}]");
        }

        return null;
    }

    /**
     * 检查用户的单个权限
     *
     * @param request
     * @param permission
     * @return
     */
    public boolean queryUserPermission(HttpServletRequest request, String permission) {
        //获取登录用户信息
        SysUser user = (SysUser) request.getSession().getAttribute("user"); //获取用户登陆信息
        if (user == null) { //如果获取不不到用户的登录信息
            return false;
        }
        List<String> funcList = (List<String>) request.getSession().getAttribute("funcList");
        Set<String> functionSet = new HashSet<String>();
        //判断用户是否有操作上的功能
        if (funcList != null && funcList.size() > 0) {
            String[] functionStrings = funcList.toString().split(",");
            for (String funString : functionStrings) {
                String[] splitStrings = funString.split(":");
                if (splitStrings.length == 2) {
                    functionSet.add(splitStrings[1].substring(0, splitStrings[1].indexOf("}")));
                }
            }
        }

        List<String> roleFunctions = (List<String>) request.getSession().getAttribute("roleFunctions");
        //判断用户是否有角色
        if (roleFunctions.size() > 0) {
            String[] splitRoleFunction = roleFunctions.toString().split(",");
            for (String roleFunction : splitRoleFunction) {
                String[] splitStrings = roleFunction.split(":");
                if (splitStrings.length == 2) {
                    functionSet.add(splitStrings[1].substring(0, splitStrings[1].indexOf("}")));
                }

            }
        }

        boolean result = false;
        for (String function : functionSet) {
            if (function.equals("\"" + permission + "\"")) {
                result = true;
            }
        }
        return result;
    }

    @SystemServiceLog(description = "保存角色")
    @RequestMapping(value = "/sys_role_saveRoleModu", method = {RequestMethod.GET, RequestMethod.POST})
    public String saveRoleModu(String moduIds, int roleIds, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html;charset=utf-8");
        if (!queryUserPermission(request, "assign_right")) {
            response.getWriter().print("error");
        }
        String isShows = request.getParameter("is_shows");
        String[] isShowsArr = isShows.split(",");
        //获得前台传过来的pid
        String pids = request.getParameter("pids");
        int[] pidsIntArr = null;
        if (pids != null && !"".equals(pids)) {
            String[] pidArr = pids.split(",");
            pidsIntArr = new int[pidArr.length];
            for (int i = 0; i < pidsIntArr.length; i++) {
                pidsIntArr[i] = Integer.valueOf(pidArr[i]);
            }
        }
        //获得前台传过来的names
        String names = request.getParameter("names");
        String[] namesArr = names.split(",");
        //循环所有的pid
        Set<Integer> pidSet = new HashSet<Integer>();
        if (pidsIntArr != null) {
            for (int i = 0; i < pidsIntArr.length; i++) {
                //如果该pid对应的is_show为2,说明是功能菜单
                if (isShowsArr[i].equals("2")) {
                    //获得对应的pid
                    pidSet.add(pidsIntArr[i]);
                }
            }
        }

        //将set集合转换为list集合
        List<Integer> pidList = new ArrayList<Integer>(pidSet);
        List<Map<Integer, String>> lists = new ArrayList<Map<Integer, String>>();
        //遍历这个pidSet
        for (int i = 0; i < pidList.size(); i++) {
            Map<Integer, String> map = new HashMap<Integer, String>();
            //创建一个list集合
            List<Map<String, Object>> functions = new ArrayList<Map<String, Object>>();
            for (int j = 0; j < pidsIntArr.length; j++) {
                if (pidList.get(i).equals(pidsIntArr[j])) {
                    Map<String, Object> function = new HashMap<String, Object>();
                    String name = namesArr[j];
                    //根据name和pid查询对应的菜单sys_modu
                    SysModu sysModu = sysModuService.selectByNameAndPid(pidList.get(i), name);
                    function.put(sysModu.getName(), sysModu.getLink());
                    functions.add(function);
                }
            }
            String functionStr = JSON.toJSONString(functions);
            map.put(pidList.get(i), functionStr);
            lists.add(map);
        }

        List<Integer> moduIdList = new ArrayList<Integer>();
        if (moduIds != null && !"".equals(moduIds)) {
            String[] moduIdsArr = moduIds.split(",");
            int[] moduIdsIntArr = new int[moduIdsArr.length];
            for (int i = 0; i < moduIdsArr.length; i++) {
                moduIdsIntArr[i] = Integer.parseInt(moduIdsArr[i]);
            }
            //获得除了is_show为2的所有moduId
            for (int i = 0; i < moduIdsIntArr.length; i++) {
                if (!isShowsArr[i].equals("2")) {
                    moduIdList.add(moduIdsIntArr[i]);
                }
            }
        }
        sysRoleModuService.updateSysUserModu(roleIds, moduIdList, lists);
		/*if (moduIds != null && !"".equals(moduIds)) {
			
			UpdateRoleModuParam uRoleModuParam = new UpdateRoleModuParam();
			
			String[] moduIdsArr = moduIds.split(",");
			int[] moduIdsIntArr = new int[moduIdsArr.length];
			for (int i=0; i<moduIdsArr.length; i++) {
				moduIdsIntArr[i] = Integer.parseInt(moduIdsArr[i]);
			}
			
			uRoleModuParam.setModuIds(moduIdsIntArr);
			uRoleModuParam.setRoleId(roleIds);
			
			sysRoleModuService.updateByModuIds(uRoleModuParam);
		} else {
			sysRoleModuService.deleteByRoleId(roleIds);
		}*/
        response.getWriter().print("succ");
        return null;
    }

    @SystemServiceLog(description = "菜单列表")
    @RequestMapping(value = "/sys_modu", method = {RequestMethod.GET})
    public String sysModu(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return "system/modulist";
    }

    @SystemServiceLog(description = "菜单树")
    @RequestMapping(value = "/sys_modu_buidTree", method = {RequestMethod.GET, RequestMethod.POST})
    public String sysModuBuidTree(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        response.setContentType("text/html;charset=utf-8");

        List<SysModu> moduList = sysModuService.getAll();
        response.getWriter().print(createTreeJson(moduList));

        return null;
    }

    @SystemServiceLog(description = "删除菜单")
    @RequestMapping(value = "/sys_modu_del", method = {RequestMethod.GET, RequestMethod.POST})
    public String sysModuDel(String id, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html;charset=utf-8");
        sysModuService.deleteByPrimaryKey(Integer.parseInt(id));
        response.getWriter().print("succ");
        return null;
    }

    @SystemServiceLog(description = "保存菜单")
    @RequestMapping(value = "/sys_modu_saveMenu", method = {RequestMethod.GET, RequestMethod.POST})
    public String sysModuSaveMenu(SysModu moduObj, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        response.setContentType("text/html;charset=utf-8");

//        if (moduObj.getId() != null && !moduObj.getId().equals("")) {
        if (null!=moduObj.getId()) {
            moduObj.setRemark("cdjd");
            sysModuService.updateByPrimaryKeySelective(moduObj);

        } else {

            moduObj.setGrade(2);
            moduObj.setRemark("cdjd");
            sysModuService.insertSelective(moduObj);

        }

        response.getWriter().print("succ");
        return null;
    }

    @SystemServiceLog(description = "保存菜单模型")
    @RequestMapping(value = "/sys_modu_saveModel", method = {RequestMethod.GET, RequestMethod.POST})
    public String sysModuSaveModel(SysModu moduObj, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        response.setContentType("text/html;charset=utf-8");

//        if (moduObj.getId() != null && !moduObj.getId().equals("")) {
        if (null!=moduObj.getId()) {
            moduObj.setRemark("mkjd");
            sysModuService.updateByPrimaryKeySelective(moduObj);

        } else {

            moduObj.setGrade(1);
            moduObj.setRemark("mkjd");
            sysModuService.insertSelective(moduObj);

        }

        response.getWriter().print("succ");
        return null;
    }

    @SystemServiceLog(description = "保存菜单模型列表")
    @RequestMapping(value = "/sys_modu_showList", method = {RequestMethod.GET, RequestMethod.POST})
    public String sysModuShowList(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        response.setContentType("text/html;charset=utf-8");

        List<SysModu> moduList = sysModuService.showModuList();

        String json1 = this.createTreeJson(moduList);
        String json2 = json1.replaceAll("name", "text");

        response.getWriter().print(json2);

        return null;
    }

    @SystemServiceLog(description = "保存企业菜单模型列表")
    @RequestMapping(value = "/sys_enterprise_modu_showList", method = {RequestMethod.GET, RequestMethod.POST})
    public String enterpriseSysModuShowList(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        response.setContentType("text/html;charset=utf-8");

        List<SysModu> moduList = sysModuService.showEnterpriseModuList();

        String json1 = this.createTreeJson(moduList);
        String json2 = json1.replaceAll("name", "text");

        response.getWriter().print(json2);

        return null;
    }

    @SystemServiceLog(description = "用户")
    @RequestMapping(value = "/sys_user", method = {RequestMethod.GET})
    public String sysUser(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return "system/sysuser";
    }

    @SystemServiceLog(description = "企业系统用户")
    @RequestMapping(value = "/enterpriseSystemUser", method = {RequestMethod.GET})
    public String enterprisesysUser() {
        return "enterprise/enterprise_sysuser";
    }

    @SystemServiceLog(description = "企业系统用户列表")
    @RequestMapping(value = "/enterprise_sys_user_list", method = {RequestMethod.GET, RequestMethod.POST})
    public void enterpriseSysUserList(int page, int rows, String name, String account, String enterpriseName, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html;charset=utf-8");
        Map<String, Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("offset", pageInfo.get("first_page"));
        paraMap.put("rows", pageInfo.get("page_size"));
        paraMap.put("name", name);
        paraMap.put("account", account);
        paraMap.put("enterpriseName", enterpriseName);
        Pager<SysUser> pager = sysUserService.getEnterpriseSysUserList(paraMap);
        response.getWriter().print(Data2Jsp.Json2Jsp(pager));
    }

    @SystemServiceLog(description = "用户列表")
    @RequestMapping(value = "/sys_user_list", method = {RequestMethod.GET, RequestMethod.POST})
    public String sysUserList(int page, int rows, String name, String account,String status, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html;charset=utf-8");

        Map<String, Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("offset", pageInfo.get("first_page"));
        paraMap.put("rows", pageInfo.get("page_size"));
        paraMap.put("name", name);
        paraMap.put("account", account);
        if (status == null) {
            status = "1";
        }
        paraMap.put("status",  status);
        Pager<SysUser> pager = sysUserService.getAll(paraMap);
//        Pager<SysUser> pager = sysUserService.getAll(pageInfo.get("first_page"), pageInfo.get("page_size"), name, account);
        response.getWriter().print(Data2Jsp.Json2Jsp(pager));
        return null;
    }
    @SystemServiceLog(description = "企业用户管理修改用户")
    @RequestMapping(value = "/enerprise_sys_user_update", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> enterpriseSysUserUpdate(SysUser sysUser) {
        if (sysUser == null || sysUser.getId() == null) {
            return fail("参数错误 ");
        }
        SysUser user = sysUserService.getSysUserByPhone(sysUser.getPhone());
        if (user != null && !user.getId().equals(sysUser.getId())) {
            return fail("该手机号已注册");
        }
        sysUser.setUpdateTime(new Date());
        sysUserService.updateByPrimaryKeySelective(sysUser);
        return success();
    }

    @SystemServiceLog(description = "新增用户")
    @RequestMapping(value = "/sys_user_save", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> sysUserSave(SysUser userObj, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        SysUser existUser = sysUserService.selectByAccount(userObj.getAccount());
        if (existUser != null) {
            return fail("该帐号已注册，请重新注册一个帐号");
        }
        existUser = sysUserService.getSysUserByPhone(userObj.getPhone());
        if (existUser != null) {
            return fail("该手机号已注册！");
        }
        userObj.setPassword(MD5.toMD5(userObj.getPassword()));
        userObj.setCreateTime(new Date());
        sysUserService.insertSelective(userObj);
        return success();

    }


    @SystemServiceLog(description = "修改新增用户")
    @RequestMapping(value = "/sys_user_saveOrUpdate", method = {RequestMethod.GET, RequestMethod.POST})
    public String sysUserSaveOrUpdate(SysUser userObj, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        response.setContentType("text/html;charset=utf-8");

        String resultStr = "";

        SysUser existUser = sysUserService.selectByAccount(userObj.getAccount());
        if (existUser != null && !existUser.getAccount().equals("")) {
            resultStr = "ishave";
        } else {
//            if (userObj.getId() != null && !userObj.getId().equals("")) {
        	if (null != userObj.getId()) {
                userObj.setUpdateTime(new Date());
                sysUserService.updateByPrimaryKeySelective(userObj);
            } else {
                SysUser sysUser = sysUserService.getSysUserByPhone(userObj.getPhone());
                if (sysUser != null) {
                    resultStr = "phoneUsed";
                    response.getWriter().print(resultStr);
                    return null;
                }
                userObj.setPassword(MD5.toMD5(userObj.getPassword()));
                userObj.setCreateTime(new Date());
                sysUserService.insertSelective(userObj);
            }
            resultStr = "succ";
        }

        response.getWriter().print(resultStr);
        return null;
    }

    @SystemServiceLog(description = "用户重置密码")
    @RequestMapping(value = "/sys_user_resetPwd", method = {RequestMethod.GET, RequestMethod.POST})
    public String sysUserResetPwd(Integer id, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        response.setContentType("text/html;charset=utf-8");

        String resultStr = "error";

        SysUser existUser = sysUserService.selectByPrimaryKey(id);
        if (existUser != null && !existUser.getAccount().equals("")) {
            existUser.setPassword(MD5.toMD5(existUser.getPhone().substring(5, 11)));
            sysUserService.updateByPrimaryKeySelective(existUser);
            resultStr = "succ";
        }

        response.getWriter().print(resultStr);
        return null;
    }

    @SystemServiceLog(description = "检查用户权限")
    @RequestMapping(value = "/sys_user_checkRoleModu", method = {RequestMethod.GET, RequestMethod.POST})
    public String sysUserCheckRoleModu(Integer userId, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html;charset=utf-8");

        StringBuffer bf = new StringBuffer();
        try {
            bf.append("[");
            List<SysUserRole> list = sysUserService.selectUserRoleByUserId(userId);

            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    SysUserRole sysUserRole = list.get(i);
                    bf.append("{");
                    bf.append("\"role_id\":" + sysUserRole.getRoleId());
                    if (i < list.size() - 1) {
                        bf.append("},");
                    } else {
                        bf.append("}");
                    }
                }
            }
            bf.append("]");
            response.getWriter().print(bf.toString());
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().print("[{}]");
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    @SystemServiceLog(description = "分配用户角色")
    @RequestMapping(value = "/sys_user_grantRole", method = {RequestMethod.GET, RequestMethod.POST})
    public String sysUserGrantRole(String roleIds, Integer userIds, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html;charset=utf-8");
        if (!queryUserPermission(request, "assign_role")) {
            response.getWriter().print("error");
            return null;
        }
        SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
        try {
            if (roleIds != null && !"".equals(roleIds)) {
                sysUserRoleService.deleteByUserid(userIds);
                if (roleIds.contains(",")) {
                    final List<SysUserRole> lists = new ArrayList<SysUserRole>();
                    Date date = new Date();
                    String[] roleidArray = roleIds.split(",");
                    for (String roleid : roleidArray) {
                        SysUserRole sysUserRole = new SysUserRole();
                        sysUserRole.setRoleId(Integer.parseInt(roleid));
                        sysUserRole.setUserId(userIds);
                        sysUserRole.setUpdateTime(date);
                        sysUserRole.setUpdateUser(sysUser.getId().toString());
                        lists.add(sysUserRole);
                        //		sysUserRoleService.insertSelective(sysUserRole);
                    }
                    jdbcTemplate.execute("delete from x_sys_user_role where user_id =" + userIds);
                    String sql = "insert into x_sys_user_role(user_id,role_id,update_user,update_time) values(?,?,?,?)";
                    jdbcTemplate.batchUpdate(sql, new BatchSetterUtil<SysUserRole>(lists) {
                        public void setValues(PreparedStatement ps, int i)
                                throws SQLException {
                            SysUserRole userRole = lists.get(i);
                            ps.setInt(1, userRole.getUserId());
                            ps.setInt(2, userRole.getRoleId());
                            ps.setString(3, userRole.getUpdateUser());
                            ps.setTimestamp(4, new Timestamp(userRole.getUpdateTime().getTime()));
                        }
                    });
                } else {
                    SysUserRole sysUserRole = new SysUserRole();
                    sysUserRole.setRoleId(Integer.parseInt(roleIds));
                    sysUserRole.setUserId(userIds);
                    sysUserRole.setUpdateTime(new Date());
                    sysUserRole.setUpdateUser(sysUser.getId().toString());
                    jdbcTemplate.execute("delete from x_sys_user_role where user_id =" + userIds);
                    sysUserRoleService.insertSelective(sysUserRole);
                }
            } else {
                jdbcTemplate.execute("delete from x_sys_user_role where user_id =" + userIds);
            }
            response.getWriter().print("succ");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().print("fail");
        }

        return null;
    }

    @SuppressWarnings("rawtypes")
    @SystemServiceLog(description = "分配用户权限")
    @RequestMapping(value = "/sys_user_checkUserModu", method = {RequestMethod.GET, RequestMethod.POST})
    public String sysUserCheckUserModu(Integer userId, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html;charset=utf-8");

        StringBuffer bf = new StringBuffer();
        try {
            bf.append("[");
            List<SysUserModu> list = sysUserService.selectByEnabledUserId(userId);
            List<String> values = new ArrayList<String>();
            for (SysUserModu sysUserModu : list) {
                if (sysUserModu.getFunction() != null && !"".equals(sysUserModu.getFunction())) {
                    JSONArray jsonArray = JSONArray.fromObject(sysUserModu.getFunction());
                    List<Map<String, String>> mapListJson = (List) jsonArray;

                    for (Map<String, String> map : mapListJson) {
                        Set<String> keySet = map.keySet();
                        for (String str : keySet) {
                            values.add(map.get(str));
                        }
                    }
                }
            }
            List<SysModu> sysModus = sysModuService.selectModuByLinks(values);
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    SysUserModu sysUserModu = list.get(i);
                    bf.append("{");
                    bf.append("\"modu_id\":" + sysUserModu.getModuId());
                    if (i <= list.size() - 1) {
                        bf.append("},");
                    }/* else {
						bf.append("}");
					}*/
                }
            }
            if (sysModus.size() > 0) {
                for (int i = 0; i < sysModus.size(); i++) {
                    SysModu sysModu = sysModus.get(i);
                    bf.append("{");
                    bf.append("\"modu_id\":" + sysModu.getId());
                    if (i < sysModus.size() - 1) {
                        bf.append("},");
                    } else {
                        bf.append("}");
                    }
                }
            }
            bf.append("]");
            response.getWriter().print(bf.toString());
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().print("[{}]");
        }

        return null;
    }

    @SuppressWarnings("unused")
    @SystemServiceLog(description = "更新或新增用户角色")
    @RequestMapping(value = "/sys_user_saveUserModu", method = {RequestMethod.GET, RequestMethod.POST})
    public String sysUserSaveUserModu(Integer userIds, String moduIds, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html;charset=utf-8");
        if (!queryUserPermission(request, "assign_right")) {
            response.getWriter().print("error");
            return null;
        }
        String isShows = request.getParameter("is_shows");
        String[] isShowsArr = isShows.split(",");
        //获得前台传过来的pid
        String pids = request.getParameter("pids");
        int[] pidsIntArr = null;
        if (pids != null && !"".equals(pids)) {
            String[] pidArr = pids.split(",");
            pidsIntArr = new int[pidArr.length];
            for (int i = 0; i < pidsIntArr.length; i++) {
                pidsIntArr[i] = Integer.valueOf(pidArr[i]);
            }
        }
        //获得前台传过来的names
        String names = request.getParameter("names");
        String[] namesArr = names.split(",");
        //循环所有的pid
        Set<Integer> pidSet = new HashSet<Integer>();
        if (pidsIntArr != null) {
            for (int i = 0; i < pidsIntArr.length; i++) {
                //如果该pid对应的is_show为2,说明是功能菜单
                if (isShowsArr[i].equals("2")) {
                    //获得对应的pid
                    pidSet.add(pidsIntArr[i]);
                }
            }
        }

        //将set集合转换为list集合
        List<Integer> pidList = new ArrayList<Integer>(pidSet);
        List<Map<Integer, String>> lists = new ArrayList<Map<Integer, String>>();
        //遍历这个pidSet
        for (int i = 0; i < pidList.size(); i++) {
            Map<Integer, String> map = new HashMap<Integer, String>();
            //创建一个list集合
            List<Map<String, Object>> functions = new ArrayList<Map<String, Object>>();
            for (int j = 0; j < pidsIntArr.length; j++) {
                if (pidList.get(i).equals(pidsIntArr[j])) {
                    Map<String, Object> function = new HashMap<String, Object>();
                    String name = namesArr[j];
                    //根据name和pid查询对应的菜单sys_modu
                    SysModu sysModu = sysModuService.selectByNameAndPid(pidList.get(i), name);
                    function.put(sysModu.getName(), sysModu.getLink());
                    functions.add(function);
                }
            }
            String functionStr = JSON.toJSONString(functions);
            map.put(pidList.get(i), functionStr);
            lists.add(map);
        }

        List<Integer> moduIdList = new ArrayList<Integer>();
        if (moduIds != null && !"".equals(moduIds)) {
            String[] moduIdsArr = moduIds.split(",");
            int[] moduIdsIntArr = new int[moduIdsArr.length];
            for (int i = 0; i < moduIdsArr.length; i++) {
                moduIdsIntArr[i] = Integer.parseInt(moduIdsArr[i]);
            }
            //获得除了is_show为2的所有moduId
            for (int i = 0; i < moduIdsIntArr.length; i++) {
                if (!isShowsArr[i].equals("2")) {
                    moduIdList.add(moduIdsIntArr[i]);
                }
            }
        }
        sysUserModuService.updateSysUserModu(userIds, moduIdList, lists);
		/*if (moduIds != null && !"".equals(moduIds)) {
			
			UpdateUserModuParam userModuParam = new UpdateUserModuParam();
			
			String[] moduIdsArr = moduIds.split(",");
			int[] moduIdsIntArr = new int[moduIdsArr.length];
			for (int i=0; i<moduIdsArr.length; i++) {
				moduIdsIntArr[i] = Integer.parseInt(moduIdsArr[i]);
			}
			
			userModuParam.setModuIds(moduIdsIntArr);
			userModuParam.setUserId(userIds);
			
			sysUserModuService.updateByUserIdModuId(userIds, userModuParam);
		}else{
			sysUserModuService.deleteByUserId(userIds);
		}*/
        response.getWriter().print("succ");

        return null;
    }

    /**
     * 创建一颗树，以json字符串形式返回
     *
     * @param list 原始数据列表
     * @return 树
     */
    private String createTreeJson(List<SysModu> list) {
        JSONArray rootArray = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            SysModu resouce = list.get(i);
            if (resouce != null) {
                if (resouce.getPid() == null || resouce.getPid() == 0) {
                    JSONObject rootObj = createBranch(list, resouce);
                    rootArray.add(rootObj);
                }
            }

        }

        return rootArray.toString();
    }

    /**
     * 递归创建分支节点Json对象
     *
     * @param list        创建树的原始数据
     * @param currentNode 当前节点
     * @return 当前节点与该节点的子节点json对象
     */
    private JSONObject createBranch(List<SysModu> list, SysModu currentNode) {
		/*
		 * 将javabean对象解析成为JSON对象
		 */
        JSONObject currentObj = JSONObject.fromObject(currentNode);
        JSONArray childArray = new JSONArray();
		/*
		 * 循环遍历原始数据列表，判断列表中某对象的父id值是否等于当前节点的id值，
		 * 如果相等，进入递归创建新节点的子节点，直至无子节点时，返回节点，并将该 节点放入当前节点的子节点列表中
		 */
        for (int i = 0; i < list.size(); i++) {
            SysModu newNode = list.get(i);
            if (newNode.getPid() != null
                    && newNode.getPid().compareTo(currentNode.getId()) == 0) {
                JSONObject childObj = createBranch(list, newNode);
                childArray.add(childObj);
            }
        }

		/*
		 * 判断当前子节点数组是否为空，不为空将子节点数组加入children字段中
		 */
        if (!childArray.isEmpty()) {
            currentObj.put("children", childArray);
        }

        return currentObj;
    }

    // json to javabean
    public static Object getObject4JsonString(String jsonString, Class pojoCalss) {
        Object pojo;
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        String[] dateFormats = new String[]{"yyyy-MM-dd HH:mm:ss"};
        JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));
        pojo = JSONObject.toBean(jsonObject, pojoCalss);
        return pojo;
    }

    /**
     * 会员等级combobox
     */
    @SystemServiceLog(description = "会员等级")
    @RequestMapping(value = "/sys_memberLevelCombobox", method = {RequestMethod.GET, RequestMethod.POST})
    public String sysMemberLevelCombobox(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html;charset=utf-8");
        HttpSession sess = request.getSession();
        List<MemberLevel> memberLevelList = (List<MemberLevel>) sess.getAttribute("memberLevel");
        if (null == memberLevelList) {
            memberLevelList = sysUserService.selectAllMemberLevel();
        }
        StringBuffer tree = new StringBuffer();
        tree.append("[");
        if (memberLevelList != null && memberLevelList.size() > 0) {
            for (int i = 0; i < memberLevelList.size(); i++) {
                MemberLevel memberLevel = memberLevelList.get(i);
                tree.append("{");
                tree.append("\"id\":\"" + memberLevel.getId() + "\",");
                tree.append("\"text\":\"" + memberLevel.getName() + "\"");
                if (i < memberLevelList.size() - 1) {
                    tree.append("},");
                } else {
                    tree.append("}");
                }
            }
        }
        tree.append("]");
        response.getWriter().print(tree.toString());
        return null;
    }

    /**
     * 车辆类型combobox
     */
    @SystemServiceLog(description = "车辆类型")
    @RequestMapping(value = "/sys_carTypeCombobox", method = {RequestMethod.GET, RequestMethod.POST})
    public String sysCarTypeCombobox(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html;charset=utf-8");
        HttpSession sess = request.getSession();
        List<CarType> carTypeList = (List<CarType>) sess.getAttribute("carType");
        if (null == carTypeList) {
            carTypeList = carTypeService.selectAllNotPage();
        }
        StringBuffer tree = new StringBuffer();
        tree.append("[");
        if (carTypeList != null && carTypeList.size() > 0) {
            for (int i = 0; i < carTypeList.size(); i++) {
                CarType carType = carTypeList.get(i);
                tree.append("{");
                tree.append("\"id\":\"" + carType.getId() + "\",");
                tree.append("\"text\":\"" + carType.getType() + "\"");
                if (i < carTypeList.size() - 1) {
                    tree.append("},");
                } else {
                    tree.append("}");
                }
            }
        }
        tree.append("]");
        response.getWriter().print(tree.toString());
        return null;
    }

    @SystemServiceLog(description = "可选所属城市")
    @RequestMapping(value = "/sys_optional_cityCombobox", method = {RequestMethod.GET, RequestMethod.POST})
    public String sysOptionalCityCombobox(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html;charset=utf-8");
        HttpSession sess = request.getSession();
        SysUser user = (SysUser) sess.getAttribute("user");
        List<City> cityList = new ArrayList<City>();//(List<City>) sess.getAttribute("city");

        if (user.getCityCode().equals("00")) {
            cityList = (List<City>) sess.getAttribute("city");
            if (null == cityList)
                cityList = sysUserService.selectAllCity();
        } else {
            cityList = cityService.queryCityByCode(user.getCityCode());
        }

        StringBuffer tree = new StringBuffer();
        tree.append("[");
        if (cityList != null && cityList.size() > 0) {
            for (int i = 0; i < cityList.size(); i++) {
                City city = cityList.get(i);
                tree.append("{");
                tree.append("\"id\":\"" + city.getCode() + "\",");
                tree.append("\"text\":\"" + city.getName() + "\"");
                if (i < cityList.size() - 1) {
                    tree.append("},");
                } else {
                    tree.append("}");
                }
            }
        }
        tree.append("]");

        response.getWriter().print(tree.toString());
        return null;
    }

    @SystemServiceLog(description = "所属城市")
    @RequestMapping(value = "/sys_not00_cityCombobox", method = {RequestMethod.GET, RequestMethod.POST})
    public String sys_not_ChinaCombobox(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html;charset=utf-8");
        HttpSession sess = request.getSession();
        List<City> cityList = (List<City>) sess.getAttribute("city");
        if (null == cityList) {
            cityList = sysUserService.selectAllCity();
        }
        StringBuffer tree = new StringBuffer();
        tree.append("[");
        if (cityList != null && cityList.size() > 0) {
            for (int i = 0; i < cityList.size(); i++) {
                City city = cityList.get(i);
                if ("00".equals(city.getCode())) continue;
                tree.append("{");
                tree.append("\"id\":\"" + city.getCode() + "\",");
                tree.append("\"text\":\"" + city.getName() + "\"");
                if (i < cityList.size() - 1) {
                    tree.append("},");
                } else {
                    tree.append("}");
                }
            }
        }
        tree.append("]");

        response.getWriter().print(tree.toString());
        return null;
    }

    @SystemServiceLog(description = "所属城市")
    @RequestMapping(value = "/sys_{layer}_cityCombobox")
    public String sys_layer_cityCombobox(@PathVariable String layer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        HttpSession sess = request.getSession();
        List<City> cityList = cityService.selectCityByLayer(layer);
        StringBuffer tree = new StringBuffer();
        tree.append("[");
        if (cityList != null && cityList.size() > 0) {
            for (int i = 0; i < cityList.size(); i++) {
                City city = cityList.get(i);
                if ("00".equals(city.getCode())) continue;
                tree.append("{");
                tree.append("\"id\":\"" + city.getCode() + "\",");
                tree.append("\"text\":\"" + city.getName() + "\"");
                if (i < cityList.size() - 1) {
                    tree.append("},");
                } else {
                    tree.append("}");
                }
            }
        }
        tree.append("]");

        response.getWriter().print(tree.toString());
        return null;
    }

    @SystemServiceLog(description = "获取问题类型")
    @RequestMapping(value = "/getSysDicType", method = {RequestMethod.GET, RequestMethod.POST})
    public String getSysDicType(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        HttpSession sess = request.getSession();
        List<SysDicType> sysDicTypes = sysDicTypeService.querySysDicType();
        StringBuffer tree = getStringBuffer(sysDicTypes);
        response.getWriter().print(tree.toString());
        return null;
    }

    @SystemServiceLog(description = "获取救援类型")
    @RequestMapping(value = "/getSysDic", method = {RequestMethod.GET, RequestMethod.POST})
    public String getSysDic(Integer id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        HttpSession sess = request.getSession();
        List<SysDic> sysDics = sysDicService.querySysDic(id);
        StringBuffer tree = getStringBuffer(sysDics);
        response.getWriter().print(tree.toString());
        return null;
    }

    private <T> StringBuffer getStringBuffer(List<T> type) {
        StringBuffer tree = new StringBuffer();
        tree.append("[");
        if (type != null && type.size() > 0) {
            for (int i = 0; i < type.size(); i++) {
                if (type.get(i) instanceof SysDicType) {
                    SysDicType sysDicType = (SysDicType) type.get(i);
                    tree.append("{");
                    tree.append("\"id\":\"" + sysDicType.getId() + "\",");
                    tree.append("\"name\":\"" + sysDicType.getName() + "\"");
                    if (i < type.size() - 1) {
                        tree.append("},");
                    } else {
                        tree.append("}");
                    }
                } else {
                    SysDic sysDic = (SysDic) type.get(i);
                    tree.append("{");
                    tree.append("\"id\":\"" + sysDic.getId() + "\",");
                    tree.append("\"name\":\"" + sysDic.getName() + "\"");
                    if (i < type.size() - 1) {
                        tree.append("},");
                    } else {
                        tree.append("}");
                    }
                }

            }
        }
        tree.append("]");
        return tree;
    }


}

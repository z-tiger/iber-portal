package com.iber.portal.advices;

import com.google.common.collect.Maps;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.sys.SysOperateLogService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * 操作日志
 */
@Component
@Aspect
public class OperateLogAdvices {

    @Autowired
    private SysOperateLogService sysOperateLogService;

    // 不需要的特征值
    private static final String[] FILTERS = {"List","list", "get", "query", "page", "Page"
            ,"sysDictType","sysCityCombobox","car_repair_info","car_navigate_info","allCarRunOrderInfo"
            ,"Combobox","carOfflineApplyTotal","selectUserByConnectorId","warn_remind_total","warnRemindTotal"};

    /**
     * 后置日志记录
     *
     * @param point
     * @throws Exception
     */
    @AfterReturning(value = "within(com.iber.portal.controller.*.*) " +
            "&& !within(com.iber.portal.controller.operateLog.*)" +
            "&& !within(com.iber.portal.controller.base.*)" +
            "|| within(com.iber.portal.controller.*) ")
    public void platformLog(JoinPoint point) throws Exception {
        StringBuilder params = new StringBuilder();
        final Signature signature = point.getSignature();
        final String method = signature.getName();
        for (int i = 0, len = FILTERS.length; i < len; i++) {
            if (method.contains(FILTERS[i])) return;
        }

        final ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        HttpSession session = request.getSession();
        SysUser sysUser = (SysUser) session.getAttribute("user");
        if (sysUser == null) return;

        final Map<String, String[]> parameterMap = request.getParameterMap();
        if (!CollectionUtils.isEmpty(parameterMap)) {
            final Set<Map.Entry<String, String[]>> entries = parameterMap.entrySet();
            for (final Map.Entry<String, String[]> entry : entries) {
                final String key = entry.getKey();
                final String[] value = entry.getValue();
                final String str = ArrayUtils.isEmpty(value) ? "" : value.length == 1 ? value[0] : Arrays.toString(value);
                params.append(key).append(" : ").append(str).append(";");
            }
        }

        Map<String, Object> map = Maps.newHashMap();
        if ("login".equals(method) || "modifyPwd".equals(method)) {
            params = new StringBuilder("******");
        }
        if ("saveRoleModu".equals(method) && StringUtils.length(params) > 100) {
            map.put("inParam", params.substring(0,97)+"...");
        }else {
            map.put("inParam", params.toString());
        }
        map.put("userId", sysUser.getId());
        map.put("name", sysUser.getName());
        map.put("ip", request.getRemoteAddr());
        map.put("methodDescribe", getServiceMthodDescription(point));
        map.put("methodName", signature.getDeclaringType().getSimpleName() +
                "." + method);
        map.put("createTime", System.currentTimeMillis());
        // 保存操作日志
        sysOperateLogService.savePlatformLog(map);
    }


    /**
     * 获取注解中对方法的描述信息 用于service层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    public static String getServiceMthodDescription(JoinPoint joinPoint)
            throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    if (method.getAnnotation(SystemServiceLog.class) != null) {
                        description = method.getAnnotation(SystemServiceLog.class).description();
                    }
                    break;
                }
            }
        }
        return description;
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    public static String getControllerMethodDescription(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(SystemControllerLog.class).description();
                    break;
                }
            }
        }
        return description;
    }

}

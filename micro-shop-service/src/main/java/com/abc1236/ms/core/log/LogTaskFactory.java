package com.abc1236.ms.core.log;

import com.abc1236.ms.constant.state.LogSucceed;
import com.abc1236.ms.constant.state.LogType;
import com.abc1236.ms.core.SpringContextHolder;
import com.abc1236.ms.entity.system.LoginLog;
import com.abc1236.ms.entity.system.OperationLog;
import com.abc1236.ms.mapper.system.LoginLogMapper;
import com.abc1236.ms.mapper.system.OperationLogMapper;
import com.abc1236.ms.util.ToolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

/**
 * 日志操作任务创建工厂
 *
 * @author fengshuonan
 * @date 2016年12月6日 下午9:18:27
 */
public class LogTaskFactory {

    private static Logger logger = LoggerFactory.getLogger(LogManager.class);
    private static LoginLogMapper loginLogRepository = SpringContextHolder.getBean(LoginLogMapper.class);
    private static OperationLogMapper operationLogRepository = SpringContextHolder.getBean(OperationLogMapper.class);

    public static TimerTask loginLog(final Long userId, final String ip) {
        return new TimerTask() {
            @Override
            public void run() {
                try {
                    LoginLog loginLog = LogFactory.createLoginLog(LogType.LOGIN, userId, null, ip);
                    loginLogRepository.insert(loginLog);
                } catch (Exception e) {
                    logger.error("创建登录日志异常!", e);
                }
            }
        };
    }

    public static TimerTask loginLog(final String username, final String msg, final String ip) {
        return new TimerTask() {
            @Override
            public void run() {
                LoginLog loginLog = LogFactory.createLoginLog(
                    LogType.LOGIN_FAIL, null, "账号:" + username + "," + msg, ip);
                try {
                    loginLogRepository.insert(loginLog);
                } catch (Exception e) {
                    logger.error("创建登录失败异常!", e);
                }
            }
        };
    }

    public static TimerTask exitLog(final Long userId, final String ip) {
        return new TimerTask() {
            @Override
            public void run() {
                LoginLog loginLog = LogFactory.createLoginLog(LogType.EXIT, userId, null, ip);
                try {
                    loginLogRepository.insert(loginLog);
                } catch (Exception e) {
                    logger.error("创建退出日志异常!", e);
                }
            }
        };
    }

    public static TimerTask bussinessLog(final Long userId, final String bussinessName, final String clazzName, final String methodName, final String msg) {
        return new TimerTask() {
            @Override
            public void run() {
                OperationLog operationLog = LogFactory.createOperationLog(
                    LogType.BUSSINESS, userId, bussinessName, clazzName, methodName, msg, LogSucceed.SUCCESS);
                try {
                    operationLogRepository.insert(operationLog);
                } catch (Exception e) {
                    logger.error("创建业务日志异常!", e);
                }
            }
        };
    }

    public static TimerTask exceptionLog(final Long userId, final Exception exception) {
        return new TimerTask() {
            @Override
            public void run() {
                String msg = ToolUtil.getExceptionMsg(exception);
                OperationLog operationLog = LogFactory.createOperationLog(
                    LogType.EXCEPTION, userId, "", null, null, msg, LogSucceed.FAIL);
                try {
                    operationLogRepository.insert(operationLog);
                } catch (Exception e) {
                    logger.error("创建异常日志异常!", e);
                }
            }
        };
    }
}

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author Dinglong Zhang
 * @Date 2020-11-01 15:02
 */
public class MeMappender extends AppenderSkeleton {
    private String appName;

    @Override
    protected void append(LoggingEvent loggingEvent) {
        if (loggingEvent == null || loggingEvent.getMessage() == null) {
            return;
        }
        // 必须设置 appName
        if (appName == null || appName.isEmpty()) {
            return;
        }

        //打印日志核心方法
        String level = loggingEvent.getLevel().toString();
        String loggername = loggingEvent.getLoggerName();
        String msg = loggingEvent.getRenderedMessage();
        String threadName = loggingEvent.getThreadName();
        Throwable throwable = loggingEvent.getThrowableInformation() != null ? loggingEvent.getThrowableInformation().getThrowable() : null;
        //实现日志处理逻辑
        List<LoggingEvent> log4jlist = new ArrayList();
        log4jlist.add(loggingEvent);


    }

    @Override
    public void close() {
        //释放资源

    }

    @Override
    public boolean requiresLayout() {
        //是否需要按格式输出文本
        return false;
    }

    /**
     * 定义 setter 方法，这样在配置文件中添加类似 log4j.appender.CustomAppender.appName=test_app_name 的配置项时，配置会被注入到这个 appender 中
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }
}

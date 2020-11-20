import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author Dinglong Zhang
 * @Date 2020-11-01 15:02
 */
public class MeMappender extends AppenderSkeleton {
    private static MeMappender instance;
    private int maxsize=101;
    private long discardedLogCount = 0;

    private static List<LoggingEvent> currentloggingEventList;
    private Layout layout;


    //单例设计模式
    private MeMappender(List<LoggingEvent> list) {
        MeMappender.currentloggingEventList = list;
        instance = this;
    }

    public static synchronized MeMappender getInstance(List<LoggingEvent> list) {
        if (instance == null) {
            instance = new MeMappender(list);

        }
        return instance;
    }




    @Override
    protected void append(LoggingEvent loggingEvent) {
        currentloggingEventList.add(loggingEvent);

        if (currentloggingEventList.size() >= maxsize) {
            discardedLogCount++;
            currentloggingEventList.remove(0);
        }

    }

    //getcurrentlogs
    public List<LoggingEvent> getCurrentLogs() {
        return Collections.unmodifiableList(currentloggingEventList);
    }

    public List<String> getEventStrings() {

        List<String> eventstrings = getCurrentLogs().stream().map(loggingEvent -> this.layout.format(loggingEvent)).collect(Collectors.toList());
        List<String> EventStrings = Collections.unmodifiableList(eventstrings);
        return EventStrings;
    }

    public void printlogs() {
        List<String> eventstrings = getEventStrings();
        for (String event:eventstrings) {
            System.out.println(event);
        }
        currentloggingEventList.clear();

    }


    @Override
    public void close() {
        //释放资源
        instance = null;
        discardedLogCount = 0;
        currentloggingEventList = null;
        layout = null;

    }

    @Override
    public boolean requiresLayout() {
        //是否需要按格式输出文本
        return false;
    }



    @Override
    public Layout getLayout() {
        return layout;
    }

    @Override
    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    public long getDiscardedLogCount() {
        return discardedLogCount;
    }

    public void setDiscardedLogCount(long discardedLogCount) {
        this.discardedLogCount = discardedLogCount;
    }

    public int getMaxsize() {
        return maxsize;
    }

    public void setMaxsize(int maxsize) {
        this.maxsize = maxsize;
    }

    public List<LoggingEvent> getCurrentloggingEventList() {
        return currentloggingEventList;
    }

    public static void setCurrentloggingEventList(List<LoggingEvent> currentloggingEventList) {
        MeMappender.currentloggingEventList = currentloggingEventList;
    }
    /**
     * 定义 setter 方法，这样在配置文件中添加类似 log4j.appender.CustomAppender.appName=test_app_name 的配置项时，配置会被注入到这个 appender 中
     */
}

import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import java.io.StringWriter;
import java.time.LocalDateTime;

/**
 * @Description
 * @Author Dinglong Zhang
 * @Date 2020-11-15 22:33
 */
public class VelocityLayout extends Layout {

    private String pattern;

    VelocityContext ctx = new VelocityContext();
    VelocityEngine ve = new VelocityEngine();


    public VelocityLayout(String pattern) {
        this.pattern = pattern;
    }


    @Override
    public String format(LoggingEvent loggingEvent) {
        ve.init();
        ctx.put("d", LocalDateTime.now());
        ctx.put("p",loggingEvent.getLevel().toString());
        ctx.put("t",loggingEvent.getThreadName());
        ctx.put("m",loggingEvent.getMessage());
        ctx.put("c",loggingEvent.getLogger().getName());
        ctx.put("n",System.lineSeparator());

        StringWriter sw = new StringWriter();

        ve.evaluate(ctx,sw,"",pattern);

        return sw.toString();
    }

    @Override
    public boolean ignoresThrowable() {
        return false;
    }

    @Override
    public void activateOptions() {

    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}

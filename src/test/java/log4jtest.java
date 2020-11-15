import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

/**
 * @Description
 * @Author Dinglong Zhang
 * @Date 2020-11-01 16:13
 */
public class log4jtest {
    private static Logger log = Logger.getLogger(log4jtest.class);

    @Test
    public void testlog() {
        log.debug("{} debug OK");
        log.info("{} OK");
        log.warn("Not OK");
        log.error("log4j ERROR OCCUR!");

    }

}

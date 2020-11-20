
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;

class MeMappenderTest {

    private Layout velocityLayout;
    private static final String pattern = "$d [$t] $p $c: $m$n ";
    private MeMappender meMappender;


    @Test
    public void testsingleton() {
        MeMappender meMappender11 = MeMappender.getInstance(new ArrayList<LoggingEvent>());
        MeMappender meMappender22 = MeMappender.getInstance(new ArrayList<LoggingEvent>());
        assertEquals(meMappender11,meMappender22);
        meMappender11.close();
        meMappender22.close();
    }

    @Test
    public void testAddOneLoggingEvent(){
        Logger logger  = Logger.getLogger("memlogger");
        MeMappender meMappender = MeMappender.getInstance(new ArrayList<LoggingEvent>());
        logger.addAppender(meMappender);
        logger.info("info log");
        assertEquals(meMappender.getCurrentLogs().size(),1);
        meMappender.close();
    }

    @Test
    public void testmaxsizelimit() {
        Logger logger1  = Logger.getLogger("maxlogger1");
        MeMappender meMappender1 = MeMappender.getInstance(new ArrayList<LoggingEvent>());
        logger1.addAppender(meMappender1);
        for (int i = 0; i < 100; i++) {
            logger1.error("error log");
        }
        assertEquals(meMappender1.getCurrentLogs().size(),100);
        meMappender1.close();
    }

    @Test
    public void testprintLogs() {
        Logger logger2  = Logger.getLogger("maxlogger2");
        MeMappender meMappender2 = MeMappender.getInstance(new ArrayList<LoggingEvent>());
        logger2.addAppender(meMappender2);
        logger2.warn("warning log");
        velocityLayout = new VelocityLayout(pattern);
        meMappender2.setLayout(velocityLayout);
        meMappender2.printlogs();
        assertEquals(meMappender2.getLayout(),velocityLayout);
        meMappender2.close();
    }



    @Test
    public void testdifferentloggerlevel() {
        Logger logger4  = Logger.getLogger("maxlogger4");
        MeMappender meMappender4 = MeMappender.getInstance(new ArrayList<LoggingEvent>());
        logger4.addAppender(meMappender4);
        logger4.setLevel(Level.INFO);
        logger4.debug("Debug Message");
        logger4.info("Info Message");
        logger4.warn("Warn Message");
        assertEquals(meMappender4.getCurrentLogs().size(),2);
        meMappender4.close();
    }

    @Test
    public void testDiscardLogcounts(){
        Logger logger5  = Logger.getLogger("maxlogger5");
        MeMappender meMappender5 = MeMappender.getInstance(new ArrayList<LoggingEvent>());
        logger5.addAppender(meMappender5);
        for (int i = 0; i < 300000; i++) {
            logger5.error("error log");
        }
        assertEquals(meMappender5.getDiscardedLogCount(),299900);
    }

    @Test
    public void printlogswithoutlog4j() {
        Logger logger6  = Logger.getLogger("maxlogger6");
        MeMappender meMappender6 = MeMappender.getInstance(new ArrayList<LoggingEvent>());
        logger6.addAppender(meMappender6);
        logger6.warn("warning log");
        meMappender6.setLayout(new SimpleLayout());
        meMappender6.printlogs();
        meMappender6.close();
    }

}
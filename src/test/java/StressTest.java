import org.apache.commons.logging.Log;
import org.apache.log4j.*;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StressTest {
    private Logger logger;
    private Layout velocityLayout;
    private static MeMappender memappender;

    public void testperformance(Appender Memappender,int testnumber,String testobject) {
        logger = Logger.getLogger("appender");
        long starttime =System.currentTimeMillis();
        logger.addAppender(Memappender);
        addlogs(testnumber,logger);
        long endtime = System.currentTimeMillis();
        long time = endtime - starttime;
        System.out.println("The total time spent by " + testobject +"is "+time);
    }



    @Test
    public void testarraylist() {
        MeMappender arraymeMappender = MeMappender.getInstance(new ArrayList<LoggingEvent>());
        testperformance(arraymeMappender,50000,"arraylist");
        arraymeMappender.close();
    }

    @Test
    public void testlinkedlist() {
        MeMappender linkedmeMappender = MeMappender.getInstance(new LinkedList<LoggingEvent>());
        testperformance(linkedmeMappender,50000,"linkedlist");
        linkedmeMappender.close();
    }

    @Test
    public void testConsoleAppender(){
        Logger loggerpattern = Logger.getLogger("testconsole");
        String str = "$d [$t] $p $c: $m $n";
        VelocityLayout velocityLayout = new VelocityLayout(str);
        ConsoleAppender consoleAppender = new ConsoleAppender(velocityLayout);
        loggerpattern.addAppender(consoleAppender);
        for (int i = 0; i < 1000; i++) {
            loggerpattern.warn("warn message");
        }
        consoleAppender.close();
    }

    @Test
    public void testFileAppender() throws IOException {
        String str = "$d [$t] $p $c: $m $n";
        VelocityLayout velocityLayout = new VelocityLayout(str);
        Logger filelogger = Logger.getLogger("fileappender");
        FileAppender fileAppender = new FileAppender(velocityLayout,"log.txt");
        filelogger.addAppender(fileAppender);
        long startTime = System.currentTimeMillis();
        addlogs(1000,filelogger);
        long endTime = System.currentTimeMillis();
        long time = endTime-startTime;
        System.out.println("fileappender time is " + time);
    }

    @Test
    public void beforemaxsize() {
        MeMappender bfmaxmeMappender = MeMappender.getInstance(new ArrayList<LoggingEvent>());
        bfmaxmeMappender.setMaxsize(40000);
        testperformance(bfmaxmeMappender,350000,"bfformaxsize");
        bfmaxmeMappender.close();
    }

    @Test
    public void reachedmaxsize(){
        MeMappender reachmaxmeMappender = MeMappender.getInstance(new ArrayList<LoggingEvent>());
        reachmaxmeMappender.setMaxsize(40000);
        testperformance(reachmaxmeMappender,50000,"reachedmaxsize");
        reachmaxmeMappender.close();
    }

    @Test
    public void differentmaxsize() {
        MeMappender difmaxsize = MeMappender.getInstance(new ArrayList<LoggingEvent>());
        difmaxsize.setMaxsize(30000);
        testperformance(difmaxsize,25000,"difmaxsize");
        difmaxsize.close();
    }

    @Test
    public void reacheddifferentmaxsize() {
        MeMappender reacheddifmaxsize = MeMappender.getInstance(new ArrayList<LoggingEvent>());
        reacheddifmaxsize.setMaxsize(30000);
        testperformance(reacheddifmaxsize,38000,"reacheddifmaxsize");
        reacheddifmaxsize.close();
    }

    @Test
    public void testpatternlayoutstress() {
        Logger loggerpattern = Logger.getLogger("testpattern");
        loggerpattern.addAppender(new ConsoleAppender(new PatternLayout()));
        for (int i = 0; i < 10000; i++) {
            loggerpattern.warn("warn message");
        }
    }

    @Test
    public void TestVelocityLayoutStress() {
        String str = "$d [$t] $p $c: $m $n";
        Logger loggervelocity = Logger.getLogger("velocitytest");
        loggervelocity.addAppender(new ConsoleAppender(new VelocityLayout(str)));
        for (int i = 0; i < 10000; i++) {
            loggervelocity.warn("warn message");
        }
    }

    public void addlogs(int testnumber,Logger logger) {
        for (int i = 0; i < testnumber; i++) {
            logger.setLevel(Level.ALL);
            logger.warn("warning message");
        }
    }
}
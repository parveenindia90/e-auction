package com.auction.seller.app.util;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MethodStartEndLoggerTest {
    @InjectMocks
    MethodStartEndLogger.MethodStartEndLoggerAspect aspect;
    @Mock
    private ProceedingJoinPoint proceedingJoinPoint;
    @Mock
    MethodSignature signature;
    @Mock
    MethodStartEndLogger methodStartEndLogger;
    private static TestMemoryAppender memoryAppender;

    @Before
    public void setup() {
        when(proceedingJoinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn("testMethodName");
        when(signature.getDeclaringType()).thenReturn(Object.class);
        Logger logger = (Logger) LoggerFactory.getLogger(MethodStartEndLogger.MethodStartEndLoggerAspect.class);
        memoryAppender = new TestMemoryAppender();
        memoryAppender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
        logger.setLevel(Level.DEBUG);
        logger.addAppender(memoryAppender);
        memoryAppender.start();
    }

    @After
    public void cleanUp() {
        memoryAppender.reset();
        memoryAppender.stop();
    }

    @Test
    public void testLogStartEndMethod_success_noArgsInfo() throws Throwable {
        aspect.logStartEndMethod(proceedingJoinPoint, methodStartEndLogger);
        assertEquals("START :: Object :: testMethodName", memoryAppender.list.get(0).getFormattedMessage());
        assertEquals("END :: Object :: testMethodName", memoryAppender.list.get(1).getFormattedMessage());
    }

    @Test
    public void testLogStartEndMethod_success_withArgsInfo() throws Throwable {
        when(methodStartEndLogger.withArgumentsInfo()).thenReturn(true);
        aspect.logStartEndMethod(proceedingJoinPoint, methodStartEndLogger);
        assertEquals("START :: Object :: testMethodName :: argument names: null, argument values: null", memoryAppender.list.get(0).getFormattedMessage());
        assertEquals("END :: Object :: testMethodName", memoryAppender.list.get(1).getFormattedMessage());
    }

    @Test
    public void testLogStartEndMethod_exception_success() throws Throwable {
        when(proceedingJoinPoint.proceed()).thenThrow(new RuntimeException());
        boolean isException = false;
        try {
            aspect.logStartEndMethod(proceedingJoinPoint, methodStartEndLogger);
        } catch (Exception e) {
            isException = true;
        }
        assertTrue(isException);
        assertEquals("START :: Object :: testMethodName", memoryAppender.list.get(0).getFormattedMessage());
        assertEquals("END :: Object :: testMethodName", memoryAppender.list.get(1).getFormattedMessage());
    }

    class TestMemoryAppender extends ListAppender<ILoggingEvent> {
        public void reset() {
            this.list.clear();
        }

        public boolean contains(String string, Level level) {
            return this.list.stream()
                    .anyMatch(event -> event.getMessage().toString().contains(string)
                            && event.getLevel().equals(level));
        }

        public int countEventsForLogger(String loggerName) {
            return (int) this.list.stream()
                    .filter(event -> event.getLoggerName().contains(loggerName))
                    .count();
        }

        public List<ILoggingEvent> search(String string) {
            return this.list.stream()
                    .filter(event -> event.getMessage().toString().contains(string))
                    .collect(Collectors.toList());
        }

        public List<ILoggingEvent> search(String string, Level level) {
            return this.list.stream()
                    .filter(event -> event.getMessage().toString().contains(string)
                            && event.getLevel().equals(level))
                    .collect(Collectors.toList());
        }

        public int getSize() {
            return this.list.size();
        }

        public List<ILoggingEvent> getLoggedEvents() {
            return Collections.unmodifiableList(this.list);
        }
    }
}

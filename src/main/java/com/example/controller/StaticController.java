package com.example.controller;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import org.apache.commons.codec.digest.DigestUtils;

@Controller
public class StaticController {
    @Get("/api/static")
    public String hello() {
        return "Hello World!";
    }

    @Get("/api/static/{totalTime}/{sleepTime}")
    public void loadCPU(@PathVariable long totalTime, @PathVariable long sleepTime) {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        long startTime = threadMXBean.getCurrentThreadCpuTime();
        //processor loading
        while (threadMXBean.getCurrentThreadCpuTime() - startTime < totalTime * 1000000000) {
            for (int i = 0; i < 100000; i++) {
                byte[] data = new byte[64 * 1024]; // 64К данных
                String md5Hash = DigestUtils.md5Hex(data);
                md5Hash = md5Hash.trim();
            }
        }
        //IO
        try {
            Thread.sleep(sleepTime * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

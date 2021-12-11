package com.shf.jobs;

import java.util.Date;

/**
 * 自定义Job
 */
public class JobDemo {
    public void run(){
        System.out.println("自定义Job执行了"+new Date());
    }
}

package com.haoting.mvc.provider;

import com.haoting.mvc.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Random;

/**
 * id生成工具
 * 
 * @author haoting.wang
 * @version 1.0 2016-8-24 下午8:40:29
 */
public class IdProvider {

    private static final Logger log        = LoggerFactory.getLogger(IdProvider.class);
    private String              hostAddr;
    private UniqueTimer         timer;

    private static IdProvider   idProvider = new IdProvider();

    private IdProvider(){
        try {

            timer = new UniqueTimer();

            InetAddress addr = InetAddress.getLocalHost();

            hostAddr = addr.getHostAddress();
            hostAddr = hostAddr.substring(hostAddr.lastIndexOf(".") + 1);
            DecimalFormat df = new DecimalFormat("000");
            hostAddr = df.format(Integer.parseInt(hostAddr));

        } catch (IOException e) {
            log.error("[UniqID] Get HostAddr Error", e);
            hostAddr = String.valueOf("001");
        }

        if (StringUtils.isBlank(hostAddr) || "127.0.0.1".equals(hostAddr)) {
            hostAddr = String.valueOf("001");
        }

        if (log.isDebugEnabled()) {
            log.debug("[UniqID]hostAddr is:" + hostAddr);
        }
    }

    public static IdProvider getInstance() {
        return idProvider;
    }

    public String getUniqId() {

        long currentTime = timer.getCurrentTime();

        Random random = new Random();

        int pross = (int) ((1 + random.nextDouble()) * 100);

        return currentTime + hostAddr + pross;
    }

    private class UniqueTimer {

        private long lastTime = System.currentTimeMillis();

        public synchronized long getCurrentTime() {
            long currTime = System.currentTimeMillis();

            lastTime = Math.max(lastTime + 1, currTime);

            return lastTime;
        }
    }


	public static long getUid() {
		return System.nanoTime();
	}


	public static void main(String[] args) {

        long start = System.currentTimeMillis();
        String uid = IdProvider.getInstance().getUniqId();
        System.out.println(uid);
        System.out.println(uid.length());

        System.out.println(System.currentTimeMillis() - start);

        System.out.println(Long.MAX_VALUE);
    }
}

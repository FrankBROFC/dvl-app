package com.dvlcube.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Queue;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;

public class TokenUtils {
	private static final Queue<String> pincodes = new ConcurrentLinkedQueue<>();

	private static void generatePinCodes() {
		TreeSet<String> set = new TreeSet<>();

		// Limpa a fila
		pincodes.clear();

		for (int i = 0; i < 5000; i++) {
			String tmp = getRandomParameter();

			int rnd = ThreadLocalRandom.current().nextInt(1, 3);

			int init = tmp.length() / 2 - rnd;
			int end = init + 8;

			String token = tmp.substring(init, end).toLowerCase();

			if (!set.contains(token)) {
				set.add(token);
				pincodes.add(token);
			}
		}
	}

	private static String getRandomParameter() {
		try {
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");

			byte[] b = new byte[20];
			secureRandom.nextBytes(b);

			return Base64.getEncoder().encodeToString(b).replaceAll("\\W", "");

		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}

		return null;
	}

	public static String getToken() {
		if (pincodes.size() == 0)
			generatePinCodes();

		return pincodes.poll();
	}
}

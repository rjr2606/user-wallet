package com.assignment.util;

import static com.assignment.util.Constants.*;

import java.time.Instant;
public class GenerateTransID {

	public static String generateTransId(long fromWId , long toWId) {
		StringBuilder sb = new StringBuilder();
		long time = Instant.now().toEpochMilli();
		sb.append(fromWId).append(SEPARATOR).append(toWId).append(SEPARATOR).append(time);
		return sb.toString();
	}
}

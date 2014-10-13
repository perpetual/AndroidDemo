package com.example.androiddemo.utils;

public class StackClassName {

	public static String topClassName() {
		return stackClassName(0);
	}
	
	public static String stackClassName(int index) {
		final String emptyString = new String();
		StackTraceElement[] traces = Thread.currentThread().getStackTrace();
		if (index < 0 || index >= traces.length) {
			return emptyString;
		}
		
		String className = traces[index].getClassName();
		int dotIndex = className.lastIndexOf('.');
		if (dotIndex >= 0) {
			return className.substring(dotIndex + 1);
		}
		return emptyString;
	}
}

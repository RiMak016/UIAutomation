package com.demo.qa.common;

import java.sql.Connection;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;

import javax.swing.text.DateFormatter;

public class Constants {

	public static String browser = null;

	public static String chrome_driver_path = null;

	public static String firefox_driver_path = null;

	public static boolean is_initialized = false;

	public static boolean is_initialized_variable = false;

	public static Properties prop = new Properties();

	public static int explicit_wait_time = 0;

	public static int implicit_wait_time = 0;

	public static String base_url = null;
	
}

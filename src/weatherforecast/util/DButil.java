package weatherforecast.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import android.database.sqlite.SQLiteDatabase;

/*
 * ���ڻ�ȡ���ݿ����ӵĲ�������
 */
public class DButil {
	
	public static SQLiteDatabase database; 

	
			static String DB_PATH = "/data/data/weatherforecast.view/databases/"; 
	        static String DB_NAME = "weather.db3"; 
	 
	 
	        public static String url=DB_PATH+DB_NAME;
	 
	public static SQLiteDatabase getDB(){
		SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(url, null);
		return db;
		
	}
}

package weatherforecast.dao;
import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import weatherforecast.model.City_ID;
import weatherforecast.util.*;
public class CityDao {
	public static ArrayList<City_ID>  getIDByName(String name) {//执行总查询
		ArrayList<City_ID> list1 =new ArrayList<City_ID>();
		if(!name.equals(""))
		{
			list1=CityDao.getIDByNameEN(name);
			ArrayList<City_ID> list2=CityDao.getIDByNameCN(name);
			//ArrayList<City_ID> list=new ArrayList<City_ID>();
			
			list1.addAll(list2);
			
		}
		
		return list1;
		
	}
	public static ArrayList<City_ID>  getIDByNameCN(String name) {//通过中文名查询
		ArrayList<City_ID> list=new ArrayList<City_ID>();
		SQLiteDatabase db=DButil.getDB();
		String sql="select * from  city where namecn like '%"+name+"%' or districtcn" +
				" like '%"+name+"%' or provcn like '%"+name+"%'";
		System.out.println(sql);
		Cursor cursor =db.rawQuery(sql, null);
		
		while(cursor.moveToNext()){
			City_ID city=new City_ID(cursor.getInt(0),cursor.getString(1),cursor.getString(2),
					cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));
			list.add(city);
		}
		
		return list;
		
	}
	public static ArrayList<City_ID> getIDByNameEN (String name){//通过英文名查询
		ArrayList<City_ID> list=new ArrayList<City_ID>();
		SQLiteDatabase db=DButil.getDB();
		String str1 = "";
		for(int i=0;i<name.length();i++){
			str1=str1+"%"+name.charAt(i);
		}
		str1=str1+"%";
		
		String sql="select * from  city where districten like '"+str1+"' or " +
				"nameen like'"+str1+"' or proven like'"+str1+"'";
		System.out.println(sql);
		Cursor cursor =db.rawQuery(sql, null);
		
		while(cursor.moveToNext()){
			City_ID city=new City_ID(cursor.getInt(0),cursor.getString(1),cursor.getString(2),
					cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));
			list.add(city);
		}
		
		return list;
			
		
	}
	public static ArrayList<City_ID> getIDByNameCN1 (String name){//通过1级namecn匹配id（县，区）
		ArrayList<City_ID> list=new ArrayList<City_ID>();
		SQLiteDatabase db=DButil.getDB();
		String sql="select * from  city where namecn like'%"+name+"%'";
		Cursor cursor =db.rawQuery(sql, null);
		
		while(cursor.moveToNext()){
			City_ID city=new City_ID(cursor.getInt(0),cursor.getString(1),cursor.getString(2),
					cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));
			list.add(city);
		}
		
		return list;
			
		
	}
	public static ArrayList<City_ID> getIDByNameCN2 (String name){//通过2级namecn匹配id（市）
		ArrayList<City_ID> list=new ArrayList<City_ID>();
		SQLiteDatabase db=DButil.getDB();
		String sql="select * from  city where districtcn like'%"+name+"%'";
		Cursor cursor =db.rawQuery(sql, null);
		
		while(cursor.moveToNext()){
			City_ID city=new City_ID(cursor.getInt(0),cursor.getString(1),cursor.getString(2),
					cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));
			list.add(city);
		}
		
		return list;
			
		
	}
	public static ArrayList<City_ID> getIDByNameCN3 (String name){//通过3级namecn匹配id（省）
		ArrayList<City_ID> list=new ArrayList<City_ID>();
		SQLiteDatabase db=DButil.getDB();
		String sql="select * from  city where provcn like'%"+name+"%'";
		Cursor cursor =db.rawQuery(sql, null);
		
		while(cursor.moveToNext()){
			City_ID city=new City_ID(cursor.getInt(0),cursor.getString(1),cursor.getString(2),
					cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));
			list.add(city);
		}
		
		return list;
			
		
	}
	public static ArrayList<City_ID> getIDByNameEN1 (String name){//通过1级namecn匹配id（县，区）
		ArrayList<City_ID> list=new ArrayList<City_ID>();
		SQLiteDatabase db=DButil.getDB();
		String str1 = null;
		for(int i=0;i<name.length();i++){
			str1+="%"+name.charAt(i);
		}
		str1+="%";
		
		String sql="select * from  city where nameen like'"+str1+"'";
		Cursor cursor =db.rawQuery(sql, null);
		
		while(cursor.moveToNext()){
			City_ID city=new City_ID(cursor.getInt(0),cursor.getString(1),cursor.getString(2),
					cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));
			list.add(city);
		}
		
		return list;
			
		
	}
	public static ArrayList<City_ID> getIDByNameEN2 (String name){//通过1级namecn匹配id（县，区）
		ArrayList<City_ID> list=new ArrayList<City_ID>();
		SQLiteDatabase db=DButil.getDB();
		String str1 = null;
		for(int i=0;i<name.length();i++){
			str1+="%"+name.charAt(i);
		}
		str1+="%";
		
		String sql="select * from  city where districten like'"+str1+"'";
		Cursor cursor =db.rawQuery(sql, null);
		
		while(cursor.moveToNext()){
			City_ID city=new City_ID(cursor.getInt(0),cursor.getString(1),cursor.getString(2),
					cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));
			list.add(city);
		}
		
		return list;
			
		
	}
	public static ArrayList<City_ID> getIDByNameEN3 (String name){//通过1级namecn匹配id（县，区）
		ArrayList<City_ID> list=new ArrayList<City_ID>();
		SQLiteDatabase db=DButil.getDB();
		String str1 = null;
		for(int i=0;i<name.length();i++){
			str1+="%"+name.charAt(i);
		}
		str1+="%";
		
		String sql="select * from  city where proven like'"+str1+"'";
		Cursor cursor =db.rawQuery(sql, null);
		
		while(cursor.moveToNext()){
			City_ID city=new City_ID(cursor.getInt(0),cursor.getString(1),cursor.getString(2),
					cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));
			list.add(city);
		}
		
		return list;
		
	}
}

package weatherforecast.dao;
/**
 * �ṩ���в�ѯ,��ӳ��е���
 * @author ���ҳ�
 *
 */
import java.util.ArrayList;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import weatherforecast.model.City_ID;
import weatherforecast.util.*;
public class CityDao {
	public static City_ID getCityByID(int id){
		SQLiteDatabase db=DButil.getDB();
		String sql="select * from city where id="+id;
		Cursor cursor=db.rawQuery(sql, null);
		cursor.moveToNext();
		City_ID city=new City_ID(cursor.getInt(0),cursor.getString(1),cursor.getString(2),
				cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));
		return city;
	}
	public static City_ID getCurrentCityID(String name){//���ݶ�λ���е����ƻ�ȡʵ����
		String name1=(String) name.subSequence(0, name.length()-1);
		return CityDao.getIDByNameCN1(name1).get(0);
		
	}
	public static boolean isExist(City_ID mycity){
		SQLiteDatabase db=DButil.getDB();
		String sql="select * from icity where id="+mycity.getId();
		Cursor cursor =db.rawQuery(sql, null);
		boolean flag;
		flag=cursor.moveToNext();
		return flag;
	}
	public static void deleteCity(int id){
		SQLiteDatabase db=DButil.getDB();
		String sql="delete from icity where id="+id;
		db.execSQL(sql);
	}
	public static ArrayList<City_ID> showicity(){//�����ղصĳ��еļ���
		SQLiteDatabase db=DButil.getDB();
		ArrayList<City_ID> list1 =new ArrayList<City_ID>();
		String sql="select * from icity";
        Cursor cursor =db.rawQuery(sql, null);
		
		while(cursor.moveToNext()){
			City_ID city=new City_ID(cursor.getInt(0),cursor.getString(1),cursor.getString(2),
					cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));
			list1.add(city);
		}
		cursor.close();
		return list1;
	}
	public static boolean insertCity(City_ID mycity){//���ӳ��е��ղ��б�
		if(!CityDao.isExist(mycity)){
		SQLiteDatabase db=DButil.getDB();
		String sql="insert into icity values("+mycity.getId()+",'"
				+mycity.getNameen()+"','"+mycity.getNamecn()+"','"
				+mycity.getDistricten()+"','"+mycity.getDistrictcn()+"','"
				+mycity.getProven()+"','"+mycity.getProvcn()+"')";
		System.out.println(sql);
		db.execSQL(sql);
		return true;
		}else{
			return false;
		}
	}
	public static ArrayList<City_ID>  getIDByName(String name) {//ִ���ܲ�ѯ
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
	public static ArrayList<City_ID>  getIDByNameCN(String name) {//ͨ����������ѯ
		ArrayList<City_ID> list=new ArrayList<City_ID>();
		SQLiteDatabase db=DButil.getDB();
		String sql="select * from  city where namecn like '%"+name+"%' or districtcn" +
				" like '%"+name+"%' or provcn like '%"+name+"%'";
		System.out.println(sql);
		Cursor cursor =db.rawQuery(sql, null);
		
		while(cursor.moveToNext()){
			City_ID city=new City_ID(cursor.getInt(0),cursor.getString(1),cursor.getString(2),
					cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));
			System.out.println("id:"+cursor.getInt(0));
			list.add(city);
		}
		
		cursor.close();
		return list;
		
	}
	public static ArrayList<City_ID> getIDByNameEN (String name){//ͨ��Ӣ������ѯ
		ArrayList<City_ID> list=new ArrayList<City_ID>();
		SQLiteDatabase db=DButil.getDB();//��ȡ��
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
			System.out.println("id:"+cursor.getInt(0));
			list.add(city);
		}
		cursor.close();
		return list;
			
		
	}
	public static ArrayList<City_ID> getIDByNameCN1 (String name){//ͨ��1��namecnƥ��id���أ�����
		ArrayList<City_ID> list=new ArrayList<City_ID>();
		SQLiteDatabase db=DButil.getDB();
		String sql="select * from  city where namecn like'%"+name+"%'";
		Cursor cursor =db.rawQuery(sql, null);
		
		while(cursor.moveToNext()){
			City_ID city=new City_ID(cursor.getInt(0),cursor.getString(1),cursor.getString(2),
					cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));
			list.add(city);
		}
		cursor.close();
		return list;
			
		
	}
	public static ArrayList<City_ID> getIDByNameCN2 (String name){//ͨ��2��namecnƥ��id���У�
		ArrayList<City_ID> list=new ArrayList<City_ID>();
		SQLiteDatabase db=DButil.getDB();
		String sql="select * from  city where districtcn like'%"+name+"%'";
		Cursor cursor =db.rawQuery(sql, null);
		
		while(cursor.moveToNext()){
			City_ID city=new City_ID(cursor.getInt(0),cursor.getString(1),cursor.getString(2),
					cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));
			list.add(city);
		}
		cursor.close();
		return list;
			
		
	}
	public static ArrayList<City_ID> getIDByNameCN3 (String name){//ͨ��3��namecnƥ��id��ʡ��
		ArrayList<City_ID> list=new ArrayList<City_ID>();
		SQLiteDatabase db=DButil.getDB();
		String sql="select * from  city where provcn like'%"+name+"%'";
		Cursor cursor =db.rawQuery(sql, null);
		
		while(cursor.moveToNext()){
			City_ID city=new City_ID(cursor.getInt(0),cursor.getString(1),cursor.getString(2),
					cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));
			list.add(city);
		}
		cursor.close();
		return list;
			
		
	}
	public static ArrayList<City_ID> getIDByNameEN1 (String name){//ͨ��1��namecnƥ��id���أ�����
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
		cursor.close();
		return list;
			
		
	}
	public static ArrayList<City_ID> getIDByNameEN2 (String name){//ͨ��1��namecnƥ��id���أ�����
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
		cursor.close();
		return list;
			
		
	}
	public static ArrayList<City_ID> getIDByNameEN3 (String name){//ͨ��1��namecnƥ��id���أ�����
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
		cursor.close();
		return list;
		
	}
}

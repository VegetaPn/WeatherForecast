package weatherforecast.dao;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import weatherforecast.util.*;
public class CityDao {

	public static int getIDByNameCN1 (String name){//ͨ��1��namecn��ȡid���أ�����
		SQLiteDatabase db=DButil.getDB();
		String sql="select id from  city where namecn='"+name+"'";
		Cursor cursor =db.rawQuery(sql, null);
		cursor.moveToNext();
				
			return cursor.getInt(0);
			
		
	}
}

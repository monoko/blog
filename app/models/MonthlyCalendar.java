package models;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;



public class MonthlyCalendar{

	public int year;
	public int month;
	public int day;
	public int firstday;
	public int lastday;
	
	// 現在年月
	public MonthlyCalendar(){
		Calendar cal = Calendar.getInstance(); 
		this.year = cal.get(Calendar.YEAR);
		this.month = cal.get(Calendar.MONTH) + 1;
		this.day = 1;
		this.firstday = cal.get(Calendar.DAY_OF_WEEK);// 曜日(1234567)
		this.lastday = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	// 選択年月
	public MonthlyCalendar(MonthlyCalendar selectedForm){
		this.year = selectedForm.year;
		this.month = selectedForm.month;
		
		Calendar cal = Calendar.getInstance();
		cal.set(this.year, this.month-1, 1);
		this.firstday = cal.get(Calendar.DAY_OF_WEEK);
		this.lastday = cal.getActualMaximum(Calendar.DAY_OF_MONTH);		
	}
	
	// 配列の取得
	public int[][] list(){
		
		//配列の取得
		int[][] monthlyCalendarList = new int[5][7];
		int row = 0;
		int col = this.firstday-1;
		for (int date = 1; date <= this.lastday; date++) {
			monthlyCalendarList[row][col] = date;
			if (col == 6) {
				row++;
				col = 0;//初期化
			} else {
				col++;
			}
		}
		return monthlyCalendarList;
	}
	
	// 年のハッシュマップ  セレクトボックス用
	public LinkedHashMap<String,String> yearList(){
		LinkedHashMap<String,String> yearList = new LinkedHashMap<>();
		Calendar cal = Calendar.getInstance();
		this.year = cal.get(Calendar.YEAR);
		for(int i = this.year-10; i <= this.year; i++){
			yearList.put(Integer.toString(i),Integer.toString(i));
		}
		return yearList;
	}
}
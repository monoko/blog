package models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import models.Day;



public class MonthlyCalendar{

	public int year;
	public int month;
	public int day;
	public int firstday;
	public int lastday;
	
	// 現在年月　コンストラクタ
	public MonthlyCalendar(){
		Calendar cal = Calendar.getInstance(); 
		this.year = cal.get(Calendar.YEAR);
		this.month = cal.get(Calendar.MONTH) + 1;
		this.day = 1;
		this.firstday = cal.get(Calendar.DAY_OF_WEEK);// 曜日(1234567)
		this.lastday = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	// 選択年月　コンストラクタ
	public MonthlyCalendar(MonthlyCalendar selectedForm){
		this.year = selectedForm.year;
		this.month = selectedForm.month;
		
		Calendar cal = Calendar.getInstance();
		cal.set(this.year, this.month-1, 1);
		this.firstday = cal.get(Calendar.DAY_OF_WEEK);
		this.lastday = cal.getActualMaximum(Calendar.DAY_OF_MONTH);		
	}
	
	// 配列の取得
	public ArrayList<Day> calendarList(){
		ArrayList<Day> calendarList = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		this.lastday = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		int week = cal.get(Calendar.DAY_OF_WEEK);
		for(int i = 1; i <= this.lastday; i++){
		    cal.set(day, i);
		    calendarList.add(new Day(day ,week));
		}
		return calendarList;
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
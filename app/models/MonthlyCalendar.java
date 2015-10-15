package models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;



public class MonthlyCalendar{

	private int year;
	private int month;
	private int day;
	private int firstweek;
	private int lastday;
	private List<Article> result;

	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}
	
	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public int getFirstday() {
		return firstweek;
	}

	public int getLastday() {
		return lastday;
	}

	public List<Article> getResult() {
		return result;
	}

	// 年月コンストラクタ　初期表示
	public MonthlyCalendar(){
		Calendar cal = Calendar.getInstance(); 
		this.year = cal.get(Calendar.YEAR);
		this.month = cal.get(Calendar.MONTH) + 1;
		this.day = 1;
		cal.set(this.year, this.month-1, 1);
		this.initialize(cal);
	}
	
	// 年月コンストラクタ　選択後
	public MonthlyCalendar(MonthlyCalendar selectedForm){
		this.year = selectedForm.year;
		this.month = selectedForm.month;
		Calendar cal = Calendar.getInstance();
		cal.set(this.year, this.month-1, 1);
		this.initialize(cal);
	}
	
	// コンストラクタの共通処理
	public void initialize(Calendar cal) {
		this.firstweek = cal.get(Calendar.DAY_OF_WEEK);
		this.lastday = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		this.result = this.monthlyArticleList();
	}
	
	
	
	// カレンダーリスト取得
	public ArrayList<Day> calendarList(){
		ArrayList<Day> calendarList = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(this.year, this.month-1, 1);
		int week = cal.get(Calendar.DAY_OF_WEEK);
		
		for(int i = 1; i < this.firstweek; i++){
			calendarList.add(new Day(0 ,0, false));
		}
		
		for(int i = 1; i <= this.lastday; i++ ){
			int day = i;
			calendarList.add(new Day(day ,week, this.isArticle(day)));
			if (week == 7){
				week = 1;
			}else{
				week++;
			}
		}
		
		int lastWeek = calendarList.get(calendarList.size()-1).week;
		if(lastWeek != 7){
			for(int i = lastWeek; i < 7; i++){
			calendarList.add(new Day(0 ,0, false));
			}
		}
		return calendarList;
	}
	
	// 各日付に記事があるか否かを判定
	public boolean isArticle(int day){
		
		for (Article article: this.result){
			
			// 対象日
			Calendar cal = Calendar.getInstance();
			cal.clear();
			cal.set(this.year, this.month-1, day, 0, 0, 0);
			Date selectedDay = cal.getTime();
			
			// 記事の書かれた日
			Date date = article.created_at;
			Date createdDay = DateUtils.truncate(date, Calendar.DAY_OF_MONTH);
			
			if (createdDay.equals(selectedDay)){
				return true;
			}
		}
		return false;
	}
	

	// 年のハッシュマップ  セレクトボックス用
	public LinkedHashMap<String,String> yearList(){
		LinkedHashMap<String,String> yearList = new LinkedHashMap<>();
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		//　過去10年を取得
		for(int i = year; i >= year-10; i--){
			yearList.put(Integer.toString(i),Integer.toString(i));
		}
		return yearList;
	}
	
	// 月のハッシュマップ  セレクトボックス用
	public LinkedHashMap<String,String> monthList(){
		LinkedHashMap<String,String> monthList = new LinkedHashMap<>();
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH)+1;
		monthList.put(Integer.toString(month),Integer.toString(month));
		for(int i = 1; i <= 12; i++){
			if(i == month){
			}else{
				monthList.put(Integer.toString(i),Integer.toString(i));
			}
		}
		return monthList;
	}	
	
	// 月の記事一覧
	private List<Article> monthlyArticleList(){
		
		int year = this.year;
		int month = this.month;
		
		List<Article> monthlyArticleList = Article.getMonthly(year, month);

		return monthlyArticleList;
	}
}
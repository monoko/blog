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
	private int firstweek;
	private int lastday;
	private List<Article> result;

	public int getYear() {
		return year;
	}
	
	public int getMonth() {
		return month;
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
		cal.set(this.year, this.month-1, 1);
		this.initialize(cal);
	}
	
	// 年月コンストラクタ　選択後
	public MonthlyCalendar(int year, int month){
		this.year = year;
		this.month = month;
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
		
		// 初日(1日)が日曜日以外の場合 初日より前を0で埋める
		for(int i = 1; i < this.firstweek; i++){
			calendarList.add(new Day(0 ,0, false));
		}
		
		// 初日～最終日
		for(int i = 1; i <= this.lastday; i++ ){
			int day = i;
			calendarList.add(new Day(day ,week, this.isArticle(day)));
			if (week == 7){
				week = 1;
			}else{
				week++;
			}
		}
		
		// 最終日が土曜日以外の場合 最終日より後を0で埋める
		int lastWeek = calendarList.get(calendarList.size()-1).week;
		for(int i = lastWeek; i < 7; i++){
			calendarList.add(new Day(0 ,0, false));
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
		return Article.getMonthly(this.year, this.month);
	}
}
package models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;

import com.avaje.ebean.ExpressionList;

import play.db.ebean.Model.Finder;



public class MonthlyCalendar{

	private int year;
	private int month;
	private int day;
	private int firstday;
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
		return firstday;
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
		this.ReturnValue(cal);
	}
	
	// 年月コンストラクタ　選択後
	public MonthlyCalendar(MonthlyCalendar selectedForm){
		this.year = selectedForm.year;
		this.month = selectedForm.month;
		Calendar cal = Calendar.getInstance();
		cal.set(this.year, this.month-1, 1);
		this.ReturnValue(cal);
	}
	
	// コンストラクタの共通処理
	public void ReturnValue(Calendar cal) {
		this.firstday = cal.get(Calendar.DAY_OF_WEEK);
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
		
		for(int i = 1; i < this.firstday; i++){
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
		
		int lastWeek = calendarList.get(this.firstday+this.lastday-2).week;
		if(lastWeek != 7){
			for(int i = 1; i <= 7-lastWeek; i++){
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
		for(int i = year-10; i <= year; i++){
			yearList.put(Integer.toString(i),Integer.toString(i));
		}
		return yearList;
	}
	
	// 月の記事一覧
	public static Finder<Date, Article> find = new Finder<Date, Article>(Date.class, Article.class);
	
	private List<Article> monthlyArticleList(){
		
		Calendar cal = Calendar.getInstance();
		cal.clear();
		
		cal.set(this.year, this.month-1, 1, 0, 0, 0);
		Date startDate = cal.getTime();
		
		cal.set(this.year, this.month, 1, 0, 0, 0);
		Date endDate = cal.getTime();
		
		ExpressionList<Article> monthlyArticles = 
				Article.find.where().between("created_at", startDate, endDate);
		List<Article> monthlyArticleList =  monthlyArticles.findList();
		
		return monthlyArticleList;
	}
}
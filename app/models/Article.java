package models;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.avaje.ebean.Expr;
import com.avaje.ebean.ExpressionList;

import play.db.ebean.Model;

@Entity
public class Article extends Model {
	
	@Id
	public Long id;
    public String title;
    public String body;
    public Date created_at;
    public Date updated_at;
    @Transient
    public String search;
        
    public static Finder<Long, Article> find = new Finder<Long, Article>(Long.class, Article.class);
    
    public static List<Article> all() {
    	List<Article> article = Article.find.all();
    	return article;
    }
    
    public static void create(Article article) {
    	
    }
      
    public static void delete(Long id){
    	Article.find.ref(id).delete();
    }

   public static Article show(Long id){
    	Article article = Article.find.byId(id);
    	return article;
    }
   
   public static List<Article> search(Article filledForm){
	  
	   String search = filledForm.search;
	   search = search.replaceAll(" ","%");
	   search = search.replaceAll("　","%");
	   search = '%' + search + '%';
	   
	   ExpressionList<Article> articlelist = 
			   Article.find.where().or(Expr.like("title", search),Expr.like("body", search));
	   List<Article> articles =  articlelist.findList();
	   return articles;
   }
   
   public static List<Article> getMonthly(int year, int month) {
	    Calendar cal = Calendar.getInstance();
		cal.clear();
		
		cal.set(year, month-1, 1, 0, 0, 0);
		Date startDate = cal.getTime();
		
		cal.set(year, month, 1, 0, 0, 0);
		Date endDate = cal.getTime();
		
		ExpressionList<Article> monthlyArticles = 
				Article.find.where().between("created_at", startDate, endDate);
		List<Article> monthlyArticleList =  monthlyArticles.findList();
	    
		return monthlyArticleList;
   }
}
package models;

import java.util.Date;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.avaje.ebean.Expr;
import com.avaje.ebean.ExpressionList;

import play.data.Form;
import play.db.ebean.Model;

@Entity
public class Article extends Model {
	private static final String Select = null;
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
	   search = '%' + search + '%';
	   
	   //titleかbodyにsearchの単語があれば表示
	   ExpressionList<Article> articlelist = 
			   //Article.find.where().or(Article.find.where().ilike("title",search), Article.find.where().ilike("body",search));
			   Article.find.where().or(Expr.like("title", search),Expr.like("body", search));
	   List<Article> articles =  articlelist.findList();
	   return articles;
   }
}
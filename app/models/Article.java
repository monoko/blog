package models;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.Form;
import play.db.ebean.Model;

@Entity
public class Article extends Model {
	@Id
	public Long id;
    public String title;
    public String body;
    public Date created_at;
    public Date updated_at;
    
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
   
   
}

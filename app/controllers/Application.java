package controllers;

import java.util.Date;
import java.util.Calendar;
import models.Article;
import play.*;
import play.data.Form;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {
	  
	  static Form<Article> articleForm = Form.form(Article.class);

	  //indexArticleへリダイレクト
	  public static Result index() {
	    return redirect(routes.Application.indexArticle());
	    
	  }
	  
	  //記事一覧
	  public static Result indexArticle() {
		  return ok(
			        views.html.index.render(Article.all())
			    );
	  }
	  
	  //記事作成画面へ
	  public static Result newArticle() {
		  return ok(
			        views.html.newedit.render(articleForm)
			    );
	  }
	  
	  //記事作成完了
	  public static Result createArticle() {
		  
		  Article filledForm = articleForm.bindFromRequest().get();
		  filledForm.created_at = (Date) Calendar.getInstance().getTime();
		  filledForm.updated_at = (Date) Calendar.getInstance().getTime();
		  filledForm.save();
	      
		  return redirect(routes.Application.indexArticle());
	  }
	  
	  //記事詳細
	  public static Result showArticle(Long id) {
		  return ok(
			        views.html.show.render(Article.show(id))
			    ); 
	  }
	  
	  //記事削除
	  public static Result deleteArticle(Long id) {
		  
		  Article.delete(id);
		  
		  return redirect(routes.Application.indexArticle());
	  }
	  
	  //記事編集
	  public static Result editArticle(Long id) {
		  articleForm = articleForm.fill(Article.show(id));
		  return ok(
				  views.html.edit.render(articleForm,id)
			    );
	  }
	  
	  //記事更新
	  public static Result updateArticle(Long id) {
		  
		  Article filledForm = articleForm.bindFromRequest().get();
		  Article article= Article.show(id);
		  article.title = filledForm.title;
		  article.body = filledForm.body;
		  article.updated_at = (Date) Calendar.getInstance().getTime();
		  
		  article.save();
	      
		  return redirect(routes.Application.indexArticle());
	  }
}
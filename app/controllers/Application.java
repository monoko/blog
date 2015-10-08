package controllers;

import java.util.Calendar;
import java.util.Date;

import models.Article;
import models.MonthlyCalendar;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;


public class Application extends Controller {
	  
	  static Form<Article> articleForm = Form.form(Article.class);
	  static Form<MonthlyCalendar> monthlyForm = Form.form(MonthlyCalendar.class);

	  //indexArticleへリダイレクト
	  public static Result index() {
	    return redirect(routes.Application.indexArticle());
	    
	  }
	  
	  //記事一覧
	  public static Result indexArticle() {
		  MonthlyCalendar monthlyCalendar = new MonthlyCalendar();
		  return ok(
			        views.html.index.render(Article.all(),articleForm, monthlyCalendar ,monthlyCalendar.list(),monthlyForm, monthlyCalendar.yearList())
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
	  
	//記事検索  
	  public static Result searchArticle() {
		  Article filledForm = articleForm.bindFromRequest().get();
		  MonthlyCalendar monthlyCalendar = new MonthlyCalendar();
		  return ok(
			        views.html.index.render(Article.search(filledForm),articleForm, monthlyCalendar,monthlyCalendar.list(),monthlyForm, monthlyCalendar.yearList())
			    );
	  }
	//月別表示  
	  public static Result monthlyArticle() {
		  MonthlyCalendar selectedForm = monthlyForm.bindFromRequest().get();
		  MonthlyCalendar monthlyCalendar = new MonthlyCalendar(selectedForm);
		  return ok(
			        views.html.index.render(Article.all(),articleForm,monthlyCalendar,monthlyCalendar.list(),monthlyForm, monthlyCalendar.yearList())
			    );
	  }
  
}
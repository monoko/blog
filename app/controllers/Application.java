package controllers;

import java.util.Calendar;
import java.util.Date;
import models.Article;
import models.MonthlyCalendar;
import models.MonthlyForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.data.*;
import static play.data.Form.*; 
import models.User;
import play.*;
import play.mvc.*;
import play.mvc.Http.*;

public class Application extends Controller {
	  
	  static Form<Article> articleForm = Form.form(Article.class);
	  static Form<MonthlyForm> monthlyForm = Form.form(MonthlyForm.class);
	  //static Form<Login> loginForm = Form.form(Login.class);

	  // ログイン画面表示
	  public static Result login() {
		  return ok(views.html.login.render(Form.form(User.class)));
	  }
	  
	  // 認証
	  public static Result authenticate() {
	        Form<User> loginForm = Form.form(User.class).bindFromRequest();
	        if (loginForm.hasErrors()) {
	            return badRequest(views.html.login.render(loginForm));
	        }
	        session().clear();
	        session("name", loginForm.get().name);
	        return redirect(routes.Application.index());
	   }
	  
	  // ログアウト
	  public static Result logout() {
	        session().clear();
	        return redirect(routes.Application.login());
	  }
	  
	  //　indexArticleへリダイレクト
	  public static Result index() {
		  return redirect(routes.Application.indexArticle());
	  }
	  
	  //　記事一覧
	  public static Result indexArticle() {
		  MonthlyCalendar monthlyCalendar = new MonthlyCalendar();
		  
		  return ok(
			        views.html.index.render(
			        		monthlyCalendar.getResult(),
			        		articleForm,
			        		monthlyCalendar,
			        		monthlyCalendar.calendarList(),
			        		monthlyForm,
			        		monthlyCalendar.yearList(),
			        		monthlyCalendar.monthList(),
			        		monthlyCalendar.getResult()
			        )
			    );
	  }
	  
	  //　記事作成画面へ
	  @Security.Authenticated(Secured.class)
	  public static Result newArticle() {
		  
		  return ok(
			        views.html.newedit.render(articleForm)
			    );
	  }
	  
	  //　記事作成完了
	  @Security.Authenticated(Secured.class)
	  public static Result createArticle() {
		  
		  Article filledForm = articleForm.bindFromRequest().get();
		  filledForm.created_at = (Date) Calendar.getInstance().getTime();
		  filledForm.updated_at = (Date) Calendar.getInstance().getTime();
		  
		  filledForm.save();
	      
		  return redirect(routes.Application.indexArticle());
	  }
	  
	  //　記事詳細
	  public static Result showArticle(Long id) {
		  
		  return ok(
			        views.html.show.render(Article.show(id))
			    ); 
	  }
	  
	  //　記事削除
	  @Security.Authenticated(Secured.class)
	  public static Result deleteArticle(Long id) {
		  
		  Article.delete(id);
		  
		  return redirect(routes.Application.indexArticle());
	  }
	  
	  //　記事編集
	  @Security.Authenticated(Secured.class)
	  public static Result editArticle(Long id) {
		  articleForm = articleForm.fill(Article.show(id));
		  
		  return ok(
				  views.html.edit.render(articleForm,id)
			    );
	  }
	  
	  //　記事更新
	  @Security.Authenticated(Secured.class)
	  public static Result updateArticle(Long id) {
		  
		  Article filledForm = articleForm.bindFromRequest().get();
		  Article article= Article.show(id);
		  article.title = filledForm.title;
		  article.body = filledForm.body;
		  article.updated_at = (Date) Calendar.getInstance().getTime();
		  
		  article.save();
	      
		  return redirect(routes.Application.indexArticle());
	  }
	  
	//　全文検索  
	  public static Result searchArticle() {
		  Article filledForm = articleForm.bindFromRequest().get();
		  MonthlyCalendar monthlyCalendar = new MonthlyCalendar();
		  
		  return ok(
			        views.html.index.render(
			        		Article.search(filledForm),
			        		articleForm,
			        		monthlyCalendar,
			        		monthlyCalendar.calendarList(),
			        		monthlyForm,
			        		monthlyCalendar.yearList(),
			        		monthlyCalendar.monthList(),
			        		monthlyCalendar.getResult()
			        )
			    );
	  }
	//　カレンダーと月別検索
	  public static Result monthlyArticle() {
		  MonthlyForm selectedForm = monthlyForm.bindFromRequest().get();
		  MonthlyCalendar monthlyCalendar = new MonthlyCalendar(selectedForm.year, selectedForm.month);
		  
		  return ok(
			        views.html.index.render(
			        		monthlyCalendar.getResult(),
			        		articleForm,
			        		monthlyCalendar,
			        		monthlyCalendar.calendarList(),
			        		monthlyForm,
			        		monthlyCalendar.yearList(),
			        		monthlyCalendar.monthList(),
			        		monthlyCalendar.getResult()
			        )
			    );
	  }
}

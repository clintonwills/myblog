package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import models.*;
import views.html.admin.*;
import views.html.home.*;

public class Application extends Controller {
  
  //home page of the blog
  public static Result index() {
    return ok(views.html.home.index.render(Post.getAll()));
  }
  
  //form to handle new comments from visitors
  private static Form<Comment> commentForm = form(Comment.class);
  
  //view a particular post by specifying its id
  public static Result viewPost(Long id){
	  return ok(viewPost.render(Post.getPost(id), commentForm));
  }
  
  //save a new comment 
  public static Result saveComment(Long id){
	  Form<Comment> filledForm = commentForm.bindFromRequest();
	  if (filledForm.hasErrors())
		  return badRequest(viewPost.render(Post.getPost(id), filledForm));
	  else{
		  Comment cmt =  filledForm.get();

		  //assign the Post associated with the submitted comment before saving it to the database
		  cmt.post = Post.getPost(id);	
		  cmt.save();
		  flash("comment saved", "Your comment has been posted");
		  return redirect(routes.Application.viewPost(id));
	  }
  }
  
}
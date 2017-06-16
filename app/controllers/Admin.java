package controllers;

import play.mvc.*;
import views.html.admin.*;

//import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import models.*;
import play.data.*;

//controller for admin's actions and requests
public class Admin extends Controller {

	//Form wraps a Post and is used to create and edit a post
	private static Form<Post> postForm = form(Post.class);

	//display the login page
	//redirect to the dashboard is a user is already logged in
	public static Result loginPage() {
		if (session("username") != null)
			return redirect(routes.Admin.getDashboard());

		return ok(login.render(form(Login.class)));
	}

	//authenticate the user during logging in
	public static Result authenticate() {
		Form<Login> loginForm = form(Login.class).bindFromRequest();
		if (loginForm.hasErrors()) {
			return badRequest(login.render(loginForm));
		} else {
			session().clear();	//clear the current session so as to ...
			session("username", loginForm.get().username);	//assign a new user to the session
			return redirect(routes.Admin.getDashboard());
		}
	}

	//logout action for admin
	public static Result logout(){
		//clear the session
		session().clear();
		return redirect(routes.Admin.loginPage());
	}

	//display the admin dashboard to authorized users only
	@Security.Authenticated(Secured.class)
	public static Result getDashboard() {
		return ok(dashboard.render(Post.getAll(), session("username")));
	}

	//display a form to add a new Post
	@Security.Authenticated(Secured.class)
	public static Result add() {
		return ok(newpost.render(postForm, session("username")));
	}

	//edit a post with post-id: id
	@Security.Authenticated(Secured.class)
	public static Result edit(Long id) {
		return ok(editPost.render(id, postForm.fill(Post.getPost(id)), session("username")));
	}

	//POST action to save a new Post
	public static Result save() {
		Form<Post> filledForm = postForm.bindFromRequest();
		if (filledForm.hasErrors())
			return badRequest(newpost.render(filledForm, session("username")));
		else {
			Post newPost = filledForm.get();

			//assign the current user as the Author of the post
			newPost.createdBy = User.getUser(session("username"));
			newPost.save();
			flash("success", "New post successfully created");
			return redirect(routes.Admin.getDashboard());
		}
	}

	//POST action to update an edited post
	@Security.Authenticated(Secured.class)
	public static Result update(Long id){
		Form<Post> filledForm = postForm.bindFromRequest();
		if (filledForm.hasErrors())
			return badRequest(editPost.render(id, filledForm, session("username")));
		else {
			filledForm.get().update(id);
			flash("update", "existing post updated");
			return redirect(routes.Admin.getDashboard());
		}
	}

	public static Result view(Long id) {
		return TODO;
	}

	//display all comments
	@Security.Authenticated(Secured.class)
	public static Result viewComments(){
		return ok(comment.render(Comment.find.all(), session("username")));
	}

	//delete a post
	@Security.Authenticated(Secured.class)
	public static Result delete(Long id) {
		//delete post
		Post.getPost(id).delete();
		flash("delete", "post deleted");
		return redirect(routes.Admin.getDashboard());
	}

	// to handle user login
	public static class Login {
		public String username;
		public String password;

		public String validate() {
			if (User.authenticate(username, password) == null)
				return "Invalid Username or Password";
			else
				return null;
		}
	}

}

package controllers;

import play.*;
import play.mvc.*;
import play.mvc.Http;
import play.mvc.Http.Context;
import models.*;

//class is used to authenticate users
//ensures that users are logged in before they can peform 
//certain actions
public class Secured extends Security.Authenticator{

	//returns the username who is currently logged in 
	//or null if there isn't one
	@Override
	public String getUsername(Context ctx){
		return ctx.session().get("username");
	}

	//return to the login page if user is not logged in
	@Override
    public Result onUnauthorized(Context ctx) {
        return redirect(routes.Admin.loginPage());
    }
}

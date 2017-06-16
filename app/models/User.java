package models;

import play.data.validation.Constraints.*;
import play.db.ebean.*;
import javax.persistence.*;
import java.util.*;

@Entity
public class User extends Model{
	
	@Id
	public Long id;
	
	@Required
	public String username;
	@Required
	public String password;
	
	//a user has one or more posts
	@OneToMany(mappedBy="createdBy")
	public List<Post> posts = new ArrayList<Post>();
	
	//used to query the User table
	public static Finder<Long, User> find = new Finder(Long.class, User.class);
	
	public User(){}
	
	public User(String username, String password){
		this.username = username;
		this.password = password;
	}
	
	//authenticate that the username and password belong to a particular User
	//return that user or null if not found
	public static User authenticate(String username, String password){
		return find.where().eq("username", username).eq("password", password).findUnique();
	}
	
	//return a user based on the passed username
	public static User getUser(String username){
		return find.where().eq("username", username).findUnique();
	}
}

package models;

import play.data.format.Formats;
import play.data.validation.Constraints.*;
import play.db.ebean.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.persistence.*;

@Entity
public class Post extends Model{

	@Id
	public Long id;

	//title of the post
	@Required
	public String subject;

	@Required
	@Column(columnDefinition = "TEXT")
	public String content;

	//creation date/time of a post
	public Date created = new Date();

	//each post has a many to one relationship to users
	//i.e. A user can wirte zero or more Posts
	//User foreign key on Post table
	@ManyToOne
	public User createdBy;

	//each post has zero or more comments
	//cascade enables us delete the children rows when the parent is deleted
	@OneToMany(mappedBy="post", cascade = CascadeType.REMOVE)
	public List<Comment> comments = new ArrayList<Comment>();

	//Finder used to querry the Post table
	public static Finder<Long, Post> find = new Finder(Long.class, Post.class);

	public Post(){}

	public Post(String subject, String content){
		this.subject = subject;
		this.content = content;
	}


	//returns a List of all the Posts
	public static List<Post> getAll(){
		return find.orderBy("created desc").findList();
	}

	public static Post getPost(Long id){
		return find.byId(id);
	}

	public static void delete(Long id){
		//Comment.deleteCommentByPost(id);

		find.byId(id).delete();
	}

	public String getSubject(Long id){
		return Post.getPost(id).subject;
	}

	//returns the content for a particular post
	public String getContent(Long id){
		return Post.getPost(id).content;
	}

	//returns the formatted creatiion date for a articular post
	public String getDate(Long id){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		return dateFormat.format(Post.getPost(id).created);
	}

	public static void save(Post post){
		post.save();
	}

	public String author(){
		return createdBy.username;
	}
}

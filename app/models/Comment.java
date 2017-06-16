package models;

import play.data.format.Formats;
import play.data.validation.Constraints.*;
import play.db.ebean.*;
import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
public class Comment extends Model{

	@Id
	public Long id;

	@Required
  @Column(columnDefinition = "TEXT")
	public String content;

	//Date of cretion is implicitely assigned based on the
	//current time and date
    public Date created = new Date();

    //Comment has a many to one relationship to Post
    //foreign key for Post in Comment table
	@ManyToOne
	public Post post;

	//Finder used to query the Comment table
	public static Finder<Long, Comment> find = new Finder(Long.class, Comment.class);

	//default constructor
	public Comment(){}

	public Comment(String content){
		this.content = content;
	}

	public Comment getComment(Long id){
		return find.byId(id);
	}

	public String getContent(Long id){
		return find.byId(id).content;
	}

	//return the creation date of a comment formatted as yy/mm/dd
	public String getDate(Long id){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		return dateFormat.format(getComment(id).created);
	}


}

package Model;

import java.util.ArrayList;
import java.util.List;

public class Article {

	private String title;
	private String subtitle;
	private String author;
	private String date;
	private String url;
	private String diary;
	private List<Commentary> commentaries;
	
	// Constructor

	public Article(String title, String subtitle, String author, String date,
			String url, String diary) {
		super();
		commentaries = new ArrayList<Commentary>();
		this.title = title;
		this.subtitle = subtitle;
		this.author = author;
		this.date = date;
		this.url = url;
		this.diary = diary;
	}
	
	// Public Methods
	
	public void addCommentary(Commentary commentary){
		this.commentaries.add(commentary);
	}

	// Getters and Setters

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Commentary> getCommentaries() {
		return commentaries;
	}

	public void setCommentaries(List<Commentary> commentaries) {
		this.commentaries = commentaries;
	}

	public String getDiary() {
		return diary;
	}

	public void setDiary(String diary) {
		this.diary = diary;
	}

	// Equals and HashCode

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Article other = (Article) obj;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
	
	//To String
	
	@Override
	public String toString() {
		return "\nTitle= " + title + "\nSubtitle= " + subtitle
				+ "\nAuthor= " + author + "\nDate= " + date + "\nUrl= " + url
				+ ", Diary= " + diary + " \nCommentaries\n" + commentaries + "";
	}

}

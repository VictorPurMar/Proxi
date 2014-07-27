package Model;

public class Diary {
	
	private String diaryName;
	
	//General data
	private String titleRegEx;
	private String subtitleRegEx;
	private String authorRegEx;
	private String dateRegEx;
	private String urlRegEx;
	private String diaryNameRegEx;
	
	//Commentary data
	private String commentaryRegEx;
	private String commentNumberRegEx;
	private String commentAuthorRegEx;
	private String commentTimeRegEx;
	private String commentTextRegEx;
	
	//Constructor
	
	public Diary(String diaryName, String titleRegEx, String subtitleRegEx,
			String authorRegEx, String dateRegEx, String urlRegEx,
			String diaryNameRegEx, String commentaryRegEx,
			String commentNumberRegEx, String commentAuthorRegEx,
			String commentTimeRegEx, String commentTextRegEx) {
		super();
		this.diaryName = diaryName;
		this.titleRegEx = titleRegEx;
		this.subtitleRegEx = subtitleRegEx;
		this.authorRegEx = authorRegEx;
		this.dateRegEx = dateRegEx;
		this.urlRegEx = urlRegEx;
		this.diaryNameRegEx = diaryNameRegEx;
		this.commentaryRegEx = commentaryRegEx;
		this.commentNumberRegEx = commentNumberRegEx;
		this.commentAuthorRegEx = commentAuthorRegEx;
		this.commentTimeRegEx = commentTimeRegEx;
		this.commentTextRegEx = commentTextRegEx;
	}
	
	//Getters and Setters

	public String getDiaryName() {
		return diaryName;
	}

	public void setDiaryName(String diaryName) {
		this.diaryName = diaryName;
	}

	public String getTitleRegEx() {
		return titleRegEx;
	}

	public void setTitleRegEx(String titleRegEx) {
		this.titleRegEx = titleRegEx;
	}

	public String getSubtitleRegEx() {
		return subtitleRegEx;
	}

	public void setSubtitleRegEx(String subtitleRegEx) {
		this.subtitleRegEx = subtitleRegEx;
	}

	public String getAuthorRegEx() {
		return authorRegEx;
	}

	public void setAuthorRegEx(String authorRegEx) {
		this.authorRegEx = authorRegEx;
	}

	public String getDateRegEx() {
		return dateRegEx;
	}

	public void setDateRegEx(String dateRegEx) {
		this.dateRegEx = dateRegEx;
	}

	public String getUrlRegEx() {
		return urlRegEx;
	}

	public void setUrlRegEx(String urlRegEx) {
		this.urlRegEx = urlRegEx;
	}

	public String getDiaryNameRegEx() {
		return diaryNameRegEx;
	}

	public void setDiaryNameRegEx(String diaryNameRegEx) {
		this.diaryNameRegEx = diaryNameRegEx;
	}

	public String getCommentaryRegEx() {
		return commentaryRegEx;
	}

	public void setCommentaryRegEx(String commentaryRegEx) {
		this.commentaryRegEx = commentaryRegEx;
	}

	public String getCommentNumberRegEx() {
		return commentNumberRegEx;
	}

	public void setCommentNumberRegEx(String commentNumberRegEx) {
		this.commentNumberRegEx = commentNumberRegEx;
	}

	public String getCommentAuthorRegEx() {
		return commentAuthorRegEx;
	}

	public void setCommentAuthorRegEx(String commentAuthorRegEx) {
		this.commentAuthorRegEx = commentAuthorRegEx;
	}

	public String getCommentTimeRegEx() {
		return commentTimeRegEx;
	}

	public void setCommentTimeRegEx(String commentTimeRegEx) {
		this.commentTimeRegEx = commentTimeRegEx;
	}

	public String getCommentTextRegEx() {
		return commentTextRegEx;
	}

	public void setCommentTextRegEx(String commentTextRegEx) {
		this.commentTextRegEx = commentTextRegEx;
	}
	
	// Equals and HashCode

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((diaryName == null) ? 0 : diaryName.hashCode());
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
		Diary other = (Diary) obj;
		if (diaryName == null) {
			if (other.diaryName != null)
				return false;
		} else if (!diaryName.equals(other.diaryName))
			return false;
		return true;
	}
	
	//To String

	@Override
	public String toString() {
		return "Diary [diaryName=" + diaryName + ", titleRegEx=" + titleRegEx
				+ ", subtitleRegEx=" + subtitleRegEx + ", authorRegEx="
				+ authorRegEx + ", dateRegEx=" + dateRegEx + ", urlRegEx="
				+ urlRegEx + ", diaryNameRegEx=" + diaryNameRegEx
				+ ", commentaryRegEx=" + commentaryRegEx
				+ ", commentNumberRegEx=" + commentNumberRegEx
				+ ", commentAuthorRegEx=" + commentAuthorRegEx
				+ ", commentTimeRegEx=" + commentTimeRegEx
				+ ", commentTextRegEx=" + commentTextRegEx + "]";
	}
	
	
	
	
}

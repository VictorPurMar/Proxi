package Model;

public class Commentary {

	private String nickName;
	private String date;
	private int number;
	private String commentary;

	// Constructor
	public Commentary(String nickName, String date, int number,
			String commentary) {
		super();
		this.nickName = nickName;
		this.date = date;
		this.number = number;
		this.commentary = commentary;
	}

	// Getters and Setters

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getCommentary() {
		return commentary;
	}

	public void setCommentary(String commentary) {
		this.commentary = commentary;
	}

	// Equals and HashCode

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + number;
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
		Commentary other = (Commentary) obj;
		if (number != other.number)
			return false;
		return true;
	}

	// To String

	@Override
	public String toString() {
		return "\nCommentary [nickName=" + nickName + ", date=" + date
				+ ", number=" + number + ", commentary=" + commentary + "]";
	}

}

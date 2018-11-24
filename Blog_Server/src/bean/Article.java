package bean;

import java.sql.Timestamp;

public class Article implements java.io.Serializable {
	
	
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
		private Long id;
		private String title;
		private String content;
		private String keyWords;
		private String description;
		private String columnld;
		private String label;
		private String titlelmgs;
		private String status;
		private String type;
		private Timestamp releaseTime;
		private String author;
		
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getKeyWords() {
			return keyWords;
		}
		public void setKeyWords(String keyWords) {
			this.keyWords = keyWords;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getColumnld() {
			return columnld;
		}
		public void setColumnld(String columnld) {
			this.columnld = columnld;
		}
		public String getLabel() {
			return label;
		}
		public void setLabel(String label) {
			this.label = label;
		}
		public String getTitlelmgs() {
			return titlelmgs;
		}
		public void setTitlelmgs(String titlelmgs) {
			this.titlelmgs = titlelmgs;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		
		public Timestamp getReleaseTime() {
			return releaseTime;
		}
		public void setReleaseTime(Timestamp releaseTime) {
			this.releaseTime = releaseTime;
		}
		public String getAuthor() {
			return author;
		}
		public void setAuthor(String author) {
			this.author = author;
		}
		
}

package dev.lansdon.models;

import javax.persistence.*;

@Entity
@Table(name="info_request")
public class InfoRequest {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(name="request")
	private String requestText;
	@Column(name="response")
	private String responseText;
	private Boolean viewed;

	public InfoRequest() {
		requestText = "";
		responseText = "";
		viewed = false;
	}
	
	public String getRequestText() {
		return requestText;
	}
	public void setRequestText(String requestText) {
		this.requestText = requestText;
	}
	public String getResponseText() {
		return responseText;
	}
	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Boolean getViewed() { return viewed; }
	public void setViewed(Boolean viewed) { this.viewed = viewed; }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((requestText == null) ? 0 : requestText.hashCode());
		result = prime * result + ((responseText == null) ? 0 : responseText.hashCode());
		result = prime * result + ((viewed == null) ? 0 : viewed.hashCode());
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
		InfoRequest other = (InfoRequest) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (requestText == null) {
			if (other.requestText != null)
				return false;
		} else if (!requestText.equals(other.requestText))
			return false;
		if (responseText == null) {
			if (other.responseText != null)
				return false;
		} else if (!responseText.equals(other.responseText))
			return false;
		if (viewed == null) {
			if (other.viewed != null)
				return false;
		} else if (!viewed.equals(other.viewed))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "InfoRequest [id=" + id + ", requestText=" + requestText
				+ ", responseText=" + responseText + ", viewed=" + viewed + "]";
	}
}

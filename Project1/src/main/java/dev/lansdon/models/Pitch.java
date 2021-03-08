package dev.lansdon.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="pitch")
public class Pitch {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@OneToOne
	@JoinColumn(name="story_id")
	private Story story;
	@ManyToOne
	@JoinColumn(name="status_id")
	private PitchStatus status;
	@ManyToOne
	@JoinColumn(name="asst_status_id")
	private PitchStatus asstStatus;
	@ManyToOne
	@JoinColumn(name="editor_status_id")
	private PitchStatus editorStatus;
	@ManyToOne
	@JoinColumn(name="sr_status_id")
	private PitchStatus srStatus;
	@Column(name="reject_reason")
	private String rejectReason;
	@Column(name="pending_date")
	private LocalDate pendingDate;
	@Column(name="author_id")
	private Integer author;

	public Pitch() {
		PitchStatus ps = new PitchStatus();
		ps.setId(1);
		ps.setName("Pending");
		status = ps;
		asstStatus = ps;
		editorStatus = ps;
		srStatus = ps;
	}
	
	public String getRejectReason() {
		return rejectReason;
	}
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Story getStory() {
		return story;
	}
	public void setStory(Story story) {
		this.story = story;
	}
	public PitchStatus getStatus() {
		return status;
	}
	public void setStatus(PitchStatus status) {
		this.status = status;
	}
	public PitchStatus getAsstStatus() {
		return asstStatus;
	}
	public void setAsstStatus(PitchStatus asstStatus) {
		this.asstStatus = asstStatus;
	}
	public PitchStatus getEditorStatus() {
		return editorStatus;
	}
	public void setEditorStatus(PitchStatus editorStatus) {
		this.editorStatus = editorStatus;
	}
	public PitchStatus getSrStatus() {
		return srStatus;
	}
	public void setSrStatus(PitchStatus srStatus) {
		this.srStatus = srStatus;
	}
	public LocalDate getPendingDate() { return pendingDate; }
	public void setPendingDate(LocalDate pendingDate) { this.pendingDate = pendingDate; }
	public Integer getAuthor() { return author; }
	public void setAuthor(Integer author) { this.author = author; }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((asstStatus == null) ? 0 : asstStatus.hashCode());
		result = prime * result + ((editorStatus == null) ? 0 : editorStatus.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((srStatus == null) ? 0 : srStatus.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((story == null) ? 0 : story.hashCode());
		result = prime * result + ((rejectReason == null) ? 0 : rejectReason.hashCode());
		result = prime * result + ((pendingDate == null) ? 0 : pendingDate.hashCode());
		result = prime * result + ((author == null) ? 0 : author.hashCode());
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
		Pitch other = (Pitch) obj;
		if (asstStatus == null) {
			if (other.asstStatus != null)
				return false;
		} else if (!asstStatus.equals(other.asstStatus))
			return false;
		if (editorStatus == null) {
			if (other.editorStatus != null)
				return false;
		} else if (!editorStatus.equals(other.editorStatus))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (srStatus == null) {
			if (other.srStatus != null)
				return false;
		} else if (!srStatus.equals(other.srStatus))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (story == null) {
			if (other.story != null)
				return false;
		} else if (!story.equals(other.story))
			return false;
		if (rejectReason == null) {
			if (other.rejectReason != null)
				return false;
		} else if (!rejectReason.equals(other.rejectReason))
			return false;
		if (pendingDate == null) {
			if (other.pendingDate != null)
				return false;
		} else if (!pendingDate.equals(other.pendingDate))
			return false;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Pitch [id=" + id + ", story=" + story + ", status=" + status + ", asstStatus=" + asstStatus
				+ ", editorStatus=" + editorStatus + ", srStatus=" + srStatus + "rejectReason=" + rejectReason
				+ "pendingDate=" + pendingDate + "]";
	}
}

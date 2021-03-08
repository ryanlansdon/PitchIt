package dev.lansdon.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="app_user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String username;
	@Column(name="passwd")
	private String password;
	@Column(name="first_name")
	private String firstName;
	@Column(name="last_name")
	private String lastName;
	@ManyToOne
	@JoinColumn(name="role_id")
	private Role role;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="genre_committee",
			joinColumns=@JoinColumn(name="editor_id"),
			inverseJoinColumns=@JoinColumn(name="genre_id"))
	private Set<Genre> genres;
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name="editor_id")
	private Set<InfoRequest> outRequests;
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name="requested_id")
	private Set<InfoRequest> inRequests;
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name="author_id")
	private Set<Pitch> pitches;
	private Integer points;

	public User() {
		points = 0;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Role getRole() { return role; }
	public void setRole(Role role) { this.role = role; }
	public Set<Genre> getGenres() { return genres; }
	public void setGenres(Set<Genre> genres) { this.genres = genres; }
	public Set<InfoRequest> getOutRequests() { return outRequests; }
	public void setOutRequests(Set<InfoRequest> outRequests) { this.outRequests = outRequests; }
	public Set<InfoRequest> getInRequests() { return inRequests; }
	public void setInRequests(Set<InfoRequest> inRequests) { this.inRequests = inRequests; }
	public Set<Pitch> getPitches() { return pitches; }
	public void setPitches(Set<Pitch> pitches) { this.pitches = pitches; }
	public Integer getPoints() { return points; }
	public void setPoints(Integer points) { this.points = points; }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((genres == null) ? 0 : genres.hashCode());
		result = prime * result + ((outRequests == null) ? 0 : outRequests.hashCode());
		result = prime * result + ((inRequests == null) ? 0 : inRequests.hashCode());
		result = prime * result + ((pitches == null) ? 0 : pitches.hashCode());
		result = prime * result + ((points == null) ? 0 : points.hashCode());
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
		User other = (User) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (genres == null) {
			if (other.genres != null)
				return false;
		} else if (!genres.equals(other.genres))
			return false;
		if (outRequests == null) {
			if (other.outRequests != null)
				return false;
		} else if (!outRequests.equals(other.outRequests))
			return false;
		if (inRequests == null) {
			if (other.inRequests != null)
				return false;
		} else if (!inRequests.equals(other.inRequests))
			return false;
		if (pitches == null) {
			if (other.pitches != null)
				return false;
		} else if (!pitches.equals(other.pitches))
			return false;
		if (points == null) {
			if (other.points != null)
				return false;
		} else if (!points.equals(other.points))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + "]";
	}
}

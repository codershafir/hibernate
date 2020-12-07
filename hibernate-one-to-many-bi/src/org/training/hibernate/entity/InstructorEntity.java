package org.training.hibernate.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "instructor")
@Data
@NoArgsConstructor
public class InstructorEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email")
	private String email;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "instructor_detail_id")
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private InstructorDetailEntity instructorDetailEntity;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "instructorEntity", cascade = { CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.PERSIST, CascadeType.REFRESH })
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private List<CourseEntity> courseEntityList;

	public InstructorEntity(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public void addCourseEntityList(CourseEntity courseEntity) {
		if (Objects.isNull(courseEntityList)) {
			courseEntityList = new ArrayList<>();
		}
		courseEntityList.add(courseEntity);
		courseEntity.setInstructorEntity(this);
	}
}

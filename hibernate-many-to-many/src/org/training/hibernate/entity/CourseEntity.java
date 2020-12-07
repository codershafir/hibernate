package org.training.hibernate.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "course")
@Data
@NoArgsConstructor
public class CourseEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id;

	@Column(name = "title")
	private String title;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "instructor_id")
	private InstructorEntity instructorEntity;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "course_id")
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private List<ReviewEntity> reviewEntityList;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	@JoinTable(name = "course_student", joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private List<StudentEntity> studentEntityList;

	public void addReview(ReviewEntity reviewEntity) {
		if (reviewEntityList == null) {
			reviewEntityList = new ArrayList<>();
		}
		reviewEntityList.add(reviewEntity);
	}

	public void addStudent(StudentEntity studentEntity) {
		if (studentEntityList == null) {
			studentEntityList = new ArrayList<>();
		}
		studentEntityList.add(studentEntity);
	}

	public CourseEntity(String title) {
		this.title = title;
	}
}

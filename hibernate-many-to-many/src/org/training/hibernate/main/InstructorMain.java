package org.training.hibernate.main;

import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.training.hibernate.entity.CourseEntity;
import org.training.hibernate.entity.InstructorDetailEntity;
import org.training.hibernate.entity.InstructorEntity;
import org.training.hibernate.entity.ReviewEntity;
import org.training.hibernate.entity.StudentEntity;

public class InstructorMain {

	public static void main(String[] args) {
		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(InstructorEntity.class).addAnnotatedClass(InstructorDetailEntity.class)
				.addAnnotatedClass(CourseEntity.class).addAnnotatedClass(ReviewEntity.class)
				.addAnnotatedClass(StudentEntity.class).buildSessionFactory();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter option:");
		System.out.println("1.Create Data");
		System.out.println("2.Read Data");
		System.out.println("3.Delete Data");
		int option = sc.nextInt();
		try {
			switch (option) {
			case 1:
				createData(sessionFactory);
				break;
			case 2:
				readData(sessionFactory);
				break;
			case 3:
				deleteData(sessionFactory);
				break;
			default:
				break;
			}
		} finally {
			sessionFactory.close();
			sc.close();
		}
	}

	/**
	 * For creating new student data in DB
	 * 
	 * @param sessionFactory
	 */
	private static void createData(SessionFactory sessionFactory) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			CourseEntity courseEntity1 = new CourseEntity("Java Programming");
			CourseEntity courseEntity2 = new CourseEntity("Photoshop");
			session.save(courseEntity1);
			session.save(courseEntity2);
			StudentEntity student1 = new StudentEntity("Shafir", "Babu", "sweetshafir@gmail.com");
			StudentEntity student2 = new StudentEntity("Hamee", "Hassan", "hamee@gmail.com");
			session.save(student1);
			session.save(student2);
			courseEntity1.addStudent(student1);
			courseEntity1.addStudent(student2);
			courseEntity2.addStudent(student1);
			courseEntity2.addStudent(student2);
			session.getTransaction().commit();
		} finally {
			session.close();
		}
	}

	/**
	 * For reading data from DB
	 * 
	 * @param sessionFactory
	 */
	private static void readData(SessionFactory sessionFactory) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			CourseEntity courseEntity = session.get(CourseEntity.class, 16);
			System.out.println("Course >>>> Student");
			System.out.println(courseEntity);
			System.out.println(courseEntity.getStudentEntityList());
			StudentEntity studentEntity = session.get(StudentEntity.class, 1);
			System.out.println("Student >>>> Course");
			System.out.println(studentEntity);
			System.out.println(studentEntity.getCourseEntityList());
			session.getTransaction().commit();
		} finally {
			session.close();
		}
	}

	/**
	 * For delete data from DB
	 * 
	 * @param sessionFactory
	 */
	private static void deleteData(SessionFactory sessionFactory) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			CourseEntity courseEntity = session.get(CourseEntity.class, 17);
			System.out.println("Before course Delete");
			System.out.println("Course");
			System.out.println(courseEntity);
			System.out.println("Student");
			System.out.println(courseEntity.getStudentEntityList());
			session.delete(courseEntity);
			System.out.println("After course delete");
			StudentEntity studentEntity = session.get(StudentEntity.class, 1);
			System.out.println(studentEntity);
			System.out.println(studentEntity.getCourseEntityList());
			session.getTransaction().commit();
		} finally {
			session.close();
		}
	}
}

package org.training.hibernate.main;

import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.training.hibernate.entity.CourseEntity;
import org.training.hibernate.entity.InstructorDetailEntity;
import org.training.hibernate.entity.InstructorEntity;

public class InstructorMain {

	public static void main(String[] args) {
		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(InstructorEntity.class).addAnnotatedClass(InstructorDetailEntity.class)
				.addAnnotatedClass(CourseEntity.class).buildSessionFactory();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter option:");
		System.out.println("1.Create Data");
		System.out.println("2.Read Data");
		System.out.println("3.Update Data");
		System.out.println("4.Delete Data");
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
			InstructorEntity instructorEntity = new InstructorEntity("Mohammed Shafir", "Babu",
					"sweetshafir@gmail.com");
			InstructorDetailEntity instructorDetailEntity = new InstructorDetailEntity("www.youtube.com/codergeek",
					"Boxing");
			instructorEntity.setInstructorDetailEntity(instructorDetailEntity);
			CourseEntity courseEntity1 = new CourseEntity("Java Programming1");
			CourseEntity courseEntity2 = new CourseEntity("Spring Framework1");
			courseEntity1.setInstructorEntity(instructorEntity);
			courseEntity2.setInstructorEntity(instructorEntity);
			session.save(instructorEntity);
			session.save(courseEntity1);
			session.save(courseEntity2);
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
			InstructorEntity instructorEntity = session.get(InstructorEntity.class, 8);
			System.out.println(instructorEntity);
			System.out.println(instructorEntity.getInstructorDetailEntity());
			System.out.println(instructorEntity.getCourseEntityList());

			CourseEntity courseEntity = session.get(CourseEntity.class, 5);
			System.out.println(courseEntity);
			System.out.println(courseEntity.getInstructorEntity());
			System.out.println(courseEntity.getInstructorEntity().getInstructorDetailEntity());
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
	private static void deleteData(SessionFactory sessionFactory) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			CourseEntity courseEntity = session.get(CourseEntity.class, 5);
			session.delete(courseEntity);
			InstructorEntity instructorEntity = session.get(InstructorEntity.class, 8);
			System.out.println(instructorEntity);
			System.out.println(instructorEntity.getInstructorDetailEntity());
			System.out.println(instructorEntity.getCourseEntityList());
			session.getTransaction().commit();
		} finally {
			session.close();
		}
	}
}

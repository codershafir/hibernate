package org.training.hibernate.main;

import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.training.hibernate.entity.CourseEntity;
import org.training.hibernate.entity.InstructorDetailEntity;
import org.training.hibernate.entity.InstructorEntity;
import org.training.hibernate.entity.ReviewEntity;

public class InstructorMain {

	public static void main(String[] args) {
		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(InstructorEntity.class).addAnnotatedClass(InstructorDetailEntity.class)
				.addAnnotatedClass(CourseEntity.class).addAnnotatedClass(ReviewEntity.class).buildSessionFactory();
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
			CourseEntity courseEntity1 = new CourseEntity("Java Programming1");
			ReviewEntity reviewEntity1 = new ReviewEntity("Good");
			ReviewEntity reviewEntity2 = new ReviewEntity("Average");
			courseEntity1.addReview(reviewEntity1);
			courseEntity1.addReview(reviewEntity2);
			session.save(courseEntity1);
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
			CourseEntity courseEntity = session.get(CourseEntity.class, 10);
			System.out.println(courseEntity);
			System.out.println(courseEntity.getReviewEntityList());
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
			CourseEntity courseEntity = session.get(CourseEntity.class, 10);
			session.delete(courseEntity);
			session.getTransaction().commit();
		} finally {
			session.close();
		}
	}
}

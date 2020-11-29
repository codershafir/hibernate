package org.training.hibernate.main;

import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.training.hibernate.entity.InstructorDetailEntity;
import org.training.hibernate.entity.InstructorEntity;

public class InstructorMain {

	public static void main(String[] args) {
		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(InstructorEntity.class).addAnnotatedClass(InstructorDetailEntity.class)
				.buildSessionFactory();
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
				queryData(sessionFactory);
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
		InstructorEntity instructorEntity = new InstructorEntity("Mohammed Shafir", "Babu", "sweetshafir@gmail.com");
		InstructorDetailEntity instructorDetailEntity = new InstructorDetailEntity("www.youtube.com/codergeek",
				"Cricket");
		instructorEntity.setInstructorDetailEntity(instructorDetailEntity);
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(instructorEntity);
		session.getTransaction().commit();
	}

	private static void queryData(SessionFactory sessionFactory) {
		Session session = sessionFactory.getCurrentSession();
		int id;
		try {
			session.beginTransaction();
			System.out.println("From Instructor to Instructor Detail:");
			id = 5;
			InstructorEntity instructorEntity = session.get(InstructorEntity.class, id);
			System.out.println("The Instructor Entity:");
			System.out.println(instructorEntity);
			System.out.println("The Associated Detail Entity:");
			System.out.println(instructorEntity.getInstructorDetailEntity());

			System.out.println("From Instructor Detail to Instructor:");
			id = 3;
			InstructorDetailEntity instructorDetailEntity = session.get(InstructorDetailEntity.class, id);
			System.out.println("The Instructor Detail Entity:");
			System.out.println(instructorDetailEntity);
			System.out.println("The Associated Instructor Entity:");
			System.out.println(instructorDetailEntity.getInstructorEntity());

			session.getTransaction().commit();

		} finally {
			session.close();
		}
	}

}

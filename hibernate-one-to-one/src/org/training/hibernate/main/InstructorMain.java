package org.training.hibernate.main;

import java.util.List;
import java.util.Objects;
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
			case 3:
				updateData(sessionFactory);
				break;
			case 4:
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
		InstructorEntity studentEntity = new InstructorEntity("Hameedha", "Hassan", "hamee@gmail.com");
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(studentEntity);
		session.getTransaction().commit();

		session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		InstructorEntity studentEntity1 = session.get(InstructorEntity.class, studentEntity.getId());
		session.getTransaction().commit();
		System.out.println("Single Student: " + studentEntity1);

		session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		List<InstructorEntity> studentList = session.createQuery("from StudentEntity").getResultList();
		System.out.println("\n\nAll Students: ");
		displayStudents(studentList);
		studentList = session.createQuery("from StudentEntity s where s.firstName='Mohammed Shafir'").getResultList();
		System.out.println("\n\nStudents with first name Mohammed Shafir");
		displayStudents(studentList);
		session.getTransaction().commit();
	}

	private static void updateData(SessionFactory sessionFactory) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		int result = session
				.createQuery("update StudentEntity s set s.email='shgjhgsf@outlook.com' where s.firstName='Ram'")
				.executeUpdate();
		session.getTransaction().commit();
		System.out.println("No of records changed: " + result);
	}

	private static void deleteData(SessionFactory sessionFactory) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		InstructorEntity instructorEntity = session.get(InstructorEntity.class, 4);
		if (Objects.nonNull(instructorEntity)) {
			session.delete(instructorEntity);
		} else {
			System.out.println("The object is already deleted.");
		}
		session.getTransaction().commit();
	}

	private static void displayStudents(List<InstructorEntity> studentList) {
		if (studentList.isEmpty() || studentList == null) {
			System.out.println("No Data Found");
		} else {
			studentList.stream().forEach(System.out::println);
		}
	}

}

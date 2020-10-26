package org.training.hibernate.main;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.training.hibernate.entity.StudentEntity;

public class HibernateMain {

	public static void main(String[] args) {
		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(StudentEntity.class).buildSessionFactory();
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
		StudentEntity studentEntity1 = new StudentEntity("Mohammed Shafir", "Babu", "sweetshafir@gmail.com");
		StudentEntity studentEntity2 = new StudentEntity("Ram", "sam", "ram@gmail.com");
		StudentEntity studentEntity3 = new StudentEntity("Ragu", "David", "ragu@gmail.com");
		StudentEntity studentEntity4 = new StudentEntity("Sam", "Guna", "sam@gmail.com");
		StudentEntity studentEntity5 = new StudentEntity("Ram", "Krish", "ram@gmail.com");
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(studentEntity1);
		session.save(studentEntity2);
		session.save(studentEntity3);
		session.save(studentEntity4);
		session.save(studentEntity5);
		session.getTransaction().commit();
	}

	private static void queryData(SessionFactory sessionFactory) {
		StudentEntity studentEntity = new StudentEntity("Hameedha", "Hassan", "hamee@gmail.com");
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(studentEntity);
		session.getTransaction().commit();

		session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		StudentEntity studentEntity1 = session.get(StudentEntity.class, studentEntity.getId());
		session.getTransaction().commit();
		System.out.println("Single Student: " + studentEntity1);

		session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		List<StudentEntity> studentList = session.createQuery("from StudentEntity").getResultList();
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
		int result = session.createQuery("delete StudentEntity s where s.email='shgjhgsf@outlook.com'").executeUpdate();
		session.getTransaction().commit();
		System.out.println("No of records changed: " + result);
	}

	private static void displayStudents(List<StudentEntity> studentList) {
		if (studentList.isEmpty() || studentList == null) {
			System.out.println("No Data Found");
		} else {
			studentList.stream().forEach(System.out::println);
		}
	}

}

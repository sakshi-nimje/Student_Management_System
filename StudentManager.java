import java.util.*;
import java.io.*;

public class StudentManager {
    private List<Student> students = new ArrayList<>();
    private static final String FILE_NAME = "students.data";

    public StudentManager() {
        loadStudents();
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Search Student");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1: addStudent(sc); break;
                case 2: viewStudents(); break;
                case 3: updateStudent(sc); break;
                case 4: deleteStudent(sc); break;
                case 5: searchStudent(sc); break;
                case 6: saveStudents(); return;
                default: System.out.println("Invalid option!"); break;
            }
        }
    }

    private void addStudent(Scanner sc) {
        System.out.print("Enter ID: ");
        String id = sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Age: ");
        int age = sc.nextInt();
        sc.nextLine(); // Consume newline
        System.out.print("Enter Course: ");
        String course = sc.nextLine();

        students.add(new Student(id, name, age, course));
        System.out.println("Student added successfully.");
    }

    private void viewStudents() {
        for (Student student : students) {
            System.out.println(student);
        }
    }

    private void updateStudent(Scanner sc) {
        System.out.print("Enter ID of the student to update: ");
        String id = sc.nextLine();
        for (Student student : students) {
            if (student.getId().equals(id)) {
                System.out.print("Enter new Name: ");
                student.setName(sc.nextLine());
                System.out.print("Enter new Age: ");
                student.setAge(sc.nextInt());
                sc.nextLine();
                System.out.print("Enter new Course: ");
                student.setCourse(sc.nextLine());
                System.out.println("Student updated successfully.");
                return;
            }
        }
        System.out.println("Student not found.");
    }

    private void deleteStudent(Scanner sc) {
        System.out.print("Enter ID of the student to delete: ");
        String id = sc.nextLine();
        Iterator<Student> iterator = students.iterator();
        while (iterator.hasNext()) {
            Student student = iterator.next();
            if (student.getId().equals(id)) {
                iterator.remove();
                System.out.println("Student deleted successfully.");
                return;
            }
        }
        System.out.println("Student not found.");
    }

    private void searchStudent(Scanner sc) {
        System.out.print("Enter ID or Name to search: ");
        String query = sc.nextLine();
        for (Student student : students) {
            if (student.getId().equals(query) || student.getName().equalsIgnoreCase(query)) {
                System.out.println(student);
                return;
            }
        }
        System.out.println("Student not found.");
    }

    @SuppressWarnings("unchecked")
    private void loadStudents() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            students = (List<Student>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No previous student records found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveStudents() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
            System.out.println("Student records saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
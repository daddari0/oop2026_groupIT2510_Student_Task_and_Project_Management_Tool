package repositories;

public interface EnrollmentRepository {
    boolean isUserEnrolledInCourse(int userId, int courseId);
}

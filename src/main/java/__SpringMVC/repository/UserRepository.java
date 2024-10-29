package __SpringMVC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import __SpringMVC.bean.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserIdAndPassword(String userId, String password);

	User findByUserId(String userId);
}

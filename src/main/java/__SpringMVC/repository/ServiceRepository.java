package __SpringMVC.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import __SpringMVC.bean.ServiceDetails;
import __SpringMVC.bean.User;

public interface ServiceRepository extends JpaRepository<ServiceDetails, Integer> {

	List<ServiceDetails> findAllByUser(User user);

}

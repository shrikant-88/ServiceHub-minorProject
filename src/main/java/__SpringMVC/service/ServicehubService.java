package __SpringMVC.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import __SpringMVC.bean.User;
import __SpringMVC.repository.UserRepository;

@Service
public class ServicehubService {
	@Autowired
    UserRepository repo;

	public User getUserIdAndPassword(String userId, String password) {
		// TODO Auto-generated method stub
		
		return repo.findByUserIdAndPassword(userId,password);
		
	}

	public void saveUser(User user) {
		// TODO Auto-generated method stub
		repo.save(user);
		System.out.println("success");
		
	}

	public User findByUserId(String userId) {
		// TODO Auto-generated method stub
		return repo.findByUserId(userId);
	}
}

package __SpringMVC.bean;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ServiceDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String address;
	
	private String serviceType;
	
	private String dateAndTime;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@Override
	public String toString() {
	    return "ServiceDetails{" +
	            // Avoid printing the entire User object to prevent recursion
	            "userId=" + (user != null ? user.getId() : null) + 
	            '}';
	}
}

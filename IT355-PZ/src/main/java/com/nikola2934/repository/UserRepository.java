
package com.nikola2934.repository;

import com.nikola2934.model.User;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Long>{
    public User findByUsername(String username);
    @Query(value="SELECT user.`user_id`, user.`name`, user.`lastname`, user.`earnings`, user.`username`, user.`email`, user.`password`, user.`paypal_email`, user.`picture`, user.`text`, user.`active` FROM user WHERE user.username LIKE %:query%",nativeQuery = true)
    public Set<User> search(String query);
}


package com.nikola2934.repository;

import com.nikola2934.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PurchaseRepository extends JpaRepository<Purchase,Long>{
    @Query(value="SELECT * FROM `purchase` WHERE purchase.secret = :key",nativeQuery = true)
    public Purchase findPurchaseByKey(@Param("key") String key);
}

package com.example.coffee_project.repository.user;

import com.example.coffee_project.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface IUserRepository extends JpaRepository<User, Integer> {
    Page<User> findUserByUserNameContaining(Pageable pageable, String search);

    User findUserByAccountAccountName(String name);

    User findUserByUserPhoneNumber(String numberPhone);

    User findUserByUserEmail(String email);

    User findUserByUserIdCard(String idCard);

    @Transactional
    @Modifying
    @Query(value = "call remove_user(:id) ;",nativeQuery = true)
    void removeUserByUserId(@Param("id") Integer id);
}

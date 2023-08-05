package com.example.coffee_project.repository.account;

import com.example.coffee_project.model.account.Account;
import com.example.coffee_project.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.EntityManager;

public interface IAccountRepository extends JpaRepository<Account,String> {
// @Query(value = "select account.account_name,\n" +
//         "role.role_name,\n" +
//         "user.user_name,\n" +
//         " from account \n" +
//         " join user on account.account_name=user.account_name " +
//         " join employee on user .account_name=user.account_name " +
//         "where account.account_name like :name",nativeQuery = true)
// Page<Account> findAllByNameContaining(Pageable pageable,@Param("name") String name);
 Page<Account> findAllByAccountNameContaining(Pageable pageable,String name);
 Account findAccountByAccountName(String name);
}

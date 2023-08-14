-- CODE PROCEDURE

-- REMOVE USER
DELIMITER //
create procedure remove_user(id int)
begin
SET FOREIGN_KEY_CHECKS = 0;
delete from `user`
where `user`.user_id = id;
SET FOREIGN_KEY_CHECKS = 1;
end //
DELIMITER ;



-- REMOVE CUSTOMER
DELIMITER //
create procedure remove_customer(id int)
begin
SET FOREIGN_KEY_CHECKS = 0;
delete from customer where customer.customer_id = id;
SET FOREIGN_KEY_CHECKS = 1;
end //
DELIMITER ;

-- REMOVE ACCOUNT
DELIMITER //
create procedure remove_account(name varchar(255))
begin
SET FOREIGN_KEY_CHECKS = 0;
delete from `account`
where `account`.account_name = name;
SET FOREIGN_KEY_CHECKS = 1;
end //
DELIMITER ;
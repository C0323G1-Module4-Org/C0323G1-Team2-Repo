 create database  coffee;
use coffee;
create table `role`(
`role_id` int primary key auto_increment,
`role_name` varchar(255) not null
);
create table `account`(
`account_name` varchar(255) primary key,
`account_password` varchar(255) not null,
`role_id` int not null,
foreign key(`role_id`) references `role`(`role_id`)
);

create table `employee_type`(
`employee_type_id` int primary key auto_increment,
`employee_type_name` varchar (255) not null unique
);
create table `user`(
`user_id` int primary key auto_increment,
`user_name` varchar(255) not null,
`user_gender` bit(1),
`user_birthday` date not null,
`user_phone_number` varchar (20) not null unique,
`user_email` varchar(255) not null unique,
`user_id_card` varchar(20) not null unique,
`user_salary` double not null,
`user_address` varchar (255) not null,
`user_image_path` mediumtext,
`employee_type_id` int,
`account_name` varchar(255) unique not null,
foreign key (`employee_type_id`) references `employee_type`(`employee_type_id`),
foreign key (`account_name`) references `account`(`account_name`)
);
create table `customer`(
`customer_id` int primary key auto_increment,
`customer_name` varchar(255) not null,
`customer_gender` bit,
`customer_birthday` date,
`customer_phone_number` varchar (20) not null unique,
`customer_point` int default 0
);
create table `order`(
`order_id` int primary key auto_increment,
`order_status` bit(1),
`order_date` datetime not null,
`customer_id` int,
`user_id` int not null,
foreign key(`customer_id`) references `customer`(`customer_id`),
foreign key(`user_id`) references `user`(`user_id`)
);
create table `product_type`(
`product_type_id` int primary key auto_increment,
`product_type_name` varchar(255) not null unique
);
create table `product`(
`product_id` int primary key auto_increment,
`product_name` varchar(255) not null,
`product_price` double not null,
`product_description` varchar(255),
`product_image_path` mediumtext,
`product_type_id` int not null,
foreign key (`product_type_id`) references `product_type`(`product_type_id`)
);
create table `order_detail`(
`order_detail_id` int primary key auto_increment,
`quantity_product` int not null,
`product_price` double not null,
`product_id` int not null,
`order_id` int not null,
foreign key (`order_id`) references `order`(`order_id`),
foreign key (`product_id`) references `product`(`product_id`)
);
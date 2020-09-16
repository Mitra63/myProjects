

insert into role (Role_Name) values('ADMIN');
insert into role (Role_Name) values('USER');

insert into Rest_User (username, password) values('admin', '$2a$10$tS5dGLTyXvbfzh/f7Bx/iOlB3gIKtb.AH4e4jhB53snpF35HHJgV2');
insert into Rest_User (username, password) values('user1', '$2a$10$wwW6NhnbclceX4PrgSfPbu1CIpPX2GR1QFlW6iT1CXVI75U65k6ti');
insert into Rest_User (username, password) values('user2','$2a$10$3C7dvljY2FIZ7vnxpFOpnOzHAv14pA9fzEt.LA2iVe9gcH.KQBVmO');

insert into user_role (User_Id, Role_Id) values(1,1);
insert into user_role (User_Id, Role_Id) values(2,2);
insert into user_role (User_Id, Role_Id) values(3,2);

insert into Customer(User_Id,First_Name,Last_Name,National_Code,Mobile,Address)
values(1,'ali','jafari','2134563454','09356785678','tehran');

insert into Customer(User_Id,First_Name,Last_Name,National_Code,Mobile,Address)
values(2,'davood','ahmadi','5434564325','09383456789','tehran');

insert into Customer(User_Id,First_Name,Last_Name,National_Code,Mobile,Address)
values(3,'nader','jamshidi','2542562354','09125475845','tehran');
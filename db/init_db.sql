-- https://www.sitepoint.com/how-to-install-mysql/
-- https://www.mysqltutorial.org/mysql-show-tables/
-- https://docs.oracle.com/javacomponents/advanced-management-console-2/install-guide/mysql-database-installation-and-configuration-advanced-management-console.htm#JSAMI121

# create root
mysqld.exe --initialize-insecure --user=mysql
mysql -u root
create user  'admin_db' identified by 'password';
create database hibernate;
show databases;
grant all on hibernate.* to 'admin_db';
show grants;
show grants for admin_db;
exit
# reboot service MySQL
net stop mysql
net start mysql
# login as admin db
mysql -u admin_db -p
show databases;
use hibernate
show tables;


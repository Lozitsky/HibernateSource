# https://www.a2hosting.com/kb/developer-corner/mysql/convert-mysql-database-utf-8
# https://dba.stackexchange.com/questions/76788/create-a-mysql-database-with-charset-utf-8
# https://dev.mysql.com/doc/refman/8.0/en/charset-column.html
# https://www.got-it.ai/solutions/sqlquerychat/sql-help/general-sql/how-to-create-a-mysql-database-into-a-utf-8-charset/
# https://dev.mysql.com/doc/refman/8.0/en/charset-unicode-conversion.html
# https://docs.oracle.com/cd/E17952_01/mysql-8.0-en/show-collation.html

# https://www.mysqltutorial.org/mysql-create-table/

drop table if exists author;
select default_character_set_name
from information_schema.SCHEMATA S
where schema_name = 'hibernate';
create table if not exists author
(
    id          bigint(20)   not null auto_increment primary key,
    name        varchar(255) not null,
    second_name varchar(255) default null
# ,    primary key (`id`)
) engine = InnoDB;
show tables;
drop table if exists book;
CREATE TABLE book
(
    id        bigint(20)   not null auto_increment,
    author_id bigint(20)   not null,
    name      varchar(255) not null,
    primary key (id, author_id),
#     KEY `fk_autor_id_idx` (`author_id`),
#     CONSTRAINT `fk_autor_id` FOREIGN KEY (`author_id`) REFERENCES `author` (`id`)
    foreign key (author_id)
        references author (id)
        ON UPDATE RESTRICT ON DELETE CASCADE
) engine = InnoDB;
show tables;

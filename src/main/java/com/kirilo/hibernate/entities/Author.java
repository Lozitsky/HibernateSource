package com.kirilo.hibernate.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

// https://dev.mysql.com/doc/refman/8.0/en/create-table-foreign-keys.html
// https://intellij-support.jetbrains.com/hc/en-us/community/posts/208001969/comments/208676565

@Entity
public class Author {
    private long id;
    private String name;
    private String secondName;
    private Collection<Book> booksById;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "second_name", nullable = true, length = 255)
    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return id == author.id &&
                Objects.equals(name, author.name) &&
                Objects.equals(secondName, author.secondName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, secondName);
    }

    @OneToMany(mappedBy = "authorByAuthorId")
    public Collection<Book> getBooksById() {
        return booksById;
    }

    public void setBooksById(Collection<Book> booksById) {
        this.booksById = booksById;
    }
}

package backend.school_management_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private long userId;

    @Basic
    @Column(name = "email", unique = true, nullable = false, length = 45)
    private String email;

    @Basic
    @Column(name = "password", nullable = false, length = 65)
    private  String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles = new HashSet<>();

    @OneToOne(mappedBy = "user") // user copied from Student class (User user)
    private Student student;

    @OneToOne(mappedBy = "user") // user copied from Instructor class (User user)
    private Instructor instructor;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Display user information
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                '}';
    }

    // Helper method => Assign Role to Users
    public void assignRoleToUser(Role role){
        this.roles.add(role);
        role.getUsers().add(this);
    }

    // Helper method => Remove Role from Users
    public void removeRoleFromUser(Role role){
        this.roles.remove(role);
        role.getUsers().remove(this);
    }

    // Generated from generate equals and hasCode
    // Compare role attributes with the Object attributes (like roleId with roleId in the Object
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId && Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, email, password);
    }
}

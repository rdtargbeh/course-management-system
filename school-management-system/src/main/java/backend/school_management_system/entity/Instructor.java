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
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "instructors")
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "instructor_id", nullable = false)
    private  Long instructorId;

    @Basic
    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;

    @Basic
    @Column(name = "last_name", nullable = false, length = 45)
    private String  lastName;

    @Basic
    @Column(name = "summary", nullable = false, length = 100)
    private String summary;

    @OneToMany(mappedBy ="instructor", fetch = FetchType.LAZY)
    private Set<Course> courses = new HashSet<>(); // relationship to courses

    @OneToOne(cascade = CascadeType.REMOVE) // cascade remove instructor info from user if instructor is remove
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;  // relationship to users -  each teacher is a user

    // Constructor
    public Instructor(String firstName, String lastName, String summary, User user) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.summary = summary;
        this.user = user;
    }

    // Display instructor information
    @Override
    public String toString() {
        return "Instructor{" +
                "instructorId=" + instructorId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }

    // Generated from generate equals and hasCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Instructor that = (Instructor) o;
        return Objects.equals(instructorId, that.instructorId) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(summary, that.summary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instructorId, firstName, lastName, summary);
    }
}
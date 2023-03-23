package engine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import engine.model.Quiz;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
public class User {

    @Id
    @NotBlank
    @Pattern(regexp = "\\w+@\\w+\\.\\w+")
    private String email;
    @Size(min = 5)
    private String password;
    @JsonIgnore
    private String roles;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Quiz> quizes;

    public User() {}

    public User(String email, String password, String roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Set<Quiz> getQuizes() {
        return quizes;
    }

    public void setQuizes(Set<Quiz> quizes) {
        this.quizes = quizes;
    }
}

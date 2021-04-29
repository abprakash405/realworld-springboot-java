package io.github.raeperd.realworld.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class User {

    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    private String email;
    private String username;
    private String bio;
    private String image;
    private String password;

    public User(String email, String username, String password) {
        this(email, username, null, null);
        this.password = password;
    }

    protected User(String email, String username, String bio, String image) {
        this.id = null;
        this.email = email;
        this.username = username;
        this.bio = bio;
        this.image = image;
    }

    protected User() {
    }

    User updateUser(UserUpdateCommand updateCommand) {
        updateCommand.getEmailToUpdate().ifPresent(emailToUpdate -> this.email = emailToUpdate);
        updateCommand.getUsernameToUpdate().ifPresent(usernameToUpdate -> this.username = usernameToUpdate);
        updateCommand.getBioToUpdate().ifPresent(bioToUpdate -> this.bio = bioToUpdate);
        updateCommand.getImageToUpdate().ifPresent(imageToUpdate -> this.image = imageToUpdate);
        updateCommand.getPasswordToUpdate().ifPresent(passwordToUpdate -> this.password = passwordToUpdate);
        return this;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getBio() {
        return bio;
    }

    public String getImage() {
        return image;
    }
}

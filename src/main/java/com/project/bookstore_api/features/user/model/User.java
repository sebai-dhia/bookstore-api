package com.project.bookstore_api.features.user.model;

import java.time.LocalDate;
import java.util.Random;
import java.util.function.Function;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.*;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "_user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_role")
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Past
    private LocalDate birthday;

    @Column(unique = true)
    @Size(max = 255, message = "Email cannot exceed 255 characters")
    private String email;

    @Column(length = 100)
    private String password;

    @NotBlank(message = "Username cannot be blank")
    @Column(unique = true, length = 25)
    private String username;

    @Enumerated(EnumType.STRING)
    private Role role;


    protected User(Role role){
        this.role = role;
    }


    @PrePersist
    public void generateUserName(){
        if(getUsername() ==null || getUsername().isBlank()){
            Random random = new Random();
            StringBuilder usernameBuilder = new StringBuilder();

            // Helper: pick 2 random chars from a string
            Function<String, String> pick2RandomChars = (str) -> {
                if(str == null || str.length()<2){
                    return str!=null ? str:"";
                }
                char[] chars = str.toLowerCase().toCharArray();
                StringBuilder picked = new StringBuilder(2);
                for(int i = 0; i<2; i++){
                    picked.append(chars[random.nextInt(chars.length)]);
                }
                return  picked.toString();
            };

            // Collect 2 random chars from each available field

            if(getFirstName()!=null && !getFirstName().isBlank()){
                usernameBuilder.append(pick2RandomChars.apply(getFirstName()));
            }
            if(getLastName()!=null && !getLastName().isBlank()){
                usernameBuilder.append(pick2RandomChars.apply(getLastName()));
            }
            if(getBirthday()!=null){
                usernameBuilder.append(getBirthday().getDayOfMonth());
            }
            if(getEmail()!= null && !getEmail().isBlank()){
                usernameBuilder.append(pick2RandomChars.apply(getEmail()));
            }

            if(usernameBuilder.isEmpty()){
                String randomTwo = pick2RandomChars.apply("abcdefghijklmnopqrstuvwxyz0123456789");
                usernameBuilder.append(getRole()).append(randomTwo);
            }

            usernameBuilder.append(System.currentTimeMillis());

            setUsername(usernameBuilder.toString());

        }

    }
}

package com.nhom7.qltd.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Data
@Entity
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @Column(name = "id")
    private UUID id = UUID.randomUUID();
    @Column(name = "user_name", nullable = false, unique = true)
    @NotBlank(message = "Vui lòng nhập tên đăng nhập")
    @Size(min = 5, max = 50, message = "Tên đăng nhập phải từ 5 đến 50 ký tự")
    private String username;
    @Column(name = "password", nullable = false)
    @NotBlank(message = "Vui lòng nhập mật khẩu")
    private String password;
    @Column(name = "name", nullable = false)
    @NotBlank(message = "Vui lòng nhập tên")
    private String name;
    @Column(name = "email", nullable = false)
    @Size(min = 5, max = 100, message = "Email phải từ 5 đến 100 ký tự")
    @Email
    private String email;
    @Column(name = "PhoneNumber", nullable = false)
    @Size(min = 10, max = 11, message = "Số điện thoại phải từ 10 đến 11 số")
    private String phone;
    @OneToMany(mappedBy = "user")
    private List<Comment> comments;
    @OneToMany(mappedBy = "user")
    private List<HopDongVay> hopDongVays;
    @OneToMany(mappedBy = "user")
    private List<HopDongMoThe> hopDongMoThes;
    @OneToMany(mappedBy = "user")
    private List<TinTuc> tinTucs;
    @OneToMany(mappedBy = "user")
    private List<Message> messages;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> userRoles = this.getRoles();
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();
    }

    @Override
    public String getPassword() {
        return password;
    }


    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return
                false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }


    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

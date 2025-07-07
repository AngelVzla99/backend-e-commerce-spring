package com.example.springboot.User.entities;

import com.example.springboot.Order.entities.OrderEntity;
import com.example.springboot.Order.entities.PaymentMethodEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "first_name", length = 50, nullable = false)
    private  String firstName;

    @Column(name = "last_name", length = 50, nullable = false)
    private  String lastName;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Column(name = "email", length = 50, nullable = false)
    private  String email;

    @Column(name = "phone_number", length = 50, nullable = false)
    private  String phoneNumber;

    @Column(name = "password", length = 50, nullable = false)
    private  String password;

    @Column(name = "reset_password_token", length = 100)
    private String resetPasswordToken;

    // other relations
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id", referencedColumnName = "id",
                            nullable = false, updatable = false)})
    private Set<RoleEntity> roles = new HashSet<RoleEntity>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<CartItemEntity> cartItems;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<AddressEntity> addresses;

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    // to string
    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", createdAt=" + createdAt +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<OrderEntity> orders;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<PaymentMethodEntity> paymentMethods;

    // constructors

    public UserEntity() {}

    public UserEntity(String firstName, String lastName, String email, String phoneNumber, String password, Set<RoleEntity> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.roles = roles;
    }

    public UserEntity(Long id, String firstName, String lastName, String email, String phoneNumber, String password, Set<RoleEntity> roles, String resetPasswordToken) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.roles = roles;
        this.resetPasswordToken = resetPasswordToken;
    }

    public UserEntity(Long id, String firstName, String lastName, Date createdAt, String email, String phoneNumber, String password, Set<RoleEntity> roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = createdAt;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.roles = roles;
    }

    // getters and setters


    public List<AddressEntity> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressEntity> addresses) {
        this.addresses = addresses;
    }

    public List<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderEntity> orders) {
        this.orders = orders;
    }

    public List<PaymentMethodEntity> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<PaymentMethodEntity> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public List<CartItemEntity> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemEntity> cartItems) {
        this.cartItems = cartItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }
}
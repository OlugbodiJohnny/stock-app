package com.example.stockapp2.models;


import com.example.stockapp2.enums.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;


/**
 * A user.
 */
@Entity
@Table(name = "users")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class User extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @Column(name = "password_hash", length = 60, nullable = false)
    private String password;

    @Column(length = 254, unique = true)
    private String email;

    @Column(unique = true)
    private String fullName;

    @Column(unique = true)
    private String phoneNumber;

    @NotNull
    @Column(nullable = false)
    private boolean activated = false;

    @NotNull
    @Column(nullable = false)
    private boolean deleted = false;

    @NotNull
    @Column(nullable = false)
    private boolean emailVerified = false;

    @Column(name = "image_url", length = 256)
    private String imageUrl;


    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    private String activationKey;


    @Column(name = "reset_key", length = 20)
    @JsonIgnore
    private String resetKey;

    @Column(name = "reset_date")
    private Instant resetDate = null;

    private String otp;

    private String verificationOtp;

    @Enumerated(EnumType.STRING)
    private UserType userType = UserType.USER;

    private Long otpCreatedAt;

    private Long verificationOtpCreatedAt;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_authority",
            joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "authority_name", referencedColumnName = "name") }
    )
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(CascadeType.SAVE_UPDATE)
    private Set<Authority> authorities = new HashSet<>();

    // prettier-ignore
    @Override
    public String toString() {
        return "User{" +
                ", email='" + email + '\'' +
                ", activated=" + activated +
                ", deleted=" + deleted +
                ", imageUrl='" + imageUrl + '\'' +
                ", activationKey='" + activationKey + '\'' +
                ", resetKey='" + resetKey + '\'' +
                ", resetDate=" + resetDate +
                '}';
    }
}

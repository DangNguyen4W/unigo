package com.unigo.entity;

import java.math.BigInteger;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "permissions")
public class Permission extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private BigInteger id;
  @Column(name = "code", nullable = false, unique = true, length = 100)
  private String code;
  @Column(name = "is_active", nullable = false)
  private boolean isActive;

  public BigInteger getId() {
    return id;
  }

  public String getCode() {
    return code;
  }

  

  public boolean isActive() {
    return isActive;
  }

    public void setCode(String code) {
        this.code = code;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isIsActive() {
        return isActive;
    }

}

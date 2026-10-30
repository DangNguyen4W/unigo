package com.unigo.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unigo.entity.Permission;

public interface PermissionRepository extends JpaRepository<Permission, BigInteger> {

}

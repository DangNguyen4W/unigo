package com.unigo.dto.request;

import java.math.BigInteger;
import java.util.Collection;

public record AssignPermissionsRequest(
    Integer roleId,
    Collection<BigInteger> permissionIds) {

}

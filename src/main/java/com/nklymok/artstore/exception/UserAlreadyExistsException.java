package com.nklymok.artstore.exception;

import com.nklymok.artstore.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "User Already Exists")
public class UserAlreadyExistsException extends Exception {

}

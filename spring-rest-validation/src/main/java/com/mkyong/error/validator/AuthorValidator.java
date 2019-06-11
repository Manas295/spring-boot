package com.mkyong.error.validator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Value;

public class AuthorValidator implements ConstraintValidator<Author, String> {


	List<String> authors = Arrays.asList("Santideva","Marie Kondo","Martin Fowler","mkyong");

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return authors.contains(value);
	}
}

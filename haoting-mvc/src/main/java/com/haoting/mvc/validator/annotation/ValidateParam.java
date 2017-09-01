package com.haoting.mvc.validator.annotation;

import com.haoting.mvc.validator.Validator;

import java.lang.annotation.*;

/**
 * 自定义请求参数注解
 * 
 * @author Joe
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidateParam {

	/**
	 * 验证器
	 * @return
	 */
	Validator[] value() default {};
	
	/**
	 * 参数的描述名称
	 * @return
	 */
	String name() default "";
}


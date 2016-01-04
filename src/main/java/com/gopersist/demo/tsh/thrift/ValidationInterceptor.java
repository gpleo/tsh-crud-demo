package com.gopersist.demo.tsh.thrift;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.Configuration;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import com.gopersist.demo.tsh.RequestException;

/**
 * 数据验证拦截器
 * 
 * 对Thrift的接口进行实现时，会接收从客户端传入的参数，
 * Thrift在定义IDL时会对参数进行简单的required的限制，但仍然需要一些更细的检查。
 * 这个拦截器将拦截所有调用Thrift Service接口实现类的方法（在applicationContext-validation.xml中设定），
 * 对传入的参数使用Hibernate Validation进行检查，具体参数的验证需求在validation/*-mapping.xml中配置。
 * 
 * @author leo
 *
 */
public class ValidationInterceptor{
	private static Validator validator;
	
	static {
		// 使用配置文件配置数据验证
		Configuration<?> configuration = Validation.byDefaultProvider().configure();
		configuration.addMapping( ValidationInterceptor.class.getResourceAsStream( "/validation/blog-mapping.xml" ) );
		ValidatorFactory factory = configuration.buildValidatorFactory();
		validator = factory.getValidator();
	}

	/**
	 * 在applicationContext-validation.xml中配置的拦截器需拦截的方法，进入这些方法之前，先验证参数是否合法
	 * 
	 * @param joinPoint
	 * @throws Throwable
	 */
	public void before(JoinPoint joinPoint) throws Throwable {
		Object target = joinPoint.getTarget();	// 取得目标类
		String methodName = joinPoint.getSignature().getName();	// 取得目标方法名
		Class<?>[] parameterTypes = ((MethodSignature)joinPoint.getSignature()).getMethod().getParameterTypes();	// 取得目标方法参数类型
		Method m = target.getClass().getMethod(methodName, parameterTypes);	// 取得目标方法
		
		Object[] args = joinPoint.getArgs();	// 取得目标参数值
		Parameter[] params = m.getParameters();	// 取得目标形式参数
		if (params!=null && params.length>0) {
			for (int i=0, len=params.length; i<len; i++) {
				Parameter param = params[i];
				Annotation valid = param.getAnnotation(Valid.class);	// 检查参数是否有Valid注解，如果有表示参数需要验证
				if (valid!=null) {
					Set<ConstraintViolation<Object>> constraintViolations = validator.validate(args[i]);	// 使用Hibernate Validation验证
					if (constraintViolations.size()>0) {
						RequestException re = new RequestException();
						re.errorCode = 400;
						re.errorMessage = "验证失败";
						Map<String, String> errorFields = new HashMap<String, String>();
						for (ConstraintViolation<Object> v : constraintViolations) {
							errorFields.put(v.getPropertyPath().toString(), v.getMessage());
						}
						re.errorFields = errorFields;
						throw re;
					}
				}
			}
		}
	}
}

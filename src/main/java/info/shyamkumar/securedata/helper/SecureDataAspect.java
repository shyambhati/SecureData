package info.shyamkumar.securedata.helper;

import static info.shyamkumar.securedata.SecureData.processData;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SecureDataAspect {

// This method will be executed after the return of any public method within a class annotated with @RestController
	@AfterReturning(pointcut = "@within(org.springframework.web.bind.annotation.RestController) && "
			+ "execution(public * *(..))", returning = "result")
	public Object afterReturning(Object result) throws Exception {
		if (result != null) {
			// Process the result object using the SecureJson processData method
			processData(result);
		}
		// Return the modified result
		return result;
	}
}
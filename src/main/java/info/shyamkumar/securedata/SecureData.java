package info.shyamkumar.securedata;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import info.shyamkumar.securedata.config.SecureMaskingProperties;
import info.shyamkumar.securedata.helper.SecureDataAspect;

@Configuration
@EnableAspectJAutoProxy
public class SecureData {

	/**
	 * Processes the given object by applying data masking.
	 *
	 * @param <T>    the type of the object
	 * @param object the object to be processed
	 * @return the processed object with masked data
	 */
	public static <T> T processData(T object) {
		return ProcessData.processData(object);
	}

	/**
	 * Creates a bean for ResponseModificationAspect.
	 *
	 * @return a new instance of ResponseModificationAspect
	 */
	@Bean
	public SecureDataAspect responseModificationAspect() {
		return new SecureDataAspect();
	}
	
	@Bean
	public SecureMaskingProperties maskingProperties() {
		return SecureMaskingProperties.setConfig();
	}
}
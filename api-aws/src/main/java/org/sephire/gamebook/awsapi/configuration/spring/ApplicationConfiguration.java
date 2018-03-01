package org.sephire.gamebook.awsapi.configuration.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ApiFunctions.class})
public class ApplicationConfiguration {


}

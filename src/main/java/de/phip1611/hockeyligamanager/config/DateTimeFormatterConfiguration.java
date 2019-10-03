/* 
   Copyright 2019 Philipp Schuster
   
   Web:      phip1611.de
   E-Mail:   philipp.schuster@phip1611.de 
   Twitter:  @phip1611 
 */
package de.phip1611.hockeyligamanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@Configuration
public class DateTimeFormatterConfiguration {

    public static final String PATTERN = "yyyy-dd-MM HH:mm";

    @Bean
    public DateTimeFormatter dateTimeFormatter() {
        return DateTimeFormatter.ofPattern(PATTERN);
    }

}

package com.logicleaptech.ws;

import java.text.ParseException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
 
import com.podio.item.FieldValuesUpdate;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		// Experimenting
		PodioDate podioDate = null;

		try {
			podioDate = new PodioDate("2016-12-01 00:00:00", "");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Date date = new Date("12/1/2016");
		// podioDate.setStart(date);
		// PodioDate.PODIO_DATE_FORMATTER.set(new SimpleDateFormat() );;
		System.out.println(podioDate.getStart());

		FieldValuesUpdate fvu = podioDate.getFieldValuesUpdate("date");
		System.out.println(fvu.getValues());

		// Experimenting
		SpringApplication.run(Application.class, args);
	}
}
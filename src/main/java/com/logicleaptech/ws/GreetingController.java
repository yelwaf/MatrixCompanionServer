package com.logicleaptech.ws;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.logicleaptech.reusable.Reusable;
import com.podio.APIFactory;
import com.podio.ResourceFactory;
import com.podio.item.FieldValuesUpdate;
import com.podio.item.ItemAPI;
import com.podio.item.ItemCreate;
import com.podio.oauth.OAuthClientCredentials;
import com.podio.oauth.OAuthUsernameCredentials;
import com.podio.user.UserAPI;

@RestController
public class GreetingController {

	final Logger logger = LoggerFactory.getLogger(GreetingController.class);

	private final String[] podioField = { "date", "name", "title", "comments",
			"attend-meeting", "category-10", "category-11", "category-9",
			"category-8", "category-7", "category-6", "category-5",
			"category-4", "category-3", "category-2", "category", "book-read" };

	private final String delimiter = ";";

	private static final String template = "%s";
	private final AtomicLong counter = new AtomicLong();
	private final String authCode = "none";

	@RequestMapping("/greeting")
	public Greeting greeting(
			@RequestParam(value = "code", defaultValue = "World") String name) {

		logger.info("MtrxAL: " + name + ";");

		String myAppURI = "http://www.infinitehealthcoaching.com/MatrixActivity-0.0.1/greeting";
		String podioAPI = "https://api.podio.com";
		// Elizabeth
		// String podioAPIkey =
		// "jV1qhnzcfBZU6RaghkYp7xJlzuS1PE0fmfHwSwwsciCMzr9HDoTUw35SaXEb85jb";
		// String podioClientID = "matrixipareporting-z0u7tg";
		// String podioUsername = "elizabeth@matrixinvestornetwork.com";
		// String podioPassword = "Hooley8.22";
		// Glenn
		String podioAPIkey = "g394VdJdZfC8RpIfseYf8V4Jf9JSCGD1bhQQlvNGO78t07qQpgmdOyRsRqNRIYjA";
		String podioClientID = "matrixipareporting";
		String podioUsername = "g.fawley@logicleaptech.com";
		String podioPassword = "sp1derMan";

		String podioApplication = "MatrixIPAReporting";

		String authURI = "https://podio.com/oauth/authorize?client_id="
				+ podioClientID + "&redirect_uri=" + myAppURI + "&scope=app";

		ResourceFactory resourceFactory = new ResourceFactory(
				new OAuthClientCredentials(podioClientID, podioAPIkey),
				new OAuthUsernameCredentials(podioUsername, podioPassword));
		APIFactory apiFactory = new APIFactory(resourceFactory);

		// ItemAPI itemAPI = apiFactory.getClass(ItemAPI.class);

		// ItemAPI itemAPI = new ItemAPI(resourceFactory);

		UserAPI userAPI = apiFactory.getAPI(UserAPI.class);
		ItemAPI itemAPI = apiFactory.getAPI(ItemAPI.class);

		// Profile profile = userAPI.getProfile();
		// name = profile.getName();

		/*
		 * UserAPI userAPI = apiFactory.getAPI(UserAPI.class); Profile profile =
		 * userAPI.getProfile(); name = profile.getName();
		 * 
		 * // SearchAPI podioSearch = new SearchAPI(resourceFactory);
		 * com.podio.app.Application app = new com.podio.app.Application(); //
		 * app.setId(17256855);
		 * 
		 * com.podio.item.Item newItem = new com.podio.item.Item();
		 * FieldValuesView fvv = new FieldValuesView();
		 * 
		 * List<Map<String, ?>> values; Map<String, ?> valueMap;
		 * List<FieldValuesView> fvvList; //ItemAPI itemAPI = new
		 * ItemAPI(resourceFactory);
		 * 
		 * ItemAPI itemAPI = apiFactory.getAPI(ItemAPI.class);
		 * 
		 * ItemCreate ic = new ItemCreate();
		 * 
		 * valueMap.put("title", "POC"); valueMap.put("date", "2016-12-01");
		 * valueMap.put("number", "5"); valueMap.put("name",
		 * "Rhonda Fawley Extraordinaire");
		 * 
		 * values.add(valueMap);
		 * 
		 * fvv.setValues(values); fvvList.add(fvv);
		 * 
		 * newItem.setFields(fvvList); newItem.setApplication(app);
		 */
		// /////////////////////////////////////
		PodioDate podioDate = null;

		try {
			String dateField = Reusable.word(name, delimiter, 0);
			String newDate = Reusable.mid(dateField, 7, 4); // year
			newDate = newDate.concat("-");
			newDate = newDate.concat(Reusable.left(dateField, 2)); // month
			newDate = newDate.concat("-");
			newDate = newDate.concat(Reusable.mid(dateField, 4, 2)); // day
			newDate = newDate.concat(" 00:00:00");

			// podioDate = new PodioDate("2016-12-01 00:00:00", "");
			podioDate = new PodioDate(newDate, "");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ItemCreate item;
		List<FieldValuesUpdate> list;
		list = new ArrayList<FieldValuesUpdate>();

		int Idx = 0;
		list.add(podioDate.getFieldValuesUpdate(podioField[Idx]));

		do {
			Idx++;
			list.add(new FieldValuesUpdate(podioField[Idx], "value", Reusable
					.word(name, delimiter, Idx)));
		} while (Idx < 3);

		// String xyz = "pdq";
		// list.add(new FieldValuesUpdate(podioField[3], "value", xyz));

		for (int i = 4; i < Reusable.words(name, delimiter); i++) {

			list.add(new FieldValuesUpdate(podioField[i], "value",
					getPodioValue(Reusable.word(name, delimiter, i))));
		}

		// list.add(new FieldValuesUpdate("title", "value", "POC"));
		// list.add(new FieldValuesUpdate("number", "value", 555));

		// title, testfield and testintfield are the external_ids of the fields
		item = new ItemCreate("externalId", list,
				Collections.<Integer> emptyList(),
				Collections.<String> emptyList());

		// itemAPI.addItem(appId, item, false);

		itemAPI.addItem(17256855, item, false);
		// ///////////////////////
		return new Greeting(counter.incrementAndGet(), String.format(template,
				Reusable.word(name, delimiter, 1)));
		// return new Greeting(counter.incrementAndGet(), name);
	}

	/*
	 * @RequestMapping("/getAuthCode") public Greeting authCode(
	 * 
	 * @RequestParam(value = "code", defaultValue = "none") String code) {
	 * 
	 * 
	 * }
	 */
	private int getPodioValue(String in) {
		int retVal = 2;

		if (in.equals("on")) {
			retVal = 1;
		}

		return retVal;
	}
}
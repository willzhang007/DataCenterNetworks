package com.network.json;

import net.sf.json.JSONObject;

public class JsonTools {

	public JsonTools() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param key: jsonString header info
	 * @param object: type of parse collection
	 * @return
	 */
	public static String createJsonString(String key, Object value)
	{
		JSONObject jsonObject=new JSONObject();
		jsonObject.put(key, value);
		//create jsonString
		return jsonObject.toString();
	}

}

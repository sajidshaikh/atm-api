package com.navyug.atm.api.utility;

import com.navyug.atm.api.contants.HttpResponse;
import com.navyug.atm.api.response.ATMResponse;
/**
 * 
 * @author mohdsajid
 *
 */

public class ResponseUtility {

	public static ATMResponse setResponse(Object obj,String message, Boolean status, Integer statusCode)throws Exception{
		ATMResponse response = new ATMResponse();
		response.setMessage(message);
		response.setStatusCode(statusCode);
		response.setSuccess(status);
		response.setData(obj);
		return response;
	}
	
	public static ATMResponse exceptionResponse(Object obj,String message, Boolean status, Integer statusCode){
		ATMResponse response = new ATMResponse();
		response.setMessage(message);
		response.setStatusCode(statusCode);
		response.setSuccess(status);
		response.setData(obj);
		return response;
	}
	
	public static ATMResponse defaultException(){
		ATMResponse response = new ATMResponse();
			//hard-coded values added to avoid exceptions while reading from file.
			response.setMessage("Internal Server Error");
			response.setStatusCode(HttpResponse.SC_INTERNAL_SERVER_ERROR);
			response.setSuccess(false);
			response.setData(null);
		return response;
	}
	
	
}

package edu.calpoly.eharlig.budget_brews.services;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.calpoly.eharlig.budget_brews.models.Beer;

public class AddBeer implements RequestHandler<Beer, Object> {

	// need to think of how to add beer to store?
	public Object handleRequest(Beer arg0, Context arg1) {
		// TODO Auto-generated method stub
		return null;
	}

}

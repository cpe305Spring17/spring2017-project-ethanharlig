package edu.calpoly.eharlig.budgetbrews.services;

import java.util.ArrayList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.calpoly.eharlig.budgetbrews.models.Subscription;
import edu.calpoly.eharlig.budgetbrews.util.Vote;

public class DownvotePost implements RequestHandler<Subscription, Object> {
  
  public Object handleRequest(Subscription request, Context context) {
    Vote.vote(request, "downvotes");
    return new ArrayList<>();
  }

}


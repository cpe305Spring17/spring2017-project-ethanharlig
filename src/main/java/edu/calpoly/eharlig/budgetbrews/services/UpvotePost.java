package edu.calpoly.eharlig.budgetbrews.services;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.calpoly.eharlig.budgetbrews.models.Subscription;
import edu.calpoly.eharlig.budgetbrews.util.Vote;

public class UpvotePost implements RequestHandler<Subscription, List<String>> {

  @Override
  public List<String> handleRequest(Subscription request, Context context) {
    Vote.vote(request, "upvotes");
    return new ArrayList<>();
  }

}

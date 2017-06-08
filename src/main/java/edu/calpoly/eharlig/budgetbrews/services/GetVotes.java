package edu.calpoly.eharlig.budgetbrews.services;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.calpoly.eharlig.budgetbrews.dataaccess.DBAccess;
import edu.calpoly.eharlig.budgetbrews.models.User;

public class GetVotes implements RequestHandler<User, List<ArrayList<Object>>> {

  @Override
  public List<ArrayList<Object>> handleRequest(User request, Context context) {
    Table usersTable = DBAccess.getTable("users");
    Item userItem = usersTable.getItem("username", request.getUsername());
    List<ArrayList<Object>> allVotes = new ArrayList<ArrayList<Object>>();
    allVotes.add((ArrayList<Object>) userItem.getList("upvotes"));
    allVotes.add((ArrayList<Object>) userItem.getList("downvotes"));

    return allVotes;
  }

}

[![Build Status](https://travis-ci.org/cpe305Spring17/spring2017-project-ethanharlig.svg?branch=master)](https://travis-ci.org/cpe305Spring17/spring2017-project-ethanharlig)

![Budget Brews](src/main/website/res/img/logo.png)

## Ethan Harlig 

Crowdsource the best beer deals near you (_currently have support for San Luis Obispo, CA_)!

Users of Budget Brews are shown the cheapest beer deals near them! They can filter the beer prices by beer brand and store in which they can select multiple brands or stores to filter. 


# Current state of the site with a "party" background
![](https://i.imgur.com/uAMicd7.gif)

- Users
    - [x] Notify users when prices are updated (Observer pattern)
      - [ ] Subscribe to beers
         - [ ] Weekly email with beer updates and best deals from subscriptions
    - [ ] Upvote and downvote user updates
        - [ ] Down vote threshold causes deletion
    - [ ] Profile
        - [x] Username
        - [x] Password
        - [ ] Reputation
            - Number of up votes minus down votes
- Filter by
    - [x] Store(s)
        - [x] Support to only filter by one store or many stores
    - [x] Beer(s)
        - [x] Support to only filter by one beer brand or many beer brands
    - [ ] Price per beer
- API itself
   - [ ] Return response codes for each Lambda function
   - [ ] Change tables such that store name is a sort key
   - [x] Add DBAccess package for data access layer

&nbsp;
&nbsp;

###### Disclaimer
<sub>Build may show as errored because I am not uploading my RealCredentials.java file. Travis-CI doesn't like this so I try to comment it out but sometimes I forget and the build errors :(</sub>

[![Build Status](https://travis-ci.org/cpe305Spring17/spring2017-project-ethanharlig.svg?branch=master)](https://travis-ci.org/cpe305Spring17/spring2017-project-ethanharlig)

![Budget Brews](res/img/logo.png)

## Ethan Harlig 
### Hosting of website through gh-pages branch. Please [click here](https://cpe305spring17.github.io/spring2017-project-ethanharlig/) to check out my site!

<sub>Please note that since this project is using AWS Lambda with Java, the startup time of the functions is up to eight seconds. Because of this, the website may have a high latency when it hasn't been used in a while.</sub>

---
     
Crowdsource the best beer deals near you (_currently have support for San Luis Obispo, CA_)!

Users of Budget Brews are shown the cheapest beer deals near them! They can filter the beer prices by beer brand and store in which they can select multiple brands or stores to filter. 

- Users
    - [x] Notify users when prices are updated (Observer pattern)
      - [x] Subscribe to beers
         - [x] Notify subscribed users when a price has changed
    - x ] Upvote and downvote user updates
    - [x] Username
    - [x] Password
- Filter by
    - [x] Store(s)
        - [x] Support to only filter by one store or many stores
    - [x] Beer(s)
        - [x] Support to only filter by one beer brand or many beer brands

&nbsp;
&nbsp;

---

###### Disclaimer
<sub>Build may show as errored because I am not uploading my RealCredentials.java file. Travis-CI doesn't like this so I try to comment it out but sometimes I forget and the build errors :(</sub>

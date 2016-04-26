package io.mulberry.sample.twitter4j;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by yeongeon on 2/2/16.
 */
public class Twitter4jMain {

  public static void main(String[] args){
    ConfigurationBuilder cb = new ConfigurationBuilder();
    cb.setDebugEnabled(true)
        .setOAuthConsumerKey("")
        .setOAuthConsumerSecret("")
        .setOAuthAccessToken("")
        .setOAuthAccessTokenSecret("");
    TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
    StatusListener listener = new StatusListener() {
      @Override
      public void onStatus(Status status) {
        if(status.getGeoLocation()!=null){
          //if(!status.getLang().equals("ko")){ return; }
          System.out.println("---------------------------------------------");
          System.out.println(status.getLang());
          System.out.println("@" + status.getUser().getScreenName()
              + " - " + status.getText() + ", "+ status.getGeoLocation().getLatitude() +", "+ status.getGeoLocation().getLongitude() );
          System.out.println("---------------------------------------------");
        }
      }

      @Override
      public void onDeletionNotice(
          StatusDeletionNotice statusDeletionNotice) {
        //System.out.println("Got a status deletion notice id:"+ statusDeletionNotice.getStatusId());
      }

      @Override
      public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
        //System.out.println("Got track limitation notice:"+ numberOfLimitedStatuses);
      }

      @Override
      public void onScrubGeo(long userId, long upToStatusId) {
        //System.out.println("Got scrub_geo event userId:" + userId+ " upToStatusId:" + upToStatusId);
      }

      @Override
      public void onStallWarning(StallWarning warning) {
        //System.out.println("Got stall warning:" + warning);
      }

      @Override
      public void onException(Exception ex) {
        ex.printStackTrace();
      }
    };

    FilterQuery fq = new FilterQuery();
    String[] languages = new String[] {"ko"};

    double lat = 39.701343;
    double longitude = 126.532104;
    double lat1 = lat - .30;
    double longitude1 = longitude - .30;
    double lat2 = lat + .30;
    double longitude2 = longitude + .30;
    double[][] locations= {{longitude1, lat1}, {longitude2, lat2}};

    fq.locations(locations);
    //fq.language(languages);

    twitterStream.addListener(listener);
    twitterStream.filter(fq);
    //twitterStream.firehose(10);

    String[] users = {""};
    twitterStream.user(users);

    twitterStream.sample();

  }
}

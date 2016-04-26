package io.mulberry.sample.geoip;

import com.maxmind.geoip.LookupService;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

public class GeoipApplication {
  private static Logger LOG = LoggerFactory.getLogger(GeoipApplication.class);

  static final Class<GeoipApplication> SELF_CLASS = GeoipApplication.class;

  enum TYPE {
    ONE,
    TWO
  };

  private final TYPE type;

  public GeoipApplication(TYPE type){
    this.type = type;
  }

  private void geoip2() throws IOException, GeoIp2Exception {
    // https://github.com/maxmind/MaxMind-DB/blob/master/source-data%2FGeoIP2-Country-Test.json
    String targetFile = SELF_CLASS.getResource("/geoip/GeoIP2-Country-Test.mmdb").getPath();
    System.out.println("targetFile: "+ targetFile);
    File file = new File(targetFile);
    String host = "1.1.1.3"; //getLocalServerIp();
    System.out.printf("host: %s\n", host);

    DatabaseReader.Builder builder = new DatabaseReader.Builder(file);
    DatabaseReader reader = builder.build();
    InetAddress ipAddress = InetAddress.getByName(host);
    CountryResponse response = reader.country(ipAddress);
    String country = response.getCountry().toString();

    System.out.printf("country: %s\n", country);
  }

  private void geoip() throws IOException {
    String targetFile = SELF_CLASS.getResource("/geoip/GeoIP.dat").getPath();
    System.out.println("targetFile: "+ targetFile);
    File file = new File(targetFile);
    String host = "8.8.8.8"; //getLocalServerIp();
    System.out.printf("host: %s\n", host);

    LookupService lookup = new LookupService(file);
    String code = lookup.getCountry(host).getCode();
    String name = lookup.getCountry(host).getName();

    System.out.printf("code: %s, name: %s\n", code, name);
  }

  public void run() throws IOException, GeoIp2Exception {
    switch (this.type){
      case TWO:{
        geoip2();
      }break;
      case ONE:
      default:{
        geoip();
      }
    }
  }

  public static void main(String[] args) throws IOException, GeoIp2Exception {
    GeoipApplication app = new GeoipApplication(TYPE.ONE);
    app.run();
  }
}

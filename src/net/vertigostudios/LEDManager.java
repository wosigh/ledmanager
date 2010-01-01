package net.vertigostudios;

import com.palm.luna.LSException;
import com.palm.luna.service.LunaServiceThread;
import com.palm.luna.service.ServiceMessage;
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
import java.io.IOException;
//import java.io.InputStreamReader;
import org.json.JSONException;
import org.json.JSONObject;

public class LEDManager extends LunaServiceThread
{
  private String hwVersion;

  public LEDManager()
  {
    this.hwVersion = "1.0";
  }

  /*protected void init()
  {
      super.init();
  }*/
  public void version(ServiceMessage message)
  {
    try
    {
      StringBuilder sb = new StringBuilder(8192);
      sb.append("{version:");
      sb.append(JSONObject.quote(this.hwVersion));
      sb.append("}");
      message.respond(sb.toString());
    } catch (LSException e) {
      this.logger.severe("", e);
    }
  }

  public void flameOn(ServiceMessage msg) throws JSONException, LSException, IOException, InterruptedException {

      Process p = null;
      p = Runtime.getRuntime().exec("/media/cryptofs/apps/usr/palm/applications/net.vertigo-studios.app.myflashlight/app/scripts/lighton.sh");
      //p.waitFor();
      msg.respond("Flame on");
  }


  public void flameOff(ServiceMessage msg) throws JSONException, LSException, IOException, InterruptedException {
      Process p = null;
      p = Runtime.getRuntime().exec("/media/cryptofs/apps/usr/palm/applications/net.vertigo-studios.app.myflashlight/app/scripts/lightoff.sh");
      //p.waitFor();
      msg.respond("Flame off");
  }
  public void flameOn2(ServiceMessage msg) throws JSONException, LSException, IOException, InterruptedException {

      Process p = null;
      p = Runtime.getRuntime().exec("/lighton.sh");
      //p.waitFor();
      msg.respond("Flame on");
  }


  public void flameOff2(ServiceMessage msg) throws JSONException, LSException, IOException, InterruptedException {
      Process p = null;
      p = Runtime.getRuntime().exec("/lightoff.sh");
      //p.waitFor();
      msg.respond("Flame off");
  }
}

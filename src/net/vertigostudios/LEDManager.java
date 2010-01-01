package net.vertigostudios;

import com.palm.luna.LSException;
import com.palm.luna.service.LunaServiceThread;
import com.palm.luna.service.ServiceMessage;
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
//import java.io.InputStreamReader;
import org.json.JSONException;
import org.json.JSONObject;

public class LEDManager extends LunaServiceThread
{
  private String hwVersion;
  private String tmpFileName;
  private File tmpFile;

  public LEDManager()
  {
    this.hwVersion = "1.0";
    this.tmpFileName = "/tmp/ledshellscript.sh";
    this.tmpFile = new File (this.tmpFileName);
  }

  /*protected void init()
  {
      super.init();
  }*/

  @LunaServiceThread.PublicMethod
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
/*
  @LunaServiceThread.PublicMethod
  public void flameOn(ServiceMessage msg) throws JSONException, LSException, IOException, InterruptedException {

      Process p = null;
      p = Runtime.getRuntime().exec("/media/cryptofs/apps/usr/palm/applications/net.vertigo-studios.app.myflashlight/app/scripts/lighton.sh");
      //p.waitFor();
      msg.respond("Flame on");
  }

@LunaServiceThread.PublicMethod
  public void flameOff(ServiceMessage msg) throws JSONException, LSException, IOException, InterruptedException {
      Process p = null;
      p = Runtime.getRuntime().exec("/media/cryptofs/apps/usr/palm/applications/net.vertigo-studios.app.myflashlight/app/scripts/lightoff.sh");
      //p.waitFor();
      //msg.respond("Flame off");
  }
*/
@LunaServiceThread.PublicMethod
public void flameOn(ServiceMessage msg) throws JSONException, LSException, IOException, InterruptedException {

    try {
        //Check if file exists if it does then lets delete it to start fresh
      if (this.tmpFile.exists()) {
        this.tmpFile.delete();
      }

        BufferedWriter bw = new BufferedWriter(new FileWriter(this.tmpFile));
        bw.write("#!/bin/sh\n");

        bw.write("echo -n 1 >/sys/class/i2c-adapter/i2c-2/2-0033/avin\n");
        bw.write("echo -n 100mA >/sys/class/i2c-adapter/i2c-2/2-0033/torch_current\n");
        bw.write("echo -n torch >/sys/class/i2c-adapter/i2c-2/2-0033/mode\n");

        bw.write("exit 0\n");
        bw.flush();
        bw.close();
    }
    catch (Exception err) {
    }
  
      Process p = null;
      //p = Runtime.getRuntime().exec("chmod 744 /tmp/ledshellscript.sh");
      p = Runtime.getRuntime().exec("/bin/sh "+this.tmpFileName);
      //p.waitFor();
      //msg.respond("Flame on");
  }

@LunaServiceThread.PublicMethod
  public void flameOff(ServiceMessage msg) throws JSONException, LSException, IOException, InterruptedException {
    try {
        //Check if file exists if it does then lets delete it to start fresh
        if (this.tmpFile.exists()) {
            this.tmpFile.delete();
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter(this.tmpFile));
        bw.write("#!/bin/sh\n");
        bw.write("echo -n shutdown >/sys/class/i2c-adapter/i2c-2/2-0033/mode\n");
        bw.write("echo -n 0mA >/sys/class/i2c-adapter/i2c-2/2-0033/torch_current\n");
        bw.write("echo -n 0 >/sys/class/i2c-adapter/i2c-2/2-0033/avin\n");
        bw.write("exit 0\n");
        bw.flush();
        bw.close();
    }
    catch (Exception err) {

    }

      Process p = null;
      //p = Runtime.getRuntime().exec("chmod 744 /tmp/ledshellscript.sh");
      p = Runtime.getRuntime().exec("/bin/sh "+this.tmpFileName);
  }
}

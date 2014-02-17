package application;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Random;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayerBuilder;
import javafx.stage.Stage;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound2D extends Application implements Runnable
{
  private boolean sfx = true;

  private static short[] zombieSample;
  private static AudioFormat zombieFormat;

  private static short[] fireSample;
  private static AudioFormat fireFormat;

  private AudioInputStream playerStream;
  private AudioFormat playerFormat;
  private File[] stepArray;
  private File[] burnArray;

  private MediaPlayerBuilder menuBuilder;
  private MediaPlayerBuilder gameBuilder;
  private static volatile BooleanProperty menuSwitch;
  private static ChangeListener<Boolean> listen;
  private static MediaPlayer player;
  private static Media openScreen;
  ArrayList<Media> musicList;
  private Random rand;
  private static boolean isReadyFX = false;

  public void switchMenu()
  {
    while (isReadyFX == false)
    {
      try
      {
        menuSwitch.get();
      } catch (NullPointerException e)
      {
      }
    }
    menuSwitch.set(!menuSwitch.get());
    System.out.println("Switched");
  }

  public void pauseMusic()
  {
    player.pause();
  }

  public void playMusic()
  {
    while (player == null)
      ;
    player.play();
  }

  public Sound2D(boolean notMainSound)
  {

  }

  public Sound2D()
  {
    rand = new Random();
    try
    // Trying to load zombie sfx
    {
      File moanFile = new File("src/sounds/ZombieMoan.wav");
      AudioInputStream stream = AudioSystem.getAudioInputStream(moanFile);
      zombieFormat = stream.getFormat();
      byte[] array = getSamples(stream);
      zombieSample = new short[array.length / 2];
      for (int i = 0; i < zombieSample.length; i++)
      {
        zombieSample[i] = getSample(array, i * 2);
      }
    } catch (UnsupportedAudioFileException ex)
    {
      ex.printStackTrace();
    } catch (IOException ex)
    {
      System.out.println("Zombie Sound File does not exist; turning off sfx");
      sfx = false;
    }
    try
    // Trying to load fire sfx
    {
      File blastFile = new File("src/sounds/FireBlast.wav");
      AudioInputStream stream = AudioSystem.getAudioInputStream(blastFile);
      fireFormat = stream.getFormat();
      byte[] array = getSamples(stream);
      fireSample = new short[array.length / 2];
      for (int i = 0; i < fireSample.length; i++)
      {
        fireSample[i] = getSample(array, i * 2);
      }
    } catch (UnsupportedAudioFileException ex)
    {
      ex.printStackTrace();
    } catch (IOException ex)
    {
      System.out.println("Fire Sound File does not exist; turning off sfx");
      sfx = false;
    }
    File footFile = new File("src/sounds/footstep");
    stepArray = footFile.listFiles();
    File burnFile = new File("src/sounds/burning");
    burnArray = footFile.listFiles();
  }

  public void play(InputStream source, AudioFormat format)
  {

    // Use a short, 100ms (1/10th sec) buffer for real-time
    // change to the sound stream
    int bufferSize = format.getFrameSize()
        * Math.round(format.getSampleRate() / 10);
    byte[] buffer = new byte[bufferSize];

    // Create a line to play to
    SourceDataLine line;
    try
    {
      DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
      line = (SourceDataLine) AudioSystem.getLine(info);
      line.open(format, bufferSize);
    } catch (LineUnavailableException ex)
    {
      ex.printStackTrace();
      return;
    }

    // Start the line
    line.start();

    // Copy data to the line
    try
    {
      int numBytesRead = 0;
      while (numBytesRead != -1)
      {
        numBytesRead = source.read(buffer, 0, buffer.length);
        if (numBytesRead != -1)
        {
          line.write(buffer, 0, numBytesRead);
        }
      }
    } catch (IOException ex)
    {
      ex.printStackTrace();
    }

    // Wait until all data is played, then close the line
    line.drain();
    line.close();

  }

  // ==========================================================================
  // Helper method for getting a 16-bit sample from a byte array.
  // Samples are assumed to be in 16-bit, signed, little-endian format.
  // In computing, endianness is the ordering of bytes within a longer data
  // word.
  // Little-endian format means the least significant byte is at the lowest
  // address.
  // ==========================================================================
  public static short getSample(byte[] buffer, int position)
  {
    return (short) (((buffer[position + 1] & 0xff) << 8) | (buffer[position] & 0xff));
  }

  private byte[] getSamples(AudioInputStream audioStream)
  {
    // get the number of bytes to read
    int length = (int) (audioStream.getFrameLength() * audioStream.getFormat()
        .getFrameSize());

    // read the entire stream
    byte[] samples = new byte[length];
    DataInputStream inputStream = new DataInputStream(audioStream);
    try
    {
      // Blocks until all elements of samples have been populated or
      // until it throws an error because either not all the data fits in
      // samples
      // or not enough data is available or some other thread closes the input
      // stream.
      inputStream.readFully(samples);
    } catch (IOException ex)
    {
      System.out.println("Error reading Input Stream");
      ex.printStackTrace();
    }

    // return the samples
    return samples;
  }

  public void playDistSound(Point2D.Double source, Point2D.Double player, int playerFace,
      int soundType)
  {

    Thread sound = new Thread(this.new DistSound(source, player, playerFace,
        soundType));
    sound.start();
  }

  public void playRunSound()
  {
    if (sfx)
    {
      File[] array = stepArray;
      try
      {
        File playFile = array[rand.nextInt(array.length)];
        playerStream = AudioSystem.getAudioInputStream(playFile);
        playerFormat = playerStream.getFormat();
      } catch (UnsupportedAudioFileException ex)
      {
        ex.printStackTrace();
      } catch (IOException ex)
      {
        System.out.println("Step File does not exist");
        return;
      }
      Thread dummy = new Thread(new Runnable()
      {
        @Override
        public void run()
        {
          play(playerStream, playerFormat);
        }
      });
      dummy.start();
    }
  }

  public static void main(String[] args) throws InterruptedException
  {
    Sound2D man = new Sound2D();
    Thread t = new Thread(man);
    t.start();
    System.out.println("launch");
    man.playDistSound(new Point2D.Double(1, 1), new Point2D.Double(0, 0), 0, 1);
    man.playDistSound(new Point2D.Double(0, 0), new Point2D.Double(1, 1), 0, 2);
    man.switchMenu();
  }

  @Override
  public void run()
  {
    Application.launch();
  }

  public void start(Stage primaryStage) throws IOException, URISyntaxException
  {
    musicList = new ArrayList<>();
    File musicFile = new File("src/gameMusic");
    System.out.println("\"src/gameMusic\" exists?; " + musicFile.exists());
    String filePath = "file:///"
        + musicFile.getAbsolutePath().replace('\\', '/');
    System.out.println("Path is: " + filePath);
    for (String fileName : musicFile.list())
    {
      if (fileName.endsWith(".mp3"))
        musicList.add(new Media(filePath + "/" + fileName));
    }
    openScreen = new Media(filePath + "/menuMusic/EveningOfChaos.mp3");
    menuSwitch = new SimpleBooleanProperty(true);
    listen = new ChangeListener<Boolean>()
    {

      @Override
      public void changed(ObservableValue<? extends Boolean> arg0,
          Boolean arg1, Boolean arg2)
      {
        player.stop();
        if (menuSwitch.get())
        {
          player = menuBuilder.media(openScreen).build();
        } else
        {
          player = gameBuilder.media(
              musicList.get(rand.nextInt(musicList.size()))).build();
        }

      }
    };
    menuSwitch.addListener(listen);

    // Attaches the 'on Ready, play' runnable to the builder

    menuBuilder = MediaPlayerBuilder.create().onReady(new Runnable()
    {

      @Override
      public void run()
      {
        player.play();
      }
    }).onEndOfMedia(new Runnable()
    {

      @Override
      public void run()
      {
        player = menuBuilder.media(openScreen).build();

      }
    });
    player = menuBuilder.media(openScreen).build();
    gameBuilder = MediaPlayerBuilder.create().onReady(new Runnable()
    {

      @Override
      public void run()
      {
        player.play();
      }
    }).
    // Adds the looping scheme to the builder for in game music
        onEndOfMedia(new Runnable()
        {

          @Override
          public void run()
          {
            int val = rand.nextInt(musicList.size());
            System.out.println("Changed to song " + val);
            player = gameBuilder.media(musicList.get(val)).build();
          }
        });
    System.out.println("end of line");
    isReadyFX = true;
  }

  class DistSound implements Runnable
  {
    private Point2D.Double source;
    private Point2D.Double player;
    private byte faceDegree;
    private AudioFormat format;
    private short[] sample;

    DistSound(Point2D.Double source2, Point2D.Double player2, int faceDegree, int soundType)
    {
      this.source = source2;
      this.player = player2;
      this.faceDegree = (byte) faceDegree;
      if (soundType == 1)
      {
        format = zombieFormat;
        sample = zombieSample;
      } else if(soundType == 2)
      {
        format = fireFormat;
        sample = fireSample;
      } else if(soundType == 3)
      {
        try
        {
          File playFile = burnArray[rand.nextInt(burnArray.length)];
          playerStream = AudioSystem.getAudioInputStream(playFile);
          playerFormat = playerStream.getFormat();
        } catch (UnsupportedAudioFileException ex)
        {
          ex.printStackTrace();
        } catch (IOException ex)
        {
          System.out.println("Step File does not exist");
          return;
        }
      }
    }

    @Override
    public void run()
    {
      System.out.println("Gragh this is a moan");
      if (sfx)
      {
        // Player Direction
        Double leftEar = new Point2D.Double();
        Double rightEar = new Point2D.Double();
        switch (faceDegree)
        {
        case 1:// = north
          leftEar.setLocation(player.x - 25, player.y);
          rightEar.setLocation(player.x + 25, player.y);
          break;
        case 2:// = east
          leftEar.setLocation(player.x, player.y - 25);
          rightEar.setLocation(player.x, player.y + 25);
          break;
        case 3:// = south
          leftEar.setLocation(player.x + 25, player.y);
          rightEar.setLocation(player.x - 25, player.y);
          break;
        case 4:// = west
          leftEar.setLocation(player.x, player.y + 25);
          rightEar.setLocation(player.x, player.y - 25);
          break;
        case 5:// = northeast
          leftEar.setLocation(player.x -  17, player.y -  17);
          rightEar.setLocation(player.x +  17, player.y +  17);
          break;
        case 6:// = southeast
          leftEar.setLocation(player.x +  17, player.y -  17);
          rightEar.setLocation(player.x -  17, player.y +  17);
          break;
        case 7:// = southwest
          leftEar.setLocation(player.x + 17, player.y -  17);
          rightEar.setLocation(player.x - 17, player.y +  17);
          break;
        case 8:// = northwest
          leftEar.setLocation(player.x + 17, player.y + 17);
          rightEar.setLocation(player.x - 17, player.y - 17);
          break;
        }
        double distLeft = source.distance(leftEar);
        double distRight = source.distance(rightEar);
        double distTrue = source.distance(player);
        double changeWhole = 1 / distTrue;
        double change3D = distRight / distLeft;
        int offset = 2; // offset 2 is right, offset 1 is left;
        if (distRight > distLeft)
        {
          change3D = 1 / change3D;
          offset--;
        }
        short[] returnSample = new short[sample.length];
        for (int i = 0; i < sample.length; i++)
        {
          if (offset == 2)
          {
            returnSample[i] = (short) (sample[i] * change3D * changeWhole);
            offset = 0;
          } else
          {
            returnSample[i] = (short) (sample[i] * changeWhole);
          }
          offset++;
        }
        byte[] byteBuffer = new byte[returnSample.length * 2];
        for (int i = 0; i < returnSample.length; i++)
        {
          byteBuffer[i * 2] = (byte) (returnSample[i] & 0xff);
          byteBuffer[i * 2 + 1] = (byte) ((returnSample[i] >> 8) & 0xff);
        }
        InputStream inputStream = new ByteArrayInputStream(byteBuffer);
        play(inputStream, format);
      }
    }
  }

}

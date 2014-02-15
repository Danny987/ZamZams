package application;

import java.awt.Point;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Timer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayerBuilder;

public class Sound2D extends Application implements Runnable
{
  private boolean sfx = true;
  private boolean music = true;
  private static short[] zombieSample;
  private AudioFormat zombieFormat;
  private boolean initJavaFX;

  private MediaPlayerBuilder builder;
  private MediaPlayer player;
  ArrayList<Media> musicList;
  private Random rand;

  public void leaveMenu()
  {
    player.stop();
    player = builder.media(musicList.get(rand.nextInt(musicList.size())))
        .build();
  }

  public Sound2D()
  {
    rand = new Random();
    try
    // Trying to load zombie sfx
    {
      File moanFile = new File("ZombieMoan.wav");
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
  }

  void playDistSound(Point source, Point player, byte faceDegree,
      boolean isZombie)
  {
    Thread soundMachine = new Thread()
    {
      public void run()
      {
        if (sfx)
        { /* MAKE THIS THING SOMETIME SOON */
          Point leftEar = new Point();
          int sinVal = 0;
          System.out.println("Sin =" + sinVal);
          int offset = 2;
          if (sinVal < 0)
          {
            offset--;
            sinVal = sinVal * -1;
          }
          short soundSample[];
          // if(isZombie)
          // {

          // }
          // else
          // {

          // }
          short[] returnSample = new short[zombieSample.length];
          for (int i = 0; i < zombieSample.length; i++)
          {
            if (offset == 2)
            {
              returnSample[i] = (short) (zombieSample[i] * sinVal);
              offset = 0;
            } else
            {
              returnSample[i] = zombieSample[i];
            }
            offset++;
          }
          byte[] byteBuffer = new byte[returnSample.length * 2];
          for (int i = 0; i < returnSample.length; i++)
          {
            byteBuffer[i * 2] = (byte) (returnSample[i] & 0xff);
            byteBuffer[i * 2 + 1] = (byte) ((returnSample[i] >> 8) & 0xff);
          }
          InputStream streamMoan = new ByteArrayInputStream(byteBuffer);
          play(streamMoan, zombieFormat);
        }
      }
    };
    soundMachine.start();
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

  public static void main(String[] args)
  {
    Sound2D man = new Sound2D();
    Thread t = new Thread(man);
    t.start();
    System.out.println("launch");
    for (byte i = 0; i > 2; i++)
    {
      System.out.println("test");
      man.playDistSound(new Point(1, 1), new Point(0, 0), i, true);
    }
  }

  @Override
  public void run()
  {
    Sound2D test = new Sound2D();
    test.launch();
  }

  public void start(Stage primaryStage)
  {
    musicList = new ArrayList<>();
    // JavaFX should be initialized

    Media openScreen = new Media(
        "file:///C:/Users/Kratok/workspace/SoundMaster/EveningOfChaos.mp3");

    Media musOne = new Media(
        "file:///C:/Users/Kratok/workspace/SoundMaster/archipelago.mp3");
    Media musTwo = new Media(
        "file:///C:/Users/Kratok/workspace/SoundMaster/LostFrontier.mp3");
    Media musThree = new Media(
        "file:///C:/Users/Kratok/workspace/SoundMaster/Hush.mp3");

    /*
     * Attaches the 'on Ready, play' runnable to the builder
     */
    builder = MediaPlayerBuilder.create().onReady(new Runnable()
    {

      @Override
      public void run()
      {
        player.play();
      }
    }).// Adds the looping scheme to the builder for in game music
    onEndOfMedia(new Runnable()
    {

      @Override
      public void run()
      {
        int val = rand.nextInt(musicList.size());
        System.out.println("Changed to song" + val);
        player = builder.media(musicList.get(val))
            .build();
      }
    });
    player = builder.media(musOne).build();
    // For cycling through the different available mp3 files
    musicList.add(musOne);
    musicList.add(musTwo);
    musicList.add(musThree);
    System.out.println("End of line");
  }
}

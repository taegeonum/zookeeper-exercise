package kr.ac.snu.cms.zookeeper;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Random;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

public class Barrier extends SyncPrimitive {
  int size;
  String name;

  /**
   * Barrier constructor
   *
   * @param address
   * @param root
   * @param size
   */
  Barrier(String address, String root, int size) {
    super(address);
    this.root = root;
    this.size = size;

    if (zk != null) {
      try {
        Stat s = zk.exists(root, false);
        if (s == null) {
          /* 1. Create  persistent barrier node */ 
          zk.
        }
      } catch (KeeperException e) {
        System.out
        .println("Keeper exception when instantiating queue: "
            + e.toString());
      } catch (InterruptedException e) {
        System.out.println("Interrupted exception");
      }
    }

    try {
      name = new String(InetAddress.getLocalHost().getCanonicalHostName().toString());
    } catch (UnknownHostException e) {
      System.out.println(e.toString());
    }

  }

  /**
   * Join barrier
   *
   * @return
   * @throws KeeperException
   * @throws InterruptedException
   */

  boolean enter() throws KeeperException, InterruptedException{
    /* 2. Create **** child node */
    zk.
    
    while (true) {
      synchronized (mutex) {
        List<String> list = zk.getChildren(root, /* true or false? */);

        /* waiting */
        if (list.size() < size) {
          mutex.wait();
        } else {
          return true;
        }
      }
    }
  }

  /**
   * Wait until all reach barrier
   *
   * @return
   * @throws KeeperException
   * @throws InterruptedException
   */

  boolean leave() throws KeeperException, InterruptedException{
    /* 3. ?? */ 
    
    while (true) {
      /* waiting */
      synchronized (mutex) {
        List<String> list = zk.getChildren(root, /* true of false? */);
        if (list.size() > 0) {
          mutex.wait();
        } else {
          return true;
        }
      }
    }
  }
  
  /*
   * One argument: the number of total processes
   */
  public static void main(String[] args) {
    Barrier b = new Barrier("localhost", "/b1", new Integer(args[0]));
    try{
      boolean flag = b.enter();
      System.out.println("Entered barrier: " + args[0]);
      if(!flag) System.out.println("Error when entering the barrier");
    } catch (KeeperException e){

    } catch (InterruptedException e){

    }

    // Generate random integer
    Random rand = new Random();
    int r = rand.nextInt(100);
    // Loop for rand iterations
    for (int i = 0; i < r; i++) {
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {

      }
    }
    
    try{
      b.leave();
    } catch (KeeperException e){

    } catch (InterruptedException e){

    }
    System.out.println("Left barrier");
  }
}
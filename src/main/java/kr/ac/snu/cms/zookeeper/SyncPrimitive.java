package kr.ac.snu.cms.zookeeper;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Random;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

public class SyncPrimitive implements Watcher {

  static ZooKeeper zk = null;
  static Integer mutex;

  String root;

  SyncPrimitive(String address) {
    if(zk == null){
      try {
        /* Create a zookeeper client */
        System.out.println("Starting ZK:");
        zk = new ZooKeeper(address, 2181, this); // register watcher 
        mutex = new Integer(-1);
        System.out.println("Finished starting ZK: " + zk);
      } catch (IOException e) {
        System.out.println(e.toString());
        zk = null;
      }
    }
    //else mutex = new Integer(-1);
  }

  synchronized public void process(WatchedEvent event) {
    synchronized (mutex) {
      //System.out.println("Process: " + event.getType());
      mutex.notify();
    }
  }
}
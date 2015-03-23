package kr.ac.snu.cms.zookeeper;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;


public class NaiveLeaderElection implements Watcher {

  private ZooKeeper zk;
  private final String root;
  private final String number;
  private boolean isLeader; 
  
  /*
   * Naive leader election example
   * It creates a persistent election node and adds a sequential child node to the election node.
   * 
   * If its sequential number is lowest, then it is leader. If not, it is follower. 
   */
  NaiveLeaderElection(String root, String address) throws KeeperException, InterruptedException  {

    try {
      System.out.println("Starting ZK:");
      /* 1. Create ZooKeeper client and set watcher to this */
      this.zk = ;
      System.out.println("Finished starting ZK: " + zk);
    } catch (IOException e) {
      System.out.println(e.toString());
      zk = null;
    }
    
    this.root = root;
    if (zk != null) {
      try {
        /* 2. Check root node exists */ 
        Stat s = ;
        if (s == null) {
          /* 3. Create persistent root node */ 
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
    
    /* 4. Create ephemeral sequential node to the root node. */ 
    String actualPath = zk.create(/* TODO */, new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
    
    /* Get my sequence number */ 
    String[] splited = actualPath.split("/");
    this.number = splited[splited.length-1];
    
    /* Find leader */
    findAndWatchLeader();
  }
  
  private void findAndWatchLeader() {
    List<String> childrens = null;
    try {
      /* 5. get children of the root node */
      childrens = 
    } catch (KeeperException | InterruptedException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    
    System.out.println("my number: " + this.number + ", childrens: " + childrens);
    String leader = findLeader(childrens);
    
    if (this.number.compareTo(leader) == 0) {
      this.isLeader = true;
      System.out.println("I'm leader");
    } else {
      System.out.println("I'm follower");
      this.isLeader = false;
      // watch leader 
      watchLeader(root, leader);
    }
  }
  
  private void watchLeader(String root, String leader) {
    try {
      /* 6. Add watcher to the leader node */
      
    } catch (KeeperException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void process(WatchedEvent event) {
    findAndWatchLeader();
  }
  
  private String findLeader(List<String> childrens) {
    String leader = null;
    
    for (String children : childrens) {
      if (leader == null) {
        leader = children;
      } else {
        if (leader.compareTo(children) > 0) {
          leader = children;
        }
      }
    }
    
    return leader;
  }

  public void doSth() {
    while(!this.isLeader) {
      try {
        Thread.sleep(1000);
        System.out.println("Follower does sth...");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    try {
      for (int i = 0; i < 8; i++) {
        Thread.sleep(1000);
        System.out.println("Leader does sth...");
      }
      
      System.out.println("Leader fails");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }
  
  public static void main(String[] args) throws KeeperException, InterruptedException {

    NaiveLeaderElection e1 = new NaiveLeaderElection("/election", "localhost");
    e1.doSth();
  }


}

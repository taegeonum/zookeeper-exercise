
EXC="java -cp target/zk-practice-1.0-SNAPSHOT.jar kr.ac.snu.cms.zookeeper."
CMD=""
if [ "$1" = "leader" ];then
  $EXC"NaiveLeaderElection"
elif [ "$1" = "barrier" ];then
  if [ "$#" -eq 2 ];then
    CMD=$EXC"Barrier "$2
    echo $CMD
  else
    echo "Illegal number of parameters: enter process number"
  fi
elif [ "$1" = "queue" ]; then
  if [ "$#" -eq 3 ];then
    CMD=$EXC"Queue "$2" "$3
    echo $CMD
  else
    echo "Illegal number of parameters: enter producer/consumer type and the number of elements"
  fi
fi

$CMD

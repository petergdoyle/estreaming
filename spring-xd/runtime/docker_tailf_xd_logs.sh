#!/bin/sh

  while true; do
  echo -e "*** select the output from stream *** \n\
  1) jmstest \n\
  2) jmstest_with_transform \n\
  3) jms_payload_test \
  "
  read opt

  case $opt in
      1)
      fn='jmstest'
      break
      ;;
      2)
      fn='jmstest_with_transform'
      break
      ;;
      3)
      fn='jms_payload_test'
      break
      ;;
      *)
      echo "invalid option"
      ;;
  esac
  done


docker exec -ti estreaming-xd-singlenode tail -f /tmp/xd/output/$fn.out

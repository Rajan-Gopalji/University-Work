#! /bin/sh

export CLASSPATH=.:../testing/addressBookTest/:../testing/junit.jar
java -cp ${CLASSPATH} AddressBookTest
read line

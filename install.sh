#!/bin/bash
if [[ "$1" == "--help" ]]; then
  echo "
  Usage: ./script [OPTION]...

  Install Java (JDK 1.8),
  Maven (Latest Version),
  Git (Latest Version),
  PostgreSQL (Version 10)

  OPTIONS:
        --help     display this help and exit
        --verbose  provides additional details during sscipt execution.

  Exit Status:
    Returns success when all necessary applications for Java development installed."
  exit
fi
echo "Environment setup"
if [[ "$1" == "--verbose" ]]; then
   if ! (yum list installed java-1.8.0-openjdk) then
      echo "JDK setup"
      sudo yum install java-1.8.0-openjdk --assumeyes --verbose
      java -version
   fi
   if ! (yum list installed maven) then
      echo "Maven setup"
      sudo yum install maven --assumeyes --verbose
      mvn -version
   fi
   if ! (yum list installed git) then
      echo "Git setup"
      sudo yum install git --assumeyes --verbose
      git --version
   fi
   if ! (yum list installed postgresql-server) then
      echo "PostgreSQL 10 setup"
      sudo yum install postgresql-server --assumeyes --verbose
   fi
else
   if ! (yum list installed java-1.8.0-openjdk) then
      echo "no JDK found. JDK setup"
      sudo yum install java-1.8.0-openjdk --assumeyes --quiet
   fi
   if ! (yum list installed maven) then
      echo "No maven found. Maven setup"
      sudo yum install maven --assumeyes --quiet
   fi
   if ! (yum list installed git) then
      echo "No git found. Git setup"
      sudo yum install git --assumeyes --quiet
   fi
   if ! (yum list installed postgresql-server) then
      echo "No PosthreSQL found. PostgreSQL 10 setup"
      sudo yum install postgresql-server --assumeyes --quiet
   fi
fi
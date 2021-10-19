#!/bin/bash

isVerbose=false
limits=()
for arg in "$@"
do
        if [ "$arg" == "--verbose" ]; then isVerbose=true
        fi
done
for arg in "$@"
do
case "$arg" in
  "--help"   )
          echo "
  Usage: ./script [OPTION]...

  java application which should be able to download data from the mentioned API and save it in various targets (DB, file).

  OPTIONS:
        --help     display this help and exit
        --verbose  provides additional details during sscipt execution.
        -Dstorage  specifies storage type (db, file)
        -Doutpath  required when the storage type is file
        -Ddate     date range
        -Dcoordinates path to file which contains coordinate data
        --clear clear database data
        --build build project with maven
        --check show table content
  Exit Status:
    Returns success when all necessary applications for Java development installed."
  exit;;
 "--clear"   )
         psql -U postgres -d postgres -c "DELETE FROM crimes";
         psql -U postgres -d postgres -c "DELETE FROM streets";
         psql -U postgres -d postgres -c "DELETE FROM outcome_statuses";
         psql -U postgres -d postgres -c "DELETE FROM locations";exit;;
 "--build" ) if [ "$isVerbose" == true ]; then
                mvn package;
            else
                mvn package --quiet; echo "build success";
        fi
        exit;;
 "--check" ) echo "content of crimes"; psql -U postgres -d postgres -c "SELECT * FROM crimes";
             echo "content of locations"; psql -U postgres -d postgres -c "SELECT * FROM locations";
             echo "content of streets"; psql -U postgres -d postgres -c "SELECT * FROM streets";
             echo "content of statuses"; psql -U postgres -d postgres -c "SELECT * FROM outcome_statuses";
        exit;;
 *       ) limits+=("$arg");;
esac
done
psql -U postgres -d postgres -c "SELECT 1 FROM crimes" > /dev/null;
existCrimes=$?;
psql -U postgres -d postgres -c "SELECT 1 FROM locations" > /dev/null;
existLocations=$?;
psql -U postgres -d postgres -c "SELECT 1 FROM streets" > /dev/null;
existStreets=$?;
psql -U postgres -d postgres -c "SELECT 1 FROM outcome_statuses" > /dev/null;
existStatuses=$?;
if [[ $xistCrimes -ne 0 ]] || [[ $existLocations -ne 0 ]] || [[ $existStreets -ne 0 ]] || [[ $existStatuses -ne 0 ]];  then
   psql -U postgres -d postgres -f script.sql;
fi

java -jar target/test-1.0-SNAPSHOT.jar "${limits[@]}"
if [ $? -eq 0 ]; then
   echo "done";
fi

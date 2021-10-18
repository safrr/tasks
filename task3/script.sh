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
        *       ) limits+=("$arg");;
    esac
done

java -jar target/test-1.0-SNAPSHOT.jar "${limits[@]}"
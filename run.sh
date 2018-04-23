#!/bin/bash
./compile.sh
lancerserveur(){
    rm -f ./rmipresence
    ps aux | grep rmiregistry | grep -v grep > rmipresence
    rmipresence="rmipresence"
    if [ -s $rmipresence ]
    then
        echo "rmiregistry déjà lancé"
    else
        echo "Lancement rmiregistry ..."
        cd ./classes && rmiregistry&
        echo "Lancement rmiregistry Fait!"
    fi
    echo $DIRSTACK
    java -cp classes projetrmi.Serveur
}
lancerclient(){
    java -cp classes projetrmi.Client
}
case "$1" in
    client)
        lancerclient
    ;;
    serveur)
        lancerserveur
    ;;
    *)
        echo $"Usage: $0 {client|serveur}"
        exit 1
esac
exit 0

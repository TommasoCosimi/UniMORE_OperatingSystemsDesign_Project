#!/bin/bash
echo "Elimino i contenuti della cartella bin"
rm -rf ./bin/
mkdir bin

echo "Mi muovo nella cartella src:"
cd ./src
pwd

echo "Compilo le classi in bin"
javac -d ../bin ./*.java
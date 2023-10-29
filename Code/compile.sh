#!/bin/bash
echo "Elimino i contenuti della cartella bin"
rm -rf ./bin/
mkdir bin
cd bin
mkdir SupportFiles
mkdir Examples
cd ..

echo "Mi muovo nella cartella src/SupportFiles:"
cd ./src/SupportFiles
pwd

echo "Compilo le classi in bin"
javac -d ../../bin/SupportFiles ./*.java
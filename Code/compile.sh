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

echo "Creo dei file di supporto per l'utilizzo di alcune classi"
cd ../../bin/SupportFiles
echo "File di esempio contenente una riga." > FileConUnaRiga.txt
echo "File di esempio contenente più righe." > FileConPiuRighe.txt
echo "Riga aggiuntiva." >> FileConPiuRighe.txt
echo "Ulteriore riga aggiuntiva." >> FileConPiuRighe.txt
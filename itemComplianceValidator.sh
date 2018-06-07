#!/bin/bash

echo Digite o copie la ruta completa a analizar:

read path

java -jar itemComplianceValidator.jar $path

read -r -p "Presione cualquier tecla para continuar" -n 1
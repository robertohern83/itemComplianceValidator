@echo off
set /p path=Digite o copie la ruta completa a analizar: 

@echo on
cls

java -jar itemComplianceValidator.jar %path%
pause
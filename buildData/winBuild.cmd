@echo off
SETLOCAL
set version=%1
@echo on
javapackager -deploy -v -native image -BsystemWide=false -Bicon=logos\logoAll.ico -BappVersion=%version% -outdir Windows\C57note64%version%Bundle -outfile C57note64 -srcdir jars -srcfiles C57note64%version%.jar -appclass c57note64.C57note64Main -name C57note64 -title "C57note64"
@echo off
copy LICENSE.txt Windows\C57note64%version%Bundle\bundles\C57note64\LICENSE.txt
ENDLOCAL
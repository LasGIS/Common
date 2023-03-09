@echo off

call mvn package

if errorlevel 1 exit 1

pushd assembly\target\tracking-portal-admin.assembly-1.0.0-SNAPSHOT-bin\tracking-portal-admin.assembly-1.0.0-SNAPSHOT

call start1.cmd

popd

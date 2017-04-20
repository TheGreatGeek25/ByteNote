; Default script for building ByteNote

#define MyAppName "ByteNote"
#define MyAppVersion "1.1.1"
#define MyAppExeName "ByteNote.exe"

[Setup]
; NOTE: The value of AppId uniquely identifies this application.
; Do not use the same AppId value in installers for other applications.
; (To generate a new GUID, click Tools | Generate GUID inside the IDE.)
AppName={#MyAppName}
AppVersion={#MyAppVersion}
;AppVerName={#MyAppName} {#MyAppVersion}
;AppPublisher={#MyAppPublisher}
;AppPublisherURL={#MyAppURL}
;AppSupportURL={#MyAppURL}
;AppUpdatesURL={#MyAppURL}
DefaultDirName={pf}\{#MyAppName}
DefaultGroupName={#MyAppName}
LicenseFile=C:\Users\D\Documents\CharliesStuff\C57note64\Windows\C57note64v{#MyAppVersion}Bundle\bundles\C57note64\LICENSE.txt
OutputDir=C:\Users\D\Documents\CharliesStuff\C57note64\Windows\EXEinstallers
OutputBaseFilename=C57note64v{#MyAppVersion}-windows-32bit-setup
SetupIconFile=C:\Users\D\Documents\CharliesStuff\C57note64\Windows\C57note64v{#MyAppVersion}Bundle\bundles\C57note64\C57note64.ico
ChangesAssociations=yes
UninstallDisplayIcon={app}\{#MyAppExeName}
VersionInfoProductName={#MyAppName}
VersionInfoProductVersion={#MyAppVersion}
VersionInfoVersion={#MyAppVersion}
Compression=lzma2/max
SolidCompression=yes

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked
Name: "associate"; Description: "&Associate files"; GroupDescription: "Other tasks:"


[Files]
Source: "C:\Users\D\Documents\CharliesStuff\C57note64\Windows\C57note64v{#MyAppVersion}Bundle\bundles\C57note64\C57note64.exe"; DestDir: "{app}"; Flags: ignoreversion
Source: "C:\Users\D\Documents\CharliesStuff\C57note64\Windows\C57note64v{#MyAppVersion}Bundle\bundles\C57note64\*"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs
; NOTE: Don't use "Flags: ignoreversion" on any shared system files

[Registry]
Root: HKCR; Subkey: ".cnote"; ValueType: string; ValueName: ""; ValueData:"C57note64NoteFile"; Flags: uninsdeletevalue; Tasks: associate
Root: HKCR; Subkey: "C57note64NoteFile"; ValueType: string; ValueName: ""; ValueData: "C57note64 Note File"; Flags: uninsdeletekey; Tasks: associate
Root: HKCR; Subkey: "C57note64NoteFile\DefaultIcon"; ValueType: string; ValueName: ""; ValueData: "{app}\{#MyAppExeName}"; Tasks: associate
Root: HKCR; Subkey: "C57note64NoteFile\shell\open\command"; ValueType: string; ValueName: ""; ValueData: """{app}\{#MyAppExeName}"" ""%l"""; Tasks: associate

[Icons]
Name: "{group}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"
Name: "{group}\{cm:UninstallProgram,{#MyAppName}}"; Filename: "{uninstallexe}"
Name: "{commondesktop}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; Tasks: desktopicon

[Run]
Filename: "{app}\{#MyAppExeName}"; Description: "{cm:LaunchProgram,{#StringChange(MyAppName, '&', '&&')}}"; Flags: nowait postinstall skipifsilent


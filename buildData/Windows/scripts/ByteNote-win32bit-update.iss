;#define bundleDir
;#define outputDir
;#define appName
;#define appVersion
;#define appExeName
;#define outputName

[Setup]
AppName={#appName}
AppId={#appName}
UsePreviousAppDir=yes
CreateUninstallRegKey=no
UpdateUninstallLogAppName=no
AppVersion={#appVersion}
DefaultDirName={pf}\{#appName}
DefaultGroupName={#appName}
LicenseFile={#bundleDir}\LICENSE.txt
OutputDir={#outputDir}
OutputBaseFilename={#outputName}
SetupIconFile={#bundleDir}\{#appName}.ico
ChangesAssociations=yes
UninstallDisplayIcon={app}\{#appExeName}
VersionInfoProductName={#appName}
VersionInfoProductVersion={#appVersion}
VersionInfoVersion={#appVersion}
Compression=lzma2/max
SolidCompression=yes

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked
Name: "associate"; Description: "&Associate files"; GroupDescription: "Other tasks:"


[Files]
Source: "{#bundleDir}\{#appName}.exe"; DestDir: "{app}"; Flags: ignoreversion
Source: "{#bundleDir}\*"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs
; NOTE: Don't use "Flags: ignoreversion" on any shared system files

[Registry]
Root: HKCR; Subkey: ".cnote"; ValueType: string; ValueName: ""; ValueData:"{#appName}OldNoteFile"; Flags: uninsdeletevalue; Tasks: associate
Root: HKCR; Subkey: ".bynt"; ValueType: string; ValueName: ""; ValueData:"{#appName}NoteFile"; Flags: uninsdeletevalue; Tasks: associate
Root: HKCR; Subkey: "{#appName}OldNoteFile"; ValueType: string; ValueName: ""; ValueData: "{#appName} Note File (obsolete)"; Flags: uninsdeletekey; Tasks: associate
Root: HKCR; Subkey: "{#appName}NoteFile"; ValueType: string; ValueName: ""; ValueData: "{#appName} Note File"; Flags: uninsdeletekey; Tasks: associate
Root: HKCR; Subkey: "{#appName}OldNoteFile\DefaultIcon"; ValueType: string; ValueName: ""; ValueData: "{app}\{#appExeName}"; Tasks: associate
Root: HKCR; Subkey: "{#appName}NoteFile\DefaultIcon"; ValueType: string; ValueName: ""; ValueData: "{app}\{#appExeName}"; Tasks: associate
Root: HKCR; Subkey: "{#appName}OldNoteFile\shell\open\command"; ValueType: string; ValueName: ""; ValueData: """{app}\{#appExeName}"" ""%l"""; Tasks: associate
Root: HKCR; Subkey: "{#appName}NoteFile\shell\open\command"; ValueType: string; ValueName: ""; ValueData: """{app}\{#appExeName}"" ""%l"""; Tasks: associate

[Icons]
Name: "{group}\{#appName}"; Filename: "{app}\{#appExeName}"
Name: "{group}\{cm:UninstallProgram,{#appName}}"; Filename: "{uninstallexe}"
Name: "{commondesktop}\{#appName}"; Filename: "{app}\{#appExeName}"; Tasks: desktopicon

[Run]
Filename: "{app}\{#appExeName}"; Description: "{cm:LaunchProgram,{#StringChange(appName, '&', '&&')}}"; Flags: nowait postinstall skipifsilent


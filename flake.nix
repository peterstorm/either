{ 
description = "Flake to manage my Java workspace.";

inputs.nixpkgs.url = "nixpkgs/nixpkgs-unstable";

outputs = inputs: 
let
  system = "aarch64-darwin";
  pkgs = inputs.nixpkgs.legacyPackages.${system};
  javaVersion = pkgs.jdk21_headless;
in { 
  devShell.${system} = pkgs.mkShell rec {
    name = "java-shell";
    packages = with pkgs; [ 
	vscode 
	gh 
	maven
    ];
    buildInputs = [ 
	javaVersion
    ];
    shellHook = ''
      export JAVA_HOME=${javaVersion}
    '';
  };
 };
}

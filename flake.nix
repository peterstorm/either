{
  description = "Flake to manage my Java workspace.";
  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
    flake-parts.url = "github:hercules-ci/flake-parts";
    flake-parts.inputs.nixpkgs-lib.follows = "nixpkgs";
  };
  outputs = inputs@{ self, nixpkgs, flake-parts, ... }:
    flake-parts.lib.mkFlake { inherit inputs; } {
      systems = [
        "x86_64-linux"
        "aarch64-darwin"
      ];

      perSystem = { pkgs, config, system, ... }: let
      javaVersion = pkgs.jdk21_headless;
      in {
        devShells.default = pkgs.mkShell {
          packages = with pkgs; [
          ];
          buildInputs = with pkgs;
            [
              javaVersion
              maven
            ];
          shellHook = ''
            export JAVA_HOME=${javaVersion}
          '';
        };
      };
    };
}

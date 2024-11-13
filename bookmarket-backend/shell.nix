{ pkgs ? import <nixpkgs> {} }:
pkgs.mkShell {
    buildInputs = with pkgs; [
        jdk
    ];
    packages = with pkgs; [
        maven
        sqlite
    ];
}

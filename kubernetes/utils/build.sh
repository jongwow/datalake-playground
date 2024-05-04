#!/bin/bash

# Define the source directory where Go files are located
SOURCE_DIR="./lib" # Update this path to your source directory

# Define the target directory where executables will be placed
TARGET_DIR="./command" # Update this path to your target directory

# Create the target directory if it doesn't exist
mkdir -p "$TARGET_DIR"

# Loop over each Go file in the source directory
for go_file in "$SOURCE_DIR"/*.go; do
    # Extract the base filename without path and extension
    base_name=$(basename "$go_file" .go)

    # Compile the Go file and output the executable to the target directory
    go build -o "$TARGET_DIR/$base_name" "$go_file"
    if [ $? -eq 0 ]; then
        echo "Built $base_name successfully"
    else
        echo "Failed to build $base_name"
    fi
done
